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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "Proyecto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proyecto.findAll", query = "SELECT p FROM Proyecto p"),
    @NamedQuery(name = "Proyecto.findByIDProyecto", query = "SELECT p FROM Proyecto p WHERE p.iDProyecto = :iDProyecto"),
    @NamedQuery(name = "Proyecto.findByFechaInicio", query = "SELECT p FROM Proyecto p WHERE p.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "Proyecto.findByConstructora", query = "SELECT p FROM Proyecto p WHERE p.constructora = :constructora"),
    @NamedQuery(name = "Proyecto.findByNumeroBanos", query = "SELECT p FROM Proyecto p WHERE p.numeroBanos = :numeroBanos"),
    @NamedQuery(name = "Proyecto.findByNumeroHabitaciones", query = "SELECT p FROM Proyecto p WHERE p.numeroHabitaciones = :numeroHabitaciones"),
    @NamedQuery(name = "Proyecto.findByBancoVinculado", query = "SELECT p FROM Proyecto p WHERE p.bancoVinculado = :bancoVinculado"),
    @NamedQuery(name = "Proyecto.findByPorcentajeCuotaInicial", query = "SELECT p FROM Proyecto p WHERE p.porcentajeCuotaInicial = :porcentajeCuotaInicial"),
    @NamedQuery(name = "Proyecto.findByCiudad", query = "SELECT p FROM Proyecto p WHERE p.ciudad = :ciudad"),
    @NamedQuery(name = "Proyecto.findByClasificacion", query = "SELECT p FROM Proyecto p WHERE p.clasificacion = :clasificacion"),
    @NamedQuery(name = "Proyecto.findByAcabados", query = "SELECT p FROM Proyecto p WHERE p.acabados = :acabados"),
    @NamedQuery(name = "Proyecto.findBySerial", query = "SELECT p FROM Proyecto p WHERE p.serial = :serial")})
public class Proyecto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_Proyecto")
    private Integer iDProyecto;
    @Basic(optional = false)
    @Column(name = "Fecha_Inicio")
    private String fechaInicio;
    @Basic(optional = false)
    @Column(name = "Constructora")
    private String constructora;
    @Basic(optional = false)
    @Column(name = "Numero_Banos")
    private double numeroBanos;
    @Basic(optional = false)
    @Column(name = "Numero_Habitaciones")
    private double numeroHabitaciones;
    @Basic(optional = false)
    @Column(name = "Banco_Vinculado")
    private String bancoVinculado;
    @Basic(optional = false)
    @Column(name = "Porcentaje_Cuota_Inicial")
    private double porcentajeCuotaInicial;
    @Basic(optional = false)
    @Column(name = "Ciudad")
    private String ciudad;
    @Basic(optional = false)
    @Column(name = "Clasificacion")
    private String clasificacion;
    @Basic(optional = false)
    @Column(name = "Acabados")
    private String acabados;
    @Basic(optional = false)
    @Column(name = "Serial")
    private String serial;
    @JoinColumn(name = "ID_Lider", referencedColumnName = "ID_Lider")
    @ManyToOne(optional = false)
    private Lider iDLider;
    @JoinColumn(name = "ID_Tipo", referencedColumnName = "ID_Tipo")
    @ManyToOne(optional = false)
    private Tipo iDTipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDProyecto")
    private Collection<Compra> compraCollection;

    public Proyecto() {
    }

    public Proyecto(Integer iDProyecto) {
        this.iDProyecto = iDProyecto;
    }

    public Proyecto(Integer iDProyecto, String fechaInicio, String constructora, double numeroBanos, double numeroHabitaciones, String bancoVinculado, double porcentajeCuotaInicial, String ciudad, String clasificacion, String acabados, String serial) {
        this.iDProyecto = iDProyecto;
        this.fechaInicio = fechaInicio;
        this.constructora = constructora;
        this.numeroBanos = numeroBanos;
        this.numeroHabitaciones = numeroHabitaciones;
        this.bancoVinculado = bancoVinculado;
        this.porcentajeCuotaInicial = porcentajeCuotaInicial;
        this.ciudad = ciudad;
        this.clasificacion = clasificacion;
        this.acabados = acabados;
        this.serial = serial;
    }

    public Integer getIDProyecto() {
        return iDProyecto;
    }

    public void setIDProyecto(Integer iDProyecto) {
        this.iDProyecto = iDProyecto;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getConstructora() {
        return constructora;
    }

    public void setConstructora(String constructora) {
        this.constructora = constructora;
    }

    public double getNumeroBanos() {
        return numeroBanos;
    }

    public void setNumeroBanos(double numeroBanos) {
        this.numeroBanos = numeroBanos;
    }

    public double getNumeroHabitaciones() {
        return numeroHabitaciones;
    }

    public void setNumeroHabitaciones(double numeroHabitaciones) {
        this.numeroHabitaciones = numeroHabitaciones;
    }

    public String getBancoVinculado() {
        return bancoVinculado;
    }

    public void setBancoVinculado(String bancoVinculado) {
        this.bancoVinculado = bancoVinculado;
    }

    public double getPorcentajeCuotaInicial() {
        return porcentajeCuotaInicial;
    }

    public void setPorcentajeCuotaInicial(double porcentajeCuotaInicial) {
        this.porcentajeCuotaInicial = porcentajeCuotaInicial;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getAcabados() {
        return acabados;
    }

    public void setAcabados(String acabados) {
        this.acabados = acabados;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Lider getIDLider() {
        return iDLider;
    }

    public void setIDLider(Lider iDLider) {
        this.iDLider = iDLider;
    }

    public Tipo getIDTipo() {
        return iDTipo;
    }

    public void setIDTipo(Tipo iDTipo) {
        this.iDTipo = iDTipo;
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
        hash += (iDProyecto != null ? iDProyecto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proyecto)) {
            return false;
        }
        Proyecto other = (Proyecto) object;
        if ((this.iDProyecto == null && other.iDProyecto != null) || (this.iDProyecto != null && !this.iDProyecto.equals(other.iDProyecto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Proyecto[ iDProyecto=" + iDProyecto + " ]";
    }
    
}
