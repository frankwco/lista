package base.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import base.modelo.CategoriaIndicador;
import base.modelo.ContasReceberParcelas;
import base.modelo.Tipo;
import dao.GenericDAO;
import util.Transacional;

public class ContasReceberParcelasService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<ContasReceberParcelas> dao;

	@Transacional
	public void inserirAlterar(ContasReceberParcelas tipo) {
		if (tipo.getId() == null) {
			dao.inserir(tipo);
		} else {
			dao.alterar(tipo);
		}
	}

	public ContasReceberParcelas buscarParcelaFechamentoMes(Long idFechamento) {
		List<ContasReceberParcelas> lc = dao.listar(ContasReceberParcelas.class, "fechamentoMes.id=" + idFechamento);
		if (lc.size() > 0) {
			return lc.get(0);
		} else {
			return null;
		}
	}

}
