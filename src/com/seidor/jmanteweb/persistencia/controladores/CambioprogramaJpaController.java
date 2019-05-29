/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.controladores;

import com.seidor.jmanteweb.persistencia.controladores.exceptions.NonexistentEntityException;
import com.seidor.jmanteweb.persistencia.controladores.exceptions.PreexistingEntityException;
import com.seidor.jmanteweb.persistencia.entidades.Cambioprograma;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.seidor.jmanteweb.persistencia.entidades.Componentemenu;
import com.seidor.jmanteweb.persistencia.entidades.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class CambioprogramaJpaController implements Serializable {

    public CambioprogramaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cambioprograma cambioprograma) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Componentemenu pantalla = cambioprograma.getPantalla();
            if (pantalla != null) {
                pantalla = em.getReference(pantalla.getClass(), pantalla.getNombre());
                cambioprograma.setPantalla(pantalla);
            }
            Usuario usuario = cambioprograma.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getUsuario());
                cambioprograma.setUsuario(usuario);
            }
            em.persist(cambioprograma);
            if (pantalla != null) {
                pantalla.getCambioprogramaList().add(cambioprograma);
                pantalla = em.merge(pantalla);
            }
            if (usuario != null) {
                usuario.getCambioprogramaList().add(cambioprograma);
                usuario = em.merge(usuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCambioprograma(cambioprograma.getIdcambio()) != null) {
                throw new PreexistingEntityException("Cambioprograma " + cambioprograma + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cambioprograma cambioprograma) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cambioprograma persistentCambioprograma = em.find(Cambioprograma.class, cambioprograma.getIdcambio());
            Componentemenu pantallaOld = persistentCambioprograma.getPantalla();
            Componentemenu pantallaNew = cambioprograma.getPantalla();
            Usuario usuarioOld = persistentCambioprograma.getUsuario();
            Usuario usuarioNew = cambioprograma.getUsuario();
            if (pantallaNew != null) {
                pantallaNew = em.getReference(pantallaNew.getClass(), pantallaNew.getNombre());
                cambioprograma.setPantalla(pantallaNew);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getUsuario());
                cambioprograma.setUsuario(usuarioNew);
            }
            cambioprograma = em.merge(cambioprograma);
            if (pantallaOld != null && !pantallaOld.equals(pantallaNew)) {
                pantallaOld.getCambioprogramaList().remove(cambioprograma);
                pantallaOld = em.merge(pantallaOld);
            }
            if (pantallaNew != null && !pantallaNew.equals(pantallaOld)) {
                pantallaNew.getCambioprogramaList().add(cambioprograma);
                pantallaNew = em.merge(pantallaNew);
            }
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getCambioprogramaList().remove(cambioprograma);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getCambioprogramaList().add(cambioprograma);
                usuarioNew = em.merge(usuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = cambioprograma.getIdcambio();
                if (findCambioprograma(id) == null) {
                    throw new NonexistentEntityException("The cambioprograma with id " + id + " no longer exists.");
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
            Cambioprograma cambioprograma;
            try {
                cambioprograma = em.getReference(Cambioprograma.class, id);
                cambioprograma.getIdcambio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cambioprograma with id " + id + " no longer exists.", enfe);
            }
            Componentemenu pantalla = cambioprograma.getPantalla();
            if (pantalla != null) {
                pantalla.getCambioprogramaList().remove(cambioprograma);
                pantalla = em.merge(pantalla);
            }
            Usuario usuario = cambioprograma.getUsuario();
            if (usuario != null) {
                usuario.getCambioprogramaList().remove(cambioprograma);
                usuario = em.merge(usuario);
            }
            em.remove(cambioprograma);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cambioprograma> findCambioprogramaEntities() {
        return findCambioprogramaEntities(true, -1, -1);
    }

    public List<Cambioprograma> findCambioprogramaEntities(int maxResults, int firstResult) {
        return findCambioprogramaEntities(false, maxResults, firstResult);
    }

    private List<Cambioprograma> findCambioprogramaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cambioprograma.class));
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

    public Cambioprograma findCambioprograma(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cambioprograma.class, id);
        } finally {
            em.close();
        }
    }

    public int getCambioprogramaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cambioprograma> rt = cq.from(Cambioprograma.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
