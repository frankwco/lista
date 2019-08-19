package base.service;

import java.io.Serializable;

import javax.inject.Inject;


import base.modelo.Cliente;
import dao.GenericDAO;
import util.Transacional;

public class ClienteService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<Cliente> daoTipo;
	
	@Transacional
	public void inserirAlterar(Cliente tipo){
		if(tipo.getId()==null){
			daoTipo.inserir(tipo);
		}else{
			daoTipo.alterar(tipo);
		}
	}
	
	@Transacional
	public void updateSenhaCliente(String senha, String email){
		daoTipo.updateSenhaCliente(senha, email);
	}

}
