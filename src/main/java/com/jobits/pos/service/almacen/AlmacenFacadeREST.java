/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobits.pos.service.almacen;

import com.jobits.pos.controllers.InsumoController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobits.pos.authentication.Secured;
import com.jobits.pos.controller.almacen.AlmacenManageService;
import com.jobits.pos.controller.almacen.IPVService;
import com.jobits.pos.controller.almacen.TransaccionDetailService;
import com.jobits.pos.controller.insumo.InsumoDetailService;
import com.jobits.pos.controllers.AlmacenController;
import com.jobits.pos.core.domain.models.Almacen;
import com.jobits.pos.core.domain.models.Cocina;
import com.jobits.pos.core.domain.models.Insumo;
import com.jobits.pos.core.domain.models.InsumoAlmacen;
import com.jobits.pos.core.domain.models.InsumoElaborado;
import com.jobits.pos.core.domain.models.Ipv;
import com.jobits.pos.core.domain.models.Transaccion;
import com.jobits.pos.core.domain.models.TransaccionEntrada;
import com.jobits.pos.core.module.PosCoreModule;
import com.jobits.pos.core.domain.models.IpvRegistro;
import com.jobits.pos.core.domain.models.IpvRegistroPK;
import com.jobits.pos.core.domain.models.IpvVentaRegistro;
import com.jobits.pos.core.domain.models.TransaccionTransformacion;
import com.jobits.pos.persistence.models.TransformacionModel;
import com.jobits.pos.service.AbstractFacade;
import com.jobits.utils.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author Jorge
 */
@Path("almacen/")
public class AlmacenFacadeREST extends AbstractFacade<Almacen> {//TODO: aqui por defecto se usa el almacen 0...cambiar

    private EntityManager em;

    private final String PTO_ELAB = "ptoElab";

    private final AlmacenManageService almacenService = PosCoreModule.getInstance().getImplementation(AlmacenManageService.class);
    private final InsumoDetailService insumoService = PosCoreModule.getInstance().getImplementation(InsumoDetailService.class);
    private final TransaccionDetailService transaccionService = PosCoreModule.getInstance().getImplementation(TransaccionDetailService.class);
    private final IPVService ipvService = PosCoreModule.getInstance().getImplementation(IPVService.class);

    public AlmacenFacadeREST() {
        super(Almacen.class);
        em = super.getEntityManager();
    }

    @RolesAllowed("2")
    @Secured
    @GET
    public Response getPrimerAlmacen() {
        var list = almacenService.getItems();
        if (list.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No existe un almacen principal. por favor cree uno.").build();
        }
        var ret = almacenService.getInsumoAlmacenList(list.get(0));
        return toJsonString(Response.Status.OK, ret);
    }

    @RolesAllowed("2")
    @Secured
    @POST
    @Path("AGREGAR-INSUMO")
    public Response addIinsumo(String hashMap) {
        HashMap<String, Object> values;
        try {
            values = new ObjectMapper().readValue(hashMap, HashMap.class);
        } catch (JsonProcessingException ex) {
            return handleException(ex);
        }

        try {
            String insumoNombre = (String) values.get("insumoNombre");
            String um = (String) values.get("um");
            float estimacionStock = Float.parseFloat(values.get("estimacionStock").toString());
            startTransaction();
            InsumoController insController = new InsumoController(em);
            Insumo i = new Insumo();
            i.setNombre(insumoNombre);
            i.setUm(um);
            i.setStockEstimation(estimacionStock);
            if (insumoNombre.isEmpty() || um.isEmpty()) {
                return toJsonString(Response.Status.BAD_GATEWAY, "Valores vacios en nombre o unidad de medida");
            }
            if (estimacionStock < 0) {
                return toJsonString(Response.Status.BAD_REQUEST, "La estimacion del stock debe ser mayor que 0");
            }
            insumoService.create(i);
            almacenService.agregarInsumoAlmacen(i);

        } catch (NumberFormatException e) {
            return handleException(e);
        }
        return toJsonString(Response.Status.OK, "Operacion exitosa");
    }

