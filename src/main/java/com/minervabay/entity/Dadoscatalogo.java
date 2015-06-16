package com.minervabay.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author thiago
 */
@Entity
@Table(name = "dadoscatalogo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dadoscatalogo.findAll", query = "SELECT d FROM Dadoscatalogo d"),
    @NamedQuery(name = "Dadoscatalogo.findByPatrimonio", query = "SELECT d FROM Dadoscatalogo d WHERE d.patrimonio = :patrimonio"),
    @NamedQuery(name = "Dadoscatalogo.findByTitulo", query = "SELECT d FROM Dadoscatalogo d WHERE d.titulo = :titulo"),
    @NamedQuery(name = "Dadoscatalogo.findByAutoria", query = "SELECT d FROM Dadoscatalogo d WHERE d.autoria = :autoria"),
    @NamedQuery(name = "Dadoscatalogo.findByVeiculo", query = "SELECT d FROM Dadoscatalogo d WHERE d.veiculo = :veiculo"),
    @NamedQuery(name = "Dadoscatalogo.findByDataPublicacao", query = "SELECT d FROM Dadoscatalogo d WHERE d.dataPublicacao = :dataPublicacao"),
    @NamedQuery(name = "Dadoscatalogo.findByArquivo", query = "SELECT d FROM Dadoscatalogo d WHERE d.arquivo = :arquivo")})
public class Dadoscatalogo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "patrimonio", updatable = false)
    private Integer patrimonio;
    @Size(max = 2147483647)
    @Column(name = "titulo")
    private String titulo;
    @Size(max = 2147483647)
    @Column(name = "autoria")
    private String autoria;
    @Size(max = 2147483647)
    @Column(name = "veiculo")
    private String veiculo;
    @Column(name = "data_publicacao")
    @Temporal(TemporalType.DATE)
    private Date dataPublicacao;
    @Size(max = 2147483647)
    @Column(name = "arquivo")
    private String arquivo;
    @OneToMany(mappedBy = "patrimonio", cascade = CascadeType.REMOVE)
    private Collection<PalavrasChave> palavrasChaveCollection;
    @OneToMany(mappedBy = "patrimonio", cascade = CascadeType.REMOVE)
    private Collection<Comentarios> comentariosCollection;

    public Dadoscatalogo() {
    }

    public Dadoscatalogo(Integer patrimonio) {
        this.patrimonio = patrimonio;
    }

    public Integer getPatrimonio() {
        return patrimonio;
    }

    public void setPatrimonio(Integer patrimonio) {
        this.patrimonio = patrimonio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutoria() {
        return autoria;
    }

    public void setAutoria(String autoria) {
        this.autoria = autoria;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public Date getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(Date dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public String getArquivo() {
        return arquivo;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }

    @XmlTransient
    public Collection<PalavrasChave> getPalavrasChaveCollection() {
        return palavrasChaveCollection;
    }

    public void setPalavrasChaveCollection(Collection<PalavrasChave> palavrasChaveCollection) {
        this.palavrasChaveCollection = palavrasChaveCollection;
    }

    @XmlTransient
    public Collection<Comentarios> getComentariosCollection() {
        return comentariosCollection;
    }

    public void setComentariosCollection(Collection<Comentarios> comentariosCollection) {
        this.comentariosCollection = comentariosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (patrimonio != null ? patrimonio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dadoscatalogo)) {
            return false;
        }
        Dadoscatalogo other = (Dadoscatalogo) object;
        if ((this.patrimonio == null && other.patrimonio != null) || (this.patrimonio != null && !this.patrimonio.equals(other.patrimonio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.minervabay.entity.Dadoscatalogo[ patrimonio=" + patrimonio + " ]";
    }
    
    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("patrimonio", patrimonio)
                .add("titulo", titulo)
                .add("autoria", autoria)
                .add("veiculo", veiculo)
                .add("datapublicacao",(dataPublicacao == null ? "" : dataPublicacao.toString()))
                .add("arquivo", arquivo == null ? "" : arquivo)
                .build();
    }
    
}
