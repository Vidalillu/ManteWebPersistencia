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
import com.seidor.jmanteweb.persistencia.entidades.Favorito;
import com.seidor.jmanteweb.persistencia.entidades.FavoritoPK;
import com.seidor.jmanteweb.persistencia.entidades.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class FavoritoJpaController implements Serializable {

    public FavoritoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Favorito favorito) throws PreexistingEntityException, Exception {
        if (favorito.getFavoritoPK() == null) {
            favorito.setFavoritoPK(new FavoritoPK());
        }
        favorito.getFavoritoPK().setUsuario(favorito.getUsuario1().getUsuario());
        favorito.getFavoritoPK().setComponentemenu(favorito.getComponentemenu1().getNombre());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Componentemenu componentemenu1 = favorito.getComponentemenu1();
            if (componentemenu1 != null) {
                componentemenu1 = em.getReference(componentemenu1.getClass(), componentemenu1.getNombre());
                favorito.setComponentemenu1(componentemenu1);
            }
            Usuario usuario1 = favorito.getUsuario1();
            if (usuario1 != null) {
                usuario1 = em.getReference(usuario1.getClass(), usuario1.getUsuario());
                favorito.setUsuario1(usuario1);
            }
            em.persist(favorito);
            if (componentemenu1 != null) {
                componentemenu1.getFavoritoList().add(favorito);
                componentemenu1 = em.merge(componentemenu1);
            }
            if (usuario1 != null) {
                usuario1.getFavoritoList().add(favorito);
                usuario1 = em.merge(usuario1);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFavorito(favorito.getFavoritoPK()) != null) {
                throw new PreexistingEntityException("Favorito " + favorito + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Favorito favorito) throws NonexistentEntityException, Exception {
        favorito.getFavoritoPK().setUsuario(favorito.getUsuario1().getUsuario());
        favorito.getFavoritoPK().setComponentemenu(favorito.getComponentemenu1().getNombre());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Favorito persistentFavorito = em.find(Favorito.class, favorito.getFavoritoPK());
            Componentemenu componentemenu1Old = persistentFavorito.getComponentemenu1();
            Componentemenu componentemenu1New = favorito.getComponentemenu1();
            Usuario usuario1Old = persistentFavorito.getUsuario1();
            Usuario usuario1New = favorito.getUsuario1();
            if (componentemenu1New != null) {
                componentemenu1New = em.getReference(componentemenu1New.getClass(), componentemenu1New.getNombre());
                favorito.setComponentemenu1(componentemenu1New);
            }
            if (usuario1New != null) {
                usuario1New = em.getReference(usuario1New.getClass(), usuario1New.getUsuario());
                favorito.setUsuario1(usuario1New);
            }
            favorito = em.merge(favorito);
            if (componentemenu1Old != null && !componentemenu1Old.equals(componentemenu1New)) {
                componentemenu1Old.getFavoritoList().remove(favorito);
                componentemenu1Old = em.merge(componentemenu1Old);
            }
            if (componentemenu1New != null && !componentemenu1New.equals(componentemenu1Old)) {
                componentemenu1New.getFavoritoList().add(favorito);
                componentemenu1New = em.merge(componentemenu1New);
            }
            if (usuario1Old != null && !usuario1Old.equals(usuario1New)) {
                usuario1Old.getFavoritoList().remove(favorito);
                usuario1Old = em.merge(usuario1Old);
            }
            if (usuario1New != null && !usuario1New.equals(usuario1Old)) {
                usuario1New.getFavoritoList().add(favorito);
                usuario1New = em.merge(usuario1New);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                FavoritoPK id = favorito.getFavoritoPK();
                if (findFavorito(id) == null) {
                    throw new NonexistentEntityException("The favorito with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(FavoritoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Favorito favorito;
            try {
                favorito = em.getReference(Favorito.class, id);
                favorito.getFavoritoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The favorito with id " + id + " no longer exists.", enfe);
            }
            Componentemenu componentemenu1 = favorito.getComponentemenu1();
            if (componentemenu1 != null) {
                componentemenu1.getFavoritoList().remove(favorito);
                componentemenu1 = em.merge(componentemenu1);
            }
            Usuario usuario1 = favorito.getUsuario1();
            if (usuario1 != null) {
                usuario1.getFavoritoList().remove(favorito);
                usuario1 = em.merge(usuario1);
            }
            em.remove(favorito);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Favorito> findFavoritoEntities() {
        return findFavoritoEntities(true, -1, -1);
    }

    public List<Favorito> findFavoritoEntities(int maxResults, int firstResult) {
        return findFavoritoEntities(false, maxResults, firstResult);
    }

    private List<Favorito> findFavoritoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Favorito.class));
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

    public Favorito findFavorito(FavoritoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Favorito.class, id);
        } finally {
            em.close();
        }
    }

    public int getFavoritoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Favorito> rt = cq.from(Favorito.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
