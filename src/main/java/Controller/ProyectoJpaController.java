/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Controller.exceptions.IllegalOrphanException;
import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Lider;
import Entidades.Tipo;
import Entidades.Compra;
import Entidades.Proyecto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author BRYAN
 */
public class ProyectoJpaController implements Serializable {

    public ProyectoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proyecto proyecto) throws PreexistingEntityException, Exception {
        if (proyecto.getCompraCollection() == null) {
            proyecto.setCompraCollection(new ArrayList<Compra>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Lider IDLider = proyecto.getIDLider();
            if (IDLider != null) {
                IDLider = em.getReference(IDLider.getClass(), IDLider.getIDLider());
                proyecto.setIDLider(IDLider);
            }
            Tipo IDTipo = proyecto.getIDTipo();
            if (IDTipo != null) {
                IDTipo = em.getReference(IDTipo.getClass(), IDTipo.getIDTipo());
                proyecto.setIDTipo(IDTipo);
            }
            Collection<Compra> attachedCompraCollection = new ArrayList<Compra>();
            for (Compra compraCollectionCompraToAttach : proyecto.getCompraCollection()) {
                compraCollectionCompraToAttach = em.getReference(compraCollectionCompraToAttach.getClass(), compraCollectionCompraToAttach.getIDCompra());
                attachedCompraCollection.add(compraCollectionCompraToAttach);
            }
            proyecto.setCompraCollection(attachedCompraCollection);
            em.persist(proyecto);
            if (IDLider != null) {
                IDLider.getProyectoCollection().add(proyecto);
                IDLider = em.merge(IDLider);
            }
            if (IDTipo != null) {
                IDTipo.getProyectoCollection().add(proyecto);
                IDTipo = em.merge(IDTipo);
            }
            for (Compra compraCollectionCompra : proyecto.getCompraCollection()) {
                Proyecto oldIDProyectoOfCompraCollectionCompra = compraCollectionCompra.getIDProyecto();
                compraCollectionCompra.setIDProyecto(proyecto);
                compraCollectionCompra = em.merge(compraCollectionCompra);
                if (oldIDProyectoOfCompraCollectionCompra != null) {
                    oldIDProyectoOfCompraCollectionCompra.getCompraCollection().remove(compraCollectionCompra);
                    oldIDProyectoOfCompraCollectionCompra = em.merge(oldIDProyectoOfCompraCollectionCompra);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProyecto(proyecto.getIDProyecto()) != null) {
                throw new PreexistingEntityException("Proyecto " + proyecto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proyecto proyecto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proyecto persistentProyecto = em.find(Proyecto.class, proyecto.getIDProyecto());
            Lider IDLiderOld = persistentProyecto.getIDLider();
            Lider IDLiderNew = proyecto.getIDLider();
            Tipo IDTipoOld = persistentProyecto.getIDTipo();
            Tipo IDTipoNew = proyecto.getIDTipo();
            Collection<Compra> compraCollectionOld = persistentProyecto.getCompraCollection();
            Collection<Compra> compraCollectionNew = proyecto.getCompraCollection();
            List<String> illegalOrphanMessages = null;
            for (Compra compraCollectionOldCompra : compraCollectionOld) {
                if (!compraCollectionNew.contains(compraCollectionOldCompra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Compra " + compraCollectionOldCompra + " since its IDProyecto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (IDLiderNew != null) {
                IDLiderNew = em.getReference(IDLiderNew.getClass(), IDLiderNew.getIDLider());
                proyecto.setIDLider(IDLiderNew);
            }
            if (IDTipoNew != null) {
                IDTipoNew = em.getReference(IDTipoNew.getClass(), IDTipoNew.getIDTipo());
                proyecto.setIDTipo(IDTipoNew);
            }
            Collection<Compra> attachedCompraCollectionNew = new ArrayList<Compra>();
            for (Compra compraCollectionNewCompraToAttach : compraCollectionNew) {
                compraCollectionNewCompraToAttach = em.getReference(compraCollectionNewCompraToAttach.getClass(), compraCollectionNewCompraToAttach.getIDCompra());
                attachedCompraCollectionNew.add(compraCollectionNewCompraToAttach);
            }
            compraCollectionNew = attachedCompraCollectionNew;
            proyecto.setCompraCollection(compraCollectionNew);
            proyecto = em.merge(proyecto);
            if (IDLiderOld != null && !IDLiderOld.equals(IDLiderNew)) {
                IDLiderOld.getProyectoCollection().remove(proyecto);
                IDLiderOld = em.merge(IDLiderOld);
            }
            if (IDLiderNew != null && !IDLiderNew.equals(IDLiderOld)) {
                IDLiderNew.getProyectoCollection().add(proyecto);
                IDLiderNew = em.merge(IDLiderNew);
            }
            if (IDTipoOld != null && !IDTipoOld.equals(IDTipoNew)) {
                IDTipoOld.getProyectoCollection().remove(proyecto);
                IDTipoOld = em.merge(IDTipoOld);
            }
            if (IDTipoNew != null && !IDTipoNew.equals(IDTipoOld)) {
                IDTipoNew.getProyectoCollection().add(proyecto);
                IDTipoNew = em.merge(IDTipoNew);
            }
            for (Compra compraCollectionNewCompra : compraCollectionNew) {
                if (!compraCollectionOld.contains(compraCollectionNewCompra)) {
                    Proyecto oldIDProyectoOfCompraCollectionNewCompra = compraCollectionNewCompra.getIDProyecto();
                    compraCollectionNewCompra.setIDProyecto(proyecto);
                    compraCollectionNewCompra = em.merge(compraCollectionNewCompra);
                    if (oldIDProyectoOfCompraCollectionNewCompra != null && !oldIDProyectoOfCompraCollectionNewCompra.equals(proyecto)) {
                        oldIDProyectoOfCompraCollectionNewCompra.getCompraCollection().remove(compraCollectionNewCompra);
                        oldIDProyectoOfCompraCollectionNewCompra = em.merge(oldIDProyectoOfCompraCollectionNewCompra);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = proyecto.getIDProyecto();
                if (findProyecto(id) == null) {
                    throw new NonexistentEntityException("The proyecto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proyecto proyecto;
            try {
                proyecto = em.getReference(Proyecto.class, id);
                proyecto.getIDProyecto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proyecto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Compra> compraCollectionOrphanCheck = proyecto.getCompraCollection();
            for (Compra compraCollectionOrphanCheckCompra : compraCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proyecto (" + proyecto + ") cannot be destroyed since the Compra " + compraCollectionOrphanCheckCompra + " in its compraCollection field has a non-nullable IDProyecto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Lider IDLider = proyecto.getIDLider();
            if (IDLider != null) {
                IDLider.getProyectoCollection().remove(proyecto);
                IDLider = em.merge(IDLider);
            }
            Tipo IDTipo = proyecto.getIDTipo();
            if (IDTipo != null) {
                IDTipo.getProyectoCollection().remove(proyecto);
                IDTipo = em.merge(IDTipo);
            }
            em.remove(proyecto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Proyecto> findProyectoEntities() {
        return findProyectoEntities(true, -1, -1);
    }

    public List<Proyecto> findProyectoEntities(int maxResults, int firstResult) {
        return findProyectoEntities(false, maxResults, firstResult);
    }

    private List<Proyecto> findProyectoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proyecto.class));
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

    public Proyecto findProyecto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proyecto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProyectoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proyecto> rt = cq.from(Proyecto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
