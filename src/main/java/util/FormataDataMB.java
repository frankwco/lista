package util;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
@Named("formataDataMB")
public class FormataDataMB implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public String formatarData(Date dt) {
		if (dt != null) {
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			return format.format(dt);

		} else {
			return "";
		}
	}

}
