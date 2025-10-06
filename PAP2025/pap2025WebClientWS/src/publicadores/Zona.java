/**
 * Zona.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package publicadores;

public class Zona implements java.io.Serializable {
    public static final Zona BIBLOTECA_CENTRAL = new Zona("BIBLOTECA_CENTRAL");
    public static final Zona BIBLOTECA_NORTE = new Zona("BIBLOTECA_NORTE");
    public static final Zona BIBLOTECA_SUR = new Zona("BIBLOTECA_SUR");
    public static final Zona BIBLOTECA_ESTE = new Zona("BIBLOTECA_ESTE");
    public static final Zona BIBLOTECA_OESTE = new Zona("BIBLOTECA_OESTE");

    private java.lang.String _value_;

    public Zona() {
    }

    protected Zona(java.lang.String value) {
        _value_ = value;
    }

    public static Zona fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        Zona enumeration = (Zona)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }

    public static Zona fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }

    public java.lang.String getValue() { return _value_;}

    public boolean equals(java.lang.Object obj) {return (obj == this);}

    public int hashCode() { return toString().hashCode();}

    public java.lang.String toString() { return _value_;}

    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}

    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }

    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }

    // Type metadata
    private static java.util.Hashtable _table_ = new java.util.Hashtable();

    // Constructor
    static {
        _table_.put("BIBLOTECA_CENTRAL", BIBLOTECA_CENTRAL);
        _table_.put("BIBLOTECA_NORTE", BIBLOTECA_NORTE);
        _table_.put("BIBLOTECA_SUR", BIBLOTECA_SUR);
        _table_.put("BIBLOTECA_ESTE", BIBLOTECA_ESTE);
        _table_.put("BIBLOTECA_OESTE", BIBLOTECA_OESTE);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Zona.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://publicadores/", "zona"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
