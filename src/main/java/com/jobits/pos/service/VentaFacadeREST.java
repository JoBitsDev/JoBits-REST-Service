/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobits.pos.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobits.pos.authentication.Secured;
import com.jobits.pos.persistence.Venta;
import com.jobits.pos.controllers.VentaResumenController;
import com.jobits.pos.persistence.Area;
import com.jobits.pos.persistence.Cocina;
import com.jobits.pos.persistence.Personal;
import com.jobits.pos.persistence.models.DetallesVentasModel;
import com.jobits.pos.persistence.models.VentaCalculator;
import com.jobits.pos.persistence.models.VentaResumenModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import com.jobits.utils.R;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.QueryParam;

/**
 * FirstDream
 *
 * @author Jorge
 *
 */
@Path("venta/")
public class VentaFacadeREST extends AbstractFacade<Venta> {

    @PersistenceContext(unitName = "Restaurant_Manager_Web_ServicePU")
    private EntityManager em;

    private Date d;
    SimpleDateFormat Format = new SimpleDateFormat("dd'/'MM'/'yy"),
            hour = new SimpleDateFormat(" hh ':' mm ' ' a ");

    public VentaFacadeREST() {
        super(Venta.class);
    }

    @RolesAllowed("3")
    @GET
    @Path("SALES-COUNT")
    @Secured
    public Response getResumenVentas(@QueryParam("fecha") String fecha) {
        ArrayList<Integer> ret = new ArrayList<>();
        try {
            List<Venta> ventas = findVentas(R.DATE_FORMAT.parse(fecha));
            if (ventas.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).
                        entity("No existe una venta registrada en la fecha seleccionada").build();
            }
            for (Venta v : ventas) {
                ret.add(v.getId());
            }
            return Response.ok(new ObjectMapper().
                    writeValueAsString(ret)).build();
        } catch (ParseException ex) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity("Formato de entrada incorrecto").build();
        } catch (JsonProcessingException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity("Error en el Object Mapper. Contacte con soporte").build();
        }

    }

    /**
     * <h3> Metodo para devolver el resumen general de las ventas de un dia en
     * especifico </h3>
     * este metodo devuelve un json que es necesario parsearlo metodo con nivel
     * 3 de seguridad
     *
     * @param fecha - la fecha que se pasa por parametro debe estar en el
     * formato  <h3>dd/mm/aaaa</h3>
     * @return un objeto de tipo {@link VentaResumenModel} convertido a json
     */
    @RolesAllowed("3")
    @GET
    @Path("SALES")
    @Secured
    public Response getResumenVentas(@QueryParam("idVenta") int idVenta) {
        Venta v;
        try {
            v = find(idVenta);
            if (v == null) {
                return Response.status(Response.Status.NOT_FOUND).
                        entity("No existe una venta registrada en la fecha seleccionada").build();
            }
            return Response.ok(new ObjectMapper().
                    writeValueAsString(VentaResumenController.
                            createResumenFromVenta(getCurrentTennantConnection(), v))).build();
        } catch (JsonProcessingException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity("Error en el Object Mapper. Contacte con soporte").build();
        }

    }

    @RolesAllowed("3")
    @GET
    @Path("DETALLES-POR-AREA")
    @Secured
    public Response getResumenPorArea(@QueryParam("idVenta") int idVenta, @QueryParam("areaCod") String area) {
            return toJsonString(Response.Status.OK,
                    DetallesVentasModel.createDetallesVentaFromEntity(
                            VentaCalculator.getResumenVentaPorArea(
                                    find(idVenta),
                                    getEntityManager().find(Area.class, area))));
    }

    @RolesAllowed("3")
    @GET
    @Path("DETALLES-POR-DEPENDIENTE")
    @Secured
    public Response getResumenPorDependiente(@QueryParam("idVenta") int idVenta,
            @QueryParam("usuario") String usuario) {
        return toJsonString(Response.Status.OK,
                DetallesVentasModel.createDetallesVentaFromEntity(
                        VentaCalculator.getResumenVentasCamarero(
                                find(idVenta),
                                getEntityManager().find(Personal.class,
                                        usuario))));

    }

    @RolesAllowed("3")
    @GET
    @Path("DETALLES-POR-COCINA")
    @Secured
    public Response getResumenPorCocina(
            @QueryParam("idVenta") int idVenta,
            @QueryParam("cocinaCod") String cocinaCod) {
        return toJsonString(Response.Status.OK,
                DetallesVentasModel.createDetallesVentaFromEntity(
                        VentaCalculator.getResumenVentasCocina(
                                find(idVenta),
                                getEntityManager().find(Cocina.class,
                                        cocinaCod))));

    }

    @RolesAllowed("3")
    @GET
    @Path("DETALLES-POR")
    @Secured
    public Response getResumenPorDependiente(@QueryParam("idVenta") int idVenta) {
        return toJsonString(Response.Status.OK,
                DetallesVentasModel.createDetallesVentaFromEntity(
                        VentaCalculator.getResumenVentas(
                                find(idVenta))));

    }

    public List<Venta> findVentas(Date fecha) {
        List<Venta> ret = new ArrayList<>(
                getEntityManager().createNamedQuery("Venta.findByFecha")
                        .setParameter("fecha", fecha)
                        .getResultList());
        return ret;
    }
}
