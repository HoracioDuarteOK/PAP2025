/**
 * DtPrestamo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package publicadores;

public class DtPrestamo  implements java.io.Serializable {
    private int id;
    private DtLector lector;
    private DtMaterial material;
    private DtBibliotecario bibliotecario;
    private DTFecha fechaPrestamo;
    private DTFecha fechaVencimiento;
    private DTFecha fechaDevolucion;
    private java.lang.String estado; // "EN_CURSO", "DEVUELTO", "VENCIDO"

    public DtPrestamo() {
    }

    public DtPrestamo(
           int id,
           DtLector lector,
           DtMaterial material,
           DtBibliotecario bibliotecario,
           DTFecha fechaPrestamo,
           DTFecha fechaVencimiento,
           DTFecha fechaDevolucion,
           java.lang.String estado) {
           this.id = id;
           this.lector = lector;
           this.material = material;
           this.bibliotecario = bibliotecario;
           this.fechaPrestamo = fechaPrestamo;
           this.fechaVencimiento = fechaVencimiento;
           this.fechaDevolucion = fechaDevolucion;
           this.estado = estado;
    }

    /**
     * Gets the id value for this DtPrestamo.
     * 
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id value for this DtPrestamo.
     * 
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the lector value for this DtPrestamo.
     * 
     * @return lector
     */
    public DtLector getLector() {
        return lector;
    }

    /**
     * Sets the lector value for this DtPrestamo.
     * 
     * @param lector
     */
    public void setLector(DtLector lector) {
        this.lector = lector;
    }

    /**
     * Gets the material value for this DtPrestamo.
     * 
     * @return material
     */
    public DtMaterial getMaterial() {
        return material;
    }

    /**
     * Sets the material value for this DtPrestamo.
     * 
     * @param material
     */
    public void setMaterial(DtMaterial material) {
        this.material = material;
    }

    /**
     * Gets the bibliotecario value for this DtPrestamo.
     * 
     * @return bibliotecario
     */
    public DtBibliotecario getBibliotecario() {
        return bibliotecario;
    }

    /**
     * Sets the bibliotecario value for this DtPrestamo.
     * 
     * @param bibliotecario
     */
    public void setBibliotecario(DtBibliotecario bibliotecario) {
        this.bibliotecario = bibliotecario;
    }

    /**
     * Gets the fechaPrestamo value for this DtPrestamo.
     * 
     * @return fechaPrestamo
     */
    public DTFecha getFechaPrestamo() {
        return fechaPrestamo;
    }

    /**
     * Sets the fechaPrestamo value for this DtPrestamo.
     * 
     * @param fechaPrestamo
     */
    public void setFechaPrestamo(DTFecha fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    /**
     * Gets the fechaVencimiento value for this DtPrestamo.
     * 
     * @return fechaVencimiento
     */
    public DTFecha getFechaVencimiento() {
        return fechaVencimiento;
    }

    /**
     * Sets the fechaVencimiento value for this DtPrestamo.
     * 
     * @param fechaVencimiento
     */
    public void setFechaVencimiento(DTFecha fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    /**
     * Gets the fechaDevolucion value for this DtPrestamo.
     * 
     * @return fechaDevolucion
     */
    public DTFecha getFechaDevolucion() {
        return fechaDevolucion;
    }

    /**
     * Sets the fechaDevolucion value for this DtPrestamo.
     * 
     * @param fechaDevolucion
     */
    public void setFechaDevolucion(DTFecha fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    /**
     * Gets the estado value for this DtPrestamo.
     * 
     * @return estado
     */
    public java.lang.String getEstado() {
        return estado;
    }

    /**
     * Sets the estado value for this DtPrestamo.
     * 
     * @param estado
     */
    public void setEstado(java.lang.String estado) {
        this.estado = estado;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DtPrestamo)) return false;
        DtPrestamo other = (DtPrestamo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = this;
        boolean _equals;
        _equals = true && 
            this.id == other.getId() &&
            ((this.lector==null && other.getLector()==null) || 
             (this.lector!=null &&
              this.lector.equals(other.getLector()))) &&
            ((this.material==null && other.getMaterial()==null) || 
             (this.material!=null &&
              this.material.equals(other.getMaterial()))) &&
            ((this.bibliotecario==null && other.getBibliotecario()==null) || 
             (this.bibliotecario!=null &&
              this.bibliotecario.equals(other.getBibliotecario()))) &&
            ((this.fechaPrestamo==null && other.getFechaPrestamo()==null) || 
             (this.fechaPrestamo!=null &&
              this.fechaPrestamo.equals(other.getFechaPrestamo()))) &&
            ((this.fechaVencimiento==null && other.getFechaVencimiento()==null) || 
             (this.fechaVencimiento!=null &&
              this.fechaVencimiento.equals(other.getFechaVencimiento()))) &&
            ((this.fechaDevolucion==null && other.getFechaDevolucion()==null) || 
             (this.fechaDevolucion!=null &&
              this.fechaDevolucion.equals(other.getFechaDevolucion()))) &&
            ((this.estado==null && other.getEstado()==null) || 
             (this.estado!=null &&
              this.estado.equals(other.getEstado())));
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
        if (getLector() != null) {
            _hashCode += getLector().hashCode();
        }
        if (getMaterial() != null) {
            _hashCode += getMaterial().hashCode();
        }
        if (getBibliotecario() != null) {
            _hashCode += getBibliotecario().hashCode();
        }
        if (getFechaPrestamo() != null) {
            _hashCode += getFechaPrestamo().hashCode();
        }
        if (getFechaVencimiento() != null) {
            _hashCode += getFechaVencimiento().hashCode();
        }
        if (getFechaDevolucion() != null) {
            _hashCode += getFechaDevolucion().hashCode();
        }
        if (getEstado() != null) {
            _hashCode += getEstado().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DtPrestamo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://publicadores/", "dtPrestamo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lector");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lector"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://publicadores/", "dtLector"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("material");
        elemField.setXmlName(new javax.xml.namespace.QName("", "material"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://publicadores/", "dtMaterial"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bibliotecario");
        elemField.setXmlName(new javax.xml.namespace.QName("", "bibliotecario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://publicadores/", "dtBibliotecario"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaPrestamo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fechaPrestamo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://publicadores/", "dtFecha"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaVencimiento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fechaVencimiento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://publicadores/", "dtFecha"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaDevolucion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fechaDevolucion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://publicadores/", "dtFecha"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "estado"));
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