    /**
     * Metodo que filtra los insumos del almacen principal por una cocina
     * especifica
     *
     * @param codCocina el codigo de la cocina a filtrar
     * @return la lista de {@link InsumoAlmacen} que contienen esa cocina
     */
    @RolesAllowed("2")
    @GET
    @Secured
    @Path("FILTRAR")
    public Response filterBy(@QueryParam(PTO_ELAB) String codCocina) {
        if (codCocina == null) {
            return toJsonString(Response.Status.BAD_REQUEST, "Peticion no válida");
        }
        var ret = almacenService.getInsumoAlmacenList(almacenService.getItems().get(0)).stream().filter((var t) -> {
            return t.getInsumo().getIpvList().stream().anyMatch(ipv -> (ipv.getCocina().getCodCocina().equals(codCocina)));
        }).collect(Collectors.toList());
        return toJsonString(Response.Status.OK, ret);
    }

    @RolesAllowed("2")
    @Secured
    @POST
    @Path("ENTRADA")
    public Response entrada(String hashMap) {
        HashMap<String, Object> values;
        try {
            values = new ObjectMapper().readValue(hashMap, HashMap.class);
        } catch (JsonProcessingException ex) {
            return handleException(ex);
        }
        String almacenCod = (String) values.get("almacenCod");
        String insumoCod = (String) values.get("insumoCod");
        float cant = Float.parseFloat(values.get("cantidad").toString());
        if (cant <= 0) {
            return toJsonString(Response.Status.BAD_REQUEST, "La cantidad de entrada no puede ser menor que 0");
        }
        float valor = Float.parseFloat(values.get("monto").toString());
        TransaccionEntrada newEntrada = transaccionService.addTransaccionEntrada(null, super.getEntityManager().find(Insumo.class, insumoCod),
                 new Date(), new Date(), almacenService.getItems().get(0), cant, valor);
        return toJsonString(Response.Status.OK, newEntrada.getTransaccion()); //TODO cambiar a 200
    }

    @RolesAllowed("2")
    @Secured
    @GET
    @Path("IMPRIMIR-ESTADO-ALMACEN")
    public Response ticketEntrada() {
        almacenService.imprimirResumenAlmacen(almacenService.getItems().get(0));
        return toJsonString(Response.Status.OK, "Impresion Exitosa");
    }

    @RolesAllowed("2")
    @Secured
    @GET
    @Path("IMPRIMIR-TICKET-COMPRA")
    public Response ticketCompra() {
        almacenService.imprimirReporteParaCompras(almacenService.getItems().get(0), 1);
        return toJsonString(Response.Status.OK, "Impresion Exitosa");
    }

    @RolesAllowed("2")
    @Secured
    @POST
    @Path("SALIDA")
    public Response salida(String hashMap) {
        HashMap<String, Object> params;
        try {
            params = new ObjectMapper().readValue(hashMap, HashMap.class);
            String almacenCod = (String) params.get("almacenCod");
            String insumoCod = (String) params.get("insumoCod");
            float cant = Float.parseFloat(params.get("cantidad").toString());
            if (cant <= 0) {
                return toJsonString(Response.Status.BAD_REQUEST, "La cantidad a dar salida no puede ser menor que 0");
            }
            String destino = (String) params.get("destino");
            Transaccion salida = transaccionService.addTransaccionSalida(null,
                    super.getEntityManager().find(Insumo.class, insumoCod), findVenta().getFecha(), new Date(),
                    super.find(almacenCod), super.getEntityManager().find(Cocina.class, destino), cant,-1);
            return toJsonString(Response.Status.OK, salida.getTransaccionSalida());
        } catch (JsonProcessingException | NumberFormatException | BadRequestException ex) {
            return handleException(ex);
        }

    }

