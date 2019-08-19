package base.service;

import java.io.Serializable;

import javax.inject.Inject;

import base.modelo.CategoriaIndicador;
import base.modelo.Indicador;
import base.modelo.Tipo;
import dao.GenericDAO;
import util.Transacional;

public class IndicadorService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<Indicador> dao;
	
	@Transacional
	public void inserirAlterar(Indicador tipo){
		if(tipo.getId()==null){
			dao.inserir(tipo);
		}else{
			dao.alterar(tipo);
		}
	}


}
