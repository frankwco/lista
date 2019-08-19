package util;

public class NossoNumeroSicoob {

	public static void main(String[] args) {
		System.out.println(calculoNossoNumero("9676"));
	}

	private static String formataNumero(String numero, int tamanho) {
		while (numero.length() < tamanho) {
			numero = "0" + numero;
		}
		return numero;
	}

	public static int calculoNossoNumero(String numeroDocumento) {
		String nossoNumero = formataNumero(numeroDocumento, 7); 
		int qtde_nosso_numero = nossoNumero.length();
		String sequencia = formataNumero("4361", 4) + formataNumero("280992", 10) + formataNumero(nossoNumero, 7);
		int cont = 0;
		int calculoDv = 0;
		int constante = 0;
		int resto = 0;
		int digitoVerificador = -1;
		for (int num = 0; num < sequencia.length(); num++) {			
			cont++;
			if (cont == 1) {
				constante = 3;
			}
			if (cont == 2) {
				constante = 1;
			}
			if (cont == 3) {
				constante = 9;
			}
			if (cont == 4) {
				constante = 7;
				cont = 0;
			}
			calculoDv = calculoDv + (Integer.parseInt(sequencia.substring(num, num + 1)) * constante);
		}
		resto = calculoDv % 11;
		if (resto == 0 || resto == 1) {
			digitoVerificador = 0;
		} else {
			digitoVerificador = 11 - resto;
		}

		return digitoVerificador;
	}
}
