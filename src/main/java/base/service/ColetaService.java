package base.service;

import java.io.Serializable;

import javax.inject.Inject;


import base.modelo.Coleta;
import dao.GenericDAO;
import util.Transacional;

public class ColetaService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<Coleta> daoTipo;
	
	@Transacional
	public void inserirAlterar(Coleta tipo){
		if(tipo.getId()==null){
			daoTipo.inserir(tipo);
		}else{
			daoTipo.alterar(tipo);
		}
	}

}
