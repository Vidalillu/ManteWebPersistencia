/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.controladores;

import com.seidor.jmanteweb.persistencia.controladores.exceptions.IllegalOrphanException;
import com.seidor.jmanteweb.persistencia.controladores.exceptions.NonexistentEntityException;
import com.seidor.jmanteweb.persistencia.controladores.exceptions.PreexistingEntityException;
import com.seidor.jmanteweb.persistencia.entidades.IncidenciaParte;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.seidor.jmanteweb.persistencia.entidades.Partes;
import com.seidor.jmanteweb.persistencia.entidades.Peticiones;
import com.seidor.jmanteweb.persistencia.entidades.Tarifa;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class IncidenciaParteJpaController implements Serializable {

    public IncidenciaParteJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(IncidenciaParte incidenciaParte) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Partes partesOrphanCheck = incidenciaParte.getPartes();
        if (partesOrphanCheck != null) {
            IncidenciaParte oldIncidenciaParteOfPartes = partesOrphanCheck.getIncidenciaParte();
            if (oldIncidenciaParteOfPartes != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Partes " + partesOrphanCheck + " already has an item of type IncidenciaParte whose partes column cannot be null. Please make another selection for the partes field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Partes partes = incidenciaParte.getPartes();
            if (partes != null) {
                partes = em.getReference(partes.getClass(), partes.getNumParte());
                incidenciaParte.setPartes(partes);
            }
            Peticiones idPeticion = incidenciaParte.getIdPeticion();
            if (idPeticion != null) {
                idPeticion = em.getReference(idPeticion.getClass(), idPeticion.getIdpeticion());
                incidenciaParte.setIdPeticion(idPeticion);
            }
            Tarifa tarifa = incidenciaParte.getTarifa();
            if (tarifa != null) {
                tarifa = em.getReference(tarifa.getClass(), tarifa.getTarifa());
                incidenciaParte.setTarifa(tarifa);
            }
            em.persist(incidenciaParte);
            if (partes != null) {
                partes.setIncidenciaParte(incidenciaParte);
                partes = em.merge(partes);
            }
            if (idPeticion != null) {
                idPeticion.getIncidenciaParteList().add(incidenciaParte);
                idPeticion = em.merge(idPeticion);
            }
            if (tarifa != null) {
                tarifa.getIncidenciaParteList().add(incidenciaParte);
                tarifa = em.merge(tarifa);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findIncidenciaParte(incidenciaParte.getNumParte()) != null) {
                throw new PreexistingEntityException("IncidenciaParte " + incidenciaParte + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(IncidenciaParte incidenciaParte) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            IncidenciaParte persistentIncidenciaParte = em.find(IncidenciaParte.class, incidenciaParte.getNumParte());
            Partes partesOld = persistentIncidenciaParte.getPartes();
            Partes partesNew = incidenciaParte.getPartes();
            Peticiones idPeticionOld = persistentIncidenciaParte.getIdPeticion();
            Peticiones idPeticionNew = incidenciaParte.getIdPeticion();
            Tarifa tarifaOld = persistentIncidenciaParte.getTarifa();
            Tarifa tarifaNew = incidenciaParte.getTarifa();
            List<String> illegalOrphanMessages = null;
            if (partesNew != null && !partesNew.equals(partesOld)) {
                IncidenciaParte oldIncidenciaParteOfPartes = partesNew.getIncidenciaParte();
                if (oldIncidenciaParteOfPartes != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Partes " + partesNew + " already has an item of type IncidenciaParte whose partes column cannot be null. Please make another selection for the partes field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (partesNew != null) {
                partesNew = em.getReference(partesNew.getClass(), partesNew.getNumParte());
                incidenciaParte.setPartes(partesNew);
            }
            if (idPeticionNew != null) {
                idPeticionNew = em.getReference(idPeticionNew.getClass(), idPeticionNew.getIdpeticion());
                incidenciaParte.setIdPeticion(idPeticionNew);
            }
            if (tarifaNew != null) {
                tarifaNew = em.getReference(tarifaNew.getClass(), tarifaNew.getTarifa());
                incidenciaParte.setTarifa(tarifaNew);
            }
            incidenciaParte = em.merge(incidenciaParte);
            if (partesOld != null && !partesOld.equals(partesNew)) {
                partesOld.setIncidenciaParte(null);
                partesOld = em.merge(partesOld);
            }
            if (partesNew != null && !partesNew.equals(partesOld)) {
                partesNew.setIncidenciaParte(incidenciaParte);
                partesNew = em.merge(partesNew);
            }
            if (idPeticionOld != null && !idPeticionOld.equals(idPeticionNew)) {
                idPeticionOld.getIncidenciaParteList().remove(incidenciaParte);
                idPeticionOld = em.merge(idPeticionOld);
            }
            if (idPeticionNew != null && !idPeticionNew.equals(idPeticionOld)) {
                idPeticionNew.getIncidenciaParteList().add(incidenciaParte);
                idPeticionNew = em.merge(idPeticionNew);
            }
            if (tarifaOld != null && !tarifaOld.equals(tarifaNew)) {
                tarifaOld.getIncidenciaParteList().remove(incidenciaParte);
                tarifaOld = em.merge(tarifaOld);
            }
            if (tarifaNew != null && !tarifaNew.equals(tarifaOld)) {
                tarifaNew.getIncidenciaParteList().add(incidenciaParte);
                tarifaNew = em.merge(tarifaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = incidenciaParte.getNumParte();
                if (findIncidenciaParte(id) == null) {
                    throw new NonexistentEntityException("The incidenciaParte with id " + id + " no longer exists.");
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
            IncidenciaParte incidenciaParte;
            try {
                incidenciaParte = em.getReference(IncidenciaParte.class, id);
                incidenciaParte.getNumParte();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The incidenciaParte with id " + id + " no longer exists.", enfe);
            }
            Partes partes = incidenciaParte.getPartes();
            if (partes != null) {
                partes.setIncidenciaParte(null);
                partes = em.merge(partes);
            }
            Peticiones idPeticion = incidenciaParte.getIdPeticion();
            if (idPeticion != null) {
                idPeticion.getIncidenciaParteList().remove(incidenciaParte);
                idPeticion = em.merge(idPeticion);
            }
            Tarifa tarifa = incidenciaParte.getTarifa();
            if (tarifa != null) {
                tarifa.getIncidenciaParteList().remove(incidenciaParte);
                tarifa = em.merge(tarifa);
            }
            em.remove(incidenciaParte);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<IncidenciaParte> findIncidenciaParteEntities() {
        return findIncidenciaParteEntities(true, -1, -1);
    }

    public List<IncidenciaParte> findIncidenciaParteEntities(int maxResults, int firstResult) {
        return findIncidenciaParteEntities(false, maxResults, firstResult);
    }

    private List<IncidenciaParte> findIncidenciaParteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(IncidenciaParte.class));
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

    public IncidenciaParte findIncidenciaParte(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(IncidenciaParte.class, id);
        } finally {
            em.close();
        }
    }

    public int getIncidenciaParteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<IncidenciaParte> rt = cq.from(IncidenciaParte.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
