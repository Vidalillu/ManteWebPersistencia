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
import com.seidor.jmanteweb.persistencia.entidades.Clientes;
import com.seidor.jmanteweb.persistencia.entidades.Responsabilidad;
import com.seidor.jmanteweb.persistencia.entidades.ResponsabilidadPK;
import com.seidor.jmanteweb.persistencia.entidades.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class ResponsabilidadJpaController implements Serializable {

    public ResponsabilidadJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Responsabilidad responsabilidad) throws PreexistingEntityException, Exception {
        if (responsabilidad.getResponsabilidadPK() == null) {
            responsabilidad.setResponsabilidadPK(new ResponsabilidadPK());
        }
        responsabilidad.getResponsabilidadPK().setUsuarioMante(responsabilidad.getUsuario().getUsuario());
        responsabilidad.getResponsabilidadPK().setCliente(responsabilidad.getClientes().getCliente());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes clientes = responsabilidad.getClientes();
            if (clientes != null) {
                clientes = em.getReference(clientes.getClass(), clientes.getCliente());
                responsabilidad.setClientes(clientes);
            }
            Usuario usuario = responsabilidad.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getUsuario());
                responsabilidad.setUsuario(usuario);
            }
            em.persist(responsabilidad);
            if (clientes != null) {
                clientes.getResponsabilidadList().add(responsabilidad);
                clientes = em.merge(clientes);
            }
            if (usuario != null) {
                usuario.getResponsabilidadList().add(responsabilidad);
                usuario = em.merge(usuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findResponsabilidad(responsabilidad.getResponsabilidadPK()) != null) {
                throw new PreexistingEntityException("Responsabilidad " + responsabilidad + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Responsabilidad responsabilidad) throws NonexistentEntityException, Exception {
        responsabilidad.getResponsabilidadPK().setUsuarioMante(responsabilidad.getUsuario().getUsuario());
        responsabilidad.getResponsabilidadPK().setCliente(responsabilidad.getClientes().getCliente());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Responsabilidad persistentResponsabilidad = em.find(Responsabilidad.class, responsabilidad.getResponsabilidadPK());
            Clientes clientesOld = persistentResponsabilidad.getClientes();
            Clientes clientesNew = responsabilidad.getClientes();
            Usuario usuarioOld = persistentResponsabilidad.getUsuario();
            Usuario usuarioNew = responsabilidad.getUsuario();
            if (clientesNew != null) {
                clientesNew = em.getReference(clientesNew.getClass(), clientesNew.getCliente());
                responsabilidad.setClientes(clientesNew);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getUsuario());
                responsabilidad.setUsuario(usuarioNew);
            }
            responsabilidad = em.merge(responsabilidad);
            if (clientesOld != null && !clientesOld.equals(clientesNew)) {
                clientesOld.getResponsabilidadList().remove(responsabilidad);
                clientesOld = em.merge(clientesOld);
            }
            if (clientesNew != null && !clientesNew.equals(clientesOld)) {
                clientesNew.getResponsabilidadList().add(responsabilidad);
                clientesNew = em.merge(clientesNew);
            }
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getResponsabilidadList().remove(responsabilidad);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getResponsabilidadList().add(responsabilidad);
                usuarioNew = em.merge(usuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ResponsabilidadPK id = responsabilidad.getResponsabilidadPK();
                if (findResponsabilidad(id) == null) {
                    throw new NonexistentEntityException("The responsabilidad with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ResponsabilidadPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Responsabilidad responsabilidad;
            try {
                responsabilidad = em.getReference(Responsabilidad.class, id);
                responsabilidad.getResponsabilidadPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The responsabilidad with id " + id + " no longer exists.", enfe);
            }
            Clientes clientes = responsabilidad.getClientes();
            if (clientes != null) {
                clientes.getResponsabilidadList().remove(responsabilidad);
                clientes = em.merge(clientes);
            }
            Usuario usuario = responsabilidad.getUsuario();
            if (usuario != null) {
                usuario.getResponsabilidadList().remove(responsabilidad);
                usuario = em.merge(usuario);
            }
            em.remove(responsabilidad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Responsabilidad> findResponsabilidadEntities() {
        return findResponsabilidadEntities(true, -1, -1);
    }

    public List<Responsabilidad> findResponsabilidadEntities(int maxResults, int firstResult) {
        return findResponsabilidadEntities(false, maxResults, firstResult);
    }

    private List<Responsabilidad> findResponsabilidadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Responsabilidad.class));
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

    public Responsabilidad findResponsabilidad(ResponsabilidadPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Responsabilidad.class, id);
        } finally {
            em.close();
        }
    }

    public int getResponsabilidadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Responsabilidad> rt = cq.from(Responsabilidad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
