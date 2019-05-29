/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.controladores;

import com.seidor.jmanteweb.persistencia.controladores.exceptions.NonexistentEntityException;
import com.seidor.jmanteweb.persistencia.controladores.exceptions.PreexistingEntityException;
import com.seidor.jmanteweb.persistencia.entidades.ConexionPcsSeidor;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.seidor.jmanteweb.persistencia.entidades.ConexionRemota;
import com.seidor.jmanteweb.persistencia.entidades.PcSeidor;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class ConexionPcsSeidorJpaController implements Serializable {

    public ConexionPcsSeidorJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConexionPcsSeidor conexionPcsSeidor) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ConexionRemota idConexion = conexionPcsSeidor.getIdConexion();
            if (idConexion != null) {
                idConexion = em.getReference(idConexion.getClass(), idConexion.getId());
                conexionPcsSeidor.setIdConexion(idConexion);
            }
            PcSeidor pc = conexionPcsSeidor.getPc();
            if (pc != null) {
                pc = em.getReference(pc.getClass(), pc.getPc());
                conexionPcsSeidor.setPc(pc);
            }
            em.persist(conexionPcsSeidor);
            if (idConexion != null) {
                idConexion.getConexionPcsSeidorList().add(conexionPcsSeidor);
                idConexion = em.merge(idConexion);
            }
            if (pc != null) {
                pc.getConexionPcsSeidorList().add(conexionPcsSeidor);
                pc = em.merge(pc);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findConexionPcsSeidor(conexionPcsSeidor.getId()) != null) {
                throw new PreexistingEntityException("ConexionPcsSeidor " + conexionPcsSeidor + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ConexionPcsSeidor conexionPcsSeidor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ConexionPcsSeidor persistentConexionPcsSeidor = em.find(ConexionPcsSeidor.class, conexionPcsSeidor.getId());
            ConexionRemota idConexionOld = persistentConexionPcsSeidor.getIdConexion();
            ConexionRemota idConexionNew = conexionPcsSeidor.getIdConexion();
            PcSeidor pcOld = persistentConexionPcsSeidor.getPc();
            PcSeidor pcNew = conexionPcsSeidor.getPc();
            if (idConexionNew != null) {
                idConexionNew = em.getReference(idConexionNew.getClass(), idConexionNew.getId());
                conexionPcsSeidor.setIdConexion(idConexionNew);
            }
            if (pcNew != null) {
                pcNew = em.getReference(pcNew.getClass(), pcNew.getPc());
                conexionPcsSeidor.setPc(pcNew);
            }
            conexionPcsSeidor = em.merge(conexionPcsSeidor);
            if (idConexionOld != null && !idConexionOld.equals(idConexionNew)) {
                idConexionOld.getConexionPcsSeidorList().remove(conexionPcsSeidor);
                idConexionOld = em.merge(idConexionOld);
            }
            if (idConexionNew != null && !idConexionNew.equals(idConexionOld)) {
                idConexionNew.getConexionPcsSeidorList().add(conexionPcsSeidor);
                idConexionNew = em.merge(idConexionNew);
            }
            if (pcOld != null && !pcOld.equals(pcNew)) {
                pcOld.getConexionPcsSeidorList().remove(conexionPcsSeidor);
                pcOld = em.merge(pcOld);
            }
            if (pcNew != null && !pcNew.equals(pcOld)) {
                pcNew.getConexionPcsSeidorList().add(conexionPcsSeidor);
                pcNew = em.merge(pcNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = conexionPcsSeidor.getId();
                if (findConexionPcsSeidor(id) == null) {
                    throw new NonexistentEntityException("The conexionPcsSeidor with id " + id + " no longer exists.");
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
            ConexionPcsSeidor conexionPcsSeidor;
            try {
                conexionPcsSeidor = em.getReference(ConexionPcsSeidor.class, id);
                conexionPcsSeidor.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The conexionPcsSeidor with id " + id + " no longer exists.", enfe);
            }
            ConexionRemota idConexion = conexionPcsSeidor.getIdConexion();
            if (idConexion != null) {
                idConexion.getConexionPcsSeidorList().remove(conexionPcsSeidor);
                idConexion = em.merge(idConexion);
            }
            PcSeidor pc = conexionPcsSeidor.getPc();
            if (pc != null) {
                pc.getConexionPcsSeidorList().remove(conexionPcsSeidor);
                pc = em.merge(pc);
            }
            em.remove(conexionPcsSeidor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ConexionPcsSeidor> findConexionPcsSeidorEntities() {
        return findConexionPcsSeidorEntities(true, -1, -1);
    }

    public List<ConexionPcsSeidor> findConexionPcsSeidorEntities(int maxResults, int firstResult) {
        return findConexionPcsSeidorEntities(false, maxResults, firstResult);
    }

    private List<ConexionPcsSeidor> findConexionPcsSeidorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConexionPcsSeidor.class));
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

    public ConexionPcsSeidor findConexionPcsSeidor(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConexionPcsSeidor.class, id);
        } finally {
            em.close();
        }
    }

    public int getConexionPcsSeidorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConexionPcsSeidor> rt = cq.from(ConexionPcsSeidor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
