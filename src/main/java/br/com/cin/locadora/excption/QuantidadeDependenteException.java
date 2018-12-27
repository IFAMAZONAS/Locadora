package br.com.cin.locadora.excption;

public class QuantidadeDependenteException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5650584268939285630L;
	
	private String menssagem;
	
	public QuantidadeDependenteException() {
		// TODO Auto-generated constructor stub
	}

	public QuantidadeDependenteException(String menssagem) {
		super();
		this.menssagem = menssagem;
	}
	
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return super.getMessage();
	}
	
	

}
