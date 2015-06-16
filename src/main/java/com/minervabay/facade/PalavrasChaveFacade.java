/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.minervabay.facade;

import com.minervabay.entity.PalavrasChave;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author thiago
 */
@Stateless
public class PalavrasChaveFacade extends AbstractFacade<PalavrasChave> {
    @PersistenceContext(unitName = "com.minervabay_minervabay_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public PalavrasChaveFacade() {
        super(PalavrasChave.class);
    }
    
    public List findByPatrimonio(Integer patrimonio) {
        return em.createNamedQuery("PalavrasChave.findByPatrimonioNumber").setParameter("patrimonio", patrimonio).getResultList();
    }
    
    public void removeByPatrimonio(Integer patrimonio) {
        em.createNamedQuery("PalavrasChave.removeByPatrimonioNumber").setParameter("patrimonio", patrimonio).executeUpdate();
    }
    
}
