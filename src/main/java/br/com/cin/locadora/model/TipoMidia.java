/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cin.locadora.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 2192525
 */
@Entity
@Table(name = "tipo_midia")
public class TipoMidia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_midia")
    private Integer idTipoMidia;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "valor")
    private Double valor;
    @OneToMany(mappedBy = "tipoMidia")
    private Collection<Filme> filmeCollection;

    public TipoMidia() {
    }

    public TipoMidia(Integer idTipoMidia) {
        this.idTipoMidia = idTipoMidia;
    }

    public Integer getIdTipoMidia() {
        return idTipoMidia;
    }

    public void setIdTipoMidia(Integer idTipoMidia) {
        this.idTipoMidia = idTipoMidia;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    @XmlTransient
    public Collection<Filme> getFilmeCollection() {
        return filmeCollection;
    }

    public void setFilmeCollection(Collection<Filme> filmeCollection) {
        this.filmeCollection = filmeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoMidia != null ? idTipoMidia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoMidia)) {
            return false;
        }
        TipoMidia other = (TipoMidia) object;
        if ((this.idTipoMidia == null && other.idTipoMidia != null) || (this.idTipoMidia != null && !this.idTipoMidia.equals(other.idTipoMidia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication1.TipoMidia[ idTipoMidia=" + idTipoMidia + " ]";
    }
    
}
