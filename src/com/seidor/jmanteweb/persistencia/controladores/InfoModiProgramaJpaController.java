/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.jmanteweb.persistencia.controladores;

import com.seidor.jmanteweb.persistencia.controladores.exceptions.NonexistentEntityException;
import com.seidor.jmanteweb.persistencia.controladores.exceptions.PreexistingEntityException;
import com.seidor.jmanteweb.persistencia.entidades.InfoModiPrograma;
import com.seidor.jmanteweb.persistencia.entidades.InfoModiProgramaPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.seidor.jmanteweb.persistencia.entidades.Modis;
import com.seidor.jmanteweb.persistencia.entidades.Programas;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class InfoModiProgramaJpaController implements Serializable {

    public InfoModiProgramaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(InfoModiPrograma infoModiPrograma) throws PreexistingEntityException, Exception {
        if (infoModiPrograma.getInfoModiProgramaPK() == null) {
            infoModiPrograma.setInfoModiProgramaPK(new InfoModiProgramaPK());
        }
        infoModiPrograma.getInfoModiProgramaPK().setPrograma(infoModiPrograma.getProgramas().getPrograma());
        infoModiPrograma.getInfoModiProgramaPK().setIdmodi(infoModiPrograma.getModis().getIdmodi());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Modis modis = infoModiPrograma.getModis();
            if (modis != null) {
                modis = em.getReference(modis.getClass(), modis.getIdmodi());
                infoModiPrograma.setModis(modis);
            }
            Programas programas = infoModiPrograma.getProgramas();
            if (programas != null) {
                programas = em.getReference(programas.getClass(), programas.getPrograma());
                infoModiPrograma.setProgramas(programas);
            }
            em.persist(infoModiPrograma);
            if (modis != null) {
                modis.getInfoModiProgramaList().add(infoModiPrograma);
                modis = em.merge(modis);
            }
            if (programas != null) {
                programas.getInfoModiProgramaList().add(infoModiPrograma);
                programas = em.merge(programas);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findInfoModiPrograma(infoModiPrograma.getInfoModiProgramaPK()) != null) {
                throw new PreexistingEntityException("InfoModiPrograma " + infoModiPrograma + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(InfoModiPrograma infoModiPrograma) throws NonexistentEntityException, Exception {
        infoModiPrograma.getInfoModiProgramaPK().setPrograma(infoModiPrograma.getProgramas().getPrograma());
        infoModiPrograma.getInfoModiProgramaPK().setIdmodi(infoModiPrograma.getModis().getIdmodi());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            InfoModiPrograma persistentInfoModiPrograma = em.find(InfoModiPrograma.class, infoModiPrograma.getInfoModiProgramaPK());
            Modis modisOld = persistentInfoModiPrograma.getModis();
            Modis modisNew = infoModiPrograma.getModis();
            Programas programasOld = persistentInfoModiPrograma.getProgramas();
            Programas programasNew = infoModiPrograma.getProgramas();
            if (modisNew != null) {
                modisNew = em.getReference(modisNew.getClass(), modisNew.getIdmodi());
                infoModiPrograma.setModis(modisNew);
            }
            if (programasNew != null) {
                programasNew = em.getReference(programasNew.getClass(), programasNew.getPrograma());
                infoModiPrograma.setProgramas(programasNew);
            }
            infoModiPrograma = em.merge(infoModiPrograma);
            if (modisOld != null && !modisOld.equals(modisNew)) {
                modisOld.getInfoModiProgramaList().remove(infoModiPrograma);
                modisOld = em.merge(modisOld);
            }
            if (modisNew != null && !modisNew.equals(modisOld)) {
                modisNew.getInfoModiProgramaList().add(infoModiPrograma);
                modisNew = em.merge(modisNew);
            }
            if (programasOld != null && !programasOld.equals(programasNew)) {
                programasOld.getInfoModiProgramaList().remove(infoModiPrograma);
                programasOld = em.merge(programasOld);
            }
            if (programasNew != null && !programasNew.equals(programasOld)) {
                programasNew.getInfoModiProgramaList().add(infoModiPrograma);
                programasNew = em.merge(programasNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                InfoModiProgramaPK id = infoModiPrograma.getInfoModiProgramaPK();
                if (findInfoModiPrograma(id) == null) {
                    throw new NonexistentEntityException("The infoModiPrograma with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(InfoModiProgramaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            InfoModiPrograma infoModiPrograma;
            try {
                infoModiPrograma = em.getReference(InfoModiPrograma.class, id);
                infoModiPrograma.getInfoModiProgramaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The infoModiPrograma with id " + id + " no longer exists.", enfe);
            }
            Modis modis = infoModiPrograma.getModis();
            if (modis != null) {
                modis.getInfoModiProgramaList().remove(infoModiPrograma);
                modis = em.merge(modis);
            }
            Programas programas = infoModiPrograma.getProgramas();
            if (programas != null) {
                programas.getInfoModiProgramaList().remove(infoModiPrograma);
                programas = em.merge(programas);
            }
            em.remove(infoModiPrograma);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<InfoModiPrograma> findInfoModiProgramaEntities() {
        return findInfoModiProgramaEntities(true, -1, -1);
    }

    public List<InfoModiPrograma> findInfoModiProgramaEntities(int maxResults, int firstResult) {
        return findInfoModiProgramaEntities(false, maxResults, firstResult);
    }

    private List<InfoModiPrograma> findInfoModiProgramaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(InfoModiPrograma.class));
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

    public InfoModiPrograma findInfoModiPrograma(InfoModiProgramaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(InfoModiPrograma.class, id);
        } finally {
            em.close();
        }
    }

    public int getInfoModiProgramaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<InfoModiPrograma> rt = cq.from(InfoModiPrograma.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
