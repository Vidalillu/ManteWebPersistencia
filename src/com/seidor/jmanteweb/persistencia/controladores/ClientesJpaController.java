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
import com.seidor.jmanteweb.persistencia.entidades.ConexionRemota;
import com.seidor.jmanteweb.persistencia.entidades.InfoBd;
import com.seidor.jmanteweb.persistencia.entidades.Bonomodis;
import com.seidor.jmanteweb.persistencia.entidades.Clientes;
import com.seidor.jmanteweb.persistencia.entidades.EmpresaColaboradora;
import com.seidor.jmanteweb.persistencia.entidades.EstadosCliente;
import com.seidor.jmanteweb.persistencia.entidades.ServiciosMante;
import com.seidor.jmanteweb.persistencia.entidades.Tarifa;
import com.seidor.jmanteweb.persistencia.entidades.Responsabilidad;
import java.util.ArrayList;
import java.util.List;
import com.seidor.jmanteweb.persistencia.entidades.Peticiones;
import com.seidor.jmanteweb.persistencia.entidades.InfoPrograma;
import com.seidor.jmanteweb.persistencia.entidades.InfoModi;
import com.seidor.jmanteweb.persistencia.entidades.Partes;
import com.seidor.jmanteweb.persistencia.entidades.Tarea;
import com.seidor.jmanteweb.persistencia.entidades.RegistroRevision;
import com.seidor.jmanteweb.persistencia.entidades.Usuario;
import com.seidor.jmanteweb.persistencia.entidades.Contactos;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class ClientesJpaController implements Serializable {

    public ClientesJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Clientes clientes) throws PreexistingEntityException, Exception {
        if (clientes.getResponsabilidadList() == null) {
            clientes.setResponsabilidadList(new ArrayList<Responsabilidad>());
        }
        if (clientes.getPeticionesList() == null) {
            clientes.setPeticionesList(new ArrayList<Peticiones>());
        }
        if (clientes.getInfoProgramaList() == null) {
            clientes.setInfoProgramaList(new ArrayList<InfoPrograma>());
        }
        if (clientes.getInfoModiList() == null) {
            clientes.setInfoModiList(new ArrayList<InfoModi>());
        }
        if (clientes.getPartesList() == null) {
            clientes.setPartesList(new ArrayList<Partes>());
        }
        if (clientes.getTareaList() == null) {
            clientes.setTareaList(new ArrayList<Tarea>());
        }
        if (clientes.getRegistroRevisionList() == null) {
            clientes.setRegistroRevisionList(new ArrayList<RegistroRevision>());
        }
        if (clientes.getUsuarioList() == null) {
            clientes.setUsuarioList(new ArrayList<Usuario>());
        }
        if (clientes.getContactosList() == null) {
            clientes.setContactosList(new ArrayList<Contactos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ConexionRemota conexionRemota = clientes.getConexionRemota();
            if (conexionRemota != null) {
                conexionRemota = em.getReference(conexionRemota.getClass(), conexionRemota.getId());
                clientes.setConexionRemota(conexionRemota);
            }
            InfoBd infoBd = clientes.getInfoBd();
            if (infoBd != null) {
                infoBd = em.getReference(infoBd.getClass(), infoBd.getId());
                clientes.setInfoBd(infoBd);
            }
            Bonomodis idBonomodisActivo = clientes.getIdBonomodisActivo();
            if (idBonomodisActivo != null) {
                idBonomodisActivo = em.getReference(idBonomodisActivo.getClass(), idBonomodisActivo.getIdPeticion());
                clientes.setIdBonomodisActivo(idBonomodisActivo);
            }
            EmpresaColaboradora tipoCliente = clientes.getTipoCliente();
            if (tipoCliente != null) {
                tipoCliente = em.getReference(tipoCliente.getClass(), tipoCliente.getNombre());
                clientes.setTipoCliente(tipoCliente);
            }
            EstadosCliente estado = clientes.getEstado();
            if (estado != null) {
                estado = em.getReference(estado.getClass(), estado.getEstado());
                clientes.setEstado(estado);
            }
            ServiciosMante idServiciomanteActivo = clientes.getIdServiciomanteActivo();
            if (idServiciomanteActivo != null) {
                idServiciomanteActivo = em.getReference(idServiciomanteActivo.getClass(), idServiciomanteActivo.getIdPeticion());
                clientes.setIdServiciomanteActivo(idServiciomanteActivo);
            }
            Tarifa tarifa = clientes.getTarifa();
            if (tarifa != null) {
                tarifa = em.getReference(tarifa.getClass(), tarifa.getTarifa());
                clientes.setTarifa(tarifa);
            }
            List<Responsabilidad> attachedResponsabilidadList = new ArrayList<Responsabilidad>();
            for (Responsabilidad responsabilidadListResponsabilidadToAttach : clientes.getResponsabilidadList()) {
                responsabilidadListResponsabilidadToAttach = em.getReference(responsabilidadListResponsabilidadToAttach.getClass(), responsabilidadListResponsabilidadToAttach.getResponsabilidadPK());
                attachedResponsabilidadList.add(responsabilidadListResponsabilidadToAttach);
            }
            clientes.setResponsabilidadList(attachedResponsabilidadList);
            List<Peticiones> attachedPeticionesList = new ArrayList<Peticiones>();
            for (Peticiones peticionesListPeticionesToAttach : clientes.getPeticionesList()) {
                peticionesListPeticionesToAttach = em.getReference(peticionesListPeticionesToAttach.getClass(), peticionesListPeticionesToAttach.getIdpeticion());
                attachedPeticionesList.add(peticionesListPeticionesToAttach);
            }
            clientes.setPeticionesList(attachedPeticionesList);
            List<InfoPrograma> attachedInfoProgramaList = new ArrayList<InfoPrograma>();
            for (InfoPrograma infoProgramaListInfoProgramaToAttach : clientes.getInfoProgramaList()) {
                infoProgramaListInfoProgramaToAttach = em.getReference(infoProgramaListInfoProgramaToAttach.getClass(), infoProgramaListInfoProgramaToAttach.getInfoProgramaPK());
                attachedInfoProgramaList.add(infoProgramaListInfoProgramaToAttach);
            }
            clientes.setInfoProgramaList(attachedInfoProgramaList);
            List<InfoModi> attachedInfoModiList = new ArrayList<InfoModi>();
            for (InfoModi infoModiListInfoModiToAttach : clientes.getInfoModiList()) {
                infoModiListInfoModiToAttach = em.getReference(infoModiListInfoModiToAttach.getClass(), infoModiListInfoModiToAttach.getInfoModiPK());
                attachedInfoModiList.add(infoModiListInfoModiToAttach);
            }
            clientes.setInfoModiList(attachedInfoModiList);
            List<Partes> attachedPartesList = new ArrayList<Partes>();
            for (Partes partesListPartesToAttach : clientes.getPartesList()) {
                partesListPartesToAttach = em.getReference(partesListPartesToAttach.getClass(), partesListPartesToAttach.getNumParte());
                attachedPartesList.add(partesListPartesToAttach);
            }
            clientes.setPartesList(attachedPartesList);
            List<Tarea> attachedTareaList = new ArrayList<Tarea>();
            for (Tarea tareaListTareaToAttach : clientes.getTareaList()) {
                tareaListTareaToAttach = em.getReference(tareaListTareaToAttach.getClass(), tareaListTareaToAttach.getId());
                attachedTareaList.add(tareaListTareaToAttach);
            }
            clientes.setTareaList(attachedTareaList);
            List<RegistroRevision> attachedRegistroRevisionList = new ArrayList<RegistroRevision>();
            for (RegistroRevision registroRevisionListRegistroRevisionToAttach : clientes.getRegistroRevisionList()) {
                registroRevisionListRegistroRevisionToAttach = em.getReference(registroRevisionListRegistroRevisionToAttach.getClass(), registroRevisionListRegistroRevisionToAttach.getId());
                attachedRegistroRevisionList.add(registroRevisionListRegistroRevisionToAttach);
            }
            clientes.setRegistroRevisionList(attachedRegistroRevisionList);
            List<Usuario> attachedUsuarioList = new ArrayList<Usuario>();
            for (Usuario usuarioListUsuarioToAttach : clientes.getUsuarioList()) {
                usuarioListUsuarioToAttach = em.getReference(usuarioListUsuarioToAttach.getClass(), usuarioListUsuarioToAttach.getUsuario());
                attachedUsuarioList.add(usuarioListUsuarioToAttach);
            }
            clientes.setUsuarioList(attachedUsuarioList);
            List<Contactos> attachedContactosList = new ArrayList<Contactos>();
            for (Contactos contactosListContactosToAttach : clientes.getContactosList()) {
                contactosListContactosToAttach = em.getReference(contactosListContactosToAttach.getClass(), contactosListContactosToAttach.getId());
                attachedContactosList.add(contactosListContactosToAttach);
            }
            clientes.setContactosList(attachedContactosList);
            em.persist(clientes);
            if (conexionRemota != null) {
                Clientes oldClienteOfConexionRemota = conexionRemota.getCliente();
                if (oldClienteOfConexionRemota != null) {
                    oldClienteOfConexionRemota.setConexionRemota(null);
                    oldClienteOfConexionRemota = em.merge(oldClienteOfConexionRemota);
                }
                conexionRemota.setCliente(clientes);
                conexionRemota = em.merge(conexionRemota);
            }
            if (infoBd != null) {
                Clientes oldClienteOfInfoBd = infoBd.getCliente();
                if (oldClienteOfInfoBd != null) {
                    oldClienteOfInfoBd.setInfoBd(null);
                    oldClienteOfInfoBd = em.merge(oldClienteOfInfoBd);
                }
                infoBd.setCliente(clientes);
                infoBd = em.merge(infoBd);
            }
            if (idBonomodisActivo != null) {
                idBonomodisActivo.getClientesList().add(clientes);
                idBonomodisActivo = em.merge(idBonomodisActivo);
            }
            if (tipoCliente != null) {
                tipoCliente.getClientesList().add(clientes);
                tipoCliente = em.merge(tipoCliente);
            }
            if (estado != null) {
                estado.getClientesList().add(clientes);
                estado = em.merge(estado);
            }
            if (idServiciomanteActivo != null) {
                idServiciomanteActivo.getClientesList().add(clientes);
                idServiciomanteActivo = em.merge(idServiciomanteActivo);
            }
            if (tarifa != null) {
                tarifa.getClientesList().add(clientes);
                tarifa = em.merge(tarifa);
            }
            for (Responsabilidad responsabilidadListResponsabilidad : clientes.getResponsabilidadList()) {
                Clientes oldClientesOfResponsabilidadListResponsabilidad = responsabilidadListResponsabilidad.getClientes();
                responsabilidadListResponsabilidad.setClientes(clientes);
                responsabilidadListResponsabilidad = em.merge(responsabilidadListResponsabilidad);
                if (oldClientesOfResponsabilidadListResponsabilidad != null) {
                    oldClientesOfResponsabilidadListResponsabilidad.getResponsabilidadList().remove(responsabilidadListResponsabilidad);
                    oldClientesOfResponsabilidadListResponsabilidad = em.merge(oldClientesOfResponsabilidadListResponsabilidad);
                }
            }
            for (Peticiones peticionesListPeticiones : clientes.getPeticionesList()) {
                Clientes oldClienteOfPeticionesListPeticiones = peticionesListPeticiones.getCliente();
                peticionesListPeticiones.setCliente(clientes);
                peticionesListPeticiones = em.merge(peticionesListPeticiones);
                if (oldClienteOfPeticionesListPeticiones != null) {
                    oldClienteOfPeticionesListPeticiones.getPeticionesList().remove(peticionesListPeticiones);
                    oldClienteOfPeticionesListPeticiones = em.merge(oldClienteOfPeticionesListPeticiones);
                }
            }
            for (InfoPrograma infoProgramaListInfoPrograma : clientes.getInfoProgramaList()) {
                Clientes oldClientesOfInfoProgramaListInfoPrograma = infoProgramaListInfoPrograma.getClientes();
                infoProgramaListInfoPrograma.setClientes(clientes);
                infoProgramaListInfoPrograma = em.merge(infoProgramaListInfoPrograma);
                if (oldClientesOfInfoProgramaListInfoPrograma != null) {
                    oldClientesOfInfoProgramaListInfoPrograma.getInfoProgramaList().remove(infoProgramaListInfoPrograma);
                    oldClientesOfInfoProgramaListInfoPrograma = em.merge(oldClientesOfInfoProgramaListInfoPrograma);
                }
            }
            for (InfoModi infoModiListInfoModi : clientes.getInfoModiList()) {
                Clientes oldClientesOfInfoModiListInfoModi = infoModiListInfoModi.getClientes();
                infoModiListInfoModi.setClientes(clientes);
                infoModiListInfoModi = em.merge(infoModiListInfoModi);
                if (oldClientesOfInfoModiListInfoModi != null) {
                    oldClientesOfInfoModiListInfoModi.getInfoModiList().remove(infoModiListInfoModi);
                    oldClientesOfInfoModiListInfoModi = em.merge(oldClientesOfInfoModiListInfoModi);
                }
            }
            for (Partes partesListPartes : clientes.getPartesList()) {
                Clientes oldClienteOfPartesListPartes = partesListPartes.getCliente();
                partesListPartes.setCliente(clientes);
                partesListPartes = em.merge(partesListPartes);
                if (oldClienteOfPartesListPartes != null) {
                    oldClienteOfPartesListPartes.getPartesList().remove(partesListPartes);
                    oldClienteOfPartesListPartes = em.merge(oldClienteOfPartesListPartes);
                }
            }
            for (Tarea tareaListTarea : clientes.getTareaList()) {
                Clientes oldClienteOfTareaListTarea = tareaListTarea.getCliente();
                tareaListTarea.setCliente(clientes);
                tareaListTarea = em.merge(tareaListTarea);
                if (oldClienteOfTareaListTarea != null) {
                    oldClienteOfTareaListTarea.getTareaList().remove(tareaListTarea);
                    oldClienteOfTareaListTarea = em.merge(oldClienteOfTareaListTarea);
                }
            }
            for (RegistroRevision registroRevisionListRegistroRevision : clientes.getRegistroRevisionList()) {
                Clientes oldClienteOfRegistroRevisionListRegistroRevision = registroRevisionListRegistroRevision.getCliente();
                registroRevisionListRegistroRevision.setCliente(clientes);
                registroRevisionListRegistroRevision = em.merge(registroRevisionListRegistroRevision);
                if (oldClienteOfRegistroRevisionListRegistroRevision != null) {
                    oldClienteOfRegistroRevisionListRegistroRevision.getRegistroRevisionList().remove(registroRevisionListRegistroRevision);
                    oldClienteOfRegistroRevisionListRegistroRevision = em.merge(oldClienteOfRegistroRevisionListRegistroRevision);
                }
            }
            for (Usuario usuarioListUsuario : clientes.getUsuarioList()) {
                Clientes oldClienteOfUsuarioListUsuario = usuarioListUsuario.getCliente();
                usuarioListUsuario.setCliente(clientes);
                usuarioListUsuario = em.merge(usuarioListUsuario);
                if (oldClienteOfUsuarioListUsuario != null) {
                    oldClienteOfUsuarioListUsuario.getUsuarioList().remove(usuarioListUsuario);
                    oldClienteOfUsuarioListUsuario = em.merge(oldClienteOfUsuarioListUsuario);
                }
            }
            for (Contactos contactosListContactos : clientes.getContactosList()) {
                Clientes oldClienteOfContactosListContactos = contactosListContactos.getCliente();
                contactosListContactos.setCliente(clientes);
                contactosListContactos = em.merge(contactosListContactos);
                if (oldClienteOfContactosListContactos != null) {
                    oldClienteOfContactosListContactos.getContactosList().remove(contactosListContactos);
                    oldClienteOfContactosListContactos = em.merge(oldClienteOfContactosListContactos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findClientes(clientes.getCliente()) != null) {
                throw new PreexistingEntityException("Clientes " + clientes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Clientes clientes) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes persistentClientes = em.find(Clientes.class, clientes.getCliente());
            ConexionRemota conexionRemotaOld = persistentClientes.getConexionRemota();
            ConexionRemota conexionRemotaNew = clientes.getConexionRemota();
            InfoBd infoBdOld = persistentClientes.getInfoBd();
            InfoBd infoBdNew = clientes.getInfoBd();
            Bonomodis idBonomodisActivoOld = persistentClientes.getIdBonomodisActivo();
            Bonomodis idBonomodisActivoNew = clientes.getIdBonomodisActivo();
            EmpresaColaboradora tipoClienteOld = persistentClientes.getTipoCliente();
            EmpresaColaboradora tipoClienteNew = clientes.getTipoCliente();
            EstadosCliente estadoOld = persistentClientes.getEstado();
            EstadosCliente estadoNew = clientes.getEstado();
            ServiciosMante idServiciomanteActivoOld = persistentClientes.getIdServiciomanteActivo();
            ServiciosMante idServiciomanteActivoNew = clientes.getIdServiciomanteActivo();
            Tarifa tarifaOld = persistentClientes.getTarifa();
            Tarifa tarifaNew = clientes.getTarifa();
            List<Responsabilidad> responsabilidadListOld = persistentClientes.getResponsabilidadList();
            List<Responsabilidad> responsabilidadListNew = clientes.getResponsabilidadList();
            List<Peticiones> peticionesListOld = persistentClientes.getPeticionesList();
            List<Peticiones> peticionesListNew = clientes.getPeticionesList();
            List<InfoPrograma> infoProgramaListOld = persistentClientes.getInfoProgramaList();
            List<InfoPrograma> infoProgramaListNew = clientes.getInfoProgramaList();
            List<InfoModi> infoModiListOld = persistentClientes.getInfoModiList();
            List<InfoModi> infoModiListNew = clientes.getInfoModiList();
            List<Partes> partesListOld = persistentClientes.getPartesList();
            List<Partes> partesListNew = clientes.getPartesList();
            List<Tarea> tareaListOld = persistentClientes.getTareaList();
            List<Tarea> tareaListNew = clientes.getTareaList();
            List<RegistroRevision> registroRevisionListOld = persistentClientes.getRegistroRevisionList();
            List<RegistroRevision> registroRevisionListNew = clientes.getRegistroRevisionList();
            List<Usuario> usuarioListOld = persistentClientes.getUsuarioList();
            List<Usuario> usuarioListNew = clientes.getUsuarioList();
            List<Contactos> contactosListOld = persistentClientes.getContactosList();
            List<Contactos> contactosListNew = clientes.getContactosList();
            List<String> illegalOrphanMessages = null;
            if (infoBdOld != null && !infoBdOld.equals(infoBdNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain InfoBd " + infoBdOld + " since its cliente field is not nullable.");
            }
            for (Responsabilidad responsabilidadListOldResponsabilidad : responsabilidadListOld) {
                if (!responsabilidadListNew.contains(responsabilidadListOldResponsabilidad)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Responsabilidad " + responsabilidadListOldResponsabilidad + " since its clientes field is not nullable.");
                }
            }
            for (Peticiones peticionesListOldPeticiones : peticionesListOld) {
                if (!peticionesListNew.contains(peticionesListOldPeticiones)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Peticiones " + peticionesListOldPeticiones + " since its cliente field is not nullable.");
                }
            }
            for (InfoPrograma infoProgramaListOldInfoPrograma : infoProgramaListOld) {
                if (!infoProgramaListNew.contains(infoProgramaListOldInfoPrograma)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InfoPrograma " + infoProgramaListOldInfoPrograma + " since its clientes field is not nullable.");
                }
            }
            for (InfoModi infoModiListOldInfoModi : infoModiListOld) {
                if (!infoModiListNew.contains(infoModiListOldInfoModi)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InfoModi " + infoModiListOldInfoModi + " since its clientes field is not nullable.");
                }
            }
            for (Partes partesListOldPartes : partesListOld) {
                if (!partesListNew.contains(partesListOldPartes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Partes " + partesListOldPartes + " since its cliente field is not nullable.");
                }
            }
            for (RegistroRevision registroRevisionListOldRegistroRevision : registroRevisionListOld) {
                if (!registroRevisionListNew.contains(registroRevisionListOldRegistroRevision)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RegistroRevision " + registroRevisionListOldRegistroRevision + " since its cliente field is not nullable.");
                }
            }
            for (Contactos contactosListOldContactos : contactosListOld) {
                if (!contactosListNew.contains(contactosListOldContactos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Contactos " + contactosListOldContactos + " since its cliente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (conexionRemotaNew != null) {
                conexionRemotaNew = em.getReference(conexionRemotaNew.getClass(), conexionRemotaNew.getId());
                clientes.setConexionRemota(conexionRemotaNew);
            }
            if (infoBdNew != null) {
                infoBdNew = em.getReference(infoBdNew.getClass(), infoBdNew.getId());
                clientes.setInfoBd(infoBdNew);
            }
            if (idBonomodisActivoNew != null) {
                idBonomodisActivoNew = em.getReference(idBonomodisActivoNew.getClass(), idBonomodisActivoNew.getIdPeticion());
                clientes.setIdBonomodisActivo(idBonomodisActivoNew);
            }
            if (tipoClienteNew != null) {
                tipoClienteNew = em.getReference(tipoClienteNew.getClass(), tipoClienteNew.getNombre());
                clientes.setTipoCliente(tipoClienteNew);
            }
            if (estadoNew != null) {
                estadoNew = em.getReference(estadoNew.getClass(), estadoNew.getEstado());
                clientes.setEstado(estadoNew);
            }
            if (idServiciomanteActivoNew != null) {
                idServiciomanteActivoNew = em.getReference(idServiciomanteActivoNew.getClass(), idServiciomanteActivoNew.getIdPeticion());
                clientes.setIdServiciomanteActivo(idServiciomanteActivoNew);
            }
            if (tarifaNew != null) {
                tarifaNew = em.getReference(tarifaNew.getClass(), tarifaNew.getTarifa());
                clientes.setTarifa(tarifaNew);
            }
            List<Responsabilidad> attachedResponsabilidadListNew = new ArrayList<Responsabilidad>();
            for (Responsabilidad responsabilidadListNewResponsabilidadToAttach : responsabilidadListNew) {
                responsabilidadListNewResponsabilidadToAttach = em.getReference(responsabilidadListNewResponsabilidadToAttach.getClass(), responsabilidadListNewResponsabilidadToAttach.getResponsabilidadPK());
                attachedResponsabilidadListNew.add(responsabilidadListNewResponsabilidadToAttach);
            }
            responsabilidadListNew = attachedResponsabilidadListNew;
            clientes.setResponsabilidadList(responsabilidadListNew);
            List<Peticiones> attachedPeticionesListNew = new ArrayList<Peticiones>();
            for (Peticiones peticionesListNewPeticionesToAttach : peticionesListNew) {
                peticionesListNewPeticionesToAttach = em.getReference(peticionesListNewPeticionesToAttach.getClass(), peticionesListNewPeticionesToAttach.getIdpeticion());
                attachedPeticionesListNew.add(peticionesListNewPeticionesToAttach);
            }
            peticionesListNew = attachedPeticionesListNew;
            clientes.setPeticionesList(peticionesListNew);
            List<InfoPrograma> attachedInfoProgramaListNew = new ArrayList<InfoPrograma>();
            for (InfoPrograma infoProgramaListNewInfoProgramaToAttach : infoProgramaListNew) {
                infoProgramaListNewInfoProgramaToAttach = em.getReference(infoProgramaListNewInfoProgramaToAttach.getClass(), infoProgramaListNewInfoProgramaToAttach.getInfoProgramaPK());
                attachedInfoProgramaListNew.add(infoProgramaListNewInfoProgramaToAttach);
            }
            infoProgramaListNew = attachedInfoProgramaListNew;
            clientes.setInfoProgramaList(infoProgramaListNew);
            List<InfoModi> attachedInfoModiListNew = new ArrayList<InfoModi>();
            for (InfoModi infoModiListNewInfoModiToAttach : infoModiListNew) {
                infoModiListNewInfoModiToAttach = em.getReference(infoModiListNewInfoModiToAttach.getClass(), infoModiListNewInfoModiToAttach.getInfoModiPK());
                attachedInfoModiListNew.add(infoModiListNewInfoModiToAttach);
            }
            infoModiListNew = attachedInfoModiListNew;
            clientes.setInfoModiList(infoModiListNew);
            List<Partes> attachedPartesListNew = new ArrayList<Partes>();
            for (Partes partesListNewPartesToAttach : partesListNew) {
                partesListNewPartesToAttach = em.getReference(partesListNewPartesToAttach.getClass(), partesListNewPartesToAttach.getNumParte());
                attachedPartesListNew.add(partesListNewPartesToAttach);
            }
            partesListNew = attachedPartesListNew;
            clientes.setPartesList(partesListNew);
            List<Tarea> attachedTareaListNew = new ArrayList<Tarea>();
            for (Tarea tareaListNewTareaToAttach : tareaListNew) {
                tareaListNewTareaToAttach = em.getReference(tareaListNewTareaToAttach.getClass(), tareaListNewTareaToAttach.getId());
                attachedTareaListNew.add(tareaListNewTareaToAttach);
            }
            tareaListNew = attachedTareaListNew;
            clientes.setTareaList(tareaListNew);
            List<RegistroRevision> attachedRegistroRevisionListNew = new ArrayList<RegistroRevision>();
            for (RegistroRevision registroRevisionListNewRegistroRevisionToAttach : registroRevisionListNew) {
                registroRevisionListNewRegistroRevisionToAttach = em.getReference(registroRevisionListNewRegistroRevisionToAttach.getClass(), registroRevisionListNewRegistroRevisionToAttach.getId());
                attachedRegistroRevisionListNew.add(registroRevisionListNewRegistroRevisionToAttach);
            }
            registroRevisionListNew = attachedRegistroRevisionListNew;
            clientes.setRegistroRevisionList(registroRevisionListNew);
            List<Usuario> attachedUsuarioListNew = new ArrayList<Usuario>();
            for (Usuario usuarioListNewUsuarioToAttach : usuarioListNew) {
                usuarioListNewUsuarioToAttach = em.getReference(usuarioListNewUsuarioToAttach.getClass(), usuarioListNewUsuarioToAttach.getUsuario());
                attachedUsuarioListNew.add(usuarioListNewUsuarioToAttach);
            }
            usuarioListNew = attachedUsuarioListNew;
            clientes.setUsuarioList(usuarioListNew);
            List<Contactos> attachedContactosListNew = new ArrayList<Contactos>();
            for (Contactos contactosListNewContactosToAttach : contactosListNew) {
                contactosListNewContactosToAttach = em.getReference(contactosListNewContactosToAttach.getClass(), contactosListNewContactosToAttach.getId());
                attachedContactosListNew.add(contactosListNewContactosToAttach);
            }
            contactosListNew = attachedContactosListNew;
            clientes.setContactosList(contactosListNew);
            clientes = em.merge(clientes);
            if (conexionRemotaOld != null && !conexionRemotaOld.equals(conexionRemotaNew)) {
                conexionRemotaOld.setCliente(null);
                conexionRemotaOld = em.merge(conexionRemotaOld);
            }
            if (conexionRemotaNew != null && !conexionRemotaNew.equals(conexionRemotaOld)) {
                Clientes oldClienteOfConexionRemota = conexionRemotaNew.getCliente();
                if (oldClienteOfConexionRemota != null) {
                    oldClienteOfConexionRemota.setConexionRemota(null);
                    oldClienteOfConexionRemota = em.merge(oldClienteOfConexionRemota);
                }
                conexionRemotaNew.setCliente(clientes);
                conexionRemotaNew = em.merge(conexionRemotaNew);
            }
            if (infoBdNew != null && !infoBdNew.equals(infoBdOld)) {
                Clientes oldClienteOfInfoBd = infoBdNew.getCliente();
                if (oldClienteOfInfoBd != null) {
                    oldClienteOfInfoBd.setInfoBd(null);
                    oldClienteOfInfoBd = em.merge(oldClienteOfInfoBd);
                }
                infoBdNew.setCliente(clientes);
                infoBdNew = em.merge(infoBdNew);
            }
            if (idBonomodisActivoOld != null && !idBonomodisActivoOld.equals(idBonomodisActivoNew)) {
                idBonomodisActivoOld.getClientesList().remove(clientes);
                idBonomodisActivoOld = em.merge(idBonomodisActivoOld);
            }
            if (idBonomodisActivoNew != null && !idBonomodisActivoNew.equals(idBonomodisActivoOld)) {
                idBonomodisActivoNew.getClientesList().add(clientes);
                idBonomodisActivoNew = em.merge(idBonomodisActivoNew);
            }
            if (tipoClienteOld != null && !tipoClienteOld.equals(tipoClienteNew)) {
                tipoClienteOld.getClientesList().remove(clientes);
                tipoClienteOld = em.merge(tipoClienteOld);
            }
            if (tipoClienteNew != null && !tipoClienteNew.equals(tipoClienteOld)) {
                tipoClienteNew.getClientesList().add(clientes);
                tipoClienteNew = em.merge(tipoClienteNew);
            }
            if (estadoOld != null && !estadoOld.equals(estadoNew)) {
                estadoOld.getClientesList().remove(clientes);
                estadoOld = em.merge(estadoOld);
            }
            if (estadoNew != null && !estadoNew.equals(estadoOld)) {
                estadoNew.getClientesList().add(clientes);
                estadoNew = em.merge(estadoNew);
            }
            if (idServiciomanteActivoOld != null && !idServiciomanteActivoOld.equals(idServiciomanteActivoNew)) {
                idServiciomanteActivoOld.getClientesList().remove(clientes);
                idServiciomanteActivoOld = em.merge(idServiciomanteActivoOld);
            }
            if (idServiciomanteActivoNew != null && !idServiciomanteActivoNew.equals(idServiciomanteActivoOld)) {
                idServiciomanteActivoNew.getClientesList().add(clientes);
                idServiciomanteActivoNew = em.merge(idServiciomanteActivoNew);
            }
            if (tarifaOld != null && !tarifaOld.equals(tarifaNew)) {
                tarifaOld.getClientesList().remove(clientes);
                tarifaOld = em.merge(tarifaOld);
            }
            if (tarifaNew != null && !tarifaNew.equals(tarifaOld)) {
                tarifaNew.getClientesList().add(clientes);
                tarifaNew = em.merge(tarifaNew);
            }
            for (Responsabilidad responsabilidadListNewResponsabilidad : responsabilidadListNew) {
                if (!responsabilidadListOld.contains(responsabilidadListNewResponsabilidad)) {
                    Clientes oldClientesOfResponsabilidadListNewResponsabilidad = responsabilidadListNewResponsabilidad.getClientes();
                    responsabilidadListNewResponsabilidad.setClientes(clientes);
                    responsabilidadListNewResponsabilidad = em.merge(responsabilidadListNewResponsabilidad);
                    if (oldClientesOfResponsabilidadListNewResponsabilidad != null && !oldClientesOfResponsabilidadListNewResponsabilidad.equals(clientes)) {
                        oldClientesOfResponsabilidadListNewResponsabilidad.getResponsabilidadList().remove(responsabilidadListNewResponsabilidad);
                        oldClientesOfResponsabilidadListNewResponsabilidad = em.merge(oldClientesOfResponsabilidadListNewResponsabilidad);
                    }
                }
            }
            for (Peticiones peticionesListNewPeticiones : peticionesListNew) {
                if (!peticionesListOld.contains(peticionesListNewPeticiones)) {
                    Clientes oldClienteOfPeticionesListNewPeticiones = peticionesListNewPeticiones.getCliente();
                    peticionesListNewPeticiones.setCliente(clientes);
                    peticionesListNewPeticiones = em.merge(peticionesListNewPeticiones);
                    if (oldClienteOfPeticionesListNewPeticiones != null && !oldClienteOfPeticionesListNewPeticiones.equals(clientes)) {
                        oldClienteOfPeticionesListNewPeticiones.getPeticionesList().remove(peticionesListNewPeticiones);
                        oldClienteOfPeticionesListNewPeticiones = em.merge(oldClienteOfPeticionesListNewPeticiones);
                    }
                }
            }
            for (InfoPrograma infoProgramaListNewInfoPrograma : infoProgramaListNew) {
                if (!infoProgramaListOld.contains(infoProgramaListNewInfoPrograma)) {
                    Clientes oldClientesOfInfoProgramaListNewInfoPrograma = infoProgramaListNewInfoPrograma.getClientes();
                    infoProgramaListNewInfoPrograma.setClientes(clientes);
                    infoProgramaListNewInfoPrograma = em.merge(infoProgramaListNewInfoPrograma);
                    if (oldClientesOfInfoProgramaListNewInfoPrograma != null && !oldClientesOfInfoProgramaListNewInfoPrograma.equals(clientes)) {
                        oldClientesOfInfoProgramaListNewInfoPrograma.getInfoProgramaList().remove(infoProgramaListNewInfoPrograma);
                        oldClientesOfInfoProgramaListNewInfoPrograma = em.merge(oldClientesOfInfoProgramaListNewInfoPrograma);
                    }
                }
            }
            for (InfoModi infoModiListNewInfoModi : infoModiListNew) {
                if (!infoModiListOld.contains(infoModiListNewInfoModi)) {
                    Clientes oldClientesOfInfoModiListNewInfoModi = infoModiListNewInfoModi.getClientes();
                    infoModiListNewInfoModi.setClientes(clientes);
                    infoModiListNewInfoModi = em.merge(infoModiListNewInfoModi);
                    if (oldClientesOfInfoModiListNewInfoModi != null && !oldClientesOfInfoModiListNewInfoModi.equals(clientes)) {
                        oldClientesOfInfoModiListNewInfoModi.getInfoModiList().remove(infoModiListNewInfoModi);
                        oldClientesOfInfoModiListNewInfoModi = em.merge(oldClientesOfInfoModiListNewInfoModi);
                    }
                }
            }
            for (Partes partesListNewPartes : partesListNew) {
                if (!partesListOld.contains(partesListNewPartes)) {
                    Clientes oldClienteOfPartesListNewPartes = partesListNewPartes.getCliente();
                    partesListNewPartes.setCliente(clientes);
                    partesListNewPartes = em.merge(partesListNewPartes);
                    if (oldClienteOfPartesListNewPartes != null && !oldClienteOfPartesListNewPartes.equals(clientes)) {
                        oldClienteOfPartesListNewPartes.getPartesList().remove(partesListNewPartes);
                        oldClienteOfPartesListNewPartes = em.merge(oldClienteOfPartesListNewPartes);
                    }
                }
            }
            for (Tarea tareaListOldTarea : tareaListOld) {
                if (!tareaListNew.contains(tareaListOldTarea)) {
                    tareaListOldTarea.setCliente(null);
                    tareaListOldTarea = em.merge(tareaListOldTarea);
                }
            }
            for (Tarea tareaListNewTarea : tareaListNew) {
                if (!tareaListOld.contains(tareaListNewTarea)) {
                    Clientes oldClienteOfTareaListNewTarea = tareaListNewTarea.getCliente();
                    tareaListNewTarea.setCliente(clientes);
                    tareaListNewTarea = em.merge(tareaListNewTarea);
                    if (oldClienteOfTareaListNewTarea != null && !oldClienteOfTareaListNewTarea.equals(clientes)) {
                        oldClienteOfTareaListNewTarea.getTareaList().remove(tareaListNewTarea);
                        oldClienteOfTareaListNewTarea = em.merge(oldClienteOfTareaListNewTarea);
                    }
                }
            }
            for (RegistroRevision registroRevisionListNewRegistroRevision : registroRevisionListNew) {
                if (!registroRevisionListOld.contains(registroRevisionListNewRegistroRevision)) {
                    Clientes oldClienteOfRegistroRevisionListNewRegistroRevision = registroRevisionListNewRegistroRevision.getCliente();
                    registroRevisionListNewRegistroRevision.setCliente(clientes);
                    registroRevisionListNewRegistroRevision = em.merge(registroRevisionListNewRegistroRevision);
                    if (oldClienteOfRegistroRevisionListNewRegistroRevision != null && !oldClienteOfRegistroRevisionListNewRegistroRevision.equals(clientes)) {
                        oldClienteOfRegistroRevisionListNewRegistroRevision.getRegistroRevisionList().remove(registroRevisionListNewRegistroRevision);
                        oldClienteOfRegistroRevisionListNewRegistroRevision = em.merge(oldClienteOfRegistroRevisionListNewRegistroRevision);
                    }
                }
            }
            for (Usuario usuarioListOldUsuario : usuarioListOld) {
                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
                    usuarioListOldUsuario.setCliente(null);
                    usuarioListOldUsuario = em.merge(usuarioListOldUsuario);
                }
            }
            for (Usuario usuarioListNewUsuario : usuarioListNew) {
                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
                    Clientes oldClienteOfUsuarioListNewUsuario = usuarioListNewUsuario.getCliente();
                    usuarioListNewUsuario.setCliente(clientes);
                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
                    if (oldClienteOfUsuarioListNewUsuario != null && !oldClienteOfUsuarioListNewUsuario.equals(clientes)) {
                        oldClienteOfUsuarioListNewUsuario.getUsuarioList().remove(usuarioListNewUsuario);
                        oldClienteOfUsuarioListNewUsuario = em.merge(oldClienteOfUsuarioListNewUsuario);
                    }
                }
            }
            for (Contactos contactosListNewContactos : contactosListNew) {
                if (!contactosListOld.contains(contactosListNewContactos)) {
                    Clientes oldClienteOfContactosListNewContactos = contactosListNewContactos.getCliente();
                    contactosListNewContactos.setCliente(clientes);
                    contactosListNewContactos = em.merge(contactosListNewContactos);
                    if (oldClienteOfContactosListNewContactos != null && !oldClienteOfContactosListNewContactos.equals(clientes)) {
                        oldClienteOfContactosListNewContactos.getContactosList().remove(contactosListNewContactos);
                        oldClienteOfContactosListNewContactos = em.merge(oldClienteOfContactosListNewContactos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = clientes.getCliente();
                if (findClientes(id) == null) {
                    throw new NonexistentEntityException("The clientes with id " + id + " no longer exists.");
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
            Clientes clientes;
            try {
                clientes = em.getReference(Clientes.class, id);
                clientes.getCliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clientes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            InfoBd infoBdOrphanCheck = clientes.getInfoBd();
            if (infoBdOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clientes (" + clientes + ") cannot be destroyed since the InfoBd " + infoBdOrphanCheck + " in its infoBd field has a non-nullable cliente field.");
            }
            List<Responsabilidad> responsabilidadListOrphanCheck = clientes.getResponsabilidadList();
            for (Responsabilidad responsabilidadListOrphanCheckResponsabilidad : responsabilidadListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clientes (" + clientes + ") cannot be destroyed since the Responsabilidad " + responsabilidadListOrphanCheckResponsabilidad + " in its responsabilidadList field has a non-nullable clientes field.");
            }
            List<Peticiones> peticionesListOrphanCheck = clientes.getPeticionesList();
            for (Peticiones peticionesListOrphanCheckPeticiones : peticionesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clientes (" + clientes + ") cannot be destroyed since the Peticiones " + peticionesListOrphanCheckPeticiones + " in its peticionesList field has a non-nullable cliente field.");
            }
            List<InfoPrograma> infoProgramaListOrphanCheck = clientes.getInfoProgramaList();
            for (InfoPrograma infoProgramaListOrphanCheckInfoPrograma : infoProgramaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clientes (" + clientes + ") cannot be destroyed since the InfoPrograma " + infoProgramaListOrphanCheckInfoPrograma + " in its infoProgramaList field has a non-nullable clientes field.");
            }
            List<InfoModi> infoModiListOrphanCheck = clientes.getInfoModiList();
            for (InfoModi infoModiListOrphanCheckInfoModi : infoModiListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clientes (" + clientes + ") cannot be destroyed since the InfoModi " + infoModiListOrphanCheckInfoModi + " in its infoModiList field has a non-nullable clientes field.");
            }
            List<Partes> partesListOrphanCheck = clientes.getPartesList();
            for (Partes partesListOrphanCheckPartes : partesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clientes (" + clientes + ") cannot be destroyed since the Partes " + partesListOrphanCheckPartes + " in its partesList field has a non-nullable cliente field.");
            }
            List<RegistroRevision> registroRevisionListOrphanCheck = clientes.getRegistroRevisionList();
            for (RegistroRevision registroRevisionListOrphanCheckRegistroRevision : registroRevisionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clientes (" + clientes + ") cannot be destroyed since the RegistroRevision " + registroRevisionListOrphanCheckRegistroRevision + " in its registroRevisionList field has a non-nullable cliente field.");
            }
            List<Contactos> contactosListOrphanCheck = clientes.getContactosList();
            for (Contactos contactosListOrphanCheckContactos : contactosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clientes (" + clientes + ") cannot be destroyed since the Contactos " + contactosListOrphanCheckContactos + " in its contactosList field has a non-nullable cliente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            ConexionRemota conexionRemota = clientes.getConexionRemota();
            if (conexionRemota != null) {
                conexionRemota.setCliente(null);
                conexionRemota = em.merge(conexionRemota);
            }
            Bonomodis idBonomodisActivo = clientes.getIdBonomodisActivo();
            if (idBonomodisActivo != null) {
                idBonomodisActivo.getClientesList().remove(clientes);
                idBonomodisActivo = em.merge(idBonomodisActivo);
            }
            EmpresaColaboradora tipoCliente = clientes.getTipoCliente();
            if (tipoCliente != null) {
                tipoCliente.getClientesList().remove(clientes);
                tipoCliente = em.merge(tipoCliente);
            }
            EstadosCliente estado = clientes.getEstado();
            if (estado != null) {
                estado.getClientesList().remove(clientes);
                estado = em.merge(estado);
            }
            ServiciosMante idServiciomanteActivo = clientes.getIdServiciomanteActivo();
            if (idServiciomanteActivo != null) {
                idServiciomanteActivo.getClientesList().remove(clientes);
                idServiciomanteActivo = em.merge(idServiciomanteActivo);
            }
            Tarifa tarifa = clientes.getTarifa();
            if (tarifa != null) {
                tarifa.getClientesList().remove(clientes);
                tarifa = em.merge(tarifa);
            }
            List<Tarea> tareaList = clientes.getTareaList();
            for (Tarea tareaListTarea : tareaList) {
                tareaListTarea.setCliente(null);
                tareaListTarea = em.merge(tareaListTarea);
            }
            List<Usuario> usuarioList = clientes.getUsuarioList();
            for (Usuario usuarioListUsuario : usuarioList) {
                usuarioListUsuario.setCliente(null);
                usuarioListUsuario = em.merge(usuarioListUsuario);
            }
            em.remove(clientes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Clientes> findClientesEntities() {
        return findClientesEntities(true, -1, -1);
    }

    public List<Clientes> findClientesEntities(int maxResults, int firstResult) {
        return findClientesEntities(false, maxResults, firstResult);
    }

    private List<Clientes> findClientesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Clientes.class));
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

    public Clientes findClientes(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Clientes.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Clientes> rt = cq.from(Clientes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
