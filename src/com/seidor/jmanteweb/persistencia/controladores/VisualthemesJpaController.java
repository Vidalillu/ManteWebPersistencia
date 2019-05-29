/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.controladores;

import com.seidor.jmanteweb.persistencia.controladores.exceptions.IllegalOrphanException;
import com.seidor.jmanteweb.persistencia.controladores.exceptions.NonexistentEntityException;
import com.seidor.jmanteweb.persistencia.controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.seidor.jmanteweb.persistencia.entidades.Visuallooks;
import com.seidor.jmanteweb.persistencia.entidades.Visualcomponent;
import com.seidor.jmanteweb.persistencia.entidades.Visualthemes;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class VisualthemesJpaController implements Serializable {

    public VisualthemesJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Visualthemes visualthemes) throws PreexistingEntityException, Exception {
        if (visualthemes.getVisualcomponentList() == null) {
            visualthemes.setVisualcomponentList(new ArrayList<Visualcomponent>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Visuallooks lookandfeel = visualthemes.getLookandfeel();
            if (lookandfeel != null) {
                lookandfeel = em.getReference(lookandfeel.getClass(), lookandfeel.getLookandfeel());
                visualthemes.setLookandfeel(lookandfeel);
            }
            List<Visualcomponent> attachedVisualcomponentList = new ArrayList<Visualcomponent>();
            for (Visualcomponent visualcomponentListVisualcomponentToAttach : visualthemes.getVisualcomponentList()) {
                visualcomponentListVisualcomponentToAttach = em.getReference(visualcomponentListVisualcomponentToAttach.getClass(), visualcomponentListVisualcomponentToAttach.getIdcomponent());
                attachedVisualcomponentList.add(visualcomponentListVisualcomponentToAttach);
            }
            visualthemes.setVisualcomponentList(attachedVisualcomponentList);
            em.persist(visualthemes);
            if (lookandfeel != null) {
                lookandfeel.getVisualthemesList().add(visualthemes);
                lookandfeel = em.merge(lookandfeel);
            }
            for (Visualcomponent visualcomponentListVisualcomponent : visualthemes.getVisualcomponentList()) {
                Visualthemes oldThemeOfVisualcomponentListVisualcomponent = visualcomponentListVisualcomponent.getTheme();
                visualcomponentListVisualcomponent.setTheme(visualthemes);
                visualcomponentListVisualcomponent = em.merge(visualcomponentListVisualcomponent);
                if (oldThemeOfVisualcomponentListVisualcomponent != null) {
                    oldThemeOfVisualcomponentListVisualcomponent.getVisualcomponentList().remove(visualcomponentListVisualcomponent);
                    oldThemeOfVisualcomponentListVisualcomponent = em.merge(oldThemeOfVisualcomponentListVisualcomponent);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVisualthemes(visualthemes.getIdthemes()) != null) {
                throw new PreexistingEntityException("Visualthemes " + visualthemes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Visualthemes visualthemes) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Visualthemes persistentVisualthemes = em.find(Visualthemes.class, visualthemes.getIdthemes());
            Visuallooks lookandfeelOld = persistentVisualthemes.getLookandfeel();
            Visuallooks lookandfeelNew = visualthemes.getLookandfeel();
            List<Visualcomponent> visualcomponentListOld = persistentVisualthemes.getVisualcomponentList();
            List<Visualcomponent> visualcomponentListNew = visualthemes.getVisualcomponentList();
            List<String> illegalOrphanMessages = null;
            for (Visualcomponent visualcomponentListOldVisualcomponent : visualcomponentListOld) {
                if (!visualcomponentListNew.contains(visualcomponentListOldVisualcomponent)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Visualcomponent " + visualcomponentListOldVisualcomponent + " since its theme field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (lookandfeelNew != null) {
                lookandfeelNew = em.getReference(lookandfeelNew.getClass(), lookandfeelNew.getLookandfeel());
                visualthemes.setLookandfeel(lookandfeelNew);
            }
            List<Visualcomponent> attachedVisualcomponentListNew = new ArrayList<Visualcomponent>();
            for (Visualcomponent visualcomponentListNewVisualcomponentToAttach : visualcomponentListNew) {
                visualcomponentListNewVisualcomponentToAttach = em.getReference(visualcomponentListNewVisualcomponentToAttach.getClass(), visualcomponentListNewVisualcomponentToAttach.getIdcomponent());
                attachedVisualcomponentListNew.add(visualcomponentListNewVisualcomponentToAttach);
            }
            visualcomponentListNew = attachedVisualcomponentListNew;
            visualthemes.setVisualcomponentList(visualcomponentListNew);
            visualthemes = em.merge(visualthemes);
            if (lookandfeelOld != null && !lookandfeelOld.equals(lookandfeelNew)) {
                lookandfeelOld.getVisualthemesList().remove(visualthemes);
                lookandfeelOld = em.merge(lookandfeelOld);
            }
            if (lookandfeelNew != null && !lookandfeelNew.equals(lookandfeelOld)) {
                lookandfeelNew.getVisualthemesList().add(visualthemes);
                lookandfeelNew = em.merge(lookandfeelNew);
            }
            for (Visualcomponent visualcomponentListNewVisualcomponent : visualcomponentListNew) {
                if (!visualcomponentListOld.contains(visualcomponentListNewVisualcomponent)) {
                    Visualthemes oldThemeOfVisualcomponentListNewVisualcomponent = visualcomponentListNewVisualcomponent.getTheme();
                    visualcomponentListNewVisualcomponent.setTheme(visualthemes);
                    visualcomponentListNewVisualcomponent = em.merge(visualcomponentListNewVisualcomponent);
                    if (oldThemeOfVisualcomponentListNewVisualcomponent != null && !oldThemeOfVisualcomponentListNewVisualcomponent.equals(visualthemes)) {
                        oldThemeOfVisualcomponentListNewVisualcomponent.getVisualcomponentList().remove(visualcomponentListNewVisualcomponent);
                        oldThemeOfVisualcomponentListNewVisualcomponent = em.merge(oldThemeOfVisualcomponentListNewVisualcomponent);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = visualthemes.getIdthemes();
                if (findVisualthemes(id) == null) {
                    throw new NonexistentEntityException("The visualthemes with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Visualthemes visualthemes;
            try {
                visualthemes = em.getReference(Visualthemes.class, id);
                visualthemes.getIdthemes();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The visualthemes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Visualcomponent> visualcomponentListOrphanCheck = visualthemes.getVisualcomponentList();
            for (Visualcomponent visualcomponentListOrphanCheckVisualcomponent : visualcomponentListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Visualthemes (" + visualthemes + ") cannot be destroyed since the Visualcomponent " + visualcomponentListOrphanCheckVisualcomponent + " in its visualcomponentList field has a non-nullable theme field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Visuallooks lookandfeel = visualthemes.getLookandfeel();
            if (lookandfeel != null) {
                lookandfeel.getVisualthemesList().remove(visualthemes);
                lookandfeel = em.merge(lookandfeel);
            }
            em.remove(visualthemes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Visualthemes> findVisualthemesEntities() {
        return findVisualthemesEntities(true, -1, -1);
    }

    public List<Visualthemes> findVisualthemesEntities(int maxResults, int firstResult) {
        return findVisualthemesEntities(false, maxResults, firstResult);
    }

    private List<Visualthemes> findVisualthemesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Visualthemes.class));
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

    public Visualthemes findVisualthemes(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Visualthemes.class, id);
        } finally {
            em.close();
        }
    }

    public int getVisualthemesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Visualthemes> rt = cq.from(Visualthemes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
