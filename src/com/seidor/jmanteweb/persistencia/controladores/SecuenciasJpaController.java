/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.controladores;

import com.seidor.jmanteweb.persistencia.controladores.exceptions.NonexistentEntityException;
import com.seidor.jmanteweb.persistencia.controladores.exceptions.PreexistingEntityException;
import com.seidor.jmanteweb.persistencia.entidades.Secuencias;
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
public class SecuenciasJpaController implements Serializable {

    public SecuenciasJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistenciaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Secuencias secuencias) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(secuencias);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSecuencias(secuencias.getSecuencia()) != null) {
                throw new PreexistingEntityException("Secuencias " + secuencias + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Secuencias secuencias) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            secuencias = em.merge(secuencias);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = secuencias.getSecuencia();
                if (findSecuencias(id) == null) {
                    throw new NonexistentEntityException("The secuencias with id " + id + " no longer exists.");
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
            Secuencias secuencias;
            try {
                secuencias = em.getReference(Secuencias.class, id);
                secuencias.getSecuencia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The secuencias with id " + id + " no longer exists.", enfe);
            }
            em.remove(secuencias);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Secuencias> findSecuenciasEntities() {
        return findSecuenciasEntities(true, -1, -1);
    }

    public List<Secuencias> findSecuenciasEntities(int maxResults, int firstResult) {
        return findSecuenciasEntities(false, maxResults, firstResult);
    }

    private List<Secuencias> findSecuenciasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Secuencias.class));
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

    public Secuencias findSecuencias(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Secuencias.class, id);
        } finally {
            em.close();
        }
    }

    public int getSecuenciasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Secuencias> rt = cq.from(Secuencias.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
