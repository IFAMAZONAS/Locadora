package br.com.cin.locadora.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author 2192525
 */
@Entity
@Table(name = "status_locacao")
@NamedQueries({
    @NamedQuery(name = "StatusLocacao.findAll", query = "SELECT s FROM StatusLocacao s")})
public class StatusLocacao implements Serializable {

    @OneToMany(mappedBy = "statusLocacao", fetch = FetchType.LAZY)
    private List<Locacao> locacaoList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_status_locacao")
    private Integer idStatusLocacao;
    @Column(name = "descricao")
    private String descricao;

    public StatusLocacao() {
    }

    public StatusLocacao(Integer idStatusLocacao) {
        this.idStatusLocacao = idStatusLocacao;
    }

    public Integer getIdStatusLocacao() {
        return idStatusLocacao;
    }

    public void setIdStatusLocacao(Integer idStatusLocacao) {
        this.idStatusLocacao = idStatusLocacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idStatusLocacao != null ? idStatusLocacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StatusLocacao)) {
            return false;
        }
        StatusLocacao other = (StatusLocacao) object;
        if ((this.idStatusLocacao == null && other.idStatusLocacao != null) || (this.idStatusLocacao != null && !this.idStatusLocacao.equals(other.idStatusLocacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication1.StatusLocacao[ idStatusLocacao=" + idStatusLocacao + " ]";
    }

    public List<Locacao> getLocacaoList() {
        return locacaoList;
    }

    public void setLocacaoList(List<Locacao> locacaoList) {
        this.locacaoList = locacaoList;
    }
    
}