package lista.service;

import java.io.Serializable;

import javax.inject.Inject;

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


}
