/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.controladores;

import com.seidor.jmanteweb.persistencia.controladores.exceptions.NonexistentEntityException;
import com.seidor.jmanteweb.persistencia.controladores.exceptions.PreexistingEntityException;
import com.seidor.jmanteweb.persistencia.entidades.TiposClienteOld;
import java.io.Serializable;
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
public class TiposClienteOldJpaController implements Serializable {

    public TiposClienteOldJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TiposClienteOld tiposClienteOld) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tiposClienteOld);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTiposClienteOld(tiposClienteOld.getTipocliente()) != null) {
                throw new PreexistingEntityException("TiposClienteOld " + tiposClienteOld + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TiposClienteOld tiposClienteOld) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tiposClienteOld = em.merge(tiposClienteOld);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = tiposClienteOld.getTipocliente();
                if (findTiposClienteOld(id) == null) {
                    throw new NonexistentEntityException("The tiposClienteOld with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TiposClienteOld tiposClienteOld;
            try {
                tiposClienteOld = em.getReference(TiposClienteOld.class, id);
                tiposClienteOld.getTipocliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tiposClienteOld with id " + id + " no longer exists.", enfe);
            }
            em.remove(tiposClienteOld);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TiposClienteOld> findTiposClienteOldEntities() {
        return findTiposClienteOldEntities(true, -1, -1);
    }

    public List<TiposClienteOld> findTiposClienteOldEntities(int maxResults, int firstResult) {
        return findTiposClienteOldEntities(false, maxResults, firstResult);
    }

    private List<TiposClienteOld> findTiposClienteOldEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TiposClienteOld.class));
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

    public TiposClienteOld findTiposClienteOld(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TiposClienteOld.class, id);
        } finally {
            em.close();
        }
    }

    public int getTiposClienteOldCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TiposClienteOld> rt = cq.from(TiposClienteOld.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
