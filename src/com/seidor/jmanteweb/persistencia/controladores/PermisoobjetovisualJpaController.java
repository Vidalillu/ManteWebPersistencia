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
import com.seidor.jmanteweb.persistencia.entidades.Objetovisual;
import com.seidor.jmanteweb.persistencia.entidades.Perfil;
import com.seidor.jmanteweb.persistencia.entidades.Permisoobjetovisual;
import com.seidor.jmanteweb.persistencia.entidades.PermisoobjetovisualPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class PermisoobjetovisualJpaController implements Serializable {

    public PermisoobjetovisualJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Permisoobjetovisual permisoobjetovisual) throws PreexistingEntityException, Exception {
        if (permisoobjetovisual.getPermisoobjetovisualPK() == null) {
            permisoobjetovisual.setPermisoobjetovisualPK(new PermisoobjetovisualPK());
        }
        permisoobjetovisual.getPermisoobjetovisualPK().setObjetovisual(permisoobjetovisual.getObjetovisual1().getIdobjeto());
        permisoobjetovisual.getPermisoobjetovisualPK().setPerfil(permisoobjetovisual.getPerfil1().getPerfil());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Objetovisual objetovisual1 = permisoobjetovisual.getObjetovisual1();
            if (objetovisual1 != null) {
                objetovisual1 = em.getReference(objetovisual1.getClass(), objetovisual1.getIdobjeto());
                permisoobjetovisual.setObjetovisual1(objetovisual1);
            }
            Perfil perfil1 = permisoobjetovisual.getPerfil1();
            if (perfil1 != null) {
                perfil1 = em.getReference(perfil1.getClass(), perfil1.getPerfil());
                permisoobjetovisual.setPerfil1(perfil1);
            }
            em.persist(permisoobjetovisual);
            if (objetovisual1 != null) {
                objetovisual1.getPermisoobjetovisualList().add(permisoobjetovisual);
                objetovisual1 = em.merge(objetovisual1);
            }
            if (perfil1 != null) {
                perfil1.getPermisoobjetovisualList().add(permisoobjetovisual);
                perfil1 = em.merge(perfil1);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPermisoobjetovisual(permisoobjetovisual.getPermisoobjetovisualPK()) != null) {
                throw new PreexistingEntityException("Permisoobjetovisual " + permisoobjetovisual + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Permisoobjetovisual permisoobjetovisual) throws NonexistentEntityException, Exception {
        permisoobjetovisual.getPermisoobjetovisualPK().setObjetovisual(permisoobjetovisual.getObjetovisual1().getIdobjeto());
        permisoobjetovisual.getPermisoobjetovisualPK().setPerfil(permisoobjetovisual.getPerfil1().getPerfil());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Permisoobjetovisual persistentPermisoobjetovisual = em.find(Permisoobjetovisual.class, permisoobjetovisual.getPermisoobjetovisualPK());
            Objetovisual objetovisual1Old = persistentPermisoobjetovisual.getObjetovisual1();
            Objetovisual objetovisual1New = permisoobjetovisual.getObjetovisual1();
            Perfil perfil1Old = persistentPermisoobjetovisual.getPerfil1();
            Perfil perfil1New = permisoobjetovisual.getPerfil1();
            if (objetovisual1New != null) {
                objetovisual1New = em.getReference(objetovisual1New.getClass(), objetovisual1New.getIdobjeto());
                permisoobjetovisual.setObjetovisual1(objetovisual1New);
            }
            if (perfil1New != null) {
                perfil1New = em.getReference(perfil1New.getClass(), perfil1New.getPerfil());
                permisoobjetovisual.setPerfil1(perfil1New);
            }
            permisoobjetovisual = em.merge(permisoobjetovisual);
            if (objetovisual1Old != null && !objetovisual1Old.equals(objetovisual1New)) {
                objetovisual1Old.getPermisoobjetovisualList().remove(permisoobjetovisual);
                objetovisual1Old = em.merge(objetovisual1Old);
            }
            if (objetovisual1New != null && !objetovisual1New.equals(objetovisual1Old)) {
                objetovisual1New.getPermisoobjetovisualList().add(permisoobjetovisual);
                objetovisual1New = em.merge(objetovisual1New);
            }
            if (perfil1Old != null && !perfil1Old.equals(perfil1New)) {
                perfil1Old.getPermisoobjetovisualList().remove(permisoobjetovisual);
                perfil1Old = em.merge(perfil1Old);
            }
            if (perfil1New != null && !perfil1New.equals(perfil1Old)) {
                perfil1New.getPermisoobjetovisualList().add(permisoobjetovisual);
                perfil1New = em.merge(perfil1New);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PermisoobjetovisualPK id = permisoobjetovisual.getPermisoobjetovisualPK();
                if (findPermisoobjetovisual(id) == null) {
                    throw new NonexistentEntityException("The permisoobjetovisual with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PermisoobjetovisualPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Permisoobjetovisual permisoobjetovisual;
            try {
                permisoobjetovisual = em.getReference(Permisoobjetovisual.class, id);
                permisoobjetovisual.getPermisoobjetovisualPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The permisoobjetovisual with id " + id + " no longer exists.", enfe);
            }
            Objetovisual objetovisual1 = permisoobjetovisual.getObjetovisual1();
            if (objetovisual1 != null) {
                objetovisual1.getPermisoobjetovisualList().remove(permisoobjetovisual);
                objetovisual1 = em.merge(objetovisual1);
            }
            Perfil perfil1 = permisoobjetovisual.getPerfil1();
            if (perfil1 != null) {
                perfil1.getPermisoobjetovisualList().remove(permisoobjetovisual);
                perfil1 = em.merge(perfil1);
            }
            em.remove(permisoobjetovisual);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Permisoobjetovisual> findPermisoobjetovisualEntities() {
        return findPermisoobjetovisualEntities(true, -1, -1);
    }

    public List<Permisoobjetovisual> findPermisoobjetovisualEntities(int maxResults, int firstResult) {
        return findPermisoobjetovisualEntities(false, maxResults, firstResult);
    }

    private List<Permisoobjetovisual> findPermisoobjetovisualEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Permisoobjetovisual.class));
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

    public Permisoobjetovisual findPermisoobjetovisual(PermisoobjetovisualPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Permisoobjetovisual.class, id);
        } finally {
            em.close();
        }
    }

    public int getPermisoobjetovisualCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Permisoobjetovisual> rt = cq.from(Permisoobjetovisual.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
