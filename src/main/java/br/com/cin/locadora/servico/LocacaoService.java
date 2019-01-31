package br.com.cin.locadora.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cin.locadora.model.Locacao;
import br.com.cin.locadora.model.repository.LocacaoRepository;

@Service
public class LocacaoService {
	
		@Autowired
		LocacaoRepository locacaoRepository;
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
}
