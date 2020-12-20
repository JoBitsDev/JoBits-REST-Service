/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobits.pos.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobits.pos.adapters.repo.impl.AreaDAO;
import com.jobits.pos.adapters.repo.impl.CocinaDAO;
import com.jobits.pos.persistence.Area;
import com.jobits.pos.persistence.Cocina;
import com.jobits.pos.persistence.Impresora;
import com.jobits.pos.printservice.Impresora;
import com.jobits.pos.printservice.ImpresoraRepo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.print.Doc;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;

/**
 *
 * @author ERIK QUESADA
 */
public class ImpresionController implements ImpresionService {

    private List<Impresora> impresoras;
    private ObjectMapper om = new ObjectMapper();
    private EntityManager em1;

    private ImpresoraRepo repository;

    public ImpresionController(ImpresoraRepo repo, EntityManager em1) {
        this.repository = repo;
        impresoras = repo.cargarImpresoras();
        this.em1 = em1;
    }

    private List<Impresora> getImpresorasAlmacenadas() {
        return repository.cargarImpresoras();
    }

    private void guardarImpresorasAlmacenadas() /*throws IOException*/ {
        repository.guardarImpresoras(impresoras);
    }

    public List<Impresora> impresoraMathCocina(String mathWithCocina) {
        List<Impresora> listaImpresoras = new ArrayList<>();

        /*  for(int i =0;i<impresoras.size();i++){
                for (int j=0;j<impresoras.get(i).getCocinasEnlazadas().size();j++){
                    if(impresoras.get(i).getCocinasEnlazadas().get(j).getNombreCocina().equals(mathWithCocina));
                        listaImpresoras.add(impresoras.get(i));
                }
            }
         */
        return listaImpresoras;
    }

    @Override
    public Impresora agregarImpresora(Impresora impresora) {
        impresoras = getImpresorasAlmacenadas();
        if (impresoras.isEmpty()) {
            impresora.setIdImpresora(0);
        } else {
            impresora.setIdImpresora(impresoras.get(impresoras.size() - 1).getIdImpresora() + 1);
        }
        impresoras.add(impresora);
        guardarImpresorasAlmacenadas();
        return impresora;
    }

       @Override
    public void updateImpresora(Impresora impresora) {
        impresoras = getImpresorasAlmacenadas();
        for (int i = 0; i < impresoras.size(); i++) {
            if (impresora.equals(impresoras.get(i))) {
                impresoras.set(i, impresora);
                guardarImpresorasAlmacenadas();
                break;
            }
        }
    }

    @Override
    public Impresora deleteImpresora(Impresora impresora) {

        if (repository.eliminarImpresora(impresora)) {
            return impresora;
        } else {
            throw new IllegalArgumentException();
        }

    }

    @Override
    public Impresora findBy(String codImpresora) {
        impresoras = getImpresorasAlmacenadas();
        Impresora impresoraEncontrada = null;

        for (Impresora listaImpresoras : impresoras) {
            if (listaImpresoras.getCodImpresora().equals(codImpresora)) {
                return impresoraEncontrada;
            }
        }
        return null;
    }
    @Override
    public List<Impresora> findAll() {
        List<Impresora> retSorted = getImpresorasAlmacenadas();
        Collections.sort(retSorted);
        return retSorted;
    }

    @Override
    public void imprimirEnGrupo(String nombreGrupo, Doc docToPrint) throws PrintException {
        List<Impresora> listaImpresoras = repository.cargarImpresoras();

        if (nombreGrupo == null) {
            imprimirPorDefault(docToPrint);
        } else {
            for (Impresora listaImpresora : listaImpresoras) {
                if (listaImpresora.getGrupo().equals(nombreGrupo)) {
                    listaImpresora.imprimir(docToPrint);
                }
            }
        }
    }

    private void imprimirPorDefault(Doc docToPrint) throws PrintException {
        for (Impresora impresora : getImpresorasDefault()) {
            impresora.imprimir(docToPrint);
        }
    }

    @Override
    public List<String> getNombreImpresorasSistema() {
        List<String> nombreImpresorasSistema = new ArrayList<>();
        List<PrintService> impresorasSistema = Arrays.asList(PrintServiceLookup.lookupPrintServices(null, null));

        for (PrintService printService : impresorasSistema) {
            nombreImpresorasSistema.add(printService.getName());

        }
        return nombreImpresorasSistema;

    }

    @Override
    public List<String> getNombreGrupos() {
        List<String> nombreGrupos = new ArrayList<>();
        List<Cocina> listaCocinas = CocinaDAO.getInstance().findAll();
        List<Area> listaAreas = AreaDAO.getInstance().findAll();
        for (Area listaArea : listaAreas) {
            nombreGrupos.add(listaArea.getNombre());
        }
        for (Cocina listaCocina : listaCocinas) {
            nombreGrupos.add(listaCocina.getNombreCocina());
        }
        return nombreGrupos;
    }

    public List<Impresora> getImpresorasDefault() {
        List<Impresora> listaImpresoras = new ArrayList<>();

        for (Impresora x : impresoras) {
            if (x.isPorDefecto()) {
                listaImpresoras.add(x);
            }
        }
        return listaImpresoras;
    }
}
