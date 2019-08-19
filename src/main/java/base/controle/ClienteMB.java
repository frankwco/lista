package base.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;

import base.modelo.Cidade;
import base.modelo.Cliente;
import base.modelo.PontoColeta;
import base.service.ClienteService;
import base.service.PontoColetaService;
import util.ExibirMensagem;
import util.FecharDialog;
import util.Mensagem;
import dao.GenericDAO;

@ViewScoped
@Named("clienteMB")
public class ClienteMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Cliente cliente;
	private List<Cliente> clienteBusca;
	private List<Cliente> listCliente;
	private List<PontoColeta> pontosColeta;
	private boolean definirPontoColeta = false;
	private int tabAtiva = 0;

	@Inject
	private GenericDAO<Cliente> daoCliente; // faz as buscas

	@Inject
	private GenericDAO<PontoColeta> daoPontosColeta; // faz as buscas

	@Inject
	private ClienteService clienteService; // inserir no banco

	@Inject
	private PontoColetaService pontoColetaService; // inserir no banco

	@PostConstruct
	public void inicializar() {

		cliente = new Cliente();

		listCliente = new ArrayList<>();
		carregarLista();
		clienteBusca = new ArrayList<>();

	}

	public void definirPonto() {
		System.out.println("Definir: " + definirPontoColeta);
	}

	public void preencherLista(Cliente t) {
		// PrimeFaces.current().resetInputs("frmModalSalvar");
		this.cliente = t;
	}
	
	public String chamarPontosColeta(Cliente t) {
		// PrimeFaces.current().resetInputs("frmModalSalvar");
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("idCliente", t.getId());
		return "pontosColeta";
	}
	


	public void preencherListaPontosColeta(Cliente t) {
		this.cliente = t;
		pontosColeta = daoPontosColeta.listar(PontoColeta.class, "cliente.id=" + this.cliente.getId());
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
		// clienteService.update(" Cliente set status = false where id = " +
		// t.getId());
		criarNovoObjeto("Física");
		ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
		carregarLista();
	}

	public void salvar() {

		try {
			if (cliente != null && cliente.getCpfCnpj() != null && !cliente.getCpfCnpj().equals("000.000.000-00")
					&& !cliente.getCpfCnpj().equals("00.000.000/0000-00")) {
				System.out.println("CLiente IF: " + cliente.getId());
				if (cliente.getId() == null) {
					List<Cliente> list = daoCliente.listar(Cliente.class, "cpfCnpj='" + cliente.getCpfCnpj() + "'");
					if (list.size() < 1) {

						cliente.setTipo("Cliente");
						cliente.setStatus(true);
						clienteService.inserirAlterar(cliente);
						if (definirPontoColeta == true) {
							PontoColeta pontoColeta = new PontoColeta();
							pontoColeta.setStatus(true);
							pontoColeta.setCliente(cliente);
							pontoColeta.setCep(cliente.getCep());
							pontoColeta.setCidade(cliente.getCidade());
							pontoColeta.setCpfCnpj(cliente.getCpfCnpj());
							pontoColeta.setDiaVencimentoFatura(cliente.getDiaVencimentoFatura());
							pontoColeta.setEmail(cliente.getEmail());
							pontoColeta.setEndereco(cliente.getEndereco());
							pontoColeta.setModeloCadastroPonto("Ponto de Coleta com os Dados do Cliente");
							pontoColeta.setTipo("Ponto Coleta");
							pontoColeta.setNomeFantasia(cliente.getNomeFantasia());
							pontoColeta.setNomeRazaoSocial(cliente.getNomeRazaoSocial());
							pontoColeta.setNumero(cliente.getNumero());
							pontoColeta.setResponsavel(cliente.getResponsavel());
							pontoColeta.setRgInscricaoEstadual(cliente.getRgInscricaoEstadual());
							pontoColeta.setTelefone1(cliente.getTelefone1());
							pontoColeta.setTelefone2(cliente.getTelefone2());
							pontoColeta.setTipoPessoa(cliente.getTipoPessoa());
							pontoColeta.setBairro(cliente.getBairro());
							pontoColeta.setEdificio(cliente.getEdificio());
							pontoColeta.setSala(cliente.getSala());
							pontoColeta.setAndar(cliente.getAndar());
							pontoColeta.setComplemento(cliente.getComplemento());
							pontoColeta.setObservacao(cliente.getObservacao());
							pontoColeta.setEmail2(cliente.getEmail2());
							pontoColeta.setApelido(cliente.getApelido());
							pontoColeta.setRazaoSocialCliente(cliente.getNomeRazaoSocial());
					
							pontoColeta.setCpfResponsavel(cliente.getCpfResponsavel());
							pontoColeta.setRgResponsavel(cliente.getRgResponsavel());
							pontoColeta.setOrgaoEmissorRgResponsavel(cliente.getOrgaoEmissorRgResponsavel());
							pontoColeta.setEnderecoResponsavel(cliente.getEnderecoResponsavel());
							pontoColeta.setCidadeResponsavel(cliente.getCidadeResponsavel());
							
							pontoColetaService.inserirAlterar(pontoColeta);
						}
						ExibirMensagem.exibirMensagem(Mensagem.SUCESSO + "ssss");
						FecharDialog.fecharDialogCliente();
						criarNovoObjeto("Física");
						carregarLista();
					} else {
						ExibirMensagem.exibirMensagem("Já tem um cliente cadastrado para este CPF/CNPJ: "
								+ list.get(0).getNomeRazaoSocialNomeFantasia());
					}
				} else {

					cliente.setStatus(true);
					clienteService.inserirAlterar(cliente);
					PontoColeta pontoColeta;
					List<PontoColeta> lp = daoPontosColeta.listar(PontoColeta.class, "cliente.id=" + cliente.getId()
							+ " and modeloCadastroPonto='Ponto de Coleta com os Dados do Cliente'");
					if (lp.size() > 0) {
						pontoColeta = lp.get(0);
						pontoColeta.setCep(cliente.getCep());
						pontoColeta.setCidade(cliente.getCidade());
						pontoColeta.setCpfCnpj(cliente.getCpfCnpj());
						pontoColeta.setDiaVencimentoFatura(cliente.getDiaVencimentoFatura());
						pontoColeta.setEmail(cliente.getEmail());
						pontoColeta.setEndereco(cliente.getEndereco());
						pontoColeta.setNomeFantasia(cliente.getNomeFantasia());
						pontoColeta.setNomeRazaoSocial(cliente.getNomeRazaoSocial());
						pontoColeta.setNumero(cliente.getNumero());
						pontoColeta.setResponsavel(cliente.getResponsavel());
						pontoColeta.setRgInscricaoEstadual(cliente.getRgInscricaoEstadual());
						pontoColeta.setTelefone1(cliente.getTelefone1());
						pontoColeta.setTelefone2(cliente.getTelefone2());
						pontoColeta.setTipoPessoa(cliente.getTipoPessoa());
						pontoColeta.setBairro(cliente.getBairro());

						pontoColeta.setEdificio(cliente.getEdificio());
						pontoColeta.setSala(cliente.getSala());
						pontoColeta.setAndar(cliente.getAndar());
						pontoColeta.setComplemento(cliente.getComplemento());
						pontoColeta.setObservacao(cliente.getObservacao());
						pontoColeta.setEmail2(cliente.getEmail2());
						pontoColeta.setApelido(cliente.getApelido());
						pontoColeta.setRazaoSocialCliente(cliente.getNomeRazaoSocial());
						
						pontoColeta.setCpfResponsavel(cliente.getCpfResponsavel());
						pontoColeta.setRgResponsavel(cliente.getRgResponsavel());
						pontoColeta.setOrgaoEmissorRgResponsavel(cliente.getOrgaoEmissorRgResponsavel());
						pontoColeta.setEnderecoResponsavel(cliente.getEnderecoResponsavel());
						pontoColeta.setCidadeResponsavel(cliente.getCidadeResponsavel());

						pontoColetaService.inserirAlterar(pontoColeta);
					}
					ExibirMensagem.exibirMensagem(Mensagem.SUCESSO + " - sucesso 2");
					FecharDialog.fecharDialogCliente();
					criarNovoObjeto("Física");
					carregarLista();
				}
			} else {
				ExibirMensagem.exibirMensagem("CPF ou CNPJ está incorreto!");
			}
		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
			e.printStackTrace();
		}
	}

	public void criarNovoObjeto(String tipoPessoa) {
		cliente = new Cliente();
		definirPontoColeta = false;
		cliente.setTipoPessoa(tipoPessoa);
		PrimeFaces.current().resetInputs("frmModalSalvar:tabViewCliente");
		setTabAtiva(0);
	}

	public void carregarLista() {
		listCliente = daoCliente.listaComStatus(Cliente.class,"cidade.nome, nomeRazaoSocial");
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

	public List<PontoColeta> getPontosColeta() {
		return pontosColeta;
	}

	public void setPontosColeta(List<PontoColeta> pontosColeta) {
		this.pontosColeta = pontosColeta;
	}

	public boolean isDefinirPontoColeta() {
		return definirPontoColeta;
	}

	public void setDefinirPontoColeta(boolean definirPontoColeta) {
		this.definirPontoColeta = definirPontoColeta;
	}

	public int getTabAtiva() {
		return tabAtiva;
	}

	public void setTabAtiva(int tabAtiva) {
		this.tabAtiva = tabAtiva;
	}

}
