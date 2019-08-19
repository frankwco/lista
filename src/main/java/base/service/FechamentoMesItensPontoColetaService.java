package base.service;

import java.io.Serializable;

import javax.inject.Inject;

import base.modelo.CategoriaIndicador;
import base.modelo.FechamentoMesItensPontoColeta;
import base.modelo.Tipo;
import dao.GenericDAO;
import util.Transacional;

public class FechamentoMesItensPontoColetaService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<FechamentoMesItensPontoColeta> dao;
	
	@Transacional
	public void inserirAlterar(FechamentoMesItensPontoColeta tipo){
		if(tipo.getId()==null){
			dao.inserir(tipo);
		}else{
			dao.alterar(tipo);
		}
	}


}
