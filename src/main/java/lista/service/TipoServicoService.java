package lista.service;

import java.io.Serializable;

import javax.inject.Inject;

import base.modelo.CategoriaIndicador;
import base.modelo.Tipo;
import dao.GenericDAO;
import lista.modelo.EntidadeTipoServico;
import util.Transacional;

public class TipoServicoService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<EntidadeTipoServico> dao;
	
	@Transacional
	public void inserirAlterar(EntidadeTipoServico tipo){
		if(tipo.getId()==null){
			dao.inserir(tipo);
		}else{
			dao.alterar(tipo);
		}
	}


}
