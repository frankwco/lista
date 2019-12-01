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


import lista.modelo.EntidadeServicoComum;
import lista.service.ServicoComumService;
import util.ExibirMensagem;
import util.FecharDialog;
import util.Mensagem;
import dao.GenericDAO;

@ViewScoped
@Named("servicoComumMB")
public class ServicoComumMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private EntidadeServicoComum servicoComum;
	private List<EntidadeServicoComum> servicoComumBusca;
	private List<EntidadeServicoComum> listServicoComum;
	
	@Inject
	private GenericDAO<EntidadeServicoComum> daoServicoComum; // faz as buscas

	@Inject
	private ServicoComumService servicoComumService; // inserir no banco

	@PostConstruct
	public void inicializar() {

		servicoComum = new EntidadeServicoComum();

		listServicoComum = new ArrayList<>();
		listServicoComum = daoServicoComum.listaComStatus(EntidadeServicoComum.class);
		servicoComumBusca = new ArrayList<>();

	}
	
	
	 public void selecionarTodosMes() {
//		 System.out.println("Seleção de todos");
		 /*  39 */     if ((this.servicoComum.getJaneiro() != null) && (this.servicoComum.getJaneiro().booleanValue() == true)) {
		 /*  40 */       this.servicoComum.setJaneiro(Boolean.valueOf(false));
		 /*  41 */       this.servicoComum.setFevereiro(Boolean.valueOf(false));
		 /*  42 */       this.servicoComum.setMarco(Boolean.valueOf(false));
		 /*  43 */       this.servicoComum.setAbril(Boolean.valueOf(false));
		 /*  44 */       this.servicoComum.setMaio(Boolean.valueOf(false));
		 /*  45 */       this.servicoComum.setJunho(Boolean.valueOf(false));
		 /*  46 */       this.servicoComum.setJulho(Boolean.valueOf(false));
		 /*  47 */       this.servicoComum.setAgosto(Boolean.valueOf(false));
		 /*  48 */       this.servicoComum.setSetembro(Boolean.valueOf(false));
		 /*  49 */       this.servicoComum.setOutubro(Boolean.valueOf(false));
		 /*  50 */       this.servicoComum.setNovembro(Boolean.valueOf(false));
		 /*  51 */       this.servicoComum.setDezembro(Boolean.valueOf(false));
		 /*     */     } else {
		 /*  53 */       this.servicoComum.setJaneiro(Boolean.valueOf(true));
		 /*  54 */       this.servicoComum.setFevereiro(Boolean.valueOf(true));
		 /*  55 */       this.servicoComum.setMarco(Boolean.valueOf(true));
		 /*  56 */       this.servicoComum.setAbril(Boolean.valueOf(true));
		 /*  57 */       this.servicoComum.setMaio(Boolean.valueOf(true));
		 /*  58 */       this.servicoComum.setJunho(Boolean.valueOf(true));
		 /*  59 */       this.servicoComum.setJulho(Boolean.valueOf(true));
		 /*  60 */       this.servicoComum.setAgosto(Boolean.valueOf(true));
		 /*  61 */       this.servicoComum.setSetembro(Boolean.valueOf(true));
		 /*  62 */       this.servicoComum.setOutubro(Boolean.valueOf(true));
		 /*  63 */       this.servicoComum.setNovembro(Boolean.valueOf(true));
		 /*  64 */       this.servicoComum.setDezembro(Boolean.valueOf(true));
		 /*     */     }
		 /*     */   }

	public void preencherLista(EntidadeServicoComum t) {
		this.servicoComum = t;

	}

	public void inativar(EntidadeServicoComum t) {
		t.setStatus("excluido");
		servicoComumService.inserirAlterar(t);
		// servicoComumService.update(" EntidadeServicoComum set status = false where id = " +
		// t.getId());
		criarNovoObjeto();
		ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
		carregarLista();
	}

	public void salvar() {

		try {
			if (servicoComum.getId() == null) {
				String casaOracaoLogada="";
				HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
						.getRequest();
				HttpSession session = (HttpSession) request.getSession();
				if (session.getAttribute("casaOracaoLogada") != null) {
					casaOracaoLogada = (String) session.getAttribute("casaOracaoLogada");
				}
				servicoComum.setCodigoCasaOracao(casaOracaoLogada);
				//servicoComum.setStatus(true);
				servicoComumService.inserirAlterar(servicoComum);

			} else {
				//servicoComum.setStatus(true);
				servicoComumService.inserirAlterar(servicoComum);
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
	
	public List<EntidadeServicoComum> completarAtividade(String str) {
		List<EntidadeServicoComum> list = daoServicoComum.listaComStatus(EntidadeServicoComum.class);
		List<EntidadeServicoComum> selecionados = new ArrayList<>();
		for (EntidadeServicoComum cur : list) {
			if (cur.getDescricao().toLowerCase().startsWith(str)) {
				selecionados.add(cur);
			}
		}
		return selecionados;
	}

	public void criarNovoObjeto() {
		servicoComum = new EntidadeServicoComum();
	}

	public void carregarLista() {
		listServicoComum = daoServicoComum.listaComStatus(EntidadeServicoComum.class);
	}

	public EntidadeServicoComum getServicoComum() {
		return servicoComum;
	}

	public void setServicoComum(EntidadeServicoComum servicoComum) {
		this.servicoComum = servicoComum;
	}

	public List<EntidadeServicoComum> getServicoComumBusca() {
		return servicoComumBusca;
	}

	public void setServicoComumBusca(List<EntidadeServicoComum> servicoComumBusca) {
		this.servicoComumBusca = servicoComumBusca;
	}

	public List<EntidadeServicoComum> getListServicoComum() {
		return listServicoComum;
	}

	public void setListServicoComum(List<EntidadeServicoComum> listServicoComum) {
		this.listServicoComum = listServicoComum;
	}

		
	
}
