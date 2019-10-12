package lista.service;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import base.modelo.CategoriaIndicador;
import base.modelo.Tipo;
import dao.GenericDAO;
import lista.modelo.EntidadeVisitas;
import util.Transacional;

public class VisitasService implements Serializable{
	
	/**
	 * 
	 */
	
	
	
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<EntidadeVisitas> dao;
	
	@Transacional
	public void inserirAlterar(EntidadeVisitas tipo){
		if(tipo.getId()==null){
			dao.inserir(tipo);
		}else{
			dao.alterar(tipo);
		}
	}
	
	


}
