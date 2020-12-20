/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobits.pos.printservice;

import com.jobits.pos.persistence.Impresora;
import java.util.List;

/**
 *
 * @author Javier
 */
public interface ImpresoraRepo {
    
    public List<Impresora> cargarImpresoras();
    
    public boolean guardarImpresoras(List<Impresora> listaAGuardar);
    
    public boolean eliminarImpresora(Impresora impresoraToDelete);
    
}
