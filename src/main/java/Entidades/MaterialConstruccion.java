/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author BRYAN
 */
@Entity
@Table(name = "MaterialConstruccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MaterialConstruccion.findAll", query = "SELECT m FROM MaterialConstruccion m"),
    @NamedQuery(name = "MaterialConstruccion.findByIDMaterialConstruccion", query = "SELECT m FROM MaterialConstruccion m WHERE m.iDMaterialConstruccion = :iDMaterialConstruccion"),
    @NamedQuery(name = "MaterialConstruccion.findByNombreMaterial", query = "SELECT m FROM MaterialConstruccion m WHERE m.nombreMaterial = :nombreMaterial"),
    @NamedQuery(name = "MaterialConstruccion.findByImportado", query = "SELECT m FROM MaterialConstruccion m WHERE m.importado = :importado"),
    @NamedQuery(name = "MaterialConstruccion.findByPrecioUnidad", query = "SELECT m FROM MaterialConstruccion m WHERE m.precioUnidad = :precioUnidad")})
public class MaterialConstruccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_MaterialConstruccion")
    private Integer iDMaterialConstruccion;
    @Basic(optional = false)
    @Column(name = "Nombre_Material")
    private String nombreMaterial;
    @Basic(optional = false)
    @Column(name = "Importado")
    private String importado;
    @Basic(optional = false)
    @Column(name = "Precio_Unidad")
    private int precioUnidad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDMaterialConstruccion")
    private Collection<Compra> compraCollection;

    public MaterialConstruccion() {
    }

    public MaterialConstruccion(Integer iDMaterialConstruccion) {
        this.iDMaterialConstruccion = iDMaterialConstruccion;
    }

    public MaterialConstruccion(Integer iDMaterialConstruccion, String nombreMaterial, String importado, int precioUnidad) {
        this.iDMaterialConstruccion = iDMaterialConstruccion;
        this.nombreMaterial = nombreMaterial;
        this.importado = importado;
        this.precioUnidad = precioUnidad;
    }

    public Integer getIDMaterialConstruccion() {
        return iDMaterialConstruccion;
    }

    public void setIDMaterialConstruccion(Integer iDMaterialConstruccion) {
        this.iDMaterialConstruccion = iDMaterialConstruccion;
    }

    public String getNombreMaterial() {
        return nombreMaterial;
    }

    public void setNombreMaterial(String nombreMaterial) {
        this.nombreMaterial = nombreMaterial;
    }

    public String getImportado() {
        return importado;
    }

    public void setImportado(String importado) {
        this.importado = importado;
    }

    public int getPrecioUnidad() {
        return precioUnidad;
    }

    public void setPrecioUnidad(int precioUnidad) {
        this.precioUnidad = precioUnidad;
    }

    @XmlTransient
    public Collection<Compra> getCompraCollection() {
        return compraCollection;
    }

    public void setCompraCollection(Collection<Compra> compraCollection) {
        this.compraCollection = compraCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDMaterialConstruccion != null ? iDMaterialConstruccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MaterialConstruccion)) {
            return false;
        }
        MaterialConstruccion other = (MaterialConstruccion) object;
        if ((this.iDMaterialConstruccion == null && other.iDMaterialConstruccion != null) || (this.iDMaterialConstruccion != null && !this.iDMaterialConstruccion.equals(other.iDMaterialConstruccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.MaterialConstruccion[ iDMaterialConstruccion=" + iDMaterialConstruccion + " ]";
    }
    
}
