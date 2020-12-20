/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobits.pos.printservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobits.pos.exceptionshandlers.ExceptionHandler;
import com.jobits.pos.persistence.Impresora;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;

public class ImpresoraRepoImpl implements ImpresoraRepo {

    private String FILE_NAME = "impresoras.json";
    private ObjectMapper om = new ObjectMapper();
    private EntityManager em1;

    public ImpresoraRepoImpl(EntityManager em1) {
        this.em1 = em1;
    }

    @Override
    public boolean eliminarImpresora(Impresora impresoraToDelete) {
        List<Impresora> lista = cargarImpresoras();
        boolean b = lista.remove(impresoraToDelete);
        guardarImpresoras(lista);
        return b;
    }

    @Override
    public List<Impresora> cargarImpresoras() {
        em1.getEntityManagerFactory().getCache().evictAll();
        em1.close();
        em1 = em1.getEntityManagerFactory().createEntityManager();
        javax.persistence.criteria.CriteriaQuery cq = em1.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Impresora.class));
        return em1.createQuery(cq).getResultList();
    }

    @Override
    public boolean guardarImpresoras(List<Impresora> listaAGuardar) {
        
        
        return true;
    }
}
