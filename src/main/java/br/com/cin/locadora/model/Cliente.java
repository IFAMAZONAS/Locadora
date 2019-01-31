package br.com.cin.locadora.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
public class Cliente implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_cliente")
    private Integer id;	
    private String cpf;   
    private String nome;
    private String endereco; 
    private String email;
    @Column(name="local_trabalho")
    private String localTrabalho;  
    @Column(name="fone_residencial")
    private String foneResidencial;
    @Column(name="fone_celular")
    private String foneCelular;  
    @Column(name="fone_comercial")
    private String foneComercial;   
    private String sexo;    
   
    
    @ManyToOne(fetch = FetchType.EAGER)
    private StatusCliente status;
    
    @OneToMany(mappedBy = "idCliente", fetch = FetchType.EAGER)
    private List<Locacao> locacaoList;
    
    
    @OneToMany(mappedBy = "idCliente", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Dependente> dependentes  = new ArrayList<Dependente>();
    
    public Cliente() {
		// TODO Auto-generated constructor stub
	}
    
    
   
    public Integer getId() {
		return id;
	}
    
    public void setId(Integer id) {	this.id = id;	}
    
	public String getCpf() {return cpf;	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLocalTrabalho() {
		return localTrabalho;
	}
	public void setLocalTrabalho(String localTrabalho) {
		this.localTrabalho = localTrabalho;
	}
	public String getFoneResidencial() {
		return foneResidencial;
	}
	public void setFoneResidencial(String foneResidencial) {
		this.foneResidencial = foneResidencial;
	}
	public String getFoneCelular() {
		return foneCelular;
	}
	public void setFoneCelular(String foneCelular) {
		this.foneCelular = foneCelular;
	}
	public String getFoneComercial() {
		return foneComercial;
	}
	public void setFoneComercial(String foneComercial) {
		this.foneComercial = foneComercial;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	
	public void setDependentes(List<Dependente> dependentes) {
		this.dependentes = dependentes;
	}
	
	public List<Dependente> getDependentes() {
		return dependentes;
	}
	
	public void setStatus(StatusCliente status) {
		this.status = status;
	}
	
	public StatusCliente getStatus() {
		return status;
	}
	
	
	
}
