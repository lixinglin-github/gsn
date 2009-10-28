/**
 * This class is automatically generated by mig. DO NOT EDIT THIS FILE.
 * This class implements a Java interface to the 'DozerObjectStatusMsg'
 * message type.
 */

package ch.ethz.permafrozer.messages;

public class DozerObjectStatusMsg extends ch.ethz.permafrozer.messages.DataHeaderMsg {

    /** The default size of this message type in bytes. */
    public static final int DEFAULT_MESSAGE_SIZE = 17;

    /** The Active Message type associated with this message. */
    public static final int AM_TYPE = 144;

    /** Create a new DozerObjectStatusMsg of size 17. */
    public DozerObjectStatusMsg() {
        super(DEFAULT_MESSAGE_SIZE);
        amTypeSet(AM_TYPE);
    }

    /** Create a new DozerObjectStatusMsg of the given data_length. */
    public DozerObjectStatusMsg(int data_length) {
        super(data_length);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new DozerObjectStatusMsg with the given data_length
     * and base offset.
     */
    public DozerObjectStatusMsg(int data_length, int base_offset) {
        super(data_length, base_offset);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new DozerObjectStatusMsg using the given byte array
     * as backing store.
     */
    public DozerObjectStatusMsg(byte[] data) {
        super(data);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new DozerObjectStatusMsg using the given byte array
     * as backing store, with the given base offset.
     */
    public DozerObjectStatusMsg(byte[] data, int base_offset) {
        super(data, base_offset);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new DozerObjectStatusMsg using the given byte array
     * as backing store, with the given base offset and data length.
     */
    public DozerObjectStatusMsg(byte[] data, int base_offset, int data_length) {
        super(data, base_offset, data_length);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new DozerObjectStatusMsg embedded in the given message
     * at the given base offset.
     */
    public DozerObjectStatusMsg(net.tinyos.message.Message msg, int base_offset) {
        super(msg, base_offset, DEFAULT_MESSAGE_SIZE);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new DozerObjectStatusMsg embedded in the given message
     * at the given base offset and length.
     */
    public DozerObjectStatusMsg(net.tinyos.message.Message msg, int base_offset, int data_length) {
        super(msg, base_offset, data_length);
        amTypeSet(AM_TYPE);
    }

    /**
    /* Return a String representation of this message. Includes the
     * message type name and the non-indexed field values.
     */
    public String toString() {
      String s = "Message <DozerObjectStatusMsg> \n";
      try {
        s += "  [header.seqNr=0x"+Long.toHexString(get_header_seqNr())+"]\n";
      } catch (ArrayIndexOutOfBoundsException aioobe) { /* Skip field */ }
      try {
        s += "  [header.originatorID=0x"+Long.toHexString(get_header_originatorID())+"]\n";
      } catch (ArrayIndexOutOfBoundsException aioobe) { /* Skip field */ }
      try {
        s += "  [header.aTime.low=0x"+Long.toHexString(get_header_aTime_low())+"]\n";
      } catch (ArrayIndexOutOfBoundsException aioobe) { /* Skip field */ }
      try {
        s += "  [header.aTime.high=0x"+Long.toHexString(get_header_aTime_high())+"]\n";
      } catch (ArrayIndexOutOfBoundsException aioobe) { /* Skip field */ }
      try {
        s += "  [payload.object.objid=0x"+Long.toHexString(get_payload_object_objid())+"]\n";
      } catch (ArrayIndexOutOfBoundsException aioobe) { /* Skip field */ }
      try {
        s += "  [payload.object.uidhash=0x"+Long.toHexString(get_payload_object_uidhash())+"]\n";
      } catch (ArrayIndexOutOfBoundsException aioobe) { /* Skip field */ }
      try {
        s += "  [payload.object.numPgs=0x"+Long.toHexString(get_payload_object_numPgs())+"]\n";
      } catch (ArrayIndexOutOfBoundsException aioobe) { /* Skip field */ }
      try {
        s += "  [payload.object.crc=0x"+Long.toHexString(get_payload_object_crc())+"]\n";
      } catch (ArrayIndexOutOfBoundsException aioobe) { /* Skip field */ }
      try {
        s += "  [payload.object.numPgsComplete=0x"+Long.toHexString(get_payload_object_numPgsComplete())+"]\n";
      } catch (ArrayIndexOutOfBoundsException aioobe) { /* Skip field */ }
      return s;
    }

    // Message-type-specific access methods appear below.

    /////////////////////////////////////////////////////////
    // Accessor methods for field: header.seqNr
    //   Field type: int
    //   Offset (bits): 0
    //   Size (bits): 16
    /////////////////////////////////////////////////////////

    /**
     * Return whether the field 'header.seqNr' is signed (false).
     */
    public static boolean isSigned_header_seqNr() {
        return false;
    }

    /**
     * Return whether the field 'header.seqNr' is an array (false).
     */
    public static boolean isArray_header_seqNr() {
        return false;
    }

    /**
     * Return the offset (in bytes) of the field 'header.seqNr'
     */
    public static int offset_header_seqNr() {
        return (0 / 8);
    }

    /**
     * Return the offset (in bits) of the field 'header.seqNr'
     */
    public static int offsetBits_header_seqNr() {
        return 0;
    }

    /**
     * Return the value (as a int) of the field 'header.seqNr'
     */
    public int get_header_seqNr() {
        return (int)getUIntBEElement(offsetBits_header_seqNr(), 16);
    }

    /**
     * Set the value of the field 'header.seqNr'
     */
    public void set_header_seqNr(int value) {
        setUIntBEElement(offsetBits_header_seqNr(), 16, value);
    }

    /**
     * Return the size, in bytes, of the field 'header.seqNr'
     */
    public static int size_header_seqNr() {
        return (16 / 8);
    }

    /**
     * Return the size, in bits, of the field 'header.seqNr'
     */
    public static int sizeBits_header_seqNr() {
        return 16;
    }

    /////////////////////////////////////////////////////////
    // Accessor methods for field: header.originatorID
    //   Field type: int
    //   Offset (bits): 16
    //   Size (bits): 16
    /////////////////////////////////////////////////////////

    /**
     * Return whether the field 'header.originatorID' is signed (false).
     */
    public static boolean isSigned_header_originatorID() {
        return false;
    }

    /**
     * Return whether the field 'header.originatorID' is an array (false).
     */
    public static boolean isArray_header_originatorID() {
        return false;
    }

    /**
     * Return the offset (in bytes) of the field 'header.originatorID'
     */
    public static int offset_header_originatorID() {
        return (16 / 8);
    }

    /**
     * Return the offset (in bits) of the field 'header.originatorID'
     */
    public static int offsetBits_header_originatorID() {
        return 16;
    }

    /**
     * Return the value (as a int) of the field 'header.originatorID'
     */
    public int get_header_originatorID() {
        return (int)getUIntBEElement(offsetBits_header_originatorID(), 16);
    }

    /**
     * Set the value of the field 'header.originatorID'
     */
    public void set_header_originatorID(int value) {
        setUIntBEElement(offsetBits_header_originatorID(), 16, value);
    }

    /**
     * Return the size, in bytes, of the field 'header.originatorID'
     */
    public static int size_header_originatorID() {
        return (16 / 8);
    }

    /**
     * Return the size, in bits, of the field 'header.originatorID'
     */
    public static int sizeBits_header_originatorID() {
        return 16;
    }

    /////////////////////////////////////////////////////////
    // Accessor methods for field: header.aTime.low
    //   Field type: int
    //   Offset (bits): 32
    //   Size (bits): 16
    /////////////////////////////////////////////////////////

    /**
     * Return whether the field 'header.aTime.low' is signed (false).
     */
    public static boolean isSigned_header_aTime_low() {
        return false;
    }

    /**
     * Return whether the field 'header.aTime.low' is an array (false).
     */
    public static boolean isArray_header_aTime_low() {
        return false;
    }

    /**
     * Return the offset (in bytes) of the field 'header.aTime.low'
     */
    public static int offset_header_aTime_low() {
        return (32 / 8);
    }

    /**
     * Return the offset (in bits) of the field 'header.aTime.low'
     */
    public static int offsetBits_header_aTime_low() {
        return 32;
    }

    /**
     * Return the value (as a int) of the field 'header.aTime.low'
     */
    public int get_header_aTime_low() {
        return (int)getUIntBEElement(offsetBits_header_aTime_low(), 16);
    }

    /**
     * Set the value of the field 'header.aTime.low'
     */
    public void set_header_aTime_low(int value) {
        setUIntBEElement(offsetBits_header_aTime_low(), 16, value);
    }

    /**
     * Return the size, in bytes, of the field 'header.aTime.low'
     */
    public static int size_header_aTime_low() {
        return (16 / 8);
    }

    /**
     * Return the size, in bits, of the field 'header.aTime.low'
     */
    public static int sizeBits_header_aTime_low() {
        return 16;
    }

    /////////////////////////////////////////////////////////
    // Accessor methods for field: header.aTime.high
    //   Field type: short
    //   Offset (bits): 48
    //   Size (bits): 8
    /////////////////////////////////////////////////////////

    /**
     * Return whether the field 'header.aTime.high' is signed (false).
     */
    public static boolean isSigned_header_aTime_high() {
        return false;
    }

    /**
     * Return whether the field 'header.aTime.high' is an array (false).
     */
    public static boolean isArray_header_aTime_high() {
        return false;
    }

    /**
     * Return the offset (in bytes) of the field 'header.aTime.high'
     */
    public static int offset_header_aTime_high() {
        return (48 / 8);
    }

    /**
     * Return the offset (in bits) of the field 'header.aTime.high'
     */
    public static int offsetBits_header_aTime_high() {
        return 48;
    }

    /**
     * Return the value (as a short) of the field 'header.aTime.high'
     */
    public short get_header_aTime_high() {
        return (short)getUIntBEElement(offsetBits_header_aTime_high(), 8);
    }

    /**
     * Set the value of the field 'header.aTime.high'
     */
    public void set_header_aTime_high(short value) {
        setUIntBEElement(offsetBits_header_aTime_high(), 8, value);
    }

    /**
     * Return the size, in bytes, of the field 'header.aTime.high'
     */
    public static int size_header_aTime_high() {
        return (8 / 8);
    }

    /**
     * Return the size, in bits, of the field 'header.aTime.high'
     */
    public static int sizeBits_header_aTime_high() {
        return 8;
    }

    /////////////////////////////////////////////////////////
    // Accessor methods for field: payload.object.objid
    //   Field type: int
    //   Offset (bits): 56
    //   Size (bits): 16
    /////////////////////////////////////////////////////////

    /**
     * Return whether the field 'payload.object.objid' is signed (false).
     */
    public static boolean isSigned_payload_object_objid() {
        return false;
    }

    /**
     * Return whether the field 'payload.object.objid' is an array (false).
     */
    public static boolean isArray_payload_object_objid() {
        return false;
    }

    /**
     * Return the offset (in bytes) of the field 'payload.object.objid'
     */
    public static int offset_payload_object_objid() {
        return (56 / 8);
    }

    /**
     * Return the offset (in bits) of the field 'payload.object.objid'
     */
    public static int offsetBits_payload_object_objid() {
        return 56;
    }

    /**
     * Return the value (as a int) of the field 'payload.object.objid'
     */
    public int get_payload_object_objid() {
        return (int)getUIntBEElement(offsetBits_payload_object_objid(), 16);
    }

    /**
     * Set the value of the field 'payload.object.objid'
     */
    public void set_payload_object_objid(int value) {
        setUIntBEElement(offsetBits_payload_object_objid(), 16, value);
    }

    /**
     * Return the size, in bytes, of the field 'payload.object.objid'
     */
    public static int size_payload_object_objid() {
        return (16 / 8);
    }

    /**
     * Return the size, in bits, of the field 'payload.object.objid'
     */
    public static int sizeBits_payload_object_objid() {
        return 16;
    }

    /////////////////////////////////////////////////////////
    // Accessor methods for field: payload.object.uidhash
    //   Field type: long
    //   Offset (bits): 72
    //   Size (bits): 32
    /////////////////////////////////////////////////////////

    /**
     * Return whether the field 'payload.object.uidhash' is signed (false).
     */
    public static boolean isSigned_payload_object_uidhash() {
        return false;
    }

    /**
     * Return whether the field 'payload.object.uidhash' is an array (false).
     */
    public static boolean isArray_payload_object_uidhash() {
        return false;
    }

    /**
     * Return the offset (in bytes) of the field 'payload.object.uidhash'
     */
    public static int offset_payload_object_uidhash() {
        return (72 / 8);
    }

    /**
     * Return the offset (in bits) of the field 'payload.object.uidhash'
     */
    public static int offsetBits_payload_object_uidhash() {
        return 72;
    }

    /**
     * Return the value (as a long) of the field 'payload.object.uidhash'
     */
    public long get_payload_object_uidhash() {
        return (long)getUIntBEElement(offsetBits_payload_object_uidhash(), 32);
    }

    /**
     * Set the value of the field 'payload.object.uidhash'
     */
    public void set_payload_object_uidhash(long value) {
        setUIntBEElement(offsetBits_payload_object_uidhash(), 32, value);
    }

    /**
     * Return the size, in bytes, of the field 'payload.object.uidhash'
     */
    public static int size_payload_object_uidhash() {
        return (32 / 8);
    }

    /**
     * Return the size, in bits, of the field 'payload.object.uidhash'
     */
    public static int sizeBits_payload_object_uidhash() {
        return 32;
    }

    /////////////////////////////////////////////////////////
    // Accessor methods for field: payload.object.numPgs
    //   Field type: short
    //   Offset (bits): 104
    //   Size (bits): 8
    /////////////////////////////////////////////////////////

    /**
     * Return whether the field 'payload.object.numPgs' is signed (false).
     */
    public static boolean isSigned_payload_object_numPgs() {
        return false;
    }

    /**
     * Return whether the field 'payload.object.numPgs' is an array (false).
     */
    public static boolean isArray_payload_object_numPgs() {
        return false;
    }

    /**
     * Return the offset (in bytes) of the field 'payload.object.numPgs'
     */
    public static int offset_payload_object_numPgs() {
        return (104 / 8);
    }

    /**
     * Return the offset (in bits) of the field 'payload.object.numPgs'
     */
    public static int offsetBits_payload_object_numPgs() {
        return 104;
    }

    /**
     * Return the value (as a short) of the field 'payload.object.numPgs'
     */
    public short get_payload_object_numPgs() {
        return (short)getUIntBEElement(offsetBits_payload_object_numPgs(), 8);
    }

    /**
     * Set the value of the field 'payload.object.numPgs'
     */
    public void set_payload_object_numPgs(short value) {
        setUIntBEElement(offsetBits_payload_object_numPgs(), 8, value);
    }

    /**
     * Return the size, in bytes, of the field 'payload.object.numPgs'
     */
    public static int size_payload_object_numPgs() {
        return (8 / 8);
    }

    /**
     * Return the size, in bits, of the field 'payload.object.numPgs'
     */
    public static int sizeBits_payload_object_numPgs() {
        return 8;
    }

    /////////////////////////////////////////////////////////
    // Accessor methods for field: payload.object.crc
    //   Field type: int
    //   Offset (bits): 112
    //   Size (bits): 16
    /////////////////////////////////////////////////////////

    /**
     * Return whether the field 'payload.object.crc' is signed (false).
     */
    public static boolean isSigned_payload_object_crc() {
        return false;
    }

    /**
     * Return whether the field 'payload.object.crc' is an array (false).
     */
    public static boolean isArray_payload_object_crc() {
        return false;
    }

    /**
     * Return the offset (in bytes) of the field 'payload.object.crc'
     */
    public static int offset_payload_object_crc() {
        return (112 / 8);
    }

    /**
     * Return the offset (in bits) of the field 'payload.object.crc'
     */
    public static int offsetBits_payload_object_crc() {
        return 112;
    }

    /**
     * Return the value (as a int) of the field 'payload.object.crc'
     */
    public int get_payload_object_crc() {
        return (int)getUIntBEElement(offsetBits_payload_object_crc(), 16);
    }

    /**
     * Set the value of the field 'payload.object.crc'
     */
    public void set_payload_object_crc(int value) {
        setUIntBEElement(offsetBits_payload_object_crc(), 16, value);
    }

    /**
     * Return the size, in bytes, of the field 'payload.object.crc'
     */
    public static int size_payload_object_crc() {
        return (16 / 8);
    }

    /**
     * Return the size, in bits, of the field 'payload.object.crc'
     */
    public static int sizeBits_payload_object_crc() {
        return 16;
    }

    /////////////////////////////////////////////////////////
    // Accessor methods for field: payload.object.numPgsComplete
    //   Field type: short
    //   Offset (bits): 128
    //   Size (bits): 8
    /////////////////////////////////////////////////////////

    /**
     * Return whether the field 'payload.object.numPgsComplete' is signed (false).
     */
    public static boolean isSigned_payload_object_numPgsComplete() {
        return false;
    }

    /**
     * Return whether the field 'payload.object.numPgsComplete' is an array (false).
     */
    public static boolean isArray_payload_object_numPgsComplete() {
        return false;
    }

    /**
     * Return the offset (in bytes) of the field 'payload.object.numPgsComplete'
     */
    public static int offset_payload_object_numPgsComplete() {
        return (128 / 8);
    }

    /**
     * Return the offset (in bits) of the field 'payload.object.numPgsComplete'
     */
    public static int offsetBits_payload_object_numPgsComplete() {
        return 128;
    }

    /**
     * Return the value (as a short) of the field 'payload.object.numPgsComplete'
     */
    public short get_payload_object_numPgsComplete() {
        return (short)getUIntBEElement(offsetBits_payload_object_numPgsComplete(), 8);
    }

    /**
     * Set the value of the field 'payload.object.numPgsComplete'
     */
    public void set_payload_object_numPgsComplete(short value) {
        setUIntBEElement(offsetBits_payload_object_numPgsComplete(), 8, value);
    }

    /**
     * Return the size, in bytes, of the field 'payload.object.numPgsComplete'
     */
    public static int size_payload_object_numPgsComplete() {
        return (8 / 8);
    }

    /**
     * Return the size, in bits, of the field 'payload.object.numPgsComplete'
     */
    public static int sizeBits_payload_object_numPgsComplete() {
        return 8;
    }

}
