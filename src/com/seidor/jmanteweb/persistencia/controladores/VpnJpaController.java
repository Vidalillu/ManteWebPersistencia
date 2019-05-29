/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.controladores;

import com.seidor.jmanteweb.persistencia.controladores.exceptions.NonexistentEntityException;
import com.seidor.jmanteweb.persistencia.controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.seidor.jmanteweb.persistencia.entidades.ConexionRemota;
import com.seidor.jmanteweb.persistencia.entidades.Vpn;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class VpnJpaController implements Serializable {

    public VpnJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vpn vpn) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ConexionRemota idConexRemota = vpn.getIdConexRemota();
            if (idConexRemota != null) {
                idConexRemota = em.getReference(idConexRemota.getClass(), idConexRemota.getId());
                vpn.setIdConexRemota(idConexRemota);
            }
            em.persist(vpn);
            if (idConexRemota != null) {
                idConexRemota.getVpnList().add(vpn);
                idConexRemota = em.merge(idConexRemota);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVpn(vpn.getId()) != null) {
                throw new PreexistingEntityException("Vpn " + vpn + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vpn vpn) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vpn persistentVpn = em.find(Vpn.class, vpn.getId());
            ConexionRemota idConexRemotaOld = persistentVpn.getIdConexRemota();
            ConexionRemota idConexRemotaNew = vpn.getIdConexRemota();
            if (idConexRemotaNew != null) {
                idConexRemotaNew = em.getReference(idConexRemotaNew.getClass(), idConexRemotaNew.getId());
                vpn.setIdConexRemota(idConexRemotaNew);
            }
            vpn = em.merge(vpn);
            if (idConexRemotaOld != null && !idConexRemotaOld.equals(idConexRemotaNew)) {
                idConexRemotaOld.getVpnList().remove(vpn);
                idConexRemotaOld = em.merge(idConexRemotaOld);
            }
            if (idConexRemotaNew != null && !idConexRemotaNew.equals(idConexRemotaOld)) {
                idConexRemotaNew.getVpnList().add(vpn);
                idConexRemotaNew = em.merge(idConexRemotaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = vpn.getId();
                if (findVpn(id) == null) {
                    throw new NonexistentEntityException("The vpn with id " + id + " no longer exists.");
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
            Vpn vpn;
            try {
                vpn = em.getReference(Vpn.class, id);
                vpn.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vpn with id " + id + " no longer exists.", enfe);
            }
            ConexionRemota idConexRemota = vpn.getIdConexRemota();
            if (idConexRemota != null) {
                idConexRemota.getVpnList().remove(vpn);
                idConexRemota = em.merge(idConexRemota);
            }
            em.remove(vpn);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vpn> findVpnEntities() {
        return findVpnEntities(true, -1, -1);
    }

    public List<Vpn> findVpnEntities(int maxResults, int firstResult) {
        return findVpnEntities(false, maxResults, firstResult);
    }

    private List<Vpn> findVpnEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vpn.class));
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

    public Vpn findVpn(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vpn.class, id);
        } finally {
            em.close();
        }
    }

    public int getVpnCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vpn> rt = cq.from(Vpn.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
