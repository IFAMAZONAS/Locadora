package br.com.cin.locadora.servico;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cin.locadora.model.Cliente;
import br.com.cin.locadora.model.Locacao;
import br.com.cin.locadora.model.repository.ClienteRepository;
import br.com.cin.locadora.model.repository.LocacaoRepository;

@Service
public class LocacaoService {
		
		private static Integer ABERTO=1;
		private static Integer FECHADO=2;
	
		@Autowired
		LocacaoRepository locacaoRepository;
		@Autowired
		ClienteRepository clienteRepository;
		/*****
		 * 
		 * @param locacao
		 * @return
		 */
		public boolean persistirLocacao(Locacao locacao) {
			if(!locacao.getIdCliente().equals(null)) {
				this.locacaoRepository.save(locacao);
				return true;
			}else {
				return false;
			}
			
		}
		/***
		 * 
		 * @param idCliente
		 * @return
		 */
		public Iterable<Locacao> listarLocacoesAbertas(Integer idCliente) {
			// TODO Auto-generated method stub
			return  this.obterLocacoesAbertasPorCliente(idCliente);
		}
		
		/***
		 * 
		 * @param idCliente
		 * @return
		 */
		public Iterable<Locacao> listarLocacoesFechadas(Integer idCliente) {
			// TODO Auto-generated method stub
			return  this.obterLocacoesFechadasPorCliente(idCliente);
		}
		
		/***
		 * 
		 * @param idCliente
		 * @return
		 */
		private List<Locacao> obterLocacoesAbertasPorCliente(Integer idCliente) {			
			List<Locacao> listaDeLocacoes = new ArrayList<Locacao>();
			Cliente cliente = this.clienteRepository.findById(idCliente).get();
			for(Locacao locacao : cliente.getLocacaoList()) {
				if(locacao.getStatusLocacao().getIdStatusLocacao()==ABERTO) {
					 listaDeLocacoes.add(locacao);
					
				}
			}return listaDeLocacoes;
		}
		
		private List<Locacao> obterLocacoesFechadasPorCliente(Integer idCliente) {			
			List<Locacao> listaDeLocacoes = new ArrayList<Locacao>();
			Cliente cliente = this.clienteRepository.findById(idCliente).get();
			for(Locacao locacao : cliente.getLocacaoList()) {
				if(locacao.getStatusLocacao().getIdStatusLocacao()==FECHADO) {
					 listaDeLocacoes.add(locacao);
					
				}
			}return listaDeLocacoes;
		}
}
