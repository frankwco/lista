package base.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import base.modelo.Cidade;
import base.service.CidadeService;
import util.ExibirMensagem;
import util.FecharDialog;
import util.Mensagem;
import dao.GenericDAO;

@ViewScoped
@Named("cidadeMB")
public class CidadeMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Cidade cidade;
	private List<Cidade> cidadeBusca;
	private List<Cidade> listCidade;
	private List<Cidade> listaCidadesSelecionadas;

	@Inject
	private GenericDAO<Cidade> daoCidade; // faz as buscas

	@Inject
	private CidadeService cidadeService; // inserir no banco

	@PostConstruct
	public void inicializar() {

		cidade = new Cidade();

		listCidade = new ArrayList<>();
		listCidade = daoCidade.listaComStatus(Cidade.class);
		cidadeBusca = new ArrayList<>();
		listaCidadesSelecionadas=daoCidade.listar(Cidade.class,"selecionada is true");

	}

	public void preencherLista(Cidade t) {
		this.cidade = t;

	}
	
	public List<Cidade> completar(String str) {
		List<Cidade> list = daoCidade.listar(Cidade.class,"selecionada is true");
		List<Cidade> selecionados = new ArrayList<>();
		for (Cidade cur : list) {
			if (cur.getNome().toLowerCase().startsWith(str.toLowerCase())) {
				selecionados.add(cur);
			}
		}
		return selecionados;
	}

	public void inativar(Cidade t) {
		t.setStatus(false);
		cidadeService.inserirAlterar(t);
		// cidadeService.update(" Cidade set status = false where id = " +
		// t.getId());
		criarNovoObjeto();
		ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
		carregarLista();
	}

	public void salvar() {

		try {
			if (cidade.getId() == null) {
				cidade.setStatus(true);
				cidadeService.inserirAlterar(cidade);

			} else {
				cidade.setStatus(true);
				cidadeService.inserirAlterar(cidade);
			}

			criarNovoObjeto();
			ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
			FecharDialog.fecharDialogCidade();
			carregarLista();

		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
			e.printStackTrace();
		}

	}

	public void criarNovoObjeto() {
		cidade = new Cidade();
	}

	public void carregarLista() {
		listCidade = daoCidade.listaComStatus(Cidade.class);
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public List<Cidade> getCidadeBusca() {
		return cidadeBusca;
	}

	public void setCidadeBusca(List<Cidade> cidadeBusca) {
		this.cidadeBusca = cidadeBusca;
	}

	public List<Cidade> getListCidade() {
		return listCidade;
	}

	public void setListCidade(List<Cidade> listCidade) {
		this.listCidade = listCidade;
	}

	public List<Cidade> getListaCidadesSelecionadas() {
		return listaCidadesSelecionadas;
	}

	public void setListaCidadesSelecionadas(List<Cidade> listaCidadesSelecionadas) {
		this.listaCidadesSelecionadas = listaCidadesSelecionadas;
	}

	
	
}
