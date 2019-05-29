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
import com.seidor.jmanteweb.persistencia.entidades.Programas;
import java.util.ArrayList;
import java.util.List;
import com.seidor.jmanteweb.persistencia.entidades.InfoModiPrograma;
import com.seidor.jmanteweb.persistencia.entidades.InfoModi;
import com.seidor.jmanteweb.persistencia.entidades.Modis;
import com.seidor.jmanteweb.persistencia.entidades.Tarea;
import java.math.BigInteger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class ModisJpaController implements Serializable {

    public ModisJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Modis modis) throws PreexistingEntityException, Exception {
        if (modis.getProgramasList() == null) {
            modis.setProgramasList(new ArrayList<Programas>());
        }
        if (modis.getInfoModiProgramaList() == null) {
            modis.setInfoModiProgramaList(new ArrayList<InfoModiPrograma>());
        }
        if (modis.getInfoModiList() == null) {
            modis.setInfoModiList(new ArrayList<InfoModi>());
        }
        if (modis.getTareaList() == null) {
            modis.setTareaList(new ArrayList<Tarea>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Programas> attachedProgramasList = new ArrayList<Programas>();
            for (Programas programasListProgramasToAttach : modis.getProgramasList()) {
                programasListProgramasToAttach = em.getReference(programasListProgramasToAttach.getClass(), programasListProgramasToAttach.getPrograma());
                attachedProgramasList.add(programasListProgramasToAttach);
            }
            modis.setProgramasList(attachedProgramasList);
            List<InfoModiPrograma> attachedInfoModiProgramaList = new ArrayList<InfoModiPrograma>();
            for (InfoModiPrograma infoModiProgramaListInfoModiProgramaToAttach : modis.getInfoModiProgramaList()) {
                infoModiProgramaListInfoModiProgramaToAttach = em.getReference(infoModiProgramaListInfoModiProgramaToAttach.getClass(), infoModiProgramaListInfoModiProgramaToAttach.getInfoModiProgramaPK());
                attachedInfoModiProgramaList.add(infoModiProgramaListInfoModiProgramaToAttach);
            }
            modis.setInfoModiProgramaList(attachedInfoModiProgramaList);
            List<InfoModi> attachedInfoModiList = new ArrayList<InfoModi>();
            for (InfoModi infoModiListInfoModiToAttach : modis.getInfoModiList()) {
                infoModiListInfoModiToAttach = em.getReference(infoModiListInfoModiToAttach.getClass(), infoModiListInfoModiToAttach.getInfoModiPK());
                attachedInfoModiList.add(infoModiListInfoModiToAttach);
            }
            modis.setInfoModiList(attachedInfoModiList);
            List<Tarea> attachedTareaList = new ArrayList<Tarea>();
            for (Tarea tareaListTareaToAttach : modis.getTareaList()) {
                tareaListTareaToAttach = em.getReference(tareaListTareaToAttach.getClass(), tareaListTareaToAttach.getId());
                attachedTareaList.add(tareaListTareaToAttach);
            }
            modis.setTareaList(attachedTareaList);
            em.persist(modis);
            for (Programas programasListProgramas : modis.getProgramasList()) {
                programasListProgramas.getModisList().add(modis);
                programasListProgramas = em.merge(programasListProgramas);
            }
            for (InfoModiPrograma infoModiProgramaListInfoModiPrograma : modis.getInfoModiProgramaList()) {
                Modis oldModisOfInfoModiProgramaListInfoModiPrograma = infoModiProgramaListInfoModiPrograma.getModis();
                infoModiProgramaListInfoModiPrograma.setModis(modis);
                infoModiProgramaListInfoModiPrograma = em.merge(infoModiProgramaListInfoModiPrograma);
                if (oldModisOfInfoModiProgramaListInfoModiPrograma != null) {
                    oldModisOfInfoModiProgramaListInfoModiPrograma.getInfoModiProgramaList().remove(infoModiProgramaListInfoModiPrograma);
                    oldModisOfInfoModiProgramaListInfoModiPrograma = em.merge(oldModisOfInfoModiProgramaListInfoModiPrograma);
                }
            }
            for (InfoModi infoModiListInfoModi : modis.getInfoModiList()) {
                Modis oldModisOfInfoModiListInfoModi = infoModiListInfoModi.getModis();
                infoModiListInfoModi.setModis(modis);
                infoModiListInfoModi = em.merge(infoModiListInfoModi);
                if (oldModisOfInfoModiListInfoModi != null) {
                    oldModisOfInfoModiListInfoModi.getInfoModiList().remove(infoModiListInfoModi);
                    oldModisOfInfoModiListInfoModi = em.merge(oldModisOfInfoModiListInfoModi);
                }
            }
            for (Tarea tareaListTarea : modis.getTareaList()) {
                Modis oldIdmodiOfTareaListTarea = tareaListTarea.getIdmodi();
                tareaListTarea.setIdmodi(modis);
                tareaListTarea = em.merge(tareaListTarea);
                if (oldIdmodiOfTareaListTarea != null) {
                    oldIdmodiOfTareaListTarea.getTareaList().remove(tareaListTarea);
                    oldIdmodiOfTareaListTarea = em.merge(oldIdmodiOfTareaListTarea);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findModis(modis.getIdmodi()) != null) {
                throw new PreexistingEntityException("Modis " + modis + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Modis modis) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Modis persistentModis = em.find(Modis.class, modis.getIdmodi());
            List<Programas> programasListOld = persistentModis.getProgramasList();
            List<Programas> programasListNew = modis.getProgramasList();
            List<InfoModiPrograma> infoModiProgramaListOld = persistentModis.getInfoModiProgramaList();
            List<InfoModiPrograma> infoModiProgramaListNew = modis.getInfoModiProgramaList();
            List<InfoModi> infoModiListOld = persistentModis.getInfoModiList();
            List<InfoModi> infoModiListNew = modis.getInfoModiList();
            List<Tarea> tareaListOld = persistentModis.getTareaList();
            List<Tarea> tareaListNew = modis.getTareaList();
            List<String> illegalOrphanMessages = null;
            for (InfoModiPrograma infoModiProgramaListOldInfoModiPrograma : infoModiProgramaListOld) {
                if (!infoModiProgramaListNew.contains(infoModiProgramaListOldInfoModiPrograma)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InfoModiPrograma " + infoModiProgramaListOldInfoModiPrograma + " since its modis field is not nullable.");
                }
            }
            for (InfoModi infoModiListOldInfoModi : infoModiListOld) {
                if (!infoModiListNew.contains(infoModiListOldInfoModi)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InfoModi " + infoModiListOldInfoModi + " since its modis field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Programas> attachedProgramasListNew = new ArrayList<Programas>();
            for (Programas programasListNewProgramasToAttach : programasListNew) {
                programasListNewProgramasToAttach = em.getReference(programasListNewProgramasToAttach.getClass(), programasListNewProgramasToAttach.getPrograma());
                attachedProgramasListNew.add(programasListNewProgramasToAttach);
            }
            programasListNew = attachedProgramasListNew;
            modis.setProgramasList(programasListNew);
            List<InfoModiPrograma> attachedInfoModiProgramaListNew = new ArrayList<InfoModiPrograma>();
            for (InfoModiPrograma infoModiProgramaListNewInfoModiProgramaToAttach : infoModiProgramaListNew) {
                infoModiProgramaListNewInfoModiProgramaToAttach = em.getReference(infoModiProgramaListNewInfoModiProgramaToAttach.getClass(), infoModiProgramaListNewInfoModiProgramaToAttach.getInfoModiProgramaPK());
                attachedInfoModiProgramaListNew.add(infoModiProgramaListNewInfoModiProgramaToAttach);
            }
            infoModiProgramaListNew = attachedInfoModiProgramaListNew;
            modis.setInfoModiProgramaList(infoModiProgramaListNew);
            List<InfoModi> attachedInfoModiListNew = new ArrayList<InfoModi>();
            for (InfoModi infoModiListNewInfoModiToAttach : infoModiListNew) {
                infoModiListNewInfoModiToAttach = em.getReference(infoModiListNewInfoModiToAttach.getClass(), infoModiListNewInfoModiToAttach.getInfoModiPK());
                attachedInfoModiListNew.add(infoModiListNewInfoModiToAttach);
            }
            infoModiListNew = attachedInfoModiListNew;
            modis.setInfoModiList(infoModiListNew);
            List<Tarea> attachedTareaListNew = new ArrayList<Tarea>();
            for (Tarea tareaListNewTareaToAttach : tareaListNew) {
                tareaListNewTareaToAttach = em.getReference(tareaListNewTareaToAttach.getClass(), tareaListNewTareaToAttach.getId());
                attachedTareaListNew.add(tareaListNewTareaToAttach);
            }
            tareaListNew = attachedTareaListNew;
            modis.setTareaList(tareaListNew);
            modis = em.merge(modis);
            for (Programas programasListOldProgramas : programasListOld) {
                if (!programasListNew.contains(programasListOldProgramas)) {
                    programasListOldProgramas.getModisList().remove(modis);
                    programasListOldProgramas = em.merge(programasListOldProgramas);
                }
            }
            for (Programas programasListNewProgramas : programasListNew) {
                if (!programasListOld.contains(programasListNewProgramas)) {
                    programasListNewProgramas.getModisList().add(modis);
                    programasListNewProgramas = em.merge(programasListNewProgramas);
                }
            }
            for (InfoModiPrograma infoModiProgramaListNewInfoModiPrograma : infoModiProgramaListNew) {
                if (!infoModiProgramaListOld.contains(infoModiProgramaListNewInfoModiPrograma)) {
                    Modis oldModisOfInfoModiProgramaListNewInfoModiPrograma = infoModiProgramaListNewInfoModiPrograma.getModis();
                    infoModiProgramaListNewInfoModiPrograma.setModis(modis);
                    infoModiProgramaListNewInfoModiPrograma = em.merge(infoModiProgramaListNewInfoModiPrograma);
                    if (oldModisOfInfoModiProgramaListNewInfoModiPrograma != null && !oldModisOfInfoModiProgramaListNewInfoModiPrograma.equals(modis)) {
                        oldModisOfInfoModiProgramaListNewInfoModiPrograma.getInfoModiProgramaList().remove(infoModiProgramaListNewInfoModiPrograma);
                        oldModisOfInfoModiProgramaListNewInfoModiPrograma = em.merge(oldModisOfInfoModiProgramaListNewInfoModiPrograma);
                    }
                }
            }
            for (InfoModi infoModiListNewInfoModi : infoModiListNew) {
                if (!infoModiListOld.contains(infoModiListNewInfoModi)) {
                    Modis oldModisOfInfoModiListNewInfoModi = infoModiListNewInfoModi.getModis();
                    infoModiListNewInfoModi.setModis(modis);
                    infoModiListNewInfoModi = em.merge(infoModiListNewInfoModi);
                    if (oldModisOfInfoModiListNewInfoModi != null && !oldModisOfInfoModiListNewInfoModi.equals(modis)) {
                        oldModisOfInfoModiListNewInfoModi.getInfoModiList().remove(infoModiListNewInfoModi);
                        oldModisOfInfoModiListNewInfoModi = em.merge(oldModisOfInfoModiListNewInfoModi);
                    }
                }
            }
            for (Tarea tareaListOldTarea : tareaListOld) {
                if (!tareaListNew.contains(tareaListOldTarea)) {
                    tareaListOldTarea.setIdmodi(null);
                    tareaListOldTarea = em.merge(tareaListOldTarea);
                }
            }
            for (Tarea tareaListNewTarea : tareaListNew) {
                if (!tareaListOld.contains(tareaListNewTarea)) {
                    Modis oldIdmodiOfTareaListNewTarea = tareaListNewTarea.getIdmodi();
                    tareaListNewTarea.setIdmodi(modis);
                    tareaListNewTarea = em.merge(tareaListNewTarea);
                    if (oldIdmodiOfTareaListNewTarea != null && !oldIdmodiOfTareaListNewTarea.equals(modis)) {
                        oldIdmodiOfTareaListNewTarea.getTareaList().remove(tareaListNewTarea);
                        oldIdmodiOfTareaListNewTarea = em.merge(oldIdmodiOfTareaListNewTarea);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigInteger id = modis.getIdmodi();
                if (findModis(id) == null) {
                    throw new NonexistentEntityException("The modis with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigInteger id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Modis modis;
            try {
                modis = em.getReference(Modis.class, id);
                modis.getIdmodi();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The modis with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<InfoModiPrograma> infoModiProgramaListOrphanCheck = modis.getInfoModiProgramaList();
            for (InfoModiPrograma infoModiProgramaListOrphanCheckInfoModiPrograma : infoModiProgramaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Modis (" + modis + ") cannot be destroyed since the InfoModiPrograma " + infoModiProgramaListOrphanCheckInfoModiPrograma + " in its infoModiProgramaList field has a non-nullable modis field.");
            }
            List<InfoModi> infoModiListOrphanCheck = modis.getInfoModiList();
            for (InfoModi infoModiListOrphanCheckInfoModi : infoModiListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Modis (" + modis + ") cannot be destroyed since the InfoModi " + infoModiListOrphanCheckInfoModi + " in its infoModiList field has a non-nullable modis field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Programas> programasList = modis.getProgramasList();
            for (Programas programasListProgramas : programasList) {
                programasListProgramas.getModisList().remove(modis);
                programasListProgramas = em.merge(programasListProgramas);
            }
            List<Tarea> tareaList = modis.getTareaList();
            for (Tarea tareaListTarea : tareaList) {
                tareaListTarea.setIdmodi(null);
                tareaListTarea = em.merge(tareaListTarea);
            }
            em.remove(modis);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Modis> findModisEntities() {
        return findModisEntities(true, -1, -1);
    }

    public List<Modis> findModisEntities(int maxResults, int firstResult) {
        return findModisEntities(false, maxResults, firstResult);
    }

    private List<Modis> findModisEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Modis.class));
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

    public Modis findModis(BigInteger id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Modis.class, id);
        } finally {
            em.close();
        }
    }

    public int getModisCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Modis> rt = cq.from(Modis.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
