package base.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import base.modelo.Permissoes;
import base.modelo.PontoColeta;
import base.modelo.Usuario;
import base.service.PermissoesService;
import base.service.UsuarioService;
import util.CriptografiaSenha;
import util.ExibirMensagem;
import util.FecharDialog;
import util.Mensagem;
import dao.GenericDAO;

@ViewScoped
@Named("usuarioMB")
public class UsuarioMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuario;
	private Permissoes permissao;
	private List<Permissoes> permisssoes;
	private List<Usuario> usuarioBusca;
	private List<Usuario> listaUsuario;

	@Inject
	private GenericDAO<Usuario> daoUsuario; // faz as buscas

	@Inject
	private UsuarioService usuarioService; // inserir no banco

	@Inject
	private GenericDAO<Permissoes> daoPermissoes; // faz as buscas

	@Inject
	private PermissoesService permissoesService; // inserir no banco

	@PostConstruct
	public void inicializar() {
		usuario = new Usuario();
		listaUsuario = new ArrayList<>();
		listaUsuario = daoUsuario.listaComStatus(Usuario.class);
		usuarioBusca = new ArrayList<>();
		permissao = new Permissoes();

	}
	

	public UsuarioMB() {
		// TODO Auto-generated constructor stub

	}

	public void preencherLista(Usuario t) {
		this.usuario = t;

	}

	public void criarNovoObjeto() {
		usuario = new Usuario();
	}

	public void carregarLista() {
		listaUsuario = daoUsuario.listaComStatus(Usuario.class);
	}

	public void preencherPermissoes(Usuario usu) {
		System.out.println("No conceder per");
		this.usuario = usu;
		if (usuario != null && usuario.getId() != null) {
			permisssoes = daoPermissoes.listar(Permissoes.class, "usuario.id=" + usuario.getId());
		}
	}

	public void incluirPermissao() {
		System.out.println("No incluir Permissões");
		if (permissao != null && permissao.getId() == null) {
			permissao.setUsuario(usuario);
			permissoesService.inserirAlterar(permissao);
			permissao = new Permissoes();
			preencherPermissoes(usuario);

		}
	}
	
	public List<Usuario> completar(String str) {
		List<Usuario> list = daoUsuario.listaComStatus(Usuario.class);
		List<Usuario> selecionados = new ArrayList<>();
		for (Usuario cur : list) {
			if (cur.getNome().toLowerCase().contains(str.toLowerCase())) {
				selecionados.add(cur);
			}
		}
		return selecionados;
	}

	public void inativarPermissao(Permissoes t) {
		t.setStatus(false);
		permissoesService.inserirAlterar(t);
		ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
		preencherPermissoes(usuario);
	}

	// não sei oq acontece aqui em baixo, estou tentando arrumar.

	public void inativar(Usuario t) {
		t.setStatus(false);
		usuarioService.inserirAlterar(t);
		// pessoaService.update(" Pessoa set status = false where id = " +
		// t.getId());
		criarNovoObjeto();
		ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
		carregarLista();
	}

	public void salvar() {
		try {
			if (usuario.getId() == null) {
				usuario.setPermissao("responsavel");
				usuario.setSenha(CriptografiaSenha.criptografar(usuario.getSenha()));
				usuario.setStatus(true);
				usuarioService.inserirAlterar(usuario);

			} else {
				usuario.setSenha(CriptografiaSenha.criptografar(usuario.getSenha()));
				usuario.setStatus(true);
				usuarioService.inserirAlterar(usuario);
			}

			criarNovoObjeto();
			ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
			FecharDialog.fecharDialogUsuario();
			carregarLista();

		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
			e.printStackTrace();
		}

	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarioBusca() {
		return usuarioBusca;
	}

	public void setUsuarioBusca(List<Usuario> usuarioBusca) {
		this.usuarioBusca = usuarioBusca;
	}

	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public GenericDAO<Usuario> getDaoUsuario() {
		return daoUsuario;
	}

	public void setDaoUsuario(GenericDAO<Usuario> daoUsuario) {
		this.daoUsuario = daoUsuario;
	}

	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public Permissoes getPermissao() {
		return permissao;
	}

	public void setPermissao(Permissoes permissao) {
		this.permissao = permissao;
	}

	public List<Permissoes> getPermisssoes() {
		return permisssoes;
	}

	public void setPermisssoes(List<Permissoes> permisssoes) {
		this.permisssoes = permisssoes;
	}

}
