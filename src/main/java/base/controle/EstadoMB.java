package base.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import base.modelo.Estado;
import base.service.EstadoService;
import util.ExibirMensagem;
import util.FecharDialog;
import util.Mensagem;
import dao.GenericDAO;

@ViewScoped
@Named("estadoMB")
public class EstadoMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Estado estado;
	private List<Estado> estadoBusca;
	private List<Estado> listEstado;

	@Inject
	private GenericDAO<Estado> daoEstado; // faz as buscas

	@Inject
	private EstadoService estadoService; // inserir no banco

	@PostConstruct
	public void inicializar() {

		estado = new Estado();

		listEstado = new ArrayList<>();
		listEstado = daoEstado.listaComStatus(Estado.class);
		estadoBusca = new ArrayList<>();

	}

	public void preencherListaEstado(Estado t) {
		this.estado = t;

	}
	
	public List<Estado> completar(String str) {
		List<Estado> list = daoEstado.listaComStatus(Estado.class);
		List<Estado> selecionados = new ArrayList<>();
		for (Estado cur : list) {
			if (cur.getNome().toLowerCase().startsWith(str.toLowerCase())) {
				selecionados.add(cur);
			}
		}
		return selecionados;
	}

	public void inativar(Estado t) {
		t.setStatus(false);
		estadoService.inserirAlterar(t);
		// estadoService.update(" Estado set status = false where id = " +
		// t.getId());
		criarNovoObjeto();
		ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
		carregarLista();
	}

	public void salvar() {

		try {
			if (estado.getId() == null) {
				estado.setStatus(true);
				estadoService.inserirAlterar(estado);

			} else {
				estado.setStatus(true);
				estadoService.inserirAlterar(estado);
			}

			criarNovoObjeto();
			ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
			FecharDialog.fecharDialogEstado();
			carregarLista();

		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
			e.printStackTrace();
		}

	}

	public void criarNovoObjeto() {
		estado = new Estado();
	}

	public void carregarLista() {
		listEstado = daoEstado.listaComStatus(Estado.class);
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Estado> getEstadoBusca() {
		return estadoBusca;
	}

	public void setEstadoBusca(List<Estado> estadoBusca) {
		this.estadoBusca = estadoBusca;
	}

	public List<Estado> getListEstado() {
		return listEstado;
	}

	public void setListEstado(List<Estado> listEstado) {
		this.listEstado = listEstado;
	}
	
}
