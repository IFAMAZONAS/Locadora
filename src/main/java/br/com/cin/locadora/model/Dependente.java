package br.com.cin.locadora.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity()
@Table(name="dependente",schema="public")
public class Dependente {
	

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dependente")
    private Integer idDependente;
    @Column(name = "nome")
    private String nome;
    @Column(name = "email")
    private String email;
    @Column(name = "sexo")
    private String sexo;
    @Column(name = "data_nascimento")
    private Date dataNascimento;
    @Column(name="ativo")
    private boolean ativo;

    
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    @ManyToOne(optional=false)
    private Cliente idCliente;

    public Dependente() {
    }

    public Dependente(Integer idDependente) {
        this.idDependente = idDependente;
    }

    public Integer getIdDependente() {
        return idDependente;
    }

    public void setIdDependente(Integer idDependente) {
        this.idDependente = idDependente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }



    public void setIdCliente(Cliente idCliente) {
		this.idCliente = idCliente;
	}
    
    public Cliente getIdCliente() {
		return idCliente;
	}
    
    public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
    
    public boolean isAtivo() {
		return ativo;
	}

	
	
}
