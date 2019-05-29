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
import com.seidor.jmanteweb.persistencia.entidades.UsuarioRueda;
import com.seidor.jmanteweb.persistencia.entidades.Clientes;
import com.seidor.jmanteweb.persistencia.entidades.Componentemenu;
import com.seidor.jmanteweb.persistencia.entidades.Perfil;
import com.seidor.jmanteweb.persistencia.entidades.RuedaPalex;
import com.seidor.jmanteweb.persistencia.entidades.Registroparte;
import java.util.ArrayList;
import java.util.List;
import com.seidor.jmanteweb.persistencia.entidades.Cambioprograma;
import com.seidor.jmanteweb.persistencia.entidades.Responsabilidad;
import com.seidor.jmanteweb.persistencia.entidades.Guardiapalex;
import com.seidor.jmanteweb.persistencia.entidades.Pantallaini;
import com.seidor.jmanteweb.persistencia.entidades.UsuarioTarea;
import com.seidor.jmanteweb.persistencia.entidades.Presupuestos;
import com.seidor.jmanteweb.persistencia.entidades.Visualizaciongrid;
import com.seidor.jmanteweb.persistencia.entidades.InfoModi;
import com.seidor.jmanteweb.persistencia.entidades.Partes;
import com.seidor.jmanteweb.persistencia.entidades.Tarea;
import com.seidor.jmanteweb.persistencia.entidades.Favorito;
import com.seidor.jmanteweb.persistencia.entidades.RegistroRevision;
import com.seidor.jmanteweb.persistencia.entidades.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;

