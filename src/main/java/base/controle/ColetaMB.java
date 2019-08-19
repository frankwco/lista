package base.controle;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.hibernate.Session;

import base.modelo.GrupoLancamento;
import base.modelo.GrupoResiduo;
import base.modelo.ItensColeta;
import base.modelo.ItensInformacaoFinanceiraGrupo;
import base.modelo.ItensInformacaoFinanceiraPontoColeta;
import base.modelo.PontoColeta;
import base.modelo.Usuario;
import base.modelo.Cidade;
import base.modelo.Coleta;
import base.modelo.ContasReceberParcelas;
import base.modelo.FechamentoMes;
import base.service.ItensColetaService;
import base.service.ColetaService;
import util.ChamarRelatorio;
import util.ConverteStringDate;
import util.ExibirMensagem;
import util.FecharDialog;
import util.FormataDataMB;
import util.Mensagem;
import dao.GenericDAO;

@ViewScoped
@Named("coletaMB")
public class ColetaMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Coleta coleta;
	private List<Coleta> coletaBusca;
	private List<Coleta> listColeta;
	private List<ItensColeta> listItensColeta;
	private List<ItensColeta> listItensColetaConsulta = new ArrayList<>();
	private List<ItensColeta> listItensColetaConsultaTabela = new ArrayList<>();
	private Date dataInicialConsulta = new Date();
	private Date dataFinalConsulta = new Date();
	private GrupoLancamento grupoLancamentoSelecionado;
	private ItensColeta itensColeta;
	List<GrupoResiduo> listaGrupoResiduos;
	private Cidade cidade;
	private PontoColeta ultimoPontoColeta;

	private String ocultarZeros = "Sim";

	private List<PontoColeta> listaPontos;

	@Inject
	private EntityManager manager;

	@Inject
	private GenericDAO<Coleta> daoColeta; // faz as buscas

	@Inject
	private GenericDAO<GrupoResiduo> daoGrupoResiduos; // faz as buscas

	@Inject
	private GenericDAO<ItensInformacaoFinanceiraGrupo> daoItensInformacaoFinanceiraGrupo; // faz
																							// as
																							// buscas

	@Inject
	private GenericDAO<ItensInformacaoFinanceiraPontoColeta> daoItensInformacaoFinanceiraPontoColeta; // faz
	// as
	// buscas
	@Inject
	private GenericDAO<ItensColeta> daoItensColeta; // faz as buscas

	@Inject
	private ColetaService coletaService; // inserir no banco

	@Inject
	private ItensColetaService itensColetaService; // inserir no banco

	@Inject
	private GenericDAO<PontoColeta> daoPontoColeta; // faz as buscas

	@PostConstruct
	public void inicializar() {

		itensColeta = new ItensColeta();
		listColeta = new ArrayList<>();
		//listColeta = daoColeta.listaComStatus(Coleta.class);
		coletaBusca = new ArrayList<>();
		listaGrupoResiduos = daoGrupoResiduos.listaComStatus(GrupoResiduo.class, "ordem");
		this.criarNovoObjeto();
		buscarPontosColeta();
	}
	
	public void verificarValorColeta(Double it) {
		if(it>30.) {
			ExibirMensagem.exibirMensagemErro("Verifique a quantidade coletada, está maior que 30KG");
		}
	}

	public void limparBaseLancamentos() {

		List<Coleta> lc = daoColeta.listar(Coleta.class,
				"dataColeta between '2019-01-01' and '2019-05-31' and pontoColeta.cliente.id!=447 and pontoColeta.cliente.id!=1542 and pontoColeta.cliente.id!=1540 and pontoColeta.cliente.id!=885");
		for (Coleta c : lc) {

			List<ItensColeta> lic = daoItensColeta.listar(ItensColeta.class, "coleta.id=" + c.getId());
			for (ItensColeta ic : lic) {
				ic.setStatus(false);
				itensColetaService.inserirAlterar(ic);
			}
			c.setStatus(false);
			coletaService.inserirAlterar(c);
		}

		ExibirMensagem.exibirMensagem("Coleta removida com sucesso!");
	}

	public void buscarPontosColeta() {
		System.out.println("No buscar Pontos coleta");
		if (cidade == null) {
			listaPontos = daoPontoColeta.listaComStatus(PontoColeta.class);
		} else {
			System.out.println("No buscar Pontos coleta ELSE");
			listaPontos = daoPontoColeta.listar(PontoColeta.class, "cidade.id=" + cidade.getId());
		}
	}

	public List<PontoColeta> completar(String str) {
		if (str == null) {
			str = "";
		}
		List<PontoColeta> list;
		if (cidade == null) {

			list = daoPontoColeta.listaComStatus(PontoColeta.class);
		} else {
			list = daoPontoColeta.listar(PontoColeta.class, "cidade.id=" + cidade.getId());
		}
		List<PontoColeta> selecionados = new ArrayList<>();
		for (PontoColeta cur : list) {
			if (cur.getNomeRazaoSocial().toLowerCase().contains(str)
					|| cur.getNomeFantasia().toLowerCase().contains(str)) {
				selecionados.add(cur);
			}
		}
		return selecionados;
	}

	public String getTotalPesoColetado() {
		Double total = 0.;
		for (GrupoResiduo grupo : listaGrupoResiduos) {
			grupo.setTotalColetado(0.);
		}

		for (ItensColeta c : listItensColetaConsulta) {
			total += c.getQuantidadeColetada();
			for (GrupoResiduo grupo : listaGrupoResiduos) {
				if (c.getGrupoResiduo().getId().equals(grupo.getId())) {
					grupo.setTotalColetado(grupo.getTotalColetado() + c.getQuantidadeColetada());
				}
			}
		}

		return new DecimalFormat("#,###.000").format(total);
	}

	public String getTotalColetas() {
		if (listItensColetaConsulta != null) {
			List<Long> ll = new ArrayList<>();
			for (ItensColeta its : listItensColetaConsulta) {
				if (!ll.contains(its.getColeta().getId())) {
					ll.add(its.getColeta().getId());
				}
			}

			return String.valueOf(ll.size());
		} else {
			return "0";
		}
	}

	public void relatorioPontosSemColeta() {

		HashMap parametro = new HashMap<>();
		parametro.put("DATA_INICIAL", ConverteStringDate.retornaData(dataInicialConsulta));
		parametro.put("DATA_FINAL", ConverteStringDate.retornaData(dataFinalConsulta));
		parametro.put("DATA_INICIAL_MOSTRAR", ConverteStringDate.retornaDataddMMyyyyBarra(dataInicialConsulta));
		parametro.put("DATA_FINAL_MOSTRAR", ConverteStringDate.retornaDataddMMyyyyBarra(dataFinalConsulta));
		ChamarRelatorio ch = new ChamarRelatorio("pontos_sem_coleta.jasper", parametro, "pontosSemColeta");
		Session sessions = manager.unwrap(Session.class);
		sessions.doWork(ch);
	}

	public void consultarItensLancamentoColetas() {

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (dataInicialConsulta != null && dataFinalConsulta != null) {
			String query = "coleta.dataColeta between '" + format.format(dataInicialConsulta) + "' and '"
					+ format.format(dataFinalConsulta) + "'";
			if (coleta != null && coleta.getPontoColeta() != null) {
				query += " and coleta.pontoColeta.id=" + coleta.getPontoColeta().getId();
			}

			listItensColetaConsulta = daoItensColeta.listar(ItensColeta.class, query);

			if (ocultarZeros.equals("Sim")) {
				query += " and quantidadeColetada>0";
				listItensColetaConsultaTabela = daoItensColeta.listar(ItensColeta.class, query);
			} else {
				listItensColetaConsultaTabela = listItensColetaConsulta;
			}

		} else {
			ExibirMensagem.exibirMensagem("Inserir a Data Inicial e a Data Final");
		}

	}

	public void salvarItemColeta(ItensColeta it) {
		if (it != null) {
			if (it.getId() != null) {
				itensColetaService.inserirAlterar(it);
				ExibirMensagem.exibirMensagem("Item de Coleta alterado com sucesso!");
			}
		}
	}

	public String converterData(Date dt) {
		if (dt != null) {
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			return format.format(dt);

		} else {
			return "";
		}
	}

	public void buscarColetas() {
		listColeta = daoColeta.listaComStatus(Coleta.class);
	}

	public void adicionarItemLancamento() {
		listItensColeta.add(itensColeta);
		itensColeta = new ItensColeta();
		ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
		FecharDialog.fecharDialogInserirLancamento();
	}

	public void verificaValor(Double valor) {
		System.out.println(valor);
	}

	public void finalizarLancamentos() {

		if (coleta.getPontoColeta() != null && coleta.getDataColeta()!=null) {
			if (coleta.getId() == null) {
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				List<Coleta> coletasMesmaData = daoColeta.listar(Coleta.class,
						"dataColeta='" + format.format(coleta.getDataColeta()) + "' and pontoColeta.id="
								+ coleta.getPontoColeta().getId());

				if (coletasMesmaData.size() < 1) {
					coleta.setStatus(true);
					coletaService.inserirAlterar(coleta);
					for (ItensColeta it : listItensColeta) {
						it.setStatus(true);
						it.setColeta(coleta);
						itensColetaService.inserirAlterar(it);

					}
					if (coleta != null && coleta.getId() != null && coleta.getPontoColeta() != null) {
						ultimoPontoColeta = coleta.getPontoColeta();
					}
					criarNovoObjeto();
					ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);

				} else {
					ExibirMensagem.exibirMensagem("Já existe uma coleta registrada para este PONTO nesta DATA!!");
				}

			} else {
				coletaService.inserirAlterar(coleta);
				for (ItensColeta it : listItensColeta) {

					itensColetaService.inserirAlterar(it);

				}

				criarNovoObjeto();
				ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
			}
		} else {
			ExibirMensagem.exibirMensagem("Ponto de Coleta não Selecionado ou a data é inválida!");
		}

	}

	public void preencherLista(Coleta t) {
		this.coleta = t;
		listItensColeta = daoItensColeta.listar(ItensColeta.class, "coleta.id=" + coleta.getId());
	}

	public void inativar(Coleta t) {

		t.setStatus(false);
		coletaService.inserirAlterar(t);
		criarNovoObjeto();
		ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
		carregarLista();
	}

	public void inativarItens(ItensColeta t) {

		if (t.getId() != null) {
			t.setStatus(false);
			itensColetaService.inserirAlterar(t);
			carregarListaItens();
		} else {
			System.out.println("Aqui no remover");

			listItensColeta.remove(t);
		}

		criarNovoObjetoItens();
		ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);

	}

	public void limparCidade() {
		cidade = null;
	}

	public void inativarColeta() {
		if (coleta != null && coleta.getId() != null) {
			List<ItensColeta> lic = daoItensColeta.listar(ItensColeta.class, "coleta.id=" + coleta.getId());
			for (ItensColeta ic : lic) {
				ic.setStatus(false);
				itensColetaService.inserirAlterar(ic);
			}
			coleta.setStatus(false);
			coletaService.inserirAlterar(coleta);
			criarNovoObjeto();
			ExibirMensagem.exibirMensagem("Coleta removida com sucesso!");
		}
	}

	public void salvar() {

		try {
			if (coleta.getId() == null) {
				coleta.setStatus(true);
				coletaService.inserirAlterar(coleta);

			} else {
				coleta.setStatus(true);
				coletaService.inserirAlterar(coleta);
			}

			criarNovoObjeto();
			ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);

			carregarLista();

		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
			e.printStackTrace();
		}

	}

	public void carregarListaGrupos() {
		// System.out.println("CArregar lista grupos" +
		// coleta.getPontoColeta().getId());
		if (coleta != null && coleta.getPontoColeta() != null) {
			List<ItensInformacaoFinanceiraPontoColeta> listaPontos = daoItensInformacaoFinanceiraPontoColeta.listar(
					ItensInformacaoFinanceiraPontoColeta.class, "pontoColeta.id=" + coleta.getPontoColeta().getId()
							+ " and informacoesFinanceiras.vigente is true and informacoesFinanceiras.status is true");
			// System.out.println("Ta,manmhp 1: " + listaPontos.size());
			if (listaPontos.size() > 0) {
				if (listaPontos.get(0).getInformacoesFinanceiras() != null) {
					List<ItensInformacaoFinanceiraGrupo> listaGrupos = daoItensInformacaoFinanceiraGrupo.listar(
							ItensInformacaoFinanceiraGrupo.class,
							"informacoesFinanceiras.id=" + listaPontos.get(0).getInformacoesFinanceiras().getId());
					// System.out.println("TGamaho 2? " + listaGrupos.size());

					listItensColeta = new ArrayList<>();
					for (ItensInformacaoFinanceiraGrupo g : listaGrupos) {
						ItensColeta i = new ItensColeta();
						i.setGrupoResiduo(g.getGrupoResiduos());
						i.setStatus(true);
						listItensColeta.add(i);
					}
				}
			}
		}

	}

	public void carregarUltimoPontoColeta() {
		if (coleta.getId() == null) {
			coleta.setPontoColeta(ultimoPontoColeta);
			carregarListaGrupos();
		} else {
			ExibirMensagem.exibirMensagem("Primeiro clique em Nova Coleta!!");
		}
	}

	public void criarNovoObjeto() {
		Usuario ultimoMotorista = null;
		Date ultimaData = null;

		listItensColeta = new ArrayList<>();
		// for (GrupoResiduo g : listaGrupoResiduos) {
		// ItensColeta i = new ItensColeta();
		// i.setGrupoResiduo(g);
		// i.setStatus(true);
		// listItensColeta.add(i);
		// }

		if (coleta != null && coleta.getMotorista() != null) {
			ultimoMotorista = coleta.getMotorista();
		}

		if (coleta != null && coleta.getDataColeta() != null) {
			ultimaData = coleta.getDataColeta();
		}

		coleta = new Coleta();

		if (ultimoMotorista != null) {
			coleta.setMotorista(ultimoMotorista);
		}
		if (ultimaData != null) {
			coleta.setDataColeta(ultimaData);
		}

	}

	public void criarNovoObjetoItens() {
		itensColeta = new ItensColeta();
	}

	public void carregarLista() {
		listColeta = daoColeta.listaComStatus(Coleta.class);
	}

	public void carregarListaItens() {
		listItensColeta = daoItensColeta.listaComStatus(ItensColeta.class);
	}

	public Coleta getColeta() {
		return coleta;
	}

	public void setColeta(Coleta coleta) {
		this.coleta = coleta;
	}

	public List<Coleta> getColetaBusca() {
		return coletaBusca;
	}

	public void setColetaBusca(List<Coleta> coletaBusca) {
		this.coletaBusca = coletaBusca;
	}

	public List<Coleta> getListColeta() {
		return listColeta;
	}

	public void setListColeta(List<Coleta> listColeta) {
		this.listColeta = listColeta;
	}

	public List<ItensColeta> getListItensColeta() {
		return listItensColeta;
	}

	public void setListItensColeta(List<ItensColeta> listItensColeta) {
		this.listItensColeta = listItensColeta;
	}

	public GrupoLancamento getGrupoLancamentoSelecionado() {
		return grupoLancamentoSelecionado;
	}

	public void setGrupoLancamentoSelecionado(GrupoLancamento grupoLancamentoSelecionado) {
		this.grupoLancamentoSelecionado = grupoLancamentoSelecionado;
	}

	public ItensColeta getItensColeta() {
		return itensColeta;
	}

	public void setItensColeta(ItensColeta itensColeta) {
		this.itensColeta = itensColeta;
	}

	public List<ItensColeta> getListItensColetaConsulta() {
		return listItensColetaConsulta;
	}

	public void setListItensColetaConsulta(List<ItensColeta> listItensColetaConsulta) {
		this.listItensColetaConsulta = listItensColetaConsulta;
	}

	public Date getDataInicialConsulta() {
		return dataInicialConsulta;
	}

	public void setDataInicialConsulta(Date dataInicialConsulta) {
		this.dataInicialConsulta = dataInicialConsulta;
	}

	public Date getDataFinalConsulta() {
		return dataFinalConsulta;
	}

	public void setDataFinalConsulta(Date dataFinalConsulta) {
		this.dataFinalConsulta = dataFinalConsulta;
	}

	public List<GrupoResiduo> getListaGrupoResiduos() {
		return listaGrupoResiduos;
	}

	public void setListaGrupoResiduos(List<GrupoResiduo> listaGrupoResiduos) {
		this.listaGrupoResiduos = listaGrupoResiduos;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public List<PontoColeta> getListaPontos() {
		return listaPontos;
	}

	public void setListaPontos(List<PontoColeta> listaPontos) {
		this.listaPontos = listaPontos;
	}

	public PontoColeta getUltimoPontoColeta() {
		return ultimoPontoColeta;
	}

	public void setUltimoPontoColeta(PontoColeta ultimoPontoColeta) {
		this.ultimoPontoColeta = ultimoPontoColeta;
	}

	public String getOcultarZeros() {
		return ocultarZeros;
	}

	public void setOcultarZeros(String ocultarZeros) {
		this.ocultarZeros = ocultarZeros;
	}

	public List<ItensColeta> getListItensColetaConsultaTabela() {
		return listItensColetaConsultaTabela;
	}

	public void setListItensColetaConsultaTabela(List<ItensColeta> listItensColetaConsultaTabela) {
		this.listItensColetaConsultaTabela = listItensColetaConsultaTabela;
	}

}
