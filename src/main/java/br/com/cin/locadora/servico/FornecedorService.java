package br.com.cin.locadora.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cin.locadora.model.Fornecedor;
import br.com.cin.locadora.model.repository.FornecedorRepository;

@Service
public class FornecedorService {

	@Autowired
	FornecedorRepository fornecedorrepository;
	
	public void salvarFornecedor(Fornecedor fornecedor) {
		fornecedorrepository.save(fornecedor);
	}
	
	public Iterable<Fornecedor> listarTodos(){
		return this.fornecedorrepository.findAll();
	}
	
	public Fornecedor buscarPorId (Integer id) {
		return this.fornecedorrepository.findById(id).get();
	}
	
	public void atualizar (Fornecedor fornecedor) {
		this.fornecedorrepository.save(fornecedor);
		
	}
	
	
}
