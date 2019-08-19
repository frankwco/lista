package base.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import base.modelo.Rota;
import base.service.RotaService;
import util.ExibirMensagem;
import util.FecharDialog;
import util.Mensagem;
import dao.GenericDAO;

@ViewScoped
@Named("rotaMB")
public class RotaMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Rota rota;
	private List<Rota> rotaBusca;
	private List<Rota> listRota;

	@Inject
	private GenericDAO<Rota> daoRota; // faz as buscas

	@Inject
	private RotaService rotaService; // inserir no banco

	@PostConstruct
	public void inicializar() {

		rota = new Rota();

		listRota = new ArrayList<>();
		listRota = daoRota.listaComStatus(Rota.class);
		rotaBusca = new ArrayList<>();

	}

	public void preencherLista(Rota t) {
		this.rota = t;

	}
	
	public List<Rota> completar(String str) {
		List<Rota> list = daoRota.listaComStatus(Rota.class);
		List<Rota> selecionados = new ArrayList<>();
		for (Rota cur : list) {
			if (cur.getNome().toLowerCase().startsWith(str.toLowerCase())) {
				selecionados.add(cur);
			}
		}
		return selecionados;
	}

	public void inativar(Rota t) {
		t.setStatus(false);
		rotaService.inserirAlterar(t);
		// rotaService.update(" Rota set status = false where id = " +
		// t.getId());
		criarNovoObjeto();
		ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
		carregarLista();
	}

	public void salvar() {

		try {
			if (rota.getId() == null) {
				rota.setStatus(true);
				rotaService.inserirAlterar(rota);

			} else {
				rota.setStatus(true);
				rotaService.inserirAlterar(rota);
			}

			criarNovoObjeto();
			ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
			FecharDialog.fecharDialogRota();
			carregarLista();

		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
			e.printStackTrace();
		}

	}

	public void criarNovoObjeto() {
		rota = new Rota();
	}

	public void carregarLista() {
		listRota = daoRota.listaComStatus(Rota.class);
	}

	public Rota getRota() {
		return rota;
	}

	public void setRota(Rota rota) {
		this.rota = rota;
	}

	public List<Rota> getRotaBusca() {
		return rotaBusca;
	}

	public void setRotaBusca(List<Rota> rotaBusca) {
		this.rotaBusca = rotaBusca;
	}

	public List<Rota> getListRota() {
		return listRota;
	}

	public void setListRota(List<Rota> listRota) {
		this.listRota = listRota;
	}

	
}
