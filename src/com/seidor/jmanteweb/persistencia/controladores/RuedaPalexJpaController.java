/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.controladores;

import com.seidor.jmanteweb.persistencia.controladores.exceptions.IllegalOrphanException;
import com.seidor.jmanteweb.persistencia.controladores.exceptions.NonexistentEntityException;
import com.seidor.jmanteweb.persistencia.controladores.exceptions.PreexistingEntityException;
import com.seidor.jmanteweb.persistencia.entidades.RuedaPalex;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.seidor.jmanteweb.persistencia.entidades.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class RuedaPalexJpaController implements Serializable {

    public RuedaPalexJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RuedaPalex ruedaPalex) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Usuario usuario1OrphanCheck = ruedaPalex.getUsuario1();
        if (usuario1OrphanCheck != null) {
            RuedaPalex oldRuedaPalexOfUsuario1 = usuario1OrphanCheck.getRuedaPalex();
            if (oldRuedaPalexOfUsuario1 != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Usuario " + usuario1OrphanCheck + " already has an item of type RuedaPalex whose usuario1 column cannot be null. Please make another selection for the usuario1 field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario1 = ruedaPalex.getUsuario1();
            if (usuario1 != null) {
                usuario1 = em.getReference(usuario1.getClass(), usuario1.getUsuario());
                ruedaPalex.setUsuario1(usuario1);
            }
            em.persist(ruedaPalex);
            if (usuario1 != null) {
                usuario1.setRuedaPalex(ruedaPalex);
                usuario1 = em.merge(usuario1);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRuedaPalex(ruedaPalex.getUsuario()) != null) {
                throw new PreexistingEntityException("RuedaPalex " + ruedaPalex + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RuedaPalex ruedaPalex) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RuedaPalex persistentRuedaPalex = em.find(RuedaPalex.class, ruedaPalex.getUsuario());
            Usuario usuario1Old = persistentRuedaPalex.getUsuario1();
            Usuario usuario1New = ruedaPalex.getUsuario1();
            List<String> illegalOrphanMessages = null;
            if (usuario1New != null && !usuario1New.equals(usuario1Old)) {
                RuedaPalex oldRuedaPalexOfUsuario1 = usuario1New.getRuedaPalex();
                if (oldRuedaPalexOfUsuario1 != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Usuario " + usuario1New + " already has an item of type RuedaPalex whose usuario1 column cannot be null. Please make another selection for the usuario1 field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuario1New != null) {
                usuario1New = em.getReference(usuario1New.getClass(), usuario1New.getUsuario());
                ruedaPalex.setUsuario1(usuario1New);
            }
            ruedaPalex = em.merge(ruedaPalex);
            if (usuario1Old != null && !usuario1Old.equals(usuario1New)) {
                usuario1Old.setRuedaPalex(null);
                usuario1Old = em.merge(usuario1Old);
            }
            if (usuario1New != null && !usuario1New.equals(usuario1Old)) {
                usuario1New.setRuedaPalex(ruedaPalex);
                usuario1New = em.merge(usuario1New);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = ruedaPalex.getUsuario();
                if (findRuedaPalex(id) == null) {
                    throw new NonexistentEntityException("The ruedaPalex with id " + id + " no longer exists.");
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
            RuedaPalex ruedaPalex;
            try {
                ruedaPalex = em.getReference(RuedaPalex.class, id);
                ruedaPalex.getUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ruedaPalex with id " + id + " no longer exists.", enfe);
            }
            Usuario usuario1 = ruedaPalex.getUsuario1();
            if (usuario1 != null) {
                usuario1.setRuedaPalex(null);
                usuario1 = em.merge(usuario1);
            }
            em.remove(ruedaPalex);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RuedaPalex> findRuedaPalexEntities() {
        return findRuedaPalexEntities(true, -1, -1);
    }

    public List<RuedaPalex> findRuedaPalexEntities(int maxResults, int firstResult) {
        return findRuedaPalexEntities(false, maxResults, firstResult);
    }

    private List<RuedaPalex> findRuedaPalexEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RuedaPalex.class));
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

    public RuedaPalex findRuedaPalex(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RuedaPalex.class, id);
        } finally {
            em.close();
        }
    }

    public int getRuedaPalexCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RuedaPalex> rt = cq.from(RuedaPalex.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
