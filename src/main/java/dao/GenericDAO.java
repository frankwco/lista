package dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GenericDAO<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public void alterar(T objeto) {
		manager.merge(objeto);
	}

	public void remover(T objeto) {
		Object o = manager.merge(objeto);
		manager.remove(o);
	}

	public void inserir(T objeto) {
		manager.persist(objeto);
	}

	public T buscarPorId(Class<T> classe, Long id) {
		return manager.find(classe, id);
	}

//	public T buscarCondicao(Class classeEntidade, String condicao) {
//		return (T) manager.createQuery("from "+classeEntidade.getSimpleName()+" where status is true and "+condicao).getSingleResult();
//	}

	public List<T> listaComStatus(Class classeEntidade) {
		String casaOracaoLogada = "";
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		HttpSession session = (HttpSession) request.getSession();
		if (session.getAttribute("casaOracaoLogada") != null) {
			casaOracaoLogada = (String) session.getAttribute("casaOracaoLogada");
		}
		return manager.createQuery("from " + classeEntidade.getSimpleName()
				+ " where (status <> 'excluido' or status is null) and codigoCasaOracao='" + casaOracaoLogada
				+ "' order by id").getResultList();
	}

	public List<T> listaComStatus(Class classeEntidade, String orderBy) {
		String casaOracaoLogada = "";
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		HttpSession session = (HttpSession) request.getSession();
		if (session.getAttribute("casaOracaoLogada") != null) {
			casaOracaoLogada = (String) session.getAttribute("casaOracaoLogada");
		}
		return manager.createQuery("from " + classeEntidade.getSimpleName()
				+ " where (status <> 'excluido' or status is null) and codigoCasaOracao='" + casaOracaoLogada
				+ "' order by " + orderBy).getResultList();
	}

	public List<T> listaComStatusSemCodigoCasaOracao(Class classeEntidade, String orderBy) {
		return manager.createQuery("from " + classeEntidade.getSimpleName()
				+ " where (status <> 'excluido' or status is null) order by " + orderBy).getResultList();
	}
	
	public List<T> listaComStatusSemCodigoCasaOracao(Class classeEntidade) {
		return manager.createQuery("from " + classeEntidade.getSimpleName()
				+ " where (status <> 'excluido' or status is null) ").getResultList();
	}

	public List<T> listar(Class classeEntidade, String condicao) {

		String casaOracaoLogada = "";
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		HttpSession session = (HttpSession) request.getSession();
		if (session.getAttribute("casaOracaoLogada") != null) {
			casaOracaoLogada = (String) session.getAttribute("casaOracaoLogada");
		}
		Query query = null;
		try {
			query = manager.createQuery("from " + classeEntidade.getSimpleName()
					+ " where (status <> 'excluido' or status is null) and codigoCasaOracao='" + casaOracaoLogada
					+ "' and " + condicao);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return query.getResultList();
	}

	public List<T> listarSemCodigoCasaOracao(Class classeEntidade, String condicao) {
		Query query = null;
		try {
			query = manager.createQuery("from " + classeEntidade.getSimpleName()
					+ " where (status <> 'excluido' or status is null) and " + condicao);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return query.getResultList();
	}

//	public List<T> listarCodicaoLivre(Class classeEntidade, String condicao) {
//		Query query = null;
//		try {
//			query = manager.createQuery("from " + classeEntidade.getSimpleName() + " where " + condicao);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return query.getResultList();
//	}

	public List listarCadastros(Class classe, String condicao) {
		Query query = null;
		try {
			query = manager.createQuery("from " + classe.getSimpleName() + " where " + condicao
					+ " and (status <> 'excluido' or status is null)");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return query.getResultList();
	}

//	
//	
//	
//	public void update(String alteracao) {
//		String sql = "";
//		
//			sql = ("update " + alteracao);
//			int update = manager.createQuery(sql).executeUpdate();
//			
//		
//	}

	public void updateSenha(String senha, String email) {
		String sql = "";

		sql = ("update Usuario set senha = '" + senha + "' where email like '" + email + "'");
		int update = manager.createQuery(sql).executeUpdate();

	}

	public void updateSenhaCliente(String senha, String email) {
		String sql = "";

		sql = ("update Cliente set senha = '" + senha + "' where email like '" + email + "'");
		int update = manager.createQuery(sql).executeUpdate();

	}

}
