/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.minervabay.facade;

import com.minervabay.entity.Dadoscatalogo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author thiago
 */
@Stateless
public class DadoscatalogoFacade extends AbstractFacade<Dadoscatalogo> {
    @PersistenceContext(unitName = "com.minervabay_minervabay_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public DadoscatalogoFacade() {
        super(Dadoscatalogo.class);
    }
    
}
