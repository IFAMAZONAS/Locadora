package br.com.cin.locadora.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cin.locadora.model.Cartao;

public interface CartaoRepository extends JpaRepository<Cartao, Integer> {

}
