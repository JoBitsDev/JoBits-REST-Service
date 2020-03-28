/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jobits.pos.authentication;

import com.jobits.pos.persistence.pasarela.Token;
import javax.persistence.EntityManagerFactory;

/**
 * FirstDream
 * @author Jorge
 * 
 */
public class TennantWrapper {

    private Token tennantToken;
    private EntityManagerFactory tennantEmf;

    public TennantWrapper(Token tennantToken, EntityManagerFactory tennantEmf) {
        this.tennantToken = tennantToken;
        this.tennantEmf = tennantEmf;
    }

    public Token getTennantToken() {
        return tennantToken;
    }

    public void setTennantToken(Token tennantToken) {
        this.tennantToken = tennantToken;
    }

    public EntityManagerFactory getTennantEmf() {
        return tennantEmf;
    }

    public void setTennantEmf(EntityManagerFactory tennantEmf) {
        this.tennantEmf = tennantEmf;
    }
    
    
    
}
