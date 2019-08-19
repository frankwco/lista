package base.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;

import base.modelo.Cidade;
import base.modelo.Cliente;
import base.modelo.ContasReceber;
import base.modelo.ContasReceberParcelas;
import base.modelo.InformacaoFinanceira;
import base.modelo.ItensInformacaoFinanceiraPontoColeta;
import base.modelo.PontoColeta;
import base.service.ClienteService;
import base.service.ContasReceberParcelasService;
import base.service.ContasReceberService;
import base.service.InformacoesFinanceirasService;
import base.service.PontoColetaService;
import util.ExibirMensagem;
import util.FecharDialog;
import util.Mensagem;
import util.NossoNumeroSicoob;
import dao.GenericDAO;

@ViewScoped
@Named("contasReceberMB")
public class ContasReceberMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Cliente cliente;
	private List<Cliente> clienteBusca;
	private List<Cliente> listCliente;
	private InformacaoFinanceira informacoesFinanceiras;
	private List<InformacaoFinanceira> informacoesFinanceirasBusca;

	private ContasReceber contasReceber;
	private ContasReceberParcelas contasReceberParcelas;

	private List<ContasReceber> listaContasReceber;
	private List<ContasReceberParcelas> listaParcelas;

	@Inject
	private GenericDAO<Cliente> daoCliente; // faz as buscas

	@Inject
	private GenericDAO<PontoColeta> daoPontosColeta; // faz as buscas

	@Inject
	private ClienteService clienteService; // inserir no banco

	@Inject
	private PontoColetaService pontoColetaService; // inserir no banco

	@Inject
	private GenericDAO<InformacaoFinanceira> daoInformacoesFinanceiras; // faz
																		// as
																		// buscas

	@Inject
	private InformacoesFinanceirasService informacoesFinanceirasService; // inserir
																			// no
																			// banco

	@Inject
	private GenericDAO<ContasReceber> daoContasReceber; // faz as buscas

	@Inject
	private ContasReceberService contasReceberService; // inserir no banco

	@Inject
	private GenericDAO<ContasReceberParcelas> daoContasReceberParcelas; // faz
																		// as
																		// buscas

	@Inject
	private ContasReceberParcelasService contasReceberParcelasService; // inserir
																		// no
																		// banco
	@Inject
	private GenericDAO<ItensInformacaoFinanceiraPontoColeta> daoItemInformacaoFinanceiraPontoColeta; // faz as buscas

	@PostConstruct
	public void inicializar() {
		cliente = new Cliente();
		listCliente = new ArrayList<>();
		listCliente = daoCliente.listaComStatus(Cliente.class);
		clienteBusca = new ArrayList<>();
		novoContasReceber();
		preencherListaContasReceber();
		recuperar();
	}

	public void baixarParcela() {
		if (contasReceberParcelas != null) {
			if (contasReceberParcelas.getId() != null) {
				contasReceberParcelasService.inserirAlterar(contasReceberParcelas);
				contasReceberParcelas = new ContasReceberParcelas();
				ExibirMensagem.exibirMensagem("Baixa efetuada com sucesso!");
				FecharDialog.fecharDialogContasReceberBaixa();
			} else {
				ExibirMensagem.exibirMensagem("Baixa efetuada com sucesso!");
				FecharDialog.fecharDialogContasReceberBaixa();
			}
		}
	}

	public void cancelarBaixarParcela(ContasReceberParcelas parc) {
		if (parc != null) {
			parc.setValorPago(null);
			parc.setDataPagamento(null);
			if (parc.getId() != null) {
				contasReceberParcelasService.inserirAlterar(parc);
				ExibirMensagem.exibirMensagem("Cancelamento de Baixa efetuada com sucesso!");

			} else {
				ExibirMensagem.exibirMensagem("Cancelamento de Baixa efetuada com sucesso!");
			}
		}
	}

	private void mudarNossoNumero(ContasReceberParcelas par) {
		if (par.getNossoNumero().contains("-")) {
			par.setNossoNumero(par.getNossoNumero().substring(0, par.getNossoNumero().length() - 1).replace("-", ""));
		}
		Integer nn = Integer.parseInt(par.getNossoNumero());
		for (int x = par.getParcela() - 1; x < listaParcelas.size(); x++) {
			ContasReceberParcelas p = listaParcelas.get(x);
			System.out.println("Nosso Número: " + nn);
			p.setNossoNumero(nn + "-" + NossoNumeroSicoob.calculoNossoNumero(String.valueOf(nn)));

			nn++;
		}
	}

	public void salvarParcela(ContasReceberParcelas parc) {
		if (parc != null) {
			mudarNossoNumero(parc);
			if (parc.getId() != null) {

				contasReceberParcelasService.inserirAlterar(parc);
				ExibirMensagem.exibirMensagem("Cancelamento de Baixa efetuada com sucesso!");
			}
		}
	}

	private void recuperar() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		HttpSession session = (HttpSession) request.getSession();
		if (session.getAttribute("informacaoFinanceira") != null) {
			InformacaoFinanceira c = (InformacaoFinanceira) session.getAttribute("informacaoFinanceira");
			contasReceber.setInformacaoFinanceira(c);
			contasReceber.setQuantidadeParcelas(c.getQuantidadeMesesContrato());
			contasReceber.setDataPrimeiroVencimento(c.getDataPrimeiroPagamento());

			Calendar ca = Calendar.getInstance();
			ca.setTime(contasReceber.getDataPrimeiroVencimento());
			int mes = ca.get(Calendar.MONTH);
			int ano = ca.get(Calendar.YEAR);

			if (mes == 0) {
				mes = 12;
				ano--;
			}

			contasReceber.setMesPrimeiraParcela(mes);
			contasReceber.setAnoPrimeiraParcela(ano);

			List<ItensInformacaoFinanceiraPontoColeta> l = daoItemInformacaoFinanceiraPontoColeta
					.listar(ItensInformacaoFinanceiraPontoColeta.class, "informacoesFinanceiras.id=" + c.getId());
			if (c.getExisteValorFixoMensal().equals("Mensal por Ponto")) {
				contasReceber.setValorParcelas(c.getValorFixoMensalPonto() * l.size());
			} else if (c.getExisteValorFixoMensal().equals("Mensal para todos Pontos")) {
				contasReceber.setValorParcelas(c.getValorFixoMensalTotal());
			} else {
				contasReceber.setValorParcelas(0.);
			}
			gerarParcelas();

			session.removeAttribute("informacaoFinanceira");
		}
	}

	public void preencherListaContasReceber() {
		listaContasReceber = daoContasReceber.listaComStatus(ContasReceber.class, "dataCadastro desc");
	}

	public void excluirContasReceber() {
		if (contasReceber != null && contasReceber.getId() != null && listaParcelas.size() > 0) {
			for (ContasReceberParcelas p : listaParcelas) {
				p.setStatus(false);
				contasReceberParcelasService.inserirAlterar(p);
			}
			contasReceber.setStatus(false);
			contasReceberService.inserirAlterar(contasReceber);
			novoContasReceber();
			ExibirMensagem.exibirMensagem("Conta a Receber excluída com sucesso!");
		} else {
			ExibirMensagem.exibirMensagem(
					"Ocorreu um erro ao excluir, tente pesquisar novamente a Conta a Receber que pretende excluir!");
		}
	}

	public void novoContasReceber() {
		contasReceber = new ContasReceber();
		contasReceberParcelas = new ContasReceberParcelas();
		listaContasReceber = new ArrayList<>();
		listaParcelas = new ArrayList<>();
	}

	public void carregarContaReceber(ContasReceber cr) {
		contasReceber = cr;
		listaParcelas = daoContasReceberParcelas.listar(ContasReceberParcelas.class,
				"contasReceber.id=" + contasReceber.getId());
	}

	public void gerarParcelas() {

		Calendar c = Calendar.getInstance();
		c.setTime(contasReceber.getDataPrimeiroVencimento());
		int mes = c.get(Calendar.MONTH);
		// c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 10);
		// c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
		// c.set(Calendar.YEAR, c.get(Calendar.YEAR) + 1);

		listaParcelas = new ArrayList<>();
		int mesP = contasReceber.getMesPrimeiraParcela();
		int ano = contasReceber.getAnoPrimeiraParcela();
		for (int x = 0; x < contasReceber.getQuantidadeParcelas(); x++) {
			ContasReceberParcelas p = new ContasReceberParcelas();

			c.set(Calendar.MONTH, (mes + x));
			p.setReferenteMes(mesP);
			mesP++;
			p.setReferenteAno(ano);
			p.setNossoNumero(String.valueOf(contasReceber.getNossoNumero()));
			p.setDataVencimento(c.getTime());
			p.setParcela((x + 1));
			p.setQuantidadeParcelas(contasReceber.getQuantidadeParcelas());
			p.setValorParcela(contasReceber.getValorParcelas());
			p.setContasReceber(contasReceber);
			if (mesP > 12) {
				mesP = 1;
				ano++;
			}

			listaParcelas.add(p);
		}

		if (contasReceber.getNossoNumero() != null && listaParcelas.size() > 0) {
			mudarNossoNumero(listaParcelas.get(0));
		}
	}

	public List<Cliente> completar(String str) {
		List<Cliente> list = daoCliente.listaComStatus(Cliente.class);
		List<Cliente> selecionados = new ArrayList<>();
		for (Cliente cur : list) {
			if (cur.getNomeRazaoSocial().toLowerCase().contains(str.toLowerCase())
					|| cur.getNomeFantasia().toLowerCase().contains(str.toLowerCase())) {
				selecionados.add(cur);
			}
		}
		return selecionados;
	}

	public void inativar(Cliente t) {
		t.setStatus(false);
		clienteService.inserirAlterar(t);
		ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
		carregarLista();
	}

	public void salvar() {
		try {
			if (contasReceber.getId() == null) {
				contasReceber.setDataCadastro(new Date());

				contasReceberService.inserirAlterar(contasReceber);

				for (ContasReceberParcelas p : listaParcelas) {
					p.setIdInformacaoFinanceiraUnico(
							p.getContasReceber().getInformacaoFinanceira().getIdInformacaoFinanceiraUnico());
					contasReceberParcelasService.inserirAlterar(p);

				}
				ExibirMensagem.exibirMensagem("Contas a Receber inserida com sucesso!");
			} else {
				ExibirMensagem.exibirMensagem("Contas a Receber já está inserida!");
			}
		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
			e.printStackTrace();
		}
	}

	public void carregarLista() {
		listCliente = daoCliente.listaComStatus(Cliente.class);
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getClienteBusca() {
		return clienteBusca;
	}

	public void setClienteBusca(List<Cliente> clienteBusca) {
		this.clienteBusca = clienteBusca;
	}

	public List<Cliente> getListCliente() {
		return listCliente;
	}

	public void setListCliente(List<Cliente> listCliente) {
		this.listCliente = listCliente;
	}

	public ContasReceber getContasReceber() {
		return contasReceber;
	}

	public void setContasReceber(ContasReceber contasReceber) {
		this.contasReceber = contasReceber;
	}

	public List<ContasReceberParcelas> getListaParcelas() {
		return listaParcelas;
	}

	public void setListaParcelas(List<ContasReceberParcelas> listaParcelas) {
		this.listaParcelas = listaParcelas;
	}

	public List<ContasReceber> getListaContasReceber() {
		return listaContasReceber;
	}

	public void setListaContasReceber(List<ContasReceber> listaContasReceber) {
		this.listaContasReceber = listaContasReceber;
	}

	public ContasReceberParcelas getContasReceberParcelas() {
		return contasReceberParcelas;
	}

	public void setContasReceberParcelas(ContasReceberParcelas contasReceberParcelas) {
		this.contasReceberParcelas = contasReceberParcelas;
	}

}
