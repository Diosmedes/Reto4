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
import Entidades.Proyecto;
import Entidades.Tipo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author BRYAN
 */
public class TipoJpaController implements Serializable {

    public TipoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipo tipo) throws PreexistingEntityException, Exception {
        if (tipo.getProyectoCollection() == null) {
            tipo.setProyectoCollection(new ArrayList<Proyecto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Proyecto> attachedProyectoCollection = new ArrayList<Proyecto>();
            for (Proyecto proyectoCollectionProyectoToAttach : tipo.getProyectoCollection()) {
                proyectoCollectionProyectoToAttach = em.getReference(proyectoCollectionProyectoToAttach.getClass(), proyectoCollectionProyectoToAttach.getIDProyecto());
                attachedProyectoCollection.add(proyectoCollectionProyectoToAttach);
            }
            tipo.setProyectoCollection(attachedProyectoCollection);
            em.persist(tipo);
            for (Proyecto proyectoCollectionProyecto : tipo.getProyectoCollection()) {
                Tipo oldIDTipoOfProyectoCollectionProyecto = proyectoCollectionProyecto.getIDTipo();
                proyectoCollectionProyecto.setIDTipo(tipo);
                proyectoCollectionProyecto = em.merge(proyectoCollectionProyecto);
                if (oldIDTipoOfProyectoCollectionProyecto != null) {
                    oldIDTipoOfProyectoCollectionProyecto.getProyectoCollection().remove(proyectoCollectionProyecto);
                    oldIDTipoOfProyectoCollectionProyecto = em.merge(oldIDTipoOfProyectoCollectionProyecto);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipo(tipo.getIDTipo()) != null) {
                throw new PreexistingEntityException("Tipo " + tipo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipo tipo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipo persistentTipo = em.find(Tipo.class, tipo.getIDTipo());
            Collection<Proyecto> proyectoCollectionOld = persistentTipo.getProyectoCollection();
            Collection<Proyecto> proyectoCollectionNew = tipo.getProyectoCollection();
            List<String> illegalOrphanMessages = null;
            for (Proyecto proyectoCollectionOldProyecto : proyectoCollectionOld) {
                if (!proyectoCollectionNew.contains(proyectoCollectionOldProyecto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Proyecto " + proyectoCollectionOldProyecto + " since its IDTipo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Proyecto> attachedProyectoCollectionNew = new ArrayList<Proyecto>();
            for (Proyecto proyectoCollectionNewProyectoToAttach : proyectoCollectionNew) {
                proyectoCollectionNewProyectoToAttach = em.getReference(proyectoCollectionNewProyectoToAttach.getClass(), proyectoCollectionNewProyectoToAttach.getIDProyecto());
                attachedProyectoCollectionNew.add(proyectoCollectionNewProyectoToAttach);
            }
            proyectoCollectionNew = attachedProyectoCollectionNew;
            tipo.setProyectoCollection(proyectoCollectionNew);
            tipo = em.merge(tipo);
            for (Proyecto proyectoCollectionNewProyecto : proyectoCollectionNew) {
                if (!proyectoCollectionOld.contains(proyectoCollectionNewProyecto)) {
                    Tipo oldIDTipoOfProyectoCollectionNewProyecto = proyectoCollectionNewProyecto.getIDTipo();
                    proyectoCollectionNewProyecto.setIDTipo(tipo);
                    proyectoCollectionNewProyecto = em.merge(proyectoCollectionNewProyecto);
                    if (oldIDTipoOfProyectoCollectionNewProyecto != null && !oldIDTipoOfProyectoCollectionNewProyecto.equals(tipo)) {
                        oldIDTipoOfProyectoCollectionNewProyecto.getProyectoCollection().remove(proyectoCollectionNewProyecto);
                        oldIDTipoOfProyectoCollectionNewProyecto = em.merge(oldIDTipoOfProyectoCollectionNewProyecto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipo.getIDTipo();
                if (findTipo(id) == null) {
                    throw new NonexistentEntityException("The tipo with id " + id + " no longer exists.");
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
            Tipo tipo;
            try {
                tipo = em.getReference(Tipo.class, id);
                tipo.getIDTipo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Proyecto> proyectoCollectionOrphanCheck = tipo.getProyectoCollection();
            for (Proyecto proyectoCollectionOrphanCheckProyecto : proyectoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipo (" + tipo + ") cannot be destroyed since the Proyecto " + proyectoCollectionOrphanCheckProyecto + " in its proyectoCollection field has a non-nullable IDTipo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipo> findTipoEntities() {
        return findTipoEntities(true, -1, -1);
    }

    public List<Tipo> findTipoEntities(int maxResults, int firstResult) {
        return findTipoEntities(false, maxResults, firstResult);
    }

    private List<Tipo> findTipoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipo.class));
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

    public Tipo findTipo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipo.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipo> rt = cq.from(Tipo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
