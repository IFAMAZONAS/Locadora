package br.com.cin.locadora.controlador.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LocacaoUtil {

	public static int DIAS_DEVOLUCAO_LANCAMENTO = 1;
	
	public static int DIAS_DEVOLUCAO_NORMAL = 3;
	
	
	
	public String  getDatas(Calendar calendar) {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Calendar c = calendar.getInstance(); 
		return dateFormat.format(c);
	}
	
	
}
