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
import com.seidor.jmanteweb.persistencia.entidades.EstadosCliente;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class EstadosClienteJpaController implements Serializable {

    public EstadosClienteJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstadosCliente estadosCliente) throws PreexistingEntityException, Exception {
        if (estadosCliente.getClientesList() == null) {
            estadosCliente.setClientesList(new ArrayList<Clientes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Clientes> attachedClientesList = new ArrayList<Clientes>();
            for (Clientes clientesListClientesToAttach : estadosCliente.getClientesList()) {
                clientesListClientesToAttach = em.getReference(clientesListClientesToAttach.getClass(), clientesListClientesToAttach.getCliente());
                attachedClientesList.add(clientesListClientesToAttach);
            }
            estadosCliente.setClientesList(attachedClientesList);
            em.persist(estadosCliente);
            for (Clientes clientesListClientes : estadosCliente.getClientesList()) {
                EstadosCliente oldEstadoOfClientesListClientes = clientesListClientes.getEstado();
                clientesListClientes.setEstado(estadosCliente);
                clientesListClientes = em.merge(clientesListClientes);
                if (oldEstadoOfClientesListClientes != null) {
                    oldEstadoOfClientesListClientes.getClientesList().remove(clientesListClientes);
                    oldEstadoOfClientesListClientes = em.merge(oldEstadoOfClientesListClientes);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstadosCliente(estadosCliente.getEstado()) != null) {
                throw new PreexistingEntityException("EstadosCliente " + estadosCliente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EstadosCliente estadosCliente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EstadosCliente persistentEstadosCliente = em.find(EstadosCliente.class, estadosCliente.getEstado());
            List<Clientes> clientesListOld = persistentEstadosCliente.getClientesList();
            List<Clientes> clientesListNew = estadosCliente.getClientesList();
            List<Clientes> attachedClientesListNew = new ArrayList<Clientes>();
            for (Clientes clientesListNewClientesToAttach : clientesListNew) {
                clientesListNewClientesToAttach = em.getReference(clientesListNewClientesToAttach.getClass(), clientesListNewClientesToAttach.getCliente());
                attachedClientesListNew.add(clientesListNewClientesToAttach);
            }
            clientesListNew = attachedClientesListNew;
            estadosCliente.setClientesList(clientesListNew);
            estadosCliente = em.merge(estadosCliente);
            for (Clientes clientesListOldClientes : clientesListOld) {
                if (!clientesListNew.contains(clientesListOldClientes)) {
                    clientesListOldClientes.setEstado(null);
                    clientesListOldClientes = em.merge(clientesListOldClientes);
                }
            }
            for (Clientes clientesListNewClientes : clientesListNew) {
                if (!clientesListOld.contains(clientesListNewClientes)) {
                    EstadosCliente oldEstadoOfClientesListNewClientes = clientesListNewClientes.getEstado();
                    clientesListNewClientes.setEstado(estadosCliente);
                    clientesListNewClientes = em.merge(clientesListNewClientes);
                    if (oldEstadoOfClientesListNewClientes != null && !oldEstadoOfClientesListNewClientes.equals(estadosCliente)) {
                        oldEstadoOfClientesListNewClientes.getClientesList().remove(clientesListNewClientes);
                        oldEstadoOfClientesListNewClientes = em.merge(oldEstadoOfClientesListNewClientes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = estadosCliente.getEstado();
                if (findEstadosCliente(id) == null) {
                    throw new NonexistentEntityException("The estadosCliente with id " + id + " no longer exists.");
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
            EstadosCliente estadosCliente;
            try {
                estadosCliente = em.getReference(EstadosCliente.class, id);
                estadosCliente.getEstado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadosCliente with id " + id + " no longer exists.", enfe);
            }
            List<Clientes> clientesList = estadosCliente.getClientesList();
            for (Clientes clientesListClientes : clientesList) {
                clientesListClientes.setEstado(null);
                clientesListClientes = em.merge(clientesListClientes);
            }
            em.remove(estadosCliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EstadosCliente> findEstadosClienteEntities() {
        return findEstadosClienteEntities(true, -1, -1);
    }

    public List<EstadosCliente> findEstadosClienteEntities(int maxResults, int firstResult) {
        return findEstadosClienteEntities(false, maxResults, firstResult);
    }

    private List<EstadosCliente> findEstadosClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EstadosCliente.class));
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

    public EstadosCliente findEstadosCliente(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstadosCliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadosClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EstadosCliente> rt = cq.from(EstadosCliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
