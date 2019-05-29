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
import com.seidor.jmanteweb.persistencia.entidades.Ordencomponente;
import com.seidor.jmanteweb.persistencia.entidades.OrdencomponentePK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class OrdencomponenteJpaController implements Serializable {

    public OrdencomponenteJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ordencomponente ordencomponente) throws PreexistingEntityException, Exception {
        if (ordencomponente.getOrdencomponentePK() == null) {
            ordencomponente.setOrdencomponentePK(new OrdencomponentePK());
        }
        ordencomponente.getOrdencomponentePK().setMenu(ordencomponente.getComponentemenu1().getNombre());
        ordencomponente.getOrdencomponentePK().setComponente(ordencomponente.getComponentemenu().getNombre());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Componentemenu componentemenu = ordencomponente.getComponentemenu();
            if (componentemenu != null) {
                componentemenu = em.getReference(componentemenu.getClass(), componentemenu.getNombre());
                ordencomponente.setComponentemenu(componentemenu);
            }
            Componentemenu componentemenu1 = ordencomponente.getComponentemenu1();
            if (componentemenu1 != null) {
                componentemenu1 = em.getReference(componentemenu1.getClass(), componentemenu1.getNombre());
                ordencomponente.setComponentemenu1(componentemenu1);
            }
            em.persist(ordencomponente);
            if (componentemenu != null) {
                componentemenu.getOrdencomponenteList().add(ordencomponente);
                componentemenu = em.merge(componentemenu);
            }
            if (componentemenu1 != null) {
                componentemenu1.getOrdencomponenteList().add(ordencomponente);
                componentemenu1 = em.merge(componentemenu1);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOrdencomponente(ordencomponente.getOrdencomponentePK()) != null) {
                throw new PreexistingEntityException("Ordencomponente " + ordencomponente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ordencomponente ordencomponente) throws NonexistentEntityException, Exception {
        ordencomponente.getOrdencomponentePK().setMenu(ordencomponente.getComponentemenu1().getNombre());
        ordencomponente.getOrdencomponentePK().setComponente(ordencomponente.getComponentemenu().getNombre());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ordencomponente persistentOrdencomponente = em.find(Ordencomponente.class, ordencomponente.getOrdencomponentePK());
            Componentemenu componentemenuOld = persistentOrdencomponente.getComponentemenu();
            Componentemenu componentemenuNew = ordencomponente.getComponentemenu();
            Componentemenu componentemenu1Old = persistentOrdencomponente.getComponentemenu1();
            Componentemenu componentemenu1New = ordencomponente.getComponentemenu1();
            if (componentemenuNew != null) {
                componentemenuNew = em.getReference(componentemenuNew.getClass(), componentemenuNew.getNombre());
                ordencomponente.setComponentemenu(componentemenuNew);
            }
            if (componentemenu1New != null) {
                componentemenu1New = em.getReference(componentemenu1New.getClass(), componentemenu1New.getNombre());
                ordencomponente.setComponentemenu1(componentemenu1New);
            }
            ordencomponente = em.merge(ordencomponente);
            if (componentemenuOld != null && !componentemenuOld.equals(componentemenuNew)) {
                componentemenuOld.getOrdencomponenteList().remove(ordencomponente);
                componentemenuOld = em.merge(componentemenuOld);
            }
            if (componentemenuNew != null && !componentemenuNew.equals(componentemenuOld)) {
                componentemenuNew.getOrdencomponenteList().add(ordencomponente);
                componentemenuNew = em.merge(componentemenuNew);
            }
            if (componentemenu1Old != null && !componentemenu1Old.equals(componentemenu1New)) {
                componentemenu1Old.getOrdencomponenteList().remove(ordencomponente);
                componentemenu1Old = em.merge(componentemenu1Old);
            }
            if (componentemenu1New != null && !componentemenu1New.equals(componentemenu1Old)) {
                componentemenu1New.getOrdencomponenteList().add(ordencomponente);
                componentemenu1New = em.merge(componentemenu1New);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                OrdencomponentePK id = ordencomponente.getOrdencomponentePK();
                if (findOrdencomponente(id) == null) {
                    throw new NonexistentEntityException("The ordencomponente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(OrdencomponentePK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ordencomponente ordencomponente;
            try {
                ordencomponente = em.getReference(Ordencomponente.class, id);
                ordencomponente.getOrdencomponentePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ordencomponente with id " + id + " no longer exists.", enfe);
            }
            Componentemenu componentemenu = ordencomponente.getComponentemenu();
            if (componentemenu != null) {
                componentemenu.getOrdencomponenteList().remove(ordencomponente);
                componentemenu = em.merge(componentemenu);
            }
            Componentemenu componentemenu1 = ordencomponente.getComponentemenu1();
            if (componentemenu1 != null) {
                componentemenu1.getOrdencomponenteList().remove(ordencomponente);
                componentemenu1 = em.merge(componentemenu1);
            }
            em.remove(ordencomponente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ordencomponente> findOrdencomponenteEntities() {
        return findOrdencomponenteEntities(true, -1, -1);
    }

    public List<Ordencomponente> findOrdencomponenteEntities(int maxResults, int firstResult) {
        return findOrdencomponenteEntities(false, maxResults, firstResult);
    }

    private List<Ordencomponente> findOrdencomponenteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ordencomponente.class));
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

    public Ordencomponente findOrdencomponente(OrdencomponentePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ordencomponente.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrdencomponenteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ordencomponente> rt = cq.from(Ordencomponente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
