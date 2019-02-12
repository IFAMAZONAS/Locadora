package br.com.cin.locadora.model.repository;

import org.hibernate.annotations.Check;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cin.locadora.model.Cartao;
import br.com.cin.locadora.model.Cheque;

public interface ChequeRepository extends JpaRepository<Cheque, Integer> {

}
