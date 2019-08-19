package base.service;

import java.io.Serializable;

import javax.inject.Inject;

import base.modelo.CategoriaIndicador;
import base.modelo.Contrato;
import base.modelo.Atividade;
import base.modelo.Tipo;
import dao.GenericDAO;
import util.Transacional;

public class ContratoService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<Contrato> dao;
	
	@Transacional
	public void inserirAlterar(Contrato tipo){
		if(tipo.getId()==null){
			dao.inserir(tipo);
		}else{
			dao.alterar(tipo);
		}
	}


}
