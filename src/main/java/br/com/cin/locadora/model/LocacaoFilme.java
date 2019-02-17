package br.com.cin.locadora.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author 2192525
 */
@Entity
@Table(name = "locacao_filme")

public class LocacaoFilme implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_filme_locacao")
    private Integer idFilmeLocacao;
    @Column(name = "data_prevista_devolucao")
    @Temporal(TemporalType.DATE)
    private Date dataPrevistaDevolucao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Double valor;
    @Column(name = "status")
    private Integer status;
    @Column(name = "data_devolucao")
    @Temporal(TemporalType.DATE)
    private Date dataDevolucao;
    @JoinColumn(name = "id_filme", referencedColumnName = "id_filme")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Filme idFilme;
    @JoinColumn(name = "id_locacao", referencedColumnName = "id_locacao")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Locacao idLocacao;

    public LocacaoFilme() {
    }

    public LocacaoFilme(Integer idFilmeLocacao) {
        this.idFilmeLocacao = idFilmeLocacao;
    }

    public Integer getIdFilmeLocacao() {
        return idFilmeLocacao;
    }

    public void setIdFilmeLocacao(Integer idFilmeLocacao) {
        this.idFilmeLocacao = idFilmeLocacao;
    }

    public Date getDataPrevistaDevolucao() {
        return dataPrevistaDevolucao;
    }

    public void setDataPrevistaDevolucao(Date dataPrevistaDevolucao) {
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public Filme getIdFilme() {
        return idFilme;
    }

    public void setIdFilme(Filme idFilme) {
        this.idFilme = idFilme;
    }

    public Locacao getIdLocacao() {
        return idLocacao;
    }

    public void setIdLocacao(Locacao idLocacao) {
        this.idLocacao = idLocacao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFilmeLocacao != null ? idFilmeLocacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LocacaoFilme)) {
            return false;
        }
        LocacaoFilme other = (LocacaoFilme) object;
        if ((this.idFilmeLocacao == null && other.idFilmeLocacao != null) || (this.idFilmeLocacao != null && !this.idFilmeLocacao.equals(other.idFilmeLocacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication1.LocacaoFilme[ idFilmeLocacao=" + idFilmeLocacao + " ]";
    }
    
}