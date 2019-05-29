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
import com.seidor.jmanteweb.persistencia.entidades.Peticiones;
import java.util.ArrayList;
import java.util.List;
import com.seidor.jmanteweb.persistencia.entidades.InfoModi;
import com.seidor.jmanteweb.persistencia.entidades.Clientes;
import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class BonomodisJpaController implements Serializable {

    public BonomodisJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Bonomodis bonomodis) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (bonomodis.getBonomodisList() == null) {
            bonomodis.setBonomodisList(new ArrayList<Bonomodis>());
        }
        if (bonomodis.getInfoModiList() == null) {
            bonomodis.setInfoModiList(new ArrayList<InfoModi>());
        }
        if (bonomodis.getClientesList() == null) {
            bonomodis.setClientesList(new ArrayList<Clientes>());
        }
        List<String> illegalOrphanMessages = null;
        Peticiones peticionesOrphanCheck = bonomodis.getPeticiones();
        if (peticionesOrphanCheck != null) {
            Bonomodis oldBonomodisOfPeticiones = peticionesOrphanCheck.getBonomodis();
            if (oldBonomodisOfPeticiones != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Peticiones " + peticionesOrphanCheck + " already has an item of type Bonomodis whose peticiones column cannot be null. Please make another selection for the peticiones field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bonomodis bonomodisPadre = bonomodis.getBonomodisPadre();
            if (bonomodisPadre != null) {
                bonomodisPadre = em.getReference(bonomodisPadre.getClass(), bonomodisPadre.getIdPeticion());
                bonomodis.setBonomodisPadre(bonomodisPadre);
            }
            Peticiones peticiones = bonomodis.getPeticiones();
            if (peticiones != null) {
                peticiones = em.getReference(peticiones.getClass(), peticiones.getIdpeticion());
                bonomodis.setPeticiones(peticiones);
            }
            List<Bonomodis> attachedBonomodisList = new ArrayList<Bonomodis>();
            for (Bonomodis bonomodisListBonomodisToAttach : bonomodis.getBonomodisList()) {
                bonomodisListBonomodisToAttach = em.getReference(bonomodisListBonomodisToAttach.getClass(), bonomodisListBonomodisToAttach.getIdPeticion());
                attachedBonomodisList.add(bonomodisListBonomodisToAttach);
            }
            bonomodis.setBonomodisList(attachedBonomodisList);
            List<InfoModi> attachedInfoModiList = new ArrayList<InfoModi>();
            for (InfoModi infoModiListInfoModiToAttach : bonomodis.getInfoModiList()) {
                infoModiListInfoModiToAttach = em.getReference(infoModiListInfoModiToAttach.getClass(), infoModiListInfoModiToAttach.getInfoModiPK());
                attachedInfoModiList.add(infoModiListInfoModiToAttach);
            }
            bonomodis.setInfoModiList(attachedInfoModiList);
            List<Clientes> attachedClientesList = new ArrayList<Clientes>();
            for (Clientes clientesListClientesToAttach : bonomodis.getClientesList()) {
                clientesListClientesToAttach = em.getReference(clientesListClientesToAttach.getClass(), clientesListClientesToAttach.getCliente());
                attachedClientesList.add(clientesListClientesToAttach);
            }
            bonomodis.setClientesList(attachedClientesList);
            em.persist(bonomodis);
            if (bonomodisPadre != null) {
                bonomodisPadre.getBonomodisList().add(bonomodis);
                bonomodisPadre = em.merge(bonomodisPadre);
            }
            if (peticiones != null) {
                peticiones.setBonomodis(bonomodis);
                peticiones = em.merge(peticiones);
            }
            for (Bonomodis bonomodisListBonomodis : bonomodis.getBonomodisList()) {
                Bonomodis oldBonomodisPadreOfBonomodisListBonomodis = bonomodisListBonomodis.getBonomodisPadre();
                bonomodisListBonomodis.setBonomodisPadre(bonomodis);
                bonomodisListBonomodis = em.merge(bonomodisListBonomodis);
                if (oldBonomodisPadreOfBonomodisListBonomodis != null) {
                    oldBonomodisPadreOfBonomodisListBonomodis.getBonomodisList().remove(bonomodisListBonomodis);
                    oldBonomodisPadreOfBonomodisListBonomodis = em.merge(oldBonomodisPadreOfBonomodisListBonomodis);
                }
            }
            for (InfoModi infoModiListInfoModi : bonomodis.getInfoModiList()) {
                Bonomodis oldBonomodisOfInfoModiListInfoModi = infoModiListInfoModi.getBonomodis();
                infoModiListInfoModi.setBonomodis(bonomodis);
                infoModiListInfoModi = em.merge(infoModiListInfoModi);
                if (oldBonomodisOfInfoModiListInfoModi != null) {
                    oldBonomodisOfInfoModiListInfoModi.getInfoModiList().remove(infoModiListInfoModi);
                    oldBonomodisOfInfoModiListInfoModi = em.merge(oldBonomodisOfInfoModiListInfoModi);
                }
            }
            for (Clientes clientesListClientes : bonomodis.getClientesList()) {
                Bonomodis oldIdBonomodisActivoOfClientesListClientes = clientesListClientes.getIdBonomodisActivo();
                clientesListClientes.setIdBonomodisActivo(bonomodis);
                clientesListClientes = em.merge(clientesListClientes);
                if (oldIdBonomodisActivoOfClientesListClientes != null) {
                    oldIdBonomodisActivoOfClientesListClientes.getClientesList().remove(clientesListClientes);
                    oldIdBonomodisActivoOfClientesListClientes = em.merge(oldIdBonomodisActivoOfClientesListClientes);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBonomodis(bonomodis.getIdPeticion()) != null) {
                throw new PreexistingEntityException("Bonomodis " + bonomodis + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Bonomodis bonomodis) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bonomodis persistentBonomodis = em.find(Bonomodis.class, bonomodis.getIdPeticion());
            Bonomodis bonomodisPadreOld = persistentBonomodis.getBonomodisPadre();
            Bonomodis bonomodisPadreNew = bonomodis.getBonomodisPadre();
            Peticiones peticionesOld = persistentBonomodis.getPeticiones();
            Peticiones peticionesNew = bonomodis.getPeticiones();
            List<Bonomodis> bonomodisListOld = persistentBonomodis.getBonomodisList();
            List<Bonomodis> bonomodisListNew = bonomodis.getBonomodisList();
            List<InfoModi> infoModiListOld = persistentBonomodis.getInfoModiList();
            List<InfoModi> infoModiListNew = bonomodis.getInfoModiList();
            List<Clientes> clientesListOld = persistentBonomodis.getClientesList();
            List<Clientes> clientesListNew = bonomodis.getClientesList();
            List<String> illegalOrphanMessages = null;
            if (peticionesNew != null && !peticionesNew.equals(peticionesOld)) {
                Bonomodis oldBonomodisOfPeticiones = peticionesNew.getBonomodis();
                if (oldBonomodisOfPeticiones != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Peticiones " + peticionesNew + " already has an item of type Bonomodis whose peticiones column cannot be null. Please make another selection for the peticiones field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (bonomodisPadreNew != null) {
                bonomodisPadreNew = em.getReference(bonomodisPadreNew.getClass(), bonomodisPadreNew.getIdPeticion());
                bonomodis.setBonomodisPadre(bonomodisPadreNew);
            }
            if (peticionesNew != null) {
                peticionesNew = em.getReference(peticionesNew.getClass(), peticionesNew.getIdpeticion());
                bonomodis.setPeticiones(peticionesNew);
            }
            List<Bonomodis> attachedBonomodisListNew = new ArrayList<Bonomodis>();
            for (Bonomodis bonomodisListNewBonomodisToAttach : bonomodisListNew) {
                bonomodisListNewBonomodisToAttach = em.getReference(bonomodisListNewBonomodisToAttach.getClass(), bonomodisListNewBonomodisToAttach.getIdPeticion());
                attachedBonomodisListNew.add(bonomodisListNewBonomodisToAttach);
            }
            bonomodisListNew = attachedBonomodisListNew;
            bonomodis.setBonomodisList(bonomodisListNew);
            List<InfoModi> attachedInfoModiListNew = new ArrayList<InfoModi>();
            for (InfoModi infoModiListNewInfoModiToAttach : infoModiListNew) {
                infoModiListNewInfoModiToAttach = em.getReference(infoModiListNewInfoModiToAttach.getClass(), infoModiListNewInfoModiToAttach.getInfoModiPK());
                attachedInfoModiListNew.add(infoModiListNewInfoModiToAttach);
            }
            infoModiListNew = attachedInfoModiListNew;
            bonomodis.setInfoModiList(infoModiListNew);
            List<Clientes> attachedClientesListNew = new ArrayList<Clientes>();
            for (Clientes clientesListNewClientesToAttach : clientesListNew) {
                clientesListNewClientesToAttach = em.getReference(clientesListNewClientesToAttach.getClass(), clientesListNewClientesToAttach.getCliente());
                attachedClientesListNew.add(clientesListNewClientesToAttach);
            }
            clientesListNew = attachedClientesListNew;
            bonomodis.setClientesList(clientesListNew);
            bonomodis = em.merge(bonomodis);
            if (bonomodisPadreOld != null && !bonomodisPadreOld.equals(bonomodisPadreNew)) {
                bonomodisPadreOld.getBonomodisList().remove(bonomodis);
                bonomodisPadreOld = em.merge(bonomodisPadreOld);
            }
            if (bonomodisPadreNew != null && !bonomodisPadreNew.equals(bonomodisPadreOld)) {
                bonomodisPadreNew.getBonomodisList().add(bonomodis);
                bonomodisPadreNew = em.merge(bonomodisPadreNew);
            }
            if (peticionesOld != null && !peticionesOld.equals(peticionesNew)) {
                peticionesOld.setBonomodis(null);
                peticionesOld = em.merge(peticionesOld);
            }
            if (peticionesNew != null && !peticionesNew.equals(peticionesOld)) {
                peticionesNew.setBonomodis(bonomodis);
                peticionesNew = em.merge(peticionesNew);
            }
            for (Bonomodis bonomodisListOldBonomodis : bonomodisListOld) {
                if (!bonomodisListNew.contains(bonomodisListOldBonomodis)) {
                    bonomodisListOldBonomodis.setBonomodisPadre(null);
                    bonomodisListOldBonomodis = em.merge(bonomodisListOldBonomodis);
                }
            }
            for (Bonomodis bonomodisListNewBonomodis : bonomodisListNew) {
                if (!bonomodisListOld.contains(bonomodisListNewBonomodis)) {
                    Bonomodis oldBonomodisPadreOfBonomodisListNewBonomodis = bonomodisListNewBonomodis.getBonomodisPadre();
                    bonomodisListNewBonomodis.setBonomodisPadre(bonomodis);
                    bonomodisListNewBonomodis = em.merge(bonomodisListNewBonomodis);
                    if (oldBonomodisPadreOfBonomodisListNewBonomodis != null && !oldBonomodisPadreOfBonomodisListNewBonomodis.equals(bonomodis)) {
                        oldBonomodisPadreOfBonomodisListNewBonomodis.getBonomodisList().remove(bonomodisListNewBonomodis);
                        oldBonomodisPadreOfBonomodisListNewBonomodis = em.merge(oldBonomodisPadreOfBonomodisListNewBonomodis);
                    }
                }
            }
            for (InfoModi infoModiListOldInfoModi : infoModiListOld) {
                if (!infoModiListNew.contains(infoModiListOldInfoModi)) {
                    infoModiListOldInfoModi.setBonomodis(null);
                    infoModiListOldInfoModi = em.merge(infoModiListOldInfoModi);
                }
            }
            for (InfoModi infoModiListNewInfoModi : infoModiListNew) {
                if (!infoModiListOld.contains(infoModiListNewInfoModi)) {
                    Bonomodis oldBonomodisOfInfoModiListNewInfoModi = infoModiListNewInfoModi.getBonomodis();
                    infoModiListNewInfoModi.setBonomodis(bonomodis);
                    infoModiListNewInfoModi = em.merge(infoModiListNewInfoModi);
                    if (oldBonomodisOfInfoModiListNewInfoModi != null && !oldBonomodisOfInfoModiListNewInfoModi.equals(bonomodis)) {
                        oldBonomodisOfInfoModiListNewInfoModi.getInfoModiList().remove(infoModiListNewInfoModi);
                        oldBonomodisOfInfoModiListNewInfoModi = em.merge(oldBonomodisOfInfoModiListNewInfoModi);
                    }
                }
            }
            for (Clientes clientesListOldClientes : clientesListOld) {
                if (!clientesListNew.contains(clientesListOldClientes)) {
                    clientesListOldClientes.setIdBonomodisActivo(null);
                    clientesListOldClientes = em.merge(clientesListOldClientes);
                }
            }
            for (Clientes clientesListNewClientes : clientesListNew) {
                if (!clientesListOld.contains(clientesListNewClientes)) {
                    Bonomodis oldIdBonomodisActivoOfClientesListNewClientes = clientesListNewClientes.getIdBonomodisActivo();
                    clientesListNewClientes.setIdBonomodisActivo(bonomodis);
                    clientesListNewClientes = em.merge(clientesListNewClientes);
                    if (oldIdBonomodisActivoOfClientesListNewClientes != null && !oldIdBonomodisActivoOfClientesListNewClientes.equals(bonomodis)) {
                        oldIdBonomodisActivoOfClientesListNewClientes.getClientesList().remove(clientesListNewClientes);
                        oldIdBonomodisActivoOfClientesListNewClientes = em.merge(oldIdBonomodisActivoOfClientesListNewClientes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = bonomodis.getIdPeticion();
                if (findBonomodis(id) == null) {
                    throw new NonexistentEntityException("The bonomodis with id " + id + " no longer exists.");
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
            Bonomodis bonomodis;
            try {
                bonomodis = em.getReference(Bonomodis.class, id);
                bonomodis.getIdPeticion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bonomodis with id " + id + " no longer exists.", enfe);
            }
            Bonomodis bonomodisPadre = bonomodis.getBonomodisPadre();
            if (bonomodisPadre != null) {
                bonomodisPadre.getBonomodisList().remove(bonomodis);
                bonomodisPadre = em.merge(bonomodisPadre);
            }
            Peticiones peticiones = bonomodis.getPeticiones();
            if (peticiones != null) {
                peticiones.setBonomodis(null);
                peticiones = em.merge(peticiones);
            }
            List<Bonomodis> bonomodisList = bonomodis.getBonomodisList();
            for (Bonomodis bonomodisListBonomodis : bonomodisList) {
                bonomodisListBonomodis.setBonomodisPadre(null);
                bonomodisListBonomodis = em.merge(bonomodisListBonomodis);
            }
            List<InfoModi> infoModiList = bonomodis.getInfoModiList();
            for (InfoModi infoModiListInfoModi : infoModiList) {
                infoModiListInfoModi.setBonomodis(null);
                infoModiListInfoModi = em.merge(infoModiListInfoModi);
            }
            List<Clientes> clientesList = bonomodis.getClientesList();
            for (Clientes clientesListClientes : clientesList) {
                clientesListClientes.setIdBonomodisActivo(null);
                clientesListClientes = em.merge(clientesListClientes);
            }
            em.remove(bonomodis);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Bonomodis> findBonomodisEntities() {
        return findBonomodisEntities(true, -1, -1);
    }

    public List<Bonomodis> findBonomodisEntities(int maxResults, int firstResult) {
        return findBonomodisEntities(false, maxResults, firstResult);
    }

    private List<Bonomodis> findBonomodisEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bonomodis.class));
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

    public Bonomodis findBonomodis(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bonomodis.class, id);
        } finally {
            em.close();
        }
    }

    public int getBonomodisCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bonomodis> rt = cq.from(Bonomodis.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
