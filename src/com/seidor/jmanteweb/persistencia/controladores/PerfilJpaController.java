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
import com.seidor.jmanteweb.persistencia.entidades.Perfil;
import com.seidor.jmanteweb.persistencia.entidades.Permisoobjetovisual;
import java.util.ArrayList;
import java.util.List;
import com.seidor.jmanteweb.persistencia.entidades.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class PerfilJpaController implements Serializable {

    public PerfilJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Perfil perfil) throws PreexistingEntityException, Exception {
        if (perfil.getPermisoobjetovisualList() == null) {
            perfil.setPermisoobjetovisualList(new ArrayList<Permisoobjetovisual>());
        }
        if (perfil.getUsuarioList() == null) {
            perfil.setUsuarioList(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Componentemenu menuprograma = perfil.getMenuprograma();
            if (menuprograma != null) {
                menuprograma = em.getReference(menuprograma.getClass(), menuprograma.getNombre());
                perfil.setMenuprograma(menuprograma);
            }
            List<Permisoobjetovisual> attachedPermisoobjetovisualList = new ArrayList<Permisoobjetovisual>();
            for (Permisoobjetovisual permisoobjetovisualListPermisoobjetovisualToAttach : perfil.getPermisoobjetovisualList()) {
                permisoobjetovisualListPermisoobjetovisualToAttach = em.getReference(permisoobjetovisualListPermisoobjetovisualToAttach.getClass(), permisoobjetovisualListPermisoobjetovisualToAttach.getPermisoobjetovisualPK());
                attachedPermisoobjetovisualList.add(permisoobjetovisualListPermisoobjetovisualToAttach);
            }
            perfil.setPermisoobjetovisualList(attachedPermisoobjetovisualList);
            List<Usuario> attachedUsuarioList = new ArrayList<Usuario>();
            for (Usuario usuarioListUsuarioToAttach : perfil.getUsuarioList()) {
                usuarioListUsuarioToAttach = em.getReference(usuarioListUsuarioToAttach.getClass(), usuarioListUsuarioToAttach.getUsuario());
                attachedUsuarioList.add(usuarioListUsuarioToAttach);
            }
            perfil.setUsuarioList(attachedUsuarioList);
            em.persist(perfil);
            if (menuprograma != null) {
                menuprograma.getPerfilList().add(perfil);
                menuprograma = em.merge(menuprograma);
            }
            for (Permisoobjetovisual permisoobjetovisualListPermisoobjetovisual : perfil.getPermisoobjetovisualList()) {
                Perfil oldPerfil1OfPermisoobjetovisualListPermisoobjetovisual = permisoobjetovisualListPermisoobjetovisual.getPerfil1();
                permisoobjetovisualListPermisoobjetovisual.setPerfil1(perfil);
                permisoobjetovisualListPermisoobjetovisual = em.merge(permisoobjetovisualListPermisoobjetovisual);
                if (oldPerfil1OfPermisoobjetovisualListPermisoobjetovisual != null) {
                    oldPerfil1OfPermisoobjetovisualListPermisoobjetovisual.getPermisoobjetovisualList().remove(permisoobjetovisualListPermisoobjetovisual);
                    oldPerfil1OfPermisoobjetovisualListPermisoobjetovisual = em.merge(oldPerfil1OfPermisoobjetovisualListPermisoobjetovisual);
                }
            }
            for (Usuario usuarioListUsuario : perfil.getUsuarioList()) {
                Perfil oldPerfilOfUsuarioListUsuario = usuarioListUsuario.getPerfil();
                usuarioListUsuario.setPerfil(perfil);
                usuarioListUsuario = em.merge(usuarioListUsuario);
                if (oldPerfilOfUsuarioListUsuario != null) {
                    oldPerfilOfUsuarioListUsuario.getUsuarioList().remove(usuarioListUsuario);
                    oldPerfilOfUsuarioListUsuario = em.merge(oldPerfilOfUsuarioListUsuario);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPerfil(perfil.getPerfil()) != null) {
                throw new PreexistingEntityException("Perfil " + perfil + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Perfil perfil) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Perfil persistentPerfil = em.find(Perfil.class, perfil.getPerfil());
            Componentemenu menuprogramaOld = persistentPerfil.getMenuprograma();
            Componentemenu menuprogramaNew = perfil.getMenuprograma();
            List<Permisoobjetovisual> permisoobjetovisualListOld = persistentPerfil.getPermisoobjetovisualList();
            List<Permisoobjetovisual> permisoobjetovisualListNew = perfil.getPermisoobjetovisualList();
            List<Usuario> usuarioListOld = persistentPerfil.getUsuarioList();
            List<Usuario> usuarioListNew = perfil.getUsuarioList();
            List<String> illegalOrphanMessages = null;
            for (Permisoobjetovisual permisoobjetovisualListOldPermisoobjetovisual : permisoobjetovisualListOld) {
                if (!permisoobjetovisualListNew.contains(permisoobjetovisualListOldPermisoobjetovisual)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Permisoobjetovisual " + permisoobjetovisualListOldPermisoobjetovisual + " since its perfil1 field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (menuprogramaNew != null) {
                menuprogramaNew = em.getReference(menuprogramaNew.getClass(), menuprogramaNew.getNombre());
                perfil.setMenuprograma(menuprogramaNew);
            }
            List<Permisoobjetovisual> attachedPermisoobjetovisualListNew = new ArrayList<Permisoobjetovisual>();
            for (Permisoobjetovisual permisoobjetovisualListNewPermisoobjetovisualToAttach : permisoobjetovisualListNew) {
                permisoobjetovisualListNewPermisoobjetovisualToAttach = em.getReference(permisoobjetovisualListNewPermisoobjetovisualToAttach.getClass(), permisoobjetovisualListNewPermisoobjetovisualToAttach.getPermisoobjetovisualPK());
                attachedPermisoobjetovisualListNew.add(permisoobjetovisualListNewPermisoobjetovisualToAttach);
            }
            permisoobjetovisualListNew = attachedPermisoobjetovisualListNew;
            perfil.setPermisoobjetovisualList(permisoobjetovisualListNew);
            List<Usuario> attachedUsuarioListNew = new ArrayList<Usuario>();
            for (Usuario usuarioListNewUsuarioToAttach : usuarioListNew) {
                usuarioListNewUsuarioToAttach = em.getReference(usuarioListNewUsuarioToAttach.getClass(), usuarioListNewUsuarioToAttach.getUsuario());
                attachedUsuarioListNew.add(usuarioListNewUsuarioToAttach);
            }
            usuarioListNew = attachedUsuarioListNew;
            perfil.setUsuarioList(usuarioListNew);
            perfil = em.merge(perfil);
            if (menuprogramaOld != null && !menuprogramaOld.equals(menuprogramaNew)) {
                menuprogramaOld.getPerfilList().remove(perfil);
                menuprogramaOld = em.merge(menuprogramaOld);
            }
            if (menuprogramaNew != null && !menuprogramaNew.equals(menuprogramaOld)) {
                menuprogramaNew.getPerfilList().add(perfil);
                menuprogramaNew = em.merge(menuprogramaNew);
            }
            for (Permisoobjetovisual permisoobjetovisualListNewPermisoobjetovisual : permisoobjetovisualListNew) {
                if (!permisoobjetovisualListOld.contains(permisoobjetovisualListNewPermisoobjetovisual)) {
                    Perfil oldPerfil1OfPermisoobjetovisualListNewPermisoobjetovisual = permisoobjetovisualListNewPermisoobjetovisual.getPerfil1();
                    permisoobjetovisualListNewPermisoobjetovisual.setPerfil1(perfil);
                    permisoobjetovisualListNewPermisoobjetovisual = em.merge(permisoobjetovisualListNewPermisoobjetovisual);
                    if (oldPerfil1OfPermisoobjetovisualListNewPermisoobjetovisual != null && !oldPerfil1OfPermisoobjetovisualListNewPermisoobjetovisual.equals(perfil)) {
                        oldPerfil1OfPermisoobjetovisualListNewPermisoobjetovisual.getPermisoobjetovisualList().remove(permisoobjetovisualListNewPermisoobjetovisual);
                        oldPerfil1OfPermisoobjetovisualListNewPermisoobjetovisual = em.merge(oldPerfil1OfPermisoobjetovisualListNewPermisoobjetovisual);
                    }
                }
            }
            for (Usuario usuarioListOldUsuario : usuarioListOld) {
                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
                    usuarioListOldUsuario.setPerfil(null);
                    usuarioListOldUsuario = em.merge(usuarioListOldUsuario);
                }
            }
            for (Usuario usuarioListNewUsuario : usuarioListNew) {
                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
                    Perfil oldPerfilOfUsuarioListNewUsuario = usuarioListNewUsuario.getPerfil();
                    usuarioListNewUsuario.setPerfil(perfil);
                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
                    if (oldPerfilOfUsuarioListNewUsuario != null && !oldPerfilOfUsuarioListNewUsuario.equals(perfil)) {
                        oldPerfilOfUsuarioListNewUsuario.getUsuarioList().remove(usuarioListNewUsuario);
                        oldPerfilOfUsuarioListNewUsuario = em.merge(oldPerfilOfUsuarioListNewUsuario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = perfil.getPerfil();
                if (findPerfil(id) == null) {
                    throw new NonexistentEntityException("The perfil with id " + id + " no longer exists.");
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
            Perfil perfil;
            try {
                perfil = em.getReference(Perfil.class, id);
                perfil.getPerfil();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The perfil with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Permisoobjetovisual> permisoobjetovisualListOrphanCheck = perfil.getPermisoobjetovisualList();
            for (Permisoobjetovisual permisoobjetovisualListOrphanCheckPermisoobjetovisual : permisoobjetovisualListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Perfil (" + perfil + ") cannot be destroyed since the Permisoobjetovisual " + permisoobjetovisualListOrphanCheckPermisoobjetovisual + " in its permisoobjetovisualList field has a non-nullable perfil1 field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Componentemenu menuprograma = perfil.getMenuprograma();
            if (menuprograma != null) {
                menuprograma.getPerfilList().remove(perfil);
                menuprograma = em.merge(menuprograma);
            }
            List<Usuario> usuarioList = perfil.getUsuarioList();
            for (Usuario usuarioListUsuario : usuarioList) {
                usuarioListUsuario.setPerfil(null);
                usuarioListUsuario = em.merge(usuarioListUsuario);
            }
            em.remove(perfil);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Perfil> findPerfilEntities() {
        return findPerfilEntities(true, -1, -1);
    }

    public List<Perfil> findPerfilEntities(int maxResults, int firstResult) {
        return findPerfilEntities(false, maxResults, firstResult);
    }

    private List<Perfil> findPerfilEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Perfil.class));
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

    public Perfil findPerfil(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Perfil.class, id);
        } finally {
            em.close();
        }
    }

    public int getPerfilCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Perfil> rt = cq.from(Perfil.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
