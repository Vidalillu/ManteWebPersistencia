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
import com.seidor.jmanteweb.persistencia.entidades.EmpresaColaboradora;
import com.seidor.jmanteweb.persistencia.entidades.EmpresaProveedor;
import com.seidor.jmanteweb.persistencia.entidades.EmpresaProveedorPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class EmpresaProveedorJpaController implements Serializable {

    public EmpresaProveedorJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EmpresaProveedor empresaProveedor) throws PreexistingEntityException, Exception {
        if (empresaProveedor.getEmpresaProveedorPK() == null) {
            empresaProveedor.setEmpresaProveedorPK(new EmpresaProveedorPK());
        }
        empresaProveedor.getEmpresaProveedorPK().setNombre(empresaProveedor.getEmpresaColaboradora().getNombre());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EmpresaColaboradora empresaColaboradora = empresaProveedor.getEmpresaColaboradora();
            if (empresaColaboradora != null) {
                empresaColaboradora = em.getReference(empresaColaboradora.getClass(), empresaColaboradora.getNombre());
                empresaProveedor.setEmpresaColaboradora(empresaColaboradora);
            }
            em.persist(empresaProveedor);
            if (empresaColaboradora != null) {
                empresaColaboradora.getEmpresaProveedorList().add(empresaProveedor);
                empresaColaboradora = em.merge(empresaColaboradora);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmpresaProveedor(empresaProveedor.getEmpresaProveedorPK()) != null) {
                throw new PreexistingEntityException("EmpresaProveedor " + empresaProveedor + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EmpresaProveedor empresaProveedor) throws NonexistentEntityException, Exception {
        empresaProveedor.getEmpresaProveedorPK().setNombre(empresaProveedor.getEmpresaColaboradora().getNombre());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EmpresaProveedor persistentEmpresaProveedor = em.find(EmpresaProveedor.class, empresaProveedor.getEmpresaProveedorPK());
            EmpresaColaboradora empresaColaboradoraOld = persistentEmpresaProveedor.getEmpresaColaboradora();
            EmpresaColaboradora empresaColaboradoraNew = empresaProveedor.getEmpresaColaboradora();
            if (empresaColaboradoraNew != null) {
                empresaColaboradoraNew = em.getReference(empresaColaboradoraNew.getClass(), empresaColaboradoraNew.getNombre());
                empresaProveedor.setEmpresaColaboradora(empresaColaboradoraNew);
            }
            empresaProveedor = em.merge(empresaProveedor);
            if (empresaColaboradoraOld != null && !empresaColaboradoraOld.equals(empresaColaboradoraNew)) {
                empresaColaboradoraOld.getEmpresaProveedorList().remove(empresaProveedor);
                empresaColaboradoraOld = em.merge(empresaColaboradoraOld);
            }
            if (empresaColaboradoraNew != null && !empresaColaboradoraNew.equals(empresaColaboradoraOld)) {
                empresaColaboradoraNew.getEmpresaProveedorList().add(empresaProveedor);
                empresaColaboradoraNew = em.merge(empresaColaboradoraNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                EmpresaProveedorPK id = empresaProveedor.getEmpresaProveedorPK();
                if (findEmpresaProveedor(id) == null) {
                    throw new NonexistentEntityException("The empresaProveedor with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(EmpresaProveedorPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EmpresaProveedor empresaProveedor;
            try {
                empresaProveedor = em.getReference(EmpresaProveedor.class, id);
                empresaProveedor.getEmpresaProveedorPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empresaProveedor with id " + id + " no longer exists.", enfe);
            }
            EmpresaColaboradora empresaColaboradora = empresaProveedor.getEmpresaColaboradora();
            if (empresaColaboradora != null) {
                empresaColaboradora.getEmpresaProveedorList().remove(empresaProveedor);
                empresaColaboradora = em.merge(empresaColaboradora);
            }
            em.remove(empresaProveedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EmpresaProveedor> findEmpresaProveedorEntities() {
        return findEmpresaProveedorEntities(true, -1, -1);
    }

    public List<EmpresaProveedor> findEmpresaProveedorEntities(int maxResults, int firstResult) {
        return findEmpresaProveedorEntities(false, maxResults, firstResult);
    }

    private List<EmpresaProveedor> findEmpresaProveedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EmpresaProveedor.class));
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

    public EmpresaProveedor findEmpresaProveedor(EmpresaProveedorPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EmpresaProveedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpresaProveedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EmpresaProveedor> rt = cq.from(EmpresaProveedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
