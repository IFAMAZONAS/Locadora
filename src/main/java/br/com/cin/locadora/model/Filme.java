/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cin.locadora.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 2192525
 */
@Entity
public class Filme implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_filme")
    private Integer idFilme;
    @Column(name = "numero_serie")
    private String numeroSerie;
    @Column(name = "titulo_original")
    private String tituloOriginal;
    @Column(name = "titulo_portugues")
    private String tituloPortugues;
    @Column(name = "pais")
    private String pais;
    @Column(name = "ano")
    private Integer ano;
    @Column(name = "direcao")
    private String direcao;
    @Column(name = "elenco")
    private String elenco;
    @Column(name = "sinopse")
    private String sinopse;
    @Column(name = "duracao")
    private String duracao;
    @Column(name = "categoria")
    private Integer categoria;
    @Column(name = "data_aquisicao")
    @Temporal(TemporalType.DATE)
    private Date dataAquisicao;
    
    @JoinColumn(name = "id_fornecedor", referencedColumnName = "id_fornecedor")
    @ManyToOne
    private Fornecedor idFornecedor;
   
 
    @JoinColumn(name = "tipo_midia", referencedColumnName = "id_tipo_midia")
    @ManyToOne(fetch = FetchType.LAZY)
    private TipoMidia tipoMidia;
    
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    private ValoresLocacao valor;
    
    @JoinColumn(name = "id_genero", referencedColumnName = "id_genero")
    @ManyToOne(fetch = FetchType.LAZY)
    private Genero idGenero;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFilme", fetch = FetchType.EAGER)
    private List<LocacaoFilme> locacaoFilmeList;

    public Filme() {
    }

    public Filme(Integer idFilme) {
        this.idFilme = idFilme;
    }

    public Integer getIdFilme() {
        return idFilme;
    }

    public void setIdFilme(Integer idFilme) {
        this.idFilme = idFilme;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getTituloOriginal() {
        return tituloOriginal;
    }

    public void setTituloOriginal(String tituloOriginal) {
        this.tituloOriginal = tituloOriginal;
    }

    public String getTituloPortugues() {
        return tituloPortugues;
    }

    public void setTituloPortugues(String tituloPortugues) {
        this.tituloPortugues = tituloPortugues;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getDirecao() {
        return direcao;
    }

    public void setDirecao(String direcao) {
        this.direcao = direcao;
    }

    public String getElenco() {
        return elenco;
    }

    public void setElenco(String elenco) {
        this.elenco = elenco;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public Integer getCategoria() {
        return categoria;
    }

    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
    }

    public Date getDataAquisicao() {
        return dataAquisicao;
    }

    public void setDataAquisicao(Date dataAquisicao) {
        this.dataAquisicao = dataAquisicao;
    }

   
    public void setIdFornecedor(Fornecedor idFornecedor) {
		this.idFornecedor = idFornecedor;
	}
    
    public Fornecedor getIdFornecedor() {
		return idFornecedor;
	}
    
    public void setValor(ValoresLocacao valor) {
		this.valor = valor;
	}
    
    public ValoresLocacao getValor() {
		return valor;
	}

    public TipoMidia getTipoMidia() {
        return tipoMidia;
    }

    public void setTipoMidia(TipoMidia tipoMidia) {
        this.tipoMidia = tipoMidia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFilme != null ? idFilme.hashCode() : 0);
        return hash;
    }
    
    public void setIdGenero(Genero idGenero) {
		this.idGenero = idGenero;
	}
    
    public Genero getIdGenero() {
		return idGenero;
	}

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Filme)) {
            return false;
        }
        Filme other = (Filme) object;
        if ((this.idFilme == null && other.idFilme != null) || (this.idFilme != null && !this.idFilme.equals(other.idFilme))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "[ idFilme=" + idFilme + " ]";
    }
    
}
