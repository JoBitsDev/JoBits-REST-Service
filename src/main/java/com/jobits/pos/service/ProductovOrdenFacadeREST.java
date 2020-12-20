/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jobits.pos.service;

import com.jobits.pos.persistence.ProductovOrden;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * FirstDream
 * @author Jorge
 * 
 */
 
@Path("productovorden/")
public class ProductovOrdenFacadeREST extends AbstractFacade<ProductovOrden> {

    @PersistenceContext(unitName = "Restaurant_Manager_Web_ServicePU")
    private EntityManager em;


    public ProductovOrdenFacadeREST() {
        super(ProductovOrden.class);
    }

  
    @GET
    @Path("FIND_{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<ProductovOrden> findList(@PathParam("id") String id) {
        List<ProductovOrden> l= super.findAll();
        List<ProductovOrden> ret = new ArrayList();
        l.stream().filter((x) -> 
                (x.getOrden().getCodOrden().equals(id))).forEachOrdered((x) -> {
            ret.add(x);
        });
       return ret; 
        
       
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<ProductovOrden> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<ProductovOrden> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