/**
 *
 * @author AMARTEL
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws PreexistingEntityException, Exception {
        if (usuario.getRegistroparteList() == null) {
            usuario.setRegistroparteList(new ArrayList<Registroparte>());
        }
        if (usuario.getCambioprogramaList() == null) {
            usuario.setCambioprogramaList(new ArrayList<Cambioprograma>());
        }
        if (usuario.getResponsabilidadList() == null) {
            usuario.setResponsabilidadList(new ArrayList<Responsabilidad>());
        }
        if (usuario.getGuardiapalexList() == null) {
            usuario.setGuardiapalexList(new ArrayList<Guardiapalex>());
        }
        if (usuario.getGuardiapalexList1() == null) {
            usuario.setGuardiapalexList1(new ArrayList<Guardiapalex>());
        }
        if (usuario.getPantallainiList() == null) {
            usuario.setPantallainiList(new ArrayList<Pantallaini>());
        }
        if (usuario.getUsuarioTareaList() == null) {
            usuario.setUsuarioTareaList(new ArrayList<UsuarioTarea>());
        }
        if (usuario.getUsuarioRuedaList() == null) {
            usuario.setUsuarioRuedaList(new ArrayList<UsuarioRueda>());
        }
        if (usuario.getPresupuestosList() == null) {
            usuario.setPresupuestosList(new ArrayList<Presupuestos>());
        }
        if (usuario.getVisualizaciongridList() == null) {
            usuario.setVisualizaciongridList(new ArrayList<Visualizaciongrid>());
        }
        if (usuario.getInfoModiList() == null) {
            usuario.setInfoModiList(new ArrayList<InfoModi>());
        }
        if (usuario.getPartesList() == null) {
            usuario.setPartesList(new ArrayList<Partes>());
        }
        if (usuario.getTareaList() == null) {
            usuario.setTareaList(new ArrayList<Tarea>());
        }
        if (usuario.getFavoritoList() == null) {
            usuario.setFavoritoList(new ArrayList<Favorito>());
        }
        if (usuario.getRegistroRevisionList() == null) {
            usuario.setRegistroRevisionList(new ArrayList<RegistroRevision>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UsuarioRueda usuarioRueda = usuario.getUsuarioRueda();
            if (usuarioRueda != null) {
                usuarioRueda = em.getReference(usuarioRueda.getClass(), usuarioRueda.getUsuariomante());
                usuario.setUsuarioRueda(usuarioRueda);
            }
            Clientes cliente = usuario.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getCliente());
                usuario.setCliente(cliente);
            }
            Componentemenu menu = usuario.getMenu();
            if (menu != null) {
                menu = em.getReference(menu.getClass(), menu.getNombre());
                usuario.setMenu(menu);
            }
            Perfil perfil = usuario.getPerfil();
            if (perfil != null) {
                perfil = em.getReference(perfil.getClass(), perfil.getPerfil());
                usuario.setPerfil(perfil);
            }
            RuedaPalex ruedaPalex = usuario.getRuedaPalex();
            if (ruedaPalex != null) {
                ruedaPalex = em.getReference(ruedaPalex.getClass(), ruedaPalex.getUsuario());
                usuario.setRuedaPalex(ruedaPalex);
            }
            List<Registroparte> attachedRegistroparteList = new ArrayList<Registroparte>();
            for (Registroparte registroparteListRegistroparteToAttach : usuario.getRegistroparteList()) {
                registroparteListRegistroparteToAttach = em.getReference(registroparteListRegistroparteToAttach.getClass(), registroparteListRegistroparteToAttach.getId());
                attachedRegistroparteList.add(registroparteListRegistroparteToAttach);
            }
            usuario.setRegistroparteList(attachedRegistroparteList);
            List<Cambioprograma> attachedCambioprogramaList = new ArrayList<Cambioprograma>();
            for (Cambioprograma cambioprogramaListCambioprogramaToAttach : usuario.getCambioprogramaList()) {
                cambioprogramaListCambioprogramaToAttach = em.getReference(cambioprogramaListCambioprogramaToAttach.getClass(), cambioprogramaListCambioprogramaToAttach.getIdcambio());
                attachedCambioprogramaList.add(cambioprogramaListCambioprogramaToAttach);
            }
            usuario.setCambioprogramaList(attachedCambioprogramaList);
            List<Responsabilidad> attachedResponsabilidadList = new ArrayList<Responsabilidad>();
            for (Responsabilidad responsabilidadListResponsabilidadToAttach : usuario.getResponsabilidadList()) {
                responsabilidadListResponsabilidadToAttach = em.getReference(responsabilidadListResponsabilidadToAttach.getClass(), responsabilidadListResponsabilidadToAttach.getResponsabilidadPK());
                attachedResponsabilidadList.add(responsabilidadListResponsabilidadToAttach);
            }
            usuario.setResponsabilidadList(attachedResponsabilidadList);
            List<Guardiapalex> attachedGuardiapalexList = new ArrayList<Guardiapalex>();
            for (Guardiapalex guardiapalexListGuardiapalexToAttach : usuario.getGuardiapalexList()) {
                guardiapalexListGuardiapalexToAttach = em.getReference(guardiapalexListGuardiapalexToAttach.getClass(), guardiapalexListGuardiapalexToAttach.getIdguardia());
                attachedGuardiapalexList.add(guardiapalexListGuardiapalexToAttach);
            }
            usuario.setGuardiapalexList(attachedGuardiapalexList);
            List<Guardiapalex> attachedGuardiapalexList1 = new ArrayList<Guardiapalex>();
            for (Guardiapalex guardiapalexList1GuardiapalexToAttach : usuario.getGuardiapalexList1()) {
                guardiapalexList1GuardiapalexToAttach = em.getReference(guardiapalexList1GuardiapalexToAttach.getClass(), guardiapalexList1GuardiapalexToAttach.getIdguardia());
                attachedGuardiapalexList1.add(guardiapalexList1GuardiapalexToAttach);
            }
            usuario.setGuardiapalexList1(attachedGuardiapalexList1);
            List<Pantallaini> attachedPantallainiList = new ArrayList<Pantallaini>();
            for (Pantallaini pantallainiListPantallainiToAttach : usuario.getPantallainiList()) {
                pantallainiListPantallainiToAttach = em.getReference(pantallainiListPantallainiToAttach.getClass(), pantallainiListPantallainiToAttach.getId());
                attachedPantallainiList.add(pantallainiListPantallainiToAttach);
            }
            usuario.setPantallainiList(attachedPantallainiList);
            List<UsuarioTarea> attachedUsuarioTareaList = new ArrayList<UsuarioTarea>();
            for (UsuarioTarea usuarioTareaListUsuarioTareaToAttach : usuario.getUsuarioTareaList()) {
                usuarioTareaListUsuarioTareaToAttach = em.getReference(usuarioTareaListUsuarioTareaToAttach.getClass(), usuarioTareaListUsuarioTareaToAttach.getId());
                attachedUsuarioTareaList.add(usuarioTareaListUsuarioTareaToAttach);
            }
            usuario.setUsuarioTareaList(attachedUsuarioTareaList);
            List<UsuarioRueda> attachedUsuarioRuedaList = new ArrayList<UsuarioRueda>();
            for (UsuarioRueda usuarioRuedaListUsuarioRuedaToAttach : usuario.getUsuarioRuedaList()) {
                usuarioRuedaListUsuarioRuedaToAttach = em.getReference(usuarioRuedaListUsuarioRuedaToAttach.getClass(), usuarioRuedaListUsuarioRuedaToAttach.getUsuariomante());
                attachedUsuarioRuedaList.add(usuarioRuedaListUsuarioRuedaToAttach);
            }
            usuario.setUsuarioRuedaList(attachedUsuarioRuedaList);
            List<Presupuestos> attachedPresupuestosList = new ArrayList<Presupuestos>();
            for (Presupuestos presupuestosListPresupuestosToAttach : usuario.getPresupuestosList()) {
                presupuestosListPresupuestosToAttach = em.getReference(presupuestosListPresupuestosToAttach.getClass(), presupuestosListPresupuestosToAttach.getNum());
                attachedPresupuestosList.add(presupuestosListPresupuestosToAttach);
            }
            usuario.setPresupuestosList(attachedPresupuestosList);
            List<Visualizaciongrid> attachedVisualizaciongridList = new ArrayList<Visualizaciongrid>();
            for (Visualizaciongrid visualizaciongridListVisualizaciongridToAttach : usuario.getVisualizaciongridList()) {
                visualizaciongridListVisualizaciongridToAttach = em.getReference(visualizaciongridListVisualizaciongridToAttach.getClass(), visualizaciongridListVisualizaciongridToAttach.getIdvisua());
                attachedVisualizaciongridList.add(visualizaciongridListVisualizaciongridToAttach);
            }
            usuario.setVisualizaciongridList(attachedVisualizaciongridList);
            List<InfoModi> attachedInfoModiList = new ArrayList<InfoModi>();
            for (InfoModi infoModiListInfoModiToAttach : usuario.getInfoModiList()) {
                infoModiListInfoModiToAttach = em.getReference(infoModiListInfoModiToAttach.getClass(), infoModiListInfoModiToAttach.getInfoModiPK());
                attachedInfoModiList.add(infoModiListInfoModiToAttach);
            }
            usuario.setInfoModiList(attachedInfoModiList);
            List<Partes> attachedPartesList = new ArrayList<Partes>();
            for (Partes partesListPartesToAttach : usuario.getPartesList()) {
                partesListPartesToAttach = em.getReference(partesListPartesToAttach.getClass(), partesListPartesToAttach.getNumParte());
                attachedPartesList.add(partesListPartesToAttach);
            }
            usuario.setPartesList(attachedPartesList);
            List<Tarea> attachedTareaList = new ArrayList<Tarea>();
            for (Tarea tareaListTareaToAttach : usuario.getTareaList()) {
                tareaListTareaToAttach = em.getReference(tareaListTareaToAttach.getClass(), tareaListTareaToAttach.getId());
                attachedTareaList.add(tareaListTareaToAttach);
            }
            usuario.setTareaList(attachedTareaList);
            List<Favorito> attachedFavoritoList = new ArrayList<Favorito>();
            for (Favorito favoritoListFavoritoToAttach : usuario.getFavoritoList()) {
                favoritoListFavoritoToAttach = em.getReference(favoritoListFavoritoToAttach.getClass(), favoritoListFavoritoToAttach.getFavoritoPK());
                attachedFavoritoList.add(favoritoListFavoritoToAttach);
            }
            usuario.setFavoritoList(attachedFavoritoList);
            List<RegistroRevision> attachedRegistroRevisionList = new ArrayList<RegistroRevision>();
            for (RegistroRevision registroRevisionListRegistroRevisionToAttach : usuario.getRegistroRevisionList()) {
                registroRevisionListRegistroRevisionToAttach = em.getReference(registroRevisionListRegistroRevisionToAttach.getClass(), registroRevisionListRegistroRevisionToAttach.getId());
                attachedRegistroRevisionList.add(registroRevisionListRegistroRevisionToAttach);
            }
            usuario.setRegistroRevisionList(attachedRegistroRevisionList);
            em.persist(usuario);
            if (usuarioRueda != null) {
                Usuario oldUsuarioOfUsuarioRueda = usuarioRueda.getUsuario();
                if (oldUsuarioOfUsuarioRueda != null) {
                    oldUsuarioOfUsuarioRueda.setUsuarioRueda(null);
                    oldUsuarioOfUsuarioRueda = em.merge(oldUsuarioOfUsuarioRueda);
                }
                usuarioRueda.setUsuario(usuario);
                usuarioRueda = em.merge(usuarioRueda);
            }
            if (cliente != null) {
                cliente.getUsuarioList().add(usuario);
                cliente = em.merge(cliente);
            }
            if (menu != null) {
                menu.getUsuarioList().add(usuario);
                menu = em.merge(menu);
            }
            if (perfil != null) {
                perfil.getUsuarioList().add(usuario);
                perfil = em.merge(perfil);
            }
            if (ruedaPalex != null) {
                Usuario oldUsuario1OfRuedaPalex = ruedaPalex.getUsuario1();
                if (oldUsuario1OfRuedaPalex != null) {
                    oldUsuario1OfRuedaPalex.setRuedaPalex(null);
                    oldUsuario1OfRuedaPalex = em.merge(oldUsuario1OfRuedaPalex);
                }
                ruedaPalex.setUsuario1(usuario);
                ruedaPalex = em.merge(ruedaPalex);
            }
            for (Registroparte registroparteListRegistroparte : usuario.getRegistroparteList()) {
                Usuario oldUsuarioOfRegistroparteListRegistroparte = registroparteListRegistroparte.getUsuario();
                registroparteListRegistroparte.setUsuario(usuario);
                registroparteListRegistroparte = em.merge(registroparteListRegistroparte);
                if (oldUsuarioOfRegistroparteListRegistroparte != null) {
                    oldUsuarioOfRegistroparteListRegistroparte.getRegistroparteList().remove(registroparteListRegistroparte);
                    oldUsuarioOfRegistroparteListRegistroparte = em.merge(oldUsuarioOfRegistroparteListRegistroparte);
                }
            }
            for (Cambioprograma cambioprogramaListCambioprograma : usuario.getCambioprogramaList()) {
                Usuario oldUsuarioOfCambioprogramaListCambioprograma = cambioprogramaListCambioprograma.getUsuario();
                cambioprogramaListCambioprograma.setUsuario(usuario);
                cambioprogramaListCambioprograma = em.merge(cambioprogramaListCambioprograma);
                if (oldUsuarioOfCambioprogramaListCambioprograma != null) {
                    oldUsuarioOfCambioprogramaListCambioprograma.getCambioprogramaList().remove(cambioprogramaListCambioprograma);
                    oldUsuarioOfCambioprogramaListCambioprograma = em.merge(oldUsuarioOfCambioprogramaListCambioprograma);
                }
            }
            for (Responsabilidad responsabilidadListResponsabilidad : usuario.getResponsabilidadList()) {
                Usuario oldUsuarioOfResponsabilidadListResponsabilidad = responsabilidadListResponsabilidad.getUsuario();
                responsabilidadListResponsabilidad.setUsuario(usuario);
                responsabilidadListResponsabilidad = em.merge(responsabilidadListResponsabilidad);
                if (oldUsuarioOfResponsabilidadListResponsabilidad != null) {
                    oldUsuarioOfResponsabilidadListResponsabilidad.getResponsabilidadList().remove(responsabilidadListResponsabilidad);
                    oldUsuarioOfResponsabilidadListResponsabilidad = em.merge(oldUsuarioOfResponsabilidadListResponsabilidad);
                }
            }
            for (Guardiapalex guardiapalexListGuardiapalex : usuario.getGuardiapalexList()) {
                Usuario oldTitularOfGuardiapalexListGuardiapalex = guardiapalexListGuardiapalex.getTitular();
                guardiapalexListGuardiapalex.setTitular(usuario);
                guardiapalexListGuardiapalex = em.merge(guardiapalexListGuardiapalex);
                if (oldTitularOfGuardiapalexListGuardiapalex != null) {
                    oldTitularOfGuardiapalexListGuardiapalex.getGuardiapalexList().remove(guardiapalexListGuardiapalex);
                    oldTitularOfGuardiapalexListGuardiapalex = em.merge(oldTitularOfGuardiapalexListGuardiapalex);
                }
            }
            for (Guardiapalex guardiapalexList1Guardiapalex : usuario.getGuardiapalexList1()) {
                Usuario oldUsuarioRealOfGuardiapalexList1Guardiapalex = guardiapalexList1Guardiapalex.getUsuarioReal();
                guardiapalexList1Guardiapalex.setUsuarioReal(usuario);
                guardiapalexList1Guardiapalex = em.merge(guardiapalexList1Guardiapalex);
                if (oldUsuarioRealOfGuardiapalexList1Guardiapalex != null) {
                    oldUsuarioRealOfGuardiapalexList1Guardiapalex.getGuardiapalexList1().remove(guardiapalexList1Guardiapalex);
                    oldUsuarioRealOfGuardiapalexList1Guardiapalex = em.merge(oldUsuarioRealOfGuardiapalexList1Guardiapalex);
                }
            }
            for (Pantallaini pantallainiListPantallaini : usuario.getPantallainiList()) {
                Usuario oldUsuarioOfPantallainiListPantallaini = pantallainiListPantallaini.getUsuario();
                pantallainiListPantallaini.setUsuario(usuario);
                pantallainiListPantallaini = em.merge(pantallainiListPantallaini);
                if (oldUsuarioOfPantallainiListPantallaini != null) {
                    oldUsuarioOfPantallainiListPantallaini.getPantallainiList().remove(pantallainiListPantallaini);
                    oldUsuarioOfPantallainiListPantallaini = em.merge(oldUsuarioOfPantallainiListPantallaini);
                }
            }
            for (UsuarioTarea usuarioTareaListUsuarioTarea : usuario.getUsuarioTareaList()) {
                Usuario oldUsuarioasignadoOfUsuarioTareaListUsuarioTarea = usuarioTareaListUsuarioTarea.getUsuarioasignado();
                usuarioTareaListUsuarioTarea.setUsuarioasignado(usuario);
                usuarioTareaListUsuarioTarea = em.merge(usuarioTareaListUsuarioTarea);
                if (oldUsuarioasignadoOfUsuarioTareaListUsuarioTarea != null) {
                    oldUsuarioasignadoOfUsuarioTareaListUsuarioTarea.getUsuarioTareaList().remove(usuarioTareaListUsuarioTarea);
                    oldUsuarioasignadoOfUsuarioTareaListUsuarioTarea = em.merge(oldUsuarioasignadoOfUsuarioTareaListUsuarioTarea);
                }
            }
            for (UsuarioRueda usuarioRuedaListUsuarioRueda : usuario.getUsuarioRuedaList()) {
                Usuario oldParejaOfUsuarioRuedaListUsuarioRueda = usuarioRuedaListUsuarioRueda.getPareja();
                usuarioRuedaListUsuarioRueda.setPareja(usuario);
                usuarioRuedaListUsuarioRueda = em.merge(usuarioRuedaListUsuarioRueda);
                if (oldParejaOfUsuarioRuedaListUsuarioRueda != null) {
                    oldParejaOfUsuarioRuedaListUsuarioRueda.getUsuarioRuedaList().remove(usuarioRuedaListUsuarioRueda);
                    oldParejaOfUsuarioRuedaListUsuarioRueda = em.merge(oldParejaOfUsuarioRuedaListUsuarioRueda);
                }
            }
            for (Presupuestos presupuestosListPresupuestos : usuario.getPresupuestosList()) {
                Usuario oldUsuarioManteOfPresupuestosListPresupuestos = presupuestosListPresupuestos.getUsuarioMante();
                presupuestosListPresupuestos.setUsuarioMante(usuario);
                presupuestosListPresupuestos = em.merge(presupuestosListPresupuestos);
                if (oldUsuarioManteOfPresupuestosListPresupuestos != null) {
                    oldUsuarioManteOfPresupuestosListPresupuestos.getPresupuestosList().remove(presupuestosListPresupuestos);
                    oldUsuarioManteOfPresupuestosListPresupuestos = em.merge(oldUsuarioManteOfPresupuestosListPresupuestos);
                }
            }
            for (Visualizaciongrid visualizaciongridListVisualizaciongrid : usuario.getVisualizaciongridList()) {
                Usuario oldUsuarioOfVisualizaciongridListVisualizaciongrid = visualizaciongridListVisualizaciongrid.getUsuario();
                visualizaciongridListVisualizaciongrid.setUsuario(usuario);
                visualizaciongridListVisualizaciongrid = em.merge(visualizaciongridListVisualizaciongrid);
                if (oldUsuarioOfVisualizaciongridListVisualizaciongrid != null) {
                    oldUsuarioOfVisualizaciongridListVisualizaciongrid.getVisualizaciongridList().remove(visualizaciongridListVisualizaciongrid);
                    oldUsuarioOfVisualizaciongridListVisualizaciongrid = em.merge(oldUsuarioOfVisualizaciongridListVisualizaciongrid);
                }
            }
            for (InfoModi infoModiListInfoModi : usuario.getInfoModiList()) {
                Usuario oldUsuarioaplicacionOfInfoModiListInfoModi = infoModiListInfoModi.getUsuarioaplicacion();
                infoModiListInfoModi.setUsuarioaplicacion(usuario);
                infoModiListInfoModi = em.merge(infoModiListInfoModi);
                if (oldUsuarioaplicacionOfInfoModiListInfoModi != null) {
                    oldUsuarioaplicacionOfInfoModiListInfoModi.getInfoModiList().remove(infoModiListInfoModi);
                    oldUsuarioaplicacionOfInfoModiListInfoModi = em.merge(oldUsuarioaplicacionOfInfoModiListInfoModi);
                }
            }
            for (Partes partesListPartes : usuario.getPartesList()) {
                Usuario oldUsuarioOfPartesListPartes = partesListPartes.getUsuario();
                partesListPartes.setUsuario(usuario);
                partesListPartes = em.merge(partesListPartes);
                if (oldUsuarioOfPartesListPartes != null) {
                    oldUsuarioOfPartesListPartes.getPartesList().remove(partesListPartes);
                    oldUsuarioOfPartesListPartes = em.merge(oldUsuarioOfPartesListPartes);
                }
            }
            for (Tarea tareaListTarea : usuario.getTareaList()) {
                Usuario oldUsuarioOfTareaListTarea = tareaListTarea.getUsuario();
                tareaListTarea.setUsuario(usuario);
                tareaListTarea = em.merge(tareaListTarea);
                if (oldUsuarioOfTareaListTarea != null) {
                    oldUsuarioOfTareaListTarea.getTareaList().remove(tareaListTarea);
                    oldUsuarioOfTareaListTarea = em.merge(oldUsuarioOfTareaListTarea);
                }
            }
            for (Favorito favoritoListFavorito : usuario.getFavoritoList()) {
                Usuario oldUsuario1OfFavoritoListFavorito = favoritoListFavorito.getUsuario1();
                favoritoListFavorito.setUsuario1(usuario);
                favoritoListFavorito = em.merge(favoritoListFavorito);
                if (oldUsuario1OfFavoritoListFavorito != null) {
                    oldUsuario1OfFavoritoListFavorito.getFavoritoList().remove(favoritoListFavorito);
                    oldUsuario1OfFavoritoListFavorito = em.merge(oldUsuario1OfFavoritoListFavorito);
                }
            }
            for (RegistroRevision registroRevisionListRegistroRevision : usuario.getRegistroRevisionList()) {
                Usuario oldUsuarioOfRegistroRevisionListRegistroRevision = registroRevisionListRegistroRevision.getUsuario();
                registroRevisionListRegistroRevision.setUsuario(usuario);
                registroRevisionListRegistroRevision = em.merge(registroRevisionListRegistroRevision);
                if (oldUsuarioOfRegistroRevisionListRegistroRevision != null) {
                    oldUsuarioOfRegistroRevisionListRegistroRevision.getRegistroRevisionList().remove(registroRevisionListRegistroRevision);
                    oldUsuarioOfRegistroRevisionListRegistroRevision = em.merge(oldUsuarioOfRegistroRevisionListRegistroRevision);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuario(usuario.getUsuario()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getUsuario());
            UsuarioRueda usuarioRuedaOld = persistentUsuario.getUsuarioRueda();
            UsuarioRueda usuarioRuedaNew = usuario.getUsuarioRueda();
            Clientes clienteOld = persistentUsuario.getCliente();
            Clientes clienteNew = usuario.getCliente();
            Componentemenu menuOld = persistentUsuario.getMenu();
            Componentemenu menuNew = usuario.getMenu();
            Perfil perfilOld = persistentUsuario.getPerfil();
            Perfil perfilNew = usuario.getPerfil();
            RuedaPalex ruedaPalexOld = persistentUsuario.getRuedaPalex();
            RuedaPalex ruedaPalexNew = usuario.getRuedaPalex();
            List<Registroparte> registroparteListOld = persistentUsuario.getRegistroparteList();
            List<Registroparte> registroparteListNew = usuario.getRegistroparteList();
            List<Cambioprograma> cambioprogramaListOld = persistentUsuario.getCambioprogramaList();
            List<Cambioprograma> cambioprogramaListNew = usuario.getCambioprogramaList();
            List<Responsabilidad> responsabilidadListOld = persistentUsuario.getResponsabilidadList();
            List<Responsabilidad> responsabilidadListNew = usuario.getResponsabilidadList();
            List<Guardiapalex> guardiapalexListOld = persistentUsuario.getGuardiapalexList();
            List<Guardiapalex> guardiapalexListNew = usuario.getGuardiapalexList();
            List<Guardiapalex> guardiapalexList1Old = persistentUsuario.getGuardiapalexList1();
            List<Guardiapalex> guardiapalexList1New = usuario.getGuardiapalexList1();
            List<Pantallaini> pantallainiListOld = persistentUsuario.getPantallainiList();
            List<Pantallaini> pantallainiListNew = usuario.getPantallainiList();
            List<UsuarioTarea> usuarioTareaListOld = persistentUsuario.getUsuarioTareaList();
            List<UsuarioTarea> usuarioTareaListNew = usuario.getUsuarioTareaList();
            List<UsuarioRueda> usuarioRuedaListOld = persistentUsuario.getUsuarioRuedaList();
            List<UsuarioRueda> usuarioRuedaListNew = usuario.getUsuarioRuedaList();
            List<Presupuestos> presupuestosListOld = persistentUsuario.getPresupuestosList();
            List<Presupuestos> presupuestosListNew = usuario.getPresupuestosList();
            List<Visualizaciongrid> visualizaciongridListOld = persistentUsuario.getVisualizaciongridList();
            List<Visualizaciongrid> visualizaciongridListNew = usuario.getVisualizaciongridList();
            List<InfoModi> infoModiListOld = persistentUsuario.getInfoModiList();
            List<InfoModi> infoModiListNew = usuario.getInfoModiList();
            List<Partes> partesListOld = persistentUsuario.getPartesList();
            List<Partes> partesListNew = usuario.getPartesList();
            List<Tarea> tareaListOld = persistentUsuario.getTareaList();
            List<Tarea> tareaListNew = usuario.getTareaList();
            List<Favorito> favoritoListOld = persistentUsuario.getFavoritoList();
            List<Favorito> favoritoListNew = usuario.getFavoritoList();
            List<RegistroRevision> registroRevisionListOld = persistentUsuario.getRegistroRevisionList();
            List<RegistroRevision> registroRevisionListNew = usuario.getRegistroRevisionList();
            List<String> illegalOrphanMessages = null;
            if (usuarioRuedaOld != null && !usuarioRuedaOld.equals(usuarioRuedaNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain UsuarioRueda " + usuarioRuedaOld + " since its usuario field is not nullable.");
            }
            if (ruedaPalexOld != null && !ruedaPalexOld.equals(ruedaPalexNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain RuedaPalex " + ruedaPalexOld + " since its usuario1 field is not nullable.");
            }
            for (Cambioprograma cambioprogramaListOldCambioprograma : cambioprogramaListOld) {
                if (!cambioprogramaListNew.contains(cambioprogramaListOldCambioprograma)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cambioprograma " + cambioprogramaListOldCambioprograma + " since its usuario field is not nullable.");
                }
            }
            for (Responsabilidad responsabilidadListOldResponsabilidad : responsabilidadListOld) {
                if (!responsabilidadListNew.contains(responsabilidadListOldResponsabilidad)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Responsabilidad " + responsabilidadListOldResponsabilidad + " since its usuario field is not nullable.");
                }
            }
            for (Pantallaini pantallainiListOldPantallaini : pantallainiListOld) {
                if (!pantallainiListNew.contains(pantallainiListOldPantallaini)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pantallaini " + pantallainiListOldPantallaini + " since its usuario field is not nullable.");
                }
            }
            for (UsuarioTarea usuarioTareaListOldUsuarioTarea : usuarioTareaListOld) {
                if (!usuarioTareaListNew.contains(usuarioTareaListOldUsuarioTarea)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UsuarioTarea " + usuarioTareaListOldUsuarioTarea + " since its usuarioasignado field is not nullable.");
                }
            }
            for (Presupuestos presupuestosListOldPresupuestos : presupuestosListOld) {
                if (!presupuestosListNew.contains(presupuestosListOldPresupuestos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Presupuestos " + presupuestosListOldPresupuestos + " since its usuarioMante field is not nullable.");
                }
            }
            for (Visualizaciongrid visualizaciongridListOldVisualizaciongrid : visualizaciongridListOld) {
                if (!visualizaciongridListNew.contains(visualizaciongridListOldVisualizaciongrid)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Visualizaciongrid " + visualizaciongridListOldVisualizaciongrid + " since its usuario field is not nullable.");
                }
            }
            for (InfoModi infoModiListOldInfoModi : infoModiListOld) {
                if (!infoModiListNew.contains(infoModiListOldInfoModi)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InfoModi " + infoModiListOldInfoModi + " since its usuarioaplicacion field is not nullable.");
                }
            }
            for (Favorito favoritoListOldFavorito : favoritoListOld) {
                if (!favoritoListNew.contains(favoritoListOldFavorito)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Favorito " + favoritoListOldFavorito + " since its usuario1 field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarioRuedaNew != null) {
                usuarioRuedaNew = em.getReference(usuarioRuedaNew.getClass(), usuarioRuedaNew.getUsuariomante());
                usuario.setUsuarioRueda(usuarioRuedaNew);
            }
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getCliente());
                usuario.setCliente(clienteNew);
            }
            if (menuNew != null) {
                menuNew = em.getReference(menuNew.getClass(), menuNew.getNombre());
                usuario.setMenu(menuNew);
            }
            if (perfilNew != null) {
                perfilNew = em.getReference(perfilNew.getClass(), perfilNew.getPerfil());
                usuario.setPerfil(perfilNew);
            }
            if (ruedaPalexNew != null) {
                ruedaPalexNew = em.getReference(ruedaPalexNew.getClass(), ruedaPalexNew.getUsuario());
                usuario.setRuedaPalex(ruedaPalexNew);
            }
            List<Registroparte> attachedRegistroparteListNew = new ArrayList<Registroparte>();
            for (Registroparte registroparteListNewRegistroparteToAttach : registroparteListNew) {
                registroparteListNewRegistroparteToAttach = em.getReference(registroparteListNewRegistroparteToAttach.getClass(), registroparteListNewRegistroparteToAttach.getId());
                attachedRegistroparteListNew.add(registroparteListNewRegistroparteToAttach);
            }
            registroparteListNew = attachedRegistroparteListNew;
            usuario.setRegistroparteList(registroparteListNew);
            List<Cambioprograma> attachedCambioprogramaListNew = new ArrayList<Cambioprograma>();
            for (Cambioprograma cambioprogramaListNewCambioprogramaToAttach : cambioprogramaListNew) {
                cambioprogramaListNewCambioprogramaToAttach = em.getReference(cambioprogramaListNewCambioprogramaToAttach.getClass(), cambioprogramaListNewCambioprogramaToAttach.getIdcambio());
                attachedCambioprogramaListNew.add(cambioprogramaListNewCambioprogramaToAttach);
            }
            cambioprogramaListNew = attachedCambioprogramaListNew;
            usuario.setCambioprogramaList(cambioprogramaListNew);
            List<Responsabilidad> attachedResponsabilidadListNew = new ArrayList<Responsabilidad>();
            for (Responsabilidad responsabilidadListNewResponsabilidadToAttach : responsabilidadListNew) {
                responsabilidadListNewResponsabilidadToAttach = em.getReference(responsabilidadListNewResponsabilidadToAttach.getClass(), responsabilidadListNewResponsabilidadToAttach.getResponsabilidadPK());
                attachedResponsabilidadListNew.add(responsabilidadListNewResponsabilidadToAttach);
            }
            responsabilidadListNew = attachedResponsabilidadListNew;
            usuario.setResponsabilidadList(responsabilidadListNew);
            List<Guardiapalex> attachedGuardiapalexListNew = new ArrayList<Guardiapalex>();
            for (Guardiapalex guardiapalexListNewGuardiapalexToAttach : guardiapalexListNew) {
                guardiapalexListNewGuardiapalexToAttach = em.getReference(guardiapalexListNewGuardiapalexToAttach.getClass(), guardiapalexListNewGuardiapalexToAttach.getIdguardia());
                attachedGuardiapalexListNew.add(guardiapalexListNewGuardiapalexToAttach);
            }
            guardiapalexListNew = attachedGuardiapalexListNew;
            usuario.setGuardiapalexList(guardiapalexListNew);
            List<Guardiapalex> attachedGuardiapalexList1New = new ArrayList<Guardiapalex>();
            for (Guardiapalex guardiapalexList1NewGuardiapalexToAttach : guardiapalexList1New) {
                guardiapalexList1NewGuardiapalexToAttach = em.getReference(guardiapalexList1NewGuardiapalexToAttach.getClass(), guardiapalexList1NewGuardiapalexToAttach.getIdguardia());
                attachedGuardiapalexList1New.add(guardiapalexList1NewGuardiapalexToAttach);
            }
            guardiapalexList1New = attachedGuardiapalexList1New;
            usuario.setGuardiapalexList1(guardiapalexList1New);
            List<Pantallaini> attachedPantallainiListNew = new ArrayList<Pantallaini>();
            for (Pantallaini pantallainiListNewPantallainiToAttach : pantallainiListNew) {
                pantallainiListNewPantallainiToAttach = em.getReference(pantallainiListNewPantallainiToAttach.getClass(), pantallainiListNewPantallainiToAttach.getId());
                attachedPantallainiListNew.add(pantallainiListNewPantallainiToAttach);
            }
            pantallainiListNew = attachedPantallainiListNew;
            usuario.setPantallainiList(pantallainiListNew);
            List<UsuarioTarea> attachedUsuarioTareaListNew = new ArrayList<UsuarioTarea>();
            for (UsuarioTarea usuarioTareaListNewUsuarioTareaToAttach : usuarioTareaListNew) {
                usuarioTareaListNewUsuarioTareaToAttach = em.getReference(usuarioTareaListNewUsuarioTareaToAttach.getClass(), usuarioTareaListNewUsuarioTareaToAttach.getId());
                attachedUsuarioTareaListNew.add(usuarioTareaListNewUsuarioTareaToAttach);
            }
            usuarioTareaListNew = attachedUsuarioTareaListNew;
            usuario.setUsuarioTareaList(usuarioTareaListNew);
            List<UsuarioRueda> attachedUsuarioRuedaListNew = new ArrayList<UsuarioRueda>();
            for (UsuarioRueda usuarioRuedaListNewUsuarioRuedaToAttach : usuarioRuedaListNew) {
                usuarioRuedaListNewUsuarioRuedaToAttach = em.getReference(usuarioRuedaListNewUsuarioRuedaToAttach.getClass(), usuarioRuedaListNewUsuarioRuedaToAttach.getUsuariomante());
                attachedUsuarioRuedaListNew.add(usuarioRuedaListNewUsuarioRuedaToAttach);
            }
            usuarioRuedaListNew = attachedUsuarioRuedaListNew;
            usuario.setUsuarioRuedaList(usuarioRuedaListNew);
            List<Presupuestos> attachedPresupuestosListNew = new ArrayList<Presupuestos>();
            for (Presupuestos presupuestosListNewPresupuestosToAttach : presupuestosListNew) {
                presupuestosListNewPresupuestosToAttach = em.getReference(presupuestosListNewPresupuestosToAttach.getClass(), presupuestosListNewPresupuestosToAttach.getNum());
                attachedPresupuestosListNew.add(presupuestosListNewPresupuestosToAttach);
            }
            presupuestosListNew = attachedPresupuestosListNew;
            usuario.setPresupuestosList(presupuestosListNew);
            List<Visualizaciongrid> attachedVisualizaciongridListNew = new ArrayList<Visualizaciongrid>();
            for (Visualizaciongrid visualizaciongridListNewVisualizaciongridToAttach : visualizaciongridListNew) {
                visualizaciongridListNewVisualizaciongridToAttach = em.getReference(visualizaciongridListNewVisualizaciongridToAttach.getClass(), visualizaciongridListNewVisualizaciongridToAttach.getIdvisua());
                attachedVisualizaciongridListNew.add(visualizaciongridListNewVisualizaciongridToAttach);
            }
            visualizaciongridListNew = attachedVisualizaciongridListNew;
            usuario.setVisualizaciongridList(visualizaciongridListNew);
            List<InfoModi> attachedInfoModiListNew = new ArrayList<InfoModi>();
            for (InfoModi infoModiListNewInfoModiToAttach : infoModiListNew) {
                infoModiListNewInfoModiToAttach = em.getReference(infoModiListNewInfoModiToAttach.getClass(), infoModiListNewInfoModiToAttach.getInfoModiPK());
                attachedInfoModiListNew.add(infoModiListNewInfoModiToAttach);
            }
            infoModiListNew = attachedInfoModiListNew;
            usuario.setInfoModiList(infoModiListNew);
            List<Partes> attachedPartesListNew = new ArrayList<Partes>();
            for (Partes partesListNewPartesToAttach : partesListNew) {
                partesListNewPartesToAttach = em.getReference(partesListNewPartesToAttach.getClass(), partesListNewPartesToAttach.getNumParte());
                attachedPartesListNew.add(partesListNewPartesToAttach);
            }
            partesListNew = attachedPartesListNew;
            usuario.setPartesList(partesListNew);
            List<Tarea> attachedTareaListNew = new ArrayList<Tarea>();
            for (Tarea tareaListNewTareaToAttach : tareaListNew) {
                tareaListNewTareaToAttach = em.getReference(tareaListNewTareaToAttach.getClass(), tareaListNewTareaToAttach.getId());
                attachedTareaListNew.add(tareaListNewTareaToAttach);
            }
            tareaListNew = attachedTareaListNew;
            usuario.setTareaList(tareaListNew);
            List<Favorito> attachedFavoritoListNew = new ArrayList<Favorito>();
            for (Favorito favoritoListNewFavoritoToAttach : favoritoListNew) {
                favoritoListNewFavoritoToAttach = em.getReference(favoritoListNewFavoritoToAttach.getClass(), favoritoListNewFavoritoToAttach.getFavoritoPK());
                attachedFavoritoListNew.add(favoritoListNewFavoritoToAttach);
            }
            favoritoListNew = attachedFavoritoListNew;
            usuario.setFavoritoList(favoritoListNew);
            List<RegistroRevision> attachedRegistroRevisionListNew = new ArrayList<RegistroRevision>();
            for (RegistroRevision registroRevisionListNewRegistroRevisionToAttach : registroRevisionListNew) {
                registroRevisionListNewRegistroRevisionToAttach = em.getReference(registroRevisionListNewRegistroRevisionToAttach.getClass(), registroRevisionListNewRegistroRevisionToAttach.getId());
                attachedRegistroRevisionListNew.add(registroRevisionListNewRegistroRevisionToAttach);
            }
            registroRevisionListNew = attachedRegistroRevisionListNew;
            usuario.setRegistroRevisionList(registroRevisionListNew);
            usuario = em.merge(usuario);
            if (usuarioRuedaNew != null && !usuarioRuedaNew.equals(usuarioRuedaOld)) {
                Usuario oldUsuarioOfUsuarioRueda = usuarioRuedaNew.getUsuario();
                if (oldUsuarioOfUsuarioRueda != null) {
                    oldUsuarioOfUsuarioRueda.setUsuarioRueda(null);
                    oldUsuarioOfUsuarioRueda = em.merge(oldUsuarioOfUsuarioRueda);
                }
                usuarioRuedaNew.setUsuario(usuario);
                usuarioRuedaNew = em.merge(usuarioRuedaNew);
            }
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                clienteOld.getUsuarioList().remove(usuario);
                clienteOld = em.merge(clienteOld);
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                clienteNew.getUsuarioList().add(usuario);
                clienteNew = em.merge(clienteNew);
            }
            if (menuOld != null && !menuOld.equals(menuNew)) {
                menuOld.getUsuarioList().remove(usuario);
                menuOld = em.merge(menuOld);
            }
            if (menuNew != null && !menuNew.equals(menuOld)) {
                menuNew.getUsuarioList().add(usuario);
                menuNew = em.merge(menuNew);
            }
            if (perfilOld != null && !perfilOld.equals(perfilNew)) {
                perfilOld.getUsuarioList().remove(usuario);
                perfilOld = em.merge(perfilOld);
            }
            if (perfilNew != null && !perfilNew.equals(perfilOld)) {
                perfilNew.getUsuarioList().add(usuario);
                perfilNew = em.merge(perfilNew);
            }
            if (ruedaPalexNew != null && !ruedaPalexNew.equals(ruedaPalexOld)) {
                Usuario oldUsuario1OfRuedaPalex = ruedaPalexNew.getUsuario1();
                if (oldUsuario1OfRuedaPalex != null) {
                    oldUsuario1OfRuedaPalex.setRuedaPalex(null);
                    oldUsuario1OfRuedaPalex = em.merge(oldUsuario1OfRuedaPalex);
                }
                ruedaPalexNew.setUsuario1(usuario);
                ruedaPalexNew = em.merge(ruedaPalexNew);
            }
            for (Registroparte registroparteListOldRegistroparte : registroparteListOld) {
                if (!registroparteListNew.contains(registroparteListOldRegistroparte)) {
                    registroparteListOldRegistroparte.setUsuario(null);
                    registroparteListOldRegistroparte = em.merge(registroparteListOldRegistroparte);
                }
            }
            for (Registroparte registroparteListNewRegistroparte : registroparteListNew) {
                if (!registroparteListOld.contains(registroparteListNewRegistroparte)) {
                    Usuario oldUsuarioOfRegistroparteListNewRegistroparte = registroparteListNewRegistroparte.getUsuario();
                    registroparteListNewRegistroparte.setUsuario(usuario);
                    registroparteListNewRegistroparte = em.merge(registroparteListNewRegistroparte);
                    if (oldUsuarioOfRegistroparteListNewRegistroparte != null && !oldUsuarioOfRegistroparteListNewRegistroparte.equals(usuario)) {
                        oldUsuarioOfRegistroparteListNewRegistroparte.getRegistroparteList().remove(registroparteListNewRegistroparte);
                        oldUsuarioOfRegistroparteListNewRegistroparte = em.merge(oldUsuarioOfRegistroparteListNewRegistroparte);
                    }
                }
            }
            for (Cambioprograma cambioprogramaListNewCambioprograma : cambioprogramaListNew) {
                if (!cambioprogramaListOld.contains(cambioprogramaListNewCambioprograma)) {
                    Usuario oldUsuarioOfCambioprogramaListNewCambioprograma = cambioprogramaListNewCambioprograma.getUsuario();
                    cambioprogramaListNewCambioprograma.setUsuario(usuario);
                    cambioprogramaListNewCambioprograma = em.merge(cambioprogramaListNewCambioprograma);
                    if (oldUsuarioOfCambioprogramaListNewCambioprograma != null && !oldUsuarioOfCambioprogramaListNewCambioprograma.equals(usuario)) {
                        oldUsuarioOfCambioprogramaListNewCambioprograma.getCambioprogramaList().remove(cambioprogramaListNewCambioprograma);
                        oldUsuarioOfCambioprogramaListNewCambioprograma = em.merge(oldUsuarioOfCambioprogramaListNewCambioprograma);
                    }
                }
            }
            for (Responsabilidad responsabilidadListNewResponsabilidad : responsabilidadListNew) {
                if (!responsabilidadListOld.contains(responsabilidadListNewResponsabilidad)) {
                    Usuario oldUsuarioOfResponsabilidadListNewResponsabilidad = responsabilidadListNewResponsabilidad.getUsuario();
                    responsabilidadListNewResponsabilidad.setUsuario(usuario);
                    responsabilidadListNewResponsabilidad = em.merge(responsabilidadListNewResponsabilidad);
                    if (oldUsuarioOfResponsabilidadListNewResponsabilidad != null && !oldUsuarioOfResponsabilidadListNewResponsabilidad.equals(usuario)) {
                        oldUsuarioOfResponsabilidadListNewResponsabilidad.getResponsabilidadList().remove(responsabilidadListNewResponsabilidad);
                        oldUsuarioOfResponsabilidadListNewResponsabilidad = em.merge(oldUsuarioOfResponsabilidadListNewResponsabilidad);
                    }
                }
            }
            for (Guardiapalex guardiapalexListOldGuardiapalex : guardiapalexListOld) {
                if (!guardiapalexListNew.contains(guardiapalexListOldGuardiapalex)) {
                    guardiapalexListOldGuardiapalex.setTitular(null);
                    guardiapalexListOldGuardiapalex = em.merge(guardiapalexListOldGuardiapalex);
                }
            }
            for (Guardiapalex guardiapalexListNewGuardiapalex : guardiapalexListNew) {
                if (!guardiapalexListOld.contains(guardiapalexListNewGuardiapalex)) {
                    Usuario oldTitularOfGuardiapalexListNewGuardiapalex = guardiapalexListNewGuardiapalex.getTitular();
                    guardiapalexListNewGuardiapalex.setTitular(usuario);
                    guardiapalexListNewGuardiapalex = em.merge(guardiapalexListNewGuardiapalex);
                    if (oldTitularOfGuardiapalexListNewGuardiapalex != null && !oldTitularOfGuardiapalexListNewGuardiapalex.equals(usuario)) {
                        oldTitularOfGuardiapalexListNewGuardiapalex.getGuardiapalexList().remove(guardiapalexListNewGuardiapalex);
                        oldTitularOfGuardiapalexListNewGuardiapalex = em.merge(oldTitularOfGuardiapalexListNewGuardiapalex);
                    }
                }
            }
            for (Guardiapalex guardiapalexList1OldGuardiapalex : guardiapalexList1Old) {
                if (!guardiapalexList1New.contains(guardiapalexList1OldGuardiapalex)) {
                    guardiapalexList1OldGuardiapalex.setUsuarioReal(null);
                    guardiapalexList1OldGuardiapalex = em.merge(guardiapalexList1OldGuardiapalex);
                }
            }
            for (Guardiapalex guardiapalexList1NewGuardiapalex : guardiapalexList1New) {
                if (!guardiapalexList1Old.contains(guardiapalexList1NewGuardiapalex)) {
                    Usuario oldUsuarioRealOfGuardiapalexList1NewGuardiapalex = guardiapalexList1NewGuardiapalex.getUsuarioReal();
                    guardiapalexList1NewGuardiapalex.setUsuarioReal(usuario);
                    guardiapalexList1NewGuardiapalex = em.merge(guardiapalexList1NewGuardiapalex);
                    if (oldUsuarioRealOfGuardiapalexList1NewGuardiapalex != null && !oldUsuarioRealOfGuardiapalexList1NewGuardiapalex.equals(usuario)) {
                        oldUsuarioRealOfGuardiapalexList1NewGuardiapalex.getGuardiapalexList1().remove(guardiapalexList1NewGuardiapalex);
                        oldUsuarioRealOfGuardiapalexList1NewGuardiapalex = em.merge(oldUsuarioRealOfGuardiapalexList1NewGuardiapalex);
                    }
                }
            }
            for (Pantallaini pantallainiListNewPantallaini : pantallainiListNew) {
                if (!pantallainiListOld.contains(pantallainiListNewPantallaini)) {
                    Usuario oldUsuarioOfPantallainiListNewPantallaini = pantallainiListNewPantallaini.getUsuario();
                    pantallainiListNewPantallaini.setUsuario(usuario);
                    pantallainiListNewPantallaini = em.merge(pantallainiListNewPantallaini);
                    if (oldUsuarioOfPantallainiListNewPantallaini != null && !oldUsuarioOfPantallainiListNewPantallaini.equals(usuario)) {
                        oldUsuarioOfPantallainiListNewPantallaini.getPantallainiList().remove(pantallainiListNewPantallaini);
                        oldUsuarioOfPantallainiListNewPantallaini = em.merge(oldUsuarioOfPantallainiListNewPantallaini);
                    }
                }
            }
            for (UsuarioTarea usuarioTareaListNewUsuarioTarea : usuarioTareaListNew) {
                if (!usuarioTareaListOld.contains(usuarioTareaListNewUsuarioTarea)) {
                    Usuario oldUsuarioasignadoOfUsuarioTareaListNewUsuarioTarea = usuarioTareaListNewUsuarioTarea.getUsuarioasignado();
                    usuarioTareaListNewUsuarioTarea.setUsuarioasignado(usuario);
                    usuarioTareaListNewUsuarioTarea = em.merge(usuarioTareaListNewUsuarioTarea);
                    if (oldUsuarioasignadoOfUsuarioTareaListNewUsuarioTarea != null && !oldUsuarioasignadoOfUsuarioTareaListNewUsuarioTarea.equals(usuario)) {
                        oldUsuarioasignadoOfUsuarioTareaListNewUsuarioTarea.getUsuarioTareaList().remove(usuarioTareaListNewUsuarioTarea);
                        oldUsuarioasignadoOfUsuarioTareaListNewUsuarioTarea = em.merge(oldUsuarioasignadoOfUsuarioTareaListNewUsuarioTarea);
                    }
                }
            }
            for (UsuarioRueda usuarioRuedaListOldUsuarioRueda : usuarioRuedaListOld) {
                if (!usuarioRuedaListNew.contains(usuarioRuedaListOldUsuarioRueda)) {
                    usuarioRuedaListOldUsuarioRueda.setPareja(null);
                    usuarioRuedaListOldUsuarioRueda = em.merge(usuarioRuedaListOldUsuarioRueda);
                }
            }
            for (UsuarioRueda usuarioRuedaListNewUsuarioRueda : usuarioRuedaListNew) {
                if (!usuarioRuedaListOld.contains(usuarioRuedaListNewUsuarioRueda)) {
                    Usuario oldParejaOfUsuarioRuedaListNewUsuarioRueda = usuarioRuedaListNewUsuarioRueda.getPareja();
                    usuarioRuedaListNewUsuarioRueda.setPareja(usuario);
                    usuarioRuedaListNewUsuarioRueda = em.merge(usuarioRuedaListNewUsuarioRueda);
                    if (oldParejaOfUsuarioRuedaListNewUsuarioRueda != null && !oldParejaOfUsuarioRuedaListNewUsuarioRueda.equals(usuario)) {
                        oldParejaOfUsuarioRuedaListNewUsuarioRueda.getUsuarioRuedaList().remove(usuarioRuedaListNewUsuarioRueda);
                        oldParejaOfUsuarioRuedaListNewUsuarioRueda = em.merge(oldParejaOfUsuarioRuedaListNewUsuarioRueda);
                    }
                }
            }
            for (Presupuestos presupuestosListNewPresupuestos : presupuestosListNew) {
                if (!presupuestosListOld.contains(presupuestosListNewPresupuestos)) {
                    Usuario oldUsuarioManteOfPresupuestosListNewPresupuestos = presupuestosListNewPresupuestos.getUsuarioMante();
                    presupuestosListNewPresupuestos.setUsuarioMante(usuario);
                    presupuestosListNewPresupuestos = em.merge(presupuestosListNewPresupuestos);
                    if (oldUsuarioManteOfPresupuestosListNewPresupuestos != null && !oldUsuarioManteOfPresupuestosListNewPresupuestos.equals(usuario)) {
                        oldUsuarioManteOfPresupuestosListNewPresupuestos.getPresupuestosList().remove(presupuestosListNewPresupuestos);
                        oldUsuarioManteOfPresupuestosListNewPresupuestos = em.merge(oldUsuarioManteOfPresupuestosListNewPresupuestos);
                    }
                }
            }
            for (Visualizaciongrid visualizaciongridListNewVisualizaciongrid : visualizaciongridListNew) {
                if (!visualizaciongridListOld.contains(visualizaciongridListNewVisualizaciongrid)) {
                    Usuario oldUsuarioOfVisualizaciongridListNewVisualizaciongrid = visualizaciongridListNewVisualizaciongrid.getUsuario();
                    visualizaciongridListNewVisualizaciongrid.setUsuario(usuario);
                    visualizaciongridListNewVisualizaciongrid = em.merge(visualizaciongridListNewVisualizaciongrid);
                    if (oldUsuarioOfVisualizaciongridListNewVisualizaciongrid != null && !oldUsuarioOfVisualizaciongridListNewVisualizaciongrid.equals(usuario)) {
                        oldUsuarioOfVisualizaciongridListNewVisualizaciongrid.getVisualizaciongridList().remove(visualizaciongridListNewVisualizaciongrid);
                        oldUsuarioOfVisualizaciongridListNewVisualizaciongrid = em.merge(oldUsuarioOfVisualizaciongridListNewVisualizaciongrid);
                    }
                }
            }
            for (InfoModi infoModiListNewInfoModi : infoModiListNew) {
                if (!infoModiListOld.contains(infoModiListNewInfoModi)) {
                    Usuario oldUsuarioaplicacionOfInfoModiListNewInfoModi = infoModiListNewInfoModi.getUsuarioaplicacion();
                    infoModiListNewInfoModi.setUsuarioaplicacion(usuario);
                    infoModiListNewInfoModi = em.merge(infoModiListNewInfoModi);
                    if (oldUsuarioaplicacionOfInfoModiListNewInfoModi != null && !oldUsuarioaplicacionOfInfoModiListNewInfoModi.equals(usuario)) {
                        oldUsuarioaplicacionOfInfoModiListNewInfoModi.getInfoModiList().remove(infoModiListNewInfoModi);
                        oldUsuarioaplicacionOfInfoModiListNewInfoModi = em.merge(oldUsuarioaplicacionOfInfoModiListNewInfoModi);
                    }
                }
            }
            for (Partes partesListOldPartes : partesListOld) {
                if (!partesListNew.contains(partesListOldPartes)) {
                    partesListOldPartes.setUsuario(null);
                    partesListOldPartes = em.merge(partesListOldPartes);
                }
            }
            for (Partes partesListNewPartes : partesListNew) {
                if (!partesListOld.contains(partesListNewPartes)) {
                    Usuario oldUsuarioOfPartesListNewPartes = partesListNewPartes.getUsuario();
                    partesListNewPartes.setUsuario(usuario);
                    partesListNewPartes = em.merge(partesListNewPartes);
                    if (oldUsuarioOfPartesListNewPartes != null && !oldUsuarioOfPartesListNewPartes.equals(usuario)) {
                        oldUsuarioOfPartesListNewPartes.getPartesList().remove(partesListNewPartes);
                        oldUsuarioOfPartesListNewPartes = em.merge(oldUsuarioOfPartesListNewPartes);
                    }
                }
            }
            for (Tarea tareaListOldTarea : tareaListOld) {
                if (!tareaListNew.contains(tareaListOldTarea)) {
                    tareaListOldTarea.setUsuario(null);
                    tareaListOldTarea = em.merge(tareaListOldTarea);
                }
            }
            for (Tarea tareaListNewTarea : tareaListNew) {
                if (!tareaListOld.contains(tareaListNewTarea)) {
                    Usuario oldUsuarioOfTareaListNewTarea = tareaListNewTarea.getUsuario();
                    tareaListNewTarea.setUsuario(usuario);
                    tareaListNewTarea = em.merge(tareaListNewTarea);
                    if (oldUsuarioOfTareaListNewTarea != null && !oldUsuarioOfTareaListNewTarea.equals(usuario)) {
                        oldUsuarioOfTareaListNewTarea.getTareaList().remove(tareaListNewTarea);
                        oldUsuarioOfTareaListNewTarea = em.merge(oldUsuarioOfTareaListNewTarea);
                    }
                }
            }
            for (Favorito favoritoListNewFavorito : favoritoListNew) {
                if (!favoritoListOld.contains(favoritoListNewFavorito)) {
                    Usuario oldUsuario1OfFavoritoListNewFavorito = favoritoListNewFavorito.getUsuario1();
                    favoritoListNewFavorito.setUsuario1(usuario);
                    favoritoListNewFavorito = em.merge(favoritoListNewFavorito);
                    if (oldUsuario1OfFavoritoListNewFavorito != null && !oldUsuario1OfFavoritoListNewFavorito.equals(usuario)) {
                        oldUsuario1OfFavoritoListNewFavorito.getFavoritoList().remove(favoritoListNewFavorito);
                        oldUsuario1OfFavoritoListNewFavorito = em.merge(oldUsuario1OfFavoritoListNewFavorito);
                    }
                }
            }
            for (RegistroRevision registroRevisionListOldRegistroRevision : registroRevisionListOld) {
                if (!registroRevisionListNew.contains(registroRevisionListOldRegistroRevision)) {
                    registroRevisionListOldRegistroRevision.setUsuario(null);
                    registroRevisionListOldRegistroRevision = em.merge(registroRevisionListOldRegistroRevision);
                }
            }
            for (RegistroRevision registroRevisionListNewRegistroRevision : registroRevisionListNew) {
                if (!registroRevisionListOld.contains(registroRevisionListNewRegistroRevision)) {
                    Usuario oldUsuarioOfRegistroRevisionListNewRegistroRevision = registroRevisionListNewRegistroRevision.getUsuario();
                    registroRevisionListNewRegistroRevision.setUsuario(usuario);
                    registroRevisionListNewRegistroRevision = em.merge(registroRevisionListNewRegistroRevision);
                    if (oldUsuarioOfRegistroRevisionListNewRegistroRevision != null && !oldUsuarioOfRegistroRevisionListNewRegistroRevision.equals(usuario)) {
                        oldUsuarioOfRegistroRevisionListNewRegistroRevision.getRegistroRevisionList().remove(registroRevisionListNewRegistroRevision);
                        oldUsuarioOfRegistroRevisionListNewRegistroRevision = em.merge(oldUsuarioOfRegistroRevisionListNewRegistroRevision);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = usuario.getUsuario();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            UsuarioRueda usuarioRuedaOrphanCheck = usuario.getUsuarioRueda();
            if (usuarioRuedaOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the UsuarioRueda " + usuarioRuedaOrphanCheck + " in its usuarioRueda field has a non-nullable usuario field.");
            }
            RuedaPalex ruedaPalexOrphanCheck = usuario.getRuedaPalex();
            if (ruedaPalexOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the RuedaPalex " + ruedaPalexOrphanCheck + " in its ruedaPalex field has a non-nullable usuario1 field.");
            }
            List<Cambioprograma> cambioprogramaListOrphanCheck = usuario.getCambioprogramaList();
            for (Cambioprograma cambioprogramaListOrphanCheckCambioprograma : cambioprogramaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Cambioprograma " + cambioprogramaListOrphanCheckCambioprograma + " in its cambioprogramaList field has a non-nullable usuario field.");
            }
            List<Responsabilidad> responsabilidadListOrphanCheck = usuario.getResponsabilidadList();
            for (Responsabilidad responsabilidadListOrphanCheckResponsabilidad : responsabilidadListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Responsabilidad " + responsabilidadListOrphanCheckResponsabilidad + " in its responsabilidadList field has a non-nullable usuario field.");
            }
            List<Pantallaini> pantallainiListOrphanCheck = usuario.getPantallainiList();
            for (Pantallaini pantallainiListOrphanCheckPantallaini : pantallainiListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Pantallaini " + pantallainiListOrphanCheckPantallaini + " in its pantallainiList field has a non-nullable usuario field.");
            }
            List<UsuarioTarea> usuarioTareaListOrphanCheck = usuario.getUsuarioTareaList();
            for (UsuarioTarea usuarioTareaListOrphanCheckUsuarioTarea : usuarioTareaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the UsuarioTarea " + usuarioTareaListOrphanCheckUsuarioTarea + " in its usuarioTareaList field has a non-nullable usuarioasignado field.");
            }
            List<Presupuestos> presupuestosListOrphanCheck = usuario.getPresupuestosList();
            for (Presupuestos presupuestosListOrphanCheckPresupuestos : presupuestosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Presupuestos " + presupuestosListOrphanCheckPresupuestos + " in its presupuestosList field has a non-nullable usuarioMante field.");
            }
            List<Visualizaciongrid> visualizaciongridListOrphanCheck = usuario.getVisualizaciongridList();
            for (Visualizaciongrid visualizaciongridListOrphanCheckVisualizaciongrid : visualizaciongridListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Visualizaciongrid " + visualizaciongridListOrphanCheckVisualizaciongrid + " in its visualizaciongridList field has a non-nullable usuario field.");
            }
            List<InfoModi> infoModiListOrphanCheck = usuario.getInfoModiList();
            for (InfoModi infoModiListOrphanCheckInfoModi : infoModiListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the InfoModi " + infoModiListOrphanCheckInfoModi + " in its infoModiList field has a non-nullable usuarioaplicacion field.");
            }
            List<Favorito> favoritoListOrphanCheck = usuario.getFavoritoList();
            for (Favorito favoritoListOrphanCheckFavorito : favoritoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Favorito " + favoritoListOrphanCheckFavorito + " in its favoritoList field has a non-nullable usuario1 field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Clientes cliente = usuario.getCliente();
            if (cliente != null) {
                cliente.getUsuarioList().remove(usuario);
                cliente = em.merge(cliente);
            }
            Componentemenu menu = usuario.getMenu();
            if (menu != null) {
                menu.getUsuarioList().remove(usuario);
                menu = em.merge(menu);
            }
            Perfil perfil = usuario.getPerfil();
            if (perfil != null) {
                perfil.getUsuarioList().remove(usuario);
                perfil = em.merge(perfil);
            }
            List<Registroparte> registroparteList = usuario.getRegistroparteList();
            for (Registroparte registroparteListRegistroparte : registroparteList) {
                registroparteListRegistroparte.setUsuario(null);
                registroparteListRegistroparte = em.merge(registroparteListRegistroparte);
            }
            List<Guardiapalex> guardiapalexList = usuario.getGuardiapalexList();
            for (Guardiapalex guardiapalexListGuardiapalex : guardiapalexList) {
                guardiapalexListGuardiapalex.setTitular(null);
                guardiapalexListGuardiapalex = em.merge(guardiapalexListGuardiapalex);
            }
            List<Guardiapalex> guardiapalexList1 = usuario.getGuardiapalexList1();
            for (Guardiapalex guardiapalexList1Guardiapalex : guardiapalexList1) {
                guardiapalexList1Guardiapalex.setUsuarioReal(null);
                guardiapalexList1Guardiapalex = em.merge(guardiapalexList1Guardiapalex);
            }
            List<UsuarioRueda> usuarioRuedaList = usuario.getUsuarioRuedaList();
            for (UsuarioRueda usuarioRuedaListUsuarioRueda : usuarioRuedaList) {
                usuarioRuedaListUsuarioRueda.setPareja(null);
                usuarioRuedaListUsuarioRueda = em.merge(usuarioRuedaListUsuarioRueda);
            }
            List<Partes> partesList = usuario.getPartesList();
            for (Partes partesListPartes : partesList) {
                partesListPartes.setUsuario(null);
                partesListPartes = em.merge(partesListPartes);
            }
            List<Tarea> tareaList = usuario.getTareaList();
            for (Tarea tareaListTarea : tareaList) {
                tareaListTarea.setUsuario(null);
                tareaListTarea = em.merge(tareaListTarea);
            }
            List<RegistroRevision> registroRevisionList = usuario.getRegistroRevisionList();
            for (RegistroRevision registroRevisionListRegistroRevision : registroRevisionList) {
                registroRevisionListRegistroRevision.setUsuario(null);
                registroRevisionListRegistroRevision = em.merge(registroRevisionListRegistroRevision);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Usuario> obtenerUsuario( String usuario, String password ){
        
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery();
            Root root = cq.from(Usuario.class);
            cq.where(cb.equal( root.get("usuario"), usuario), cb.equal( root.get("password"), password) );
            Query query = em.createQuery(cq);
            return query.getResultList();  
        } finally {
            em.close();
        }
    }
    
}
