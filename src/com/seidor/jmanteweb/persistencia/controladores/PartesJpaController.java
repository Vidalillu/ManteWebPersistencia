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
import com.seidor.jmanteweb.persistencia.entidades.IncidenciaParte;
import com.seidor.jmanteweb.persistencia.entidades.Clientes;
import com.seidor.jmanteweb.persistencia.entidades.Guardiapalex;
import com.seidor.jmanteweb.persistencia.entidades.Partes;
import com.seidor.jmanteweb.persistencia.entidades.PrioridadParte;
import com.seidor.jmanteweb.persistencia.entidades.Programas;
import com.seidor.jmanteweb.persistencia.entidades.TiposParte;
import com.seidor.jmanteweb.persistencia.entidades.Usuario;
import com.seidor.jmanteweb.persistencia.entidades.Registroparte;
import java.util.ArrayList;
import java.util.List;
import com.seidor.jmanteweb.persistencia.entidades.Tarea;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

/**
 *
 * @author AMARTEL
 */
public class PartesJpaController implements Serializable {

    public PartesJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Partes partes) {
        if (partes.getRegistroparteList() == null) {
            partes.setRegistroparteList(new ArrayList<Registroparte>());
        }
        if (partes.getTareaList() == null) {
            partes.setTareaList(new ArrayList<Tarea>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            IncidenciaParte incidenciaParte = partes.getIncidenciaParte();
            if (incidenciaParte != null) {
                incidenciaParte = em.getReference(incidenciaParte.getClass(), incidenciaParte.getNumParte());
                partes.setIncidenciaParte(incidenciaParte);
            }
            Clientes cliente = partes.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getCliente());
                partes.setCliente(cliente);
            }
            Guardiapalex guardiapalex = partes.getGuardiapalex();
            if (guardiapalex != null) {
                guardiapalex = em.getReference(guardiapalex.getClass(), guardiapalex.getIdguardia());
                partes.setGuardiapalex(guardiapalex);
            }
            PrioridadParte importancia = partes.getImportancia();
            if (importancia != null) {
                importancia = em.getReference(importancia.getClass(), importancia.getPrioridad());
                partes.setImportancia(importancia);
            }
            Programas programa = partes.getPrograma();
            if (programa != null) {
                programa = em.getReference(programa.getClass(), programa.getPrograma());
                partes.setPrograma(programa);
            }
            TiposParte tipo = partes.getTipo();
            if (tipo != null) {
                tipo = em.getReference(tipo.getClass(), tipo.getCodigo());
                partes.setTipo(tipo);
            }
            Usuario usuario = partes.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getUsuario());
                partes.setUsuario(usuario);
            }
            List<Registroparte> attachedRegistroparteList = new ArrayList<Registroparte>();
            for (Registroparte registroparteListRegistroparteToAttach : partes.getRegistroparteList()) {
                registroparteListRegistroparteToAttach = em.getReference(registroparteListRegistroparteToAttach.getClass(), registroparteListRegistroparteToAttach.getId());
                attachedRegistroparteList.add(registroparteListRegistroparteToAttach);
            }
            partes.setRegistroparteList(attachedRegistroparteList);
            List<Tarea> attachedTareaList = new ArrayList<Tarea>();
            for (Tarea tareaListTareaToAttach : partes.getTareaList()) {
                tareaListTareaToAttach = em.getReference(tareaListTareaToAttach.getClass(), tareaListTareaToAttach.getId());
                attachedTareaList.add(tareaListTareaToAttach);
            }
            partes.setTareaList(attachedTareaList);
            em.persist(partes);
            if (incidenciaParte != null) {
                Partes oldPartesOfIncidenciaParte = incidenciaParte.getPartes();
                if (oldPartesOfIncidenciaParte != null) {
                    oldPartesOfIncidenciaParte.setIncidenciaParte(null);
                    oldPartesOfIncidenciaParte = em.merge(oldPartesOfIncidenciaParte);
                }
                incidenciaParte.setPartes(partes);
                incidenciaParte = em.merge(incidenciaParte);
            }
            if (cliente != null) {
                cliente.getPartesList().add(partes);
                cliente = em.merge(cliente);
            }
            if (guardiapalex != null) {
                guardiapalex.getPartesList().add(partes);
                guardiapalex = em.merge(guardiapalex);
            }
            if (importancia != null) {
                importancia.getPartesList().add(partes);
                importancia = em.merge(importancia);
            }
            if (programa != null) {
                programa.getPartesList().add(partes);
                programa = em.merge(programa);
            }
            if (tipo != null) {
                tipo.getPartesList().add(partes);
                tipo = em.merge(tipo);
            }
            if (usuario != null) {
                usuario.getPartesList().add(partes);
                usuario = em.merge(usuario);
            }
            for (Registroparte registroparteListRegistroparte : partes.getRegistroparteList()) {
                Partes oldNumParteOfRegistroparteListRegistroparte = registroparteListRegistroparte.getNumParte();
                registroparteListRegistroparte.setNumParte(partes);
                registroparteListRegistroparte = em.merge(registroparteListRegistroparte);
                if (oldNumParteOfRegistroparteListRegistroparte != null) {
                    oldNumParteOfRegistroparteListRegistroparte.getRegistroparteList().remove(registroparteListRegistroparte);
                    oldNumParteOfRegistroparteListRegistroparte = em.merge(oldNumParteOfRegistroparteListRegistroparte);
                }
            }
            for (Tarea tareaListTarea : partes.getTareaList()) {
                Partes oldIdparteOfTareaListTarea = tareaListTarea.getIdparte();
                tareaListTarea.setIdparte(partes);
                tareaListTarea = em.merge(tareaListTarea);
                if (oldIdparteOfTareaListTarea != null) {
                    oldIdparteOfTareaListTarea.getTareaList().remove(tareaListTarea);
                    oldIdparteOfTareaListTarea = em.merge(oldIdparteOfTareaListTarea);
                }
            }
            em.getTransaction().commit();
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Partes partes) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Partes persistentPartes = em.find(Partes.class, partes.getNumParte());
            IncidenciaParte incidenciaParteOld = persistentPartes.getIncidenciaParte();
            IncidenciaParte incidenciaParteNew = partes.getIncidenciaParte();
            Clientes clienteOld = persistentPartes.getCliente();
            Clientes clienteNew = partes.getCliente();
            Guardiapalex guardiapalexOld = persistentPartes.getGuardiapalex();
            Guardiapalex guardiapalexNew = partes.getGuardiapalex();
            PrioridadParte importanciaOld = persistentPartes.getImportancia();
            PrioridadParte importanciaNew = partes.getImportancia();
            Programas programaOld = persistentPartes.getPrograma();
            Programas programaNew = partes.getPrograma();
            TiposParte tipoOld = persistentPartes.getTipo();
            TiposParte tipoNew = partes.getTipo();
            Usuario usuarioOld = persistentPartes.getUsuario();
            Usuario usuarioNew = partes.getUsuario();
            List<Registroparte> registroparteListOld = persistentPartes.getRegistroparteList();
            List<Registroparte> registroparteListNew = partes.getRegistroparteList();
            List<Tarea> tareaListOld = persistentPartes.getTareaList();
            List<Tarea> tareaListNew = partes.getTareaList();
            List<String> illegalOrphanMessages = null;
            if (incidenciaParteOld != null && !incidenciaParteOld.equals(incidenciaParteNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain IncidenciaParte " + incidenciaParteOld + " since its partes field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (incidenciaParteNew != null) {
                incidenciaParteNew = em.getReference(incidenciaParteNew.getClass(), incidenciaParteNew.getNumParte());
                partes.setIncidenciaParte(incidenciaParteNew);
            }
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getCliente());
                partes.setCliente(clienteNew);
            }
            if (guardiapalexNew != null) {
                guardiapalexNew = em.getReference(guardiapalexNew.getClass(), guardiapalexNew.getIdguardia());
                partes.setGuardiapalex(guardiapalexNew);
            }
            if (importanciaNew != null) {
                importanciaNew = em.getReference(importanciaNew.getClass(), importanciaNew.getPrioridad());
                partes.setImportancia(importanciaNew);
            }
            if (programaNew != null) {
                programaNew = em.getReference(programaNew.getClass(), programaNew.getPrograma());
                partes.setPrograma(programaNew);
            }
            if (tipoNew != null) {
                tipoNew = em.getReference(tipoNew.getClass(), tipoNew.getCodigo());
                partes.setTipo(tipoNew);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getUsuario());
                partes.setUsuario(usuarioNew);
            }
            List<Registroparte> attachedRegistroparteListNew = new ArrayList<Registroparte>();
            for (Registroparte registroparteListNewRegistroparteToAttach : registroparteListNew) {
                registroparteListNewRegistroparteToAttach = em.getReference(registroparteListNewRegistroparteToAttach.getClass(), registroparteListNewRegistroparteToAttach.getId());
                attachedRegistroparteListNew.add(registroparteListNewRegistroparteToAttach);
            }
            registroparteListNew = attachedRegistroparteListNew;
            partes.setRegistroparteList(registroparteListNew);
            List<Tarea> attachedTareaListNew = new ArrayList<Tarea>();
            for (Tarea tareaListNewTareaToAttach : tareaListNew) {
                tareaListNewTareaToAttach = em.getReference(tareaListNewTareaToAttach.getClass(), tareaListNewTareaToAttach.getId());
                attachedTareaListNew.add(tareaListNewTareaToAttach);
            }
            tareaListNew = attachedTareaListNew;
            partes.setTareaList(tareaListNew);
            partes = em.merge(partes);
            if (incidenciaParteNew != null && !incidenciaParteNew.equals(incidenciaParteOld)) {
                Partes oldPartesOfIncidenciaParte = incidenciaParteNew.getPartes();
                if (oldPartesOfIncidenciaParte != null) {
                    oldPartesOfIncidenciaParte.setIncidenciaParte(null);
                    oldPartesOfIncidenciaParte = em.merge(oldPartesOfIncidenciaParte);
                }
                incidenciaParteNew.setPartes(partes);
                incidenciaParteNew = em.merge(incidenciaParteNew);
            }
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                clienteOld.getPartesList().remove(partes);
                clienteOld = em.merge(clienteOld);
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                clienteNew.getPartesList().add(partes);
                clienteNew = em.merge(clienteNew);
            }
            if (guardiapalexOld != null && !guardiapalexOld.equals(guardiapalexNew)) {
                guardiapalexOld.getPartesList().remove(partes);
                guardiapalexOld = em.merge(guardiapalexOld);
            }
            if (guardiapalexNew != null && !guardiapalexNew.equals(guardiapalexOld)) {
                guardiapalexNew.getPartesList().add(partes);
                guardiapalexNew = em.merge(guardiapalexNew);
            }
            if (importanciaOld != null && !importanciaOld.equals(importanciaNew)) {
                importanciaOld.getPartesList().remove(partes);
                importanciaOld = em.merge(importanciaOld);
            }
            if (importanciaNew != null && !importanciaNew.equals(importanciaOld)) {
                importanciaNew.getPartesList().add(partes);
                importanciaNew = em.merge(importanciaNew);
            }
            if (programaOld != null && !programaOld.equals(programaNew)) {
                programaOld.getPartesList().remove(partes);
                programaOld = em.merge(programaOld);
            }
            if (programaNew != null && !programaNew.equals(programaOld)) {
                programaNew.getPartesList().add(partes);
                programaNew = em.merge(programaNew);
            }
            if (tipoOld != null && !tipoOld.equals(tipoNew)) {
                tipoOld.getPartesList().remove(partes);
                tipoOld = em.merge(tipoOld);
            }
            if (tipoNew != null && !tipoNew.equals(tipoOld)) {
                tipoNew.getPartesList().add(partes);
                tipoNew = em.merge(tipoNew);
            }
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getPartesList().remove(partes);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getPartesList().add(partes);
                usuarioNew = em.merge(usuarioNew);
            }
            for (Registroparte registroparteListOldRegistroparte : registroparteListOld) {
                if (!registroparteListNew.contains(registroparteListOldRegistroparte)) {
                    registroparteListOldRegistroparte.setNumParte(null);
                    registroparteListOldRegistroparte = em.merge(registroparteListOldRegistroparte);
                }
            }
            for (Registroparte registroparteListNewRegistroparte : registroparteListNew) {
                if (!registroparteListOld.contains(registroparteListNewRegistroparte)) {
                    Partes oldNumParteOfRegistroparteListNewRegistroparte = registroparteListNewRegistroparte.getNumParte();
                    registroparteListNewRegistroparte.setNumParte(partes);
                    registroparteListNewRegistroparte = em.merge(registroparteListNewRegistroparte);
                    if (oldNumParteOfRegistroparteListNewRegistroparte != null && !oldNumParteOfRegistroparteListNewRegistroparte.equals(partes)) {
                        oldNumParteOfRegistroparteListNewRegistroparte.getRegistroparteList().remove(registroparteListNewRegistroparte);
                        oldNumParteOfRegistroparteListNewRegistroparte = em.merge(oldNumParteOfRegistroparteListNewRegistroparte);
                    }
                }
            }
            for (Tarea tareaListOldTarea : tareaListOld) {
                if (!tareaListNew.contains(tareaListOldTarea)) {
                    tareaListOldTarea.setIdparte(null);
                    tareaListOldTarea = em.merge(tareaListOldTarea);
                }
            }
            for (Tarea tareaListNewTarea : tareaListNew) {
                if (!tareaListOld.contains(tareaListNewTarea)) {
                    Partes oldIdparteOfTareaListNewTarea = tareaListNewTarea.getIdparte();
                    tareaListNewTarea.setIdparte(partes);
                    tareaListNewTarea = em.merge(tareaListNewTarea);
                    if (oldIdparteOfTareaListNewTarea != null && !oldIdparteOfTareaListNewTarea.equals(partes)) {
                        oldIdparteOfTareaListNewTarea.getTareaList().remove(tareaListNewTarea);
                        oldIdparteOfTareaListNewTarea = em.merge(oldIdparteOfTareaListNewTarea);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = partes.getNumParte();
                if (findPartes(id) == null) {
                    throw new NonexistentEntityException("The partes with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Partes partes;
            try {
                partes = em.getReference(Partes.class, id);
                partes.getNumParte();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The partes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            IncidenciaParte incidenciaParteOrphanCheck = partes.getIncidenciaParte();
            if (incidenciaParteOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Partes (" + partes + ") cannot be destroyed since the IncidenciaParte " + incidenciaParteOrphanCheck + " in its incidenciaParte field has a non-nullable partes field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Clientes cliente = partes.getCliente();
            if (cliente != null) {
                cliente.getPartesList().remove(partes);
                cliente = em.merge(cliente);
            }
            Guardiapalex guardiapalex = partes.getGuardiapalex();
            if (guardiapalex != null) {
                guardiapalex.getPartesList().remove(partes);
                guardiapalex = em.merge(guardiapalex);
            }
            PrioridadParte importancia = partes.getImportancia();
            if (importancia != null) {
                importancia.getPartesList().remove(partes);
                importancia = em.merge(importancia);
            }
            Programas programa = partes.getPrograma();
            if (programa != null) {
                programa.getPartesList().remove(partes);
                programa = em.merge(programa);
            }
            TiposParte tipo = partes.getTipo();
            if (tipo != null) {
                tipo.getPartesList().remove(partes);
                tipo = em.merge(tipo);
            }
            Usuario usuario = partes.getUsuario();
            if (usuario != null) {
                usuario.getPartesList().remove(partes);
                usuario = em.merge(usuario);
            }
            List<Registroparte> registroparteList = partes.getRegistroparteList();
            for (Registroparte registroparteListRegistroparte : registroparteList) {
                registroparteListRegistroparte.setNumParte(null);
                registroparteListRegistroparte = em.merge(registroparteListRegistroparte);
            }
            List<Tarea> tareaList = partes.getTareaList();
            for (Tarea tareaListTarea : tareaList) {
                tareaListTarea.setIdparte(null);
                tareaListTarea = em.merge(tareaListTarea);
            }
            em.remove(partes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Partes> findPartesEntities() {
        return findPartesEntities(true, -1, -1);
    }

    public List<Partes> findPartesEntities(int maxResults, int firstResult) {
        return findPartesEntities(false, maxResults, firstResult);
    }

    private List<Partes> findPartesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Partes.class));
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

    public Partes findPartes(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Partes.class, id);
        } finally {
            em.close();
        }
    }

    public int getPartesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Partes> rt = cq.from(Partes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Partes> listadoPartesCliente( String cliente, BigInteger abierto, String usuario ) throws PreexistingEntityException{
        
        EntityManager em = getEntityManager();
        try {
            
            
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery();
            Root parte = cq.from(Partes.class);
            Join joinCliente = parte.join("cliente");
            
            Predicate prCliente = cb.equal( joinCliente.get("cliente"), cliente);
            
            Predicate prUsuario = null;
            if ( usuario != null ){
                Join joinUsuario = parte.join("usuario");
                prUsuario = cb.equal( joinUsuario.get("usuario"), usuario );
            }
            
            Predicate prAbierto = null;
            if ( abierto != null ){
               prAbierto = cb.equal( parte.get("abierto"), abierto );
            }
            
            Predicate pAnd = null;
            if ( prUsuario != null && prAbierto != null ){
                pAnd = cb.and(prUsuario, prAbierto);
                cq.where( prCliente, pAnd );
            }
            else if ( prUsuario != null && prAbierto == null ){
                pAnd = cb.and( prUsuario );
                cq.where( prCliente, pAnd );
            }
            else if ( prAbierto != null && prUsuario == null ){
                pAnd = cb.and( prAbierto );
                cq.where( prCliente, pAnd );
            }
            else{
                cq.where( prCliente );
            }
            
            Query q = em.createQuery(cq);
            
            return q.getResultList();
            
        } 
        catch (Exception ex) {
            throw new PreexistingEntityException("Error al obtener el listado de partes para el cliente: " + cliente, ex);
        }
        finally {
            em.close();
        }
        
    }
    
}
