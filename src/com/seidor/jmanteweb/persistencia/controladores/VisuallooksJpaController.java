/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.controladores;

import com.seidor.jmanteweb.persistencia.controladores.exceptions.IllegalOrphanException;
import com.seidor.jmanteweb.persistencia.controladores.exceptions.NonexistentEntityException;
import com.seidor.jmanteweb.persistencia.controladores.exceptions.PreexistingEntityException;
import com.seidor.jmanteweb.persistencia.entidades.Visuallooks;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.seidor.jmanteweb.persistencia.entidades.Visualthemes;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class VisuallooksJpaController implements Serializable {

    public VisuallooksJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Visuallooks visuallooks) throws PreexistingEntityException, Exception {
        if (visuallooks.getVisualthemesList() == null) {
            visuallooks.setVisualthemesList(new ArrayList<Visualthemes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Visualthemes> attachedVisualthemesList = new ArrayList<Visualthemes>();
            for (Visualthemes visualthemesListVisualthemesToAttach : visuallooks.getVisualthemesList()) {
                visualthemesListVisualthemesToAttach = em.getReference(visualthemesListVisualthemesToAttach.getClass(), visualthemesListVisualthemesToAttach.getIdthemes());
                attachedVisualthemesList.add(visualthemesListVisualthemesToAttach);
            }
            visuallooks.setVisualthemesList(attachedVisualthemesList);
            em.persist(visuallooks);
            for (Visualthemes visualthemesListVisualthemes : visuallooks.getVisualthemesList()) {
                Visuallooks oldLookandfeelOfVisualthemesListVisualthemes = visualthemesListVisualthemes.getLookandfeel();
                visualthemesListVisualthemes.setLookandfeel(visuallooks);
                visualthemesListVisualthemes = em.merge(visualthemesListVisualthemes);
                if (oldLookandfeelOfVisualthemesListVisualthemes != null) {
                    oldLookandfeelOfVisualthemesListVisualthemes.getVisualthemesList().remove(visualthemesListVisualthemes);
                    oldLookandfeelOfVisualthemesListVisualthemes = em.merge(oldLookandfeelOfVisualthemesListVisualthemes);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVisuallooks(visuallooks.getLookandfeel()) != null) {
                throw new PreexistingEntityException("Visuallooks " + visuallooks + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Visuallooks visuallooks) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Visuallooks persistentVisuallooks = em.find(Visuallooks.class, visuallooks.getLookandfeel());
            List<Visualthemes> visualthemesListOld = persistentVisuallooks.getVisualthemesList();
            List<Visualthemes> visualthemesListNew = visuallooks.getVisualthemesList();
            List<String> illegalOrphanMessages = null;
            for (Visualthemes visualthemesListOldVisualthemes : visualthemesListOld) {
                if (!visualthemesListNew.contains(visualthemesListOldVisualthemes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Visualthemes " + visualthemesListOldVisualthemes + " since its lookandfeel field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Visualthemes> attachedVisualthemesListNew = new ArrayList<Visualthemes>();
            for (Visualthemes visualthemesListNewVisualthemesToAttach : visualthemesListNew) {
                visualthemesListNewVisualthemesToAttach = em.getReference(visualthemesListNewVisualthemesToAttach.getClass(), visualthemesListNewVisualthemesToAttach.getIdthemes());
                attachedVisualthemesListNew.add(visualthemesListNewVisualthemesToAttach);
            }
            visualthemesListNew = attachedVisualthemesListNew;
            visuallooks.setVisualthemesList(visualthemesListNew);
            visuallooks = em.merge(visuallooks);
            for (Visualthemes visualthemesListNewVisualthemes : visualthemesListNew) {
                if (!visualthemesListOld.contains(visualthemesListNewVisualthemes)) {
                    Visuallooks oldLookandfeelOfVisualthemesListNewVisualthemes = visualthemesListNewVisualthemes.getLookandfeel();
                    visualthemesListNewVisualthemes.setLookandfeel(visuallooks);
                    visualthemesListNewVisualthemes = em.merge(visualthemesListNewVisualthemes);
                    if (oldLookandfeelOfVisualthemesListNewVisualthemes != null && !oldLookandfeelOfVisualthemesListNewVisualthemes.equals(visuallooks)) {
                        oldLookandfeelOfVisualthemesListNewVisualthemes.getVisualthemesList().remove(visualthemesListNewVisualthemes);
                        oldLookandfeelOfVisualthemesListNewVisualthemes = em.merge(oldLookandfeelOfVisualthemesListNewVisualthemes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = visuallooks.getLookandfeel();
                if (findVisuallooks(id) == null) {
                    throw new NonexistentEntityException("The visuallooks with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Visuallooks visuallooks;
            try {
                visuallooks = em.getReference(Visuallooks.class, id);
                visuallooks.getLookandfeel();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The visuallooks with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Visualthemes> visualthemesListOrphanCheck = visuallooks.getVisualthemesList();
            for (Visualthemes visualthemesListOrphanCheckVisualthemes : visualthemesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Visuallooks (" + visuallooks + ") cannot be destroyed since the Visualthemes " + visualthemesListOrphanCheckVisualthemes + " in its visualthemesList field has a non-nullable lookandfeel field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(visuallooks);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Visuallooks> findVisuallooksEntities() {
        return findVisuallooksEntities(true, -1, -1);
    }

    public List<Visuallooks> findVisuallooksEntities(int maxResults, int firstResult) {
        return findVisuallooksEntities(false, maxResults, firstResult);
    }

    private List<Visuallooks> findVisuallooksEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Visuallooks.class));
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

    public Visuallooks findVisuallooks(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Visuallooks.class, id);
        } finally {
            em.close();
        }
    }

    public int getVisuallooksCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Visuallooks> rt = cq.from(Visuallooks.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
