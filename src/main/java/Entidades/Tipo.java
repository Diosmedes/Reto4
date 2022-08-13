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
@Table(name = "Tipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipo.findAll", query = "SELECT t FROM Tipo t"),
    @NamedQuery(name = "Tipo.findByIDTipo", query = "SELECT t FROM Tipo t WHERE t.iDTipo = :iDTipo"),
    @NamedQuery(name = "Tipo.findByCodigoTipo", query = "SELECT t FROM Tipo t WHERE t.codigoTipo = :codigoTipo"),
    @NamedQuery(name = "Tipo.findByAreaMax", query = "SELECT t FROM Tipo t WHERE t.areaMax = :areaMax"),
    @NamedQuery(name = "Tipo.findByFinanciable", query = "SELECT t FROM Tipo t WHERE t.financiable = :financiable"),
    @NamedQuery(name = "Tipo.findByEstrato", query = "SELECT t FROM Tipo t WHERE t.estrato = :estrato")})
public class Tipo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_Tipo")
    private Integer iDTipo;
    @Basic(optional = false)
    @Column(name = "Codigo_Tipo")
    private int codigoTipo;
    @Basic(optional = false)
    @Column(name = "Area_Max")
    private int areaMax;
    @Basic(optional = false)
    @Column(name = "Financiable")
    private int financiable;
    @Basic(optional = false)
    @Column(name = "Estrato")
    private int estrato;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDTipo")
    private Collection<Proyecto> proyectoCollection;

    public Tipo() {
    }

    public Tipo(Integer iDTipo) {
        this.iDTipo = iDTipo;
    }

    public Tipo(Integer iDTipo, int codigoTipo, int areaMax, int financiable, int estrato) {
        this.iDTipo = iDTipo;
        this.codigoTipo = codigoTipo;
        this.areaMax = areaMax;
        this.financiable = financiable;
        this.estrato = estrato;
    }

    public Integer getIDTipo() {
        return iDTipo;
    }

    public void setIDTipo(Integer iDTipo) {
        this.iDTipo = iDTipo;
    }

    public int getCodigoTipo() {
        return codigoTipo;
    }

    public void setCodigoTipo(int codigoTipo) {
        this.codigoTipo = codigoTipo;
    }

    public int getAreaMax() {
        return areaMax;
    }

    public void setAreaMax(int areaMax) {
        this.areaMax = areaMax;
    }

    public int getFinanciable() {
        return financiable;
    }

    public void setFinanciable(int financiable) {
        this.financiable = financiable;
    }

    public int getEstrato() {
        return estrato;
    }

    public void setEstrato(int estrato) {
        this.estrato = estrato;
    }

    @XmlTransient
    public Collection<Proyecto> getProyectoCollection() {
        return proyectoCollection;
    }

    public void setProyectoCollection(Collection<Proyecto> proyectoCollection) {
        this.proyectoCollection = proyectoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDTipo != null ? iDTipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipo)) {
            return false;
        }
        Tipo other = (Tipo) object;
        if ((this.iDTipo == null && other.iDTipo != null) || (this.iDTipo != null && !this.iDTipo.equals(other.iDTipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Tipo[ iDTipo=" + iDTipo + " ]";
    }
    
}
