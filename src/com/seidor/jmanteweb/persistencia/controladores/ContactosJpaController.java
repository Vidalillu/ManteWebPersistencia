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
import com.seidor.jmanteweb.persistencia.entidades.Contactos;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class ContactosJpaController implements Serializable {

    public ContactosJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Contactos contactos) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes cliente = contactos.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getCliente());
                contactos.setCliente(cliente);
            }
            em.persist(contactos);
            if (cliente != null) {
                cliente.getContactosList().add(contactos);
                cliente = em.merge(cliente);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findContactos(contactos.getId()) != null) {
                throw new PreexistingEntityException("Contactos " + contactos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Contactos contactos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Contactos persistentContactos = em.find(Contactos.class, contactos.getId());
            Clientes clienteOld = persistentContactos.getCliente();
            Clientes clienteNew = contactos.getCliente();
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getCliente());
                contactos.setCliente(clienteNew);
            }
            contactos = em.merge(contactos);
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                clienteOld.getContactosList().remove(contactos);
                clienteOld = em.merge(clienteOld);
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                clienteNew.getContactosList().add(contactos);
                clienteNew = em.merge(clienteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = contactos.getId();
                if (findContactos(id) == null) {
                    throw new NonexistentEntityException("The contactos with id " + id + " no longer exists.");
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
            Contactos contactos;
            try {
                contactos = em.getReference(Contactos.class, id);
                contactos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contactos with id " + id + " no longer exists.", enfe);
            }
            Clientes cliente = contactos.getCliente();
            if (cliente != null) {
                cliente.getContactosList().remove(contactos);
                cliente = em.merge(cliente);
            }
            em.remove(contactos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Contactos> findContactosEntities() {
        return findContactosEntities(true, -1, -1);
    }

    public List<Contactos> findContactosEntities(int maxResults, int firstResult) {
        return findContactosEntities(false, maxResults, firstResult);
    }

    private List<Contactos> findContactosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Contactos.class));
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

    public Contactos findContactos(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Contactos.class, id);
        } finally {
            em.close();
        }
    }

    public int getContactosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Contactos> rt = cq.from(Contactos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
