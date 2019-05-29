/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.controladores;

import com.seidor.jmanteweb.persistencia.controladores.exceptions.NonexistentEntityException;
import com.seidor.jmanteweb.persistencia.controladores.exceptions.PreexistingEntityException;
import com.seidor.jmanteweb.persistencia.entidades.Visualcomponent;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.seidor.jmanteweb.persistencia.entidades.Visualthemes;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class VisualcomponentJpaController implements Serializable {

    public VisualcomponentJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Visualcomponent visualcomponent) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Visualthemes theme = visualcomponent.getTheme();
            if (theme != null) {
                theme = em.getReference(theme.getClass(), theme.getIdthemes());
                visualcomponent.setTheme(theme);
            }
            em.persist(visualcomponent);
            if (theme != null) {
                theme.getVisualcomponentList().add(visualcomponent);
                theme = em.merge(theme);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVisualcomponent(visualcomponent.getIdcomponent()) != null) {
                throw new PreexistingEntityException("Visualcomponent " + visualcomponent + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Visualcomponent visualcomponent) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Visualcomponent persistentVisualcomponent = em.find(Visualcomponent.class, visualcomponent.getIdcomponent());
            Visualthemes themeOld = persistentVisualcomponent.getTheme();
            Visualthemes themeNew = visualcomponent.getTheme();
            if (themeNew != null) {
                themeNew = em.getReference(themeNew.getClass(), themeNew.getIdthemes());
                visualcomponent.setTheme(themeNew);
            }
            visualcomponent = em.merge(visualcomponent);
            if (themeOld != null && !themeOld.equals(themeNew)) {
                themeOld.getVisualcomponentList().remove(visualcomponent);
                themeOld = em.merge(themeOld);
            }
            if (themeNew != null && !themeNew.equals(themeOld)) {
                themeNew.getVisualcomponentList().add(visualcomponent);
                themeNew = em.merge(themeNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = visualcomponent.getIdcomponent();
                if (findVisualcomponent(id) == null) {
                    throw new NonexistentEntityException("The visualcomponent with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Visualcomponent visualcomponent;
            try {
                visualcomponent = em.getReference(Visualcomponent.class, id);
                visualcomponent.getIdcomponent();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The visualcomponent with id " + id + " no longer exists.", enfe);
            }
            Visualthemes theme = visualcomponent.getTheme();
            if (theme != null) {
                theme.getVisualcomponentList().remove(visualcomponent);
                theme = em.merge(theme);
            }
            em.remove(visualcomponent);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Visualcomponent> findVisualcomponentEntities() {
        return findVisualcomponentEntities(true, -1, -1);
    }

    public List<Visualcomponent> findVisualcomponentEntities(int maxResults, int firstResult) {
        return findVisualcomponentEntities(false, maxResults, firstResult);
    }

    private List<Visualcomponent> findVisualcomponentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Visualcomponent.class));
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

    public Visualcomponent findVisualcomponent(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Visualcomponent.class, id);
        } finally {
            em.close();
        }
    }

    public int getVisualcomponentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Visualcomponent> rt = cq.from(Visualcomponent.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
