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
import com.seidor.jmanteweb.persistencia.entidades.Clientes;
import com.seidor.jmanteweb.persistencia.entidades.RegistroRevision;
import com.seidor.jmanteweb.persistencia.entidades.Usuario;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class RegistroRevisionJpaController implements Serializable {

    public RegistroRevisionJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RegistroRevision registroRevision) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes cliente = registroRevision.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getCliente());
                registroRevision.setCliente(cliente);
            }
            Usuario usuario = registroRevision.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getUsuario());
                registroRevision.setUsuario(usuario);
            }
            em.persist(registroRevision);
            if (cliente != null) {
                cliente.getRegistroRevisionList().add(registroRevision);
                cliente = em.merge(cliente);
            }
            if (usuario != null) {
                usuario.getRegistroRevisionList().add(registroRevision);
                usuario = em.merge(usuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRegistroRevision(registroRevision.getId()) != null) {
                throw new PreexistingEntityException("RegistroRevision " + registroRevision + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RegistroRevision registroRevision) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RegistroRevision persistentRegistroRevision = em.find(RegistroRevision.class, registroRevision.getId());
            Clientes clienteOld = persistentRegistroRevision.getCliente();
            Clientes clienteNew = registroRevision.getCliente();
            Usuario usuarioOld = persistentRegistroRevision.getUsuario();
            Usuario usuarioNew = registroRevision.getUsuario();
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getCliente());
                registroRevision.setCliente(clienteNew);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getUsuario());
                registroRevision.setUsuario(usuarioNew);
            }
            registroRevision = em.merge(registroRevision);
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                clienteOld.getRegistroRevisionList().remove(registroRevision);
                clienteOld = em.merge(clienteOld);
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                clienteNew.getRegistroRevisionList().add(registroRevision);
                clienteNew = em.merge(clienteNew);
            }
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getRegistroRevisionList().remove(registroRevision);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getRegistroRevisionList().add(registroRevision);
                usuarioNew = em.merge(usuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = registroRevision.getId();
                if (findRegistroRevision(id) == null) {
                    throw new NonexistentEntityException("The registroRevision with id " + id + " no longer exists.");
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
            RegistroRevision registroRevision;
            try {
                registroRevision = em.getReference(RegistroRevision.class, id);
                registroRevision.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The registroRevision with id " + id + " no longer exists.", enfe);
            }
            Clientes cliente = registroRevision.getCliente();
            if (cliente != null) {
                cliente.getRegistroRevisionList().remove(registroRevision);
                cliente = em.merge(cliente);
            }
            Usuario usuario = registroRevision.getUsuario();
            if (usuario != null) {
                usuario.getRegistroRevisionList().remove(registroRevision);
                usuario = em.merge(usuario);
            }
            em.remove(registroRevision);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RegistroRevision> findRegistroRevisionEntities() {
        return findRegistroRevisionEntities(true, -1, -1);
    }

    public List<RegistroRevision> findRegistroRevisionEntities(int maxResults, int firstResult) {
        return findRegistroRevisionEntities(false, maxResults, firstResult);
    }

    private List<RegistroRevision> findRegistroRevisionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RegistroRevision.class));
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

    public RegistroRevision findRegistroRevision(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RegistroRevision.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegistroRevisionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RegistroRevision> rt = cq.from(RegistroRevision.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