    @RolesAllowed("2")
    @Secured
    @POST
    @Path("TRANSFORMAR")
    public Response transformacion(String listas) {
        return toJsonString(Response.Status.GONE, "Recurso no disponible");
//        try {
//            TransformacionModel model = new ObjectMapper().readValue(listas, TransformacionModel.class);
//            if (model.getEntradas().isEmpty() || model.getSalidas().isEmpty()) {
//                throw new BadRequestException("Las listas no pueden estar vacias");
//            }
//            if (model.getSalidas().size() > 1) {
//                throw new BadRequestException("La lista de salidas debe ser 1");
//            }
//
//            InsumoAlmacen salida = super.getEntityManager().find(InsumoAlmacen.class, model.getSalidas().get(0).getInsumoAlmacenPK());
//            super.getEntityManager().refresh(salida);
//            boolean derivanteValido = false;
//            for (InsumoAlmacen ia : model.getEntradas()) {
//                derivanteValido = false;
//                for (InsumoElaborado derivante : super.getEntityManager().find(Insumo.class, ia.getInsumo().getCodInsumo()).getInsumoDerivadoList()) {
//                    if (derivante.getInsumo_derivado_nombre().equals(salida.getInsumo())) {
//                        derivanteValido = true;
//                    }
//                }
//            }
//            if (!derivanteValido) {
//                throw new BadRequestException("Existe un insumo de salida no es derivante del insumo de entrada");
//            }
//            List<TransaccionTransformacion> aux = new ArrayList<>();
//            for (InsumoAlmacen entrada : model.getEntradas()) {
//                aux.add(transformInsumoAlmacen(entrada, 0));
//            }
//            getEntityManagerCon().getTransaction().begin();
//            new AlmacenController(getEntityManagerCon(), findAll().get(0)).crearTransformacion(salida, model.getSalidas().get(0).getCantidad(), aux, findAll().get(0));
//            if (getEntityManagerCon().getTransaction().isActive()) {
//                getEntityManagerCon().getTransaction().commit();
//            }
//
//            return toJsonString(Response.Status.OK, "Accion realizada exitosamente");
//        } catch (BadRequestException | IllegalArgumentException ex) {
//            return toJsonString(Response.Status.BAD_REQUEST, ex.getMessage());
//        } catch (Exception ex) {
//            return toJsonString(Response.Status.INTERNAL_SERVER_ERROR, ex.getMessage() + ex.getStackTrace()[0].toString());
//        }
    }

    @RolesAllowed("2")
    @Secured
    @GET
    @Path("IPVS-DE-INSUMO")
    public Response getIPVS(@QueryParam("insumoCod") String codInsumo) {
        ArrayList<Ipv> ipvs = new ArrayList<>(super.getEntityManager().createNamedQuery("Ipv.findByInsumocodInsumo")
                .setParameter("insumocodInsumo", codInsumo)
                .getResultList());
        List<String> cocinas = new ArrayList<>();
        for (Ipv ipv : ipvs) {
            cocinas.add(ipv.getCocina().getCodCocina());
        }
        return toJsonString(Response.Status.OK, cocinas);
    }

    @RolesAllowed("0")
    @Secured
    @GET
    @Path("REGISTRO-IPVS")
    public Response getRegistroIpvs(@QueryParam(PTO_ELAB) String puntoElaboracion) {
        ArrayList<IpvRegistro> aux = new ArrayList<>(
                super.getEntityManager().createNamedQuery("IpvRegistro.findByIpvcocinacodCocinaAndFecha")
                        .setParameter("ipvcocinacodCocina", puntoElaboracion)
                        .setParameter("fecha", findVenta().getFecha())
                        .getResultList());
        ArrayList<IpvRegistro> ret = new ArrayList<>();
        for (IpvRegistro x : aux) {
            if (x.getDisponible() != 0) {
                x.getIpvRegistroPK().setIpvinsumocodInsumo(x.getIpv().getInsumo().toString());
                ret.add(x);
            }
        }

        Collections.sort(ret, new Comparator<IpvRegistro>() {
            @Override
            public int compare(IpvRegistro o1, IpvRegistro o2) {
                return o1.getIpv().getInsumo().getNombre().compareTo(o2.getIpv().getInsumo().getNombre());
            }
        });
        return toJsonString(Response.Status.OK, ret);
    }

    @RolesAllowed("0")
    @Secured
    @GET
    @Path("REGISTRO-EXISTENCIAS")
    public Response getRegistroExistencias(@QueryParam(PTO_ELAB) String puntoElaboracion) {
        ArrayList<IpvVentaRegistro> aux = new ArrayList<>(
                super.getEntityManager().createNamedQuery("IpvVentaRegistro.findByPtoElab")
                        .setParameter("ptoElab", puntoElaboracion)
                        .setParameter("fecha", findVenta().getFecha())
                        .getResultList());

        ArrayList<IpvRegistro> ret = new ArrayList<>();
        for (IpvVentaRegistro x : aux) {
            if (x.getDisponible() != 0) {
               // ret.add(transform(x));
            }
        }
        Collections.sort(ret, new Comparator<IpvRegistro>() {
            @Override
            public int compare(IpvRegistro o1, IpvRegistro o2) {
                return o1.getIpv().getInsumo().getNombre().compareTo(o2.getIpv().getInsumo().getNombre());
            }
        });
        return toJsonString(Response.Status.OK, ret);
    }

    @RolesAllowed("2")
    @Secured
    @GET
    @Path("OPERACIONES-REALIZADAS")
    public Response getTransaccionList() {
        return toJsonString(Response.Status.OK, prepareTransacciones());
    }

