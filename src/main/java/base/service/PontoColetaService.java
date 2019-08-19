package base.service;

import java.io.Serializable;

import javax.inject.Inject;


import base.modelo.PontoColeta;
import dao.GenericDAO;
import util.Transacional;

public class PontoColetaService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<PontoColeta> daoTipo;
	
	@Transacional
	public void inserirAlterar(PontoColeta tipo){
		if(tipo.getId()==null){
			daoTipo.inserir(tipo);
		}else{
			daoTipo.alterar(tipo);
		}
	}

}
