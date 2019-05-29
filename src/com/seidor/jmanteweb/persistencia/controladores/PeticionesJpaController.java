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
import com.seidor.jmanteweb.persistencia.entidades.Bonomodis;
import com.seidor.jmanteweb.persistencia.entidades.Clientes;
import com.seidor.jmanteweb.persistencia.entidades.ServiciosMante;
import com.seidor.jmanteweb.persistencia.entidades.IncidenciaParte;
import com.seidor.jmanteweb.persistencia.entidades.Peticiones;
import java.util.ArrayList;
import java.util.List;
import com.seidor.jmanteweb.persistencia.entidades.Presupuestos;
import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class PeticionesJpaController implements Serializable {

    public PeticionesJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Peticiones peticiones) throws PreexistingEntityException, Exception {
        if (peticiones.getIncidenciaParteList() == null) {
            peticiones.setIncidenciaParteList(new ArrayList<IncidenciaParte>());
        }
        if (peticiones.getPresupuestosList() == null) {
            peticiones.setPresupuestosList(new ArrayList<Presupuestos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bonomodis bonomodis = peticiones.getBonomodis();
            if (bonomodis != null) {
                bonomodis = em.getReference(bonomodis.getClass(), bonomodis.getIdPeticion());
                peticiones.setBonomodis(bonomodis);
            }
            Clientes cliente = peticiones.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getCliente());
                peticiones.setCliente(cliente);
            }
            ServiciosMante serviciosMante = peticiones.getServiciosMante();
            if (serviciosMante != null) {
                serviciosMante = em.getReference(serviciosMante.getClass(), serviciosMante.getIdPeticion());
                peticiones.setServiciosMante(serviciosMante);
            }
            List<IncidenciaParte> attachedIncidenciaParteList = new ArrayList<IncidenciaParte>();
            for (IncidenciaParte incidenciaParteListIncidenciaParteToAttach : peticiones.getIncidenciaParteList()) {
                incidenciaParteListIncidenciaParteToAttach = em.getReference(incidenciaParteListIncidenciaParteToAttach.getClass(), incidenciaParteListIncidenciaParteToAttach.getNumParte());
                attachedIncidenciaParteList.add(incidenciaParteListIncidenciaParteToAttach);
            }
            peticiones.setIncidenciaParteList(attachedIncidenciaParteList);
            List<Presupuestos> attachedPresupuestosList = new ArrayList<Presupuestos>();
            for (Presupuestos presupuestosListPresupuestosToAttach : peticiones.getPresupuestosList()) {
                presupuestosListPresupuestosToAttach = em.getReference(presupuestosListPresupuestosToAttach.getClass(), presupuestosListPresupuestosToAttach.getNum());
                attachedPresupuestosList.add(presupuestosListPresupuestosToAttach);
            }
            peticiones.setPresupuestosList(attachedPresupuestosList);
            em.persist(peticiones);
            if (bonomodis != null) {
                Peticiones oldPeticionesOfBonomodis = bonomodis.getPeticiones();
                if (oldPeticionesOfBonomodis != null) {
                    oldPeticionesOfBonomodis.setBonomodis(null);
                    oldPeticionesOfBonomodis = em.merge(oldPeticionesOfBonomodis);
                }
                bonomodis.setPeticiones(peticiones);
                bonomodis = em.merge(bonomodis);
            }
            if (cliente != null) {
                cliente.getPeticionesList().add(peticiones);
                cliente = em.merge(cliente);
            }
            if (serviciosMante != null) {
                Peticiones oldPeticionesOfServiciosMante = serviciosMante.getPeticiones();
                if (oldPeticionesOfServiciosMante != null) {
                    oldPeticionesOfServiciosMante.setServiciosMante(null);
                    oldPeticionesOfServiciosMante = em.merge(oldPeticionesOfServiciosMante);
                }
                serviciosMante.setPeticiones(peticiones);
                serviciosMante = em.merge(serviciosMante);
            }
            for (IncidenciaParte incidenciaParteListIncidenciaParte : peticiones.getIncidenciaParteList()) {
                Peticiones oldIdPeticionOfIncidenciaParteListIncidenciaParte = incidenciaParteListIncidenciaParte.getIdPeticion();
                incidenciaParteListIncidenciaParte.setIdPeticion(peticiones);
                incidenciaParteListIncidenciaParte = em.merge(incidenciaParteListIncidenciaParte);
                if (oldIdPeticionOfIncidenciaParteListIncidenciaParte != null) {
                    oldIdPeticionOfIncidenciaParteListIncidenciaParte.getIncidenciaParteList().remove(incidenciaParteListIncidenciaParte);
                    oldIdPeticionOfIncidenciaParteListIncidenciaParte = em.merge(oldIdPeticionOfIncidenciaParteListIncidenciaParte);
                }
            }
            for (Presupuestos presupuestosListPresupuestos : peticiones.getPresupuestosList()) {
                Peticiones oldIdPeticionOfPresupuestosListPresupuestos = presupuestosListPresupuestos.getIdPeticion();
                presupuestosListPresupuestos.setIdPeticion(peticiones);
                presupuestosListPresupuestos = em.merge(presupuestosListPresupuestos);
                if (oldIdPeticionOfPresupuestosListPresupuestos != null) {
                    oldIdPeticionOfPresupuestosListPresupuestos.getPresupuestosList().remove(presupuestosListPresupuestos);
                    oldIdPeticionOfPresupuestosListPresupuestos = em.merge(oldIdPeticionOfPresupuestosListPresupuestos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPeticiones(peticiones.getIdpeticion()) != null) {
                throw new PreexistingEntityException("Peticiones " + peticiones + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Peticiones peticiones) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Peticiones persistentPeticiones = em.find(Peticiones.class, peticiones.getIdpeticion());
            Bonomodis bonomodisOld = persistentPeticiones.getBonomodis();
            Bonomodis bonomodisNew = peticiones.getBonomodis();
            Clientes clienteOld = persistentPeticiones.getCliente();
            Clientes clienteNew = peticiones.getCliente();
            ServiciosMante serviciosManteOld = persistentPeticiones.getServiciosMante();
            ServiciosMante serviciosManteNew = peticiones.getServiciosMante();
            List<IncidenciaParte> incidenciaParteListOld = persistentPeticiones.getIncidenciaParteList();
            List<IncidenciaParte> incidenciaParteListNew = peticiones.getIncidenciaParteList();
            List<Presupuestos> presupuestosListOld = persistentPeticiones.getPresupuestosList();
            List<Presupuestos> presupuestosListNew = peticiones.getPresupuestosList();
            List<String> illegalOrphanMessages = null;
            if (bonomodisOld != null && !bonomodisOld.equals(bonomodisNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Bonomodis " + bonomodisOld + " since its peticiones field is not nullable.");
            }
            if (serviciosManteOld != null && !serviciosManteOld.equals(serviciosManteNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain ServiciosMante " + serviciosManteOld + " since its peticiones field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (bonomodisNew != null) {
                bonomodisNew = em.getReference(bonomodisNew.getClass(), bonomodisNew.getIdPeticion());
                peticiones.setBonomodis(bonomodisNew);
            }
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getCliente());
                peticiones.setCliente(clienteNew);
            }
            if (serviciosManteNew != null) {
                serviciosManteNew = em.getReference(serviciosManteNew.getClass(), serviciosManteNew.getIdPeticion());
                peticiones.setServiciosMante(serviciosManteNew);
            }
            List<IncidenciaParte> attachedIncidenciaParteListNew = new ArrayList<IncidenciaParte>();
            for (IncidenciaParte incidenciaParteListNewIncidenciaParteToAttach : incidenciaParteListNew) {
                incidenciaParteListNewIncidenciaParteToAttach = em.getReference(incidenciaParteListNewIncidenciaParteToAttach.getClass(), incidenciaParteListNewIncidenciaParteToAttach.getNumParte());
                attachedIncidenciaParteListNew.add(incidenciaParteListNewIncidenciaParteToAttach);
            }
            incidenciaParteListNew = attachedIncidenciaParteListNew;
            peticiones.setIncidenciaParteList(incidenciaParteListNew);
            List<Presupuestos> attachedPresupuestosListNew = new ArrayList<Presupuestos>();
            for (Presupuestos presupuestosListNewPresupuestosToAttach : presupuestosListNew) {
                presupuestosListNewPresupuestosToAttach = em.getReference(presupuestosListNewPresupuestosToAttach.getClass(), presupuestosListNewPresupuestosToAttach.getNum());
                attachedPresupuestosListNew.add(presupuestosListNewPresupuestosToAttach);
            }
            presupuestosListNew = attachedPresupuestosListNew;
            peticiones.setPresupuestosList(presupuestosListNew);
            peticiones = em.merge(peticiones);
            if (bonomodisNew != null && !bonomodisNew.equals(bonomodisOld)) {
                Peticiones oldPeticionesOfBonomodis = bonomodisNew.getPeticiones();
                if (oldPeticionesOfBonomodis != null) {
                    oldPeticionesOfBonomodis.setBonomodis(null);
                    oldPeticionesOfBonomodis = em.merge(oldPeticionesOfBonomodis);
                }
                bonomodisNew.setPeticiones(peticiones);
                bonomodisNew = em.merge(bonomodisNew);
            }
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                clienteOld.getPeticionesList().remove(peticiones);
                clienteOld = em.merge(clienteOld);
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                clienteNew.getPeticionesList().add(peticiones);
                clienteNew = em.merge(clienteNew);
            }
            if (serviciosManteNew != null && !serviciosManteNew.equals(serviciosManteOld)) {
                Peticiones oldPeticionesOfServiciosMante = serviciosManteNew.getPeticiones();
                if (oldPeticionesOfServiciosMante != null) {
                    oldPeticionesOfServiciosMante.setServiciosMante(null);
                    oldPeticionesOfServiciosMante = em.merge(oldPeticionesOfServiciosMante);
                }
                serviciosManteNew.setPeticiones(peticiones);
                serviciosManteNew = em.merge(serviciosManteNew);
            }
            for (IncidenciaParte incidenciaParteListOldIncidenciaParte : incidenciaParteListOld) {
                if (!incidenciaParteListNew.contains(incidenciaParteListOldIncidenciaParte)) {
                    incidenciaParteListOldIncidenciaParte.setIdPeticion(null);
                    incidenciaParteListOldIncidenciaParte = em.merge(incidenciaParteListOldIncidenciaParte);
                }
            }
            for (IncidenciaParte incidenciaParteListNewIncidenciaParte : incidenciaParteListNew) {
                if (!incidenciaParteListOld.contains(incidenciaParteListNewIncidenciaParte)) {
                    Peticiones oldIdPeticionOfIncidenciaParteListNewIncidenciaParte = incidenciaParteListNewIncidenciaParte.getIdPeticion();
                    incidenciaParteListNewIncidenciaParte.setIdPeticion(peticiones);
                    incidenciaParteListNewIncidenciaParte = em.merge(incidenciaParteListNewIncidenciaParte);
                    if (oldIdPeticionOfIncidenciaParteListNewIncidenciaParte != null && !oldIdPeticionOfIncidenciaParteListNewIncidenciaParte.equals(peticiones)) {
                        oldIdPeticionOfIncidenciaParteListNewIncidenciaParte.getIncidenciaParteList().remove(incidenciaParteListNewIncidenciaParte);
                        oldIdPeticionOfIncidenciaParteListNewIncidenciaParte = em.merge(oldIdPeticionOfIncidenciaParteListNewIncidenciaParte);
                    }
                }
            }
            for (Presupuestos presupuestosListOldPresupuestos : presupuestosListOld) {
                if (!presupuestosListNew.contains(presupuestosListOldPresupuestos)) {
                    presupuestosListOldPresupuestos.setIdPeticion(null);
                    presupuestosListOldPresupuestos = em.merge(presupuestosListOldPresupuestos);
                }
            }
            for (Presupuestos presupuestosListNewPresupuestos : presupuestosListNew) {
                if (!presupuestosListOld.contains(presupuestosListNewPresupuestos)) {
                    Peticiones oldIdPeticionOfPresupuestosListNewPresupuestos = presupuestosListNewPresupuestos.getIdPeticion();
                    presupuestosListNewPresupuestos.setIdPeticion(peticiones);
                    presupuestosListNewPresupuestos = em.merge(presupuestosListNewPresupuestos);
                    if (oldIdPeticionOfPresupuestosListNewPresupuestos != null && !oldIdPeticionOfPresupuestosListNewPresupuestos.equals(peticiones)) {
                        oldIdPeticionOfPresupuestosListNewPresupuestos.getPresupuestosList().remove(presupuestosListNewPresupuestos);
                        oldIdPeticionOfPresupuestosListNewPresupuestos = em.merge(oldIdPeticionOfPresupuestosListNewPresupuestos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = peticiones.getIdpeticion();
                if (findPeticiones(id) == null) {
                    throw new NonexistentEntityException("The peticiones with id " + id + " no longer exists.");
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
            Peticiones peticiones;
            try {
                peticiones = em.getReference(Peticiones.class, id);
                peticiones.getIdpeticion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The peticiones with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Bonomodis bonomodisOrphanCheck = peticiones.getBonomodis();
            if (bonomodisOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Peticiones (" + peticiones + ") cannot be destroyed since the Bonomodis " + bonomodisOrphanCheck + " in its bonomodis field has a non-nullable peticiones field.");
            }
            ServiciosMante serviciosManteOrphanCheck = peticiones.getServiciosMante();
            if (serviciosManteOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Peticiones (" + peticiones + ") cannot be destroyed since the ServiciosMante " + serviciosManteOrphanCheck + " in its serviciosMante field has a non-nullable peticiones field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Clientes cliente = peticiones.getCliente();
            if (cliente != null) {
                cliente.getPeticionesList().remove(peticiones);
                cliente = em.merge(cliente);
            }
            List<IncidenciaParte> incidenciaParteList = peticiones.getIncidenciaParteList();
            for (IncidenciaParte incidenciaParteListIncidenciaParte : incidenciaParteList) {
                incidenciaParteListIncidenciaParte.setIdPeticion(null);
                incidenciaParteListIncidenciaParte = em.merge(incidenciaParteListIncidenciaParte);
            }
            List<Presupuestos> presupuestosList = peticiones.getPresupuestosList();
            for (Presupuestos presupuestosListPresupuestos : presupuestosList) {
                presupuestosListPresupuestos.setIdPeticion(null);
                presupuestosListPresupuestos = em.merge(presupuestosListPresupuestos);
            }
            em.remove(peticiones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Peticiones> findPeticionesEntities() {
        return findPeticionesEntities(true, -1, -1);
    }

    public List<Peticiones> findPeticionesEntities(int maxResults, int firstResult) {
        return findPeticionesEntities(false, maxResults, firstResult);
    }

    private List<Peticiones> findPeticionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Peticiones.class));
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

    public Peticiones findPeticiones(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Peticiones.class, id);
        } finally {
            em.close();
        }
    }

    public int getPeticionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Peticiones> rt = cq.from(Peticiones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
