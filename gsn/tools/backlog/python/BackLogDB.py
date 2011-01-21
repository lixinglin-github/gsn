
__author__      = "Tonio Gsell <tgsell@tik.ee.ethz.ch>"
__copyright__   = "Copyright 2010, ETH Zurich, Switzerland, Tonio Gsell"
__license__     = "GPL"
__version__     = "$Revision$"
__date__        = "$Date$"
__id__          = "$Id$"
__source__      = "$URL$"

import sqlite3
import time
import os
import logging
from threading import Thread, Lock, Event

from Statistics import StatisticsClass


SLEEP_BEFORE_RESEND_ON_RECONNECT = 30


class BackLogDBClass(Thread, StatisticsClass):
    '''
    Offers the backlog functionality.
    
    Backlogs messages in a sqlite3 database using their timestamp as primary key.
    
    If buffer_size is 0, sqlite3 INSERT action will be asynchronous
    otherwise this action will be executed in synchronous mode.
    The DELETE action is always asynchronous.
    '''

    '''
    data/instance attributes:
    _logger
    _backlogMain
    _dbname
    _con
    _cur
    _dbNumberOfEntriesId
    _storeCounterId
    _storeTimeId
    _removeCounterId
    _removeTimeId
    _dblock
    _sleep
    _resend
    _resendtimer
    _stopped
    '''

    def __init__(self, parent, dbname, backlog_db_resend_hr):
        '''
        Inititalizes the backlog database.
        
        @param parent: the BackLogMain object
        @param dbname: the name/path of the slite3 database used for backlogging.
            If it does not yet exist a new database will be created.
        @param backlog_db_resend_hr: countinous backlog database content resend
            interval in hours.
        
        @raise Exception: if there is a problem with the sqlite3 database.
        '''
        Thread.__init__(self)
        StatisticsClass.__init__(self)
        
        self._storeCounterId = self.createCounter(60)
        self._storeTimeId = self.createCounter(60)
        self._removeCounterId = self.createCounter(60)
        self._removeTimeId = self.createCounter(60)

        self._logger = logging.getLogger(self.__class__.__name__)
        
        # initialize variables
        self._backlogMain = parent
        self._dbname = dbname
        
        # thread lock to coordinate access to the database
        self._dblock = Lock()
        self._resend = Event()
        self._sleepEvent = Event()
        
        # try to create/open database
        self._dblock.acquire()
        try:
            # check_same_thread is not necessary, we are use a global lock 
            self._con = sqlite3.connect(self._dbname, check_same_thread=False)
            self._cur = self._con.cursor()
            
            self._logger.info('integrity check of database')
            
            self._cur.execute('PRAGMA integrity_check')
            if self._cur.fetchone()[0] != 'ok':
                raise sqlite3.Error('failed database integrity check')
            
            self._logger.info('vacuum database')
            
            self._con.execute('VACUUM')
            
            self._con.execute('PRAGMA synchronous = OFF')
            
            self._con.execute('CREATE table IF NOT EXISTS backlogmsg (timestamp INTEGER PRIMARY KEY ON CONFLICT REPLACE, type INTEGER, message BLOB)')
            
            self._con.execute('CREATE INDEX IF NOT EXISTS type_index ON backlogmsg (type)')
            
            self._cur.execute('SELECT COUNT(1) FROM backlogmsg')
            self._dbNumberOfEntriesId = self.createCounter(initCounterValue=self._cur.fetchone()[0])
            self._dblock.release()
            self._logger.info(str(self.getCounterValue(self._dbNumberOfEntriesId)) + ' entries in database')
            
            if self.getCounterValue(self._dbNumberOfEntriesId) > 0:
                self._isBusy = True
            else:
                self._isBusy = False
            
            self._con.commit()
        except sqlite3.Error, e:
            self._dblock.release()
            raise TypeError('sqlite3: ' + e.__str__())
        except Exception, e:
            self._dblock.release()
            raise TypeError(e.__str__())
    
        self._stopped = False
        self._sleep = False
        
        self._resendtimer = ResendTimer(backlog_db_resend_hr*3600, self.resend)
        
        self._logger.debug('database ' + self._dbname + ' ready to use')
        
        
    def storeMsg(self, timestamp, msgType, data):
        '''
        Store a message in the buffer/backlog database.
        
        The timestamp is used as primary key and has to be unique.
        
        @param timestamp: the timestamp of the message
        @param type: the message type
        @param data: the whole message
        
        @return: True if the message has been stored in the buffer/database otherwise False
        '''
        id = self.timeMeasurementStart()
        
        try:
            self._dblock.acquire()
            self._con.execute('INSERT INTO backlogmsg VALUES (?,?,?)', (timestamp, msgType, sqlite3.Binary(data)))
            self._con.commit()
            self.counterAction(self._dbNumberOfEntriesId)
            self._dblock.release()
            
            storeTime = self.timeMeasurementDiff(id)
            self.counterAction(self._storeTimeId, storeTime)
            self.counterAction(self._storeCounterId)

            self._logger.debug('store (%d,%d,%d): %f s' % (msgType, timestamp, len(data), storeTime))
            return True
        except sqlite3.Error, e:
            self._dblock.release()
            if not self._stopped:
                self.exception(e)
            return False

        
    def removeMsg(self, timestamp, msgType):
        '''
        Remove a message out of the buffer/database with a given timestamp.
        
        If a message with the given timestamp does exist in the buffer/database,
        it will be deleted.
        
        @param timestamp: the timestamp of the message to be removed
        '''
        id = self.timeMeasurementStart()

        try:
            self._dblock.acquire()
            self._cur.execute('SELECT COUNT(1) FROM backlogmsg WHERE timestamp = ? and type = ?', (timestamp,msgType))
            cnt = self._cur.fetchone()[0]
            if cnt >= 1:
                self._con.execute('DELETE FROM backlogmsg WHERE timestamp = ? and type = ?', (timestamp,msgType))
                self._con.commit()
                
            self._dblock.release()
            removeTime = self.timeMeasurementDiff(id)
            
            if cnt >= 1:
                self.counterAction(self._dbNumberOfEntriesId, -cnt)
                self.counterAction(self._removeTimeId, removeTime)
                self.counterAction(self._removeCounterId)

            self._logger.debug('del (%d,%d,?): %f s' % (msgType, timestamp, removeTime))
        except sqlite3.Error, e:
            self.timeMeasurementDiff(id)
            self._dblock.release()
            if not self._stopped:
                self.exception(e) 
            
            
    def getStatus(self, intervalSec):
        '''
        Returns the status of the backlog database as list:
        
        @param intervalSec: the passed n seconds over which min/mean/max is calculated.
        
        @return: status of the backlog database [number of database entries,
                                                 database file size, 
                                                 stores per second, 
                                                 removes per second, 
                                                 store counter, 
                                                 remove counter, 
                                                 minimum store time, 
                                                 average store time, 
                                                 maximum store time, 
                                                 minimum remove time, 
                                                 average remove time, 
                                                 maximum remove time]
        '''
        dbentries = self.getCounterValue(self._dbNumberOfEntriesId)
        dbsize = int(os.path.getsize(self._dbname)/1024)
        stpersec = self.getAvgCounterIncPerSecond(self._storeCounterId, [intervalSec])[0]
        rmpersec = self.getAvgCounterIncPerSecond(self._removeCounterId, [intervalSec])[0]
        cntst = self.getCounterValue(self._storeCounterId)
        cntrm = self.getCounterValue(self._removeCounterId)
        minst = self._convert(self.getMinCounterInc(self._storeTimeId, [intervalSec])[0])
        maxst = self._convert(self.getMaxCounterInc(self._storeTimeId, [intervalSec])[0])
        avgst = self._convert(self.getAvgCounterInc(self._storeTimeId, [intervalSec])[0])
        minrm = self._convert(self.getMinCounterInc(self._removeTimeId, [intervalSec])[0])
        maxrm = self._convert(self.getMaxCounterInc(self._removeTimeId, [intervalSec])[0])
        avgrm = self._convert(self.getAvgCounterInc(self._removeTimeId, [intervalSec])[0])
        
        return [dbentries, dbsize, stpersec, rmpersec, cntst, cntrm, minst, maxst, avgst, minrm, maxrm, avgrm]
    
    
    def _convert(self, value):
        if value == None:
            return None
        else:
            return int(value*1000)
            
                
    def resend(self, sleep=False):
        '''
        Resend all messages which are in the backlog database to GSN.
        '''
        self._isBusy = True
        self._sleep = sleep
        self._resend.set()


    def run(self):
        self._logger.info('started')
        self._resendtimer.start()
        while not self._stopped:
            self._resend.wait()
            if self._stopped:
                break
            if self._sleep:
                self._sleepEvent.wait(SLEEP_BEFORE_RESEND_ON_RECONNECT)
            if self._stopped:
                break

            timestamp = 0

            self._logger.info('resend')

            while not self._stopped:
                try:
                    self._dblock.acquire()
                    self._cur.execute('SELECT * FROM backlogmsg WHERE timestamp > ? order by timestamp asc LIMIT 1', (timestamp,))
                    row = self._cur.fetchone()
                    self._dblock.release()
                except sqlite3.Error, e:
                    self._dblock.release()
                    self.exception(e)
                    break
                    
                if row is None:
                    self._logger.info('all packets are sent')
                    self._isBusy = False
                    break

                timestamp = row[0]
                msgType = row[1]
                message = row[2]
                # should be blocking until queue is free and ready to send
                self._logger.debug('rsnd...')
                if self._backlogMain.gsnpeer.processResendMsg(msgType, timestamp, message):
                    self._logger.debug('rsnd (%d,%d,%d)' % (msgType, timestamp, len(message)))
                else:
                    self._logger.info('resend interrupted')
                    self._isBusy = False
                    break

            self._resend.clear()

        self._logger.info('died')
        
        
    def connectionToGSNlost(self):
        self._resendtimer.pause()
        
        
    def connectionToGSNestablished(self):
        self._resendtimer.resume()


    def __del__(self):
        self._dblock.acquire()
        if '_cur' in locals():
            self._cur.close()
        if '_con' in locals():
            self._con.close()
        self._dblock.release()
        
        
    def isBusy(self):
        return self._isBusy
        

    def stop(self):
        self._isBusy = False
        self._stopped = True
        self._resend.set()
        self._sleepEvent.set()
        self._resendtimer.stop()
        self._logger.info('stopped')
        
        
    def exception(self, e):
        self._backlogMain.incrementExceptionCounter()
        self._logger.exception(str(e))



class ResendTimer(Thread):
    
    '''
    data/instance attributes:
    _logger
    _interval
    _action
    _wait
    _timer
    _stopped
    '''
    
    def __init__(self, interval, action):
        Thread.__init__(self)
        self._logger = logging.getLogger(self.__class__.__name__)
        self._interval = interval
        self._action = action
        self._wait = None
        self._timer = Event()
        self._stopped = False
        
           
    def run(self):
        self._logger.info('started')
        # wait for first resume
        self._timer.wait()
        self._timer.clear()
        while not self._stopped:
            self._timer.wait(self._wait)
            if self._timer.isSet():
                self._timer.clear()
                continue
            self._action()
            
        self._logger.info('died')
    
    
    def pause(self):
        self._wait = None
        self._timer.set()
        self._logger.info('paused')
    
            
    def resume(self):
        self._wait = self._interval
        self._timer.set()
        self._logger.info('resumed')
    
    
    def stop(self):
        self._stopped = True
        self._timer.set()
        self._logger.info('stopped')
        