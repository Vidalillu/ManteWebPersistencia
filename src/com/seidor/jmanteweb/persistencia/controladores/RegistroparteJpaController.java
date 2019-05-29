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
import com.seidor.jmanteweb.persistencia.entidades.Partes;
import com.seidor.jmanteweb.persistencia.entidades.Registroparte;
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
public class RegistroparteJpaController implements Serializable {

    public RegistroparteJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Registroparte registroparte) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Partes numParte = registroparte.getNumParte();
            if (numParte != null) {
                numParte = em.getReference(numParte.getClass(), numParte.getNumParte());
                registroparte.setNumParte(numParte);
            }
            Usuario usuario = registroparte.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getUsuario());
                registroparte.setUsuario(usuario);
            }
            em.persist(registroparte);
            if (numParte != null) {
                numParte.getRegistroparteList().add(registroparte);
                numParte = em.merge(numParte);
            }
            if (usuario != null) {
                usuario.getRegistroparteList().add(registroparte);
                usuario = em.merge(usuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRegistroparte(registroparte.getId()) != null) {
                throw new PreexistingEntityException("Registroparte " + registroparte + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Registroparte registroparte) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Registroparte persistentRegistroparte = em.find(Registroparte.class, registroparte.getId());
            Partes numParteOld = persistentRegistroparte.getNumParte();
            Partes numParteNew = registroparte.getNumParte();
            Usuario usuarioOld = persistentRegistroparte.getUsuario();
            Usuario usuarioNew = registroparte.getUsuario();
            if (numParteNew != null) {
                numParteNew = em.getReference(numParteNew.getClass(), numParteNew.getNumParte());
                registroparte.setNumParte(numParteNew);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getUsuario());
                registroparte.setUsuario(usuarioNew);
            }
            registroparte = em.merge(registroparte);
            if (numParteOld != null && !numParteOld.equals(numParteNew)) {
                numParteOld.getRegistroparteList().remove(registroparte);
                numParteOld = em.merge(numParteOld);
            }
            if (numParteNew != null && !numParteNew.equals(numParteOld)) {
                numParteNew.getRegistroparteList().add(registroparte);
                numParteNew = em.merge(numParteNew);
            }
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getRegistroparteList().remove(registroparte);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getRegistroparteList().add(registroparte);
                usuarioNew = em.merge(usuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = registroparte.getId();
                if (findRegistroparte(id) == null) {
                    throw new NonexistentEntityException("The registroparte with id " + id + " no longer exists.");
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
            Registroparte registroparte;
            try {
                registroparte = em.getReference(Registroparte.class, id);
                registroparte.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The registroparte with id " + id + " no longer exists.", enfe);
            }
            Partes numParte = registroparte.getNumParte();
            if (numParte != null) {
                numParte.getRegistroparteList().remove(registroparte);
                numParte = em.merge(numParte);
            }
            Usuario usuario = registroparte.getUsuario();
            if (usuario != null) {
                usuario.getRegistroparteList().remove(registroparte);
                usuario = em.merge(usuario);
            }
            em.remove(registroparte);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Registroparte> findRegistroparteEntities() {
        return findRegistroparteEntities(true, -1, -1);
    }

    public List<Registroparte> findRegistroparteEntities(int maxResults, int firstResult) {
        return findRegistroparteEntities(false, maxResults, firstResult);
    }

    private List<Registroparte> findRegistroparteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Registroparte.class));
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

    public Registroparte findRegistroparte(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Registroparte.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegistroparteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Registroparte> rt = cq.from(Registroparte.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
