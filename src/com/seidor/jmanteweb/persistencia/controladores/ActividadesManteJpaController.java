/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.controladores;

import com.seidor.jmanteweb.persistencia.controladores.exceptions.NonexistentEntityException;
import com.seidor.jmanteweb.persistencia.controladores.exceptions.PreexistingEntityException;
import com.seidor.jmanteweb.persistencia.entidades.ActividadesMante;
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
public class ActividadesManteJpaController implements Serializable {

    public ActividadesManteJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ActividadesMante actividadesMante) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(actividadesMante);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findActividadesMante(actividadesMante.getId()) != null) {
                throw new PreexistingEntityException("ActividadesMante " + actividadesMante + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ActividadesMante actividadesMante) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            actividadesMante = em.merge(actividadesMante);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = actividadesMante.getId();
                if (findActividadesMante(id) == null) {
                    throw new NonexistentEntityException("The actividadesMante with id " + id + " no longer exists.");
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
            ActividadesMante actividadesMante;
            try {
                actividadesMante = em.getReference(ActividadesMante.class, id);
                actividadesMante.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The actividadesMante with id " + id + " no longer exists.", enfe);
            }
            em.remove(actividadesMante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ActividadesMante> findActividadesManteEntities() {
        return findActividadesManteEntities(true, -1, -1);
    }

    public List<ActividadesMante> findActividadesManteEntities(int maxResults, int firstResult) {
        return findActividadesManteEntities(false, maxResults, firstResult);
    }

    private List<ActividadesMante> findActividadesManteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ActividadesMante.class));
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

    public ActividadesMante findActividadesMante(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ActividadesMante.class, id);
        } finally {
            em.close();
        }
    }

    public int getActividadesManteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ActividadesMante> rt = cq.from(ActividadesMante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
