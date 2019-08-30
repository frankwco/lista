package lista.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lista.modelo.EntidadeTipoServico;
import lista.service.TipoServicoService;
import util.ExibirMensagem;
import util.FecharDialog;
import util.Mensagem;
import dao.GenericDAO;

@ViewScoped
@Named("tipoServicoMB")
public class TipoServicoMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private EntidadeTipoServico tipoServico;
	private List<EntidadeTipoServico> tipoServicoBusca;
	private List<EntidadeTipoServico> listTipoServico;

	@Inject
	private GenericDAO<EntidadeTipoServico> daoTipoServico; // faz as buscas

	@Inject
	private TipoServicoService tipoServicoService; // inserir no banco

	@PostConstruct
	public void inicializar() {

		tipoServico = new EntidadeTipoServico();

		listTipoServico = new ArrayList<>();
		listTipoServico = daoTipoServico.listaComStatus(EntidadeTipoServico.class);
		tipoServicoBusca = new ArrayList<>();

	}

	public void preencherLista(EntidadeTipoServico t) {
		this.tipoServico = t;

	}

	public void inativar(EntidadeTipoServico t) {
		t.setStatus("excluido");
		tipoServicoService.inserirAlterar(t);
		// tipoServicoService.update(" EntidadeTipoServico set status = false where id =
		// " +
		// t.getId());
		criarNovoObjeto();
		ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
		carregarLista();
	}

	public void salvar() {

		try {
			if (tipoServico.getId() == null) {
				String casaOracaoLogada = "";
				HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
						.getRequest();
				HttpSession session = (HttpSession) request.getSession();
				if (session.getAttribute("casaOracaoLogada") != null) {
					casaOracaoLogada = (String) session.getAttribute("casaOracaoLogada");
				}
				tipoServico.setCodigoCasaOracao(casaOracaoLogada);
				// tipoServico.setStatus(true);
				tipoServicoService.inserirAlterar(tipoServico);

			} else {
				// tipoServico.setStatus(true);
				tipoServicoService.inserirAlterar(tipoServico);
			}

			criarNovoObjeto();
			ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
			FecharDialog.fecharDialogAtividade();
			carregarLista();

		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
			e.printStackTrace();
		}
	}

	public void criarNovoObjeto() {
		tipoServico = new EntidadeTipoServico();
	}

	public void carregarLista() {
		listTipoServico = daoTipoServico.listaComStatus(EntidadeTipoServico.class);
	}

	public EntidadeTipoServico getTipoServico() {
		return tipoServico;
	}

	public void setTipoServico(EntidadeTipoServico tipoServico) {
		this.tipoServico = tipoServico;
	}

	public List<EntidadeTipoServico> getTipoServicoBusca() {
		return tipoServicoBusca;
	}

	public void setTipoServicoBusca(List<EntidadeTipoServico> tipoServicoBusca) {
		this.tipoServicoBusca = tipoServicoBusca;
	}

	public List<EntidadeTipoServico> getListTipoServico() {
		return listTipoServico;
	}

	public void setListTipoServico(List<EntidadeTipoServico> listTipoServico) {
		this.listTipoServico = listTipoServico;
	}

}
