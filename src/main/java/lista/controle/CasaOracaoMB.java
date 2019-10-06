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

import lista.modelo.EntidadeCasaOracao;
import lista.service.CasaOracaoService;
import util.ExibirMensagem;
import util.FecharDialog;
import util.Mensagem;
import dao.GenericDAO;

@ViewScoped
@Named("casaOracaoMB")
public class CasaOracaoMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private EntidadeCasaOracao casaOracao;
	private List<EntidadeCasaOracao> casaOracaoBusca;
	private List<EntidadeCasaOracao> listCasaOracao;

	@Inject
	private GenericDAO<EntidadeCasaOracao> daoCasaOracao; // faz as buscas

	@Inject
	private CasaOracaoService casaOracaoService; // inserir no banco

	@PostConstruct
	public void inicializar() {

		casaOracao = new EntidadeCasaOracao();

		listCasaOracao = new ArrayList<>();
		listCasaOracao = daoCasaOracao.listaComStatus(EntidadeCasaOracao.class);
		casaOracaoBusca = new ArrayList<>();

	}

	public void preencherLista(EntidadeCasaOracao t) {
		this.casaOracao = t;

	}

	public void inativar(EntidadeCasaOracao t) {
		t.setStatus("excluido");
		casaOracaoService.inserirAlterar(t);
		// casaOracaoService.update(" EntidadeCasaOracao set status = false where id =
		// " +
		// t.getId());
		criarNovoObjeto();
		ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
		carregarLista();
	}

	public void salvar() {

		try {
			if (casaOracao.getId() == null) {
				String casaOracaoLogada = "";
				HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
						.getRequest();
				HttpSession session = (HttpSession) request.getSession();
				if (session.getAttribute("casaOracaoLogada") != null) {
					casaOracaoLogada = (String) session.getAttribute("casaOracaoLogada");
				}
				casaOracao.setCodigoCasaOracao(casaOracaoLogada);
				// casaOracao.setStatus(true);
				casaOracaoService.inserirAlterar(casaOracao);

			} else {
				// casaOracao.setStatus(true);
				casaOracaoService.inserirAlterar(casaOracao);
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
		casaOracao = new EntidadeCasaOracao();
	}

	public void carregarLista() {
		listCasaOracao = daoCasaOracao.listaComStatus(EntidadeCasaOracao.class);
	}

	public EntidadeCasaOracao getCasaOracao() {
		return casaOracao;
	}

	public void setCasaOracao(EntidadeCasaOracao casaOracao) {
		this.casaOracao = casaOracao;
	}

	public List<EntidadeCasaOracao> getCasaOracaoBusca() {
		return casaOracaoBusca;
	}

	public void setCasaOracaoBusca(List<EntidadeCasaOracao> casaOracaoBusca) {
		this.casaOracaoBusca = casaOracaoBusca;
	}

	public List<EntidadeCasaOracao> getListCasaOracao() {
		return listCasaOracao;
	}

	public void setListCasaOracao(List<EntidadeCasaOracao> listCasaOracao) {
		this.listCasaOracao = listCasaOracao;
	}

	

}
