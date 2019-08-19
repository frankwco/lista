package base.service;

import java.io.Serializable;

import javax.inject.Inject;

import base.modelo.CategoriaIndicador;
import base.modelo.ItensInformacaoFinanceiraGrupo;
import base.modelo.Atividade;
import base.modelo.Tipo;
import dao.GenericDAO;
import util.Transacional;

public class ItensInformacaoFinanceiraGrupoResiduoService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<ItensInformacaoFinanceiraGrupo> dao;
	
	@Transacional
	public void inserirAlterar(ItensInformacaoFinanceiraGrupo tipo){
		if(tipo.getId()==null){
			dao.inserir(tipo);
		}else{
			dao.alterar(tipo);
		}
	}


}
