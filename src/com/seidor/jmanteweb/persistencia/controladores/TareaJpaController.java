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
import com.seidor.jmanteweb.persistencia.entidades.Clientes;
import com.seidor.jmanteweb.persistencia.entidades.Modis;
import com.seidor.jmanteweb.persistencia.entidades.Partes;
import com.seidor.jmanteweb.persistencia.entidades.Tarea;
import com.seidor.jmanteweb.persistencia.entidades.Usuario;
import com.seidor.jmanteweb.persistencia.entidades.UsuarioTarea;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class TareaJpaController implements Serializable {

    public TareaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tarea tarea) throws PreexistingEntityException, Exception {
        if (tarea.getUsuarioTareaList() == null) {
            tarea.setUsuarioTareaList(new ArrayList<UsuarioTarea>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes cliente = tarea.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getCliente());
                tarea.setCliente(cliente);
            }
            Modis idmodi = tarea.getIdmodi();
            if (idmodi != null) {
                idmodi = em.getReference(idmodi.getClass(), idmodi.getIdmodi());
                tarea.setIdmodi(idmodi);
            }
            Partes idparte = tarea.getIdparte();
            if (idparte != null) {
                idparte = em.getReference(idparte.getClass(), idparte.getNumParte());
                tarea.setIdparte(idparte);
            }
            Usuario usuario = tarea.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getUsuario());
                tarea.setUsuario(usuario);
            }
            List<UsuarioTarea> attachedUsuarioTareaList = new ArrayList<UsuarioTarea>();
            for (UsuarioTarea usuarioTareaListUsuarioTareaToAttach : tarea.getUsuarioTareaList()) {
                usuarioTareaListUsuarioTareaToAttach = em.getReference(usuarioTareaListUsuarioTareaToAttach.getClass(), usuarioTareaListUsuarioTareaToAttach.getId());
                attachedUsuarioTareaList.add(usuarioTareaListUsuarioTareaToAttach);
            }
            tarea.setUsuarioTareaList(attachedUsuarioTareaList);
            em.persist(tarea);
            if (cliente != null) {
                cliente.getTareaList().add(tarea);
                cliente = em.merge(cliente);
            }
            if (idmodi != null) {
                idmodi.getTareaList().add(tarea);
                idmodi = em.merge(idmodi);
            }
            if (idparte != null) {
                idparte.getTareaList().add(tarea);
                idparte = em.merge(idparte);
            }
            if (usuario != null) {
                usuario.getTareaList().add(tarea);
                usuario = em.merge(usuario);
            }
            for (UsuarioTarea usuarioTareaListUsuarioTarea : tarea.getUsuarioTareaList()) {
                Tarea oldIdtareaOfUsuarioTareaListUsuarioTarea = usuarioTareaListUsuarioTarea.getIdtarea();
                usuarioTareaListUsuarioTarea.setIdtarea(tarea);
                usuarioTareaListUsuarioTarea = em.merge(usuarioTareaListUsuarioTarea);
                if (oldIdtareaOfUsuarioTareaListUsuarioTarea != null) {
                    oldIdtareaOfUsuarioTareaListUsuarioTarea.getUsuarioTareaList().remove(usuarioTareaListUsuarioTarea);
                    oldIdtareaOfUsuarioTareaListUsuarioTarea = em.merge(oldIdtareaOfUsuarioTareaListUsuarioTarea);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTarea(tarea.getId()) != null) {
                throw new PreexistingEntityException("Tarea " + tarea + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tarea tarea) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tarea persistentTarea = em.find(Tarea.class, tarea.getId());
            Clientes clienteOld = persistentTarea.getCliente();
            Clientes clienteNew = tarea.getCliente();
            Modis idmodiOld = persistentTarea.getIdmodi();
            Modis idmodiNew = tarea.getIdmodi();
            Partes idparteOld = persistentTarea.getIdparte();
            Partes idparteNew = tarea.getIdparte();
            Usuario usuarioOld = persistentTarea.getUsuario();
            Usuario usuarioNew = tarea.getUsuario();
            List<UsuarioTarea> usuarioTareaListOld = persistentTarea.getUsuarioTareaList();
            List<UsuarioTarea> usuarioTareaListNew = tarea.getUsuarioTareaList();
            List<String> illegalOrphanMessages = null;
            for (UsuarioTarea usuarioTareaListOldUsuarioTarea : usuarioTareaListOld) {
                if (!usuarioTareaListNew.contains(usuarioTareaListOldUsuarioTarea)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UsuarioTarea " + usuarioTareaListOldUsuarioTarea + " since its idtarea field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getCliente());
                tarea.setCliente(clienteNew);
            }
            if (idmodiNew != null) {
                idmodiNew = em.getReference(idmodiNew.getClass(), idmodiNew.getIdmodi());
                tarea.setIdmodi(idmodiNew);
            }
            if (idparteNew != null) {
                idparteNew = em.getReference(idparteNew.getClass(), idparteNew.getNumParte());
                tarea.setIdparte(idparteNew);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getUsuario());
                tarea.setUsuario(usuarioNew);
            }
            List<UsuarioTarea> attachedUsuarioTareaListNew = new ArrayList<UsuarioTarea>();
            for (UsuarioTarea usuarioTareaListNewUsuarioTareaToAttach : usuarioTareaListNew) {
                usuarioTareaListNewUsuarioTareaToAttach = em.getReference(usuarioTareaListNewUsuarioTareaToAttach.getClass(), usuarioTareaListNewUsuarioTareaToAttach.getId());
                attachedUsuarioTareaListNew.add(usuarioTareaListNewUsuarioTareaToAttach);
            }
            usuarioTareaListNew = attachedUsuarioTareaListNew;
            tarea.setUsuarioTareaList(usuarioTareaListNew);
            tarea = em.merge(tarea);
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                clienteOld.getTareaList().remove(tarea);
                clienteOld = em.merge(clienteOld);
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                clienteNew.getTareaList().add(tarea);
                clienteNew = em.merge(clienteNew);
            }
            if (idmodiOld != null && !idmodiOld.equals(idmodiNew)) {
                idmodiOld.getTareaList().remove(tarea);
                idmodiOld = em.merge(idmodiOld);
            }
            if (idmodiNew != null && !idmodiNew.equals(idmodiOld)) {
                idmodiNew.getTareaList().add(tarea);
                idmodiNew = em.merge(idmodiNew);
            }
            if (idparteOld != null && !idparteOld.equals(idparteNew)) {
                idparteOld.getTareaList().remove(tarea);
                idparteOld = em.merge(idparteOld);
            }
            if (idparteNew != null && !idparteNew.equals(idparteOld)) {
                idparteNew.getTareaList().add(tarea);
                idparteNew = em.merge(idparteNew);
            }
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getTareaList().remove(tarea);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getTareaList().add(tarea);
                usuarioNew = em.merge(usuarioNew);
            }
            for (UsuarioTarea usuarioTareaListNewUsuarioTarea : usuarioTareaListNew) {
                if (!usuarioTareaListOld.contains(usuarioTareaListNewUsuarioTarea)) {
                    Tarea oldIdtareaOfUsuarioTareaListNewUsuarioTarea = usuarioTareaListNewUsuarioTarea.getIdtarea();
                    usuarioTareaListNewUsuarioTarea.setIdtarea(tarea);
                    usuarioTareaListNewUsuarioTarea = em.merge(usuarioTareaListNewUsuarioTarea);
                    if (oldIdtareaOfUsuarioTareaListNewUsuarioTarea != null && !oldIdtareaOfUsuarioTareaListNewUsuarioTarea.equals(tarea)) {
                        oldIdtareaOfUsuarioTareaListNewUsuarioTarea.getUsuarioTareaList().remove(usuarioTareaListNewUsuarioTarea);
                        oldIdtareaOfUsuarioTareaListNewUsuarioTarea = em.merge(oldIdtareaOfUsuarioTareaListNewUsuarioTarea);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = tarea.getId();
                if (findTarea(id) == null) {
                    throw new NonexistentEntityException("The tarea with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tarea tarea;
            try {
                tarea = em.getReference(Tarea.class, id);
                tarea.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tarea with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<UsuarioTarea> usuarioTareaListOrphanCheck = tarea.getUsuarioTareaList();
            for (UsuarioTarea usuarioTareaListOrphanCheckUsuarioTarea : usuarioTareaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tarea (" + tarea + ") cannot be destroyed since the UsuarioTarea " + usuarioTareaListOrphanCheckUsuarioTarea + " in its usuarioTareaList field has a non-nullable idtarea field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Clientes cliente = tarea.getCliente();
            if (cliente != null) {
                cliente.getTareaList().remove(tarea);
                cliente = em.merge(cliente);
            }
            Modis idmodi = tarea.getIdmodi();
            if (idmodi != null) {
                idmodi.getTareaList().remove(tarea);
                idmodi = em.merge(idmodi);
            }
            Partes idparte = tarea.getIdparte();
            if (idparte != null) {
                idparte.getTareaList().remove(tarea);
                idparte = em.merge(idparte);
            }
            Usuario usuario = tarea.getUsuario();
            if (usuario != null) {
                usuario.getTareaList().remove(tarea);
                usuario = em.merge(usuario);
            }
            em.remove(tarea);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tarea> findTareaEntities() {
        return findTareaEntities(true, -1, -1);
    }

    public List<Tarea> findTareaEntities(int maxResults, int firstResult) {
        return findTareaEntities(false, maxResults, firstResult);
    }

    private List<Tarea> findTareaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tarea.class));
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

    public Tarea findTarea(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tarea.class, id);
        } finally {
            em.close();
        }
    }

    public int getTareaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tarea> rt = cq.from(Tarea.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
