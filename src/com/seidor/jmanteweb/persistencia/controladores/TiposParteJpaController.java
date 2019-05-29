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
import com.seidor.jmanteweb.persistencia.entidades.TiposParte;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class TiposParteJpaController implements Serializable {

    public TiposParteJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TiposParte tiposParte) throws PreexistingEntityException, Exception {
        if (tiposParte.getPartesList() == null) {
            tiposParte.setPartesList(new ArrayList<Partes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Partes> attachedPartesList = new ArrayList<Partes>();
            for (Partes partesListPartesToAttach : tiposParte.getPartesList()) {
                partesListPartesToAttach = em.getReference(partesListPartesToAttach.getClass(), partesListPartesToAttach.getNumParte());
                attachedPartesList.add(partesListPartesToAttach);
            }
            tiposParte.setPartesList(attachedPartesList);
            em.persist(tiposParte);
            for (Partes partesListPartes : tiposParte.getPartesList()) {
                TiposParte oldTipoOfPartesListPartes = partesListPartes.getTipo();
                partesListPartes.setTipo(tiposParte);
                partesListPartes = em.merge(partesListPartes);
                if (oldTipoOfPartesListPartes != null) {
                    oldTipoOfPartesListPartes.getPartesList().remove(partesListPartes);
                    oldTipoOfPartesListPartes = em.merge(oldTipoOfPartesListPartes);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTiposParte(tiposParte.getCodigo()) != null) {
                throw new PreexistingEntityException("TiposParte " + tiposParte + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TiposParte tiposParte) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TiposParte persistentTiposParte = em.find(TiposParte.class, tiposParte.getCodigo());
            List<Partes> partesListOld = persistentTiposParte.getPartesList();
            List<Partes> partesListNew = tiposParte.getPartesList();
            List<Partes> attachedPartesListNew = new ArrayList<Partes>();
            for (Partes partesListNewPartesToAttach : partesListNew) {
                partesListNewPartesToAttach = em.getReference(partesListNewPartesToAttach.getClass(), partesListNewPartesToAttach.getNumParte());
                attachedPartesListNew.add(partesListNewPartesToAttach);
            }
            partesListNew = attachedPartesListNew;
            tiposParte.setPartesList(partesListNew);
            tiposParte = em.merge(tiposParte);
            for (Partes partesListOldPartes : partesListOld) {
                if (!partesListNew.contains(partesListOldPartes)) {
                    partesListOldPartes.setTipo(null);
                    partesListOldPartes = em.merge(partesListOldPartes);
                }
            }
            for (Partes partesListNewPartes : partesListNew) {
                if (!partesListOld.contains(partesListNewPartes)) {
                    TiposParte oldTipoOfPartesListNewPartes = partesListNewPartes.getTipo();
                    partesListNewPartes.setTipo(tiposParte);
                    partesListNewPartes = em.merge(partesListNewPartes);
                    if (oldTipoOfPartesListNewPartes != null && !oldTipoOfPartesListNewPartes.equals(tiposParte)) {
                        oldTipoOfPartesListNewPartes.getPartesList().remove(partesListNewPartes);
                        oldTipoOfPartesListNewPartes = em.merge(oldTipoOfPartesListNewPartes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = tiposParte.getCodigo();
                if (findTiposParte(id) == null) {
                    throw new NonexistentEntityException("The tiposParte with id " + id + " no longer exists.");
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
            TiposParte tiposParte;
            try {
                tiposParte = em.getReference(TiposParte.class, id);
                tiposParte.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tiposParte with id " + id + " no longer exists.", enfe);
            }
            List<Partes> partesList = tiposParte.getPartesList();
            for (Partes partesListPartes : partesList) {
                partesListPartes.setTipo(null);
                partesListPartes = em.merge(partesListPartes);
            }
            em.remove(tiposParte);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TiposParte> findTiposParteEntities() {
        return findTiposParteEntities(true, -1, -1);
    }

    public List<TiposParte> findTiposParteEntities(int maxResults, int firstResult) {
        return findTiposParteEntities(false, maxResults, firstResult);
    }

    private List<TiposParte> findTiposParteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TiposParte.class));
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

    public TiposParte findTiposParte(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TiposParte.class, id);
        } finally {
            em.close();
        }
    }

    public int getTiposParteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TiposParte> rt = cq.from(TiposParte.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
