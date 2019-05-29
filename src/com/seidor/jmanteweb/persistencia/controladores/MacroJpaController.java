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
import com.seidor.jmanteweb.persistencia.entidades.Macro;
import com.seidor.jmanteweb.persistencia.entidades.Macrodetalle;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class MacroJpaController implements Serializable {

    public MacroJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Macro macro) throws PreexistingEntityException, Exception {
        if (macro.getMacrodetalleList() == null) {
            macro.setMacrodetalleList(new ArrayList<Macrodetalle>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Componentemenu pantalla = macro.getPantalla();
            if (pantalla != null) {
                pantalla = em.getReference(pantalla.getClass(), pantalla.getNombre());
                macro.setPantalla(pantalla);
            }
            List<Macrodetalle> attachedMacrodetalleList = new ArrayList<Macrodetalle>();
            for (Macrodetalle macrodetalleListMacrodetalleToAttach : macro.getMacrodetalleList()) {
                macrodetalleListMacrodetalleToAttach = em.getReference(macrodetalleListMacrodetalleToAttach.getClass(), macrodetalleListMacrodetalleToAttach.getIdmacrodeta());
                attachedMacrodetalleList.add(macrodetalleListMacrodetalleToAttach);
            }
            macro.setMacrodetalleList(attachedMacrodetalleList);
            em.persist(macro);
            if (pantalla != null) {
                pantalla.getMacroList().add(macro);
                pantalla = em.merge(pantalla);
            }
            for (Macrodetalle macrodetalleListMacrodetalle : macro.getMacrodetalleList()) {
                Macro oldMacroOfMacrodetalleListMacrodetalle = macrodetalleListMacrodetalle.getMacro();
                macrodetalleListMacrodetalle.setMacro(macro);
                macrodetalleListMacrodetalle = em.merge(macrodetalleListMacrodetalle);
                if (oldMacroOfMacrodetalleListMacrodetalle != null) {
                    oldMacroOfMacrodetalleListMacrodetalle.getMacrodetalleList().remove(macrodetalleListMacrodetalle);
                    oldMacroOfMacrodetalleListMacrodetalle = em.merge(oldMacroOfMacrodetalleListMacrodetalle);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMacro(macro.getMacro()) != null) {
                throw new PreexistingEntityException("Macro " + macro + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Macro macro) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Macro persistentMacro = em.find(Macro.class, macro.getMacro());
            Componentemenu pantallaOld = persistentMacro.getPantalla();
            Componentemenu pantallaNew = macro.getPantalla();
            List<Macrodetalle> macrodetalleListOld = persistentMacro.getMacrodetalleList();
            List<Macrodetalle> macrodetalleListNew = macro.getMacrodetalleList();
            List<String> illegalOrphanMessages = null;
            for (Macrodetalle macrodetalleListOldMacrodetalle : macrodetalleListOld) {
                if (!macrodetalleListNew.contains(macrodetalleListOldMacrodetalle)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Macrodetalle " + macrodetalleListOldMacrodetalle + " since its macro field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (pantallaNew != null) {
                pantallaNew = em.getReference(pantallaNew.getClass(), pantallaNew.getNombre());
                macro.setPantalla(pantallaNew);
            }
            List<Macrodetalle> attachedMacrodetalleListNew = new ArrayList<Macrodetalle>();
            for (Macrodetalle macrodetalleListNewMacrodetalleToAttach : macrodetalleListNew) {
                macrodetalleListNewMacrodetalleToAttach = em.getReference(macrodetalleListNewMacrodetalleToAttach.getClass(), macrodetalleListNewMacrodetalleToAttach.getIdmacrodeta());
                attachedMacrodetalleListNew.add(macrodetalleListNewMacrodetalleToAttach);
            }
            macrodetalleListNew = attachedMacrodetalleListNew;
            macro.setMacrodetalleList(macrodetalleListNew);
            macro = em.merge(macro);
            if (pantallaOld != null && !pantallaOld.equals(pantallaNew)) {
                pantallaOld.getMacroList().remove(macro);
                pantallaOld = em.merge(pantallaOld);
            }
            if (pantallaNew != null && !pantallaNew.equals(pantallaOld)) {
                pantallaNew.getMacroList().add(macro);
                pantallaNew = em.merge(pantallaNew);
            }
            for (Macrodetalle macrodetalleListNewMacrodetalle : macrodetalleListNew) {
                if (!macrodetalleListOld.contains(macrodetalleListNewMacrodetalle)) {
                    Macro oldMacroOfMacrodetalleListNewMacrodetalle = macrodetalleListNewMacrodetalle.getMacro();
                    macrodetalleListNewMacrodetalle.setMacro(macro);
                    macrodetalleListNewMacrodetalle = em.merge(macrodetalleListNewMacrodetalle);
                    if (oldMacroOfMacrodetalleListNewMacrodetalle != null && !oldMacroOfMacrodetalleListNewMacrodetalle.equals(macro)) {
                        oldMacroOfMacrodetalleListNewMacrodetalle.getMacrodetalleList().remove(macrodetalleListNewMacrodetalle);
                        oldMacroOfMacrodetalleListNewMacrodetalle = em.merge(oldMacroOfMacrodetalleListNewMacrodetalle);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = macro.getMacro();
                if (findMacro(id) == null) {
                    throw new NonexistentEntityException("The macro with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Macro macro;
            try {
                macro = em.getReference(Macro.class, id);
                macro.getMacro();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The macro with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Macrodetalle> macrodetalleListOrphanCheck = macro.getMacrodetalleList();
            for (Macrodetalle macrodetalleListOrphanCheckMacrodetalle : macrodetalleListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Macro (" + macro + ") cannot be destroyed since the Macrodetalle " + macrodetalleListOrphanCheckMacrodetalle + " in its macrodetalleList field has a non-nullable macro field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Componentemenu pantalla = macro.getPantalla();
            if (pantalla != null) {
                pantalla.getMacroList().remove(macro);
                pantalla = em.merge(pantalla);
            }
            em.remove(macro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Macro> findMacroEntities() {
        return findMacroEntities(true, -1, -1);
    }

    public List<Macro> findMacroEntities(int maxResults, int firstResult) {
        return findMacroEntities(false, maxResults, firstResult);
    }

    private List<Macro> findMacroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Macro.class));
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

    public Macro findMacro(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Macro.class, id);
        } finally {
            em.close();
        }
    }

    public int getMacroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Macro> rt = cq.from(Macro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
