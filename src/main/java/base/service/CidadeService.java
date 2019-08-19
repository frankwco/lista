package base.service;

import java.io.Serializable;

import javax.inject.Inject;


import base.modelo.Cidade;
import dao.GenericDAO;
import util.Transacional;

public class CidadeService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<Cidade> daoTipo;
	
	@Transacional
	public void inserirAlterar(Cidade tipo){
		if(tipo.getId()==null){
			daoTipo.inserir(tipo);
		}else{
			daoTipo.alterar(tipo);
		}
	}

}
