package br.com.cin.locadora.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity(name="funcao")
public class Funcao implements Serializable {
	
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_funcao")
    private Integer idFuncao;
    @Column(name = "descricao")
    private String descricao;
    @OneToMany(mappedBy = "idFuncao")
    private Collection<Usuario> usuarioCollection;
    
	public Integer getIdFuncao() {
		return idFuncao;
	}
	public void setIdFuncao(Integer idFuncao) {
		this.idFuncao = idFuncao;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Collection<Usuario> getUsuarioCollection() {
		return usuarioCollection;
	}
	public void setUsuarioCollection(Collection<Usuario> usuarioCollection) {
		this.usuarioCollection = usuarioCollection;
	}
    
    
}
