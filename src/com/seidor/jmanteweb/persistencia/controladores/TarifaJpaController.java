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
import com.seidor.jmanteweb.persistencia.entidades.ServiciosMante;
import java.util.ArrayList;
import java.util.List;
import com.seidor.jmanteweb.persistencia.entidades.IncidenciaParte;
import com.seidor.jmanteweb.persistencia.entidades.Clientes;
import com.seidor.jmanteweb.persistencia.entidades.Tarifa;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class TarifaJpaController implements Serializable {

    public TarifaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tarifa tarifa) throws PreexistingEntityException, Exception {
        if (tarifa.getServiciosManteList() == null) {
            tarifa.setServiciosManteList(new ArrayList<ServiciosMante>());
        }
        if (tarifa.getIncidenciaParteList() == null) {
            tarifa.setIncidenciaParteList(new ArrayList<IncidenciaParte>());
        }
        if (tarifa.getClientesList() == null) {
            tarifa.setClientesList(new ArrayList<Clientes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ServiciosMante> attachedServiciosManteList = new ArrayList<ServiciosMante>();
            for (ServiciosMante serviciosManteListServiciosManteToAttach : tarifa.getServiciosManteList()) {
                serviciosManteListServiciosManteToAttach = em.getReference(serviciosManteListServiciosManteToAttach.getClass(), serviciosManteListServiciosManteToAttach.getIdPeticion());
                attachedServiciosManteList.add(serviciosManteListServiciosManteToAttach);
            }
            tarifa.setServiciosManteList(attachedServiciosManteList);
            List<IncidenciaParte> attachedIncidenciaParteList = new ArrayList<IncidenciaParte>();
            for (IncidenciaParte incidenciaParteListIncidenciaParteToAttach : tarifa.getIncidenciaParteList()) {
                incidenciaParteListIncidenciaParteToAttach = em.getReference(incidenciaParteListIncidenciaParteToAttach.getClass(), incidenciaParteListIncidenciaParteToAttach.getNumParte());
                attachedIncidenciaParteList.add(incidenciaParteListIncidenciaParteToAttach);
            }
            tarifa.setIncidenciaParteList(attachedIncidenciaParteList);
            List<Clientes> attachedClientesList = new ArrayList<Clientes>();
            for (Clientes clientesListClientesToAttach : tarifa.getClientesList()) {
                clientesListClientesToAttach = em.getReference(clientesListClientesToAttach.getClass(), clientesListClientesToAttach.getCliente());
                attachedClientesList.add(clientesListClientesToAttach);
            }
            tarifa.setClientesList(attachedClientesList);
            em.persist(tarifa);
            for (ServiciosMante serviciosManteListServiciosMante : tarifa.getServiciosManteList()) {
                Tarifa oldTarifaOfServiciosManteListServiciosMante = serviciosManteListServiciosMante.getTarifa();
                serviciosManteListServiciosMante.setTarifa(tarifa);
                serviciosManteListServiciosMante = em.merge(serviciosManteListServiciosMante);
                if (oldTarifaOfServiciosManteListServiciosMante != null) {
                    oldTarifaOfServiciosManteListServiciosMante.getServiciosManteList().remove(serviciosManteListServiciosMante);
                    oldTarifaOfServiciosManteListServiciosMante = em.merge(oldTarifaOfServiciosManteListServiciosMante);
                }
            }
            for (IncidenciaParte incidenciaParteListIncidenciaParte : tarifa.getIncidenciaParteList()) {
                Tarifa oldTarifaOfIncidenciaParteListIncidenciaParte = incidenciaParteListIncidenciaParte.getTarifa();
                incidenciaParteListIncidenciaParte.setTarifa(tarifa);
                incidenciaParteListIncidenciaParte = em.merge(incidenciaParteListIncidenciaParte);
                if (oldTarifaOfIncidenciaParteListIncidenciaParte != null) {
                    oldTarifaOfIncidenciaParteListIncidenciaParte.getIncidenciaParteList().remove(incidenciaParteListIncidenciaParte);
                    oldTarifaOfIncidenciaParteListIncidenciaParte = em.merge(oldTarifaOfIncidenciaParteListIncidenciaParte);
                }
            }
            for (Clientes clientesListClientes : tarifa.getClientesList()) {
                Tarifa oldTarifaOfClientesListClientes = clientesListClientes.getTarifa();
                clientesListClientes.setTarifa(tarifa);
                clientesListClientes = em.merge(clientesListClientes);
                if (oldTarifaOfClientesListClientes != null) {
                    oldTarifaOfClientesListClientes.getClientesList().remove(clientesListClientes);
                    oldTarifaOfClientesListClientes = em.merge(oldTarifaOfClientesListClientes);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTarifa(tarifa.getTarifa()) != null) {
                throw new PreexistingEntityException("Tarifa " + tarifa + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tarifa tarifa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tarifa persistentTarifa = em.find(Tarifa.class, tarifa.getTarifa());
            List<ServiciosMante> serviciosManteListOld = persistentTarifa.getServiciosManteList();
            List<ServiciosMante> serviciosManteListNew = tarifa.getServiciosManteList();
            List<IncidenciaParte> incidenciaParteListOld = persistentTarifa.getIncidenciaParteList();
            List<IncidenciaParte> incidenciaParteListNew = tarifa.getIncidenciaParteList();
            List<Clientes> clientesListOld = persistentTarifa.getClientesList();
            List<Clientes> clientesListNew = tarifa.getClientesList();
            List<ServiciosMante> attachedServiciosManteListNew = new ArrayList<ServiciosMante>();
            for (ServiciosMante serviciosManteListNewServiciosManteToAttach : serviciosManteListNew) {
                serviciosManteListNewServiciosManteToAttach = em.getReference(serviciosManteListNewServiciosManteToAttach.getClass(), serviciosManteListNewServiciosManteToAttach.getIdPeticion());
                attachedServiciosManteListNew.add(serviciosManteListNewServiciosManteToAttach);
            }
            serviciosManteListNew = attachedServiciosManteListNew;
            tarifa.setServiciosManteList(serviciosManteListNew);
            List<IncidenciaParte> attachedIncidenciaParteListNew = new ArrayList<IncidenciaParte>();
            for (IncidenciaParte incidenciaParteListNewIncidenciaParteToAttach : incidenciaParteListNew) {
                incidenciaParteListNewIncidenciaParteToAttach = em.getReference(incidenciaParteListNewIncidenciaParteToAttach.getClass(), incidenciaParteListNewIncidenciaParteToAttach.getNumParte());
                attachedIncidenciaParteListNew.add(incidenciaParteListNewIncidenciaParteToAttach);
            }
            incidenciaParteListNew = attachedIncidenciaParteListNew;
            tarifa.setIncidenciaParteList(incidenciaParteListNew);
            List<Clientes> attachedClientesListNew = new ArrayList<Clientes>();
            for (Clientes clientesListNewClientesToAttach : clientesListNew) {
                clientesListNewClientesToAttach = em.getReference(clientesListNewClientesToAttach.getClass(), clientesListNewClientesToAttach.getCliente());
                attachedClientesListNew.add(clientesListNewClientesToAttach);
            }
            clientesListNew = attachedClientesListNew;
            tarifa.setClientesList(clientesListNew);
            tarifa = em.merge(tarifa);
            for (ServiciosMante serviciosManteListOldServiciosMante : serviciosManteListOld) {
                if (!serviciosManteListNew.contains(serviciosManteListOldServiciosMante)) {
                    serviciosManteListOldServiciosMante.setTarifa(null);
                    serviciosManteListOldServiciosMante = em.merge(serviciosManteListOldServiciosMante);
                }
            }
            for (ServiciosMante serviciosManteListNewServiciosMante : serviciosManteListNew) {
                if (!serviciosManteListOld.contains(serviciosManteListNewServiciosMante)) {
                    Tarifa oldTarifaOfServiciosManteListNewServiciosMante = serviciosManteListNewServiciosMante.getTarifa();
                    serviciosManteListNewServiciosMante.setTarifa(tarifa);
                    serviciosManteListNewServiciosMante = em.merge(serviciosManteListNewServiciosMante);
                    if (oldTarifaOfServiciosManteListNewServiciosMante != null && !oldTarifaOfServiciosManteListNewServiciosMante.equals(tarifa)) {
                        oldTarifaOfServiciosManteListNewServiciosMante.getServiciosManteList().remove(serviciosManteListNewServiciosMante);
                        oldTarifaOfServiciosManteListNewServiciosMante = em.merge(oldTarifaOfServiciosManteListNewServiciosMante);
                    }
                }
            }
            for (IncidenciaParte incidenciaParteListOldIncidenciaParte : incidenciaParteListOld) {
                if (!incidenciaParteListNew.contains(incidenciaParteListOldIncidenciaParte)) {
                    incidenciaParteListOldIncidenciaParte.setTarifa(null);
                    incidenciaParteListOldIncidenciaParte = em.merge(incidenciaParteListOldIncidenciaParte);
                }
            }
            for (IncidenciaParte incidenciaParteListNewIncidenciaParte : incidenciaParteListNew) {
                if (!incidenciaParteListOld.contains(incidenciaParteListNewIncidenciaParte)) {
                    Tarifa oldTarifaOfIncidenciaParteListNewIncidenciaParte = incidenciaParteListNewIncidenciaParte.getTarifa();
                    incidenciaParteListNewIncidenciaParte.setTarifa(tarifa);
                    incidenciaParteListNewIncidenciaParte = em.merge(incidenciaParteListNewIncidenciaParte);
                    if (oldTarifaOfIncidenciaParteListNewIncidenciaParte != null && !oldTarifaOfIncidenciaParteListNewIncidenciaParte.equals(tarifa)) {
                        oldTarifaOfIncidenciaParteListNewIncidenciaParte.getIncidenciaParteList().remove(incidenciaParteListNewIncidenciaParte);
                        oldTarifaOfIncidenciaParteListNewIncidenciaParte = em.merge(oldTarifaOfIncidenciaParteListNewIncidenciaParte);
                    }
                }
            }
            for (Clientes clientesListOldClientes : clientesListOld) {
                if (!clientesListNew.contains(clientesListOldClientes)) {
                    clientesListOldClientes.setTarifa(null);
                    clientesListOldClientes = em.merge(clientesListOldClientes);
                }
            }
            for (Clientes clientesListNewClientes : clientesListNew) {
                if (!clientesListOld.contains(clientesListNewClientes)) {
                    Tarifa oldTarifaOfClientesListNewClientes = clientesListNewClientes.getTarifa();
                    clientesListNewClientes.setTarifa(tarifa);
                    clientesListNewClientes = em.merge(clientesListNewClientes);
                    if (oldTarifaOfClientesListNewClientes != null && !oldTarifaOfClientesListNewClientes.equals(tarifa)) {
                        oldTarifaOfClientesListNewClientes.getClientesList().remove(clientesListNewClientes);
                        oldTarifaOfClientesListNewClientes = em.merge(oldTarifaOfClientesListNewClientes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = tarifa.getTarifa();
                if (findTarifa(id) == null) {
                    throw new NonexistentEntityException("The tarifa with id " + id + " no longer exists.");
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
            Tarifa tarifa;
            try {
                tarifa = em.getReference(Tarifa.class, id);
                tarifa.getTarifa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tarifa with id " + id + " no longer exists.", enfe);
            }
            List<ServiciosMante> serviciosManteList = tarifa.getServiciosManteList();
            for (ServiciosMante serviciosManteListServiciosMante : serviciosManteList) {
                serviciosManteListServiciosMante.setTarifa(null);
                serviciosManteListServiciosMante = em.merge(serviciosManteListServiciosMante);
            }
            List<IncidenciaParte> incidenciaParteList = tarifa.getIncidenciaParteList();
            for (IncidenciaParte incidenciaParteListIncidenciaParte : incidenciaParteList) {
                incidenciaParteListIncidenciaParte.setTarifa(null);
                incidenciaParteListIncidenciaParte = em.merge(incidenciaParteListIncidenciaParte);
            }
            List<Clientes> clientesList = tarifa.getClientesList();
            for (Clientes clientesListClientes : clientesList) {
                clientesListClientes.setTarifa(null);
                clientesListClientes = em.merge(clientesListClientes);
            }
            em.remove(tarifa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tarifa> findTarifaEntities() {
        return findTarifaEntities(true, -1, -1);
    }

    public List<Tarifa> findTarifaEntities(int maxResults, int firstResult) {
        return findTarifaEntities(false, maxResults, firstResult);
    }

    private List<Tarifa> findTarifaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tarifa.class));
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

    public Tarifa findTarifa(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tarifa.class, id);
        } finally {
            em.close();
        }
    }

    public int getTarifaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tarifa> rt = cq.from(Tarifa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
