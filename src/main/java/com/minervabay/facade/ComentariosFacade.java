/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.minervabay.facade;

import com.minervabay.entity.Comentarios;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author thiago
 */
@Stateless
public class ComentariosFacade extends AbstractFacade<Comentarios> {
    @PersistenceContext(unitName = "com.minervabay_minervabay_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ComentariosFacade() {
        super(Comentarios.class);
    }
    
    public List<Comentarios> findByPatrimonio(Integer patrimonio) {
        return em.createNamedQuery("Comentarios.findByPatrimonioNumber").setParameter("patrimonio", patrimonio).getResultList();
    }

    public void removeByPatrimonio(Integer patrimonio) {
        em.createNamedQuery("Comentarios.removeByPatrimonioNumber").setParameter("patrimonio", patrimonio).executeUpdate();
    }
}
