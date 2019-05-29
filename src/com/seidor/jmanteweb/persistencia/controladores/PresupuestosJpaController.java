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
import com.seidor.jmanteweb.persistencia.entidades.Peticiones;
import com.seidor.jmanteweb.persistencia.entidades.Usuario;
import com.seidor.jmanteweb.persistencia.entidades.InfoModi;
import com.seidor.jmanteweb.persistencia.entidades.Presupuestos;
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
public class PresupuestosJpaController implements Serializable {

    public PresupuestosJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Presupuestos presupuestos) throws PreexistingEntityException, Exception {
        if (presupuestos.getInfoModiList() == null) {
            presupuestos.setInfoModiList(new ArrayList<InfoModi>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Peticiones idPeticion = presupuestos.getIdPeticion();
            if (idPeticion != null) {
                idPeticion = em.getReference(idPeticion.getClass(), idPeticion.getIdpeticion());
                presupuestos.setIdPeticion(idPeticion);
            }
            Usuario usuarioMante = presupuestos.getUsuarioMante();
            if (usuarioMante != null) {
                usuarioMante = em.getReference(usuarioMante.getClass(), usuarioMante.getUsuario());
                presupuestos.setUsuarioMante(usuarioMante);
            }
            List<InfoModi> attachedInfoModiList = new ArrayList<InfoModi>();
            for (InfoModi infoModiListInfoModiToAttach : presupuestos.getInfoModiList()) {
                infoModiListInfoModiToAttach = em.getReference(infoModiListInfoModiToAttach.getClass(), infoModiListInfoModiToAttach.getInfoModiPK());
                attachedInfoModiList.add(infoModiListInfoModiToAttach);
            }
            presupuestos.setInfoModiList(attachedInfoModiList);
            em.persist(presupuestos);
            if (idPeticion != null) {
                idPeticion.getPresupuestosList().add(presupuestos);
                idPeticion = em.merge(idPeticion);
            }
            if (usuarioMante != null) {
                usuarioMante.getPresupuestosList().add(presupuestos);
                usuarioMante = em.merge(usuarioMante);
            }
            for (InfoModi infoModiListInfoModi : presupuestos.getInfoModiList()) {
                Presupuestos oldPresupuestoOfInfoModiListInfoModi = infoModiListInfoModi.getPresupuesto();
                infoModiListInfoModi.setPresupuesto(presupuestos);
                infoModiListInfoModi = em.merge(infoModiListInfoModi);
                if (oldPresupuestoOfInfoModiListInfoModi != null) {
                    oldPresupuestoOfInfoModiListInfoModi.getInfoModiList().remove(infoModiListInfoModi);
                    oldPresupuestoOfInfoModiListInfoModi = em.merge(oldPresupuestoOfInfoModiListInfoModi);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPresupuestos(presupuestos.getNum()) != null) {
                throw new PreexistingEntityException("Presupuestos " + presupuestos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Presupuestos presupuestos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Presupuestos persistentPresupuestos = em.find(Presupuestos.class, presupuestos.getNum());
            Peticiones idPeticionOld = persistentPresupuestos.getIdPeticion();
            Peticiones idPeticionNew = presupuestos.getIdPeticion();
            Usuario usuarioManteOld = persistentPresupuestos.getUsuarioMante();
            Usuario usuarioManteNew = presupuestos.getUsuarioMante();
            List<InfoModi> infoModiListOld = persistentPresupuestos.getInfoModiList();
            List<InfoModi> infoModiListNew = presupuestos.getInfoModiList();
            if (idPeticionNew != null) {
                idPeticionNew = em.getReference(idPeticionNew.getClass(), idPeticionNew.getIdpeticion());
                presupuestos.setIdPeticion(idPeticionNew);
            }
            if (usuarioManteNew != null) {
                usuarioManteNew = em.getReference(usuarioManteNew.getClass(), usuarioManteNew.getUsuario());
                presupuestos.setUsuarioMante(usuarioManteNew);
            }
            List<InfoModi> attachedInfoModiListNew = new ArrayList<InfoModi>();
            for (InfoModi infoModiListNewInfoModiToAttach : infoModiListNew) {
                infoModiListNewInfoModiToAttach = em.getReference(infoModiListNewInfoModiToAttach.getClass(), infoModiListNewInfoModiToAttach.getInfoModiPK());
                attachedInfoModiListNew.add(infoModiListNewInfoModiToAttach);
            }
            infoModiListNew = attachedInfoModiListNew;
            presupuestos.setInfoModiList(infoModiListNew);
            presupuestos = em.merge(presupuestos);
            if (idPeticionOld != null && !idPeticionOld.equals(idPeticionNew)) {
                idPeticionOld.getPresupuestosList().remove(presupuestos);
                idPeticionOld = em.merge(idPeticionOld);
            }
            if (idPeticionNew != null && !idPeticionNew.equals(idPeticionOld)) {
                idPeticionNew.getPresupuestosList().add(presupuestos);
                idPeticionNew = em.merge(idPeticionNew);
            }
            if (usuarioManteOld != null && !usuarioManteOld.equals(usuarioManteNew)) {
                usuarioManteOld.getPresupuestosList().remove(presupuestos);
                usuarioManteOld = em.merge(usuarioManteOld);
            }
            if (usuarioManteNew != null && !usuarioManteNew.equals(usuarioManteOld)) {
                usuarioManteNew.getPresupuestosList().add(presupuestos);
                usuarioManteNew = em.merge(usuarioManteNew);
            }
            for (InfoModi infoModiListOldInfoModi : infoModiListOld) {
                if (!infoModiListNew.contains(infoModiListOldInfoModi)) {
                    infoModiListOldInfoModi.setPresupuesto(null);
                    infoModiListOldInfoModi = em.merge(infoModiListOldInfoModi);
                }
            }
            for (InfoModi infoModiListNewInfoModi : infoModiListNew) {
                if (!infoModiListOld.contains(infoModiListNewInfoModi)) {
                    Presupuestos oldPresupuestoOfInfoModiListNewInfoModi = infoModiListNewInfoModi.getPresupuesto();
                    infoModiListNewInfoModi.setPresupuesto(presupuestos);
                    infoModiListNewInfoModi = em.merge(infoModiListNewInfoModi);
                    if (oldPresupuestoOfInfoModiListNewInfoModi != null && !oldPresupuestoOfInfoModiListNewInfoModi.equals(presupuestos)) {
                        oldPresupuestoOfInfoModiListNewInfoModi.getInfoModiList().remove(infoModiListNewInfoModi);
                        oldPresupuestoOfInfoModiListNewInfoModi = em.merge(oldPresupuestoOfInfoModiListNewInfoModi);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = presupuestos.getNum();
                if (findPresupuestos(id) == null) {
                    throw new NonexistentEntityException("The presupuestos with id " + id + " no longer exists.");
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
            Presupuestos presupuestos;
            try {
                presupuestos = em.getReference(Presupuestos.class, id);
                presupuestos.getNum();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The presupuestos with id " + id + " no longer exists.", enfe);
            }
            Peticiones idPeticion = presupuestos.getIdPeticion();
            if (idPeticion != null) {
                idPeticion.getPresupuestosList().remove(presupuestos);
                idPeticion = em.merge(idPeticion);
            }
            Usuario usuarioMante = presupuestos.getUsuarioMante();
            if (usuarioMante != null) {
                usuarioMante.getPresupuestosList().remove(presupuestos);
                usuarioMante = em.merge(usuarioMante);
            }
            List<InfoModi> infoModiList = presupuestos.getInfoModiList();
            for (InfoModi infoModiListInfoModi : infoModiList) {
                infoModiListInfoModi.setPresupuesto(null);
                infoModiListInfoModi = em.merge(infoModiListInfoModi);
            }
            em.remove(presupuestos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Presupuestos> findPresupuestosEntities() {
        return findPresupuestosEntities(true, -1, -1);
    }

    public List<Presupuestos> findPresupuestosEntities(int maxResults, int firstResult) {
        return findPresupuestosEntities(false, maxResults, firstResult);
    }

    private List<Presupuestos> findPresupuestosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Presupuestos.class));
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

    public Presupuestos findPresupuestos(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Presupuestos.class, id);
        } finally {
            em.close();
        }
    }

    public int getPresupuestosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Presupuestos> rt = cq.from(Presupuestos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
