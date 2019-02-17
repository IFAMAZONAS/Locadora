package br.com.cin.locadora.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cin.locadora.model.FormaPagamento;
import br.com.cin.locadora.model.repository.FormaPagamentoRepository;

@Service
public class FormaPagamentoService {

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	

	public void salvarFormaPagamento(FormaPagamento formapagamento) {
		formaPagamentoRepository.save(formapagamento);
	}
	
	public Iterable<FormaPagamento> listarTodos(){
		return this.formaPagamentoRepository.findAll();
	}

	public FormaPagamento buscarPorId(Integer idFormaPagamento) {
		return this.formaPagamentoRepository.findById(idFormaPagamento).get();
	}
	
	public void atualizar(FormaPagamento formapagamento) {
		this.formaPagamentoRepository.save(formapagamento);
	}
}
