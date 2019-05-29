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
import com.seidor.jmanteweb.persistencia.entidades.Componentemenu;
import com.seidor.jmanteweb.persistencia.entidades.Cambioprograma;
import java.util.ArrayList;
import java.util.List;
import com.seidor.jmanteweb.persistencia.entidades.Objetovisual;
import com.seidor.jmanteweb.persistencia.entidades.Perfil;
import com.seidor.jmanteweb.persistencia.entidades.Pantallaini;
import com.seidor.jmanteweb.persistencia.entidades.Favorito;
import com.seidor.jmanteweb.persistencia.entidades.Ordencomponente;
import com.seidor.jmanteweb.persistencia.entidades.Usuario;
import com.seidor.jmanteweb.persistencia.entidades.Macro;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AMARTEL
 */
public class ComponentemenuJpaController implements Serializable {

    public ComponentemenuJpaController() {
        this.emf = Persistence.createEntityManagerFactory("JManteWebPersistencia");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Componentemenu componentemenu) throws PreexistingEntityException, Exception {
        if (componentemenu.getCambioprogramaList() == null) {
            componentemenu.setCambioprogramaList(new ArrayList<Cambioprograma>());
        }
        if (componentemenu.getObjetovisualList() == null) {
            componentemenu.setObjetovisualList(new ArrayList<Objetovisual>());
        }
        if (componentemenu.getPerfilList() == null) {
            componentemenu.setPerfilList(new ArrayList<Perfil>());
        }
        if (componentemenu.getPantallainiList() == null) {
            componentemenu.setPantallainiList(new ArrayList<Pantallaini>());
        }
        if (componentemenu.getFavoritoList() == null) {
            componentemenu.setFavoritoList(new ArrayList<Favorito>());
        }
        if (componentemenu.getComponentemenuList() == null) {
            componentemenu.setComponentemenuList(new ArrayList<Componentemenu>());
        }
        if (componentemenu.getOrdencomponenteList() == null) {
            componentemenu.setOrdencomponenteList(new ArrayList<Ordencomponente>());
        }
        if (componentemenu.getOrdencomponenteList1() == null) {
            componentemenu.setOrdencomponenteList1(new ArrayList<Ordencomponente>());
        }
        if (componentemenu.getUsuarioList() == null) {
            componentemenu.setUsuarioList(new ArrayList<Usuario>());
        }
        if (componentemenu.getMacroList() == null) {
            componentemenu.setMacroList(new ArrayList<Macro>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Componentemenu pantallapadre = componentemenu.getPantallapadre();
            if (pantallapadre != null) {
                pantallapadre = em.getReference(pantallapadre.getClass(), pantallapadre.getNombre());
                componentemenu.setPantallapadre(pantallapadre);
            }
            List<Cambioprograma> attachedCambioprogramaList = new ArrayList<Cambioprograma>();
            for (Cambioprograma cambioprogramaListCambioprogramaToAttach : componentemenu.getCambioprogramaList()) {
                cambioprogramaListCambioprogramaToAttach = em.getReference(cambioprogramaListCambioprogramaToAttach.getClass(), cambioprogramaListCambioprogramaToAttach.getIdcambio());
                attachedCambioprogramaList.add(cambioprogramaListCambioprogramaToAttach);
            }
            componentemenu.setCambioprogramaList(attachedCambioprogramaList);
            List<Objetovisual> attachedObjetovisualList = new ArrayList<Objetovisual>();
            for (Objetovisual objetovisualListObjetovisualToAttach : componentemenu.getObjetovisualList()) {
                objetovisualListObjetovisualToAttach = em.getReference(objetovisualListObjetovisualToAttach.getClass(), objetovisualListObjetovisualToAttach.getIdobjeto());
                attachedObjetovisualList.add(objetovisualListObjetovisualToAttach);
            }
            componentemenu.setObjetovisualList(attachedObjetovisualList);
            List<Perfil> attachedPerfilList = new ArrayList<Perfil>();
            for (Perfil perfilListPerfilToAttach : componentemenu.getPerfilList()) {
                perfilListPerfilToAttach = em.getReference(perfilListPerfilToAttach.getClass(), perfilListPerfilToAttach.getPerfil());
                attachedPerfilList.add(perfilListPerfilToAttach);
            }
            componentemenu.setPerfilList(attachedPerfilList);
            List<Pantallaini> attachedPantallainiList = new ArrayList<Pantallaini>();
            for (Pantallaini pantallainiListPantallainiToAttach : componentemenu.getPantallainiList()) {
                pantallainiListPantallainiToAttach = em.getReference(pantallainiListPantallainiToAttach.getClass(), pantallainiListPantallainiToAttach.getId());
                attachedPantallainiList.add(pantallainiListPantallainiToAttach);
            }
            componentemenu.setPantallainiList(attachedPantallainiList);
            List<Favorito> attachedFavoritoList = new ArrayList<Favorito>();
            for (Favorito favoritoListFavoritoToAttach : componentemenu.getFavoritoList()) {
                favoritoListFavoritoToAttach = em.getReference(favoritoListFavoritoToAttach.getClass(), favoritoListFavoritoToAttach.getFavoritoPK());
                attachedFavoritoList.add(favoritoListFavoritoToAttach);
            }
            componentemenu.setFavoritoList(attachedFavoritoList);
            List<Componentemenu> attachedComponentemenuList = new ArrayList<Componentemenu>();
            for (Componentemenu componentemenuListComponentemenuToAttach : componentemenu.getComponentemenuList()) {
                componentemenuListComponentemenuToAttach = em.getReference(componentemenuListComponentemenuToAttach.getClass(), componentemenuListComponentemenuToAttach.getNombre());
                attachedComponentemenuList.add(componentemenuListComponentemenuToAttach);
            }
            componentemenu.setComponentemenuList(attachedComponentemenuList);
            List<Ordencomponente> attachedOrdencomponenteList = new ArrayList<Ordencomponente>();
            for (Ordencomponente ordencomponenteListOrdencomponenteToAttach : componentemenu.getOrdencomponenteList()) {
                ordencomponenteListOrdencomponenteToAttach = em.getReference(ordencomponenteListOrdencomponenteToAttach.getClass(), ordencomponenteListOrdencomponenteToAttach.getOrdencomponentePK());
                attachedOrdencomponenteList.add(ordencomponenteListOrdencomponenteToAttach);
            }
            componentemenu.setOrdencomponenteList(attachedOrdencomponenteList);
            List<Ordencomponente> attachedOrdencomponenteList1 = new ArrayList<Ordencomponente>();
            for (Ordencomponente ordencomponenteList1OrdencomponenteToAttach : componentemenu.getOrdencomponenteList1()) {
                ordencomponenteList1OrdencomponenteToAttach = em.getReference(ordencomponenteList1OrdencomponenteToAttach.getClass(), ordencomponenteList1OrdencomponenteToAttach.getOrdencomponentePK());
                attachedOrdencomponenteList1.add(ordencomponenteList1OrdencomponenteToAttach);
            }
            componentemenu.setOrdencomponenteList1(attachedOrdencomponenteList1);
            List<Usuario> attachedUsuarioList = new ArrayList<Usuario>();
            for (Usuario usuarioListUsuarioToAttach : componentemenu.getUsuarioList()) {
                usuarioListUsuarioToAttach = em.getReference(usuarioListUsuarioToAttach.getClass(), usuarioListUsuarioToAttach.getUsuario());
                attachedUsuarioList.add(usuarioListUsuarioToAttach);
            }
            componentemenu.setUsuarioList(attachedUsuarioList);
            List<Macro> attachedMacroList = new ArrayList<Macro>();
            for (Macro macroListMacroToAttach : componentemenu.getMacroList()) {
                macroListMacroToAttach = em.getReference(macroListMacroToAttach.getClass(), macroListMacroToAttach.getMacro());
                attachedMacroList.add(macroListMacroToAttach);
            }
            componentemenu.setMacroList(attachedMacroList);
            em.persist(componentemenu);
            if (pantallapadre != null) {
                pantallapadre.getComponentemenuList().add(componentemenu);
                pantallapadre = em.merge(pantallapadre);
            }
            for (Cambioprograma cambioprogramaListCambioprograma : componentemenu.getCambioprogramaList()) {
                Componentemenu oldPantallaOfCambioprogramaListCambioprograma = cambioprogramaListCambioprograma.getPantalla();
                cambioprogramaListCambioprograma.setPantalla(componentemenu);
                cambioprogramaListCambioprograma = em.merge(cambioprogramaListCambioprograma);
                if (oldPantallaOfCambioprogramaListCambioprograma != null) {
                    oldPantallaOfCambioprogramaListCambioprograma.getCambioprogramaList().remove(cambioprogramaListCambioprograma);
                    oldPantallaOfCambioprogramaListCambioprograma = em.merge(oldPantallaOfCambioprogramaListCambioprograma);
                }
            }
            for (Objetovisual objetovisualListObjetovisual : componentemenu.getObjetovisualList()) {
                Componentemenu oldPantallaOfObjetovisualListObjetovisual = objetovisualListObjetovisual.getPantalla();
                objetovisualListObjetovisual.setPantalla(componentemenu);
                objetovisualListObjetovisual = em.merge(objetovisualListObjetovisual);
                if (oldPantallaOfObjetovisualListObjetovisual != null) {
                    oldPantallaOfObjetovisualListObjetovisual.getObjetovisualList().remove(objetovisualListObjetovisual);
                    oldPantallaOfObjetovisualListObjetovisual = em.merge(oldPantallaOfObjetovisualListObjetovisual);
                }
            }
            for (Perfil perfilListPerfil : componentemenu.getPerfilList()) {
                Componentemenu oldMenuprogramaOfPerfilListPerfil = perfilListPerfil.getMenuprograma();
                perfilListPerfil.setMenuprograma(componentemenu);
                perfilListPerfil = em.merge(perfilListPerfil);
                if (oldMenuprogramaOfPerfilListPerfil != null) {
                    oldMenuprogramaOfPerfilListPerfil.getPerfilList().remove(perfilListPerfil);
                    oldMenuprogramaOfPerfilListPerfil = em.merge(oldMenuprogramaOfPerfilListPerfil);
                }
            }
            for (Pantallaini pantallainiListPantallaini : componentemenu.getPantallainiList()) {
                Componentemenu oldComponenteOfPantallainiListPantallaini = pantallainiListPantallaini.getComponente();
                pantallainiListPantallaini.setComponente(componentemenu);
                pantallainiListPantallaini = em.merge(pantallainiListPantallaini);
                if (oldComponenteOfPantallainiListPantallaini != null) {
                    oldComponenteOfPantallainiListPantallaini.getPantallainiList().remove(pantallainiListPantallaini);
                    oldComponenteOfPantallainiListPantallaini = em.merge(oldComponenteOfPantallainiListPantallaini);
                }
            }
            for (Favorito favoritoListFavorito : componentemenu.getFavoritoList()) {
                Componentemenu oldComponentemenu1OfFavoritoListFavorito = favoritoListFavorito.getComponentemenu1();
                favoritoListFavorito.setComponentemenu1(componentemenu);
                favoritoListFavorito = em.merge(favoritoListFavorito);
                if (oldComponentemenu1OfFavoritoListFavorito != null) {
                    oldComponentemenu1OfFavoritoListFavorito.getFavoritoList().remove(favoritoListFavorito);
                    oldComponentemenu1OfFavoritoListFavorito = em.merge(oldComponentemenu1OfFavoritoListFavorito);
                }
            }
            for (Componentemenu componentemenuListComponentemenu : componentemenu.getComponentemenuList()) {
                Componentemenu oldPantallapadreOfComponentemenuListComponentemenu = componentemenuListComponentemenu.getPantallapadre();
                componentemenuListComponentemenu.setPantallapadre(componentemenu);
                componentemenuListComponentemenu = em.merge(componentemenuListComponentemenu);
                if (oldPantallapadreOfComponentemenuListComponentemenu != null) {
                    oldPantallapadreOfComponentemenuListComponentemenu.getComponentemenuList().remove(componentemenuListComponentemenu);
                    oldPantallapadreOfComponentemenuListComponentemenu = em.merge(oldPantallapadreOfComponentemenuListComponentemenu);
                }
            }
            for (Ordencomponente ordencomponenteListOrdencomponente : componentemenu.getOrdencomponenteList()) {
                Componentemenu oldComponentemenuOfOrdencomponenteListOrdencomponente = ordencomponenteListOrdencomponente.getComponentemenu();
                ordencomponenteListOrdencomponente.setComponentemenu(componentemenu);
                ordencomponenteListOrdencomponente = em.merge(ordencomponenteListOrdencomponente);
                if (oldComponentemenuOfOrdencomponenteListOrdencomponente != null) {
                    oldComponentemenuOfOrdencomponenteListOrdencomponente.getOrdencomponenteList().remove(ordencomponenteListOrdencomponente);
                    oldComponentemenuOfOrdencomponenteListOrdencomponente = em.merge(oldComponentemenuOfOrdencomponenteListOrdencomponente);
                }
            }
            for (Ordencomponente ordencomponenteList1Ordencomponente : componentemenu.getOrdencomponenteList1()) {
                Componentemenu oldComponentemenu1OfOrdencomponenteList1Ordencomponente = ordencomponenteList1Ordencomponente.getComponentemenu1();
                ordencomponenteList1Ordencomponente.setComponentemenu1(componentemenu);
                ordencomponenteList1Ordencomponente = em.merge(ordencomponenteList1Ordencomponente);
                if (oldComponentemenu1OfOrdencomponenteList1Ordencomponente != null) {
                    oldComponentemenu1OfOrdencomponenteList1Ordencomponente.getOrdencomponenteList1().remove(ordencomponenteList1Ordencomponente);
                    oldComponentemenu1OfOrdencomponenteList1Ordencomponente = em.merge(oldComponentemenu1OfOrdencomponenteList1Ordencomponente);
                }
            }
            for (Usuario usuarioListUsuario : componentemenu.getUsuarioList()) {
                Componentemenu oldMenuOfUsuarioListUsuario = usuarioListUsuario.getMenu();
                usuarioListUsuario.setMenu(componentemenu);
                usuarioListUsuario = em.merge(usuarioListUsuario);
                if (oldMenuOfUsuarioListUsuario != null) {
                    oldMenuOfUsuarioListUsuario.getUsuarioList().remove(usuarioListUsuario);
                    oldMenuOfUsuarioListUsuario = em.merge(oldMenuOfUsuarioListUsuario);
                }
            }
            for (Macro macroListMacro : componentemenu.getMacroList()) {
                Componentemenu oldPantallaOfMacroListMacro = macroListMacro.getPantalla();
                macroListMacro.setPantalla(componentemenu);
                macroListMacro = em.merge(macroListMacro);
                if (oldPantallaOfMacroListMacro != null) {
                    oldPantallaOfMacroListMacro.getMacroList().remove(macroListMacro);
                    oldPantallaOfMacroListMacro = em.merge(oldPantallaOfMacroListMacro);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findComponentemenu(componentemenu.getNombre()) != null) {
                throw new PreexistingEntityException("Componentemenu " + componentemenu + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Componentemenu componentemenu) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Componentemenu persistentComponentemenu = em.find(Componentemenu.class, componentemenu.getNombre());
            Componentemenu pantallapadreOld = persistentComponentemenu.getPantallapadre();
            Componentemenu pantallapadreNew = componentemenu.getPantallapadre();
            List<Cambioprograma> cambioprogramaListOld = persistentComponentemenu.getCambioprogramaList();
            List<Cambioprograma> cambioprogramaListNew = componentemenu.getCambioprogramaList();
            List<Objetovisual> objetovisualListOld = persistentComponentemenu.getObjetovisualList();
            List<Objetovisual> objetovisualListNew = componentemenu.getObjetovisualList();
            List<Perfil> perfilListOld = persistentComponentemenu.getPerfilList();
            List<Perfil> perfilListNew = componentemenu.getPerfilList();
            List<Pantallaini> pantallainiListOld = persistentComponentemenu.getPantallainiList();
            List<Pantallaini> pantallainiListNew = componentemenu.getPantallainiList();
            List<Favorito> favoritoListOld = persistentComponentemenu.getFavoritoList();
            List<Favorito> favoritoListNew = componentemenu.getFavoritoList();
            List<Componentemenu> componentemenuListOld = persistentComponentemenu.getComponentemenuList();
            List<Componentemenu> componentemenuListNew = componentemenu.getComponentemenuList();
            List<Ordencomponente> ordencomponenteListOld = persistentComponentemenu.getOrdencomponenteList();
            List<Ordencomponente> ordencomponenteListNew = componentemenu.getOrdencomponenteList();
            List<Ordencomponente> ordencomponenteList1Old = persistentComponentemenu.getOrdencomponenteList1();
            List<Ordencomponente> ordencomponenteList1New = componentemenu.getOrdencomponenteList1();
            List<Usuario> usuarioListOld = persistentComponentemenu.getUsuarioList();
            List<Usuario> usuarioListNew = componentemenu.getUsuarioList();
            List<Macro> macroListOld = persistentComponentemenu.getMacroList();
            List<Macro> macroListNew = componentemenu.getMacroList();
            List<String> illegalOrphanMessages = null;
            for (Cambioprograma cambioprogramaListOldCambioprograma : cambioprogramaListOld) {
                if (!cambioprogramaListNew.contains(cambioprogramaListOldCambioprograma)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cambioprograma " + cambioprogramaListOldCambioprograma + " since its pantalla field is not nullable.");
                }
            }
            for (Objetovisual objetovisualListOldObjetovisual : objetovisualListOld) {
                if (!objetovisualListNew.contains(objetovisualListOldObjetovisual)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Objetovisual " + objetovisualListOldObjetovisual + " since its pantalla field is not nullable.");
                }
            }
            for (Pantallaini pantallainiListOldPantallaini : pantallainiListOld) {
                if (!pantallainiListNew.contains(pantallainiListOldPantallaini)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pantallaini " + pantallainiListOldPantallaini + " since its componente field is not nullable.");
                }
            }
            for (Favorito favoritoListOldFavorito : favoritoListOld) {
                if (!favoritoListNew.contains(favoritoListOldFavorito)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Favorito " + favoritoListOldFavorito + " since its componentemenu1 field is not nullable.");
                }
            }
            for (Ordencomponente ordencomponenteListOldOrdencomponente : ordencomponenteListOld) {
                if (!ordencomponenteListNew.contains(ordencomponenteListOldOrdencomponente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ordencomponente " + ordencomponenteListOldOrdencomponente + " since its componentemenu field is not nullable.");
                }
            }
            for (Ordencomponente ordencomponenteList1OldOrdencomponente : ordencomponenteList1Old) {
                if (!ordencomponenteList1New.contains(ordencomponenteList1OldOrdencomponente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ordencomponente " + ordencomponenteList1OldOrdencomponente + " since its componentemenu1 field is not nullable.");
                }
            }
            for (Macro macroListOldMacro : macroListOld) {
                if (!macroListNew.contains(macroListOldMacro)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Macro " + macroListOldMacro + " since its pantalla field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (pantallapadreNew != null) {
                pantallapadreNew = em.getReference(pantallapadreNew.getClass(), pantallapadreNew.getNombre());
                componentemenu.setPantallapadre(pantallapadreNew);
            }
            List<Cambioprograma> attachedCambioprogramaListNew = new ArrayList<Cambioprograma>();
            for (Cambioprograma cambioprogramaListNewCambioprogramaToAttach : cambioprogramaListNew) {
                cambioprogramaListNewCambioprogramaToAttach = em.getReference(cambioprogramaListNewCambioprogramaToAttach.getClass(), cambioprogramaListNewCambioprogramaToAttach.getIdcambio());
                attachedCambioprogramaListNew.add(cambioprogramaListNewCambioprogramaToAttach);
            }
            cambioprogramaListNew = attachedCambioprogramaListNew;
            componentemenu.setCambioprogramaList(cambioprogramaListNew);
            List<Objetovisual> attachedObjetovisualListNew = new ArrayList<Objetovisual>();
            for (Objetovisual objetovisualListNewObjetovisualToAttach : objetovisualListNew) {
                objetovisualListNewObjetovisualToAttach = em.getReference(objetovisualListNewObjetovisualToAttach.getClass(), objetovisualListNewObjetovisualToAttach.getIdobjeto());
                attachedObjetovisualListNew.add(objetovisualListNewObjetovisualToAttach);
            }
            objetovisualListNew = attachedObjetovisualListNew;
            componentemenu.setObjetovisualList(objetovisualListNew);
            List<Perfil> attachedPerfilListNew = new ArrayList<Perfil>();
            for (Perfil perfilListNewPerfilToAttach : perfilListNew) {
                perfilListNewPerfilToAttach = em.getReference(perfilListNewPerfilToAttach.getClass(), perfilListNewPerfilToAttach.getPerfil());
                attachedPerfilListNew.add(perfilListNewPerfilToAttach);
            }
            perfilListNew = attachedPerfilListNew;
            componentemenu.setPerfilList(perfilListNew);
            List<Pantallaini> attachedPantallainiListNew = new ArrayList<Pantallaini>();
            for (Pantallaini pantallainiListNewPantallainiToAttach : pantallainiListNew) {
                pantallainiListNewPantallainiToAttach = em.getReference(pantallainiListNewPantallainiToAttach.getClass(), pantallainiListNewPantallainiToAttach.getId());
                attachedPantallainiListNew.add(pantallainiListNewPantallainiToAttach);
            }
            pantallainiListNew = attachedPantallainiListNew;
            componentemenu.setPantallainiList(pantallainiListNew);
            List<Favorito> attachedFavoritoListNew = new ArrayList<Favorito>();
            for (Favorito favoritoListNewFavoritoToAttach : favoritoListNew) {
                favoritoListNewFavoritoToAttach = em.getReference(favoritoListNewFavoritoToAttach.getClass(), favoritoListNewFavoritoToAttach.getFavoritoPK());
                attachedFavoritoListNew.add(favoritoListNewFavoritoToAttach);
            }
            favoritoListNew = attachedFavoritoListNew;
            componentemenu.setFavoritoList(favoritoListNew);
            List<Componentemenu> attachedComponentemenuListNew = new ArrayList<Componentemenu>();
            for (Componentemenu componentemenuListNewComponentemenuToAttach : componentemenuListNew) {
                componentemenuListNewComponentemenuToAttach = em.getReference(componentemenuListNewComponentemenuToAttach.getClass(), componentemenuListNewComponentemenuToAttach.getNombre());
                attachedComponentemenuListNew.add(componentemenuListNewComponentemenuToAttach);
            }
            componentemenuListNew = attachedComponentemenuListNew;
            componentemenu.setComponentemenuList(componentemenuListNew);
            List<Ordencomponente> attachedOrdencomponenteListNew = new ArrayList<Ordencomponente>();
            for (Ordencomponente ordencomponenteListNewOrdencomponenteToAttach : ordencomponenteListNew) {
                ordencomponenteListNewOrdencomponenteToAttach = em.getReference(ordencomponenteListNewOrdencomponenteToAttach.getClass(), ordencomponenteListNewOrdencomponenteToAttach.getOrdencomponentePK());
                attachedOrdencomponenteListNew.add(ordencomponenteListNewOrdencomponenteToAttach);
            }
            ordencomponenteListNew = attachedOrdencomponenteListNew;
            componentemenu.setOrdencomponenteList(ordencomponenteListNew);
            List<Ordencomponente> attachedOrdencomponenteList1New = new ArrayList<Ordencomponente>();
            for (Ordencomponente ordencomponenteList1NewOrdencomponenteToAttach : ordencomponenteList1New) {
                ordencomponenteList1NewOrdencomponenteToAttach = em.getReference(ordencomponenteList1NewOrdencomponenteToAttach.getClass(), ordencomponenteList1NewOrdencomponenteToAttach.getOrdencomponentePK());
                attachedOrdencomponenteList1New.add(ordencomponenteList1NewOrdencomponenteToAttach);
            }
            ordencomponenteList1New = attachedOrdencomponenteList1New;
            componentemenu.setOrdencomponenteList1(ordencomponenteList1New);
            List<Usuario> attachedUsuarioListNew = new ArrayList<Usuario>();
            for (Usuario usuarioListNewUsuarioToAttach : usuarioListNew) {
                usuarioListNewUsuarioToAttach = em.getReference(usuarioListNewUsuarioToAttach.getClass(), usuarioListNewUsuarioToAttach.getUsuario());
                attachedUsuarioListNew.add(usuarioListNewUsuarioToAttach);
            }
            usuarioListNew = attachedUsuarioListNew;
            componentemenu.setUsuarioList(usuarioListNew);
            List<Macro> attachedMacroListNew = new ArrayList<Macro>();
            for (Macro macroListNewMacroToAttach : macroListNew) {
                macroListNewMacroToAttach = em.getReference(macroListNewMacroToAttach.getClass(), macroListNewMacroToAttach.getMacro());
                attachedMacroListNew.add(macroListNewMacroToAttach);
            }
            macroListNew = attachedMacroListNew;
            componentemenu.setMacroList(macroListNew);
            componentemenu = em.merge(componentemenu);
            if (pantallapadreOld != null && !pantallapadreOld.equals(pantallapadreNew)) {
                pantallapadreOld.getComponentemenuList().remove(componentemenu);
                pantallapadreOld = em.merge(pantallapadreOld);
            }
            if (pantallapadreNew != null && !pantallapadreNew.equals(pantallapadreOld)) {
                pantallapadreNew.getComponentemenuList().add(componentemenu);
                pantallapadreNew = em.merge(pantallapadreNew);
            }
            for (Cambioprograma cambioprogramaListNewCambioprograma : cambioprogramaListNew) {
                if (!cambioprogramaListOld.contains(cambioprogramaListNewCambioprograma)) {
                    Componentemenu oldPantallaOfCambioprogramaListNewCambioprograma = cambioprogramaListNewCambioprograma.getPantalla();
                    cambioprogramaListNewCambioprograma.setPantalla(componentemenu);
                    cambioprogramaListNewCambioprograma = em.merge(cambioprogramaListNewCambioprograma);
                    if (oldPantallaOfCambioprogramaListNewCambioprograma != null && !oldPantallaOfCambioprogramaListNewCambioprograma.equals(componentemenu)) {
                        oldPantallaOfCambioprogramaListNewCambioprograma.getCambioprogramaList().remove(cambioprogramaListNewCambioprograma);
                        oldPantallaOfCambioprogramaListNewCambioprograma = em.merge(oldPantallaOfCambioprogramaListNewCambioprograma);
                    }
                }
            }
            for (Objetovisual objetovisualListNewObjetovisual : objetovisualListNew) {
                if (!objetovisualListOld.contains(objetovisualListNewObjetovisual)) {
                    Componentemenu oldPantallaOfObjetovisualListNewObjetovisual = objetovisualListNewObjetovisual.getPantalla();
                    objetovisualListNewObjetovisual.setPantalla(componentemenu);
                    objetovisualListNewObjetovisual = em.merge(objetovisualListNewObjetovisual);
                    if (oldPantallaOfObjetovisualListNewObjetovisual != null && !oldPantallaOfObjetovisualListNewObjetovisual.equals(componentemenu)) {
                        oldPantallaOfObjetovisualListNewObjetovisual.getObjetovisualList().remove(objetovisualListNewObjetovisual);
                        oldPantallaOfObjetovisualListNewObjetovisual = em.merge(oldPantallaOfObjetovisualListNewObjetovisual);
                    }
                }
            }
            for (Perfil perfilListOldPerfil : perfilListOld) {
                if (!perfilListNew.contains(perfilListOldPerfil)) {
                    perfilListOldPerfil.setMenuprograma(null);
                    perfilListOldPerfil = em.merge(perfilListOldPerfil);
                }
            }
            for (Perfil perfilListNewPerfil : perfilListNew) {
                if (!perfilListOld.contains(perfilListNewPerfil)) {
                    Componentemenu oldMenuprogramaOfPerfilListNewPerfil = perfilListNewPerfil.getMenuprograma();
                    perfilListNewPerfil.setMenuprograma(componentemenu);
                    perfilListNewPerfil = em.merge(perfilListNewPerfil);
                    if (oldMenuprogramaOfPerfilListNewPerfil != null && !oldMenuprogramaOfPerfilListNewPerfil.equals(componentemenu)) {
                        oldMenuprogramaOfPerfilListNewPerfil.getPerfilList().remove(perfilListNewPerfil);
                        oldMenuprogramaOfPerfilListNewPerfil = em.merge(oldMenuprogramaOfPerfilListNewPerfil);
                    }
                }
            }
            for (Pantallaini pantallainiListNewPantallaini : pantallainiListNew) {
                if (!pantallainiListOld.contains(pantallainiListNewPantallaini)) {
                    Componentemenu oldComponenteOfPantallainiListNewPantallaini = pantallainiListNewPantallaini.getComponente();
                    pantallainiListNewPantallaini.setComponente(componentemenu);
                    pantallainiListNewPantallaini = em.merge(pantallainiListNewPantallaini);
                    if (oldComponenteOfPantallainiListNewPantallaini != null && !oldComponenteOfPantallainiListNewPantallaini.equals(componentemenu)) {
                        oldComponenteOfPantallainiListNewPantallaini.getPantallainiList().remove(pantallainiListNewPantallaini);
                        oldComponenteOfPantallainiListNewPantallaini = em.merge(oldComponenteOfPantallainiListNewPantallaini);
                    }
                }
            }
            for (Favorito favoritoListNewFavorito : favoritoListNew) {
                if (!favoritoListOld.contains(favoritoListNewFavorito)) {
                    Componentemenu oldComponentemenu1OfFavoritoListNewFavorito = favoritoListNewFavorito.getComponentemenu1();
                    favoritoListNewFavorito.setComponentemenu1(componentemenu);
                    favoritoListNewFavorito = em.merge(favoritoListNewFavorito);
                    if (oldComponentemenu1OfFavoritoListNewFavorito != null && !oldComponentemenu1OfFavoritoListNewFavorito.equals(componentemenu)) {
                        oldComponentemenu1OfFavoritoListNewFavorito.getFavoritoList().remove(favoritoListNewFavorito);
                        oldComponentemenu1OfFavoritoListNewFavorito = em.merge(oldComponentemenu1OfFavoritoListNewFavorito);
                    }
                }
            }
            for (Componentemenu componentemenuListOldComponentemenu : componentemenuListOld) {
                if (!componentemenuListNew.contains(componentemenuListOldComponentemenu)) {
                    componentemenuListOldComponentemenu.setPantallapadre(null);
                    componentemenuListOldComponentemenu = em.merge(componentemenuListOldComponentemenu);
                }
            }
            for (Componentemenu componentemenuListNewComponentemenu : componentemenuListNew) {
                if (!componentemenuListOld.contains(componentemenuListNewComponentemenu)) {
                    Componentemenu oldPantallapadreOfComponentemenuListNewComponentemenu = componentemenuListNewComponentemenu.getPantallapadre();
                    componentemenuListNewComponentemenu.setPantallapadre(componentemenu);
                    componentemenuListNewComponentemenu = em.merge(componentemenuListNewComponentemenu);
                    if (oldPantallapadreOfComponentemenuListNewComponentemenu != null && !oldPantallapadreOfComponentemenuListNewComponentemenu.equals(componentemenu)) {
                        oldPantallapadreOfComponentemenuListNewComponentemenu.getComponentemenuList().remove(componentemenuListNewComponentemenu);
                        oldPantallapadreOfComponentemenuListNewComponentemenu = em.merge(oldPantallapadreOfComponentemenuListNewComponentemenu);
                    }
                }
            }
            for (Ordencomponente ordencomponenteListNewOrdencomponente : ordencomponenteListNew) {
                if (!ordencomponenteListOld.contains(ordencomponenteListNewOrdencomponente)) {
                    Componentemenu oldComponentemenuOfOrdencomponenteListNewOrdencomponente = ordencomponenteListNewOrdencomponente.getComponentemenu();
                    ordencomponenteListNewOrdencomponente.setComponentemenu(componentemenu);
                    ordencomponenteListNewOrdencomponente = em.merge(ordencomponenteListNewOrdencomponente);
                    if (oldComponentemenuOfOrdencomponenteListNewOrdencomponente != null && !oldComponentemenuOfOrdencomponenteListNewOrdencomponente.equals(componentemenu)) {
                        oldComponentemenuOfOrdencomponenteListNewOrdencomponente.getOrdencomponenteList().remove(ordencomponenteListNewOrdencomponente);
                        oldComponentemenuOfOrdencomponenteListNewOrdencomponente = em.merge(oldComponentemenuOfOrdencomponenteListNewOrdencomponente);
                    }
                }
            }
            for (Ordencomponente ordencomponenteList1NewOrdencomponente : ordencomponenteList1New) {
                if (!ordencomponenteList1Old.contains(ordencomponenteList1NewOrdencomponente)) {
                    Componentemenu oldComponentemenu1OfOrdencomponenteList1NewOrdencomponente = ordencomponenteList1NewOrdencomponente.getComponentemenu1();
                    ordencomponenteList1NewOrdencomponente.setComponentemenu1(componentemenu);
                    ordencomponenteList1NewOrdencomponente = em.merge(ordencomponenteList1NewOrdencomponente);
                    if (oldComponentemenu1OfOrdencomponenteList1NewOrdencomponente != null && !oldComponentemenu1OfOrdencomponenteList1NewOrdencomponente.equals(componentemenu)) {
                        oldComponentemenu1OfOrdencomponenteList1NewOrdencomponente.getOrdencomponenteList1().remove(ordencomponenteList1NewOrdencomponente);
                        oldComponentemenu1OfOrdencomponenteList1NewOrdencomponente = em.merge(oldComponentemenu1OfOrdencomponenteList1NewOrdencomponente);
                    }
                }
            }
            for (Usuario usuarioListOldUsuario : usuarioListOld) {
                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
                    usuarioListOldUsuario.setMenu(null);
                    usuarioListOldUsuario = em.merge(usuarioListOldUsuario);
                }
            }
            for (Usuario usuarioListNewUsuario : usuarioListNew) {
                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
                    Componentemenu oldMenuOfUsuarioListNewUsuario = usuarioListNewUsuario.getMenu();
                    usuarioListNewUsuario.setMenu(componentemenu);
                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
                    if (oldMenuOfUsuarioListNewUsuario != null && !oldMenuOfUsuarioListNewUsuario.equals(componentemenu)) {
                        oldMenuOfUsuarioListNewUsuario.getUsuarioList().remove(usuarioListNewUsuario);
                        oldMenuOfUsuarioListNewUsuario = em.merge(oldMenuOfUsuarioListNewUsuario);
                    }
                }
            }
            for (Macro macroListNewMacro : macroListNew) {
                if (!macroListOld.contains(macroListNewMacro)) {
                    Componentemenu oldPantallaOfMacroListNewMacro = macroListNewMacro.getPantalla();
                    macroListNewMacro.setPantalla(componentemenu);
                    macroListNewMacro = em.merge(macroListNewMacro);
                    if (oldPantallaOfMacroListNewMacro != null && !oldPantallaOfMacroListNewMacro.equals(componentemenu)) {
                        oldPantallaOfMacroListNewMacro.getMacroList().remove(macroListNewMacro);
                        oldPantallaOfMacroListNewMacro = em.merge(oldPantallaOfMacroListNewMacro);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = componentemenu.getNombre();
                if (findComponentemenu(id) == null) {
                    throw new NonexistentEntityException("The componentemenu with id " + id + " no longer exists.");
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
            Componentemenu componentemenu;
            try {
                componentemenu = em.getReference(Componentemenu.class, id);
                componentemenu.getNombre();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The componentemenu with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cambioprograma> cambioprogramaListOrphanCheck = componentemenu.getCambioprogramaList();
            for (Cambioprograma cambioprogramaListOrphanCheckCambioprograma : cambioprogramaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Componentemenu (" + componentemenu + ") cannot be destroyed since the Cambioprograma " + cambioprogramaListOrphanCheckCambioprograma + " in its cambioprogramaList field has a non-nullable pantalla field.");
            }
            List<Objetovisual> objetovisualListOrphanCheck = componentemenu.getObjetovisualList();
            for (Objetovisual objetovisualListOrphanCheckObjetovisual : objetovisualListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Componentemenu (" + componentemenu + ") cannot be destroyed since the Objetovisual " + objetovisualListOrphanCheckObjetovisual + " in its objetovisualList field has a non-nullable pantalla field.");
            }
            List<Pantallaini> pantallainiListOrphanCheck = componentemenu.getPantallainiList();
            for (Pantallaini pantallainiListOrphanCheckPantallaini : pantallainiListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Componentemenu (" + componentemenu + ") cannot be destroyed since the Pantallaini " + pantallainiListOrphanCheckPantallaini + " in its pantallainiList field has a non-nullable componente field.");
            }
            List<Favorito> favoritoListOrphanCheck = componentemenu.getFavoritoList();
            for (Favorito favoritoListOrphanCheckFavorito : favoritoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Componentemenu (" + componentemenu + ") cannot be destroyed since the Favorito " + favoritoListOrphanCheckFavorito + " in its favoritoList field has a non-nullable componentemenu1 field.");
            }
            List<Ordencomponente> ordencomponenteListOrphanCheck = componentemenu.getOrdencomponenteList();
            for (Ordencomponente ordencomponenteListOrphanCheckOrdencomponente : ordencomponenteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Componentemenu (" + componentemenu + ") cannot be destroyed since the Ordencomponente " + ordencomponenteListOrphanCheckOrdencomponente + " in its ordencomponenteList field has a non-nullable componentemenu field.");
            }
            List<Ordencomponente> ordencomponenteList1OrphanCheck = componentemenu.getOrdencomponenteList1();
            for (Ordencomponente ordencomponenteList1OrphanCheckOrdencomponente : ordencomponenteList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Componentemenu (" + componentemenu + ") cannot be destroyed since the Ordencomponente " + ordencomponenteList1OrphanCheckOrdencomponente + " in its ordencomponenteList1 field has a non-nullable componentemenu1 field.");
            }
            List<Macro> macroListOrphanCheck = componentemenu.getMacroList();
            for (Macro macroListOrphanCheckMacro : macroListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Componentemenu (" + componentemenu + ") cannot be destroyed since the Macro " + macroListOrphanCheckMacro + " in its macroList field has a non-nullable pantalla field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Componentemenu pantallapadre = componentemenu.getPantallapadre();
            if (pantallapadre != null) {
                pantallapadre.getComponentemenuList().remove(componentemenu);
                pantallapadre = em.merge(pantallapadre);
            }
            List<Perfil> perfilList = componentemenu.getPerfilList();
            for (Perfil perfilListPerfil : perfilList) {
                perfilListPerfil.setMenuprograma(null);
                perfilListPerfil = em.merge(perfilListPerfil);
            }
            List<Componentemenu> componentemenuList = componentemenu.getComponentemenuList();
            for (Componentemenu componentemenuListComponentemenu : componentemenuList) {
                componentemenuListComponentemenu.setPantallapadre(null);
                componentemenuListComponentemenu = em.merge(componentemenuListComponentemenu);
            }
            List<Usuario> usuarioList = componentemenu.getUsuarioList();
            for (Usuario usuarioListUsuario : usuarioList) {
                usuarioListUsuario.setMenu(null);
                usuarioListUsuario = em.merge(usuarioListUsuario);
            }
            em.remove(componentemenu);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Componentemenu> findComponentemenuEntities() {
        return findComponentemenuEntities(true, -1, -1);
    }

    public List<Componentemenu> findComponentemenuEntities(int maxResults, int firstResult) {
        return findComponentemenuEntities(false, maxResults, firstResult);
    }

    private List<Componentemenu> findComponentemenuEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Componentemenu.class));
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

    public Componentemenu findComponentemenu(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Componentemenu.class, id);
        } finally {
            em.close();
        }
    }

    public int getComponentemenuCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Componentemenu> rt = cq.from(Componentemenu.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
