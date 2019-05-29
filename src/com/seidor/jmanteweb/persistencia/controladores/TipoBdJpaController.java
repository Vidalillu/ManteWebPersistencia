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
import com.seidor.jmanteweb.persistencia.entidades.InfoBd;
import com.seidor.jmanteweb.persistencia.entidades.TipoBd;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class TipoBdJpaController implements Serializable {

    public TipoBdJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoBd tipoBd) throws PreexistingEntityException, Exception {
        if (tipoBd.getInfoBdList() == null) {
            tipoBd.setInfoBdList(new ArrayList<InfoBd>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<InfoBd> attachedInfoBdList = new ArrayList<InfoBd>();
            for (InfoBd infoBdListInfoBdToAttach : tipoBd.getInfoBdList()) {
                infoBdListInfoBdToAttach = em.getReference(infoBdListInfoBdToAttach.getClass(), infoBdListInfoBdToAttach.getId());
                attachedInfoBdList.add(infoBdListInfoBdToAttach);
            }
            tipoBd.setInfoBdList(attachedInfoBdList);
            em.persist(tipoBd);
            for (InfoBd infoBdListInfoBd : tipoBd.getInfoBdList()) {
                TipoBd oldTipoBdOfInfoBdListInfoBd = infoBdListInfoBd.getTipoBd();
                infoBdListInfoBd.setTipoBd(tipoBd);
                infoBdListInfoBd = em.merge(infoBdListInfoBd);
                if (oldTipoBdOfInfoBdListInfoBd != null) {
                    oldTipoBdOfInfoBdListInfoBd.getInfoBdList().remove(infoBdListInfoBd);
                    oldTipoBdOfInfoBdListInfoBd = em.merge(oldTipoBdOfInfoBdListInfoBd);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoBd(tipoBd.getTipoBd()) != null) {
                throw new PreexistingEntityException("TipoBd " + tipoBd + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoBd tipoBd) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoBd persistentTipoBd = em.find(TipoBd.class, tipoBd.getTipoBd());
            List<InfoBd> infoBdListOld = persistentTipoBd.getInfoBdList();
            List<InfoBd> infoBdListNew = tipoBd.getInfoBdList();
            List<InfoBd> attachedInfoBdListNew = new ArrayList<InfoBd>();
            for (InfoBd infoBdListNewInfoBdToAttach : infoBdListNew) {
                infoBdListNewInfoBdToAttach = em.getReference(infoBdListNewInfoBdToAttach.getClass(), infoBdListNewInfoBdToAttach.getId());
                attachedInfoBdListNew.add(infoBdListNewInfoBdToAttach);
            }
            infoBdListNew = attachedInfoBdListNew;
            tipoBd.setInfoBdList(infoBdListNew);
            tipoBd = em.merge(tipoBd);
            for (InfoBd infoBdListOldInfoBd : infoBdListOld) {
                if (!infoBdListNew.contains(infoBdListOldInfoBd)) {
                    infoBdListOldInfoBd.setTipoBd(null);
                    infoBdListOldInfoBd = em.merge(infoBdListOldInfoBd);
                }
            }
            for (InfoBd infoBdListNewInfoBd : infoBdListNew) {
                if (!infoBdListOld.contains(infoBdListNewInfoBd)) {
                    TipoBd oldTipoBdOfInfoBdListNewInfoBd = infoBdListNewInfoBd.getTipoBd();
                    infoBdListNewInfoBd.setTipoBd(tipoBd);
                    infoBdListNewInfoBd = em.merge(infoBdListNewInfoBd);
                    if (oldTipoBdOfInfoBdListNewInfoBd != null && !oldTipoBdOfInfoBdListNewInfoBd.equals(tipoBd)) {
                        oldTipoBdOfInfoBdListNewInfoBd.getInfoBdList().remove(infoBdListNewInfoBd);
                        oldTipoBdOfInfoBdListNewInfoBd = em.merge(oldTipoBdOfInfoBdListNewInfoBd);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = tipoBd.getTipoBd();
                if (findTipoBd(id) == null) {
                    throw new NonexistentEntityException("The tipoBd with id " + id + " no longer exists.");
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
            TipoBd tipoBd;
            try {
                tipoBd = em.getReference(TipoBd.class, id);
                tipoBd.getTipoBd();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoBd with id " + id + " no longer exists.", enfe);
            }
            List<InfoBd> infoBdList = tipoBd.getInfoBdList();
            for (InfoBd infoBdListInfoBd : infoBdList) {
                infoBdListInfoBd.setTipoBd(null);
                infoBdListInfoBd = em.merge(infoBdListInfoBd);
            }
            em.remove(tipoBd);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoBd> findTipoBdEntities() {
        return findTipoBdEntities(true, -1, -1);
    }

    public List<TipoBd> findTipoBdEntities(int maxResults, int firstResult) {
        return findTipoBdEntities(false, maxResults, firstResult);
    }

    private List<TipoBd> findTipoBdEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoBd.class));
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

    public TipoBd findTipoBd(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoBd.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoBdCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoBd> rt = cq.from(TipoBd.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
