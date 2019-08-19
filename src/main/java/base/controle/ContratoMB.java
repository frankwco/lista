package base.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.primefaces.PrimeFaces;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;

import base.modelo.Contrato;
import base.modelo.Atividade;
import base.modelo.Cidade;
import base.modelo.Cliente;
import base.modelo.ContasReceber;
import base.modelo.ContasReceberParcelas;
import base.modelo.Contrato;
import base.modelo.InformacaoFinanceira;
import base.modelo.ItensInformacaoFinanceiraPontoColeta;
import base.modelo.PontoColeta;
import base.service.AtividadeService;
import base.service.ClienteService;
import base.service.ContasReceberParcelasService;
import base.service.ContasReceberService;
import base.service.ContratoService;
import base.service.InformacoesFinanceirasService;
import base.service.PontoColetaService;
import util.ChamarRelatorio;
import util.ConverteStringDate;
import util.ExibirMensagem;
import util.FecharDialog;
import util.Mensagem;
import util.NossoNumeroSicoob;
import dao.GenericDAO;

@ViewScoped
@Named("contratoMB")
public class ContratoMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Contrato contrato;
	private List<Contrato> listaContrato;

	@Inject
	private GenericDAO<Contrato> daoContrato; // faz as buscas

	@Inject
	private ContratoService contratoService; // inserir no banco
	
	@Inject
	private EntityManager manager;
	
	
	@PostConstruct
	public void inicializar() {
criarNovoObjeto();
carregarLista();

if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("CONTRATO")!=null) {
	contrato = daoContrato.buscarPorId(Contrato.class,(Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("CONTRATO"));
}
	}
	
	public void imprimirModeloContrato(Contrato com) {

		HashMap parametro = new HashMap<>();
		
		parametro.put("TEXTO_CONTRATO",com.getTextoModelo());
		ChamarRelatorio ch = new ChamarRelatorio("contrato.jasper", parametro, "modeloContrato");
		Session sessions = manager.unwrap(Session.class);
		sessions.doWork(ch);
	}
	
	public String chamarAlterar(Long t) {
		FacesContext fc = FacesContext.getCurrentInstance();
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("CONTRATO",t);
		return "contratoNovo";
	}

	public void inativar(Contrato t) {
		t.setStatus(false);
		contratoService.inserirAlterar(t);
		criarNovoObjeto();
		ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
		carregarLista();
	}

	public void carregarLista() {
		listaContrato = daoContrato.listaComStatus(Contrato.class);
	}

	public void criarNovoObjeto() {
		contrato = new Contrato();
	}

	public void salvar() {

		try {
			if (contrato.getId() == null) {
				contrato.setStatus(true);
				contratoService.inserirAlterar(contrato);

			} else {
				contrato.setStatus(true);
				contratoService.inserirAlterar(contrato);
			}

			criarNovoObjeto();
			ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
			carregarLista();

		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
			e.printStackTrace();
		}
	}

	public List<String> getAtributosPF() {
		List<String> retorno = new ArrayList<>();
		retorno.add("::nomeRazaoSocial");
		retorno.add("::nomeFantasia");
		retorno.add("::cpfCnpj");
		retorno.add("::rgInscricaoEstadual");
		retorno.add("::cep");
		retorno.add("::endereco");
		retorno.add("::numero");
		retorno.add("::bairro");
		retorno.add("::edificio");
		retorno.add("::sala");
		retorno.add("::andar");
		retorno.add("::complemento");
		retorno.add("::cidade");
		retorno.add("::email");
		retorno.add("::email2");
		retorno.add("::telefone1");
		retorno.add("::telefone2");
		retorno.add("::responsavel");
		retorno.add("::cpfResponsavel");
		retorno.add("::rgResponsavel");
		retorno.add("::orgaoEmissorRgResponsavel");
		retorno.add("::enderecoResponsavel");
		retorno.add("::cidadeResponsavel");
		retorno.add("::pontosColeta");
		
		retorno.add("::dataInicial");
		retorno.add("::dataFinal");
		retorno.add("::dataEmissaoContrato");
		retorno.add("::dataPrimeiroPagamento");
		retorno.add("::valoresCobrados");
		retorno.add("::diaVencimentoFatura");
		retorno.add("::boletoOuDeposito");
		retorno.add("::pontosColeta");
		retorno.add("::classesColetadas");



		return retorno;
	}


	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public List<Contrato> getListaContrato() {
		return listaContrato;
	}

	public void setListaContrato(List<Contrato> listaContrato) {
		this.listaContrato = listaContrato;
	}
	
	

}
