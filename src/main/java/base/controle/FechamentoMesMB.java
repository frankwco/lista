package base.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.Session;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import base.modelo.Pessoa;
import base.modelo.PontoColeta;
import base.modelo.Usuario;
import base.modelo.Cliente;
import base.modelo.Coleta;
import base.modelo.ContasReceber;
import base.modelo.ContasReceberParcelas;
import base.modelo.FechamentoMes;
import base.modelo.FechamentoMesItensPontoColeta;
import base.modelo.GrupoResiduo;
import base.modelo.InformacaoFinanceira;
import base.modelo.ItensColeta;
import base.modelo.ItensInformacaoFinanceiraGrupo;
import base.modelo.ItensInformacaoFinanceiraPontoColeta;
import base.service.ColetaService;
import base.service.ContasReceberParcelasService;
import base.service.ContasReceberService;
import base.service.FechamentoMesItensPontoColetaService;
import base.service.FechamentoMesService;
import base.service.GrupoResiduoService;
import base.service.InformacoesFinanceirasService;
import base.service.ItensColetaService;
import base.service.ItensInformacaoFinanceiraGrupoResiduoService;
import base.service.ItensInformacaoFinanceiraPontoColetaService;
import base.service.PontoColetaService;
import util.ChamarRelatorio;
import util.ExibirMensagem;
import util.FecharDialog;
import util.Mensagem;
import dao.GenericDAO;
import javax.persistence.EntityManager;

