/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.controladores;

import com.seidor.jmanteweb.persistencia.controladores.exceptions.NonexistentEntityException;
import com.seidor.jmanteweb.persistencia.controladores.exceptions.PreexistingEntityException;
import com.seidor.jmanteweb.persistencia.entidades.Guardiapalex;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.seidor.jmanteweb.persistencia.entidades.Tarifapalex;
import com.seidor.jmanteweb.persistencia.entidades.Usuario;
import com.seidor.jmanteweb.persistencia.entidades.Partes;
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
public class GuardiapalexJpaController implements Serializable {

    public GuardiapalexJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Guardiapalex guardiapalex) throws PreexistingEntityException, Exception {
        if (guardiapalex.getPartesList() == null) {
            guardiapalex.setPartesList(new ArrayList<Partes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tarifapalex idtarifa = guardiapalex.getIdtarifa();
            if (idtarifa != null) {
                idtarifa = em.getReference(idtarifa.getClass(), idtarifa.getId());
                guardiapalex.setIdtarifa(idtarifa);
            }
            Usuario titular = guardiapalex.getTitular();
            if (titular != null) {
                titular = em.getReference(titular.getClass(), titular.getUsuario());
                guardiapalex.setTitular(titular);
            }
            Usuario usuarioReal = guardiapalex.getUsuarioReal();
            if (usuarioReal != null) {
                usuarioReal = em.getReference(usuarioReal.getClass(), usuarioReal.getUsuario());
                guardiapalex.setUsuarioReal(usuarioReal);
            }
            List<Partes> attachedPartesList = new ArrayList<Partes>();
            for (Partes partesListPartesToAttach : guardiapalex.getPartesList()) {
                partesListPartesToAttach = em.getReference(partesListPartesToAttach.getClass(), partesListPartesToAttach.getNumParte());
                attachedPartesList.add(partesListPartesToAttach);
            }
            guardiapalex.setPartesList(attachedPartesList);
            em.persist(guardiapalex);
            if (idtarifa != null) {
                idtarifa.getGuardiapalexList().add(guardiapalex);
                idtarifa = em.merge(idtarifa);
            }
            if (titular != null) {
                titular.getGuardiapalexList().add(guardiapalex);
                titular = em.merge(titular);
            }
            if (usuarioReal != null) {
                usuarioReal.getGuardiapalexList().add(guardiapalex);
                usuarioReal = em.merge(usuarioReal);
            }
            for (Partes partesListPartes : guardiapalex.getPartesList()) {
                Guardiapalex oldGuardiapalexOfPartesListPartes = partesListPartes.getGuardiapalex();
                partesListPartes.setGuardiapalex(guardiapalex);
                partesListPartes = em.merge(partesListPartes);
                if (oldGuardiapalexOfPartesListPartes != null) {
                    oldGuardiapalexOfPartesListPartes.getPartesList().remove(partesListPartes);
                    oldGuardiapalexOfPartesListPartes = em.merge(oldGuardiapalexOfPartesListPartes);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGuardiapalex(guardiapalex.getIdguardia()) != null) {
                throw new PreexistingEntityException("Guardiapalex " + guardiapalex + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Guardiapalex guardiapalex) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Guardiapalex persistentGuardiapalex = em.find(Guardiapalex.class, guardiapalex.getIdguardia());
            Tarifapalex idtarifaOld = persistentGuardiapalex.getIdtarifa();
            Tarifapalex idtarifaNew = guardiapalex.getIdtarifa();
            Usuario titularOld = persistentGuardiapalex.getTitular();
            Usuario titularNew = guardiapalex.getTitular();
            Usuario usuarioRealOld = persistentGuardiapalex.getUsuarioReal();
            Usuario usuarioRealNew = guardiapalex.getUsuarioReal();
            List<Partes> partesListOld = persistentGuardiapalex.getPartesList();
            List<Partes> partesListNew = guardiapalex.getPartesList();
            if (idtarifaNew != null) {
                idtarifaNew = em.getReference(idtarifaNew.getClass(), idtarifaNew.getId());
                guardiapalex.setIdtarifa(idtarifaNew);
            }
            if (titularNew != null) {
                titularNew = em.getReference(titularNew.getClass(), titularNew.getUsuario());
                guardiapalex.setTitular(titularNew);
            }
            if (usuarioRealNew != null) {
                usuarioRealNew = em.getReference(usuarioRealNew.getClass(), usuarioRealNew.getUsuario());
                guardiapalex.setUsuarioReal(usuarioRealNew);
            }
            List<Partes> attachedPartesListNew = new ArrayList<Partes>();
            for (Partes partesListNewPartesToAttach : partesListNew) {
                partesListNewPartesToAttach = em.getReference(partesListNewPartesToAttach.getClass(), partesListNewPartesToAttach.getNumParte());
                attachedPartesListNew.add(partesListNewPartesToAttach);
            }
            partesListNew = attachedPartesListNew;
            guardiapalex.setPartesList(partesListNew);
            guardiapalex = em.merge(guardiapalex);
            if (idtarifaOld != null && !idtarifaOld.equals(idtarifaNew)) {
                idtarifaOld.getGuardiapalexList().remove(guardiapalex);
                idtarifaOld = em.merge(idtarifaOld);
            }
            if (idtarifaNew != null && !idtarifaNew.equals(idtarifaOld)) {
                idtarifaNew.getGuardiapalexList().add(guardiapalex);
                idtarifaNew = em.merge(idtarifaNew);
            }
            if (titularOld != null && !titularOld.equals(titularNew)) {
                titularOld.getGuardiapalexList().remove(guardiapalex);
                titularOld = em.merge(titularOld);
            }
            if (titularNew != null && !titularNew.equals(titularOld)) {
                titularNew.getGuardiapalexList().add(guardiapalex);
                titularNew = em.merge(titularNew);
            }
            if (usuarioRealOld != null && !usuarioRealOld.equals(usuarioRealNew)) {
                usuarioRealOld.getGuardiapalexList().remove(guardiapalex);
                usuarioRealOld = em.merge(usuarioRealOld);
            }
            if (usuarioRealNew != null && !usuarioRealNew.equals(usuarioRealOld)) {
                usuarioRealNew.getGuardiapalexList().add(guardiapalex);
                usuarioRealNew = em.merge(usuarioRealNew);
            }
            for (Partes partesListOldPartes : partesListOld) {
                if (!partesListNew.contains(partesListOldPartes)) {
                    partesListOldPartes.setGuardiapalex(null);
                    partesListOldPartes = em.merge(partesListOldPartes);
                }
            }
            for (Partes partesListNewPartes : partesListNew) {
                if (!partesListOld.contains(partesListNewPartes)) {
                    Guardiapalex oldGuardiapalexOfPartesListNewPartes = partesListNewPartes.getGuardiapalex();
                    partesListNewPartes.setGuardiapalex(guardiapalex);
                    partesListNewPartes = em.merge(partesListNewPartes);
                    if (oldGuardiapalexOfPartesListNewPartes != null && !oldGuardiapalexOfPartesListNewPartes.equals(guardiapalex)) {
                        oldGuardiapalexOfPartesListNewPartes.getPartesList().remove(partesListNewPartes);
                        oldGuardiapalexOfPartesListNewPartes = em.merge(oldGuardiapalexOfPartesListNewPartes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = guardiapalex.getIdguardia();
                if (findGuardiapalex(id) == null) {
                    throw new NonexistentEntityException("The guardiapalex with id " + id + " no longer exists.");
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
            Guardiapalex guardiapalex;
            try {
                guardiapalex = em.getReference(Guardiapalex.class, id);
                guardiapalex.getIdguardia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The guardiapalex with id " + id + " no longer exists.", enfe);
            }
            Tarifapalex idtarifa = guardiapalex.getIdtarifa();
            if (idtarifa != null) {
                idtarifa.getGuardiapalexList().remove(guardiapalex);
                idtarifa = em.merge(idtarifa);
            }
            Usuario titular = guardiapalex.getTitular();
            if (titular != null) {
                titular.getGuardiapalexList().remove(guardiapalex);
                titular = em.merge(titular);
            }
            Usuario usuarioReal = guardiapalex.getUsuarioReal();
            if (usuarioReal != null) {
                usuarioReal.getGuardiapalexList().remove(guardiapalex);
                usuarioReal = em.merge(usuarioReal);
            }
            List<Partes> partesList = guardiapalex.getPartesList();
            for (Partes partesListPartes : partesList) {
                partesListPartes.setGuardiapalex(null);
                partesListPartes = em.merge(partesListPartes);
            }
            em.remove(guardiapalex);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Guardiapalex> findGuardiapalexEntities() {
        return findGuardiapalexEntities(true, -1, -1);
    }

    public List<Guardiapalex> findGuardiapalexEntities(int maxResults, int firstResult) {
        return findGuardiapalexEntities(false, maxResults, firstResult);
    }

    private List<Guardiapalex> findGuardiapalexEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Guardiapalex.class));
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

    public Guardiapalex findGuardiapalex(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Guardiapalex.class, id);
        } finally {
            em.close();
        }
    }

    public int getGuardiapalexCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Guardiapalex> rt = cq.from(Guardiapalex.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
