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
import com.seidor.jmanteweb.persistencia.entidades.Partes;
import com.seidor.jmanteweb.persistencia.entidades.PrioridadParte;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class PrioridadParteJpaController implements Serializable {

    public PrioridadParteJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PrioridadParte prioridadParte) throws PreexistingEntityException, Exception {
        if (prioridadParte.getPartesList() == null) {
            prioridadParte.setPartesList(new ArrayList<Partes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Partes> attachedPartesList = new ArrayList<Partes>();
            for (Partes partesListPartesToAttach : prioridadParte.getPartesList()) {
                partesListPartesToAttach = em.getReference(partesListPartesToAttach.getClass(), partesListPartesToAttach.getNumParte());
                attachedPartesList.add(partesListPartesToAttach);
            }
            prioridadParte.setPartesList(attachedPartesList);
            em.persist(prioridadParte);
            for (Partes partesListPartes : prioridadParte.getPartesList()) {
                PrioridadParte oldImportanciaOfPartesListPartes = partesListPartes.getImportancia();
                partesListPartes.setImportancia(prioridadParte);
                partesListPartes = em.merge(partesListPartes);
                if (oldImportanciaOfPartesListPartes != null) {
                    oldImportanciaOfPartesListPartes.getPartesList().remove(partesListPartes);
                    oldImportanciaOfPartesListPartes = em.merge(oldImportanciaOfPartesListPartes);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPrioridadParte(prioridadParte.getPrioridad()) != null) {
                throw new PreexistingEntityException("PrioridadParte " + prioridadParte + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PrioridadParte prioridadParte) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PrioridadParte persistentPrioridadParte = em.find(PrioridadParte.class, prioridadParte.getPrioridad());
            List<Partes> partesListOld = persistentPrioridadParte.getPartesList();
            List<Partes> partesListNew = prioridadParte.getPartesList();
            List<Partes> attachedPartesListNew = new ArrayList<Partes>();
            for (Partes partesListNewPartesToAttach : partesListNew) {
                partesListNewPartesToAttach = em.getReference(partesListNewPartesToAttach.getClass(), partesListNewPartesToAttach.getNumParte());
                attachedPartesListNew.add(partesListNewPartesToAttach);
            }
            partesListNew = attachedPartesListNew;
            prioridadParte.setPartesList(partesListNew);
            prioridadParte = em.merge(prioridadParte);
            for (Partes partesListOldPartes : partesListOld) {
                if (!partesListNew.contains(partesListOldPartes)) {
                    partesListOldPartes.setImportancia(null);
                    partesListOldPartes = em.merge(partesListOldPartes);
                }
            }
            for (Partes partesListNewPartes : partesListNew) {
                if (!partesListOld.contains(partesListNewPartes)) {
                    PrioridadParte oldImportanciaOfPartesListNewPartes = partesListNewPartes.getImportancia();
                    partesListNewPartes.setImportancia(prioridadParte);
                    partesListNewPartes = em.merge(partesListNewPartes);
                    if (oldImportanciaOfPartesListNewPartes != null && !oldImportanciaOfPartesListNewPartes.equals(prioridadParte)) {
                        oldImportanciaOfPartesListNewPartes.getPartesList().remove(partesListNewPartes);
                        oldImportanciaOfPartesListNewPartes = em.merge(oldImportanciaOfPartesListNewPartes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = prioridadParte.getPrioridad();
                if (findPrioridadParte(id) == null) {
                    throw new NonexistentEntityException("The prioridadParte with id " + id + " no longer exists.");
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
            PrioridadParte prioridadParte;
            try {
                prioridadParte = em.getReference(PrioridadParte.class, id);
                prioridadParte.getPrioridad();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The prioridadParte with id " + id + " no longer exists.", enfe);
            }
            List<Partes> partesList = prioridadParte.getPartesList();
            for (Partes partesListPartes : partesList) {
                partesListPartes.setImportancia(null);
                partesListPartes = em.merge(partesListPartes);
            }
            em.remove(prioridadParte);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PrioridadParte> findPrioridadParteEntities() {
        return findPrioridadParteEntities(true, -1, -1);
    }

    public List<PrioridadParte> findPrioridadParteEntities(int maxResults, int firstResult) {
        return findPrioridadParteEntities(false, maxResults, firstResult);
    }

    private List<PrioridadParte> findPrioridadParteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PrioridadParte.class));
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

    public PrioridadParte findPrioridadParte(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PrioridadParte.class, id);
        } finally {
            em.close();
        }
    }

    public int getPrioridadParteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PrioridadParte> rt = cq.from(PrioridadParte.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
