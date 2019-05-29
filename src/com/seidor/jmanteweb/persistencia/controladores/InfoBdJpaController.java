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
import com.seidor.jmanteweb.persistencia.entidades.Clientes;
import com.seidor.jmanteweb.persistencia.entidades.InfoBd;
import com.seidor.jmanteweb.persistencia.entidades.TipoBd;
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
public class InfoBdJpaController implements Serializable {

    public InfoBdJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(InfoBd infoBd) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Clientes clienteOrphanCheck = infoBd.getCliente();
        if (clienteOrphanCheck != null) {
            InfoBd oldInfoBdOfCliente = clienteOrphanCheck.getInfoBd();
            if (oldInfoBdOfCliente != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Clientes " + clienteOrphanCheck + " already has an item of type InfoBd whose cliente column cannot be null. Please make another selection for the cliente field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes cliente = infoBd.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getCliente());
                infoBd.setCliente(cliente);
            }
            TipoBd tipoBd = infoBd.getTipoBd();
            if (tipoBd != null) {
                tipoBd = em.getReference(tipoBd.getClass(), tipoBd.getTipoBd());
                infoBd.setTipoBd(tipoBd);
            }
            em.persist(infoBd);
            if (cliente != null) {
                cliente.setInfoBd(infoBd);
                cliente = em.merge(cliente);
            }
            if (tipoBd != null) {
                tipoBd.getInfoBdList().add(infoBd);
                tipoBd = em.merge(tipoBd);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findInfoBd(infoBd.getId()) != null) {
                throw new PreexistingEntityException("InfoBd " + infoBd + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(InfoBd infoBd) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            InfoBd persistentInfoBd = em.find(InfoBd.class, infoBd.getId());
            Clientes clienteOld = persistentInfoBd.getCliente();
            Clientes clienteNew = infoBd.getCliente();
            TipoBd tipoBdOld = persistentInfoBd.getTipoBd();
            TipoBd tipoBdNew = infoBd.getTipoBd();
            List<String> illegalOrphanMessages = null;
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                InfoBd oldInfoBdOfCliente = clienteNew.getInfoBd();
                if (oldInfoBdOfCliente != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Clientes " + clienteNew + " already has an item of type InfoBd whose cliente column cannot be null. Please make another selection for the cliente field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getCliente());
                infoBd.setCliente(clienteNew);
            }
            if (tipoBdNew != null) {
                tipoBdNew = em.getReference(tipoBdNew.getClass(), tipoBdNew.getTipoBd());
                infoBd.setTipoBd(tipoBdNew);
            }
            infoBd = em.merge(infoBd);
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                clienteOld.setInfoBd(null);
                clienteOld = em.merge(clienteOld);
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                clienteNew.setInfoBd(infoBd);
                clienteNew = em.merge(clienteNew);
            }
            if (tipoBdOld != null && !tipoBdOld.equals(tipoBdNew)) {
                tipoBdOld.getInfoBdList().remove(infoBd);
                tipoBdOld = em.merge(tipoBdOld);
            }
            if (tipoBdNew != null && !tipoBdNew.equals(tipoBdOld)) {
                tipoBdNew.getInfoBdList().add(infoBd);
                tipoBdNew = em.merge(tipoBdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = infoBd.getId();
                if (findInfoBd(id) == null) {
                    throw new NonexistentEntityException("The infoBd with id " + id + " no longer exists.");
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
            InfoBd infoBd;
            try {
                infoBd = em.getReference(InfoBd.class, id);
                infoBd.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The infoBd with id " + id + " no longer exists.", enfe);
            }
            Clientes cliente = infoBd.getCliente();
            if (cliente != null) {
                cliente.setInfoBd(null);
                cliente = em.merge(cliente);
            }
            TipoBd tipoBd = infoBd.getTipoBd();
            if (tipoBd != null) {
                tipoBd.getInfoBdList().remove(infoBd);
                tipoBd = em.merge(tipoBd);
            }
            em.remove(infoBd);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<InfoBd> findInfoBdEntities() {
        return findInfoBdEntities(true, -1, -1);
    }

    public List<InfoBd> findInfoBdEntities(int maxResults, int firstResult) {
        return findInfoBdEntities(false, maxResults, firstResult);
    }

    private List<InfoBd> findInfoBdEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(InfoBd.class));
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

    public InfoBd findInfoBd(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(InfoBd.class, id);
        } finally {
            em.close();
        }
    }

    public int getInfoBdCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<InfoBd> rt = cq.from(InfoBd.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
