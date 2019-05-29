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
import com.seidor.jmanteweb.persistencia.entidades.Usuario;
import com.seidor.jmanteweb.persistencia.entidades.Visualizaciongrid;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class VisualizaciongridJpaController implements Serializable {

    public VisualizaciongridJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Visualizaciongrid visualizaciongrid) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario = visualizaciongrid.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getUsuario());
                visualizaciongrid.setUsuario(usuario);
            }
            em.persist(visualizaciongrid);
            if (usuario != null) {
                usuario.getVisualizaciongridList().add(visualizaciongrid);
                usuario = em.merge(usuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVisualizaciongrid(visualizaciongrid.getIdvisua()) != null) {
                throw new PreexistingEntityException("Visualizaciongrid " + visualizaciongrid + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Visualizaciongrid visualizaciongrid) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Visualizaciongrid persistentVisualizaciongrid = em.find(Visualizaciongrid.class, visualizaciongrid.getIdvisua());
            Usuario usuarioOld = persistentVisualizaciongrid.getUsuario();
            Usuario usuarioNew = visualizaciongrid.getUsuario();
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getUsuario());
                visualizaciongrid.setUsuario(usuarioNew);
            }
            visualizaciongrid = em.merge(visualizaciongrid);
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getVisualizaciongridList().remove(visualizaciongrid);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getVisualizaciongridList().add(visualizaciongrid);
                usuarioNew = em.merge(usuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = visualizaciongrid.getIdvisua();
                if (findVisualizaciongrid(id) == null) {
                    throw new NonexistentEntityException("The visualizaciongrid with id " + id + " no longer exists.");
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
            Visualizaciongrid visualizaciongrid;
            try {
                visualizaciongrid = em.getReference(Visualizaciongrid.class, id);
                visualizaciongrid.getIdvisua();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The visualizaciongrid with id " + id + " no longer exists.", enfe);
            }
            Usuario usuario = visualizaciongrid.getUsuario();
            if (usuario != null) {
                usuario.getVisualizaciongridList().remove(visualizaciongrid);
                usuario = em.merge(usuario);
            }
            em.remove(visualizaciongrid);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Visualizaciongrid> findVisualizaciongridEntities() {
        return findVisualizaciongridEntities(true, -1, -1);
    }

    public List<Visualizaciongrid> findVisualizaciongridEntities(int maxResults, int firstResult) {
        return findVisualizaciongridEntities(false, maxResults, firstResult);
    }

    private List<Visualizaciongrid> findVisualizaciongridEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Visualizaciongrid.class));
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

    public Visualizaciongrid findVisualizaciongrid(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Visualizaciongrid.class, id);
        } finally {
            em.close();
        }
    }

    public int getVisualizaciongridCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Visualizaciongrid> rt = cq.from(Visualizaciongrid.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
