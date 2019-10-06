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
import lista.modelo.EntidadeLista;
import lista.modelo.EntidadeServicoLista;
import lista.service.CasaOracaoService;
import util.ExibirMensagem;
import util.FecharDialog;
import util.Mensagem;
import dao.GenericDAO;

@ViewScoped
@Named("listarListasMB")
public class ListarListasMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private EntidadeCasaOracao casaOracao;
	private List<EntidadeCasaOracao> casaOracaoBusca;
	private List<EntidadeCasaOracao> listCasaOracao;
	private List<EntidadeLista> listasCidade;
	
	private List<EntidadeServicoLista> servicosLista;

	private GenericDAO<EntidadeServicoLista> daoServicosLista; // faz as buscas
	
	@Inject
	private GenericDAO<EntidadeLista> daoListas; // faz as buscas

	@Inject
	private GenericDAO<EntidadeCasaOracao> daoCasaOracao; // faz as buscas

	@Inject
	private CasaOracaoService casaOracaoService; // inserir no banco

	@PostConstruct
	public void inicializar() {

		casaOracao = new EntidadeCasaOracao();

		listCasaOracao = new ArrayList<>();
		listCasaOracao = daoCasaOracao.listaComStatusSemCodigoCasaOracao(EntidadeCasaOracao.class);
		//System.out.println(listCasaOracao.size());
		casaOracaoBusca = new ArrayList<>();

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		HttpSession session = (HttpSession) request.getSession();
		if (session.getAttribute("cidadeSelecionada") != null) {
			listasCidade = daoListas.listarSemCodigoCasaOracao(EntidadeLista.class,
					"codigoCasaOracao='" + ((String) session.getAttribute("cidadeSelecionada"))+"' order by dataLista desc");
			casaOracao = daoCasaOracao.listarSemCodigoCasaOracao(EntidadeCasaOracao.class,
					"codigoCasaOracao='" + ((String) session.getAttribute("cidadeSelecionada"))+"'").get(0);
		}
		if (session.getAttribute("listaSelecionada") != null) {
			servicosLista = daoServicosLista.listarSemCodigoCasaOracao(EntidadeServicoLista.class,
					"codigoCasaOracao='" + ((String) session.getAttribute("cidadeSelecionada"))+"' order by dataLista desc");
		}
	}

	public String buscarListasCidade(EntidadeCasaOracao ca) {
		System.out.println("CIdade " + ca.getCidade());
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		HttpSession session = (HttpSession) request.getSession();
		session.setAttribute("cidadeSelecionada", ca.getCodigoCasaOracao());

		return "listasCidade.jsf?redirect=true";
	}
	
	public String buscarServicosLista(EntidadeLista ca) {
		
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		HttpSession session = (HttpSession) request.getSession();
		session.setAttribute("listaSelecionada", ca.getCodigoCasaOracao());

		return "lista.jsf?redirect=true";
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

	public List<EntidadeLista> getListasCidade() {
		return listasCidade;
	}

	public void setListasCidade(List<EntidadeLista> listasCidade) {
		this.listasCidade = listasCidade;
	}
	

}
