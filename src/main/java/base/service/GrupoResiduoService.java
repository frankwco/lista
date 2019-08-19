package base.service;

import java.io.Serializable;

import javax.inject.Inject;

import base.modelo.GrupoResiduo;
import dao.GenericDAO;
import util.Transacional;

public class GrupoResiduoService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<GrupoResiduo> daoTipo;
	
	@Transacional
	public void inserirAlterar(GrupoResiduo tipo){
		if(tipo.getId()==null){
			daoTipo.inserir(tipo);
		}else{
			daoTipo.alterar(tipo);
		}
	}

}
