/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.controladores;

import com.seidor.jmanteweb.persistencia.controladores.exceptions.NonexistentEntityException;
import com.seidor.jmanteweb.persistencia.controladores.exceptions.PreexistingEntityException;
import com.seidor.jmanteweb.persistencia.entidades.TipoVpn;
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
public class TipoVpnJpaController implements Serializable {

    public TipoVpnJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoVpn tipoVpn) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tipoVpn);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoVpn(tipoVpn.getTipoVpn()) != null) {
                throw new PreexistingEntityException("TipoVpn " + tipoVpn + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoVpn tipoVpn) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tipoVpn = em.merge(tipoVpn);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = tipoVpn.getTipoVpn();
                if (findTipoVpn(id) == null) {
                    throw new NonexistentEntityException("The tipoVpn with id " + id + " no longer exists.");
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
            TipoVpn tipoVpn;
            try {
                tipoVpn = em.getReference(TipoVpn.class, id);
                tipoVpn.getTipoVpn();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoVpn with id " + id + " no longer exists.", enfe);
            }
            em.remove(tipoVpn);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoVpn> findTipoVpnEntities() {
        return findTipoVpnEntities(true, -1, -1);
    }

    public List<TipoVpn> findTipoVpnEntities(int maxResults, int firstResult) {
        return findTipoVpnEntities(false, maxResults, firstResult);
    }

    private List<TipoVpn> findTipoVpnEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoVpn.class));
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

    public TipoVpn findTipoVpn(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoVpn.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoVpnCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoVpn> rt = cq.from(TipoVpn.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
