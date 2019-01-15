package br.com.cin.locadora.controlador;

import java.util.ArrayList;
import java.util.List;

import br.com.cin.locadora.model.Cliente;

public class ValidadorCliente {
	 public List<String> msgErros;
	 public List<String> msg;
	 
	 
	 public static ValidadorCliente validadorcliente;
	 
	 public static ValidadorCliente getInstance() {
		 if(validadorcliente== null) {
			 validadorcliente = new ValidadorCliente();
		 } return validadorcliente;
	 }
	 
	 public ValidadorCliente() {
		 this.msgErros = new ArrayList<String>();
		 this.msg = new ArrayList<String>();
	 }
	 
	 public boolean validatePesquisa(List<Cliente> clientes) {
		 this.resetarMsg();
		 if(clientes.isEmpty()) {
			 return false;
		 }else {
			 return true;
		 }
	 }
	 
	 private void resetarMsg() {
		 this.msgErros = new ArrayList<String>();
		 this.msg = new ArrayList<String>();		
	}

	public List<String> getMsg() {
		return msg;
	 }
	 
	 public List<String> getMsgErros() {
		return msgErros;
	 }
	 
	 
	 
	 
	 
}
