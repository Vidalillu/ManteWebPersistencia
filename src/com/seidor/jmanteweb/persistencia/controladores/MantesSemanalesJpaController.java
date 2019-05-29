/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.controladores;

import com.seidor.jmanteweb.persistencia.controladores.exceptions.NonexistentEntityException;
import com.seidor.jmanteweb.persistencia.controladores.exceptions.PreexistingEntityException;
import com.seidor.jmanteweb.persistencia.entidades.MantesSemanales;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author AMARTEL
 */
public class MantesSemanalesJpaController implements Serializable {

    public MantesSemanalesJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MantesSemanales mantesSemanales) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(mantesSemanales);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMantesSemanales(mantesSemanales.getId()) != null) {
                throw new PreexistingEntityException("MantesSemanales " + mantesSemanales + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MantesSemanales mantesSemanales) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            mantesSemanales = em.merge(mantesSemanales);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = mantesSemanales.getId();
                if (findMantesSemanales(id) == null) {
                    throw new NonexistentEntityException("The mantesSemanales with id " + id + " no longer exists.");
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
            MantesSemanales mantesSemanales;
            try {
                mantesSemanales = em.getReference(MantesSemanales.class, id);
                mantesSemanales.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mantesSemanales with id " + id + " no longer exists.", enfe);
            }
            em.remove(mantesSemanales);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MantesSemanales> findMantesSemanalesEntities() {
        return findMantesSemanalesEntities(true, -1, -1);
    }

    public List<MantesSemanales> findMantesSemanalesEntities(int maxResults, int firstResult) {
        return findMantesSemanalesEntities(false, maxResults, firstResult);
    }

    private List<MantesSemanales> findMantesSemanalesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MantesSemanales.class));
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

    public MantesSemanales findMantesSemanales(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MantesSemanales.class, id);
        } finally {
            em.close();
        }
    }

    public int getMantesSemanalesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MantesSemanales> rt = cq.from(MantesSemanales.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
