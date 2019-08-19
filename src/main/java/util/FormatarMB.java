package util;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import base.modelo.GrupoLancamento;
import base.modelo.GrupoResiduo;
import base.modelo.ItensColeta;
import base.modelo.ItensInformacaoFinanceiraGrupo;
import base.modelo.ItensInformacaoFinanceiraPontoColeta;
import base.modelo.Usuario;
import base.modelo.Coleta;
import base.modelo.ContasReceberParcelas;
import base.service.ItensColetaService;
import base.service.ColetaService;

import util.ExibirMensagem;
import util.FecharDialog;
import util.Mensagem;
import dao.GenericDAO;

@ViewScoped
@Named("formatarMB")
public class FormatarMB implements Serializable {
	private static final long serialVersionUID = 1L;

	public String formatarData(Date dt) {
		if (dt != null) {
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			return format.format(dt);

		} else {
			return "";
		}
	}

	public String formatarNumeroTresCasasDecimais(Double valor) {
		if (valor == null) {
			valor = 0.;
		}
		DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
		return new DecimalFormat("#,###.000", symbols).format(valor);
	}

	public String formatarNumeroDuasCasasDecimais(Double valor) {
		if (valor == null) {
			valor = 0.;
		}
		DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
		return new DecimalFormat("#,###.00", symbols).format(valor);
	}

}
