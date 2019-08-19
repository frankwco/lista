package lista.service;

import java.io.Serializable;

import javax.inject.Inject;

import base.modelo.CategoriaIndicador;
import base.modelo.Tipo;
import dao.GenericDAO;
import lista.modelo.EntidadeServicoLista;
import util.Transacional;

public class ServicoListaService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<EntidadeServicoLista> dao;
	
	@Transacional
	public void inserirAlterar(EntidadeServicoLista tipo){
		if(tipo.getId()==null){
			dao.inserir(tipo);
		}else{
			dao.alterar(tipo);
		}
	}


}
