package lista.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tabelatiposervico")
public class EntidadeTipoServico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idTipoServico")
	private Long id;
	private String nomeSingular;
	private String nomePlural;
	private String codigoCasaOracao;
	private String status;
	private Integer ordemAproximada;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeSingular() {
		return nomeSingular;
	}

	public void setNomeSingular(String nomeSingular) {
		this.nomeSingular = nomeSingular;
	}

	public String getNomePlural() {
		return nomePlural;
	}

	public void setNomePlural(String nomePlural) {
		this.nomePlural = nomePlural;
	}

	public String getCodigoCasaOracao() {
		return codigoCasaOracao;
	}

	public void setCodigoCasaOracao(String codigoCasaOracao) {
		this.codigoCasaOracao = codigoCasaOracao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getOrdemAproximada() {
		return ordemAproximada;
	}

	public void setOrdemAproximada(Integer ordemAproximada) {
		this.ordemAproximada = ordemAproximada;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoCasaOracao == null) ? 0 : codigoCasaOracao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nomePlural == null) ? 0 : nomePlural.hashCode());
		result = prime * result + ((nomeSingular == null) ? 0 : nomeSingular.hashCode());
		result = prime * result + ((ordemAproximada == null) ? 0 : ordemAproximada.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		EntidadeTipoServico other = (EntidadeTipoServico) obj;
		if (codigoCasaOracao == null) {
			if (other.codigoCasaOracao != null)
				return false;
		} else if (!codigoCasaOracao.equals(other.codigoCasaOracao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nomePlural == null) {
			if (other.nomePlural != null)
				return false;
		} else if (!nomePlural.equals(other.nomePlural))
			return false;
		if (nomeSingular == null) {
			if (other.nomeSingular != null)
				return false;
		} else if (!nomeSingular.equals(other.nomeSingular))
			return false;
		if (ordemAproximada == null) {
			if (other.ordemAproximada != null)
				return false;
		} else if (!ordemAproximada.equals(other.ordemAproximada))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

}