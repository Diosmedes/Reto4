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
@Table(name = "Lider")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lider.findAll", query = "SELECT l FROM Lider l"),
    @NamedQuery(name = "Lider.findByIDLider", query = "SELECT l FROM Lider l WHERE l.iDLider = :iDLider"),
    @NamedQuery(name = "Lider.findByNombre", query = "SELECT l FROM Lider l WHERE l.nombre = :nombre"),
    @NamedQuery(name = "Lider.findByPrimerApellido", query = "SELECT l FROM Lider l WHERE l.primerApellido = :primerApellido"),
    @NamedQuery(name = "Lider.findBySegundoApellido", query = "SELECT l FROM Lider l WHERE l.segundoApellido = :segundoApellido"),
    @NamedQuery(name = "Lider.findBySalario", query = "SELECT l FROM Lider l WHERE l.salario = :salario"),
    @NamedQuery(name = "Lider.findByCiudadResidencia", query = "SELECT l FROM Lider l WHERE l.ciudadResidencia = :ciudadResidencia"),
    @NamedQuery(name = "Lider.findByCargo", query = "SELECT l FROM Lider l WHERE l.cargo = :cargo"),
    @NamedQuery(name = "Lider.findByClasificacion", query = "SELECT l FROM Lider l WHERE l.clasificacion = :clasificacion"),
    @NamedQuery(name = "Lider.findByDocumentoIdentidad", query = "SELECT l FROM Lider l WHERE l.documentoIdentidad = :documentoIdentidad"),
    @NamedQuery(name = "Lider.findByFechaNacimiento", query = "SELECT l FROM Lider l WHERE l.fechaNacimiento = :fechaNacimiento")})
public class Lider implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_Lider")
    private Integer iDLider;
    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "Primer_Apellido")
    private String primerApellido;
    @Basic(optional = false)
    @Column(name = "Segundo_Apellido")
    private String segundoApellido;
    @Basic(optional = false)
    @Column(name = "Salario")
    private int salario;
    @Basic(optional = false)
    @Column(name = "Ciudad_Residencia")
    private String ciudadResidencia;
    @Basic(optional = false)
    @Column(name = "Cargo")
    private String cargo;
    @Basic(optional = false)
    @Column(name = "Clasificacion")
    private double clasificacion;
    @Basic(optional = false)
    @Column(name = "Documento_Identidad")
    private String documentoIdentidad;
    @Basic(optional = false)
    @Column(name = "Fecha_Nacimiento")
    private String fechaNacimiento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDLider")
    private Collection<Proyecto> proyectoCollection;

    public Lider() {
    }

    public Lider(Integer iDLider) {
        this.iDLider = iDLider;
    }

    public Lider(Integer iDLider, String nombre, String primerApellido, String segundoApellido, int salario, String ciudadResidencia, String cargo, double clasificacion, String documentoIdentidad, String fechaNacimiento) {
        this.iDLider = iDLider;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.salario = salario;
        this.ciudadResidencia = ciudadResidencia;
        this.cargo = cargo;
        this.clasificacion = clasificacion;
        this.documentoIdentidad = documentoIdentidad;
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getIDLider() {
        return iDLider;
    }

    public void setIDLider(Integer iDLider) {
        this.iDLider = iDLider;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public int getSalario() {
        return salario;
    }

    public void setSalario(int salario) {
        this.salario = salario;
    }

    public String getCiudadResidencia() {
        return ciudadResidencia;
    }

    public void setCiudadResidencia(String ciudadResidencia) {
        this.ciudadResidencia = ciudadResidencia;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public double getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(double clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    public void setDocumentoIdentidad(String documentoIdentidad) {
        this.documentoIdentidad = documentoIdentidad;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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
        hash += (iDLider != null ? iDLider.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lider)) {
            return false;
        }
        Lider other = (Lider) object;
        if ((this.iDLider == null && other.iDLider != null) || (this.iDLider != null && !this.iDLider.equals(other.iDLider))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Lider[ iDLider=" + iDLider + " ]";
    }
    
}
