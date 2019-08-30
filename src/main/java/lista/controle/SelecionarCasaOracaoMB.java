package lista.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import base.modelo.Pessoa;
import base.modelo.Atividade;
import base.service.AtividadeService;
import base.service.UsuarioService;
import util.ExibirMensagem;
import util.FecharDialog;
import util.Mensagem;
import dao.GenericDAO;
import lista.modelo.EntidadeCasaOracao;
import lista.modelo.EntidadeCasaOracaoUsuario;
import lista.modelo.EntidadeServicoLista;
import lista.service.CasaOracaoService;
import lista.service.CasaOracaoUsuarioService;

@SessionScoped
@Named("selecionarCasaOracaoMB")
public class SelecionarCasaOracaoMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	EntidadeCasaOracao casaOracao;

	List<EntidadeCasaOracaoUsuario> listaCasaOracaoUsuario;

	@Inject
	private GenericDAO<EntidadeCasaOracaoUsuario> daoCasaOracaoUsuario; // faz as buscas

	@Inject
	private CasaOracaoUsuarioService casaOracaoUsuarioService; // inserir no banco

	@Inject
	private UsuarioService usuarioService;

	@PostConstruct
	public void init() {
		listaCasaOracaoUsuario = daoCasaOracaoUsuario.listar(EntidadeCasaOracaoUsuario.class, "usuario.id="+usuarioService.recuperaUsuarioSessao().getId());
		
	}
	
	public void casaOracaoSelecionada() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
		session.setAttribute("casaOracaoLogada", String.valueOf(casaOracao.getId()));
	}
}
