package com.sinacloud.java.kvdb.util;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * accesskey and secretkey,id is connected kvserver id.
 */
public class KeyPair implements org.apache.thrift.TBase<KeyPair, KeyPair._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("KeyPair");

  private static final org.apache.thrift.protocol.TField ACCESSKEY_FIELD_DESC = new org.apache.thrift.protocol.TField("accesskey", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField TIMESTAMP_FIELD_DESC = new org.apache.thrift.protocol.TField("timestamp", org.apache.thrift.protocol.TType.I64, (short)2);
  private static final org.apache.thrift.protocol.TField TOKEN_FIELD_DESC = new org.apache.thrift.protocol.TField("token", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField SIGNATURE_FIELD_DESC = new org.apache.thrift.protocol.TField("signature", org.apache.thrift.protocol.TType.STRING, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new KeyPairStandardSchemeFactory());
    schemes.put(TupleScheme.class, new KeyPairTupleSchemeFactory());
  }

  public String accesskey; // optional
  public long timestamp; // optional
  public String token; // optional
  public String signature; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ACCESSKEY((short)1, "accesskey"),
    TIMESTAMP((short)2, "timestamp"),
    TOKEN((short)3, "token"),
    SIGNATURE((short)4, "signature");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // ACCESSKEY
          return ACCESSKEY;
        case 2: // TIMESTAMP
          return TIMESTAMP;
        case 3: // TOKEN
          return TOKEN;
        case 4: // SIGNATURE
          return SIGNATURE;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __TIMESTAMP_ISSET_ID = 0;
  private BitSet __isset_bit_vector = new BitSet(1);
  private _Fields optionals[] = {_Fields.ACCESSKEY,_Fields.TIMESTAMP,_Fields.TOKEN,_Fields.SIGNATURE};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ACCESSKEY, new org.apache.thrift.meta_data.FieldMetaData("accesskey", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TIMESTAMP, new org.apache.thrift.meta_data.FieldMetaData("timestamp", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.TOKEN, new org.apache.thrift.meta_data.FieldMetaData("token", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SIGNATURE, new org.apache.thrift.meta_data.FieldMetaData("signature", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(KeyPair.class, metaDataMap);
  }

  public KeyPair() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public KeyPair(KeyPair other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    if (other.isSetAccesskey()) {
      this.accesskey = other.accesskey;
    }
    this.timestamp = other.timestamp;
    if (other.isSetToken()) {
      this.token = other.token;
    }
    if (other.isSetSignature()) {
      this.signature = other.signature;
    }
  }

  public KeyPair deepCopy() {
    return new KeyPair(this);
  }

  @Override
  public void clear() {
    this.accesskey = null;
    setTimestampIsSet(false);
    this.timestamp = 0;
    this.token = null;
    this.signature = null;
  }

  public String getAccesskey() {
    return this.accesskey;
  }

  public KeyPair setAccesskey(String accesskey) {
    this.accesskey = accesskey;
    return this;
  }

  public void unsetAccesskey() {
    this.accesskey = null;
  }

  /** Returns true if field accesskey is set (has been assigned a value) and false otherwise */
  public boolean isSetAccesskey() {
    return this.accesskey != null;
  }

  public void setAccesskeyIsSet(boolean value) {
    if (!value) {
      this.accesskey = null;
    }
  }

  public long getTimestamp() {
    return this.timestamp;
  }

  public KeyPair setTimestamp(long timestamp) {
    this.timestamp = timestamp;
    setTimestampIsSet(true);
    return this;
  }

  public void unsetTimestamp() {
    __isset_bit_vector.clear(__TIMESTAMP_ISSET_ID);
  }

  /** Returns true if field timestamp is set (has been assigned a value) and false otherwise */
  public boolean isSetTimestamp() {
    return __isset_bit_vector.get(__TIMESTAMP_ISSET_ID);
  }

  public void setTimestampIsSet(boolean value) {
    __isset_bit_vector.set(__TIMESTAMP_ISSET_ID, value);
  }

  public String getToken() {
    return this.token;
  }

  public KeyPair setToken(String token) {
    this.token = token;
    return this;
  }

  public void unsetToken() {
    this.token = null;
  }

  /** Returns true if field token is set (has been assigned a value) and false otherwise */
  public boolean isSetToken() {
    return this.token != null;
  }

  public void setTokenIsSet(boolean value) {
    if (!value) {
      this.token = null;
    }
  }

  public String getSignature() {
    return this.signature;
  }

  public KeyPair setSignature(String signature) {
    this.signature = signature;
    return this;
  }

  public void unsetSignature() {
    this.signature = null;
  }

  /** Returns true if field signature is set (has been assigned a value) and false otherwise */
  public boolean isSetSignature() {
    return this.signature != null;
  }

  public void setSignatureIsSet(boolean value) {
    if (!value) {
      this.signature = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ACCESSKEY:
      if (value == null) {
        unsetAccesskey();
      } else {
        setAccesskey((String)value);
      }
      break;

    case TIMESTAMP:
      if (value == null) {
        unsetTimestamp();
      } else {
        setTimestamp((Long)value);
      }
      break;

    case TOKEN:
      if (value == null) {
        unsetToken();
      } else {
        setToken((String)value);
      }
      break;

    case SIGNATURE:
      if (value == null) {
        unsetSignature();
      } else {
        setSignature((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ACCESSKEY:
      return getAccesskey();

    case TIMESTAMP:
      return Long.valueOf(getTimestamp());

    case TOKEN:
      return getToken();

    case SIGNATURE:
      return getSignature();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ACCESSKEY:
      return isSetAccesskey();
    case TIMESTAMP:
      return isSetTimestamp();
    case TOKEN:
      return isSetToken();
    case SIGNATURE:
      return isSetSignature();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof KeyPair)
      return this.equals((KeyPair)that);
    return false;
  }

  public boolean equals(KeyPair that) {
    if (that == null)
      return false;

    boolean this_present_accesskey = true && this.isSetAccesskey();
    boolean that_present_accesskey = true && that.isSetAccesskey();
    if (this_present_accesskey || that_present_accesskey) {
      if (!(this_present_accesskey && that_present_accesskey))
        return false;
      if (!this.accesskey.equals(that.accesskey))
        return false;
    }

    boolean this_present_timestamp = true && this.isSetTimestamp();
    boolean that_present_timestamp = true && that.isSetTimestamp();
    if (this_present_timestamp || that_present_timestamp) {
      if (!(this_present_timestamp && that_present_timestamp))
        return false;
      if (this.timestamp != that.timestamp)
        return false;
    }

    boolean this_present_token = true && this.isSetToken();
    boolean that_present_token = true && that.isSetToken();
    if (this_present_token || that_present_token) {
      if (!(this_present_token && that_present_token))
        return false;
      if (!this.token.equals(that.token))
        return false;
    }

    boolean this_present_signature = true && this.isSetSignature();
    boolean that_present_signature = true && that.isSetSignature();
    if (this_present_signature || that_present_signature) {
      if (!(this_present_signature && that_present_signature))
        return false;
      if (!this.signature.equals(that.signature))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(KeyPair other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    KeyPair typedOther = (KeyPair)other;

    lastComparison = Boolean.valueOf(isSetAccesskey()).compareTo(typedOther.isSetAccesskey());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAccesskey()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.accesskey, typedOther.accesskey);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTimestamp()).compareTo(typedOther.isSetTimestamp());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTimestamp()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.timestamp, typedOther.timestamp);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetToken()).compareTo(typedOther.isSetToken());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetToken()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.token, typedOther.token);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSignature()).compareTo(typedOther.isSetSignature());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSignature()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.signature, typedOther.signature);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("KeyPair(");
    boolean first = true;

    if (isSetAccesskey()) {
      sb.append("accesskey:");
      if (this.accesskey == null) {
        sb.append("null");
      } else {
        sb.append(this.accesskey);
      }
      first = false;
    }
    if (isSetTimestamp()) {
      if (!first) sb.append(", ");
      sb.append("timestamp:");
      sb.append(this.timestamp);
      first = false;
    }
    if (isSetToken()) {
      if (!first) sb.append(", ");
      sb.append("token:");
      if (this.token == null) {
        sb.append("null");
      } else {
        sb.append(this.token);
      }
      first = false;
    }
    if (isSetSignature()) {
      if (!first) sb.append(", ");
      sb.append("signature:");
      if (this.signature == null) {
        sb.append("null");
      } else {
        sb.append(this.signature);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bit_vector = new BitSet(1);
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class KeyPairStandardSchemeFactory implements SchemeFactory {
    public KeyPairStandardScheme getScheme() {
      return new KeyPairStandardScheme();
    }
  }

  private static class KeyPairStandardScheme extends StandardScheme<KeyPair> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, KeyPair struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ACCESSKEY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.accesskey = iprot.readString();
              struct.setAccesskeyIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // TIMESTAMP
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.timestamp = iprot.readI64();
              struct.setTimestampIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // TOKEN
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.token = iprot.readString();
              struct.setTokenIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // SIGNATURE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.signature = iprot.readString();
              struct.setSignatureIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, KeyPair struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.accesskey != null) {
        if (struct.isSetAccesskey()) {
          oprot.writeFieldBegin(ACCESSKEY_FIELD_DESC);
          oprot.writeString(struct.accesskey);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetTimestamp()) {
        oprot.writeFieldBegin(TIMESTAMP_FIELD_DESC);
        oprot.writeI64(struct.timestamp);
        oprot.writeFieldEnd();
      }
      if (struct.token != null) {
        if (struct.isSetToken()) {
          oprot.writeFieldBegin(TOKEN_FIELD_DESC);
          oprot.writeString(struct.token);
          oprot.writeFieldEnd();
        }
      }
      if (struct.signature != null) {
        if (struct.isSetSignature()) {
          oprot.writeFieldBegin(SIGNATURE_FIELD_DESC);
          oprot.writeString(struct.signature);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class KeyPairTupleSchemeFactory implements SchemeFactory {
    public KeyPairTupleScheme getScheme() {
      return new KeyPairTupleScheme();
    }
  }

  private static class KeyPairTupleScheme extends TupleScheme<KeyPair> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, KeyPair struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetAccesskey()) {
        optionals.set(0);
      }
      if (struct.isSetTimestamp()) {
        optionals.set(1);
      }
      if (struct.isSetToken()) {
        optionals.set(2);
      }
      if (struct.isSetSignature()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetAccesskey()) {
        oprot.writeString(struct.accesskey);
      }
      if (struct.isSetTimestamp()) {
        oprot.writeI64(struct.timestamp);
      }
      if (struct.isSetToken()) {
        oprot.writeString(struct.token);
      }
      if (struct.isSetSignature()) {
        oprot.writeString(struct.signature);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, KeyPair struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.accesskey = iprot.readString();
        struct.setAccesskeyIsSet(true);
      }
      if (incoming.get(1)) {
        struct.timestamp = iprot.readI64();
        struct.setTimestampIsSet(true);
      }
      if (incoming.get(2)) {
        struct.token = iprot.readString();
        struct.setTokenIsSet(true);
      }
      if (incoming.get(3)) {
        struct.signature = iprot.readString();
        struct.setSignatureIsSet(true);
      }
    }
  }

}

