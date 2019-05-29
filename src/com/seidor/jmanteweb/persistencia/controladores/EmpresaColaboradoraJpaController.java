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
import com.seidor.jmanteweb.persistencia.entidades.EmpresaProveedor;
import java.util.ArrayList;
import java.util.List;
import com.seidor.jmanteweb.persistencia.entidades.Clientes;
import com.seidor.jmanteweb.persistencia.entidades.EmpresaColaboradora;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class EmpresaColaboradoraJpaController implements Serializable {

    public EmpresaColaboradoraJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EmpresaColaboradora empresaColaboradora) throws PreexistingEntityException, Exception {
        if (empresaColaboradora.getEmpresaProveedorList() == null) {
            empresaColaboradora.setEmpresaProveedorList(new ArrayList<EmpresaProveedor>());
        }
        if (empresaColaboradora.getClientesList() == null) {
            empresaColaboradora.setClientesList(new ArrayList<Clientes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<EmpresaProveedor> attachedEmpresaProveedorList = new ArrayList<EmpresaProveedor>();
            for (EmpresaProveedor empresaProveedorListEmpresaProveedorToAttach : empresaColaboradora.getEmpresaProveedorList()) {
                empresaProveedorListEmpresaProveedorToAttach = em.getReference(empresaProveedorListEmpresaProveedorToAttach.getClass(), empresaProveedorListEmpresaProveedorToAttach.getEmpresaProveedorPK());
                attachedEmpresaProveedorList.add(empresaProveedorListEmpresaProveedorToAttach);
            }
            empresaColaboradora.setEmpresaProveedorList(attachedEmpresaProveedorList);
            List<Clientes> attachedClientesList = new ArrayList<Clientes>();
            for (Clientes clientesListClientesToAttach : empresaColaboradora.getClientesList()) {
                clientesListClientesToAttach = em.getReference(clientesListClientesToAttach.getClass(), clientesListClientesToAttach.getCliente());
                attachedClientesList.add(clientesListClientesToAttach);
            }
            empresaColaboradora.setClientesList(attachedClientesList);
            em.persist(empresaColaboradora);
            for (EmpresaProveedor empresaProveedorListEmpresaProveedor : empresaColaboradora.getEmpresaProveedorList()) {
                EmpresaColaboradora oldEmpresaColaboradoraOfEmpresaProveedorListEmpresaProveedor = empresaProveedorListEmpresaProveedor.getEmpresaColaboradora();
                empresaProveedorListEmpresaProveedor.setEmpresaColaboradora(empresaColaboradora);
                empresaProveedorListEmpresaProveedor = em.merge(empresaProveedorListEmpresaProveedor);
                if (oldEmpresaColaboradoraOfEmpresaProveedorListEmpresaProveedor != null) {
                    oldEmpresaColaboradoraOfEmpresaProveedorListEmpresaProveedor.getEmpresaProveedorList().remove(empresaProveedorListEmpresaProveedor);
                    oldEmpresaColaboradoraOfEmpresaProveedorListEmpresaProveedor = em.merge(oldEmpresaColaboradoraOfEmpresaProveedorListEmpresaProveedor);
                }
            }
            for (Clientes clientesListClientes : empresaColaboradora.getClientesList()) {
                EmpresaColaboradora oldTipoClienteOfClientesListClientes = clientesListClientes.getTipoCliente();
                clientesListClientes.setTipoCliente(empresaColaboradora);
                clientesListClientes = em.merge(clientesListClientes);
                if (oldTipoClienteOfClientesListClientes != null) {
                    oldTipoClienteOfClientesListClientes.getClientesList().remove(clientesListClientes);
                    oldTipoClienteOfClientesListClientes = em.merge(oldTipoClienteOfClientesListClientes);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmpresaColaboradora(empresaColaboradora.getNombre()) != null) {
                throw new PreexistingEntityException("EmpresaColaboradora " + empresaColaboradora + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EmpresaColaboradora empresaColaboradora) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EmpresaColaboradora persistentEmpresaColaboradora = em.find(EmpresaColaboradora.class, empresaColaboradora.getNombre());
            List<EmpresaProveedor> empresaProveedorListOld = persistentEmpresaColaboradora.getEmpresaProveedorList();
            List<EmpresaProveedor> empresaProveedorListNew = empresaColaboradora.getEmpresaProveedorList();
            List<Clientes> clientesListOld = persistentEmpresaColaboradora.getClientesList();
            List<Clientes> clientesListNew = empresaColaboradora.getClientesList();
            List<String> illegalOrphanMessages = null;
            for (EmpresaProveedor empresaProveedorListOldEmpresaProveedor : empresaProveedorListOld) {
                if (!empresaProveedorListNew.contains(empresaProveedorListOldEmpresaProveedor)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EmpresaProveedor " + empresaProveedorListOldEmpresaProveedor + " since its empresaColaboradora field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<EmpresaProveedor> attachedEmpresaProveedorListNew = new ArrayList<EmpresaProveedor>();
            for (EmpresaProveedor empresaProveedorListNewEmpresaProveedorToAttach : empresaProveedorListNew) {
                empresaProveedorListNewEmpresaProveedorToAttach = em.getReference(empresaProveedorListNewEmpresaProveedorToAttach.getClass(), empresaProveedorListNewEmpresaProveedorToAttach.getEmpresaProveedorPK());
                attachedEmpresaProveedorListNew.add(empresaProveedorListNewEmpresaProveedorToAttach);
            }
            empresaProveedorListNew = attachedEmpresaProveedorListNew;
            empresaColaboradora.setEmpresaProveedorList(empresaProveedorListNew);
            List<Clientes> attachedClientesListNew = new ArrayList<Clientes>();
            for (Clientes clientesListNewClientesToAttach : clientesListNew) {
                clientesListNewClientesToAttach = em.getReference(clientesListNewClientesToAttach.getClass(), clientesListNewClientesToAttach.getCliente());
                attachedClientesListNew.add(clientesListNewClientesToAttach);
            }
            clientesListNew = attachedClientesListNew;
            empresaColaboradora.setClientesList(clientesListNew);
            empresaColaboradora = em.merge(empresaColaboradora);
            for (EmpresaProveedor empresaProveedorListNewEmpresaProveedor : empresaProveedorListNew) {
                if (!empresaProveedorListOld.contains(empresaProveedorListNewEmpresaProveedor)) {
                    EmpresaColaboradora oldEmpresaColaboradoraOfEmpresaProveedorListNewEmpresaProveedor = empresaProveedorListNewEmpresaProveedor.getEmpresaColaboradora();
                    empresaProveedorListNewEmpresaProveedor.setEmpresaColaboradora(empresaColaboradora);
                    empresaProveedorListNewEmpresaProveedor = em.merge(empresaProveedorListNewEmpresaProveedor);
                    if (oldEmpresaColaboradoraOfEmpresaProveedorListNewEmpresaProveedor != null && !oldEmpresaColaboradoraOfEmpresaProveedorListNewEmpresaProveedor.equals(empresaColaboradora)) {
                        oldEmpresaColaboradoraOfEmpresaProveedorListNewEmpresaProveedor.getEmpresaProveedorList().remove(empresaProveedorListNewEmpresaProveedor);
                        oldEmpresaColaboradoraOfEmpresaProveedorListNewEmpresaProveedor = em.merge(oldEmpresaColaboradoraOfEmpresaProveedorListNewEmpresaProveedor);
                    }
                }
            }
            for (Clientes clientesListOldClientes : clientesListOld) {
                if (!clientesListNew.contains(clientesListOldClientes)) {
                    clientesListOldClientes.setTipoCliente(null);
                    clientesListOldClientes = em.merge(clientesListOldClientes);
                }
            }
            for (Clientes clientesListNewClientes : clientesListNew) {
                if (!clientesListOld.contains(clientesListNewClientes)) {
                    EmpresaColaboradora oldTipoClienteOfClientesListNewClientes = clientesListNewClientes.getTipoCliente();
                    clientesListNewClientes.setTipoCliente(empresaColaboradora);
                    clientesListNewClientes = em.merge(clientesListNewClientes);
                    if (oldTipoClienteOfClientesListNewClientes != null && !oldTipoClienteOfClientesListNewClientes.equals(empresaColaboradora)) {
                        oldTipoClienteOfClientesListNewClientes.getClientesList().remove(clientesListNewClientes);
                        oldTipoClienteOfClientesListNewClientes = em.merge(oldTipoClienteOfClientesListNewClientes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = empresaColaboradora.getNombre();
                if (findEmpresaColaboradora(id) == null) {
                    throw new NonexistentEntityException("The empresaColaboradora with id " + id + " no longer exists.");
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
            EmpresaColaboradora empresaColaboradora;
            try {
                empresaColaboradora = em.getReference(EmpresaColaboradora.class, id);
                empresaColaboradora.getNombre();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empresaColaboradora with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<EmpresaProveedor> empresaProveedorListOrphanCheck = empresaColaboradora.getEmpresaProveedorList();
            for (EmpresaProveedor empresaProveedorListOrphanCheckEmpresaProveedor : empresaProveedorListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This EmpresaColaboradora (" + empresaColaboradora + ") cannot be destroyed since the EmpresaProveedor " + empresaProveedorListOrphanCheckEmpresaProveedor + " in its empresaProveedorList field has a non-nullable empresaColaboradora field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Clientes> clientesList = empresaColaboradora.getClientesList();
            for (Clientes clientesListClientes : clientesList) {
                clientesListClientes.setTipoCliente(null);
                clientesListClientes = em.merge(clientesListClientes);
            }
            em.remove(empresaColaboradora);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EmpresaColaboradora> findEmpresaColaboradoraEntities() {
        return findEmpresaColaboradoraEntities(true, -1, -1);
    }

    public List<EmpresaColaboradora> findEmpresaColaboradoraEntities(int maxResults, int firstResult) {
        return findEmpresaColaboradoraEntities(false, maxResults, firstResult);
    }

    private List<EmpresaColaboradora> findEmpresaColaboradoraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EmpresaColaboradora.class));
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

    public EmpresaColaboradora findEmpresaColaboradora(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EmpresaColaboradora.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpresaColaboradoraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EmpresaColaboradora> rt = cq.from(EmpresaColaboradora.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
