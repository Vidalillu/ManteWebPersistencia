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
import com.seidor.jmanteweb.persistencia.entidades.Bonomodis;
import com.seidor.jmanteweb.persistencia.entidades.Clientes;
import com.seidor.jmanteweb.persistencia.entidades.InfoModi;
import com.seidor.jmanteweb.persistencia.entidades.InfoModiPK;
import com.seidor.jmanteweb.persistencia.entidades.Modis;
import com.seidor.jmanteweb.persistencia.entidades.Presupuestos;
import com.seidor.jmanteweb.persistencia.entidades.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class InfoModiJpaController implements Serializable {

    public InfoModiJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(InfoModi infoModi) throws PreexistingEntityException, Exception {
        if (infoModi.getInfoModiPK() == null) {
            infoModi.setInfoModiPK(new InfoModiPK());
        }
        infoModi.getInfoModiPK().setCliente(infoModi.getClientes().getCliente());
        infoModi.getInfoModiPK().setIdmodi(infoModi.getModis().getIdmodi());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bonomodis bonomodis = infoModi.getBonomodis();
            if (bonomodis != null) {
                bonomodis = em.getReference(bonomodis.getClass(), bonomodis.getIdPeticion());
                infoModi.setBonomodis(bonomodis);
            }
            Clientes clientes = infoModi.getClientes();
            if (clientes != null) {
                clientes = em.getReference(clientes.getClass(), clientes.getCliente());
                infoModi.setClientes(clientes);
            }
            Modis modis = infoModi.getModis();
            if (modis != null) {
                modis = em.getReference(modis.getClass(), modis.getIdmodi());
                infoModi.setModis(modis);
            }
            Presupuestos presupuesto = infoModi.getPresupuesto();
            if (presupuesto != null) {
                presupuesto = em.getReference(presupuesto.getClass(), presupuesto.getNum());
                infoModi.setPresupuesto(presupuesto);
            }
            Usuario usuarioaplicacion = infoModi.getUsuarioaplicacion();
            if (usuarioaplicacion != null) {
                usuarioaplicacion = em.getReference(usuarioaplicacion.getClass(), usuarioaplicacion.getUsuario());
                infoModi.setUsuarioaplicacion(usuarioaplicacion);
            }
            em.persist(infoModi);
            if (bonomodis != null) {
                bonomodis.getInfoModiList().add(infoModi);
                bonomodis = em.merge(bonomodis);
            }
            if (clientes != null) {
                clientes.getInfoModiList().add(infoModi);
                clientes = em.merge(clientes);
            }
            if (modis != null) {
                modis.getInfoModiList().add(infoModi);
                modis = em.merge(modis);
            }
            if (presupuesto != null) {
                presupuesto.getInfoModiList().add(infoModi);
                presupuesto = em.merge(presupuesto);
            }
            if (usuarioaplicacion != null) {
                usuarioaplicacion.getInfoModiList().add(infoModi);
                usuarioaplicacion = em.merge(usuarioaplicacion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findInfoModi(infoModi.getInfoModiPK()) != null) {
                throw new PreexistingEntityException("InfoModi " + infoModi + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(InfoModi infoModi) throws NonexistentEntityException, Exception {
        infoModi.getInfoModiPK().setCliente(infoModi.getClientes().getCliente());
        infoModi.getInfoModiPK().setIdmodi(infoModi.getModis().getIdmodi());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            InfoModi persistentInfoModi = em.find(InfoModi.class, infoModi.getInfoModiPK());
            Bonomodis bonomodisOld = persistentInfoModi.getBonomodis();
            Bonomodis bonomodisNew = infoModi.getBonomodis();
            Clientes clientesOld = persistentInfoModi.getClientes();
            Clientes clientesNew = infoModi.getClientes();
            Modis modisOld = persistentInfoModi.getModis();
            Modis modisNew = infoModi.getModis();
            Presupuestos presupuestoOld = persistentInfoModi.getPresupuesto();
            Presupuestos presupuestoNew = infoModi.getPresupuesto();
            Usuario usuarioaplicacionOld = persistentInfoModi.getUsuarioaplicacion();
            Usuario usuarioaplicacionNew = infoModi.getUsuarioaplicacion();
            if (bonomodisNew != null) {
                bonomodisNew = em.getReference(bonomodisNew.getClass(), bonomodisNew.getIdPeticion());
                infoModi.setBonomodis(bonomodisNew);
            }
            if (clientesNew != null) {
                clientesNew = em.getReference(clientesNew.getClass(), clientesNew.getCliente());
                infoModi.setClientes(clientesNew);
            }
            if (modisNew != null) {
                modisNew = em.getReference(modisNew.getClass(), modisNew.getIdmodi());
                infoModi.setModis(modisNew);
            }
            if (presupuestoNew != null) {
                presupuestoNew = em.getReference(presupuestoNew.getClass(), presupuestoNew.getNum());
                infoModi.setPresupuesto(presupuestoNew);
            }
            if (usuarioaplicacionNew != null) {
                usuarioaplicacionNew = em.getReference(usuarioaplicacionNew.getClass(), usuarioaplicacionNew.getUsuario());
                infoModi.setUsuarioaplicacion(usuarioaplicacionNew);
            }
            infoModi = em.merge(infoModi);
            if (bonomodisOld != null && !bonomodisOld.equals(bonomodisNew)) {
                bonomodisOld.getInfoModiList().remove(infoModi);
                bonomodisOld = em.merge(bonomodisOld);
            }
            if (bonomodisNew != null && !bonomodisNew.equals(bonomodisOld)) {
                bonomodisNew.getInfoModiList().add(infoModi);
                bonomodisNew = em.merge(bonomodisNew);
            }
            if (clientesOld != null && !clientesOld.equals(clientesNew)) {
                clientesOld.getInfoModiList().remove(infoModi);
                clientesOld = em.merge(clientesOld);
            }
            if (clientesNew != null && !clientesNew.equals(clientesOld)) {
                clientesNew.getInfoModiList().add(infoModi);
                clientesNew = em.merge(clientesNew);
            }
            if (modisOld != null && !modisOld.equals(modisNew)) {
                modisOld.getInfoModiList().remove(infoModi);
                modisOld = em.merge(modisOld);
            }
            if (modisNew != null && !modisNew.equals(modisOld)) {
                modisNew.getInfoModiList().add(infoModi);
                modisNew = em.merge(modisNew);
            }
            if (presupuestoOld != null && !presupuestoOld.equals(presupuestoNew)) {
                presupuestoOld.getInfoModiList().remove(infoModi);
                presupuestoOld = em.merge(presupuestoOld);
            }
            if (presupuestoNew != null && !presupuestoNew.equals(presupuestoOld)) {
                presupuestoNew.getInfoModiList().add(infoModi);
                presupuestoNew = em.merge(presupuestoNew);
            }
            if (usuarioaplicacionOld != null && !usuarioaplicacionOld.equals(usuarioaplicacionNew)) {
                usuarioaplicacionOld.getInfoModiList().remove(infoModi);
                usuarioaplicacionOld = em.merge(usuarioaplicacionOld);
            }
            if (usuarioaplicacionNew != null && !usuarioaplicacionNew.equals(usuarioaplicacionOld)) {
                usuarioaplicacionNew.getInfoModiList().add(infoModi);
                usuarioaplicacionNew = em.merge(usuarioaplicacionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                InfoModiPK id = infoModi.getInfoModiPK();
                if (findInfoModi(id) == null) {
                    throw new NonexistentEntityException("The infoModi with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(InfoModiPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            InfoModi infoModi;
            try {
                infoModi = em.getReference(InfoModi.class, id);
                infoModi.getInfoModiPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The infoModi with id " + id + " no longer exists.", enfe);
            }
            Bonomodis bonomodis = infoModi.getBonomodis();
            if (bonomodis != null) {
                bonomodis.getInfoModiList().remove(infoModi);
                bonomodis = em.merge(bonomodis);
            }
            Clientes clientes = infoModi.getClientes();
            if (clientes != null) {
                clientes.getInfoModiList().remove(infoModi);
                clientes = em.merge(clientes);
            }
            Modis modis = infoModi.getModis();
            if (modis != null) {
                modis.getInfoModiList().remove(infoModi);
                modis = em.merge(modis);
            }
            Presupuestos presupuesto = infoModi.getPresupuesto();
            if (presupuesto != null) {
                presupuesto.getInfoModiList().remove(infoModi);
                presupuesto = em.merge(presupuesto);
            }
            Usuario usuarioaplicacion = infoModi.getUsuarioaplicacion();
            if (usuarioaplicacion != null) {
                usuarioaplicacion.getInfoModiList().remove(infoModi);
                usuarioaplicacion = em.merge(usuarioaplicacion);
            }
            em.remove(infoModi);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<InfoModi> findInfoModiEntities() {
        return findInfoModiEntities(true, -1, -1);
    }

    public List<InfoModi> findInfoModiEntities(int maxResults, int firstResult) {
        return findInfoModiEntities(false, maxResults, firstResult);
    }

    private List<InfoModi> findInfoModiEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(InfoModi.class));
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

    public InfoModi findInfoModi(InfoModiPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(InfoModi.class, id);
        } finally {
            em.close();
        }
    }

    public int getInfoModiCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<InfoModi> rt = cq.from(InfoModi.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
