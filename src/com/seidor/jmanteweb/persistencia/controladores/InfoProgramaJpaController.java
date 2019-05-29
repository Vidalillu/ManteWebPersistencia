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
import com.seidor.jmanteweb.persistencia.entidades.InfoPrograma;
import com.seidor.jmanteweb.persistencia.entidades.InfoProgramaPK;
import com.seidor.jmanteweb.persistencia.entidades.Partes;
import com.seidor.jmanteweb.persistencia.entidades.Programas;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

/**
 *
 * @author AMARTEL
 */
public class InfoProgramaJpaController implements Serializable {

    public InfoProgramaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(InfoPrograma infoPrograma) throws PreexistingEntityException, Exception {
        if (infoPrograma.getInfoProgramaPK() == null) {
            infoPrograma.setInfoProgramaPK(new InfoProgramaPK());
        }
        infoPrograma.getInfoProgramaPK().setPrograma(infoPrograma.getProgramas().getPrograma());
        infoPrograma.getInfoProgramaPK().setCliente(infoPrograma.getClientes().getCliente());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes clientes = infoPrograma.getClientes();
            if (clientes != null) {
                clientes = em.getReference(clientes.getClass(), clientes.getCliente());
                infoPrograma.setClientes(clientes);
            }
            Programas programas = infoPrograma.getProgramas();
            if (programas != null) {
                programas = em.getReference(programas.getClass(), programas.getPrograma());
                infoPrograma.setProgramas(programas);
            }
            em.persist(infoPrograma);
            if (clientes != null) {
                clientes.getInfoProgramaList().add(infoPrograma);
                clientes = em.merge(clientes);
            }
            if (programas != null) {
                programas.getInfoProgramaList().add(infoPrograma);
                programas = em.merge(programas);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findInfoPrograma(infoPrograma.getInfoProgramaPK()) != null) {
                throw new PreexistingEntityException("InfoPrograma " + infoPrograma + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(InfoPrograma infoPrograma) throws NonexistentEntityException, Exception {
        infoPrograma.getInfoProgramaPK().setPrograma(infoPrograma.getProgramas().getPrograma());
        infoPrograma.getInfoProgramaPK().setCliente(infoPrograma.getClientes().getCliente());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            InfoPrograma persistentInfoPrograma = em.find(InfoPrograma.class, infoPrograma.getInfoProgramaPK());
            Clientes clientesOld = persistentInfoPrograma.getClientes();
            Clientes clientesNew = infoPrograma.getClientes();
            Programas programasOld = persistentInfoPrograma.getProgramas();
            Programas programasNew = infoPrograma.getProgramas();
            if (clientesNew != null) {
                clientesNew = em.getReference(clientesNew.getClass(), clientesNew.getCliente());
                infoPrograma.setClientes(clientesNew);
            }
            if (programasNew != null) {
                programasNew = em.getReference(programasNew.getClass(), programasNew.getPrograma());
                infoPrograma.setProgramas(programasNew);
            }
            infoPrograma = em.merge(infoPrograma);
            if (clientesOld != null && !clientesOld.equals(clientesNew)) {
                clientesOld.getInfoProgramaList().remove(infoPrograma);
                clientesOld = em.merge(clientesOld);
            }
            if (clientesNew != null && !clientesNew.equals(clientesOld)) {
                clientesNew.getInfoProgramaList().add(infoPrograma);
                clientesNew = em.merge(clientesNew);
            }
            if (programasOld != null && !programasOld.equals(programasNew)) {
                programasOld.getInfoProgramaList().remove(infoPrograma);
                programasOld = em.merge(programasOld);
            }
            if (programasNew != null && !programasNew.equals(programasOld)) {
                programasNew.getInfoProgramaList().add(infoPrograma);
                programasNew = em.merge(programasNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                InfoProgramaPK id = infoPrograma.getInfoProgramaPK();
                if (findInfoPrograma(id) == null) {
                    throw new NonexistentEntityException("The infoPrograma with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(InfoProgramaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            InfoPrograma infoPrograma;
            try {
                infoPrograma = em.getReference(InfoPrograma.class, id);
                infoPrograma.getInfoProgramaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The infoPrograma with id " + id + " no longer exists.", enfe);
            }
            Clientes clientes = infoPrograma.getClientes();
            if (clientes != null) {
                clientes.getInfoProgramaList().remove(infoPrograma);
                clientes = em.merge(clientes);
            }
            Programas programas = infoPrograma.getProgramas();
            if (programas != null) {
                programas.getInfoProgramaList().remove(infoPrograma);
                programas = em.merge(programas);
            }
            em.remove(infoPrograma);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<InfoPrograma> findInfoProgramaEntities() {
        return findInfoProgramaEntities(true, -1, -1);
    }

    public List<InfoPrograma> findInfoProgramaEntities(int maxResults, int firstResult) {
        return findInfoProgramaEntities(false, maxResults, firstResult);
    }

    private List<InfoPrograma> findInfoProgramaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(InfoPrograma.class));
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

    public InfoPrograma findInfoPrograma(InfoProgramaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(InfoPrograma.class, id);
        } finally {
            em.close();
        }
    }

    public int getInfoProgramaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<InfoPrograma> rt = cq.from(InfoPrograma.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    /**
     * Metodo que obtiene los programas de un cliente
     * 
     * @param cliente
     * @return 
     */
    public List<InfoPrograma> findInfoProgramaCliente( String cliente ) throws PreexistingEntityException{
        
        EntityManager em = getEntityManager();
        try {
            
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery();
            Root infoPrograma = cq.from(InfoPrograma.class);
            Join joinCliente = infoPrograma.join("clientes");
            Predicate prCliente = cb.equal( joinCliente.get("cliente"), cliente);
            cq.where( prCliente );
            Query q = em.createQuery(cq);
            return q.getResultList();
            
        } 
        catch (Exception ex) {
            throw new PreexistingEntityException("Error al obtener el listado de programas para el cliente: " + cliente, ex);
        }
        finally {
            em.close();
        }
        
        
    }
    
}
