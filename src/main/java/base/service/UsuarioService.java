package base.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import base.modelo.CategoriaIndicador;
import base.modelo.Pessoa;
import base.modelo.Tipo;
import base.modelo.Usuario;
import dao.GenericDAO;
import util.Transacional;

public class UsuarioService implements Serializable{

	

	private static final long serialVersionUID = 1L;
	
	@Inject
	private GenericDAO<base.modelo.Usuario> dao;
	
	@Transacional
	public void inserirAlterar(Usuario tipo){
		if(tipo.getId()==null){
			dao.inserir(tipo);
		}else{
			dao.alterar(tipo);
		}
	}
	
	public Usuario recuperaUsuarioSessao() {
		 Usuario usuario = null ;
		String nomeUsuario = "";
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

		List<Usuario> usu = dao.listar(Usuario.class, "email='" + nomeUsuario + "'");
		if (usu.size() > 0) {
			usuario = usu.get(0);
		}
		return usuario;
	}
	
	@Transacional
	public void updateSenha(String senha, String email){
		dao.updateSenha(senha, email);
	}


}
