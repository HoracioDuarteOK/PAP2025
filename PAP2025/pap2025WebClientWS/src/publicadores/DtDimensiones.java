/**
 * DtDimensiones.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package publicadores;

public class DtDimensiones  implements java.io.Serializable {
    private double largo;
    private double ancho;
    private double alto;

    public DtDimensiones() {
    }

    public DtDimensiones(
           double largo,
           double ancho,
           double alto) {
           this.largo = largo;
           this.ancho = ancho;
           this.alto = alto;
    }

    /**
     * Gets the largo value for this DtDimensiones.
     * 
     * @return largo
     */
    public double getLargo() {
        return largo;
    }

    /**
     * Sets the largo value for this DtDimensiones.
     * 
     * @param largo
     */
    public void setLargo(double largo) {
        this.largo = largo;
    }

    /**
     * Gets the ancho value for this DtDimensiones.
     * 
     * @return ancho
     */
    public double getAncho() {
        return ancho;
    }

    /**
     * Sets the ancho value for this DtDimensiones.
     * 
     * @param ancho
     */
    public void setAncho(double ancho) {
        this.ancho = ancho;
    }

    /**
     * Gets the alto value for this DtDimensiones.
     * 
     * @return alto
     */
    public double getAlto() {
        return alto;
    }

    /**
     * Sets the alto value for this DtDimensiones.
     * 
     * @param alto
     */
    public void setAlto(double alto) {
        this.alto = alto;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DtDimensiones)) return false;
        DtDimensiones other = (DtDimensiones) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = this;
        boolean _equals;
        _equals = true && 
            this.largo == other.getLargo() &&
            this.ancho == other.getAncho() &&
            this.alto == other.getAlto();
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
        _hashCode += new Double(getLargo()).hashCode();
        _hashCode += new Double(getAncho()).hashCode();
        _hashCode += new Double(getAlto()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DtDimensiones.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://publicadores/", "dtDimensiones"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("largo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "largo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ancho");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ancho"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("alto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "alto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
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
