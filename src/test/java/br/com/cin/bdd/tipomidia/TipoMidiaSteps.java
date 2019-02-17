package br.com.cin.bdd.tipomidia;


import static org.junit.Assert.assertEquals;

import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Então;
import cucumber.api.java.pt.Quando;

public class TipoMidiaSteps {
	static String LANCAMENTO_SIM = "SIM";
	static String LANCAMENTO_NAO = "NÃO";
	
	static String DVD_LANCAMENTO = "DVD LANCAMENTO";
	static String BLURAY_LANCAMENTO = "BLURAY LANCAMENTO";
	
	static String HD_DVD_LANCAMENTO = "HD-DVD LANCAMENTO";
	static String VHS_LANCAMENTO = "VHS LANCAMENTO";
	
	static String DVD = "DVD";
	static String BLURAY = "BLU-RAY";
	
	static String HD_DVD = "HD-DVD";
	static String VHS= "VHS";
	String tipoMidia;
	double valorMidia;
	
	@Dado("^que usuário possa acessar o sistema$")
	public void que_usuário_possa_acessar_o_sistema() throws Throwable {
	    System.out.println("Acessar o sistema");
	}

	@Quando("^escolher um \"([^\"]*)\"$")
	public void escolher_um(String tipoMidia) throws Throwable {
		this.tipoMidia=tipoMidia;
		System.out.println("Tipo de Midia: "+tipoMidia);
	}

	@Quando("^o sistema pegar o valor da midia (\\d+.\\d+)$")
	public void o_sistema_pegar_o_valor_da_midia(String valor) throws Throwable {
		this.valorMidia=Double.parseDouble(valor);
		System.out.println("Valor: "+valorMidia);
	}

	@Então("^o sistema caso seja lançamento acescentar (\\d+)% ao valor da mídia$")
	public void o_sistema_caso_seja_lançamento_acescentar_ao_valor_da_mídia(int arg1) throws Throwable {
		System.out.println("Lancamento----");
	}
	


@Então("^o exibir uma mensagem se é lançamento: \"([^\"]*)\"$")
public void o_exibir_uma_mensagem_se_é_lançamento(String lancamento) throws Throwable {
	
	equals(calculoValorLocacao(this.tipoMidia,lancamento)==this.valorMidia);
    
}
	
		
	
	private double calculoValorLocacao(String tipoMidia, String lancamento) {
		double valorLocacao=0.0;
		if(lancamento.equalsIgnoreCase(LANCAMENTO_NAO)) {			
			if(this.tipoMidia.equalsIgnoreCase(DVD)) {
				valorLocacao = ((this.valorMidia))+this.valorMidia;
			}
			if(this.tipoMidia.equalsIgnoreCase(BLURAY)) {
				valorLocacao = ((this.valorMidia)*0.50)+this.valorMidia;
			}
			if(this.tipoMidia.equalsIgnoreCase(HD_DVD)) {
				valorLocacao = ((this.valorMidia)*0.50)+this.valorMidia;
			}
			if(this.tipoMidia.equalsIgnoreCase(VHS)) {
				valorLocacao = ((this.valorMidia)*0.50)+this.valorMidia;
			}
		}else 
			valorLocacao = this.valorMidia;		
		return valorLocacao;
	}
		
}
