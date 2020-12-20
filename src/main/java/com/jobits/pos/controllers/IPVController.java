/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobits.pos.controllers;

import com.jobits.pos.persistence.Insumo;
import com.jobits.pos.persistence.ProductovOrden;
import com.jobits.pos.persistence.Cocina;
import com.jobits.pos.persistence.ProductoInsumo;
import com.jobits.pos.persistence.IpvRegistroPK;
import com.jobits.pos.persistence.IpvVentaRegistroPK;
import com.jobits.pos.persistence.ProductoVenta;
import com.jobits.pos.persistence.IpvRegistro;
import com.jobits.pos.persistence.IpvVentaRegistro;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

/**
 * FirstDream
 *
 * @author Jorge
 *
 */
public class IPVController extends AbstractController {

    public IPVController(EntityManager em1) {
        super(em1);
    }

    public int getRestantes(String codProducto, Date fecha) {
        ProductoVenta producto = em1.find(ProductoVenta.class, codProducto);
        int cantidadMinima = Integer.MAX_VALUE;
        for (ProductoInsumo x : producto.getProductoInsumoList()) {
            IpvRegistroPK pk = new IpvRegistroPK(x.getInsumo().getCodInsumo(), producto.getCocinacodCocina().getCodCocina(), fecha);
            IpvRegistro registro = em1.find(IpvRegistro.class, pk);
            if (registro != null) {
                int aux = (int) (registro.getDisponible() / x.getCantidad());
                if (aux < cantidadMinima) {
                    cantidadMinima = aux;
                }
            }
        }
        return cantidadMinima == Integer.MAX_VALUE ? 0 : cantidadMinima;
    }

    public void updateInstance(IpvRegistro instance) {
        if (instance.getEntrada() == null) {
            instance.setEntrada((float) 0);
        }
        if (instance.getInicio() == null) {
            instance.setInicio((float) 0);
        }
        if (instance.getConsumo() == null) {
            instance.setConsumo((float) 0);
        }
        if (instance.getConsumoReal() == null) {
            instance.setConsumoReal((float) 0);
        }
        if (instance.getFinalCalculado() == null) {
            instance.setFinalCalculado((float) 0);
        }
        if (instance.getFinalAjustado() == null) {
            instance.setFinalAjustado((float) 0);
        }
        if (instance.getDisponible() == null) {
            instance.setDisponible((float) 0);
        }
        getEntityManager().getTransaction().begin();
        getEntityManager().merge(instance);
        getEntityManager().getTransaction().commit();
    }

    public void updateInstance(IpvVentaRegistro instance) {
        if (instance.getEntrada() == null) {
            instance.setEntrada((float) 0);
        }
        if (instance.getInicio() == null) {
            instance.setInicio((float) 0);
        }
        if (instance.getVenta() == null) {
            instance.setVenta((float) 0);
        }
        if (instance.getFinal1() == null) {
            instance.setFinal1((float) 0);
        }
        if (instance.getDisponible() == null) {
            instance.setDisponible((float) 0);
        }
        if (instance.getAutorizos() == null) {
            instance.setAutorizos((float) 0);
        }
        getEntityManager().getTransaction().begin();
        getEntityManager().merge(instance);
        getEntityManager().getTransaction().commit();
    }

    public List<Date> getIpvRegistroList(Cocina cocina) {
        return getEntityManager().createNamedQuery("IpvRegistro.findByIpvcocinacodCocina")
                .setParameter("ipvcocinacodCocina", cocina.getCodCocina())
                .getResultList();
    }

    public List<IpvRegistro> getIpvRegistroList(Cocina cocina, Date fecha) {
        getEntityManager().getEntityManagerFactory().getCache().evict(IpvRegistro.class);
        return new ArrayList<>(getEntityManager().createNamedQuery("IpvRegistro.findByIpvcocinacodCocinaAndFecha")
                .setParameter("ipvcocinacodCocina", cocina.getCodCocina())
                .setParameter("fecha", fecha)
                .getResultList());

    }

    public IpvRegistro getIpvRegistro(Cocina c, Date fecha, Insumo i) throws NoResultException, PersistenceException {
        try {
            return (IpvRegistro) getEntityManager().createNamedQuery("IpvRegistro.findByIpvcocinacodCocinaAndFechaAndInsumo")
                    .setParameter("ipvcocinacodCocina", c.getCodCocina())
                    .setParameter("fecha", fecha)
                    .setParameter("codinsumo", i.getCodInsumo())
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }

    public boolean hayDisponibilidad(ProductoVenta selected, Date fecha, float cantidad) {
        for (ProductoInsumo insumo : selected.getProductoInsumoList()) {
            try {
                IpvRegistro ipv = getIpvRegistro(selected.getCocinacodCocina(), fecha, insumo.getInsumo());
                float f = ipv.getConsumo() + insumo.getCantidad() * cantidad;
                if (f > ipv.getDisponible()) {
                    selected.setVisible(false);
                    getEntityManager().getTransaction().begin();
                    getEntityManager().merge(selected);
                    getEntityManager().getTransaction().commit();
                    return false;
                }
            } catch (NoResultException e) {
                return true;
            } catch (PersistenceException e) {
                throw new IllegalArgumentException(e.getMessage());

            }

        }
        return true;

    }

    //
    // Metodos Privados
    //
    private void consumirIpvRegistro(ProductovOrden productoVenta, float cantidad) {
        List<IpvRegistro> updateList = new ArrayList<>();
        for (ProductoInsumo productoInsumo : productoVenta.getProductoVenta().getProductoInsumoList()) {
            IpvRegistro registro
                    = getIpvRegistro(productoVenta.getProductoVenta().getCocinacodCocina(),
                            productoVenta.getOrden().getVentafecha(),
                            productoInsumo.getInsumo());
            if (registro != null) {
                float cantidadaRebajar = productoInsumo.getCantidad() * cantidad;
                registro.setConsumo(registro.getConsumo() + cantidadaRebajar);
                updateList.add(registro);
            }
        }
        for (IpvRegistro registro : updateList) {
            updateInstance(registro);
        }
    }

    private void devolverIpvRegistro(ProductovOrden productoVenta, float cantidad) {
        List<IpvRegistro> updateList = new ArrayList<>();
        for (ProductoInsumo productoInsumo : productoVenta.getProductoVenta().getProductoInsumoList()) {
            IpvRegistro registro
                    = getIpvRegistro(productoVenta.getProductoVenta().getCocinacodCocina(),
                            productoVenta.getOrden().getVentafecha(),
                            productoInsumo.getInsumo());
            if (registro != null) {
                float cantidadaRebajar = productoInsumo.getCantidad() * cantidad;
                registro.setConsumo(registro.getConsumo() - cantidadaRebajar);
                updateList.add(registro);
            }
        }
        for (IpvRegistro registro : updateList) {
            updateInstance(registro);
        }
    }

}
