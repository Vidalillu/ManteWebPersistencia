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
import com.seidor.jmanteweb.persistencia.entidades.Guardiapalex;
import com.seidor.jmanteweb.persistencia.entidades.Tarifapalex;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class TarifapalexJpaController implements Serializable {

    public TarifapalexJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tarifapalex tarifapalex) throws PreexistingEntityException, Exception {
        if (tarifapalex.getGuardiapalexList() == null) {
            tarifapalex.setGuardiapalexList(new ArrayList<Guardiapalex>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Guardiapalex> attachedGuardiapalexList = new ArrayList<Guardiapalex>();
            for (Guardiapalex guardiapalexListGuardiapalexToAttach : tarifapalex.getGuardiapalexList()) {
                guardiapalexListGuardiapalexToAttach = em.getReference(guardiapalexListGuardiapalexToAttach.getClass(), guardiapalexListGuardiapalexToAttach.getIdguardia());
                attachedGuardiapalexList.add(guardiapalexListGuardiapalexToAttach);
            }
            tarifapalex.setGuardiapalexList(attachedGuardiapalexList);
            em.persist(tarifapalex);
            for (Guardiapalex guardiapalexListGuardiapalex : tarifapalex.getGuardiapalexList()) {
                Tarifapalex oldIdtarifaOfGuardiapalexListGuardiapalex = guardiapalexListGuardiapalex.getIdtarifa();
                guardiapalexListGuardiapalex.setIdtarifa(tarifapalex);
                guardiapalexListGuardiapalex = em.merge(guardiapalexListGuardiapalex);
                if (oldIdtarifaOfGuardiapalexListGuardiapalex != null) {
                    oldIdtarifaOfGuardiapalexListGuardiapalex.getGuardiapalexList().remove(guardiapalexListGuardiapalex);
                    oldIdtarifaOfGuardiapalexListGuardiapalex = em.merge(oldIdtarifaOfGuardiapalexListGuardiapalex);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTarifapalex(tarifapalex.getId()) != null) {
                throw new PreexistingEntityException("Tarifapalex " + tarifapalex + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tarifapalex tarifapalex) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tarifapalex persistentTarifapalex = em.find(Tarifapalex.class, tarifapalex.getId());
            List<Guardiapalex> guardiapalexListOld = persistentTarifapalex.getGuardiapalexList();
            List<Guardiapalex> guardiapalexListNew = tarifapalex.getGuardiapalexList();
            List<String> illegalOrphanMessages = null;
            for (Guardiapalex guardiapalexListOldGuardiapalex : guardiapalexListOld) {
                if (!guardiapalexListNew.contains(guardiapalexListOldGuardiapalex)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Guardiapalex " + guardiapalexListOldGuardiapalex + " since its idtarifa field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Guardiapalex> attachedGuardiapalexListNew = new ArrayList<Guardiapalex>();
            for (Guardiapalex guardiapalexListNewGuardiapalexToAttach : guardiapalexListNew) {
                guardiapalexListNewGuardiapalexToAttach = em.getReference(guardiapalexListNewGuardiapalexToAttach.getClass(), guardiapalexListNewGuardiapalexToAttach.getIdguardia());
                attachedGuardiapalexListNew.add(guardiapalexListNewGuardiapalexToAttach);
            }
            guardiapalexListNew = attachedGuardiapalexListNew;
            tarifapalex.setGuardiapalexList(guardiapalexListNew);
            tarifapalex = em.merge(tarifapalex);
            for (Guardiapalex guardiapalexListNewGuardiapalex : guardiapalexListNew) {
                if (!guardiapalexListOld.contains(guardiapalexListNewGuardiapalex)) {
                    Tarifapalex oldIdtarifaOfGuardiapalexListNewGuardiapalex = guardiapalexListNewGuardiapalex.getIdtarifa();
                    guardiapalexListNewGuardiapalex.setIdtarifa(tarifapalex);
                    guardiapalexListNewGuardiapalex = em.merge(guardiapalexListNewGuardiapalex);
                    if (oldIdtarifaOfGuardiapalexListNewGuardiapalex != null && !oldIdtarifaOfGuardiapalexListNewGuardiapalex.equals(tarifapalex)) {
                        oldIdtarifaOfGuardiapalexListNewGuardiapalex.getGuardiapalexList().remove(guardiapalexListNewGuardiapalex);
                        oldIdtarifaOfGuardiapalexListNewGuardiapalex = em.merge(oldIdtarifaOfGuardiapalexListNewGuardiapalex);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = tarifapalex.getId();
                if (findTarifapalex(id) == null) {
                    throw new NonexistentEntityException("The tarifapalex with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tarifapalex tarifapalex;
            try {
                tarifapalex = em.getReference(Tarifapalex.class, id);
                tarifapalex.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tarifapalex with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Guardiapalex> guardiapalexListOrphanCheck = tarifapalex.getGuardiapalexList();
            for (Guardiapalex guardiapalexListOrphanCheckGuardiapalex : guardiapalexListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tarifapalex (" + tarifapalex + ") cannot be destroyed since the Guardiapalex " + guardiapalexListOrphanCheckGuardiapalex + " in its guardiapalexList field has a non-nullable idtarifa field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tarifapalex);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tarifapalex> findTarifapalexEntities() {
        return findTarifapalexEntities(true, -1, -1);
    }

    public List<Tarifapalex> findTarifapalexEntities(int maxResults, int firstResult) {
        return findTarifapalexEntities(false, maxResults, firstResult);
    }

    private List<Tarifapalex> findTarifapalexEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tarifapalex.class));
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

    public Tarifapalex findTarifapalex(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tarifapalex.class, id);
        } finally {
            em.close();
        }
    }

    public int getTarifapalexCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tarifapalex> rt = cq.from(Tarifapalex.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
