/**
* Global Sensor Networks (GSN) Source Code
* Copyright (c) 2006-2014, Ecole Polytechnique Federale de Lausanne (EPFL)
*
* This file is part of GSN.
*
* GSN is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* GSN is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with GSN. If not, see <http://www.gnu.org/licenses/>.
*
* File: gsn-tiny/src/tinygsn/model/wrappers/AbstractWrapper.java
*
* @author Do Ngoc Hoan
*/
package tinygsn.model.wrappers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import tinygsn.beans.DataField;
import tinygsn.beans.StaticData;
import tinygsn.beans.StreamSource;
import tinygsn.beans.StreamElement;
import tinygsn.beans.VSensorConfig;
import tinygsn.beans.WrapperConfig;
import tinygsn.model.wrappers.AndroidLightWrapper.LightService;
import tinygsn.services.WrapperService;
import android.content.Context;
import android.content.Intent;

public abstract class AbstractWrapper {

	private WrapperConfig config = new WrapperConfig(0,getWrapperName());
	
	public WrapperConfig getConfig() {
		return config;
	}

	public void setConfig(WrapperConfig config) {
		this.config = config;
	}

	public Class<WrapperService> SERVICE = null;

	protected static final int DEFAULT_SAMPLING_RATE = 5;
	protected int samplingRate = DEFAULT_SAMPLING_RATE;

	protected final List<StreamSource> listeners = Collections
			.synchronizedList(new ArrayList<StreamSource>());
	
	private int listenerCount = 0;

	private boolean isActive = true;

	public AbstractWrapper() {
	}

	public int getSamplingRate() {
		return samplingRate;
	}
	
	public void registerListener(StreamSource s){
		listeners.add(s);
	}
	
	public void unregisterListener(StreamSource s){
		listeners.remove(s);
	}

	public void setSamplingRate(int samplingRate) {
		this.samplingRate = samplingRate;
	}

	/**
	 * The output structure should be specified in the XML config file. However,
	 * for the simplicity of this tinygsn version, we return it from wrapper.
	 * 
	 * @return
	 */
	public abstract DataField[] getOutputStructure();


	public boolean isActive() {
		return isActive;
	}

	/**
	 * This method gets the generated stream element and notifies the input
	 * streams if needed. The return value specifies if the newly provided stream
	 * element generated at least one input stream notification or not.
	 * 
	 * @param streamElement
	 * @return If the method returns false, it means the insertion doesn't
	 *         effected any input stream.
	 */

	public Boolean postStreamElement(StreamElement streamElement) {
		for(StreamSource s:listeners) s.add(streamElement);
		return true;
	}

	public void releaseResources() {
		isActive = false;
	}

	public abstract String[] getFieldList();

	public abstract Byte[] getFieldType();
	
	public abstract void runOnce();

	public static Properties getWrapperList(Context context) {
		Properties wrapperList = new Properties();
		try {
			InputStream is = context.getAssets().open("wrapper_list.properties");
			wrapperList.load(is);
			//TODO Check if the sensor is available on this phone
		}
		catch (IOException e) {
		}

		return wrapperList;
	}

	public abstract String getWrapperName();
	
	synchronized public void start(Context context){
		if (listenerCount < 1){
			try {
				Intent  serviceIntent = new Intent(context, SERVICE);
				config.setRunning(true);
				serviceIntent.putExtra("tinygsn.beans.config",config );
				StaticData.addRunningService(getWrapperName(), serviceIntent);
				context.startService(serviceIntent);
			} catch (Exception e) {
				// release anything?
			}
		}
		listenerCount++;
	}
	
	synchronized public void stop(Context context){
		if (listenerCount == 1){
			try {
				Intent serviceIntent = StaticData.getRunningIntentByName(getWrapperName());
				if(serviceIntent != null)
				{
					serviceIntent.removeExtra("tinygsn.beans.config");
					config.setRunning(false);
					serviceIntent.putExtra("tinygsn.beans.config", config);
					context.startService(serviceIntent);
				}
			} catch (Exception e) {
				// release anything?
			}
			
		}
		listenerCount--;
	}
	
}
