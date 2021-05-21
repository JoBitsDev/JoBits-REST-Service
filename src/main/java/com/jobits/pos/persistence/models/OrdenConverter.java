/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobits.pos.persistence.models;

import com.jobits.pos.core.domain.models.Mesa;
import com.jobits.pos.core.domain.models.Orden;
import com.jobits.pos.core.domain.models.ProductoVenta;
import com.jobits.pos.core.domain.models.ProductovOrden;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 *
 * JoBits
 *
 * @author Jorge
 *
 */
public class OrdenConverter implements Function<Orden, OrdenModel> {

    @Override
    public OrdenModel apply(Orden t) {
        OrdenModel ret = new OrdenModel();
        ret.setCodOrden(t.getCodOrden());
        ret.setDeLaCasa(t.getDeLaCasa());
        ret.setGananciaXporciento(t.getGananciaXporciento());
        MesaModel mesa = new MesaModel();

        Mesa m = t.getMesacodMesa();
        mesa.setCapacidadMax(m.getCapacidadMax());
        mesa.setCodMesa(m.getCodMesa());
        mesa.setEstado(m.getEstado());
        mesa.setEstallena(m.getEstallena());
        mesa.setUbicacion(m.getUbicacion());

        ret.setMesacodMesaModel(mesa);
        ret.setPorciento(t.getPorciento());

        ArrayList<ProductoVentaOrdenModel> pvo = new ArrayList<>();
        for (ProductovOrden x : t.getProductovOrdenList()) {
            ProductoVenta p = x.getProductoVenta();
            SeccionModel seccion = new SeccionModel(
                    p.getSeccionnombreSeccion().getNombreSeccion(),
                    p.getSeccionnombreSeccion().getDescripcion());
            
            ProductoVentaModel productoVenta = new ProductoVentaModel(
                    p.getCodigoProducto(),p.getNombre(),p.getPrecioVenta(),
                    p.getDescripcion(), seccion);
            
            ProductovOrdenPKModel pkMode = new ProductovOrdenPKModel(p.getCodigoProducto(),
                    x.getOrden().getCodOrden(), x.getId());

            String nota = x.getNota() != null ? x.getNota().getDescripcion() : null;
            
            ProductoVentaOrdenModel po = new ProductoVentaOrdenModel(x.getEnviadosacocina(),pkMode,
                    x.getCantidad(),productoVenta,x.getNumeroComensal(),nota);
                    pvo.add(po);
        }
        ret.setProductoVentaOrdenList(pvo);
        return ret;
    }

}
