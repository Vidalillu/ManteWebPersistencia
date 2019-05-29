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
import com.seidor.jmanteweb.persistencia.entidades.Componentemenu;
import com.seidor.jmanteweb.persistencia.entidades.Pantallaini;
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
public class PantallainiJpaController implements Serializable {

    public PantallainiJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
        
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pantallaini pantallaini) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Componentemenu componente = pantallaini.getComponente();
            if (componente != null) {
                componente = em.getReference(componente.getClass(), componente.getNombre());
                pantallaini.setComponente(componente);
            }
            Usuario usuario = pantallaini.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getUsuario());
                pantallaini.setUsuario(usuario);
            }
            em.persist(pantallaini);
            if (componente != null) {
                componente.getPantallainiList().add(pantallaini);
                componente = em.merge(componente);
            }
            if (usuario != null) {
                usuario.getPantallainiList().add(pantallaini);
                usuario = em.merge(usuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPantallaini(pantallaini.getId()) != null) {
                throw new PreexistingEntityException("Pantallaini " + pantallaini + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pantallaini pantallaini) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pantallaini persistentPantallaini = em.find(Pantallaini.class, pantallaini.getId());
            Componentemenu componenteOld = persistentPantallaini.getComponente();
            Componentemenu componenteNew = pantallaini.getComponente();
            Usuario usuarioOld = persistentPantallaini.getUsuario();
            Usuario usuarioNew = pantallaini.getUsuario();
            if (componenteNew != null) {
                componenteNew = em.getReference(componenteNew.getClass(), componenteNew.getNombre());
                pantallaini.setComponente(componenteNew);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getUsuario());
                pantallaini.setUsuario(usuarioNew);
            }
            pantallaini = em.merge(pantallaini);
            if (componenteOld != null && !componenteOld.equals(componenteNew)) {
                componenteOld.getPantallainiList().remove(pantallaini);
                componenteOld = em.merge(componenteOld);
            }
            if (componenteNew != null && !componenteNew.equals(componenteOld)) {
                componenteNew.getPantallainiList().add(pantallaini);
                componenteNew = em.merge(componenteNew);
            }
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getPantallainiList().remove(pantallaini);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getPantallainiList().add(pantallaini);
                usuarioNew = em.merge(usuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = pantallaini.getId();
                if (findPantallaini(id) == null) {
                    throw new NonexistentEntityException("The pantallaini with id " + id + " no longer exists.");
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
            Pantallaini pantallaini;
            try {
                pantallaini = em.getReference(Pantallaini.class, id);
                pantallaini.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pantallaini with id " + id + " no longer exists.", enfe);
            }
            Componentemenu componente = pantallaini.getComponente();
            if (componente != null) {
                componente.getPantallainiList().remove(pantallaini);
                componente = em.merge(componente);
            }
            Usuario usuario = pantallaini.getUsuario();
            if (usuario != null) {
                usuario.getPantallainiList().remove(pantallaini);
                usuario = em.merge(usuario);
            }
            em.remove(pantallaini);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pantallaini> findPantallainiEntities() {
        return findPantallainiEntities(true, -1, -1);
    }

    public List<Pantallaini> findPantallainiEntities(int maxResults, int firstResult) {
        return findPantallainiEntities(false, maxResults, firstResult);
    }

    private List<Pantallaini> findPantallainiEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pantallaini.class));
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

    public Pantallaini findPantallaini(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pantallaini.class, id);
        } finally {
            em.close();
        }
    }

    public int getPantallainiCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pantallaini> rt = cq.from(Pantallaini.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
