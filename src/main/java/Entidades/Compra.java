/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author BRYAN
 */
@Entity
@Table(name = "Compra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Compra.findAll", query = "SELECT c FROM Compra c"),
    @NamedQuery(name = "Compra.findByIDCompra", query = "SELECT c FROM Compra c WHERE c.iDCompra = :iDCompra"),
    @NamedQuery(name = "Compra.findByCantidad", query = "SELECT c FROM Compra c WHERE c.cantidad = :cantidad"),
    @NamedQuery(name = "Compra.findByProveedor", query = "SELECT c FROM Compra c WHERE c.proveedor = :proveedor"),
    @NamedQuery(name = "Compra.findByPagado", query = "SELECT c FROM Compra c WHERE c.pagado = :pagado"),
    @NamedQuery(name = "Compra.findByFecha", query = "SELECT c FROM Compra c WHERE c.fecha = :fecha")})
public class Compra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_Compra")
    private Integer iDCompra;
    @Basic(optional = false)
    @Column(name = "Cantidad")
    private int cantidad;
    @Basic(optional = false)
    @Column(name = "Proveedor")
    private String proveedor;
    @Basic(optional = false)
    @Column(name = "Pagado")
    private String pagado;
    @Basic(optional = false)
    @Column(name = "Fecha")
    private String fecha;
    @JoinColumn(name = "ID_MaterialConstruccion", referencedColumnName = "ID_MaterialConstruccion")
    @ManyToOne(optional = false)
    private MaterialConstruccion iDMaterialConstruccion;
    @JoinColumn(name = "ID_Proyecto", referencedColumnName = "ID_Proyecto")
    @ManyToOne(optional = false)
    private Proyecto iDProyecto;

    public Compra() {
    }

    public Compra(Integer iDCompra) {
        this.iDCompra = iDCompra;
    }

    public Compra(Integer iDCompra, int cantidad, String proveedor, String pagado, String fecha) {
        this.iDCompra = iDCompra;
        this.cantidad = cantidad;
        this.proveedor = proveedor;
        this.pagado = pagado;
        this.fecha = fecha;
    }

    public Integer getIDCompra() {
        return iDCompra;
    }

    public void setIDCompra(Integer iDCompra) {
        this.iDCompra = iDCompra;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getPagado() {
        return pagado;
    }

    public void setPagado(String pagado) {
        this.pagado = pagado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public MaterialConstruccion getIDMaterialConstruccion() {
        return iDMaterialConstruccion;
    }

    public void setIDMaterialConstruccion(MaterialConstruccion iDMaterialConstruccion) {
        this.iDMaterialConstruccion = iDMaterialConstruccion;
    }

    public Proyecto getIDProyecto() {
        return iDProyecto;
    }

    public void setIDProyecto(Proyecto iDProyecto) {
        this.iDProyecto = iDProyecto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDCompra != null ? iDCompra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Compra)) {
            return false;
        }
        Compra other = (Compra) object;
        if ((this.iDCompra == null && other.iDCompra != null) || (this.iDCompra != null && !this.iDCompra.equals(other.iDCompra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Compra[ iDCompra=" + iDCompra + " ]";
    }
    
}
