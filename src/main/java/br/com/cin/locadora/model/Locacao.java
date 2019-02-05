
package br.com.cin.locadora.model;

import java.io.Serializable;
import java.util.ArrayList;
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

@Entity
@Table(name = "locacao")
@NamedQueries({
    @NamedQuery(name = "Locacao.findAll", query = "SELECT l FROM Locacao l")})
public class Locacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_locacao")
    private Integer idLocacao;
    @Column(name = "data_locacao")
    @Temporal(TemporalType.DATE)
    private Date dataLocacao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Double valor;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idLocacao", fetch = FetchType.EAGER)
    private List<LocacaoFilme> locacaoFilmeList = new ArrayList<LocacaoFilme>();
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Cliente idCliente;
    
    @JoinColumn(name = "status_locacao", referencedColumnName = "id_status_locacao")
    @ManyToOne(fetch = FetchType.LAZY)
    private StatusLocacao statusLocacao;

    public Locacao() {
    }

    public Locacao(Integer idLocacao) {
        this.idLocacao = idLocacao;
    }

    public Integer getIdLocacao() {
        return idLocacao;
    }

    public void setIdLocacao(Integer idLocacao) {
        this.idLocacao = idLocacao;
    }

    public Date getDataLocacao() {
        return dataLocacao;
    }

    public void setDataLocacao(Date dataLocacao) {
        this.dataLocacao = dataLocacao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

   

    public List<LocacaoFilme> getLocacaoFilmeList() {
        return locacaoFilmeList;
    }

    public void setLocacaoFilmeList(List<LocacaoFilme> locacaoFilmeList) {
        this.locacaoFilmeList = locacaoFilmeList;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }
    
    public void setStatusLocacao(StatusLocacao statusLocacao) {
		this.statusLocacao = statusLocacao;
	}
    
    public StatusLocacao getStatusLocacao() {
		return statusLocacao;
	}

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLocacao != null ? idLocacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Locacao)) {
            return false;
        }
        Locacao other = (Locacao) object;
        if ((this.idLocacao == null && other.idLocacao != null) || (this.idLocacao != null && !this.idLocacao.equals(other.idLocacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication1.Locacao[ idLocacao=" + idLocacao + " ]";
    }
    
}
