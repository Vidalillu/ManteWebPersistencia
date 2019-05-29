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
import com.seidor.jmanteweb.persistencia.entidades.Macro;
import com.seidor.jmanteweb.persistencia.entidades.Macrodetalle;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class MacrodetalleJpaController implements Serializable {

    public MacrodetalleJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Macrodetalle macrodetalle) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Macro macro = macrodetalle.getMacro();
            if (macro != null) {
                macro = em.getReference(macro.getClass(), macro.getMacro());
                macrodetalle.setMacro(macro);
            }
            em.persist(macrodetalle);
            if (macro != null) {
                macro.getMacrodetalleList().add(macrodetalle);
                macro = em.merge(macro);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMacrodetalle(macrodetalle.getIdmacrodeta()) != null) {
                throw new PreexistingEntityException("Macrodetalle " + macrodetalle + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Macrodetalle macrodetalle) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Macrodetalle persistentMacrodetalle = em.find(Macrodetalle.class, macrodetalle.getIdmacrodeta());
            Macro macroOld = persistentMacrodetalle.getMacro();
            Macro macroNew = macrodetalle.getMacro();
            if (macroNew != null) {
                macroNew = em.getReference(macroNew.getClass(), macroNew.getMacro());
                macrodetalle.setMacro(macroNew);
            }
            macrodetalle = em.merge(macrodetalle);
            if (macroOld != null && !macroOld.equals(macroNew)) {
                macroOld.getMacrodetalleList().remove(macrodetalle);
                macroOld = em.merge(macroOld);
            }
            if (macroNew != null && !macroNew.equals(macroOld)) {
                macroNew.getMacrodetalleList().add(macrodetalle);
                macroNew = em.merge(macroNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = macrodetalle.getIdmacrodeta();
                if (findMacrodetalle(id) == null) {
                    throw new NonexistentEntityException("The macrodetalle with id " + id + " no longer exists.");
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
            Macrodetalle macrodetalle;
            try {
                macrodetalle = em.getReference(Macrodetalle.class, id);
                macrodetalle.getIdmacrodeta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The macrodetalle with id " + id + " no longer exists.", enfe);
            }
            Macro macro = macrodetalle.getMacro();
            if (macro != null) {
                macro.getMacrodetalleList().remove(macrodetalle);
                macro = em.merge(macro);
            }
            em.remove(macrodetalle);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Macrodetalle> findMacrodetalleEntities() {
        return findMacrodetalleEntities(true, -1, -1);
    }

    public List<Macrodetalle> findMacrodetalleEntities(int maxResults, int firstResult) {
        return findMacrodetalleEntities(false, maxResults, firstResult);
    }

    private List<Macrodetalle> findMacrodetalleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Macrodetalle.class));
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

    public Macrodetalle findMacrodetalle(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Macrodetalle.class, id);
        } finally {
            em.close();
        }
    }

    public int getMacrodetalleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Macrodetalle> rt = cq.from(Macrodetalle.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
