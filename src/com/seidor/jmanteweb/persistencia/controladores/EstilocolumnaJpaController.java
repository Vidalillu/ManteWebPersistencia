/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.controladores;

import com.seidor.jmanteweb.persistencia.controladores.exceptions.NonexistentEntityException;
import com.seidor.jmanteweb.persistencia.controladores.exceptions.PreexistingEntityException;
import com.seidor.jmanteweb.persistencia.entidades.Estilocolumna;
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
public class EstilocolumnaJpaController implements Serializable {

    public EstilocolumnaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estilocolumna estilocolumna) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(estilocolumna);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstilocolumna(estilocolumna.getId()) != null) {
                throw new PreexistingEntityException("Estilocolumna " + estilocolumna + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estilocolumna estilocolumna) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            estilocolumna = em.merge(estilocolumna);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = estilocolumna.getId();
                if (findEstilocolumna(id) == null) {
                    throw new NonexistentEntityException("The estilocolumna with id " + id + " no longer exists.");
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
            Estilocolumna estilocolumna;
            try {
                estilocolumna = em.getReference(Estilocolumna.class, id);
                estilocolumna.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estilocolumna with id " + id + " no longer exists.", enfe);
            }
            em.remove(estilocolumna);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estilocolumna> findEstilocolumnaEntities() {
        return findEstilocolumnaEntities(true, -1, -1);
    }

    public List<Estilocolumna> findEstilocolumnaEntities(int maxResults, int firstResult) {
        return findEstilocolumnaEntities(false, maxResults, firstResult);
    }

    private List<Estilocolumna> findEstilocolumnaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estilocolumna.class));
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

    public Estilocolumna findEstilocolumna(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estilocolumna.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstilocolumnaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estilocolumna> rt = cq.from(Estilocolumna.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
