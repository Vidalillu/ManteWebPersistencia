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
import com.seidor.jmanteweb.persistencia.entidades.Modis;
import java.util.ArrayList;
import java.util.List;
import com.seidor.jmanteweb.persistencia.entidades.InfoModiPrograma;
import com.seidor.jmanteweb.persistencia.entidades.InfoPrograma;
import com.seidor.jmanteweb.persistencia.entidades.Partes;
import com.seidor.jmanteweb.persistencia.entidades.Programas;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class ProgramasJpaController implements Serializable {

    public ProgramasJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Programas programas) throws PreexistingEntityException, Exception {
        if (programas.getModisList() == null) {
            programas.setModisList(new ArrayList<Modis>());
        }
        if (programas.getInfoModiProgramaList() == null) {
            programas.setInfoModiProgramaList(new ArrayList<InfoModiPrograma>());
        }
        if (programas.getInfoProgramaList() == null) {
            programas.setInfoProgramaList(new ArrayList<InfoPrograma>());
        }
        if (programas.getPartesList() == null) {
            programas.setPartesList(new ArrayList<Partes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Modis> attachedModisList = new ArrayList<Modis>();
            for (Modis modisListModisToAttach : programas.getModisList()) {
                modisListModisToAttach = em.getReference(modisListModisToAttach.getClass(), modisListModisToAttach.getIdmodi());
                attachedModisList.add(modisListModisToAttach);
            }
            programas.setModisList(attachedModisList);
            List<InfoModiPrograma> attachedInfoModiProgramaList = new ArrayList<InfoModiPrograma>();
            for (InfoModiPrograma infoModiProgramaListInfoModiProgramaToAttach : programas.getInfoModiProgramaList()) {
                infoModiProgramaListInfoModiProgramaToAttach = em.getReference(infoModiProgramaListInfoModiProgramaToAttach.getClass(), infoModiProgramaListInfoModiProgramaToAttach.getInfoModiProgramaPK());
                attachedInfoModiProgramaList.add(infoModiProgramaListInfoModiProgramaToAttach);
            }
            programas.setInfoModiProgramaList(attachedInfoModiProgramaList);
            List<InfoPrograma> attachedInfoProgramaList = new ArrayList<InfoPrograma>();
            for (InfoPrograma infoProgramaListInfoProgramaToAttach : programas.getInfoProgramaList()) {
                infoProgramaListInfoProgramaToAttach = em.getReference(infoProgramaListInfoProgramaToAttach.getClass(), infoProgramaListInfoProgramaToAttach.getInfoProgramaPK());
                attachedInfoProgramaList.add(infoProgramaListInfoProgramaToAttach);
            }
            programas.setInfoProgramaList(attachedInfoProgramaList);
            List<Partes> attachedPartesList = new ArrayList<Partes>();
            for (Partes partesListPartesToAttach : programas.getPartesList()) {
                partesListPartesToAttach = em.getReference(partesListPartesToAttach.getClass(), partesListPartesToAttach.getNumParte());
                attachedPartesList.add(partesListPartesToAttach);
            }
            programas.setPartesList(attachedPartesList);
            em.persist(programas);
            for (Modis modisListModis : programas.getModisList()) {
                modisListModis.getProgramasList().add(programas);
                modisListModis = em.merge(modisListModis);
            }
            for (InfoModiPrograma infoModiProgramaListInfoModiPrograma : programas.getInfoModiProgramaList()) {
                Programas oldProgramasOfInfoModiProgramaListInfoModiPrograma = infoModiProgramaListInfoModiPrograma.getProgramas();
                infoModiProgramaListInfoModiPrograma.setProgramas(programas);
                infoModiProgramaListInfoModiPrograma = em.merge(infoModiProgramaListInfoModiPrograma);
                if (oldProgramasOfInfoModiProgramaListInfoModiPrograma != null) {
                    oldProgramasOfInfoModiProgramaListInfoModiPrograma.getInfoModiProgramaList().remove(infoModiProgramaListInfoModiPrograma);
                    oldProgramasOfInfoModiProgramaListInfoModiPrograma = em.merge(oldProgramasOfInfoModiProgramaListInfoModiPrograma);
                }
            }
            for (InfoPrograma infoProgramaListInfoPrograma : programas.getInfoProgramaList()) {
                Programas oldProgramasOfInfoProgramaListInfoPrograma = infoProgramaListInfoPrograma.getProgramas();
                infoProgramaListInfoPrograma.setProgramas(programas);
                infoProgramaListInfoPrograma = em.merge(infoProgramaListInfoPrograma);
                if (oldProgramasOfInfoProgramaListInfoPrograma != null) {
                    oldProgramasOfInfoProgramaListInfoPrograma.getInfoProgramaList().remove(infoProgramaListInfoPrograma);
                    oldProgramasOfInfoProgramaListInfoPrograma = em.merge(oldProgramasOfInfoProgramaListInfoPrograma);
                }
            }
            for (Partes partesListPartes : programas.getPartesList()) {
                Programas oldProgramaOfPartesListPartes = partesListPartes.getPrograma();
                partesListPartes.setPrograma(programas);
                partesListPartes = em.merge(partesListPartes);
                if (oldProgramaOfPartesListPartes != null) {
                    oldProgramaOfPartesListPartes.getPartesList().remove(partesListPartes);
                    oldProgramaOfPartesListPartes = em.merge(oldProgramaOfPartesListPartes);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProgramas(programas.getPrograma()) != null) {
                throw new PreexistingEntityException("Programas " + programas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Programas programas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Programas persistentProgramas = em.find(Programas.class, programas.getPrograma());
            List<Modis> modisListOld = persistentProgramas.getModisList();
            List<Modis> modisListNew = programas.getModisList();
            List<InfoModiPrograma> infoModiProgramaListOld = persistentProgramas.getInfoModiProgramaList();
            List<InfoModiPrograma> infoModiProgramaListNew = programas.getInfoModiProgramaList();
            List<InfoPrograma> infoProgramaListOld = persistentProgramas.getInfoProgramaList();
            List<InfoPrograma> infoProgramaListNew = programas.getInfoProgramaList();
            List<Partes> partesListOld = persistentProgramas.getPartesList();
            List<Partes> partesListNew = programas.getPartesList();
            List<String> illegalOrphanMessages = null;
            for (InfoModiPrograma infoModiProgramaListOldInfoModiPrograma : infoModiProgramaListOld) {
                if (!infoModiProgramaListNew.contains(infoModiProgramaListOldInfoModiPrograma)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InfoModiPrograma " + infoModiProgramaListOldInfoModiPrograma + " since its programas field is not nullable.");
                }
            }
            for (InfoPrograma infoProgramaListOldInfoPrograma : infoProgramaListOld) {
                if (!infoProgramaListNew.contains(infoProgramaListOldInfoPrograma)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InfoPrograma " + infoProgramaListOldInfoPrograma + " since its programas field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Modis> attachedModisListNew = new ArrayList<Modis>();
            for (Modis modisListNewModisToAttach : modisListNew) {
                modisListNewModisToAttach = em.getReference(modisListNewModisToAttach.getClass(), modisListNewModisToAttach.getIdmodi());
                attachedModisListNew.add(modisListNewModisToAttach);
            }
            modisListNew = attachedModisListNew;
            programas.setModisList(modisListNew);
            List<InfoModiPrograma> attachedInfoModiProgramaListNew = new ArrayList<InfoModiPrograma>();
            for (InfoModiPrograma infoModiProgramaListNewInfoModiProgramaToAttach : infoModiProgramaListNew) {
                infoModiProgramaListNewInfoModiProgramaToAttach = em.getReference(infoModiProgramaListNewInfoModiProgramaToAttach.getClass(), infoModiProgramaListNewInfoModiProgramaToAttach.getInfoModiProgramaPK());
                attachedInfoModiProgramaListNew.add(infoModiProgramaListNewInfoModiProgramaToAttach);
            }
            infoModiProgramaListNew = attachedInfoModiProgramaListNew;
            programas.setInfoModiProgramaList(infoModiProgramaListNew);
            List<InfoPrograma> attachedInfoProgramaListNew = new ArrayList<InfoPrograma>();
            for (InfoPrograma infoProgramaListNewInfoProgramaToAttach : infoProgramaListNew) {
                infoProgramaListNewInfoProgramaToAttach = em.getReference(infoProgramaListNewInfoProgramaToAttach.getClass(), infoProgramaListNewInfoProgramaToAttach.getInfoProgramaPK());
                attachedInfoProgramaListNew.add(infoProgramaListNewInfoProgramaToAttach);
            }
            infoProgramaListNew = attachedInfoProgramaListNew;
            programas.setInfoProgramaList(infoProgramaListNew);
            List<Partes> attachedPartesListNew = new ArrayList<Partes>();
            for (Partes partesListNewPartesToAttach : partesListNew) {
                partesListNewPartesToAttach = em.getReference(partesListNewPartesToAttach.getClass(), partesListNewPartesToAttach.getNumParte());
                attachedPartesListNew.add(partesListNewPartesToAttach);
            }
            partesListNew = attachedPartesListNew;
            programas.setPartesList(partesListNew);
            programas = em.merge(programas);
            for (Modis modisListOldModis : modisListOld) {
                if (!modisListNew.contains(modisListOldModis)) {
                    modisListOldModis.getProgramasList().remove(programas);
                    modisListOldModis = em.merge(modisListOldModis);
                }
            }
            for (Modis modisListNewModis : modisListNew) {
                if (!modisListOld.contains(modisListNewModis)) {
                    modisListNewModis.getProgramasList().add(programas);
                    modisListNewModis = em.merge(modisListNewModis);
                }
            }
            for (InfoModiPrograma infoModiProgramaListNewInfoModiPrograma : infoModiProgramaListNew) {
                if (!infoModiProgramaListOld.contains(infoModiProgramaListNewInfoModiPrograma)) {
                    Programas oldProgramasOfInfoModiProgramaListNewInfoModiPrograma = infoModiProgramaListNewInfoModiPrograma.getProgramas();
                    infoModiProgramaListNewInfoModiPrograma.setProgramas(programas);
                    infoModiProgramaListNewInfoModiPrograma = em.merge(infoModiProgramaListNewInfoModiPrograma);
                    if (oldProgramasOfInfoModiProgramaListNewInfoModiPrograma != null && !oldProgramasOfInfoModiProgramaListNewInfoModiPrograma.equals(programas)) {
                        oldProgramasOfInfoModiProgramaListNewInfoModiPrograma.getInfoModiProgramaList().remove(infoModiProgramaListNewInfoModiPrograma);
                        oldProgramasOfInfoModiProgramaListNewInfoModiPrograma = em.merge(oldProgramasOfInfoModiProgramaListNewInfoModiPrograma);
                    }
                }
            }
            for (InfoPrograma infoProgramaListNewInfoPrograma : infoProgramaListNew) {
                if (!infoProgramaListOld.contains(infoProgramaListNewInfoPrograma)) {
                    Programas oldProgramasOfInfoProgramaListNewInfoPrograma = infoProgramaListNewInfoPrograma.getProgramas();
                    infoProgramaListNewInfoPrograma.setProgramas(programas);
                    infoProgramaListNewInfoPrograma = em.merge(infoProgramaListNewInfoPrograma);
                    if (oldProgramasOfInfoProgramaListNewInfoPrograma != null && !oldProgramasOfInfoProgramaListNewInfoPrograma.equals(programas)) {
                        oldProgramasOfInfoProgramaListNewInfoPrograma.getInfoProgramaList().remove(infoProgramaListNewInfoPrograma);
                        oldProgramasOfInfoProgramaListNewInfoPrograma = em.merge(oldProgramasOfInfoProgramaListNewInfoPrograma);
                    }
                }
            }
            for (Partes partesListOldPartes : partesListOld) {
                if (!partesListNew.contains(partesListOldPartes)) {
                    partesListOldPartes.setPrograma(null);
                    partesListOldPartes = em.merge(partesListOldPartes);
                }
            }
            for (Partes partesListNewPartes : partesListNew) {
                if (!partesListOld.contains(partesListNewPartes)) {
                    Programas oldProgramaOfPartesListNewPartes = partesListNewPartes.getPrograma();
                    partesListNewPartes.setPrograma(programas);
                    partesListNewPartes = em.merge(partesListNewPartes);
                    if (oldProgramaOfPartesListNewPartes != null && !oldProgramaOfPartesListNewPartes.equals(programas)) {
                        oldProgramaOfPartesListNewPartes.getPartesList().remove(partesListNewPartes);
                        oldProgramaOfPartesListNewPartes = em.merge(oldProgramaOfPartesListNewPartes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = programas.getPrograma();
                if (findProgramas(id) == null) {
                    throw new NonexistentEntityException("The programas with id " + id + " no longer exists.");
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
            Programas programas;
            try {
                programas = em.getReference(Programas.class, id);
                programas.getPrograma();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The programas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<InfoModiPrograma> infoModiProgramaListOrphanCheck = programas.getInfoModiProgramaList();
            for (InfoModiPrograma infoModiProgramaListOrphanCheckInfoModiPrograma : infoModiProgramaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Programas (" + programas + ") cannot be destroyed since the InfoModiPrograma " + infoModiProgramaListOrphanCheckInfoModiPrograma + " in its infoModiProgramaList field has a non-nullable programas field.");
            }
            List<InfoPrograma> infoProgramaListOrphanCheck = programas.getInfoProgramaList();
            for (InfoPrograma infoProgramaListOrphanCheckInfoPrograma : infoProgramaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Programas (" + programas + ") cannot be destroyed since the InfoPrograma " + infoProgramaListOrphanCheckInfoPrograma + " in its infoProgramaList field has a non-nullable programas field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Modis> modisList = programas.getModisList();
            for (Modis modisListModis : modisList) {
                modisListModis.getProgramasList().remove(programas);
                modisListModis = em.merge(modisListModis);
            }
            List<Partes> partesList = programas.getPartesList();
            for (Partes partesListPartes : partesList) {
                partesListPartes.setPrograma(null);
                partesListPartes = em.merge(partesListPartes);
            }
            em.remove(programas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Programas> findProgramasEntities() {
        return findProgramasEntities(true, -1, -1);
    }

    public List<Programas> findProgramasEntities(int maxResults, int firstResult) {
        return findProgramasEntities(false, maxResults, firstResult);
    }

    private List<Programas> findProgramasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Programas.class));
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

    public Programas findProgramas(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Programas.class, id);
        } finally {
            em.close();
        }
    }

    public int getProgramasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Programas> rt = cq.from(Programas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
