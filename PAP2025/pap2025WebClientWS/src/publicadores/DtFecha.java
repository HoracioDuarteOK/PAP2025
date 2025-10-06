/**
 * DtFecha.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package publicadores;

public class DtFecha  implements java.io.Serializable {
    private int dia;
    private int mes;
    private int anio;

    public DtFecha() {
    }

    public DtFecha(
           int dia,
           int mes,
           int anio) {
           this.dia = dia;
           this.mes = mes;
           this.anio = anio;
    }

    /**
     * Gets the dia value for this DtFecha.
     * 
     * @return dia
     */
    public int getDia() {
        return dia;
    }

    /**
     * Sets the dia value for this DtFecha.
     * 
     * @param dia
     */
    public void setDia(int dia) {
        this.dia = dia;
    }

    /**
     * Gets the mes value for this DtFecha.
     * 
     * @return mes
     */
    public int getMes() {
        return mes;
    }

    /**
     * Sets the mes value for this DtFecha.
     * 
     * @param mes
     */
    public void setMes(int mes) {
        this.mes = mes;
    }

    /**
     * Gets the anio value for this DtFecha.
     * 
     * @return anio
     */
    public int getAnio() {
        return anio;
    }

    /**
     * Sets the anio value for this DtFecha.
     * 
     * @param anio
     */
    public void setAnio(int anio) {
        this.anio = anio;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DtFecha)) return false;
        DtFecha other = (DtFecha) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = this;
        boolean _equals;
        _equals = true && 
            this.dia == other.getDia() &&
            this.mes == other.getMes() &&
            this.anio == other.getAnio();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += getDia();
        _hashCode += getMes();
        _hashCode += getAnio();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DtFecha.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://publicadores/", "dtFecha"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mes");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("anio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "anio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
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
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
