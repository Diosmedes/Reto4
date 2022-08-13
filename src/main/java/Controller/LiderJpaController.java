/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Controller.exceptions.IllegalOrphanException;
import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import Entidades.Lider;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
public class LiderJpaController implements Serializable {

    public LiderJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Lider lider) throws PreexistingEntityException, Exception {
        if (lider.getProyectoCollection() == null) {
            lider.setProyectoCollection(new ArrayList<Proyecto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Proyecto> attachedProyectoCollection = new ArrayList<Proyecto>();
            for (Proyecto proyectoCollectionProyectoToAttach : lider.getProyectoCollection()) {
                proyectoCollectionProyectoToAttach = em.getReference(proyectoCollectionProyectoToAttach.getClass(), proyectoCollectionProyectoToAttach.getIDProyecto());
                attachedProyectoCollection.add(proyectoCollectionProyectoToAttach);
            }
            lider.setProyectoCollection(attachedProyectoCollection);
            em.persist(lider);
            for (Proyecto proyectoCollectionProyecto : lider.getProyectoCollection()) {
                Lider oldIDLiderOfProyectoCollectionProyecto = proyectoCollectionProyecto.getIDLider();
                proyectoCollectionProyecto.setIDLider(lider);
                proyectoCollectionProyecto = em.merge(proyectoCollectionProyecto);
                if (oldIDLiderOfProyectoCollectionProyecto != null) {
                    oldIDLiderOfProyectoCollectionProyecto.getProyectoCollection().remove(proyectoCollectionProyecto);
                    oldIDLiderOfProyectoCollectionProyecto = em.merge(oldIDLiderOfProyectoCollectionProyecto);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLider(lider.getIDLider()) != null) {
                throw new PreexistingEntityException("Lider " + lider + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Lider lider) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Lider persistentLider = em.find(Lider.class, lider.getIDLider());
            Collection<Proyecto> proyectoCollectionOld = persistentLider.getProyectoCollection();
            Collection<Proyecto> proyectoCollectionNew = lider.getProyectoCollection();
            List<String> illegalOrphanMessages = null;
            for (Proyecto proyectoCollectionOldProyecto : proyectoCollectionOld) {
                if (!proyectoCollectionNew.contains(proyectoCollectionOldProyecto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Proyecto " + proyectoCollectionOldProyecto + " since its IDLider field is not nullable.");
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
            lider.setProyectoCollection(proyectoCollectionNew);
            lider = em.merge(lider);
            for (Proyecto proyectoCollectionNewProyecto : proyectoCollectionNew) {
                if (!proyectoCollectionOld.contains(proyectoCollectionNewProyecto)) {
                    Lider oldIDLiderOfProyectoCollectionNewProyecto = proyectoCollectionNewProyecto.getIDLider();
                    proyectoCollectionNewProyecto.setIDLider(lider);
                    proyectoCollectionNewProyecto = em.merge(proyectoCollectionNewProyecto);
                    if (oldIDLiderOfProyectoCollectionNewProyecto != null && !oldIDLiderOfProyectoCollectionNewProyecto.equals(lider)) {
                        oldIDLiderOfProyectoCollectionNewProyecto.getProyectoCollection().remove(proyectoCollectionNewProyecto);
                        oldIDLiderOfProyectoCollectionNewProyecto = em.merge(oldIDLiderOfProyectoCollectionNewProyecto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = lider.getIDLider();
                if (findLider(id) == null) {
                    throw new NonexistentEntityException("The lider with id " + id + " no longer exists.");
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
            Lider lider;
            try {
                lider = em.getReference(Lider.class, id);
                lider.getIDLider();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lider with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Proyecto> proyectoCollectionOrphanCheck = lider.getProyectoCollection();
            for (Proyecto proyectoCollectionOrphanCheckProyecto : proyectoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Lider (" + lider + ") cannot be destroyed since the Proyecto " + proyectoCollectionOrphanCheckProyecto + " in its proyectoCollection field has a non-nullable IDLider field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(lider);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Lider> findLiderEntities() {
        return findLiderEntities(true, -1, -1);
    }

    public List<Lider> findLiderEntities(int maxResults, int firstResult) {
        return findLiderEntities(false, maxResults, firstResult);
    }

    private List<Lider> findLiderEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Lider.class));
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

    public Lider findLider(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Lider.class, id);
        } finally {
            em.close();
        }
    }

    public int getLiderCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Lider> rt = cq.from(Lider.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
