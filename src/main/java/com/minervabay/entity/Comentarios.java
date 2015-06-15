package com.minervabay.entity;

import java.io.Serializable;
import javax.json.Json;
import javax.json.JsonObject;
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
@Table(name = "comentarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comentarios.findAll", query = "SELECT c FROM Comentarios c"),
    @NamedQuery(name = "Comentarios.findBySerialnocomentarios", query = "SELECT c FROM Comentarios c WHERE c.serialnocomentarios = :serialnocomentarios"),
    @NamedQuery(name = "Comentarios.findByComentario", query = "SELECT c FROM Comentarios c WHERE c.comentario = :comentario")})
public class Comentarios implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "serialnocomentarios")
    private Integer serialnocomentarios;
    @Size(max = 2147483647)
    @Column(name = "comentario")
    private String comentario;
    @JoinColumn(name = "patrimonio", referencedColumnName = "patrimonio")
    @ManyToOne
    private Dadoscatalogo patrimonio;

    public Comentarios() {
    }

    public Comentarios(Integer serialnocomentarios) {
        this.serialnocomentarios = serialnocomentarios;
    }

    public Integer getSerialnocomentarios() {
        return serialnocomentarios;
    }

    public void setSerialnocomentarios(Integer serialnocomentarios) {
        this.serialnocomentarios = serialnocomentarios;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
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
        hash += (serialnocomentarios != null ? serialnocomentarios.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comentarios)) {
            return false;
        }
        Comentarios other = (Comentarios) object;
        if ((this.serialnocomentarios == null && other.serialnocomentarios != null) || (this.serialnocomentarios != null && !this.serialnocomentarios.equals(other.serialnocomentarios))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.minervabay.entity.Comentarios[ serialnocomentarios=" + serialnocomentarios + " ]";
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("serialnocomentarios", serialnocomentarios)
                .add("comentario", comentario)
                .add("patrimonio", patrimonio.getPatrimonio())
                .build();
    }
}
