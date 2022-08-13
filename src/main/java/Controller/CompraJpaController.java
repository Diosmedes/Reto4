/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import Entidades.Compra;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.MaterialConstruccion;
import Entidades.Proyecto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author BRYAN
 */
public class CompraJpaController implements Serializable {

    public CompraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Compra compra) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MaterialConstruccion IDMaterialConstruccion = compra.getIDMaterialConstruccion();
            if (IDMaterialConstruccion != null) {
                IDMaterialConstruccion = em.getReference(IDMaterialConstruccion.getClass(), IDMaterialConstruccion.getIDMaterialConstruccion());
                compra.setIDMaterialConstruccion(IDMaterialConstruccion);
            }
            Proyecto IDProyecto = compra.getIDProyecto();
            if (IDProyecto != null) {
                IDProyecto = em.getReference(IDProyecto.getClass(), IDProyecto.getIDProyecto());
                compra.setIDProyecto(IDProyecto);
            }
            em.persist(compra);
            if (IDMaterialConstruccion != null) {
                IDMaterialConstruccion.getCompraCollection().add(compra);
                IDMaterialConstruccion = em.merge(IDMaterialConstruccion);
            }
            if (IDProyecto != null) {
                IDProyecto.getCompraCollection().add(compra);
                IDProyecto = em.merge(IDProyecto);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCompra(compra.getIDCompra()) != null) {
                throw new PreexistingEntityException("Compra " + compra + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Compra compra) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Compra persistentCompra = em.find(Compra.class, compra.getIDCompra());
            MaterialConstruccion IDMaterialConstruccionOld = persistentCompra.getIDMaterialConstruccion();
            MaterialConstruccion IDMaterialConstruccionNew = compra.getIDMaterialConstruccion();
            Proyecto IDProyectoOld = persistentCompra.getIDProyecto();
            Proyecto IDProyectoNew = compra.getIDProyecto();
            if (IDMaterialConstruccionNew != null) {
                IDMaterialConstruccionNew = em.getReference(IDMaterialConstruccionNew.getClass(), IDMaterialConstruccionNew.getIDMaterialConstruccion());
                compra.setIDMaterialConstruccion(IDMaterialConstruccionNew);
            }
            if (IDProyectoNew != null) {
                IDProyectoNew = em.getReference(IDProyectoNew.getClass(), IDProyectoNew.getIDProyecto());
                compra.setIDProyecto(IDProyectoNew);
            }
            compra = em.merge(compra);
            if (IDMaterialConstruccionOld != null && !IDMaterialConstruccionOld.equals(IDMaterialConstruccionNew)) {
                IDMaterialConstruccionOld.getCompraCollection().remove(compra);
                IDMaterialConstruccionOld = em.merge(IDMaterialConstruccionOld);
            }
            if (IDMaterialConstruccionNew != null && !IDMaterialConstruccionNew.equals(IDMaterialConstruccionOld)) {
                IDMaterialConstruccionNew.getCompraCollection().add(compra);
                IDMaterialConstruccionNew = em.merge(IDMaterialConstruccionNew);
            }
            if (IDProyectoOld != null && !IDProyectoOld.equals(IDProyectoNew)) {
                IDProyectoOld.getCompraCollection().remove(compra);
                IDProyectoOld = em.merge(IDProyectoOld);
            }
            if (IDProyectoNew != null && !IDProyectoNew.equals(IDProyectoOld)) {
                IDProyectoNew.getCompraCollection().add(compra);
                IDProyectoNew = em.merge(IDProyectoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = compra.getIDCompra();
                if (findCompra(id) == null) {
                    throw new NonexistentEntityException("The compra with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Compra compra;
            try {
                compra = em.getReference(Compra.class, id);
                compra.getIDCompra();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The compra with id " + id + " no longer exists.", enfe);
            }
            MaterialConstruccion IDMaterialConstruccion = compra.getIDMaterialConstruccion();
            if (IDMaterialConstruccion != null) {
                IDMaterialConstruccion.getCompraCollection().remove(compra);
                IDMaterialConstruccion = em.merge(IDMaterialConstruccion);
            }
            Proyecto IDProyecto = compra.getIDProyecto();
            if (IDProyecto != null) {
                IDProyecto.getCompraCollection().remove(compra);
                IDProyecto = em.merge(IDProyecto);
            }
            em.remove(compra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Compra> findCompraEntities() {
        return findCompraEntities(true, -1, -1);
    }

    public List<Compra> findCompraEntities(int maxResults, int firstResult) {
        return findCompraEntities(false, maxResults, firstResult);
    }

    private List<Compra> findCompraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Compra.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Compra findCompra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Compra.class, id);
        } finally {
            em.close();
        }
    }

    public int getCompraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Compra> rt = cq.from(Compra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