@ViewScoped
@Named("fechamentoMesMB")
public class FechamentoMesMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private FechamentoMes fechamentoMes;
	private FechamentoMes fechamentoMesSelecionado;
	private List<FechamentoMes> listaFechamentoMes;
	private List<FechamentoMes> listaFechamentoMesExcedente;
	private Double totalAberto = 0.;
	private Double totalDesconto = 0.;
	private FechamentoMes fechamentoMesFaturamento;

	private Long idFechamentoAutenticidade;

	private ContasReceberParcelas parcelaFechamento = new ContasReceberParcelas();

	private List<InformacaoFinanceira> informacaoesFinanceirasSelecionadas = new ArrayList<>();

	@Inject
	private GenericDAO<FechamentoMes> daoFechamentoMes; // faz as buscas

	@Inject
	private GenericDAO<ContasReceberParcelas> daoContasReceberParcelas; // faz as buscas

	@Inject
	private GenericDAO<ContasReceber> daoContasReceber;

	@Inject
	private GenericDAO<ItensColeta> daoItensColeta; // faz as buscas

	@Inject
	private FechamentoMesService fechamentoMesService; // inserir no banco

	@Inject
	private GenericDAO<FechamentoMesItensPontoColeta> daoFechamentoMesItensPontoColeta; // faz
																						// as
																						// buscas
	@Inject
	private EntityManager manager;

	@Inject
	private GenericDAO<Coleta> daoColeta;

	@Inject
	private FechamentoMesItensPontoColetaService fechamentoMesItensPontoColetaService; // inserir
																						// no
																						// banco

	@Inject
	private ColetaService coletaService;

	@Inject
	private GenericDAO<InformacaoFinanceira> daoInformacaoFinanceira; // faz as
																		// buscas

	@Inject
	private GenericDAO<Cliente> daoCliente; // faz as
											// buscas

	@Inject
	private InformacoesFinanceirasService informacaoFinanceiraService; // inserir
																		// no
																		// banco

	@Inject
	private GenericDAO<ItensInformacaoFinanceiraGrupo> daoItensInformacaoFinanceiraGrupo; // faz
																							// as
																							// buscas

	@Inject
	private GenericDAO<ItensInformacaoFinanceiraPontoColeta> daoItensInformacaoFinanceiraPontoColeta; // faz
																										// as
	@Inject
	private ContasReceberService contasReceberService; // buscas

	@Inject
	private ContasReceberParcelasService contasReceberParcelaService;

	@PostConstruct
	public void inicializar() {
		criarNovoObjeto();
	}

	public void criarNovoObjeto() {
		fechamentoMes = new FechamentoMes();
		listaFechamentoMes = new ArrayList<>();
	}

	public void buscarParcelaFechamento(FechamentoMes f) {
		parcelaFechamento = contasReceberParcelaService.buscarParcelaFechamentoMes(f.getId());
		if (parcelaFechamento != null) {
			parcelaFechamento.setBoletoEmitido(f.getFaturado());
			fechamentoMesSelecionado = f;
		} else {

		}

	}

	public void calcularDescontoTotalAberto() {

		totalAberto = 0.;
		totalDesconto = 0.;
		for (FechamentoMes fs : listaFechamentoMesExcedente) {
			totalAberto += fs.getValorFaturar();
			fs.setValorPagoFaturamento(fs.getValorFaturar());
			totalDesconto += fs.getValorDesconto();
		}
		/// System.out.println(totalDesconto);
	}

	public void buscarFechamentoAberto(FechamentoMes f) {
		fechamentoMesFaturamento = f;

		listaFechamentoMesExcedente = daoFechamentoMes.listar(FechamentoMes.class,
				"idInformacaoFinanceiraUnico=" + f.getInformacaoFinanceira().getIdInformacaoFinanceiraUnico()
						+ " and (faturado is false or faturado is null) and valorFaturar > 0");
		calcularDescontoTotalAberto();
	}

	public void salvarParcelaFechamento() {
		int controle = 0, controle2 = 0;
		if (listaFechamentoMesExcedente.size() > 0) {
			for (FechamentoMes fm : listaFechamentoMesExcedente) {
				if (fm.getFaturado().equals(true)) {
					ContasReceberParcelas parcela = contasReceberParcelaService.buscarParcelaFechamentoMes(fm.getId());
					// System.out.println(parcela);
					if (parcela != null) {

						if (fm.getId().equals(fechamentoMesFaturamento.getId())) {
							controle = 1;
							fm = fechamentoMesFaturamento;
						}
						controle2 = 1;
						parcela.setBoletoEmitido(true);
						parcela.setNossoNumero(parcelaFechamento.getNossoNumero());
						parcela.setDataVencimento(parcelaFechamento.getDataVencimento());
						parcela.setObservacao(fm.getObservacao());

						if (parcela.getValorParcela().equals(fm.getValorDesconto())) {
							// parcela.setValorPago(0.);
							// parcela.setDesconto(fm.getValorDesconto());
							// parcela.setDataPagamento(new Date());
						}
						contasReceberParcelaService.inserirAlterar(parcela);
						// FechamentoMes
						// fmm=daoFechamentoMes.buscarPorId(FechamentoMes.class,fm.getId());
						fm.setFechamentoAnteriorAberto(false);
						fm.setFaturado(true);
						fechamentoMesService.inserirAlterar(fm);
						System.out.println(fm.getFaturado());
						fechamentoMesSelecionado = null;
					}
				}
			}

			if (controle == 0 && controle2 == 1) {
				fechamentoMesFaturamento.setFechamentoAnteriorAberto(false);
				fechamentoMesService.inserirAlterar(fechamentoMesFaturamento);
			}

			// buscarFechamentoMes("");
			ExibirMensagem.exibirMensagem("Faturamento Alterado com Sucesso!");
		}
	}

	public void cancelarParcelaFechamento(FechamentoMes fm) {
		ContasReceberParcelas parcela = contasReceberParcelaService.buscarParcelaFechamentoMes(fm.getId());
		if (parcela != null) {
			parcela.setBoletoEmitido(false);
			contasReceberParcelaService.inserirAlterar(parcela);
			fm.setFechamentoAnteriorAberto(true);
			fm.setFaturado(false);
			fechamentoMesService.inserirAlterar(fm);
			ExibirMensagem.exibirMensagem("Faturamento Cancelado com Sucesso!");
		}
	}

	public void buscarFechamentoMes(String parametro) {
		if (parametro.equals("excedente")) {
			parametro = " and (valorExcedenteGruposContrato > 0 or valorExcedenteGruposCobradosParte > 0)";
		}
		if (fechamentoMes.getMes() != null && fechamentoMes.getAno() != null && fechamentoMes.getMes() != 0
				&& fechamentoMes.getAno() != 0) {
			listaFechamentoMes = daoFechamentoMes.listar(FechamentoMes.class,
					"mes=" + fechamentoMes.getMes() + " and ano=" + fechamentoMes.getAno() + parametro);
		} else if (fechamentoMes.getMes() != null && fechamentoMes.getAno() != null) {
			String ano = " ", mes = " ";
			if (!fechamentoMes.getMes().equals(0)) {
				mes = " mes=" + fechamentoMes.getMes() + " ";
			}
			if (!fechamentoMes.getAno().equals(0)) {
				ano = " ano=" + fechamentoMes.getAno() + " ";
			}

			listaFechamentoMes = daoFechamentoMes.listar(FechamentoMes.class, mes + ano + parametro);
		}
	}

	public void buscarFechamentoAutenticidade() {
		if (idFechamentoAutenticidade != null && idFechamentoAutenticidade > 0) {
			listaFechamentoMes = daoFechamentoMes.listar(FechamentoMes.class, "id=" + idFechamentoAutenticidade);
			if (listaFechamentoMes.size() < 1) {
				ExibirMensagem.exibirMensagem("Nada encontrado!!");
			}
		}
	}

	public void buscarFechamentoMesCliente() {
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

		if (fechamentoMes.getMes() != null && fechamentoMes.getAno() != null && cliente != null) {

			listaFechamentoMes = daoFechamentoMes.listar(FechamentoMes.class, "mes=" + fechamentoMes.getMes()
					+ " and ano=" + fechamentoMes.getAno() + " and informacaoFinanceira.cliente.id=" + cliente.getId());
		}
	}

	public void faturarFechamentoMes(FechamentoMes fec, Boolean faturamento) {

		fec.setFaturado(faturamento);
		fechamentoMesService.inserirAlterar(fec);

		if (faturamento == true) {
			ExibirMensagem.exibirMensagem("Faturamento Realizado com Sucesso!");
		} else {
			ExibirMensagem.exibirMensagem("Faturamento Cancelado!");
		}

	}

	public void inativarFechamentoMes(FechamentoMes fec) {
		if (fechamentoMes.getMes() != null && fechamentoMes.getAno() != null && fechamentoMes.getMes() != 0
				&& fechamentoMes.getAno() != 0) {
			String consulta = "";
			if (fec != null) {
				consulta = "and id=" + fec.getId();
			}

			listaFechamentoMes = daoFechamentoMes.listar(FechamentoMes.class,
					"mes=" + fechamentoMes.getMes() + " and ano=" + fechamentoMes.getAno() + " " + consulta);
			for (FechamentoMes fm : listaFechamentoMes) {
				List<FechamentoMesItensPontoColeta> lfmipc = daoFechamentoMesItensPontoColeta
						.listar(FechamentoMesItensPontoColeta.class, "fechamentoMes.id=" + fm.getId());
				for (FechamentoMesItensPontoColeta fmipc : lfmipc) {
					fmipc.setStatus(false);
					fechamentoMesItensPontoColetaService.inserirAlterar(fmipc);
				}

//				List<Coleta> lc = daoColeta
//						.listar(Coleta.class, "fechamentoMes.id=" + fm.getId());
//				for (Coleta fmipc : lc) {
//					fmipc.setFechamentoMes(null);
//					coletaService.inserirAlterar(fmipc);
//				}

				fm.setStatus(false);
				fechamentoMesService.inserirAlterar(fm);

				List<ContasReceber> lcrl = daoContasReceber.listar(ContasReceber.class,
						"fechamentoMes.id=" + fm.getId());
				if (lcrl.size() > 0) {
					lcrl.get(0).setStatus(false);
					contasReceberService.inserirAlterar(lcrl.get(0));

					List<ContasReceberParcelas> lcrlp = daoContasReceberParcelas.listar(ContasReceberParcelas.class,
							"contasReceber.id=" + lcrl.get(0).getId());
					if (lcrlp.size() > 0) {
						lcrlp.get(0).setStatus(false);
						contasReceberParcelaService.inserirAlterar(lcrlp.get(0));
					}

				}
			}
			listaFechamentoMes = new ArrayList<>();
		}

	}

	public void gerarRomaneio(FechamentoMes fec) {

		HashMap parametro = new HashMap<>();
		parametro.put("ID_FECHAMENTO_MES", fec.getId());
		ChamarRelatorio ch = new ChamarRelatorio("romaneio.jasper", parametro, "romaneio_" + fec.getId());
		Session sessions = manager.unwrap(Session.class);
		sessions.doWork(ch);
	}

	public void gerarCertificado(FechamentoMes fec) {

		String dados = "<b><br/>" + fec.getInformacaoFinanceira().getCliente().getNomeRazaoSocial() + " - "
				+ fec.getInformacaoFinanceira().getCliente().getNomeFantasia() + " <br/>CPF/CNPJ: "
				+ fec.getInformacaoFinanceira().getCliente().getCpfCnpj() + " <br/>Endereço: "
				+ fec.getInformacaoFinanceira().getCliente().getEndereco() + ", "
				+ fec.getInformacaoFinanceira().getCliente().getNumero() + " -  "
				+ fec.getInformacaoFinanceira().getCliente().getCidade().getNome() + "-"
				+ fec.getInformacaoFinanceira().getCliente().getCidade().getEstado().getSigla() + "</b>";

		HashMap parametro = new HashMap<>();
		parametro.put("ID_FECHAMENTO_MES", fec.getId());
		parametro.put("DADOS", dados);
		ChamarRelatorio ch = new ChamarRelatorio("certificado.jasper", parametro, "certificado_" + fec.getId());
		Session sessions = manager.unwrap(Session.class);
		sessions.doWork(ch);
	}

	public void fazerFechamentoMes(String qualFechamento) {
		if (fechamentoMes.getMes() != null && fechamentoMes.getAno() != null && fechamentoMes.getMes() != 0
				&& fechamentoMes.getAno() != 0) {
			// inativarFechamentoMes();s
//			String consulta = "";
//			for (InformacaoFinanceira c : informacaoesFinanceirasSelecionadas) {
//				consulta += " and id=" + c.getId();
//			}
			List<InformacaoFinanceira> li = new ArrayList<>();
			if (qualFechamento.equals("selecionados")) {
				li = informacaoesFinanceirasSelecionadas;
			} else if (qualFechamento.equals("todos")) {
				li = daoInformacaoFinanceira.listar(InformacaoFinanceira.class, "vigente is true ");
			}

			listaFechamentoMes = daoFechamentoMes.listar(FechamentoMes.class,
					"mes=" + fechamentoMes.getMes() + " and ano=" + fechamentoMes.getAno());

			try {

				// laço principal
				for (InformacaoFinanceira informacao : li) {
					int fechamentoFeito = 0;
					FechamentoMes fechamento = new FechamentoMes();

					// VERIFICA SE JÁ TEM FECHAMENTO PARA O MÊS
					for (FechamentoMes f : listaFechamentoMes) {
						if (f.getInformacaoFinanceira().getId().equals(informacao.getId())) {
							fechamentoFeito = 1;
							fechamento = f;
							fechamento.setDiferencaGerarContasPagar(0.);
							fechamento.setPesoColetadoGrupoParte(0.);
							fechamento.setPesoColetadoGruposContrato(0.);
							fechamento.setPesoExcedenteCobradoParte(0.);
							fechamento.setPesoExcedenteGruposContrato(0.);
							fechamento.setPesoExcedenteTodosPontos(0.);
							fechamento.setQuantidadePontos(0);
							fechamento.setValorExcedenteGruposCobradosParte(0.);
							fechamento.setValorExcedenteGruposContrato(0.);
							fechamento.setValorExcedenteTodosPontos(0.);
							fechamento.setValorFinal(0.);
							fechamento.setValorFixoMensalCobradoParte(0.);
							fechamento.setValorFixoMensalContratoPonto(0.);
							fechamento.setValorFixoTodosPontos(0.);
							fechamento.setValorTotalPorQuilo(0.);
							fechamento.setValorFaturar(0.);
							fechamento.setFechamentoAnteriorAberto(false);
							break;
						}
					}

					fechamento.setAno(fechamentoMes.getAno());
					fechamento.setMes(fechamentoMes.getMes());
					fechamento.setDataFechamento(new Date());
					fechamento.setInformacaoFinanceira(informacao);
					fechamento.setIdInformacaoFinanceiraUnico(informacao.getIdInformacaoFinanceiraUnico());
					fechamentoMesService.inserirAlterar(fechamento);

					List<FechamentoMesItensPontoColeta> listaFechamentoItens = new ArrayList<>();

					List<ItensInformacaoFinanceiraPontoColeta> listaItensPontos = daoItensInformacaoFinanceiraPontoColeta
							.listar(ItensInformacaoFinanceiraPontoColeta.class,
									"informacoesFinanceiras.id=" + informacao.getId());
					List<ItensInformacaoFinanceiraGrupo> listaItensGrupos = daoItensInformacaoFinanceiraGrupo.listar(
							ItensInformacaoFinanceiraGrupo.class, "informacoesFinanceiras.id=" + informacao.getId());

					// System.out.println(informacao.getCliente().getNomeRazaoSocialNomeFantasiaEndereco());
					for (ItensInformacaoFinanceiraPontoColeta itensPontos : listaItensPontos) {

						// CONSULTA DE FECHAMENTO PARCIAL DEVE SER FEITA NESTA DE BAIXO...
						String consulta = "coleta.pontoColeta.id=" + itensPontos.getPontoColeta().getId()
								+ " and dataColeta BETWEEN '" + fechamentoMes.getAno() + "-" + fechamentoMes.getMes()
								+ "-01' and '" + fechamentoMes.getAno() + "-" + fechamentoMes.getMes() + "-31'";
						if (fechamento.getInformacaoFinanceira().getFechamentoDataDiferente().equals(true)) {

							Calendar calDataInicial = Calendar.getInstance();
							calDataInicial.setTime(new Date());
							calDataInicial.set(Calendar.DAY_OF_MONTH,
									fechamentoMes.getInformacaoFinanceira().getDiaFechamento());
							calDataInicial.set(Calendar.MONTH, (fechamento.getMes() - 1));
							calDataInicial.set(Calendar.YEAR, fechamentoMes.getAno());
							fechamento.setDataInicialFechamento(calDataInicial.getTime());

							Calendar calDataFinal = Calendar.getInstance();
							calDataFinal.setTime(new Date());
							calDataFinal.set(Calendar.DAY_OF_MONTH,
									fechamentoMes.getInformacaoFinanceira().getDiaFechamento());
							calDataFinal.set(Calendar.MONTH, (fechamento.getMes()));
							calDataFinal.set(Calendar.YEAR, fechamentoMes.getAno());
							fechamento.setDataFinalFechamento(calDataFinal.getTime());

						} else {

							Calendar calDataInicial = Calendar.getInstance();
							calDataInicial.setTime(new Date());
							calDataInicial.set(Calendar.MONTH, (fechamento.getMes()));
							calDataInicial.set(Calendar.YEAR, fechamentoMes.getAno());
							calDataInicial.set(Calendar.DAY_OF_MONTH, calDataInicial.getMinimum(Calendar.MONTH));
							fechamentoMes.setDataInicialFechamento(calDataInicial.getTime());

							Calendar calDataFinal = Calendar.getInstance();
							calDataFinal.setTime(new Date());
							calDataFinal.set(Calendar.MONTH, (fechamento.getMes()));
							calDataFinal.set(Calendar.YEAR, fechamentoMes.getAno());
							calDataInicial.set(Calendar.DAY_OF_MONTH, calDataInicial.getMinimum(Calendar.MONTH));
							fechamentoMes.setDataFinalFechamento(calDataFinal.getTime());
						}
						
						Calendar dataPesquisaInicial = Calendar.getInstance();
						dataPesquisaInicial.setTime(fechamento.getDataInicialFechamento());		
						
						Calendar dataPesquisaFinal= Calendar.getInstance();
						dataPesquisaFinal.setTime(fechamento.getDataFinalFechamento());		

						consulta = "coleta.pontoColeta.id=" + itensPontos.getPontoColeta().getId()
								+ " and dataColeta BETWEEN '" + dataPesquisaInicial.YEAR + "-" + dataPesquisaInicial.MONTH
								+ "-"+dataPesquisaInicial.DAY_OF_MONTH+"' and '" + dataPesquisaFinal.YEAR + "-" + dataPesquisaFinal.MONTH + "-"+dataPesquisaFinal.DAY_OF_MONTH+"'";

						List<ItensColeta> listaItensColeta = daoItensColeta.listarCadastros(ItensColeta.class,
								consulta);

						FechamentoMesItensPontoColeta fechamentoItens = new FechamentoMesItensPontoColeta();
						fechamentoItens.setFechamentoMes(fechamento);
						fechamentoItens.setItensPontoColeta(itensPontos);

						// BUSCA OS FechamentoMesItensPontoColeta POIS JÁ FOI FEITO O FECHAMENTO PARA O
						// MÊS
						if (fechamentoFeito == 1) {
							List<FechamentoMesItensPontoColeta> ffcm = daoFechamentoMesItensPontoColeta
									.listar(FechamentoMesItensPontoColeta.class, "fechamentoMes.id="
											+ fechamento.getId() + " and itensPontoColeta.id=" + itensPontos.getId());
							if (ffcm.size() > 0) {
								fechamentoItens = ffcm.get(0);
								fechamentoItens.setGruposCobradosParte("");
								fechamentoItens.setGruposFazemParteContrato("");
								fechamentoItens.setPesoColetadoGrupoParte(0.);
								fechamentoItens.setPesoColetadoGruposContrato(0.);
								fechamentoItens.setPesoExcedenteCobradoParte(0.);
								fechamentoItens.setPesoExcedenteGruposContrato(0.);
								fechamentoItens.setValorExcedenteGruposCobradosParte(0.);
								fechamentoItens.setValorExcedenteGruposContrato(0.);
								fechamentoItens.setValorFixoMensalCobradoParte(0.);
								fechamentoItens.setValorFixoMensalContratoPonto(0.);

								fechamentoItens.setValorTotal(0.);

							} else {
								System.out.println("A lista de FechamentoMesItensPontoColeta não tem elementos");
							}

						}

						//////

						if (itensPontos.getInformacoesFinanceiras().getExisteValorFixoMensal()
								.equals("Mensal por Ponto")) {
							fechamentoItens.setValorFixoMensalContratoPonto(
									itensPontos.getInformacoesFinanceiras().getValorFixoMensalPonto());
						}

						for (ItensInformacaoFinanceiraGrupo itemGrupo : listaItensGrupos) {
							Double tolerancia = itemGrupo.getPesoValorFixo();
							Double valorPorQuiloExcedente = itemGrupo.getValorPorQuiloExcedente();

							String grupo = itemGrupo.getGrupoResiduos().getDescricao();
							Double pesoColetado = 0.;

							Long controle = 0L;
							for (ItensColeta coleta : listaItensColeta) {
								if (coleta.getGrupoResiduo().getId().equals(itemGrupo.getGrupoResiduos().getId())) {
									pesoColetado += coleta.getQuantidadeColetada();

									if (!coleta.getColeta().getId().equals(controle)) {

										controle = coleta.getColeta().getId();
										coleta.getColeta().setFechamentoMes(fechamento);

										coletaService.inserirAlterar(coleta.getColeta());
									}

								}
							}
							// System.out.println("Item Grupo: " +
							// itemGrupo.getGrupoResiduos().getDescricao() + " - "
							// + itemGrupo.isCobraValorParte());
							if (itemGrupo.isCobraValorParte()) {
								fechamentoItens.setGruposCobradosParte(fechamentoItens.getGruposCobradosParte() + " "
										+ itemGrupo.getGrupoResiduos().getDescricao());
								fechamentoItens.setValorFixoMensalCobradoParte(
										fechamentoItens.getValorFixoMensalCobradoParte() + itemGrupo.getValorFixo());
								fechamentoItens.setPesoColetadoGrupoParte(
										fechamentoItens.getPesoColetadoGrupoParte() + pesoColetado);
								if (pesoColetado > itemGrupo.getPesoValorFixo()) {
									Double pesoCobrar = pesoColetado - itemGrupo.getPesoValorFixo();
									fechamentoItens.setValorExcedenteGruposCobradosParte(
											fechamentoItens.getValorExcedenteGruposCobradosParte()
													+ (pesoCobrar * itemGrupo.getValorPorQuiloExcedente()));
									fechamentoItens.setPesoExcedenteCobradoParte(
											fechamentoItens.getPesoExcedenteCobradoParte() + pesoCobrar);
								}

								// tem os pesos de tolerância, que pode ser
								// diferentes para os grupos.
								// TEM UM PROBLEMA, QUANDO UM GRUPO TEM TOLERANCIA
								// DE PESO.... ACHO QUE VAI TER
								// QUE FAZER UMA ASSOCIATIVA COM AQUELA TABELA DOS
								// ITENS DO GRUPO
							} else {
								// NÃO COBRADO A PARTE
								fechamentoItens
										.setGruposFazemParteContrato(fechamentoItens.getGruposFazemParteContrato() + " "
												+ itemGrupo.getGrupoResiduos().getDescricao());
								fechamentoItens.setPesoColetadoGruposContrato(
										fechamentoItens.getPesoColetadoGruposContrato() + pesoColetado);
								// fechamentoItens.set
								pesoColetado = fechamentoItens.getPesoColetadoGruposContrato();
								if (itensPontos.getInformacoesFinanceiras().getCobraExcedente().trim()
										.equals("Excedente por Ponto")) {
									if (pesoColetado > itensPontos.getInformacoesFinanceiras().getPesoMensal()) {
										Double pesoCobrar = pesoColetado
												- itensPontos.getInformacoesFinanceiras().getPesoMensal();
										fechamentoItens.setValorExcedenteGruposContrato((pesoCobrar
												* itensPontos.getInformacoesFinanceiras().getValorExcedente()));
										fechamentoItens.setPesoExcedenteGruposContrato(pesoCobrar);

									}

								}

							}

						}
						fechamentoItens.setValorTotal(fechamentoItens.getValorExcedenteGruposCobradosParte()
								+ fechamentoItens.getValorExcedenteGruposContrato()
								+ fechamentoItens.getValorFixoMensalCobradoParte()
								+ fechamentoItens.getValorFixoMensalContratoPonto());
						fechamentoMesItensPontoColetaService.inserirAlterar(fechamentoItens);
						listaFechamentoItens.add(fechamentoItens);

					}
					Double pesoTotalCobrar = 0.;
					for (FechamentoMesItensPontoColeta fm : listaFechamentoItens) {
						fechamento.setValorFinal(fechamento.getValorFinal() + fm.getValorTotal());
						pesoTotalCobrar += fm.getPesoColetadoGruposContrato();

						fechamento.setValorFixoMensalContratoPonto(
								fechamento.getValorFixoMensalContratoPonto() + fm.getValorFixoMensalContratoPonto());
						fechamento.setValorFixoMensalCobradoParte(
								fechamento.getValorFixoMensalCobradoParte() + fm.getValorFixoMensalCobradoParte());
						fechamento.setValorExcedenteGruposContrato(
								fechamento.getValorExcedenteGruposContrato() + fm.getValorExcedenteGruposContrato());
						fechamento
								.setValorExcedenteGruposCobradosParte(fechamento.getValorExcedenteGruposCobradosParte()
										+ fm.getValorExcedenteGruposCobradosParte());
						fechamento.setPesoExcedenteGruposContrato(
								fechamento.getPesoExcedenteGruposContrato() + fm.getPesoExcedenteGruposContrato());
						fechamento.setPesoExcedenteCobradoParte(
								fechamento.getPesoExcedenteCobradoParte() + fm.getPesoExcedenteCobradoParte());
						fechamento.setPesoColetadoGruposContrato(
								fechamento.getPesoColetadoGruposContrato() + fm.getPesoColetadoGruposContrato());
						fechamento.setPesoColetadoGrupoParte(
								fechamento.getPesoColetadoGrupoParte() + fm.getPesoColetadoGrupoParte());

					}

					if (fechamento.getInformacaoFinanceira().getCobradoPorQuilo()) {
						fechamento.setValorTotalPorQuilo(
								pesoTotalCobrar * fechamento.getInformacaoFinanceira().getValorPorQuilo());
					}

					if (fechamento.getInformacaoFinanceira().getExisteValorFixoMensal()
							.equals("Mensal para todos Pontos")) {
						fechamento.setValorFixoTodosPontos(
								fechamento.getInformacaoFinanceira().getValorFixoMensalTotal());
					}

					if (fechamento.getInformacaoFinanceira().getCobraExcedente()
							.equals("Excedente para todos os Pontos")) {

						Double peso = fechamento.getPesoColetadoGruposContrato();

						if (peso > fechamento.getInformacaoFinanceira().getPesoMensal()) {
							Double pesoCobrar = peso - fechamento.getInformacaoFinanceira().getPesoMensal();
							fechamento.setValorExcedenteTodosPontos(
									((pesoCobrar * fechamento.getInformacaoFinanceira().getValorExcedente())));
//							System.out.println("PESO: "+peso);
//							System.out.println("PESO COBRAR: "+pesoCobrar);
//							System.out.println("Valor Excedente: "+fechamento.getInformacaoFinanceira().getValorExcedente());
//							System.out.println("CAlculo: "+pesoCobrar * fechamento.getInformacaoFinanceira().getValorExcedente());
							fechamento.setPesoExcedenteTodosPontos(pesoCobrar);
						}
					}

					fechamento.setValorFinal(fechamento.getValorFinal() + fechamento.getValorFixoTodosPontos()
							+ fechamento.getValorTotalPorQuilo() + fechamento.getValorExcedenteTodosPontos());

					List<ContasReceberParcelas> lcr = daoContasReceberParcelas.listar(ContasReceberParcelas.class,
							"referenteMes=" + fechamentoMes.getMes() + " and referenteAno=" + fechamentoMes.getAno()
									+ " and contasReceber.informacaoFinanceira.id="
									+ fechamento.getInformacaoFinanceira().getId());

					Double valoresParcelas = 0.;
					for (ContasReceberParcelas cr : lcr) {
						valoresParcelas += cr.getValorParcela();
					}
					// ESSE VALOR ESTÁ COM PROBLEMAS PQ, QUANDO ELE GERA UMA CONTAS A PAGAR E DEPOIS
					// ATUALIZA
					// O FECHAMENTO DE MÊS ELE BUSCA TODAS AS PARCELAS DO CONTAS A RECEBER....
					fechamento.setDiferencaGerarContasPagar(fechamento.getValorFinal() - valoresParcelas);

					if (fechamento.getInformacaoFinanceira().getFormaCobranca().equals("Anual")) {
						fechamento.setValorFaturar(fechamento.getValorExcedenteGruposContrato()
								+ fechamento.getValorExcedenteGruposCobradosParte()
								+ fechamento.getValorExcedenteTodosPontos());
					} else {
						fechamento.setValorFaturar(fechamento.getValorFinal());
					}

					if (fechamento.getValorFaturar().equals(0.)) {
						fechamento.setFaturado(true);
					}

					List<FechamentoMes> fmm = daoFechamentoMes.listar(FechamentoMes.class,
							"idInformacaoFinanceiraUnico="
									+ fechamento.getInformacaoFinanceira().getIdInformacaoFinanceiraUnico()
									+ " and (faturado is false or faturado is null) and valorFaturar>0");
					if (fmm.size() > 0) {
						fechamento.setFechamentoAnteriorAberto(true);
					}

					fechamentoMesService.inserirAlterar(fechamento);

					// O QUE NÃO ULTRAPASSOU NAO PODERIA CADASTRAR, NO CASO O SINDICATO DE NOVA
					// ESPERANÇA
					// GERAR O CONTAS A RECEBER AQUI. PARA EXCEDENTE DE CONTRATOS ANUAIS,
					// PEGAR O VALOR FINAL E TIRAR O VALOR MENSAL.....
					ContasReceberParcelas parcela = new ContasReceberParcelas();
					ContasReceber contaReceber = new ContasReceber();

					// Caso de fechamento já realizado...
					if (fechamentoFeito == 1) {
						List<ContasReceber> lcrl = daoContasReceber.listar(ContasReceber.class,
								"fechamentoMes.id=" + fechamento.getId());
						if (lcrl.size() > 0) {
							contaReceber = lcrl.get(0);

							List<ContasReceberParcelas> lcrlp = daoContasReceberParcelas
									.listar(ContasReceberParcelas.class, "contasReceber.id=" + contaReceber.getId());
							if (lcrlp.size() > 0) {
								parcela = lcrlp.get(0);
							}
						} else {
							contaReceber.setDataCadastro(new Date());
						}
					} else {
						contaReceber.setDataCadastro(new Date());
					}

					contaReceber.setFechamentoMes(fechamento);
					contaReceber.setInformacaoFinanceira(fechamento.getInformacaoFinanceira());
					contaReceber.setQuantidadeParcelas(1);
					if (fechamento.getInformacaoFinanceira().getFormaCobranca().equals("Anual")) {
						contaReceber.setValorParcelas(fechamento.getValorFinal()
								- fechamento.getInformacaoFinanceira().getValorFixoMensalPonto());
					} else {
						contaReceber.setValorParcelas(fechamento.getValorFinal());
					}
					contaReceber.setValorTotal(contaReceber.getValorParcelas());
					if (contaReceber.getValorTotal() > 0.0
							|| !fechamento.getInformacaoFinanceira().getFormaCobranca().equals("Anual")) {
						contasReceberService.inserirAlterar(contaReceber);

						parcela.setDataVencimento(gerarDataVencimento(fechamento));
						parcela.setFechamentoMes(fechamento);
						parcela.setParcela(1);
						parcela.setQuantidadeParcelas(1);
						parcela.setValorParcela(contaReceber.getValorParcelas());
						parcela.setReferenteMes(fechamento.getMes());
						parcela.setReferenteAno(fechamento.getAno());

						if (fechamento.getFaturado().equals(true)) {
							// parcela.setDataPagamento(new Date());
							// parcela.setValorPago(parcela.getValorParcela());
							parcela.setBoletoEmitido(true);
						}

						if (fechamento.getInformacaoFinanceira().getIdInformacaoFinanceiraUnico() == null) {
							parcela.setIdInformacaoFinanceiraUnico(fechamento.getInformacaoFinanceira().getId());
						} else {
							parcela.setIdInformacaoFinanceiraUnico(
									fechamento.getInformacaoFinanceira().getIdInformacaoFinanceiraUnico());
						}

						if (contaReceber.getValorParcelas() <= 0.) {
							contaReceber.setStatus(false);
							parcela.setStatus(false);
							contasReceberService.inserirAlterar(contaReceber);
						}

						List<ContasReceberParcelas> lcrp = new ArrayList<>();
						lcrp.add(parcela);
						contasReceberService.salvar(contaReceber, lcrp);

					}

				}
				buscarFechamentoMes("");
				ExibirMensagem.exibirMensagem("Fechamento de mês realizado com sucesso!");
			} catch (Exception e) {
				ExibirMensagem.exibirMensagem("Ocorreu um ERRO ao realizar o fechamento, recomendamos...");
				e.printStackTrace();
			}
		}

	}

	public Date gerarDataVencimento(FechamentoMes fm) {

		Calendar c = Calendar.getInstance();
		// c.setTime(contasReceber.getDataPrimeiroVencimento());
		// int mes = c.get(Calendar.MONTH);
		if (fm.getInformacaoFinanceira().getDiaVencimentoFatura() != null) {
			c.set(Calendar.DAY_OF_MONTH, fm.getInformacaoFinanceira().getDiaVencimentoFatura());
		} else {
			c.set(Calendar.DAY_OF_MONTH, 10);
		}
		c.set(Calendar.MONTH, fechamentoMes.getMes());
		c.set(Calendar.YEAR, fechamentoMes.getAno());

		return c.getTime();

	}

	public void inativar(FechamentoMes t) {
		t.setStatus(false);
		fechamentoMesService.inserirAlterar(t);
		// informacoesFinanceirasService.update(" InformacaoFinanceira set
		// status =
		// false where id = " +
		// t.getId());

		ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);

	}

	public void salvar() {

		try {

			ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
			FecharDialog.fecharDialogInformacoesFinanceiras();

		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
			e.printStackTrace();
		}

	}

	public FechamentoMes getFechamentoMes() {
		return fechamentoMes;
	}

	public void setFechamentoMes(FechamentoMes fechamentoMes) {
		this.fechamentoMes = fechamentoMes;
	}

	public List<FechamentoMes> getListaFechamentoMes() {
		return listaFechamentoMes;
	}

	public void setListaFechamentoMes(List<FechamentoMes> listaFechamentoMes) {
		this.listaFechamentoMes = listaFechamentoMes;
	}

	public Long getIdFechamentoAutenticidade() {
		return idFechamentoAutenticidade;
	}

	public void setIdFechamentoAutenticidade(Long idFechamentoAutenticidade) {
		this.idFechamentoAutenticidade = idFechamentoAutenticidade;
	}

	public List<InformacaoFinanceira> getInformacaoesFinanceirasSelecionadas() {
		return informacaoesFinanceirasSelecionadas;
	}

	public void setInformacaoesFinanceirasSelecionadas(List<InformacaoFinanceira> informacaoesFinanceirasSelecionadas) {
		this.informacaoesFinanceirasSelecionadas = informacaoesFinanceirasSelecionadas;
	}

	public ContasReceberParcelas getParcelaFechamento() {
		return parcelaFechamento;
	}

	public void setParcelaFechamento(ContasReceberParcelas parcelaFechamento) {
		this.parcelaFechamento = parcelaFechamento;
	}

	public List<FechamentoMes> getListaFechamentoMesExcedente() {
		return listaFechamentoMesExcedente;
	}

	public void setListaFechamentoMesExcedente(List<FechamentoMes> listaFechamentoMesExcedente) {
		this.listaFechamentoMesExcedente = listaFechamentoMesExcedente;
	}

	public Double getTotalAberto() {
		return totalAberto;
	}

	public void setTotalAberto(Double totalAberto) {
		this.totalAberto = totalAberto;
	}

	public Double getTotalDesconto() {
		return totalDesconto;
	}

	public void setTotalDesconto(Double totalDesconto) {
		this.totalDesconto = totalDesconto;
	}

	public FechamentoMes getFechamentoMesFaturamento() {
		return fechamentoMesFaturamento;
	}

	public void setFechamentoMesFaturamento(FechamentoMes fechamentoMesFaturamento) {
		this.fechamentoMesFaturamento = fechamentoMesFaturamento;
	}

	/** por enquanto, retorna pelo nome/razão social do cliente **/
	// public List<InformacaoFinanceira> completarAtividade(String str) {
	// List<InformacaoFinanceira> list =
	// daoInformacoesFinanceiras.listaComStatus(InformacaoFinanceira.class);
	// List<InformacaoFinanceira> selecionados = new ArrayList<>();
	// for (InformacaoFinanceira cur : list) {
	// if (cur.getCliente().getNomeRazaoSocial().toLowerCase().startsWith(str))
	// {
	// selecionados.add(cur);
	// }
	// }
	// return selecionados;
	// }

}
