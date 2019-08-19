package base.controle;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import base.modelo.Pessoa;
import base.modelo.PontoColeta;
import base.modelo.Cliente;
import base.modelo.FechamentoMes;
import base.modelo.GrupoResiduo;
import base.modelo.InformacaoFinanceira;
import base.modelo.ItensInformacaoFinanceiraGrupo;
import base.modelo.ItensInformacaoFinanceiraPontoColeta;
import base.service.GrupoResiduoService;
import base.service.InformacoesFinanceirasService;
import base.service.ItensInformacaoFinanceiraGrupoResiduoService;
import base.service.ItensInformacaoFinanceiraPontoColetaService;
import base.service.PontoColetaService;
import util.ExibirMensagem;
import util.FecharDialog;
import util.Mensagem;
import dao.GenericDAO;
import net.sf.jasperreports.engine.util.ConcurrentMapping.Mapper;

@ViewScoped
@Named("informacoesFinanceirasMB")
public class InformacoesFinanceirasMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private InformacaoFinanceira informacoesFinanceiras;
	private List<InformacaoFinanceira> informacoesFinanceirasBusca;
	private List<InformacaoFinanceira> listInformacoesFinanceiras;
	private List<InformacaoFinanceira> listInformacoesFinanceirasVencidas;
	private List<InformacaoFinanceira> listInformacoesFinanceirasVencer;
	private List<InformacaoFinanceira> listInformacoesFinanceirasCliente;

	private PontoColeta pontoColeta;
	private List<PontoColeta> pontosColetas;
	private GrupoResiduo grupoResiduo;
	private List<GrupoResiduo> gruposRediduos;
	private int tabIndex = 0;

	private ItensInformacaoFinanceiraPontoColeta itemInformacaoFinanceiraPontoColeta;
	private List<ItensInformacaoFinanceiraPontoColeta> listaItensInformacoesFinanceiraPontoColeta;
	private ItensInformacaoFinanceiraGrupo itemInformacaoFinanceiraGrupo;
	private List<ItensInformacaoFinanceiraGrupo> listaItensInformacoesFinanceiraGrupo;
	private Date dataVencimentoInicial = new Date();
	private Date dataVencimentoFinal = new Date();

	@Inject
	private GenericDAO<InformacaoFinanceira> daoInformacoesFinanceiras; // faz as buscas

	@Inject
	private InformacoesFinanceirasService informacoesFinanceirasService; // inserir no banco

	@Inject
	private GenericDAO<PontoColeta> daoPontoColeta; // faz as buscas

	@Inject
	private PontoColetaService pontoColetaService; // inserir no banco

	@Inject
	private GenericDAO<PontoColeta> daoGrupoResiduos; // faz as buscas

	@Inject
	private GrupoResiduoService grupoResiduosService; // inserir no banco

	@Inject
	private GenericDAO<ItensInformacaoFinanceiraPontoColeta> daoItemInformacaoFinanceiraPontoColeta; // faz as buscas
	@Inject
	private ItensInformacaoFinanceiraPontoColetaService itemInformacaoFinanceiraPontoColetaService; // inserir no banco

	@Inject
	private GenericDAO<ItensInformacaoFinanceiraGrupo> daoItemInformacaoFinanceiraGrupoResiduo; // faz as buscas
	@Inject
	private ItensInformacaoFinanceiraGrupoResiduoService itemInformacaoFinanceiraGrupoResiduoService; // inserir no

	@Inject
	private GenericDAO<Cliente> daoCliente;// banco

	@PostConstruct
	public void inicializar() {

		informacoesFinanceiras = new InformacaoFinanceira();

		listInformacoesFinanceiras = new ArrayList<>();
		listInformacoesFinanceiras = daoInformacoesFinanceiras.listar(InformacaoFinanceira.class,
				"vigente is true order by dataFinal asc");
		informacoesFinanceirasBusca = new ArrayList<>();
		listaItensInformacoesFinanceiraGrupo = new ArrayList<>();
		listaItensInformacoesFinanceiraPontoColeta = new ArrayList<>();

		itemInformacaoFinanceiraPontoColeta = new ItensInformacaoFinanceiraPontoColeta();
		itemInformacaoFinanceiraGrupo = new ItensInformacaoFinanceiraGrupo();

	}

	public void buscarContratosVencidos() {

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		listInformacoesFinanceirasVencidas = daoInformacoesFinanceiras.listar(InformacaoFinanceira.class,
				"dataFinal between '2000-01-01' and '" + format.format(new Date()) + "'");

		listInformacoesFinanceirasVencer = daoInformacoesFinanceiras.listar(InformacaoFinanceira.class,
				"dataFinal between '" + format.format(new Date()) + "' and '3000-01-01' order by dataFinal asc");
	}

	public void buscarContratosVencimento() {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (dataVencimentoInicial != null && dataVencimentoFinal != null) {
			listInformacoesFinanceiras = daoInformacoesFinanceiras.listar(InformacaoFinanceira.class,
					"dataFinal between '" + format.format(dataVencimentoInicial) + "' and '"
							+ format.format(dataVencimentoFinal) + "' order by dataFinal asc");

		} else {
			listInformacoesFinanceiras = daoInformacoesFinanceiras.listaComStatus(InformacaoFinanceira.class);
		}
	}

	public void atualizarDataFinalGeral() {
		Calendar c = Calendar.getInstance();
		for (InformacaoFinanceira i : listInformacoesFinanceiras) {

			// LocalDateTime.from(i.getDataFinal().toInstant()).plusMonths(1);

			c.setTime(i.getDataInicial());
			int mes = c.get(Calendar.MONTH);
			c.set(Calendar.MONTH, (mes + 12));
			i.setDataFinal(c.getTime());
			informacoesFinanceirasService.inserirAlterar(i);
		}
		ExibirMensagem.exibirMensagem("Finalizado!");
	}
	
	public void atualizarIdUnico() {
		
		for (InformacaoFinanceira i : listInformacoesFinanceiras) {
			i.setIdInformacaoFinanceiraUnico(i.getId());		
			informacoesFinanceirasService.inserirAlterar(i);
		}
		ExibirMensagem.exibirMensagem("Finalizado!");
	}

	public void buscarDadosContratoCliente() {
		String nomeUsuario = "";
		Cliente cliente = null;
		Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			System.out.println("Aquii usuario sessão");
			Object obj = authentication.getPrincipal();
			if (obj instanceof UserDetails) {
				nomeUsuario = ((UserDetails) obj).getUsername();
			} else {
				nomeUsuario = obj.toString();
			}
		}

		List<Cliente> usu = daoCliente.listar(Cliente.class, "cpfCnpj='" + nomeUsuario + "'");
		if (usu.size() > 0) {
			cliente = usu.get(0);
		}

		if (cliente != null) {

			listInformacoesFinanceirasCliente = daoInformacoesFinanceiras.listar(InformacaoFinanceira.class,
					"cliente.id=" + cliente.getId());
		}
	}

	public String chamarContasReceber(InformacaoFinanceira inf) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
		session.setAttribute("informacaoFinanceira", inf);
		return "contasReceber";
	}

	public void zerarValorColetaFazParteContrato() {
		if (itemInformacaoFinanceiraGrupo.isCobraValorParte()) {
			itemInformacaoFinanceiraGrupo.setValorPorQuiloExcedente(0.);
			itemInformacaoFinanceiraGrupo.setPesoValorFixo(0.);
			itemInformacaoFinanceiraGrupo.setValorFixo(0.);
		}
	}

	public void zerarExisteValorFixoMensal() {
		informacoesFinanceiras.setValorFixoMensalTotal(0.);
		informacoesFinanceiras.setValorFixoMensalPonto(0.);
	}

	public void zerarCobraValorExcedente() {
		informacoesFinanceiras.setPesoMensal(0.);
		informacoesFinanceiras.setValorExcedente(0.);
	}

	public void zerarCobradoPorQuilo() {
		informacoesFinanceiras.setValorPorQuilo(0.);
	}

	private void buscarItensPontoColeta() {
		if (informacoesFinanceiras != null && informacoesFinanceiras.getId() != null) {
			listaItensInformacoesFinanceiraPontoColeta = daoItemInformacaoFinanceiraPontoColeta.listar(
					ItensInformacaoFinanceiraPontoColeta.class,
					"informacoesFinanceiras.id=" + informacoesFinanceiras.getId());
		}
	}

	private void buscarItensGrupo() {
		if (informacoesFinanceiras != null && informacoesFinanceiras.getId() != null) {
			listaItensInformacoesFinanceiraGrupo = daoItemInformacaoFinanceiraGrupoResiduo.listar(
					ItensInformacaoFinanceiraGrupo.class,
					"informacoesFinanceiras.id=" + informacoesFinanceiras.getId());
		}
	}

	private void salvarItensPontoColeta() {
		for (ItensInformacaoFinanceiraPontoColeta it : listaItensInformacoesFinanceiraPontoColeta) {
			it.setInformacoesFinanceiras(informacoesFinanceiras);
			itemInformacaoFinanceiraPontoColetaService.inserirAlterar(it);
		}
	}

	private void salvarItensGrupo() {
		for (ItensInformacaoFinanceiraGrupo it : listaItensInformacoesFinanceiraGrupo) {
			it.setInformacoesFinanceiras(informacoesFinanceiras);
			itemInformacaoFinanceiraGrupoResiduoService.inserirAlterar(it);
		}
	}

	public void removerItensPontoColeta(ItensInformacaoFinanceiraPontoColeta item) {
		if (informacoesFinanceiras != null) {
			if (informacoesFinanceiras.getId() == null) {
				if (!listaItensInformacoesFinanceiraPontoColeta.contains(item)) {
					listaItensInformacoesFinanceiraPontoColeta.remove(item);
					ExibirMensagem.exibirMensagem("Ponto exluído com sucesso!");
				}
			} else {
				item.setStatus(false);
				itemInformacaoFinanceiraPontoColetaService.inserirAlterar(item);
				ExibirMensagem.exibirMensagem("Ponto excluído com sucesso!");
				buscarItensPontoColeta();
			}
			itemInformacaoFinanceiraPontoColeta = new ItensInformacaoFinanceiraPontoColeta();
		}
	}

	public void removerItensGrupo(ItensInformacaoFinanceiraGrupo item) {
		if (informacoesFinanceiras != null) {
			if (informacoesFinanceiras.getId() == null) {
				if (!listaItensInformacoesFinanceiraGrupo.contains(item)) {
					listaItensInformacoesFinanceiraGrupo.remove(item);
					ExibirMensagem.exibirMensagem("Grupo exluído com sucesso!");
				}
			} else {
				item.setStatus(false);
				itemInformacaoFinanceiraGrupoResiduoService.inserirAlterar(item);
				ExibirMensagem.exibirMensagem("Grupo excluído com sucesso!");
				buscarItensGrupo();
			}
			itemInformacaoFinanceiraGrupo = new ItensInformacaoFinanceiraGrupo();
		}
	}

	private boolean containsItemPontoColeta(ItensInformacaoFinanceiraPontoColeta comp) {
		for (ItensInformacaoFinanceiraPontoColeta it : listaItensInformacoesFinanceiraPontoColeta) {

			if (it.getPontoColeta().getId().equals(comp.getPontoColeta().getId())) {
				return true;
			}
		}
		return false;
	}

	public void inserirItensPontoColetaCliente() {
		if (informacoesFinanceiras != null) {
			if (informacoesFinanceiras.getId() == null && informacoesFinanceiras.getCliente() != null) {
				listaItensInformacoesFinanceiraPontoColeta = new ArrayList<>();
				List<PontoColeta> itensP = daoPontoColeta.listar(PontoColeta.class,
						"cliente.id=" + informacoesFinanceiras.getCliente().getId());
				for (PontoColeta pp : itensP) {
					ItensInformacaoFinanceiraPontoColeta it = new ItensInformacaoFinanceiraPontoColeta();
					it.setPontoColeta(pp);
					listaItensInformacoesFinanceiraPontoColeta.add(it);
				}
				if (itensP.size() > 0) {
					ExibirMensagem.exibirMensagem("Ponto(s) adicionado(s)!!");
				} else {
					ExibirMensagem.exibirMensagem("Não foram encontrados pontos para este cliente!!");
				}
			}
		}
	}

	public void inserirItensPontoColeta() {
		if (informacoesFinanceiras != null && itemInformacaoFinanceiraPontoColeta.getPontoColeta() != null) {
			if (informacoesFinanceiras.getId() == null) {
				if (!containsItemPontoColeta(itemInformacaoFinanceiraPontoColeta)) {
					listaItensInformacoesFinanceiraPontoColeta.add(itemInformacaoFinanceiraPontoColeta);

					ExibirMensagem.exibirMensagem("Ponto adicionado!");
				} else {
					ExibirMensagem.exibirMensagem("Ponto de Coleta já está adicionado!");
				}
			} else {
				if (!containsItemPontoColeta(itemInformacaoFinanceiraPontoColeta)) {
					itemInformacaoFinanceiraPontoColeta.setInformacoesFinanceiras(informacoesFinanceiras);
					itemInformacaoFinanceiraPontoColetaService.inserirAlterar(itemInformacaoFinanceiraPontoColeta);
					ExibirMensagem.exibirMensagem("Ponto adicionado!");
					buscarItensPontoColeta();
				} else {
					ExibirMensagem.exibirMensagem("Ponto de Coleta já está adicionado!");
				}
			}
			itemInformacaoFinanceiraPontoColeta = new ItensInformacaoFinanceiraPontoColeta();
		} else {
			ExibirMensagem.exibirMensagem("Selecionar o Ponto de Coleta!");
		}
	}

	private boolean containsItemGrupo(ItensInformacaoFinanceiraGrupo comp) {
		for (ItensInformacaoFinanceiraGrupo it : listaItensInformacoesFinanceiraGrupo) {
			if (it.getGrupoResiduos().getDescricao().equals(comp.getGrupoResiduos().getDescricao())) {
				return true;
			}
		}
		return false;
	}

	public void inserirItensGrupo() {
		if (itemInformacaoFinanceiraGrupo.getGrupoResiduos() != null) {
			if (informacoesFinanceiras != null) {
				if (!itemInformacaoFinanceiraGrupo.isCobraValorParte()) {
					itemInformacaoFinanceiraGrupo.setValorFixo(0.);
					itemInformacaoFinanceiraGrupo.setPesoValorFixo(0.);
					itemInformacaoFinanceiraGrupo.setValorPorQuiloExcedente(0.);
				}

				if (informacoesFinanceiras.getId() == null) {
					if (!containsItemGrupo(itemInformacaoFinanceiraGrupo)) {
						listaItensInformacoesFinanceiraGrupo.add(itemInformacaoFinanceiraGrupo);
						ExibirMensagem.exibirMensagem("Grupo adicionado!");
					} else {
						ExibirMensagem.exibirMensagem("Grupo já está adicionado!");
					}
				} else {
					if (!containsItemGrupo(itemInformacaoFinanceiraGrupo)) {
						itemInformacaoFinanceiraGrupo.setInformacoesFinanceiras(informacoesFinanceiras);
						itemInformacaoFinanceiraGrupoResiduoService.inserirAlterar(itemInformacaoFinanceiraGrupo);
						ExibirMensagem.exibirMensagem("Grupoadicionado!");
						buscarItensGrupo();
					} else {
						ExibirMensagem.exibirMensagem("Grupo já está adicionado!");
					}
				}

				itemInformacaoFinanceiraGrupo = new ItensInformacaoFinanceiraGrupo();
			}
		} else {
			ExibirMensagem.exibirMensagem("Selecionar o Grupo!");
		}
	}

	public void preencherLista(InformacaoFinanceira t) {
		this.informacoesFinanceiras = t;
		buscarItensPontoColeta();
		buscarItensGrupo();
	}

	public void renovarContrato(InformacaoFinanceira t) {
		this.informacoesFinanceiras = new InformacaoFinanceira();
		this.informacoesFinanceiras.setAgencia(t.getAgencia());
		this.informacoesFinanceiras.setBanco(t.getBanco());
		this.informacoesFinanceiras.setCliente(t.getCliente());
		this.informacoesFinanceiras.setCobradoPorQuilo(t.getCobradoPorQuilo());
		this.informacoesFinanceiras.setCobraExcedente(t.getCobraExcedente());
		this.informacoesFinanceiras.setCodigoContratoAtual(t.getCodigoContratoAtual());
		this.informacoesFinanceiras.setComunicarEmail(t.getComunicarEmail());
		this.informacoesFinanceiras.setConta(t.getConta());
		this.informacoesFinanceiras.setContratoGeradoPeloCliente(t.isContratoGeradoPeloCliente());
		this.informacoesFinanceiras.setDataEmissaoContrato(new Date());
		this.informacoesFinanceiras.setDataInicial(t.getDataFinal());
		this.informacoesFinanceiras.setDataPrimeiroPagamento(new Date());
		this.informacoesFinanceiras.setIdInformacaoFinanceiraUnico(this.getInformacoesFinanceiras().getIdInformacaoFinanceiraUnico());

		Calendar c = Calendar.getInstance();
		c.setTime(this.informacoesFinanceiras.getDataInicial());
		int mes = c.get(Calendar.MONTH);
		c.set(Calendar.MONTH, (mes + 12));
		this.informacoesFinanceiras.setDataFinal(c.getTime());

		this.informacoesFinanceiras.setDiaVencimentoFatura(t.getDiaVencimentoFatura());
		this.informacoesFinanceiras.setExisteValorFixoMensal(t.getExisteValorFixoMensal());
		this.informacoesFinanceiras.setFormaCobranca(t.getFormaCobranca());
		this.informacoesFinanceiras.setGeradoPelasInformacoesFinanceirasID(t.getId());
		this.informacoesFinanceiras.setGerarContratoParaEstaInformacao(t.isGerarContratoParaEstaInformacao());
		this.informacoesFinanceiras.setPesoMensal(t.getPesoMensal());
		this.informacoesFinanceiras.setPontoColetaGerarContrato(t.getPontoColetaGerarContrato());
		this.informacoesFinanceiras.setQuantidadeMesesContrato(t.getQuantidadeMesesContrato());
		this.informacoesFinanceiras.setRecebimentoBoletoDeposito(t.getRecebimentoBoletoDeposito());
		this.informacoesFinanceiras.setResponsavel(t.getResponsavel());
		this.informacoesFinanceiras.setRomaneioContratoComDadosDo(t.getRomaneioContratoComDadosDo());
		this.informacoesFinanceiras.setStatus(true);
		this.informacoesFinanceiras.setValorExcedente(t.getValorExcedente());
		this.informacoesFinanceiras.setValorFixoMensalPonto(t.getValorFixoMensalPonto());
		this.informacoesFinanceiras.setValorFixoMensalTotal(t.getValorFixoMensalTotal());
		this.informacoesFinanceiras.setValorPorQuilo(t.getValorPorQuilo());
		this.informacoesFinanceiras.setVigente(true);

		listaItensInformacoesFinanceiraPontoColeta = daoItemInformacaoFinanceiraPontoColeta.listar(
				ItensInformacaoFinanceiraPontoColeta.class,
				"informacoesFinanceiras.id=" + informacoesFinanceiras.getId());

		listaItensInformacoesFinanceiraGrupo = daoItemInformacaoFinanceiraGrupoResiduo.listar(
				ItensInformacaoFinanceiraGrupo.class, "informacoesFinanceiras.id=" + informacoesFinanceiras.getId());

		buscarItensPontoColeta();
		buscarItensGrupo();
	}

	public void inativar(InformacaoFinanceira t) {
		t.setStatus(false);
		informacoesFinanceirasService.inserirAlterar(t);
		// informacoesFinanceirasService.update(" InformacaoFinanceira set status =
		// false where id = " +
		// t.getId());
		criarNovoObjeto();
		ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
		carregarLista();
	}

	public void salvar() {
		if (!informacoesFinanceiras.getRomaneioContratoComDadosDo().equals("Ponto de Coleta")) {
			informacoesFinanceiras.setPontoColetaGerarContrato(null);
		}

		Calendar c = Calendar.getInstance();

		c.setTime(informacoesFinanceiras.getDataInicial());
		int mes = c.get(Calendar.MONTH);
		c.set(Calendar.MONTH, (mes + 12));
		informacoesFinanceiras.setDataFinal(c.getTime());

		try {
			if (informacoesFinanceiras.getId() == null) {
				informacoesFinanceiras.setStatus(true);
				informacoesFinanceirasService.inserirAlterar(informacoesFinanceiras);
				informacoesFinanceiras.setIdInformacaoFinanceiraUnico(informacoesFinanceiras.getId());
				informacoesFinanceirasService.inserirAlterar(informacoesFinanceiras);
				salvarItensGrupo();
				salvarItensPontoColeta();

			} else {
				informacoesFinanceiras.setStatus(true);
				informacoesFinanceirasService.inserirAlterar(informacoesFinanceiras);
			}

			criarNovoObjeto();
			ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
			FecharDialog.fecharDialogInformacoesFinanceiras();
			carregarLista();

		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
			e.printStackTrace();
		}
		tabIndex = 0;
	}

	/** por enquanto, retorna pelo nome/razão social do cliente **/
	public List<InformacaoFinanceira> completarInformacaoFinanceira(String str) {
		List<InformacaoFinanceira> list = daoInformacoesFinanceiras.listaComStatus(InformacaoFinanceira.class);
		List<InformacaoFinanceira> selecionados = new ArrayList<>();
		for (InformacaoFinanceira cur : list) {
			if (cur.getCliente().getNomeRazaoSocialNomeFantasia().toLowerCase().contains(str.toLowerCase())) {
				selecionados.add(cur);
			}
		}
		return selecionados;
	}

	public void criarNovoObjeto() {
		informacoesFinanceiras = new InformacaoFinanceira();
		listaItensInformacoesFinanceiraGrupo = new ArrayList<>();
		listaItensInformacoesFinanceiraPontoColeta = new ArrayList<>();
		itemInformacaoFinanceiraPontoColeta = new ItensInformacaoFinanceiraPontoColeta();
		itemInformacaoFinanceiraGrupo = new ItensInformacaoFinanceiraGrupo();
		tabIndex = 0;
	}

	public void carregarLista() {
		listInformacoesFinanceiras = daoInformacoesFinanceiras.listaComStatus(InformacaoFinanceira.class,
				"dataFinal asc");
	}

	public InformacaoFinanceira getInformacoesFinanceiras() {
		return informacoesFinanceiras;
	}

	public void setInformacoesFinanceiras(InformacaoFinanceira informacoesFinanceiras) {
		this.informacoesFinanceiras = informacoesFinanceiras;
	}

	public List<InformacaoFinanceira> getInformacoesFinanceirasBusca() {
		return informacoesFinanceirasBusca;
	}

	public void setInformacoesFinanceirasBusca(List<InformacaoFinanceira> informacoesFinanceirasBusca) {
		this.informacoesFinanceirasBusca = informacoesFinanceirasBusca;
	}

	public List<InformacaoFinanceira> getListInformacoesFinanceiras() {
		return listInformacoesFinanceiras;
	}

	public void setListInformacoesFinanceiras(List<InformacaoFinanceira> listInformacoesFinanceiras) {
		this.listInformacoesFinanceiras = listInformacoesFinanceiras;
	}

	public PontoColeta getPontoColeta() {
		return pontoColeta;
	}

	public void setPontoColeta(PontoColeta pontoColeta) {
		this.pontoColeta = pontoColeta;
	}

	public List<PontoColeta> getPontosColetas() {
		return pontosColetas;
	}

	public void setPontosColetas(List<PontoColeta> pontosColetas) {
		this.pontosColetas = pontosColetas;
	}

	public GrupoResiduo getGrupoResiduo() {
		return grupoResiduo;
	}

	public void setGrupoResiduo(GrupoResiduo grupoResiduo) {
		this.grupoResiduo = grupoResiduo;
	}

	public List<GrupoResiduo> getGruposRediduos() {
		return gruposRediduos;
	}

	public void setGruposRediduos(List<GrupoResiduo> gruposRediduos) {
		this.gruposRediduos = gruposRediduos;
	}

	public ItensInformacaoFinanceiraPontoColeta getItemInformacaoFinanceiraPontoColeta() {
		return itemInformacaoFinanceiraPontoColeta;
	}

	public void setItemInformacaoFinanceiraPontoColeta(
			ItensInformacaoFinanceiraPontoColeta itemInformacaoFinanceiraPontoColeta) {
		this.itemInformacaoFinanceiraPontoColeta = itemInformacaoFinanceiraPontoColeta;
	}

	public List<ItensInformacaoFinanceiraPontoColeta> getListaItensInformacoesFinanceiraPontoColeta() {
		return listaItensInformacoesFinanceiraPontoColeta;
	}

	public void setListaItensInformacoesFinanceiraPontoColeta(
			List<ItensInformacaoFinanceiraPontoColeta> listaItensInformacoesFinanceiraPontoColeta) {
		this.listaItensInformacoesFinanceiraPontoColeta = listaItensInformacoesFinanceiraPontoColeta;
	}

	public ItensInformacaoFinanceiraGrupo getItemInformacaoFinanceiraGrupo() {
		return itemInformacaoFinanceiraGrupo;
	}

	public void setItemInformacaoFinanceiraGrupo(ItensInformacaoFinanceiraGrupo itemInformacaoFinanceiraGrupo) {
		this.itemInformacaoFinanceiraGrupo = itemInformacaoFinanceiraGrupo;
	}

	public List<ItensInformacaoFinanceiraGrupo> getListaItensInformacoesFinanceiraGrupo() {
		return listaItensInformacoesFinanceiraGrupo;
	}

	public void setListaItensInformacoesFinanceiraGrupo(
			List<ItensInformacaoFinanceiraGrupo> listaItensInformacoesFinanceiraGrupo) {
		this.listaItensInformacoesFinanceiraGrupo = listaItensInformacoesFinanceiraGrupo;
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}

	public List<InformacaoFinanceira> getListInformacoesFinanceirasCliente() {
		return listInformacoesFinanceirasCliente;
	}

	public void setListInformacoesFinanceirasCliente(List<InformacaoFinanceira> listInformacoesFinanceirasCliente) {
		this.listInformacoesFinanceirasCliente = listInformacoesFinanceirasCliente;
	}

	public Date getDataVencimentoInicial() {
		return dataVencimentoInicial;
	}

	public void setDataVencimentoInicial(Date dataVencimentoInicial) {
		this.dataVencimentoInicial = dataVencimentoInicial;
	}

	public Date getDataVencimentoFinal() {
		return dataVencimentoFinal;
	}

	public void setDataVencimentoFinal(Date dataVencimentoFinal) {
		this.dataVencimentoFinal = dataVencimentoFinal;
	}

	public List<InformacaoFinanceira> getListInformacoesFinanceirasVencidas() {
		return listInformacoesFinanceirasVencidas;
	}

	public void setListInformacoesFinanceirasVencidas(List<InformacaoFinanceira> listInformacoesFinanceirasVencidas) {
		this.listInformacoesFinanceirasVencidas = listInformacoesFinanceirasVencidas;
	}

	public List<InformacaoFinanceira> getListInformacoesFinanceirasVencer() {
		return listInformacoesFinanceirasVencer;
	}

	public void setListInformacoesFinanceirasVencer(List<InformacaoFinanceira> listInformacoesFinanceirasVencer) {
		this.listInformacoesFinanceirasVencer = listInformacoesFinanceirasVencer;
	}

}
