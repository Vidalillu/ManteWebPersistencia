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
import com.seidor.jmanteweb.persistencia.entidades.Modem;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class ModemJpaController implements Serializable {

    public ModemJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Modem modem) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ConexionRemota idConexRemota = modem.getIdConexRemota();
            if (idConexRemota != null) {
                idConexRemota = em.getReference(idConexRemota.getClass(), idConexRemota.getId());
                modem.setIdConexRemota(idConexRemota);
            }
            em.persist(modem);
            if (idConexRemota != null) {
                idConexRemota.getModemList().add(modem);
                idConexRemota = em.merge(idConexRemota);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findModem(modem.getId()) != null) {
                throw new PreexistingEntityException("Modem " + modem + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Modem modem) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Modem persistentModem = em.find(Modem.class, modem.getId());
            ConexionRemota idConexRemotaOld = persistentModem.getIdConexRemota();
            ConexionRemota idConexRemotaNew = modem.getIdConexRemota();
            if (idConexRemotaNew != null) {
                idConexRemotaNew = em.getReference(idConexRemotaNew.getClass(), idConexRemotaNew.getId());
                modem.setIdConexRemota(idConexRemotaNew);
            }
            modem = em.merge(modem);
            if (idConexRemotaOld != null && !idConexRemotaOld.equals(idConexRemotaNew)) {
                idConexRemotaOld.getModemList().remove(modem);
                idConexRemotaOld = em.merge(idConexRemotaOld);
            }
            if (idConexRemotaNew != null && !idConexRemotaNew.equals(idConexRemotaOld)) {
                idConexRemotaNew.getModemList().add(modem);
                idConexRemotaNew = em.merge(idConexRemotaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = modem.getId();
                if (findModem(id) == null) {
                    throw new NonexistentEntityException("The modem with id " + id + " no longer exists.");
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
            Modem modem;
            try {
                modem = em.getReference(Modem.class, id);
                modem.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The modem with id " + id + " no longer exists.", enfe);
            }
            ConexionRemota idConexRemota = modem.getIdConexRemota();
            if (idConexRemota != null) {
                idConexRemota.getModemList().remove(modem);
                idConexRemota = em.merge(idConexRemota);
            }
            em.remove(modem);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Modem> findModemEntities() {
        return findModemEntities(true, -1, -1);
    }

    public List<Modem> findModemEntities(int maxResults, int firstResult) {
        return findModemEntities(false, maxResults, firstResult);
    }

    private List<Modem> findModemEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Modem.class));
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

    public Modem findModem(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Modem.class, id);
        } finally {
            em.close();
        }
    }

    public int getModemCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Modem> rt = cq.from(Modem.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
