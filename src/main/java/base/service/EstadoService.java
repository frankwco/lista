package base.service;

import java.io.Serializable;

import javax.inject.Inject;


import base.modelo.Estado;
import dao.GenericDAO;
import util.Transacional;

public class EstadoService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<Estado> daoTipo;
	
	@Transacional
	public void inserirAlterar(Estado tipo){
		if(tipo.getId()==null){
			daoTipo.inserir(tipo);
		}else{
			daoTipo.alterar(tipo);
		}
	}

}
