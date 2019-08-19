package base.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import base.modelo.ContasReceber;
import base.modelo.ContasReceberParcelas;
import dao.GenericDAO;
import util.ExibirMensagem;
import util.FecharDialog;
import util.Mensagem;
import util.NossoNumeroSicoob;
import util.Transacional;

public class ContasReceberService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<ContasReceber> dao;

	@Inject
	private GenericDAO<ContasReceberParcelas> daoContasReceberParcelas;


	@Inject
	private ContasReceberParcelasService contasReceberParcelasService;

	@Transacional
	public void inserirAlterar(ContasReceber tipo) {
		if (tipo.getId() == null) {
			dao.inserir(tipo);
		} else {
			dao.alterar(tipo);
		}
	}
	
	public void salvar(ContasReceber contasReceber, List<ContasReceberParcelas> listaParcelas) {
		try {
			if (contasReceber != null) {
				//contasReceber.setDataCadastro(new Date());
				//this.inserirAlterar(contasReceber);
				//System.out.println(contasReceber.getId());
				for (ContasReceberParcelas p : listaParcelas) {
					p.setContasReceber(contasReceber);
					//System.out.println(p.getContasReceber().getId());
					contasReceberParcelasService.inserirAlterar(p);
				}
				//ExibirMensagem.exibirMensagem("Contas a Receber inserida com sucesso!");
			} else {
				//ExibirMensagem.exibirMensagem("Contas a Receber já está inserida!");
			}
		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
			e.printStackTrace();
		}
	}

	public void baixarParcela(ContasReceberParcelas contasReceberParcelas) {
		if (contasReceberParcelas != null) {
			if (contasReceberParcelas.getId() != null) {
				contasReceberParcelasService.inserirAlterar(contasReceberParcelas);
				contasReceberParcelas = new ContasReceberParcelas();
				ExibirMensagem.exibirMensagem("Baixa efetuada com sucesso!");
				FecharDialog.fecharDialogContasReceberBaixa();
			} else {
				ExibirMensagem.exibirMensagem("Baixa efetuada com sucesso!");
				FecharDialog.fecharDialogContasReceberBaixa();
			}
		}
	}
	
	public void cancelarBaixarParcela(ContasReceberParcelas parc) {
		if (parc != null) {
			parc.setValorPago(null);
			parc.setDataPagamento(null);
			if (parc.getId() != null) {
				contasReceberParcelasService.inserirAlterar(parc);
				ExibirMensagem.exibirMensagem("Cancelamento de Baixa efetuada com sucesso!");

			} else {
				ExibirMensagem.exibirMensagem("Cancelamento de Baixa efetuada com sucesso!");
			}
		}
	}
	
	public List<ContasReceberParcelas> carregarContaReceber(ContasReceber contasReceber) {		
		List<ContasReceberParcelas> listaParcelas = daoContasReceberParcelas.listar(ContasReceberParcelas.class,
				"contasReceber.id=" + contasReceber.getId());
	return listaParcelas;
	}

	public void excluirContasReceber(ContasReceber contasReceber) {
	
		if (contasReceber != null && contasReceber.getId() != null) {
			
			List<ContasReceberParcelas> listaParcelas = daoContasReceberParcelas.listar(ContasReceberParcelas.class,"contasReceber.id"+contasReceber.getId());
			
			for (ContasReceberParcelas p : listaParcelas) {
				p.setStatus(false);
				contasReceberParcelasService.inserirAlterar(p);
			}
			contasReceber.setStatus(false);
			this.inserirAlterar(contasReceber);
			ExibirMensagem.exibirMensagem("Conta a Receber excluída com sucesso!");
		} else {
			ExibirMensagem.exibirMensagem(
					"Ocorreu um erro ao excluir, tente pesquisar novamente a Conta a Receber que pretende excluir!");
		}
	}

}
