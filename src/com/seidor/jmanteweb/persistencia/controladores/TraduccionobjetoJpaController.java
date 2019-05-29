/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.controladores;

import com.seidor.jmanteweb.persistencia.controladores.exceptions.NonexistentEntityException;
import com.seidor.jmanteweb.persistencia.controladores.exceptions.PreexistingEntityException;
import com.seidor.jmanteweb.persistencia.entidades.Traduccionobjeto;
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
public class TraduccionobjetoJpaController implements Serializable {

    public TraduccionobjetoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Traduccionobjeto traduccionobjeto) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(traduccionobjeto);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTraduccionobjeto(traduccionobjeto.getIdtrad()) != null) {
                throw new PreexistingEntityException("Traduccionobjeto " + traduccionobjeto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Traduccionobjeto traduccionobjeto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            traduccionobjeto = em.merge(traduccionobjeto);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = traduccionobjeto.getIdtrad();
                if (findTraduccionobjeto(id) == null) {
                    throw new NonexistentEntityException("The traduccionobjeto with id " + id + " no longer exists.");
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
            Traduccionobjeto traduccionobjeto;
            try {
                traduccionobjeto = em.getReference(Traduccionobjeto.class, id);
                traduccionobjeto.getIdtrad();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The traduccionobjeto with id " + id + " no longer exists.", enfe);
            }
            em.remove(traduccionobjeto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Traduccionobjeto> findTraduccionobjetoEntities() {
        return findTraduccionobjetoEntities(true, -1, -1);
    }

    public List<Traduccionobjeto> findTraduccionobjetoEntities(int maxResults, int firstResult) {
        return findTraduccionobjetoEntities(false, maxResults, firstResult);
    }

    private List<Traduccionobjeto> findTraduccionobjetoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Traduccionobjeto.class));
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

    public Traduccionobjeto findTraduccionobjeto(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Traduccionobjeto.class, id);
        } finally {
            em.close();
        }
    }

    public int getTraduccionobjetoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Traduccionobjeto> rt = cq.from(Traduccionobjeto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
