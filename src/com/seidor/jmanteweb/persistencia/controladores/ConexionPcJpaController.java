/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.controladores;

import com.seidor.jmanteweb.persistencia.controladores.exceptions.NonexistentEntityException;
import com.seidor.jmanteweb.persistencia.controladores.exceptions.PreexistingEntityException;
import com.seidor.jmanteweb.persistencia.entidades.ConexionPc;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.seidor.jmanteweb.persistencia.entidades.ConexionRemota;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class ConexionPcJpaController implements Serializable {

    public ConexionPcJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConexionPc conexionPc) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ConexionRemota idConexRemota = conexionPc.getIdConexRemota();
            if (idConexRemota != null) {
                idConexRemota = em.getReference(idConexRemota.getClass(), idConexRemota.getId());
                conexionPc.setIdConexRemota(idConexRemota);
            }
            em.persist(conexionPc);
            if (idConexRemota != null) {
                idConexRemota.getConexionPcList().add(conexionPc);
                idConexRemota = em.merge(idConexRemota);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findConexionPc(conexionPc.getId()) != null) {
                throw new PreexistingEntityException("ConexionPc " + conexionPc + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ConexionPc conexionPc) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ConexionPc persistentConexionPc = em.find(ConexionPc.class, conexionPc.getId());
            ConexionRemota idConexRemotaOld = persistentConexionPc.getIdConexRemota();
            ConexionRemota idConexRemotaNew = conexionPc.getIdConexRemota();
            if (idConexRemotaNew != null) {
                idConexRemotaNew = em.getReference(idConexRemotaNew.getClass(), idConexRemotaNew.getId());
                conexionPc.setIdConexRemota(idConexRemotaNew);
            }
            conexionPc = em.merge(conexionPc);
            if (idConexRemotaOld != null && !idConexRemotaOld.equals(idConexRemotaNew)) {
                idConexRemotaOld.getConexionPcList().remove(conexionPc);
                idConexRemotaOld = em.merge(idConexRemotaOld);
            }
            if (idConexRemotaNew != null && !idConexRemotaNew.equals(idConexRemotaOld)) {
                idConexRemotaNew.getConexionPcList().add(conexionPc);
                idConexRemotaNew = em.merge(idConexRemotaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = conexionPc.getId();
                if (findConexionPc(id) == null) {
                    throw new NonexistentEntityException("The conexionPc with id " + id + " no longer exists.");
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
            ConexionPc conexionPc;
            try {
                conexionPc = em.getReference(ConexionPc.class, id);
                conexionPc.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The conexionPc with id " + id + " no longer exists.", enfe);
            }
            ConexionRemota idConexRemota = conexionPc.getIdConexRemota();
            if (idConexRemota != null) {
                idConexRemota.getConexionPcList().remove(conexionPc);
                idConexRemota = em.merge(idConexRemota);
            }
            em.remove(conexionPc);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ConexionPc> findConexionPcEntities() {
        return findConexionPcEntities(true, -1, -1);
    }

    public List<ConexionPc> findConexionPcEntities(int maxResults, int firstResult) {
        return findConexionPcEntities(false, maxResults, firstResult);
    }

    private List<ConexionPc> findConexionPcEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConexionPc.class));
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

    public ConexionPc findConexionPc(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConexionPc.class, id);
        } finally {
            em.close();
        }
    }

    public int getConexionPcCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConexionPc> rt = cq.from(ConexionPc.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
