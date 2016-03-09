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
 * Represents a single item in kvdb.
 */
public class TItem implements org.apache.thrift.TBase<TItem, TItem._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TItem");

  private static final org.apache.thrift.protocol.TField FLAG_FIELD_DESC = new org.apache.thrift.protocol.TField("flag", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField VALUE_FIELD_DESC = new org.apache.thrift.protocol.TField("value", org.apache.thrift.protocol.TType.STRING, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TItemStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TItemTupleSchemeFactory());
  }

  public ByteBuffer flag; // required
  public ByteBuffer value; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    FLAG((short)1, "flag"),
    VALUE((short)2, "value");

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
        case 1: // FLAG
          return FLAG;
        case 2: // VALUE
          return VALUE;
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
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.FLAG, new org.apache.thrift.meta_data.FieldMetaData("flag", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , true)));
    tmpMap.put(_Fields.VALUE, new org.apache.thrift.meta_data.FieldMetaData("value", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , true)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TItem.class, metaDataMap);
  }

  public TItem() {
  }

  public TItem(
    ByteBuffer flag,
    ByteBuffer value)
  {
    this();
    this.flag = flag;
    this.value = value;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TItem(TItem other) {
    if (other.isSetFlag()) {
      this.flag = org.apache.thrift.TBaseHelper.copyBinary(other.flag);
;
    }
    if (other.isSetValue()) {
      this.value = org.apache.thrift.TBaseHelper.copyBinary(other.value);
;
    }
  }

  public TItem deepCopy() {
    return new TItem(this);
  }

  @Override
  public void clear() {
    this.flag = null;
    this.value = null;
  }

  public byte[] getFlag() {
    setFlag(org.apache.thrift.TBaseHelper.rightSize(flag));
    return flag == null ? null : flag.array();
  }

  public ByteBuffer bufferForFlag() {
    return flag;
  }

  public TItem setFlag(byte[] flag) {
    setFlag(flag == null ? (ByteBuffer)null : ByteBuffer.wrap(flag));
    return this;
  }

  public TItem setFlag(ByteBuffer flag) {
    this.flag = flag;
    return this;
  }

  public void unsetFlag() {
    this.flag = null;
  }

  /** Returns true if field flag is set (has been assigned a value) and false otherwise */
  public boolean isSetFlag() {
    return this.flag != null;
  }

  public void setFlagIsSet(boolean value) {
    if (!value) {
      this.flag = null;
    }
  }

  public byte[] getValue() {
    setValue(org.apache.thrift.TBaseHelper.rightSize(value));
    return value == null ? null : value.array();
  }

  public ByteBuffer bufferForValue() {
    return value;
  }

  public TItem setValue(byte[] value) {
    setValue(value == null ? (ByteBuffer)null : ByteBuffer.wrap(value));
    return this;
  }

  public TItem setValue(ByteBuffer value) {
    this.value = value;
    return this;
  }

  public void unsetValue() {
    this.value = null;
  }

  /** Returns true if field value is set (has been assigned a value) and false otherwise */
  public boolean isSetValue() {
    return this.value != null;
  }

  public void setValueIsSet(boolean value) {
    if (!value) {
      this.value = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case FLAG:
      if (value == null) {
        unsetFlag();
      } else {
        setFlag((ByteBuffer)value);
      }
      break;

    case VALUE:
      if (value == null) {
        unsetValue();
      } else {
        setValue((ByteBuffer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case FLAG:
      return getFlag();

    case VALUE:
      return getValue();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case FLAG:
      return isSetFlag();
    case VALUE:
      return isSetValue();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TItem)
      return this.equals((TItem)that);
    return false;
  }

  public boolean equals(TItem that) {
    if (that == null)
      return false;

    boolean this_present_flag = true && this.isSetFlag();
    boolean that_present_flag = true && that.isSetFlag();
    if (this_present_flag || that_present_flag) {
      if (!(this_present_flag && that_present_flag))
        return false;
      if (!this.flag.equals(that.flag))
        return false;
    }

    boolean this_present_value = true && this.isSetValue();
    boolean that_present_value = true && that.isSetValue();
    if (this_present_value || that_present_value) {
      if (!(this_present_value && that_present_value))
        return false;
      if (!this.value.equals(that.value))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(TItem other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    TItem typedOther = (TItem)other;

    lastComparison = Boolean.valueOf(isSetFlag()).compareTo(typedOther.isSetFlag());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFlag()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.flag, typedOther.flag);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetValue()).compareTo(typedOther.isSetValue());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetValue()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.value, typedOther.value);
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
    StringBuilder sb = new StringBuilder("TItem(");
    boolean first = true;

    sb.append("flag:");
    if (this.flag == null) {
      sb.append("null");
    } else {
      org.apache.thrift.TBaseHelper.toString(this.flag, sb);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("value:");
    if (this.value == null) {
      sb.append("null");
    } else {
      org.apache.thrift.TBaseHelper.toString(this.value, sb);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (flag == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'flag' was not present! Struct: " + toString());
    }
    if (value == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'value' was not present! Struct: " + toString());
    }
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class TItemStandardSchemeFactory implements SchemeFactory {
    public TItemStandardScheme getScheme() {
      return new TItemStandardScheme();
    }
  }

  private static class TItemStandardScheme extends StandardScheme<TItem> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TItem struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // FLAG
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.flag = iprot.readBinary();
              struct.setFlagIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // VALUE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.value = iprot.readBinary();
              struct.setValueIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TItem struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.flag != null) {
        oprot.writeFieldBegin(FLAG_FIELD_DESC);
        oprot.writeBinary(struct.flag);
        oprot.writeFieldEnd();
      }
      if (struct.value != null) {
        oprot.writeFieldBegin(VALUE_FIELD_DESC);
        oprot.writeBinary(struct.value);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TItemTupleSchemeFactory implements SchemeFactory {
    public TItemTupleScheme getScheme() {
      return new TItemTupleScheme();
    }
  }

  private static class TItemTupleScheme extends TupleScheme<TItem> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TItem struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeBinary(struct.flag);
      oprot.writeBinary(struct.value);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TItem struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.flag = iprot.readBinary();
      struct.setFlagIsSet(true);
      struct.value = iprot.readBinary();
      struct.setValueIsSet(true);
    }
  }

}

