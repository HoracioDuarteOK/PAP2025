/**
 * DtLector.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package publicadores;

public class DtLector  implements java.io.Serializable {
    private int id;
    private java.lang.String nombre;
    private java.lang.String email;
    private java.lang.String direccion;
    private DTFecha fechaRegistro;
    private java.lang.String estadoL;
    private java.lang.String zona;

    public DtLector() {
    }

    public DtLector(
           int id,
           java.lang.String nombre,
           java.lang.String email,
           java.lang.String direccion,
           DTFecha fechaRegistro,
           java.lang.String estadoL,
           java.lang.String zona) {
           this.id = id;
           this.nombre = nombre;
           this.email = email;
           this.direccion = direccion;
           this.fechaRegistro = fechaRegistro;
           this.estadoL = estadoL;
           this.zona = zona;
    }

    /**
     * Gets the id value for this DtLector.
     * 
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id value for this DtLector.
     * 
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the nombre value for this DtLector.
     * 
     * @return nombre
     */
    public java.lang.String getNombre() {
        return nombre;
    }

    /**
     * Sets the nombre value for this DtLector.
     * 
     * @param nombre
     */
    public void setNombre(java.lang.String nombre) {
        this.nombre = nombre;
    }

    /**
     * Gets the email value for this DtLector.
     * 
     * @return email
     */
    public java.lang.String getEmail() {
        return email;
    }

    /**
     * Sets the email value for this DtLector.
     * 
     * @param email
     */
    public void setEmail(java.lang.String email) {
        this.email = email;
    }

    /**
     * Gets the direccion value for this DtLector.
     * 
     * @return direccion
     */
    public java.lang.String getDireccion() {
        return direccion;
    }

    /**
     * Sets the direccion value for this DtLector.
     * 
     * @param direccion
     */
    public void setDireccion(java.lang.String direccion) {
        this.direccion = direccion;
    }

    /**
     * Gets the fechaRegistro value for this DtLector.
     * 
     * @return fechaRegistro
     */
    public DTFecha getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * Sets the fechaRegistro value for this DtLector.
     * 
     * @param fechaRegistro
     */
    public void setFechaRegistro(DTFecha fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * Gets the estadoL value for this DtLector.
     * 
     * @return estadoL
     */
    public java.lang.String getEstadoL() {
        return estadoL;
    }

    /**
     * Sets the estadoL value for this DtLector.
     * 
     * @param estadoL
     */
    public void setEstadoL(java.lang.String estadoL) {
        this.estadoL = estadoL;
    }

    /**
     * Gets the zona value for this DtLector.
     * 
     * @return zona
     */
    public java.lang.String getZona() {
        return zona;
    }

    /**
     * Sets the zona value for this DtLector.
     * 
     * @param zona
     */
    public void setZona(java.lang.String zona) {
        this.zona = zona;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DtLector)) return false;
        DtLector other = (DtLector) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = this;
        boolean _equals;
        _equals = true && 
            this.id == other.getId() &&
            ((this.nombre==null && other.getNombre()==null) || 
             (this.nombre!=null &&
              this.nombre.equals(other.getNombre()))) &&
            ((this.email==null && other.getEmail()==null) || 
             (this.email!=null &&
              this.email.equals(other.getEmail()))) &&
            ((this.direccion==null && other.getDireccion()==null) || 
             (this.direccion!=null &&
              this.direccion.equals(other.getDireccion()))) &&
            ((this.fechaRegistro==null && other.getFechaRegistro()==null) || 
             (this.fechaRegistro!=null &&
              this.fechaRegistro.equals(other.getFechaRegistro()))) &&
            ((this.estadoL==null && other.getEstadoL()==null) || 
             (this.estadoL!=null &&
              this.estadoL.equals(other.getEstadoL()))) &&
            ((this.zona==null && other.getZona()==null) || 
             (this.zona!=null &&
              this.zona.equals(other.getZona())));
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
        _hashCode += getId();
        if (getNombre() != null) {
            _hashCode += getNombre().hashCode();
        }
        if (getEmail() != null) {
            _hashCode += getEmail().hashCode();
        }
        if (getDireccion() != null) {
            _hashCode += getDireccion().hashCode();
        }
        if (getFechaRegistro() != null) {
            _hashCode += getFechaRegistro().hashCode();
        }
        if (getEstadoL() != null) {
            _hashCode += getEstadoL().hashCode();
        }
        if (getZona() != null) {
            _hashCode += getZona().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DtLector.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://publicadores/", "dtLector"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombre");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nombre"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("email");
        elemField.setXmlName(new javax.xml.namespace.QName("", "email"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("direccion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "direccion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaRegistro");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fechaRegistro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://publicadores/", "dtFecha"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estadoL");
        elemField.setXmlName(new javax.xml.namespace.QName("", "estadoL"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("zona");
        elemField.setXmlName(new javax.xml.namespace.QName("", "zona"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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
