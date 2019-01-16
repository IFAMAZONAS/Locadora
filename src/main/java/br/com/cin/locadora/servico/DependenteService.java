package br.com.cin.locadora.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cin.locadora.model.Cliente;
import br.com.cin.locadora.model.Dependente;
import br.com.cin.locadora.model.repository.DependenteRepository;

@Service
public class DependenteService {
	
	@Autowired
	DependenteRepository dependenteRepository;
		
	public boolean cadastro(Dependente dependente) {
		Cliente cliente = dependente.getIdCliente();
		
		if(cliente.getDependentes().size()>=3) {
			return false;
		}else {
			this.dependenteRepository.save(dependente);
			return true;
		}
	}

}
