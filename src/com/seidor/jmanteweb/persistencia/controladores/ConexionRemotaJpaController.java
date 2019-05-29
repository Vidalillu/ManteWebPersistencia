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
import com.seidor.jmanteweb.persistencia.entidades.Clientes;
import com.seidor.jmanteweb.persistencia.entidades.ConexionPc;
import java.util.ArrayList;
import java.util.List;
import com.seidor.jmanteweb.persistencia.entidades.Vpn;
import com.seidor.jmanteweb.persistencia.entidades.ConexionPcsSeidor;
import com.seidor.jmanteweb.persistencia.entidades.ConexionRemota;
import com.seidor.jmanteweb.persistencia.entidades.Modem;
import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class ConexionRemotaJpaController implements Serializable {

    public ConexionRemotaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConexionRemota conexionRemota) throws PreexistingEntityException, Exception {
        if (conexionRemota.getConexionPcList() == null) {
            conexionRemota.setConexionPcList(new ArrayList<ConexionPc>());
        }
        if (conexionRemota.getVpnList() == null) {
            conexionRemota.setVpnList(new ArrayList<Vpn>());
        }
        if (conexionRemota.getConexionPcsSeidorList() == null) {
            conexionRemota.setConexionPcsSeidorList(new ArrayList<ConexionPcsSeidor>());
        }
        if (conexionRemota.getModemList() == null) {
            conexionRemota.setModemList(new ArrayList<Modem>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes cliente = conexionRemota.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getCliente());
                conexionRemota.setCliente(cliente);
            }
            List<ConexionPc> attachedConexionPcList = new ArrayList<ConexionPc>();
            for (ConexionPc conexionPcListConexionPcToAttach : conexionRemota.getConexionPcList()) {
                conexionPcListConexionPcToAttach = em.getReference(conexionPcListConexionPcToAttach.getClass(), conexionPcListConexionPcToAttach.getId());
                attachedConexionPcList.add(conexionPcListConexionPcToAttach);
            }
            conexionRemota.setConexionPcList(attachedConexionPcList);
            List<Vpn> attachedVpnList = new ArrayList<Vpn>();
            for (Vpn vpnListVpnToAttach : conexionRemota.getVpnList()) {
                vpnListVpnToAttach = em.getReference(vpnListVpnToAttach.getClass(), vpnListVpnToAttach.getId());
                attachedVpnList.add(vpnListVpnToAttach);
            }
            conexionRemota.setVpnList(attachedVpnList);
            List<ConexionPcsSeidor> attachedConexionPcsSeidorList = new ArrayList<ConexionPcsSeidor>();
            for (ConexionPcsSeidor conexionPcsSeidorListConexionPcsSeidorToAttach : conexionRemota.getConexionPcsSeidorList()) {
                conexionPcsSeidorListConexionPcsSeidorToAttach = em.getReference(conexionPcsSeidorListConexionPcsSeidorToAttach.getClass(), conexionPcsSeidorListConexionPcsSeidorToAttach.getId());
                attachedConexionPcsSeidorList.add(conexionPcsSeidorListConexionPcsSeidorToAttach);
            }
            conexionRemota.setConexionPcsSeidorList(attachedConexionPcsSeidorList);
            List<Modem> attachedModemList = new ArrayList<Modem>();
            for (Modem modemListModemToAttach : conexionRemota.getModemList()) {
                modemListModemToAttach = em.getReference(modemListModemToAttach.getClass(), modemListModemToAttach.getId());
                attachedModemList.add(modemListModemToAttach);
            }
            conexionRemota.setModemList(attachedModemList);
            em.persist(conexionRemota);
            if (cliente != null) {
                ConexionRemota oldConexionRemotaOfCliente = cliente.getConexionRemota();
                if (oldConexionRemotaOfCliente != null) {
                    oldConexionRemotaOfCliente.setCliente(null);
                    oldConexionRemotaOfCliente = em.merge(oldConexionRemotaOfCliente);
                }
                cliente.setConexionRemota(conexionRemota);
                cliente = em.merge(cliente);
            }
            for (ConexionPc conexionPcListConexionPc : conexionRemota.getConexionPcList()) {
                ConexionRemota oldIdConexRemotaOfConexionPcListConexionPc = conexionPcListConexionPc.getIdConexRemota();
                conexionPcListConexionPc.setIdConexRemota(conexionRemota);
                conexionPcListConexionPc = em.merge(conexionPcListConexionPc);
                if (oldIdConexRemotaOfConexionPcListConexionPc != null) {
                    oldIdConexRemotaOfConexionPcListConexionPc.getConexionPcList().remove(conexionPcListConexionPc);
                    oldIdConexRemotaOfConexionPcListConexionPc = em.merge(oldIdConexRemotaOfConexionPcListConexionPc);
                }
            }
            for (Vpn vpnListVpn : conexionRemota.getVpnList()) {
                ConexionRemota oldIdConexRemotaOfVpnListVpn = vpnListVpn.getIdConexRemota();
                vpnListVpn.setIdConexRemota(conexionRemota);
                vpnListVpn = em.merge(vpnListVpn);
                if (oldIdConexRemotaOfVpnListVpn != null) {
                    oldIdConexRemotaOfVpnListVpn.getVpnList().remove(vpnListVpn);
                    oldIdConexRemotaOfVpnListVpn = em.merge(oldIdConexRemotaOfVpnListVpn);
                }
            }
            for (ConexionPcsSeidor conexionPcsSeidorListConexionPcsSeidor : conexionRemota.getConexionPcsSeidorList()) {
                ConexionRemota oldIdConexionOfConexionPcsSeidorListConexionPcsSeidor = conexionPcsSeidorListConexionPcsSeidor.getIdConexion();
                conexionPcsSeidorListConexionPcsSeidor.setIdConexion(conexionRemota);
                conexionPcsSeidorListConexionPcsSeidor = em.merge(conexionPcsSeidorListConexionPcsSeidor);
                if (oldIdConexionOfConexionPcsSeidorListConexionPcsSeidor != null) {
                    oldIdConexionOfConexionPcsSeidorListConexionPcsSeidor.getConexionPcsSeidorList().remove(conexionPcsSeidorListConexionPcsSeidor);
                    oldIdConexionOfConexionPcsSeidorListConexionPcsSeidor = em.merge(oldIdConexionOfConexionPcsSeidorListConexionPcsSeidor);
                }
            }
            for (Modem modemListModem : conexionRemota.getModemList()) {
                ConexionRemota oldIdConexRemotaOfModemListModem = modemListModem.getIdConexRemota();
                modemListModem.setIdConexRemota(conexionRemota);
                modemListModem = em.merge(modemListModem);
                if (oldIdConexRemotaOfModemListModem != null) {
                    oldIdConexRemotaOfModemListModem.getModemList().remove(modemListModem);
                    oldIdConexRemotaOfModemListModem = em.merge(oldIdConexRemotaOfModemListModem);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findConexionRemota(conexionRemota.getId()) != null) {
                throw new PreexistingEntityException("ConexionRemota " + conexionRemota + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ConexionRemota conexionRemota) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ConexionRemota persistentConexionRemota = em.find(ConexionRemota.class, conexionRemota.getId());
            Clientes clienteOld = persistentConexionRemota.getCliente();
            Clientes clienteNew = conexionRemota.getCliente();
            List<ConexionPc> conexionPcListOld = persistentConexionRemota.getConexionPcList();
            List<ConexionPc> conexionPcListNew = conexionRemota.getConexionPcList();
            List<Vpn> vpnListOld = persistentConexionRemota.getVpnList();
            List<Vpn> vpnListNew = conexionRemota.getVpnList();
            List<ConexionPcsSeidor> conexionPcsSeidorListOld = persistentConexionRemota.getConexionPcsSeidorList();
            List<ConexionPcsSeidor> conexionPcsSeidorListNew = conexionRemota.getConexionPcsSeidorList();
            List<Modem> modemListOld = persistentConexionRemota.getModemList();
            List<Modem> modemListNew = conexionRemota.getModemList();
            List<String> illegalOrphanMessages = null;
            for (ConexionPc conexionPcListOldConexionPc : conexionPcListOld) {
                if (!conexionPcListNew.contains(conexionPcListOldConexionPc)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ConexionPc " + conexionPcListOldConexionPc + " since its idConexRemota field is not nullable.");
                }
            }
            for (Vpn vpnListOldVpn : vpnListOld) {
                if (!vpnListNew.contains(vpnListOldVpn)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Vpn " + vpnListOldVpn + " since its idConexRemota field is not nullable.");
                }
            }
            for (ConexionPcsSeidor conexionPcsSeidorListOldConexionPcsSeidor : conexionPcsSeidorListOld) {
                if (!conexionPcsSeidorListNew.contains(conexionPcsSeidorListOldConexionPcsSeidor)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ConexionPcsSeidor " + conexionPcsSeidorListOldConexionPcsSeidor + " since its idConexion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getCliente());
                conexionRemota.setCliente(clienteNew);
            }
            List<ConexionPc> attachedConexionPcListNew = new ArrayList<ConexionPc>();
            for (ConexionPc conexionPcListNewConexionPcToAttach : conexionPcListNew) {
                conexionPcListNewConexionPcToAttach = em.getReference(conexionPcListNewConexionPcToAttach.getClass(), conexionPcListNewConexionPcToAttach.getId());
                attachedConexionPcListNew.add(conexionPcListNewConexionPcToAttach);
            }
            conexionPcListNew = attachedConexionPcListNew;
            conexionRemota.setConexionPcList(conexionPcListNew);
            List<Vpn> attachedVpnListNew = new ArrayList<Vpn>();
            for (Vpn vpnListNewVpnToAttach : vpnListNew) {
                vpnListNewVpnToAttach = em.getReference(vpnListNewVpnToAttach.getClass(), vpnListNewVpnToAttach.getId());
                attachedVpnListNew.add(vpnListNewVpnToAttach);
            }
            vpnListNew = attachedVpnListNew;
            conexionRemota.setVpnList(vpnListNew);
            List<ConexionPcsSeidor> attachedConexionPcsSeidorListNew = new ArrayList<ConexionPcsSeidor>();
            for (ConexionPcsSeidor conexionPcsSeidorListNewConexionPcsSeidorToAttach : conexionPcsSeidorListNew) {
                conexionPcsSeidorListNewConexionPcsSeidorToAttach = em.getReference(conexionPcsSeidorListNewConexionPcsSeidorToAttach.getClass(), conexionPcsSeidorListNewConexionPcsSeidorToAttach.getId());
                attachedConexionPcsSeidorListNew.add(conexionPcsSeidorListNewConexionPcsSeidorToAttach);
            }
            conexionPcsSeidorListNew = attachedConexionPcsSeidorListNew;
            conexionRemota.setConexionPcsSeidorList(conexionPcsSeidorListNew);
            List<Modem> attachedModemListNew = new ArrayList<Modem>();
            for (Modem modemListNewModemToAttach : modemListNew) {
                modemListNewModemToAttach = em.getReference(modemListNewModemToAttach.getClass(), modemListNewModemToAttach.getId());
                attachedModemListNew.add(modemListNewModemToAttach);
            }
            modemListNew = attachedModemListNew;
            conexionRemota.setModemList(modemListNew);
            conexionRemota = em.merge(conexionRemota);
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                clienteOld.setConexionRemota(null);
                clienteOld = em.merge(clienteOld);
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                ConexionRemota oldConexionRemotaOfCliente = clienteNew.getConexionRemota();
                if (oldConexionRemotaOfCliente != null) {
                    oldConexionRemotaOfCliente.setCliente(null);
                    oldConexionRemotaOfCliente = em.merge(oldConexionRemotaOfCliente);
                }
                clienteNew.setConexionRemota(conexionRemota);
                clienteNew = em.merge(clienteNew);
            }
            for (ConexionPc conexionPcListNewConexionPc : conexionPcListNew) {
                if (!conexionPcListOld.contains(conexionPcListNewConexionPc)) {
                    ConexionRemota oldIdConexRemotaOfConexionPcListNewConexionPc = conexionPcListNewConexionPc.getIdConexRemota();
                    conexionPcListNewConexionPc.setIdConexRemota(conexionRemota);
                    conexionPcListNewConexionPc = em.merge(conexionPcListNewConexionPc);
                    if (oldIdConexRemotaOfConexionPcListNewConexionPc != null && !oldIdConexRemotaOfConexionPcListNewConexionPc.equals(conexionRemota)) {
                        oldIdConexRemotaOfConexionPcListNewConexionPc.getConexionPcList().remove(conexionPcListNewConexionPc);
                        oldIdConexRemotaOfConexionPcListNewConexionPc = em.merge(oldIdConexRemotaOfConexionPcListNewConexionPc);
                    }
                }
            }
            for (Vpn vpnListNewVpn : vpnListNew) {
                if (!vpnListOld.contains(vpnListNewVpn)) {
                    ConexionRemota oldIdConexRemotaOfVpnListNewVpn = vpnListNewVpn.getIdConexRemota();
                    vpnListNewVpn.setIdConexRemota(conexionRemota);
                    vpnListNewVpn = em.merge(vpnListNewVpn);
                    if (oldIdConexRemotaOfVpnListNewVpn != null && !oldIdConexRemotaOfVpnListNewVpn.equals(conexionRemota)) {
                        oldIdConexRemotaOfVpnListNewVpn.getVpnList().remove(vpnListNewVpn);
                        oldIdConexRemotaOfVpnListNewVpn = em.merge(oldIdConexRemotaOfVpnListNewVpn);
                    }
                }
            }
            for (ConexionPcsSeidor conexionPcsSeidorListNewConexionPcsSeidor : conexionPcsSeidorListNew) {
                if (!conexionPcsSeidorListOld.contains(conexionPcsSeidorListNewConexionPcsSeidor)) {
                    ConexionRemota oldIdConexionOfConexionPcsSeidorListNewConexionPcsSeidor = conexionPcsSeidorListNewConexionPcsSeidor.getIdConexion();
                    conexionPcsSeidorListNewConexionPcsSeidor.setIdConexion(conexionRemota);
                    conexionPcsSeidorListNewConexionPcsSeidor = em.merge(conexionPcsSeidorListNewConexionPcsSeidor);
                    if (oldIdConexionOfConexionPcsSeidorListNewConexionPcsSeidor != null && !oldIdConexionOfConexionPcsSeidorListNewConexionPcsSeidor.equals(conexionRemota)) {
                        oldIdConexionOfConexionPcsSeidorListNewConexionPcsSeidor.getConexionPcsSeidorList().remove(conexionPcsSeidorListNewConexionPcsSeidor);
                        oldIdConexionOfConexionPcsSeidorListNewConexionPcsSeidor = em.merge(oldIdConexionOfConexionPcsSeidorListNewConexionPcsSeidor);
                    }
                }
            }
            for (Modem modemListOldModem : modemListOld) {
                if (!modemListNew.contains(modemListOldModem)) {
                    modemListOldModem.setIdConexRemota(null);
                    modemListOldModem = em.merge(modemListOldModem);
                }
            }
            for (Modem modemListNewModem : modemListNew) {
                if (!modemListOld.contains(modemListNewModem)) {
                    ConexionRemota oldIdConexRemotaOfModemListNewModem = modemListNewModem.getIdConexRemota();
                    modemListNewModem.setIdConexRemota(conexionRemota);
                    modemListNewModem = em.merge(modemListNewModem);
                    if (oldIdConexRemotaOfModemListNewModem != null && !oldIdConexRemotaOfModemListNewModem.equals(conexionRemota)) {
                        oldIdConexRemotaOfModemListNewModem.getModemList().remove(modemListNewModem);
                        oldIdConexRemotaOfModemListNewModem = em.merge(oldIdConexRemotaOfModemListNewModem);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = conexionRemota.getId();
                if (findConexionRemota(id) == null) {
                    throw new NonexistentEntityException("The conexionRemota with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ConexionRemota conexionRemota;
            try {
                conexionRemota = em.getReference(ConexionRemota.class, id);
                conexionRemota.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The conexionRemota with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ConexionPc> conexionPcListOrphanCheck = conexionRemota.getConexionPcList();
            for (ConexionPc conexionPcListOrphanCheckConexionPc : conexionPcListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ConexionRemota (" + conexionRemota + ") cannot be destroyed since the ConexionPc " + conexionPcListOrphanCheckConexionPc + " in its conexionPcList field has a non-nullable idConexRemota field.");
            }
            List<Vpn> vpnListOrphanCheck = conexionRemota.getVpnList();
            for (Vpn vpnListOrphanCheckVpn : vpnListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ConexionRemota (" + conexionRemota + ") cannot be destroyed since the Vpn " + vpnListOrphanCheckVpn + " in its vpnList field has a non-nullable idConexRemota field.");
            }
            List<ConexionPcsSeidor> conexionPcsSeidorListOrphanCheck = conexionRemota.getConexionPcsSeidorList();
            for (ConexionPcsSeidor conexionPcsSeidorListOrphanCheckConexionPcsSeidor : conexionPcsSeidorListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ConexionRemota (" + conexionRemota + ") cannot be destroyed since the ConexionPcsSeidor " + conexionPcsSeidorListOrphanCheckConexionPcsSeidor + " in its conexionPcsSeidorList field has a non-nullable idConexion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Clientes cliente = conexionRemota.getCliente();
            if (cliente != null) {
                cliente.setConexionRemota(null);
                cliente = em.merge(cliente);
            }
            List<Modem> modemList = conexionRemota.getModemList();
            for (Modem modemListModem : modemList) {
                modemListModem.setIdConexRemota(null);
                modemListModem = em.merge(modemListModem);
            }
            em.remove(conexionRemota);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ConexionRemota> findConexionRemotaEntities() {
        return findConexionRemotaEntities(true, -1, -1);
    }

    public List<ConexionRemota> findConexionRemotaEntities(int maxResults, int firstResult) {
        return findConexionRemotaEntities(false, maxResults, firstResult);
    }

    private List<ConexionRemota> findConexionRemotaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConexionRemota.class));
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

    public ConexionRemota findConexionRemota(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConexionRemota.class, id);
        } finally {
            em.close();
        }
    }

    public int getConexionRemotaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConexionRemota> rt = cq.from(ConexionRemota.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
