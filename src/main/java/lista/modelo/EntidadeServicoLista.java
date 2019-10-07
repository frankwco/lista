package lista.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tabelaservicolista")
public class EntidadeServicoLista implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idServicoLista")
	private Long id;

	@JoinColumn(name = "idTipoServico")
	@ManyToOne
	private EntidadeTipoServico tipoServico;

	@JoinColumn(name = "idLista")
	@ManyToOne
	private EntidadeLista lista;
	private Integer ordem;
	private String status;
	private String codigoCasaOracao;
	private String descricao;
	private String tipoDescricao;
	
	@Transient
	private List<EntidadeItensServicoLista> itensServicoLista;
	
	public List<EntidadeItensServicoLista> getItensServicoLista() {
		return itensServicoLista;
	}

	public void setItensServicoLista(List<EntidadeItensServicoLista> itensServicoLista) {
		this.itensServicoLista = itensServicoLista;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EntidadeTipoServico getTipoServico() {
		return tipoServico;
	}

	public void setTipoServico(EntidadeTipoServico tipoServico) {
		this.tipoServico = tipoServico;
	}

	public EntidadeLista getLista() {
		return lista;
	}

	public void setLista(EntidadeLista lista) {
		this.lista = lista;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCodigoCasaOracao() {
		return codigoCasaOracao;
	}

	public void setCodigoCasaOracao(String codigoCasaOracao) {
		this.codigoCasaOracao = codigoCasaOracao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTipoDescricao() {
		return tipoDescricao;
	}

	public void setTipoDescricao(String tipoDescricao) {
		this.tipoDescricao = tipoDescricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoCasaOracao == null) ? 0 : codigoCasaOracao.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lista == null) ? 0 : lista.hashCode());
		result = prime * result + ((ordem == null) ? 0 : ordem.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((tipoDescricao == null) ? 0 : tipoDescricao.hashCode());
		result = prime * result + ((tipoServico == null) ? 0 : tipoServico.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntidadeServicoLista other = (EntidadeServicoLista) obj;
		if (codigoCasaOracao == null) {
			if (other.codigoCasaOracao != null)
				return false;
		} else if (!codigoCasaOracao.equals(other.codigoCasaOracao))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lista == null) {
			if (other.lista != null)
				return false;
		} else if (!lista.equals(other.lista))
			return false;
		if (ordem == null) {
			if (other.ordem != null)
				return false;
		} else if (!ordem.equals(other.ordem))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (tipoDescricao == null) {
			if (other.tipoDescricao != null)
				return false;
		} else if (!tipoDescricao.equals(other.tipoDescricao))
			return false;
		if (tipoServico == null) {
			if (other.tipoServico != null)
				return false;
		} else if (!tipoServico.equals(other.tipoServico))
			return false;
		return true;
	}
}