    @RolesAllowed("2")
    @Secured
    @POST
    @Path("COMBINACIONES-CON")
    public Response getOperacionesCon(String listaInsumo) {
        return toJsonString(Response.Status.GONE, "Recurso deshabilitado");
//        List<InsumoAlmacen> aux = findAll().get(0).getInsumoAlmacenList(), ret = new ArrayList<>();
//        List<Insumo> admitidos = new ArrayList<>();
//        try {
//            ObjectMapper om = new ObjectMapper();
//            List<InsumoAlmacen> lista = new ObjectMapper().readValue(listaInsumo, om.getTypeFactory().constructCollectionType(List.class,
//                    InsumoAlmacen.class
//            ));
//
//            for (InsumoAlmacen i : lista) {
//                for (InsumoElaborado ie : getEntityManagerCon().find(Insumo.class,
//                        i.getInsumo().getCodInsumo()).getInsumoDerivadoList()) {
//                    admitidos.add(ie.getInsumo());
//                }
//            }
//            for (Insumo a : admitidos) {
//                for (InsumoAlmacen i : aux) {
//                    if (i.getInsumo().getCodInsumo().equals(a.getCodInsumo())) {
//                        i.setCantidad((float) 0);
//                        ret.add(i);
//                    }
//                }
//            }
//            return toJsonString(Response.Status.OK, ret);
//
//        } catch (Exception ex) {
//            Logger.getLogger(AlmacenFacadeREST.class
//                    .getName()).log(Level.SEVERE, null, ex);
//            return toJsonString(Response.Status.BAD_REQUEST, "La peticion se proceso incorrectamente " + ex.getMessage());
//        }
    }

    public List<Transaccion> prepareTransacciones() {
        List<Transaccion> ret = super.findAll(Transaccion.class
        );
        Collections.sort(ret, (Transaccion o1, Transaccion o2) -> {
            int comp = o1.getFecha().compareTo(o2.getFecha()) * -1;
            return comp == 0 ? o1.getHora().compareTo(o2.getHora()) * -1 : comp;
        });
        for (Transaccion t : ret) {
            if (t.getTransaccionEntrada() != null) {
                t.setDescripcion("ENTRADA: " + t.getTransaccionEntrada().getValorTotal() + R.getCoinSuffix());
            }
            if (t.getTransaccionMerma() != null) {
                t.setDescripcion(t.getTransaccionMerma().getRazon().toUpperCase());
            }
            if (t.getTransaccionSalida() != null) {
                t.setDescripcion("SALIDA: " + t.getTransaccionSalida().getCocinacodCocina());
            }
            if (t.getTransaccionTraspaso() != null) {
                t.setDescripcion("TRASPASO: " + t.getTransaccionTraspaso().getAlmacenDestino());
            }
            if (t.getTransaccionTransformacionList() != null) {
                if (!t.getTransaccionTransformacionList().isEmpty()) {
                    t.setDescripcion("TRANSFORMACION: ");
                }
            }
        }
        return ret;
    }

//    public IpvRegistro transform(IpvVentaRegistro registro) {
//        IpvRegistroPK pk = new IpvRegistroPK(
//                registro.getProductoVenta().getNombre(),
//                registro.getProductoVenta().getCocinacodCocina().getCodCocina(),
//                registro.getDiaVenta().getFecha().getDay());
//        IpvRegistro ret = new IpvRegistro(pk);
//        ret.setConsumo(registro.getVenta() + registro.getAutorizos());
//        ret.setDisponible(registro.getDisponible());
//        ret.setEntrada(registro.getEntrada());
//        ret.setFinalCalculado(registro.getFinal1());
//        ret.setInicio(registro.getInicio());
//        return ret;
//    }

//    public TransaccionTransformacion transformInsumoAlmacen(InsumoAlmacen selected, float cantidadUsada) {
//        AlmacenController controller = new AlmacenController(findAll().get(0));
//        TransaccionTransformacion nueva = new TransaccionTransformacion();
//        nueva.setCantidadCreada(selected.getCantidad());
//        nueva.setCantidadUsada(cantidadUsada);
//        nueva.setDireccionInversa(false);
//        nueva.setInsumo(controller.findInsumo(findAll().get(0).getCodAlmacen(), selected.getInsumo().getCodInsumo()).getInsumo());
//        return nueva;
//    }


}
