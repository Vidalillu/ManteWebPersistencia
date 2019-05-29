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
import com.seidor.jmanteweb.persistencia.entidades.Peticiones;
import com.seidor.jmanteweb.persistencia.entidades.ServiciosMante;
import com.seidor.jmanteweb.persistencia.entidades.Tarifa;
import java.util.ArrayList;
import java.util.List;
import com.seidor.jmanteweb.persistencia.entidades.Clientes;
import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class ServiciosManteJpaController implements Serializable {

    public ServiciosManteJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ServiciosMante serviciosMante) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (serviciosMante.getServiciosManteList() == null) {
            serviciosMante.setServiciosManteList(new ArrayList<ServiciosMante>());
        }
        if (serviciosMante.getClientesList() == null) {
            serviciosMante.setClientesList(new ArrayList<Clientes>());
        }
        List<String> illegalOrphanMessages = null;
        Peticiones peticionesOrphanCheck = serviciosMante.getPeticiones();
        if (peticionesOrphanCheck != null) {
            ServiciosMante oldServiciosManteOfPeticiones = peticionesOrphanCheck.getServiciosMante();
            if (oldServiciosManteOfPeticiones != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Peticiones " + peticionesOrphanCheck + " already has an item of type ServiciosMante whose peticiones column cannot be null. Please make another selection for the peticiones field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Peticiones peticiones = serviciosMante.getPeticiones();
            if (peticiones != null) {
                peticiones = em.getReference(peticiones.getClass(), peticiones.getIdpeticion());
                serviciosMante.setPeticiones(peticiones);
            }
            ServiciosMante serviciomantePadre = serviciosMante.getServiciomantePadre();
            if (serviciomantePadre != null) {
                serviciomantePadre = em.getReference(serviciomantePadre.getClass(), serviciomantePadre.getIdPeticion());
                serviciosMante.setServiciomantePadre(serviciomantePadre);
            }
            Tarifa tarifa = serviciosMante.getTarifa();
            if (tarifa != null) {
                tarifa = em.getReference(tarifa.getClass(), tarifa.getTarifa());
                serviciosMante.setTarifa(tarifa);
            }
            List<ServiciosMante> attachedServiciosManteList = new ArrayList<ServiciosMante>();
            for (ServiciosMante serviciosManteListServiciosManteToAttach : serviciosMante.getServiciosManteList()) {
                serviciosManteListServiciosManteToAttach = em.getReference(serviciosManteListServiciosManteToAttach.getClass(), serviciosManteListServiciosManteToAttach.getIdPeticion());
                attachedServiciosManteList.add(serviciosManteListServiciosManteToAttach);
            }
            serviciosMante.setServiciosManteList(attachedServiciosManteList);
            List<Clientes> attachedClientesList = new ArrayList<Clientes>();
            for (Clientes clientesListClientesToAttach : serviciosMante.getClientesList()) {
                clientesListClientesToAttach = em.getReference(clientesListClientesToAttach.getClass(), clientesListClientesToAttach.getCliente());
                attachedClientesList.add(clientesListClientesToAttach);
            }
            serviciosMante.setClientesList(attachedClientesList);
            em.persist(serviciosMante);
            if (peticiones != null) {
                peticiones.setServiciosMante(serviciosMante);
                peticiones = em.merge(peticiones);
            }
            if (serviciomantePadre != null) {
                serviciomantePadre.getServiciosManteList().add(serviciosMante);
                serviciomantePadre = em.merge(serviciomantePadre);
            }
            if (tarifa != null) {
                tarifa.getServiciosManteList().add(serviciosMante);
                tarifa = em.merge(tarifa);
            }
            for (ServiciosMante serviciosManteListServiciosMante : serviciosMante.getServiciosManteList()) {
                ServiciosMante oldServiciomantePadreOfServiciosManteListServiciosMante = serviciosManteListServiciosMante.getServiciomantePadre();
                serviciosManteListServiciosMante.setServiciomantePadre(serviciosMante);
                serviciosManteListServiciosMante = em.merge(serviciosManteListServiciosMante);
                if (oldServiciomantePadreOfServiciosManteListServiciosMante != null) {
                    oldServiciomantePadreOfServiciosManteListServiciosMante.getServiciosManteList().remove(serviciosManteListServiciosMante);
                    oldServiciomantePadreOfServiciosManteListServiciosMante = em.merge(oldServiciomantePadreOfServiciosManteListServiciosMante);
                }
            }
            for (Clientes clientesListClientes : serviciosMante.getClientesList()) {
                ServiciosMante oldIdServiciomanteActivoOfClientesListClientes = clientesListClientes.getIdServiciomanteActivo();
                clientesListClientes.setIdServiciomanteActivo(serviciosMante);
                clientesListClientes = em.merge(clientesListClientes);
                if (oldIdServiciomanteActivoOfClientesListClientes != null) {
                    oldIdServiciomanteActivoOfClientesListClientes.getClientesList().remove(clientesListClientes);
                    oldIdServiciomanteActivoOfClientesListClientes = em.merge(oldIdServiciomanteActivoOfClientesListClientes);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findServiciosMante(serviciosMante.getIdPeticion()) != null) {
                throw new PreexistingEntityException("ServiciosMante " + serviciosMante + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ServiciosMante serviciosMante) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ServiciosMante persistentServiciosMante = em.find(ServiciosMante.class, serviciosMante.getIdPeticion());
            Peticiones peticionesOld = persistentServiciosMante.getPeticiones();
            Peticiones peticionesNew = serviciosMante.getPeticiones();
            ServiciosMante serviciomantePadreOld = persistentServiciosMante.getServiciomantePadre();
            ServiciosMante serviciomantePadreNew = serviciosMante.getServiciomantePadre();
            Tarifa tarifaOld = persistentServiciosMante.getTarifa();
            Tarifa tarifaNew = serviciosMante.getTarifa();
            List<ServiciosMante> serviciosManteListOld = persistentServiciosMante.getServiciosManteList();
            List<ServiciosMante> serviciosManteListNew = serviciosMante.getServiciosManteList();
            List<Clientes> clientesListOld = persistentServiciosMante.getClientesList();
            List<Clientes> clientesListNew = serviciosMante.getClientesList();
            List<String> illegalOrphanMessages = null;
            if (peticionesNew != null && !peticionesNew.equals(peticionesOld)) {
                ServiciosMante oldServiciosManteOfPeticiones = peticionesNew.getServiciosMante();
                if (oldServiciosManteOfPeticiones != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Peticiones " + peticionesNew + " already has an item of type ServiciosMante whose peticiones column cannot be null. Please make another selection for the peticiones field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (peticionesNew != null) {
                peticionesNew = em.getReference(peticionesNew.getClass(), peticionesNew.getIdpeticion());
                serviciosMante.setPeticiones(peticionesNew);
            }
            if (serviciomantePadreNew != null) {
                serviciomantePadreNew = em.getReference(serviciomantePadreNew.getClass(), serviciomantePadreNew.getIdPeticion());
                serviciosMante.setServiciomantePadre(serviciomantePadreNew);
            }
            if (tarifaNew != null) {
                tarifaNew = em.getReference(tarifaNew.getClass(), tarifaNew.getTarifa());
                serviciosMante.setTarifa(tarifaNew);
            }
            List<ServiciosMante> attachedServiciosManteListNew = new ArrayList<ServiciosMante>();
            for (ServiciosMante serviciosManteListNewServiciosManteToAttach : serviciosManteListNew) {
                serviciosManteListNewServiciosManteToAttach = em.getReference(serviciosManteListNewServiciosManteToAttach.getClass(), serviciosManteListNewServiciosManteToAttach.getIdPeticion());
                attachedServiciosManteListNew.add(serviciosManteListNewServiciosManteToAttach);
            }
            serviciosManteListNew = attachedServiciosManteListNew;
            serviciosMante.setServiciosManteList(serviciosManteListNew);
            List<Clientes> attachedClientesListNew = new ArrayList<Clientes>();
            for (Clientes clientesListNewClientesToAttach : clientesListNew) {
                clientesListNewClientesToAttach = em.getReference(clientesListNewClientesToAttach.getClass(), clientesListNewClientesToAttach.getCliente());
                attachedClientesListNew.add(clientesListNewClientesToAttach);
            }
            clientesListNew = attachedClientesListNew;
            serviciosMante.setClientesList(clientesListNew);
            serviciosMante = em.merge(serviciosMante);
            if (peticionesOld != null && !peticionesOld.equals(peticionesNew)) {
                peticionesOld.setServiciosMante(null);
                peticionesOld = em.merge(peticionesOld);
            }
            if (peticionesNew != null && !peticionesNew.equals(peticionesOld)) {
                peticionesNew.setServiciosMante(serviciosMante);
                peticionesNew = em.merge(peticionesNew);
            }
            if (serviciomantePadreOld != null && !serviciomantePadreOld.equals(serviciomantePadreNew)) {
                serviciomantePadreOld.getServiciosManteList().remove(serviciosMante);
                serviciomantePadreOld = em.merge(serviciomantePadreOld);
            }
            if (serviciomantePadreNew != null && !serviciomantePadreNew.equals(serviciomantePadreOld)) {
                serviciomantePadreNew.getServiciosManteList().add(serviciosMante);
                serviciomantePadreNew = em.merge(serviciomantePadreNew);
            }
            if (tarifaOld != null && !tarifaOld.equals(tarifaNew)) {
                tarifaOld.getServiciosManteList().remove(serviciosMante);
                tarifaOld = em.merge(tarifaOld);
            }
            if (tarifaNew != null && !tarifaNew.equals(tarifaOld)) {
                tarifaNew.getServiciosManteList().add(serviciosMante);
                tarifaNew = em.merge(tarifaNew);
            }
            for (ServiciosMante serviciosManteListOldServiciosMante : serviciosManteListOld) {
                if (!serviciosManteListNew.contains(serviciosManteListOldServiciosMante)) {
                    serviciosManteListOldServiciosMante.setServiciomantePadre(null);
                    serviciosManteListOldServiciosMante = em.merge(serviciosManteListOldServiciosMante);
                }
            }
            for (ServiciosMante serviciosManteListNewServiciosMante : serviciosManteListNew) {
                if (!serviciosManteListOld.contains(serviciosManteListNewServiciosMante)) {
                    ServiciosMante oldServiciomantePadreOfServiciosManteListNewServiciosMante = serviciosManteListNewServiciosMante.getServiciomantePadre();
                    serviciosManteListNewServiciosMante.setServiciomantePadre(serviciosMante);
                    serviciosManteListNewServiciosMante = em.merge(serviciosManteListNewServiciosMante);
                    if (oldServiciomantePadreOfServiciosManteListNewServiciosMante != null && !oldServiciomantePadreOfServiciosManteListNewServiciosMante.equals(serviciosMante)) {
                        oldServiciomantePadreOfServiciosManteListNewServiciosMante.getServiciosManteList().remove(serviciosManteListNewServiciosMante);
                        oldServiciomantePadreOfServiciosManteListNewServiciosMante = em.merge(oldServiciomantePadreOfServiciosManteListNewServiciosMante);
                    }
                }
            }
            for (Clientes clientesListOldClientes : clientesListOld) {
                if (!clientesListNew.contains(clientesListOldClientes)) {
                    clientesListOldClientes.setIdServiciomanteActivo(null);
                    clientesListOldClientes = em.merge(clientesListOldClientes);
                }
            }
            for (Clientes clientesListNewClientes : clientesListNew) {
                if (!clientesListOld.contains(clientesListNewClientes)) {
                    ServiciosMante oldIdServiciomanteActivoOfClientesListNewClientes = clientesListNewClientes.getIdServiciomanteActivo();
                    clientesListNewClientes.setIdServiciomanteActivo(serviciosMante);
                    clientesListNewClientes = em.merge(clientesListNewClientes);
                    if (oldIdServiciomanteActivoOfClientesListNewClientes != null && !oldIdServiciomanteActivoOfClientesListNewClientes.equals(serviciosMante)) {
                        oldIdServiciomanteActivoOfClientesListNewClientes.getClientesList().remove(clientesListNewClientes);
                        oldIdServiciomanteActivoOfClientesListNewClientes = em.merge(oldIdServiciomanteActivoOfClientesListNewClientes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = serviciosMante.getIdPeticion();
                if (findServiciosMante(id) == null) {
                    throw new NonexistentEntityException("The serviciosMante with id " + id + " no longer exists.");
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
            ServiciosMante serviciosMante;
            try {
                serviciosMante = em.getReference(ServiciosMante.class, id);
                serviciosMante.getIdPeticion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The serviciosMante with id " + id + " no longer exists.", enfe);
            }
            Peticiones peticiones = serviciosMante.getPeticiones();
            if (peticiones != null) {
                peticiones.setServiciosMante(null);
                peticiones = em.merge(peticiones);
            }
            ServiciosMante serviciomantePadre = serviciosMante.getServiciomantePadre();
            if (serviciomantePadre != null) {
                serviciomantePadre.getServiciosManteList().remove(serviciosMante);
                serviciomantePadre = em.merge(serviciomantePadre);
            }
            Tarifa tarifa = serviciosMante.getTarifa();
            if (tarifa != null) {
                tarifa.getServiciosManteList().remove(serviciosMante);
                tarifa = em.merge(tarifa);
            }
            List<ServiciosMante> serviciosManteList = serviciosMante.getServiciosManteList();
            for (ServiciosMante serviciosManteListServiciosMante : serviciosManteList) {
                serviciosManteListServiciosMante.setServiciomantePadre(null);
                serviciosManteListServiciosMante = em.merge(serviciosManteListServiciosMante);
            }
            List<Clientes> clientesList = serviciosMante.getClientesList();
            for (Clientes clientesListClientes : clientesList) {
                clientesListClientes.setIdServiciomanteActivo(null);
                clientesListClientes = em.merge(clientesListClientes);
            }
            em.remove(serviciosMante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ServiciosMante> findServiciosManteEntities() {
        return findServiciosManteEntities(true, -1, -1);
    }

    public List<ServiciosMante> findServiciosManteEntities(int maxResults, int firstResult) {
        return findServiciosManteEntities(false, maxResults, firstResult);
    }

    private List<ServiciosMante> findServiciosManteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ServiciosMante.class));
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

    public ServiciosMante findServiciosMante(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ServiciosMante.class, id);
        } finally {
            em.close();
        }
    }

    public int getServiciosManteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ServiciosMante> rt = cq.from(ServiciosMante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
