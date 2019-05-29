/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.controladores;

import com.seidor.jmanteweb.persistencia.controladores.exceptions.IllegalOrphanException;
import com.seidor.jmanteweb.persistencia.controladores.exceptions.NonexistentEntityException;
import com.seidor.jmanteweb.persistencia.controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.seidor.jmanteweb.persistencia.entidades.ConexionPcsSeidor;
import com.seidor.jmanteweb.persistencia.entidades.PcSeidor;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class PcSeidorJpaController implements Serializable {

    public PcSeidorJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PcSeidor pcSeidor) throws PreexistingEntityException, Exception {
        if (pcSeidor.getConexionPcsSeidorList() == null) {
            pcSeidor.setConexionPcsSeidorList(new ArrayList<ConexionPcsSeidor>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ConexionPcsSeidor> attachedConexionPcsSeidorList = new ArrayList<ConexionPcsSeidor>();
            for (ConexionPcsSeidor conexionPcsSeidorListConexionPcsSeidorToAttach : pcSeidor.getConexionPcsSeidorList()) {
                conexionPcsSeidorListConexionPcsSeidorToAttach = em.getReference(conexionPcsSeidorListConexionPcsSeidorToAttach.getClass(), conexionPcsSeidorListConexionPcsSeidorToAttach.getId());
                attachedConexionPcsSeidorList.add(conexionPcsSeidorListConexionPcsSeidorToAttach);
            }
            pcSeidor.setConexionPcsSeidorList(attachedConexionPcsSeidorList);
            em.persist(pcSeidor);
            for (ConexionPcsSeidor conexionPcsSeidorListConexionPcsSeidor : pcSeidor.getConexionPcsSeidorList()) {
                PcSeidor oldPcOfConexionPcsSeidorListConexionPcsSeidor = conexionPcsSeidorListConexionPcsSeidor.getPc();
                conexionPcsSeidorListConexionPcsSeidor.setPc(pcSeidor);
                conexionPcsSeidorListConexionPcsSeidor = em.merge(conexionPcsSeidorListConexionPcsSeidor);
                if (oldPcOfConexionPcsSeidorListConexionPcsSeidor != null) {
                    oldPcOfConexionPcsSeidorListConexionPcsSeidor.getConexionPcsSeidorList().remove(conexionPcsSeidorListConexionPcsSeidor);
                    oldPcOfConexionPcsSeidorListConexionPcsSeidor = em.merge(oldPcOfConexionPcsSeidorListConexionPcsSeidor);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPcSeidor(pcSeidor.getPc()) != null) {
                throw new PreexistingEntityException("PcSeidor " + pcSeidor + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PcSeidor pcSeidor) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PcSeidor persistentPcSeidor = em.find(PcSeidor.class, pcSeidor.getPc());
            List<ConexionPcsSeidor> conexionPcsSeidorListOld = persistentPcSeidor.getConexionPcsSeidorList();
            List<ConexionPcsSeidor> conexionPcsSeidorListNew = pcSeidor.getConexionPcsSeidorList();
            List<String> illegalOrphanMessages = null;
            for (ConexionPcsSeidor conexionPcsSeidorListOldConexionPcsSeidor : conexionPcsSeidorListOld) {
                if (!conexionPcsSeidorListNew.contains(conexionPcsSeidorListOldConexionPcsSeidor)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ConexionPcsSeidor " + conexionPcsSeidorListOldConexionPcsSeidor + " since its pc field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<ConexionPcsSeidor> attachedConexionPcsSeidorListNew = new ArrayList<ConexionPcsSeidor>();
            for (ConexionPcsSeidor conexionPcsSeidorListNewConexionPcsSeidorToAttach : conexionPcsSeidorListNew) {
                conexionPcsSeidorListNewConexionPcsSeidorToAttach = em.getReference(conexionPcsSeidorListNewConexionPcsSeidorToAttach.getClass(), conexionPcsSeidorListNewConexionPcsSeidorToAttach.getId());
                attachedConexionPcsSeidorListNew.add(conexionPcsSeidorListNewConexionPcsSeidorToAttach);
            }
            conexionPcsSeidorListNew = attachedConexionPcsSeidorListNew;
            pcSeidor.setConexionPcsSeidorList(conexionPcsSeidorListNew);
            pcSeidor = em.merge(pcSeidor);
            for (ConexionPcsSeidor conexionPcsSeidorListNewConexionPcsSeidor : conexionPcsSeidorListNew) {
                if (!conexionPcsSeidorListOld.contains(conexionPcsSeidorListNewConexionPcsSeidor)) {
                    PcSeidor oldPcOfConexionPcsSeidorListNewConexionPcsSeidor = conexionPcsSeidorListNewConexionPcsSeidor.getPc();
                    conexionPcsSeidorListNewConexionPcsSeidor.setPc(pcSeidor);
                    conexionPcsSeidorListNewConexionPcsSeidor = em.merge(conexionPcsSeidorListNewConexionPcsSeidor);
                    if (oldPcOfConexionPcsSeidorListNewConexionPcsSeidor != null && !oldPcOfConexionPcsSeidorListNewConexionPcsSeidor.equals(pcSeidor)) {
                        oldPcOfConexionPcsSeidorListNewConexionPcsSeidor.getConexionPcsSeidorList().remove(conexionPcsSeidorListNewConexionPcsSeidor);
                        oldPcOfConexionPcsSeidorListNewConexionPcsSeidor = em.merge(oldPcOfConexionPcsSeidorListNewConexionPcsSeidor);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = pcSeidor.getPc();
                if (findPcSeidor(id) == null) {
                    throw new NonexistentEntityException("The pcSeidor with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PcSeidor pcSeidor;
            try {
                pcSeidor = em.getReference(PcSeidor.class, id);
                pcSeidor.getPc();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pcSeidor with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ConexionPcsSeidor> conexionPcsSeidorListOrphanCheck = pcSeidor.getConexionPcsSeidorList();
            for (ConexionPcsSeidor conexionPcsSeidorListOrphanCheckConexionPcsSeidor : conexionPcsSeidorListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PcSeidor (" + pcSeidor + ") cannot be destroyed since the ConexionPcsSeidor " + conexionPcsSeidorListOrphanCheckConexionPcsSeidor + " in its conexionPcsSeidorList field has a non-nullable pc field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(pcSeidor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PcSeidor> findPcSeidorEntities() {
        return findPcSeidorEntities(true, -1, -1);
    }

    public List<PcSeidor> findPcSeidorEntities(int maxResults, int firstResult) {
        return findPcSeidorEntities(false, maxResults, firstResult);
    }

    private List<PcSeidor> findPcSeidorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PcSeidor.class));
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

    public PcSeidor findPcSeidor(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PcSeidor.class, id);
        } finally {
            em.close();
        }
    }

    public int getPcSeidorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PcSeidor> rt = cq.from(PcSeidor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
