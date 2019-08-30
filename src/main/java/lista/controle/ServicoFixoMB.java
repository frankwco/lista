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


import lista.modelo.EntidadeServicoFixo;
import lista.service.ServicoFixoService;
import util.ExibirMensagem;
import util.FecharDialog;
import util.Mensagem;
import dao.GenericDAO;

@ViewScoped
@Named("servicoFixoMB")
public class ServicoFixoMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private EntidadeServicoFixo servicoFixo;
	private List<EntidadeServicoFixo> servicoFixoBusca;
	private List<EntidadeServicoFixo> listServicoFixo;
	
	@Inject
	private GenericDAO<EntidadeServicoFixo> daoServicoFixo; // faz as buscas

	@Inject
	private ServicoFixoService servicoFixoService; // inserir no banco

	@PostConstruct
	public void inicializar() {

		servicoFixo = new EntidadeServicoFixo();

		listServicoFixo = new ArrayList<>();
		listServicoFixo = daoServicoFixo.listaComStatus(EntidadeServicoFixo.class);
		servicoFixoBusca = new ArrayList<>();

	}

	public void preencherLista(EntidadeServicoFixo t) {
		this.servicoFixo = t;

	}

	public void inativar(EntidadeServicoFixo t) {
		t.setStatus("excluido");
		servicoFixoService.inserirAlterar(t);
		// servicoFixoService.update(" EntidadeServicoFixo set status = false where id = " +
		// t.getId());
		criarNovoObjeto();
		ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
		carregarLista();
	}

	public void salvar() {

		try {
			if (servicoFixo.getId() == null) {
				String casaOracaoLogada="";
				HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
						.getRequest();
				HttpSession session = (HttpSession) request.getSession();
				if (session.getAttribute("casaOracaoLogada") != null) {
					casaOracaoLogada = (String) session.getAttribute("casaOracaoLogada");
				}
				servicoFixo.setCodigoCasaOracao(casaOracaoLogada);
				//servicoFixo.setStatus(true);
				servicoFixoService.inserirAlterar(servicoFixo);

			} else {
				//servicoFixo.setStatus(true);
				servicoFixoService.inserirAlterar(servicoFixo);
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
	
	public List<EntidadeServicoFixo> completarAtividade(String str) {
		List<EntidadeServicoFixo> list = daoServicoFixo.listaComStatus(EntidadeServicoFixo.class);
		List<EntidadeServicoFixo> selecionados = new ArrayList<>();
		for (EntidadeServicoFixo cur : list) {
			if (cur.getDescricao().toLowerCase().startsWith(str)) {
				selecionados.add(cur);
			}
		}
		return selecionados;
	}

	public void criarNovoObjeto() {
		servicoFixo = new EntidadeServicoFixo();
	}

	public void carregarLista() {
		listServicoFixo = daoServicoFixo.listaComStatus(EntidadeServicoFixo.class);
	}

	public EntidadeServicoFixo getServicoFixo() {
		return servicoFixo;
	}

	public void setServicoFixo(EntidadeServicoFixo servicoFixo) {
		this.servicoFixo = servicoFixo;
	}

	public List<EntidadeServicoFixo> getServicoFixoBusca() {
		return servicoFixoBusca;
	}

	public void setServicoFixoBusca(List<EntidadeServicoFixo> servicoFixoBusca) {
		this.servicoFixoBusca = servicoFixoBusca;
	}

	public List<EntidadeServicoFixo> getListServicoFixo() {
		return listServicoFixo;
	}

	public void setListServicoFixo(List<EntidadeServicoFixo> listServicoFixo) {
		this.listServicoFixo = listServicoFixo;
	}

	
		
	
}
