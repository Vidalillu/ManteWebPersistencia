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
import com.seidor.jmanteweb.persistencia.entidades.Usuario;
import com.seidor.jmanteweb.persistencia.entidades.UsuarioRueda;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class UsuarioRuedaJpaController implements Serializable {

    public UsuarioRuedaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UsuarioRueda usuarioRueda) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Usuario usuarioOrphanCheck = usuarioRueda.getUsuario();
        if (usuarioOrphanCheck != null) {
            UsuarioRueda oldUsuarioRuedaOfUsuario = usuarioOrphanCheck.getUsuarioRueda();
            if (oldUsuarioRuedaOfUsuario != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Usuario " + usuarioOrphanCheck + " already has an item of type UsuarioRueda whose usuario column cannot be null. Please make another selection for the usuario field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario = usuarioRueda.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getUsuario());
                usuarioRueda.setUsuario(usuario);
            }
            Usuario pareja = usuarioRueda.getPareja();
            if (pareja != null) {
                pareja = em.getReference(pareja.getClass(), pareja.getUsuario());
                usuarioRueda.setPareja(pareja);
            }
            em.persist(usuarioRueda);
            if (usuario != null) {
                usuario.setUsuarioRueda(usuarioRueda);
                usuario = em.merge(usuario);
            }
            if (pareja != null) {
                UsuarioRueda oldUsuarioRuedaOfPareja = pareja.getUsuarioRueda();
                if (oldUsuarioRuedaOfPareja != null) {
                    oldUsuarioRuedaOfPareja.setPareja(null);
                    oldUsuarioRuedaOfPareja = em.merge(oldUsuarioRuedaOfPareja);
                }
                pareja.setUsuarioRueda(usuarioRueda);
                pareja = em.merge(pareja);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuarioRueda(usuarioRueda.getUsuariomante()) != null) {
                throw new PreexistingEntityException("UsuarioRueda " + usuarioRueda + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UsuarioRueda usuarioRueda) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UsuarioRueda persistentUsuarioRueda = em.find(UsuarioRueda.class, usuarioRueda.getUsuariomante());
            Usuario usuarioOld = persistentUsuarioRueda.getUsuario();
            Usuario usuarioNew = usuarioRueda.getUsuario();
            Usuario parejaOld = persistentUsuarioRueda.getPareja();
            Usuario parejaNew = usuarioRueda.getPareja();
            List<String> illegalOrphanMessages = null;
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                UsuarioRueda oldUsuarioRuedaOfUsuario = usuarioNew.getUsuarioRueda();
                if (oldUsuarioRuedaOfUsuario != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Usuario " + usuarioNew + " already has an item of type UsuarioRueda whose usuario column cannot be null. Please make another selection for the usuario field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getUsuario());
                usuarioRueda.setUsuario(usuarioNew);
            }
            if (parejaNew != null) {
                parejaNew = em.getReference(parejaNew.getClass(), parejaNew.getUsuario());
                usuarioRueda.setPareja(parejaNew);
            }
            usuarioRueda = em.merge(usuarioRueda);
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.setUsuarioRueda(null);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.setUsuarioRueda(usuarioRueda);
                usuarioNew = em.merge(usuarioNew);
            }
            if (parejaOld != null && !parejaOld.equals(parejaNew)) {
                parejaOld.setUsuarioRueda(null);
                parejaOld = em.merge(parejaOld);
            }
            if (parejaNew != null && !parejaNew.equals(parejaOld)) {
                UsuarioRueda oldUsuarioRuedaOfPareja = parejaNew.getUsuarioRueda();
                if (oldUsuarioRuedaOfPareja != null) {
                    oldUsuarioRuedaOfPareja.setPareja(null);
                    oldUsuarioRuedaOfPareja = em.merge(oldUsuarioRuedaOfPareja);
                }
                parejaNew.setUsuarioRueda(usuarioRueda);
                parejaNew = em.merge(parejaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = usuarioRueda.getUsuariomante();
                if (findUsuarioRueda(id) == null) {
                    throw new NonexistentEntityException("The usuarioRueda with id " + id + " no longer exists.");
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
            UsuarioRueda usuarioRueda;
            try {
                usuarioRueda = em.getReference(UsuarioRueda.class, id);
                usuarioRueda.getUsuariomante();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarioRueda with id " + id + " no longer exists.", enfe);
            }
            Usuario usuario = usuarioRueda.getUsuario();
            if (usuario != null) {
                usuario.setUsuarioRueda(null);
                usuario = em.merge(usuario);
            }
            Usuario pareja = usuarioRueda.getPareja();
            if (pareja != null) {
                pareja.setUsuarioRueda(null);
                pareja = em.merge(pareja);
            }
            em.remove(usuarioRueda);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UsuarioRueda> findUsuarioRuedaEntities() {
        return findUsuarioRuedaEntities(true, -1, -1);
    }

    public List<UsuarioRueda> findUsuarioRuedaEntities(int maxResults, int firstResult) {
        return findUsuarioRuedaEntities(false, maxResults, firstResult);
    }

    private List<UsuarioRueda> findUsuarioRuedaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UsuarioRueda.class));
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

    public UsuarioRueda findUsuarioRueda(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UsuarioRueda.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioRuedaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UsuarioRueda> rt = cq.from(UsuarioRueda.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
