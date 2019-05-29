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
import com.seidor.jmanteweb.persistencia.entidades.Tarea;
import com.seidor.jmanteweb.persistencia.entidades.Usuario;
import com.seidor.jmanteweb.persistencia.entidades.UsuarioTarea;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class UsuarioTareaJpaController implements Serializable {

    public UsuarioTareaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UsuarioTarea usuarioTarea) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tarea idtarea = usuarioTarea.getIdtarea();
            if (idtarea != null) {
                idtarea = em.getReference(idtarea.getClass(), idtarea.getId());
                usuarioTarea.setIdtarea(idtarea);
            }
            Usuario usuarioasignado = usuarioTarea.getUsuarioasignado();
            if (usuarioasignado != null) {
                usuarioasignado = em.getReference(usuarioasignado.getClass(), usuarioasignado.getUsuario());
                usuarioTarea.setUsuarioasignado(usuarioasignado);
            }
            em.persist(usuarioTarea);
            if (idtarea != null) {
                idtarea.getUsuarioTareaList().add(usuarioTarea);
                idtarea = em.merge(idtarea);
            }
            if (usuarioasignado != null) {
                usuarioasignado.getUsuarioTareaList().add(usuarioTarea);
                usuarioasignado = em.merge(usuarioasignado);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuarioTarea(usuarioTarea.getId()) != null) {
                throw new PreexistingEntityException("UsuarioTarea " + usuarioTarea + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UsuarioTarea usuarioTarea) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UsuarioTarea persistentUsuarioTarea = em.find(UsuarioTarea.class, usuarioTarea.getId());
            Tarea idtareaOld = persistentUsuarioTarea.getIdtarea();
            Tarea idtareaNew = usuarioTarea.getIdtarea();
            Usuario usuarioasignadoOld = persistentUsuarioTarea.getUsuarioasignado();
            Usuario usuarioasignadoNew = usuarioTarea.getUsuarioasignado();
            if (idtareaNew != null) {
                idtareaNew = em.getReference(idtareaNew.getClass(), idtareaNew.getId());
                usuarioTarea.setIdtarea(idtareaNew);
            }
            if (usuarioasignadoNew != null) {
                usuarioasignadoNew = em.getReference(usuarioasignadoNew.getClass(), usuarioasignadoNew.getUsuario());
                usuarioTarea.setUsuarioasignado(usuarioasignadoNew);
            }
            usuarioTarea = em.merge(usuarioTarea);
            if (idtareaOld != null && !idtareaOld.equals(idtareaNew)) {
                idtareaOld.getUsuarioTareaList().remove(usuarioTarea);
                idtareaOld = em.merge(idtareaOld);
            }
            if (idtareaNew != null && !idtareaNew.equals(idtareaOld)) {
                idtareaNew.getUsuarioTareaList().add(usuarioTarea);
                idtareaNew = em.merge(idtareaNew);
            }
            if (usuarioasignadoOld != null && !usuarioasignadoOld.equals(usuarioasignadoNew)) {
                usuarioasignadoOld.getUsuarioTareaList().remove(usuarioTarea);
                usuarioasignadoOld = em.merge(usuarioasignadoOld);
            }
            if (usuarioasignadoNew != null && !usuarioasignadoNew.equals(usuarioasignadoOld)) {
                usuarioasignadoNew.getUsuarioTareaList().add(usuarioTarea);
                usuarioasignadoNew = em.merge(usuarioasignadoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = usuarioTarea.getId();
                if (findUsuarioTarea(id) == null) {
                    throw new NonexistentEntityException("The usuarioTarea with id " + id + " no longer exists.");
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
            UsuarioTarea usuarioTarea;
            try {
                usuarioTarea = em.getReference(UsuarioTarea.class, id);
                usuarioTarea.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarioTarea with id " + id + " no longer exists.", enfe);
            }
            Tarea idtarea = usuarioTarea.getIdtarea();
            if (idtarea != null) {
                idtarea.getUsuarioTareaList().remove(usuarioTarea);
                idtarea = em.merge(idtarea);
            }
            Usuario usuarioasignado = usuarioTarea.getUsuarioasignado();
            if (usuarioasignado != null) {
                usuarioasignado.getUsuarioTareaList().remove(usuarioTarea);
                usuarioasignado = em.merge(usuarioasignado);
            }
            em.remove(usuarioTarea);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UsuarioTarea> findUsuarioTareaEntities() {
        return findUsuarioTareaEntities(true, -1, -1);
    }

    public List<UsuarioTarea> findUsuarioTareaEntities(int maxResults, int firstResult) {
        return findUsuarioTareaEntities(false, maxResults, firstResult);
    }

    private List<UsuarioTarea> findUsuarioTareaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UsuarioTarea.class));
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

    public UsuarioTarea findUsuarioTarea(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UsuarioTarea.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioTareaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UsuarioTarea> rt = cq.from(UsuarioTarea.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
