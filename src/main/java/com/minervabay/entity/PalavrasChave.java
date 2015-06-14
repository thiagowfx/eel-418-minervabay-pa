/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.minervabay.entity;

import com.minervabay.entity.Dadoscatalogo;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author thiago
 */
@Entity
@Table(name = "palavras_chave")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PalavrasChave.findAll", query = "SELECT p FROM PalavrasChave p"),
    @NamedQuery(name = "PalavrasChave.findBySerialno", query = "SELECT p FROM PalavrasChave p WHERE p.serialno = :serialno"),
    @NamedQuery(name = "PalavrasChave.findByPalchave", query = "SELECT p FROM PalavrasChave p WHERE p.palchave = :palchave")})
public class PalavrasChave implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "serialno")
    private Integer serialno;
    @Size(max = 2147483647)
    @Column(name = "palchave")
    private String palchave;
    @JoinColumn(name = "patrimonio", referencedColumnName = "patrimonio")
    @ManyToOne
    private Dadoscatalogo patrimonio;

    public PalavrasChave() {
    }

    public PalavrasChave(Integer serialno) {
        this.serialno = serialno;
    }

    public Integer getSerialno() {
        return serialno;
    }

    public void setSerialno(Integer serialno) {
        this.serialno = serialno;
    }

    public String getPalchave() {
        return palchave;
    }

    public void setPalchave(String palchave) {
        this.palchave = palchave;
    }

    public Dadoscatalogo getPatrimonio() {
        return patrimonio;
    }

    public void setPatrimonio(Dadoscatalogo patrimonio) {
        this.patrimonio = patrimonio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (serialno != null ? serialno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PalavrasChave)) {
            return false;
        }
        PalavrasChave other = (PalavrasChave) object;
        if ((this.serialno == null && other.serialno != null) || (this.serialno != null && !this.serialno.equals(other.serialno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.minervabay.servlet.PalavrasChave[ serialno=" + serialno + " ]";
    }
    
}
