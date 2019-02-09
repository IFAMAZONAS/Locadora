package br.com.cin.locadora.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cin.locadora.model.Pagamento;
import br.com.cin.locadora.model.repository.PagamentoRepository;

@Service
public class PagamentoService {
	
	@Autowired
	PagamentoRepository pagamentoRepository;
	
	public Iterable<Pagamento> listarTodos() {
		return this.pagamentoRepository.findAll();
	}

}
