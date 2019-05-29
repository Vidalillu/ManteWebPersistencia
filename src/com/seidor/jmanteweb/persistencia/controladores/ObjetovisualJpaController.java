/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.controladores;

import com.seidor.jmanteweb.persistencia.controladores.exceptions.IllegalOrphanException;
import com.seidor.jmanteweb.persistencia.controladores.exceptions.NonexistentEntityException;
import com.seidor.jmanteweb.persistencia.controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.seidor.jmanteweb.persistencia.entidades.Componentemenu;
import com.seidor.jmanteweb.persistencia.entidades.Objetovisual;
import com.seidor.jmanteweb.persistencia.entidades.Permisoobjetovisual;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class ObjetovisualJpaController implements Serializable {

    public ObjetovisualJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Objetovisual objetovisual) throws PreexistingEntityException, Exception {
        if (objetovisual.getPermisoobjetovisualList() == null) {
            objetovisual.setPermisoobjetovisualList(new ArrayList<Permisoobjetovisual>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Componentemenu pantalla = objetovisual.getPantalla();
            if (pantalla != null) {
                pantalla = em.getReference(pantalla.getClass(), pantalla.getNombre());
                objetovisual.setPantalla(pantalla);
            }
            List<Permisoobjetovisual> attachedPermisoobjetovisualList = new ArrayList<Permisoobjetovisual>();
            for (Permisoobjetovisual permisoobjetovisualListPermisoobjetovisualToAttach : objetovisual.getPermisoobjetovisualList()) {
                permisoobjetovisualListPermisoobjetovisualToAttach = em.getReference(permisoobjetovisualListPermisoobjetovisualToAttach.getClass(), permisoobjetovisualListPermisoobjetovisualToAttach.getPermisoobjetovisualPK());
                attachedPermisoobjetovisualList.add(permisoobjetovisualListPermisoobjetovisualToAttach);
            }
            objetovisual.setPermisoobjetovisualList(attachedPermisoobjetovisualList);
            em.persist(objetovisual);
            if (pantalla != null) {
                pantalla.getObjetovisualList().add(objetovisual);
                pantalla = em.merge(pantalla);
            }
            for (Permisoobjetovisual permisoobjetovisualListPermisoobjetovisual : objetovisual.getPermisoobjetovisualList()) {
                Objetovisual oldObjetovisual1OfPermisoobjetovisualListPermisoobjetovisual = permisoobjetovisualListPermisoobjetovisual.getObjetovisual1();
                permisoobjetovisualListPermisoobjetovisual.setObjetovisual1(objetovisual);
                permisoobjetovisualListPermisoobjetovisual = em.merge(permisoobjetovisualListPermisoobjetovisual);
                if (oldObjetovisual1OfPermisoobjetovisualListPermisoobjetovisual != null) {
                    oldObjetovisual1OfPermisoobjetovisualListPermisoobjetovisual.getPermisoobjetovisualList().remove(permisoobjetovisualListPermisoobjetovisual);
                    oldObjetovisual1OfPermisoobjetovisualListPermisoobjetovisual = em.merge(oldObjetovisual1OfPermisoobjetovisualListPermisoobjetovisual);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findObjetovisual(objetovisual.getIdobjeto()) != null) {
                throw new PreexistingEntityException("Objetovisual " + objetovisual + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Objetovisual objetovisual) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Objetovisual persistentObjetovisual = em.find(Objetovisual.class, objetovisual.getIdobjeto());
            Componentemenu pantallaOld = persistentObjetovisual.getPantalla();
            Componentemenu pantallaNew = objetovisual.getPantalla();
            List<Permisoobjetovisual> permisoobjetovisualListOld = persistentObjetovisual.getPermisoobjetovisualList();
            List<Permisoobjetovisual> permisoobjetovisualListNew = objetovisual.getPermisoobjetovisualList();
            List<String> illegalOrphanMessages = null;
            for (Permisoobjetovisual permisoobjetovisualListOldPermisoobjetovisual : permisoobjetovisualListOld) {
                if (!permisoobjetovisualListNew.contains(permisoobjetovisualListOldPermisoobjetovisual)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Permisoobjetovisual " + permisoobjetovisualListOldPermisoobjetovisual + " since its objetovisual1 field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (pantallaNew != null) {
                pantallaNew = em.getReference(pantallaNew.getClass(), pantallaNew.getNombre());
                objetovisual.setPantalla(pantallaNew);
            }
            List<Permisoobjetovisual> attachedPermisoobjetovisualListNew = new ArrayList<Permisoobjetovisual>();
            for (Permisoobjetovisual permisoobjetovisualListNewPermisoobjetovisualToAttach : permisoobjetovisualListNew) {
                permisoobjetovisualListNewPermisoobjetovisualToAttach = em.getReference(permisoobjetovisualListNewPermisoobjetovisualToAttach.getClass(), permisoobjetovisualListNewPermisoobjetovisualToAttach.getPermisoobjetovisualPK());
                attachedPermisoobjetovisualListNew.add(permisoobjetovisualListNewPermisoobjetovisualToAttach);
            }
            permisoobjetovisualListNew = attachedPermisoobjetovisualListNew;
            objetovisual.setPermisoobjetovisualList(permisoobjetovisualListNew);
            objetovisual = em.merge(objetovisual);
            if (pantallaOld != null && !pantallaOld.equals(pantallaNew)) {
                pantallaOld.getObjetovisualList().remove(objetovisual);
                pantallaOld = em.merge(pantallaOld);
            }
            if (pantallaNew != null && !pantallaNew.equals(pantallaOld)) {
                pantallaNew.getObjetovisualList().add(objetovisual);
                pantallaNew = em.merge(pantallaNew);
            }
            for (Permisoobjetovisual permisoobjetovisualListNewPermisoobjetovisual : permisoobjetovisualListNew) {
                if (!permisoobjetovisualListOld.contains(permisoobjetovisualListNewPermisoobjetovisual)) {
                    Objetovisual oldObjetovisual1OfPermisoobjetovisualListNewPermisoobjetovisual = permisoobjetovisualListNewPermisoobjetovisual.getObjetovisual1();
                    permisoobjetovisualListNewPermisoobjetovisual.setObjetovisual1(objetovisual);
                    permisoobjetovisualListNewPermisoobjetovisual = em.merge(permisoobjetovisualListNewPermisoobjetovisual);
                    if (oldObjetovisual1OfPermisoobjetovisualListNewPermisoobjetovisual != null && !oldObjetovisual1OfPermisoobjetovisualListNewPermisoobjetovisual.equals(objetovisual)) {
                        oldObjetovisual1OfPermisoobjetovisualListNewPermisoobjetovisual.getPermisoobjetovisualList().remove(permisoobjetovisualListNewPermisoobjetovisual);
                        oldObjetovisual1OfPermisoobjetovisualListNewPermisoobjetovisual = em.merge(oldObjetovisual1OfPermisoobjetovisualListNewPermisoobjetovisual);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigInteger id = objetovisual.getIdobjeto();
                if (findObjetovisual(id) == null) {
                    throw new NonexistentEntityException("The objetovisual with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigInteger id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Objetovisual objetovisual;
            try {
                objetovisual = em.getReference(Objetovisual.class, id);
                objetovisual.getIdobjeto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The objetovisual with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Permisoobjetovisual> permisoobjetovisualListOrphanCheck = objetovisual.getPermisoobjetovisualList();
            for (Permisoobjetovisual permisoobjetovisualListOrphanCheckPermisoobjetovisual : permisoobjetovisualListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Objetovisual (" + objetovisual + ") cannot be destroyed since the Permisoobjetovisual " + permisoobjetovisualListOrphanCheckPermisoobjetovisual + " in its permisoobjetovisualList field has a non-nullable objetovisual1 field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Componentemenu pantalla = objetovisual.getPantalla();
            if (pantalla != null) {
                pantalla.getObjetovisualList().remove(objetovisual);
                pantalla = em.merge(pantalla);
            }
            em.remove(objetovisual);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Objetovisual> findObjetovisualEntities() {
        return findObjetovisualEntities(true, -1, -1);
    }

    public List<Objetovisual> findObjetovisualEntities(int maxResults, int firstResult) {
        return findObjetovisualEntities(false, maxResults, firstResult);
    }

    private List<Objetovisual> findObjetovisualEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Objetovisual.class));
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

    public Objetovisual findObjetovisual(BigInteger id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Objetovisual.class, id);
        } finally {
            em.close();
        }
    }

    public int getObjetovisualCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Objetovisual> rt = cq.from(Objetovisual.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
