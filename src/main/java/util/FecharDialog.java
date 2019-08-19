package util;

import org.primefaces.context.RequestContext;

public class FecharDialog {

	public static void fecharDialogTipoServidor() {
		RequestContext.getCurrentInstance().execute("PF('dlgTipoServidor').hide();");
	}

	public static void fecharDialogEstado() {
		RequestContext.getCurrentInstance().execute("PF('dlgEstado').hide();");
	}
	
	public static void fecharDialogRota() {
		RequestContext.getCurrentInstance().execute("PF('dlgRota').hide();");
	}
	
	public static void fecharDialogContasReceberBaixa() {
		RequestContext.getCurrentInstance().execute("PF('dlgBaixarParcela').hide();");
	}

	public static void fecharDialogGrupoResiduo() {
		RequestContext.getCurrentInstance().execute("PF('dlgGrupoResiduo').hide();");
	}

	public static void fecharDialogCidade() {
		RequestContext.getCurrentInstance().execute("PF('dlgCidade').hide();");
	}

	public static void fecharDialogCliente() {
		RequestContext.getCurrentInstance().execute("PF('dlgCliente').hide();");
	}

	public static void fecharDialogPontoColeta() {
		RequestContext.getCurrentInstance().execute("PF('dlgPontoColeta').hide();");
	}
	
	public static void fecharDialogAlterarRotaPontoColeta() {
		RequestContext.getCurrentInstance().execute("PF('dlgAlterarRotaPontoColeta').hide();");
	}
	
	public static void fecharDialogAlterarOrdemRotaPontoColeta() {
		RequestContext.getCurrentInstance().execute("PF('dlgAlterarOrdemRota').hide();");
	}
	

	public static void fecharDialogInserirLancamento() {
		RequestContext.getCurrentInstance().execute("PF('dlgInserirLancamento').hide();");
	}

	public static void fecharDialogVeiculo() {
		RequestContext.getCurrentInstance().execute("PF('dlgVeiculo').hide();");
	}

	public static void fecharDialogSalario() {
		RequestContext.getCurrentInstance().execute("PF('dlgSalario').hide();");
	}

	public static void fecharDialogPessoa() {
		RequestContext.getCurrentInstance().execute("PF('dlgPessoa').hide();");
	}

	public static void fecharDialogUsuario() {
		RequestContext.getCurrentInstance().execute("PF('dlgUsuario').hide();");
	}

	public static void fecharDialogProcesso() {
		RequestContext.getCurrentInstance().execute("PF('dlgProcesso').hide();");
	}

	public static void fecharDialogAtividade() {
		RequestContext.getCurrentInstance().execute("PF('dlgAtividade').hide();");
	}

	public static void fecharDialogInformacoesFinanceiras() {
		RequestContext.getCurrentInstance().execute("PF('dlgInformacoesFinanceiras').hide();");
	}

	public static void fecharDialogOcorrencia() {
		RequestContext.getCurrentInstance().execute("PF('dlgOcorrencia').hide();");
	}

	public static void fecharDialogCategoriaIndicador() {
		RequestContext.getCurrentInstance().execute("PF('dlgCategoriaIndicador').hide();");
	}

	public static void fecharDialogGrupoLancamento() {
		RequestContext.getCurrentInstance().execute("PF('dlgGrupoLancamento').hide();");
	}

	public static void fecharDialogIndicador() {
		RequestContext.getCurrentInstance().execute("PF('dlgIndicador').hide();");
	}

	public static void fecharDialogDescricaoDespesa() {
		RequestContext.getCurrentInstance().execute("PF('dlgDescricaoDespesa').hide();");
	}

	public static void fecharDialogDespesa() {
		RequestContext.getCurrentInstance().execute("PF('dlgDespesa').hide();");
	}

}
