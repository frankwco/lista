package testes;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

public class TesteBi {
	public static void main(String[] s) {
		Calendar calDataInicial = Calendar.getInstance();
		calDataInicial.setTime(new Date());							
		calDataInicial.set(Calendar.MONTH,5);
		calDataInicial.set(Calendar.YEAR, 2019);	
		calDataInicial.set(Calendar.DAY_OF_MONTH,calDataInicial.getMinimum(Calendar.MONTH));
		System.out.println(calDataInicial.getTime());
		
	}
}
