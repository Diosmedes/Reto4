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
import Entidades.Compra;
import Entidades.MaterialConstruccion;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author BRYAN
 */
public class MaterialConstruccionJpaController implements Serializable {

    public MaterialConstruccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MaterialConstruccion materialConstruccion) throws PreexistingEntityException, Exception {
        if (materialConstruccion.getCompraCollection() == null) {
            materialConstruccion.setCompraCollection(new ArrayList<Compra>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Compra> attachedCompraCollection = new ArrayList<Compra>();
            for (Compra compraCollectionCompraToAttach : materialConstruccion.getCompraCollection()) {
                compraCollectionCompraToAttach = em.getReference(compraCollectionCompraToAttach.getClass(), compraCollectionCompraToAttach.getIDCompra());
                attachedCompraCollection.add(compraCollectionCompraToAttach);
            }
            materialConstruccion.setCompraCollection(attachedCompraCollection);
            em.persist(materialConstruccion);
            for (Compra compraCollectionCompra : materialConstruccion.getCompraCollection()) {
                MaterialConstruccion oldIDMaterialConstruccionOfCompraCollectionCompra = compraCollectionCompra.getIDMaterialConstruccion();
                compraCollectionCompra.setIDMaterialConstruccion(materialConstruccion);
                compraCollectionCompra = em.merge(compraCollectionCompra);
                if (oldIDMaterialConstruccionOfCompraCollectionCompra != null) {
                    oldIDMaterialConstruccionOfCompraCollectionCompra.getCompraCollection().remove(compraCollectionCompra);
                    oldIDMaterialConstruccionOfCompraCollectionCompra = em.merge(oldIDMaterialConstruccionOfCompraCollectionCompra);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMaterialConstruccion(materialConstruccion.getIDMaterialConstruccion()) != null) {
                throw new PreexistingEntityException("MaterialConstruccion " + materialConstruccion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MaterialConstruccion materialConstruccion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MaterialConstruccion persistentMaterialConstruccion = em.find(MaterialConstruccion.class, materialConstruccion.getIDMaterialConstruccion());
            Collection<Compra> compraCollectionOld = persistentMaterialConstruccion.getCompraCollection();
            Collection<Compra> compraCollectionNew = materialConstruccion.getCompraCollection();
            List<String> illegalOrphanMessages = null;
            for (Compra compraCollectionOldCompra : compraCollectionOld) {
                if (!compraCollectionNew.contains(compraCollectionOldCompra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Compra " + compraCollectionOldCompra + " since its IDMaterialConstruccion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Compra> attachedCompraCollectionNew = new ArrayList<Compra>();
            for (Compra compraCollectionNewCompraToAttach : compraCollectionNew) {
                compraCollectionNewCompraToAttach = em.getReference(compraCollectionNewCompraToAttach.getClass(), compraCollectionNewCompraToAttach.getIDCompra());
                attachedCompraCollectionNew.add(compraCollectionNewCompraToAttach);
            }
            compraCollectionNew = attachedCompraCollectionNew;
            materialConstruccion.setCompraCollection(compraCollectionNew);
            materialConstruccion = em.merge(materialConstruccion);
            for (Compra compraCollectionNewCompra : compraCollectionNew) {
                if (!compraCollectionOld.contains(compraCollectionNewCompra)) {
                    MaterialConstruccion oldIDMaterialConstruccionOfCompraCollectionNewCompra = compraCollectionNewCompra.getIDMaterialConstruccion();
                    compraCollectionNewCompra.setIDMaterialConstruccion(materialConstruccion);
                    compraCollectionNewCompra = em.merge(compraCollectionNewCompra);
                    if (oldIDMaterialConstruccionOfCompraCollectionNewCompra != null && !oldIDMaterialConstruccionOfCompraCollectionNewCompra.equals(materialConstruccion)) {
                        oldIDMaterialConstruccionOfCompraCollectionNewCompra.getCompraCollection().remove(compraCollectionNewCompra);
                        oldIDMaterialConstruccionOfCompraCollectionNewCompra = em.merge(oldIDMaterialConstruccionOfCompraCollectionNewCompra);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = materialConstruccion.getIDMaterialConstruccion();
                if (findMaterialConstruccion(id) == null) {
                    throw new NonexistentEntityException("The materialConstruccion with id " + id + " no longer exists.");
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
            MaterialConstruccion materialConstruccion;
            try {
                materialConstruccion = em.getReference(MaterialConstruccion.class, id);
                materialConstruccion.getIDMaterialConstruccion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The materialConstruccion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Compra> compraCollectionOrphanCheck = materialConstruccion.getCompraCollection();
            for (Compra compraCollectionOrphanCheckCompra : compraCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This MaterialConstruccion (" + materialConstruccion + ") cannot be destroyed since the Compra " + compraCollectionOrphanCheckCompra + " in its compraCollection field has a non-nullable IDMaterialConstruccion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(materialConstruccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MaterialConstruccion> findMaterialConstruccionEntities() {
        return findMaterialConstruccionEntities(true, -1, -1);
    }

    public List<MaterialConstruccion> findMaterialConstruccionEntities(int maxResults, int firstResult) {
        return findMaterialConstruccionEntities(false, maxResults, firstResult);
    }

    private List<MaterialConstruccion> findMaterialConstruccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MaterialConstruccion.class));
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

    public MaterialConstruccion findMaterialConstruccion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MaterialConstruccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getMaterialConstruccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MaterialConstruccion> rt = cq.from(MaterialConstruccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
