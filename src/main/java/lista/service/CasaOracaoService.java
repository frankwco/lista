package lista.service;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import base.modelo.CategoriaIndicador;
import base.modelo.Tipo;
import dao.GenericDAO;
import lista.modelo.EntidadeCasaOracao;
import util.Transacional;

public class CasaOracaoService implements Serializable{
	
	/**
	 * 
	 */
	
	
	
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<EntidadeCasaOracao> dao;
	
	@Transacional
	public void inserirAlterar(EntidadeCasaOracao tipo){
		if(tipo.getId()==null){
			dao.inserir(tipo);
		}else{
			dao.alterar(tipo);
		}
	}
	
	public String retornaCasaOracao() {
		String casaOracaoLogada="";
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		HttpSession session = (HttpSession) request.getSession();
		if (session.getAttribute("casaOracaoLogada") != null) {
			casaOracaoLogada = (String) session.getAttribute("casaOracaoLogada");
		}
		if(casaOracaoLogada.equals("1")) {
			casaOracaoLogada="NOVA ESPERANÇA - PR";
		} else if(casaOracaoLogada.equals("2")) {
			casaOracaoLogada="COLORADO - PR";
		} 
		return casaOracaoLogada;
	}


}
