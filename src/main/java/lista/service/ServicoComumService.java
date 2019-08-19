package lista.service;

import java.io.Serializable;

import javax.inject.Inject;

import base.modelo.CategoriaIndicador;
import base.modelo.Tipo;
import dao.GenericDAO;
import lista.modelo.EntidadeServicoComum;
import util.Transacional;

public class ServicoComumService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<EntidadeServicoComum> dao;
	
	@Transacional
	public void inserirAlterar(EntidadeServicoComum tipo){
		if(tipo.getId()==null){
			dao.inserir(tipo);
		}else{
			dao.alterar(tipo);
		}
	}


}
