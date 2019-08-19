package base.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import base.modelo.GrupoResiduo;
import base.service.GrupoResiduoService;
import util.ExibirMensagem;
import util.FecharDialog;
import util.Mensagem;
import dao.GenericDAO;

@ViewScoped
@Named("grupoResiduoMB")
public class GrupoResiduoMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private GrupoResiduo grupoResiduo;
	private List<GrupoResiduo> grupoResiduoBusca;
	private List<GrupoResiduo> listGrupoResiduo;

	@Inject
	private GenericDAO<GrupoResiduo> daoGrupoResiduo; // faz as buscas

	@Inject
	private GrupoResiduoService grupoResiduoService; // inserir no banco

	@PostConstruct
	public void inicializar() {

		grupoResiduo = new GrupoResiduo();

		listGrupoResiduo = new ArrayList<>();
		listGrupoResiduo = daoGrupoResiduo.listaComStatus(GrupoResiduo.class, "ordem");
		grupoResiduoBusca = new ArrayList<>();

	}

	public void preencherListaEstado(GrupoResiduo t) {
		this.grupoResiduo = t;

	}

	public List<GrupoResiduo> completar(String str) {
		List<GrupoResiduo> list = daoGrupoResiduo.listaComStatus(GrupoResiduo.class,"ordem");
		List<GrupoResiduo> selecionados = new ArrayList<>();
		for (GrupoResiduo cur : list) {
			if (cur.getDescricao().toLowerCase().startsWith(str.toLowerCase())) {
				selecionados.add(cur);
			}
		}
		return selecionados;
	}

	public void inativar(GrupoResiduo t) {
		t.setStatus(false);
		grupoResiduoService.inserirAlterar(t);
		// grupoResiduoService.update(" GrupoResiduo set status = false where id = " +
		// t.getId());
		criarNovoObjeto();
		ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
		carregarLista();
	}

	public void salvar() {

		try {
			if (grupoResiduo.getId() == null) {
				grupoResiduo.setStatus(true);
				grupoResiduoService.inserirAlterar(grupoResiduo);

			} else {
				grupoResiduo.setStatus(true);
				grupoResiduoService.inserirAlterar(grupoResiduo);
			}

			criarNovoObjeto();
			ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
			FecharDialog.fecharDialogGrupoResiduo();
			carregarLista();

		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
			e.printStackTrace();
		}

	}

	public void criarNovoObjeto() {
		grupoResiduo = new GrupoResiduo();
	}

	public void carregarLista() {
		listGrupoResiduo = daoGrupoResiduo.listaComStatus(GrupoResiduo.class,"ordem");
	}

	public GrupoResiduo getGrupoResiduo() {
		return grupoResiduo;
	}

	public void setGrupoResiduo(GrupoResiduo grupoResiduo) {
		this.grupoResiduo = grupoResiduo;
	}

	public List<GrupoResiduo> getGrupoResiduoBusca() {
		return grupoResiduoBusca;
	}

	public void setGrupoResiduoBusca(List<GrupoResiduo> grupoResiduoBusca) {
		this.grupoResiduoBusca = grupoResiduoBusca;
	}

	public List<GrupoResiduo> getListGrupoResiduo() {
		return listGrupoResiduo;
	}

	public void setListGrupoResiduo(List<GrupoResiduo> listGrupoResiduo) {
		this.listGrupoResiduo = listGrupoResiduo;
	}

}
