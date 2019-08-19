package base.service;

import java.io.Serializable;

import javax.inject.Inject;


import base.modelo.Rota;
import dao.GenericDAO;
import util.Transacional;

public class RotaService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<Rota> daoTipo;
	
	@Transacional
	public void inserirAlterar(Rota tipo){
		if(tipo.getId()==null){
			daoTipo.inserir(tipo);
		}else{
			daoTipo.alterar(tipo);
		}
	}

}
