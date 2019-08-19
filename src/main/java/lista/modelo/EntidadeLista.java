package lista.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tabelaLista")
public class EntidadeLista implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idLista")
	private Long id;

	@Column(name = "dataLista")
	@Temporal(TemporalType.DATE)
	private Date dataLista;
	private String status;
	private String codigoCasaOracao;
	private String observacao;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDataLista() {
		return dataLista;
	}
	public void setDataLista(Date dataLista) {
		this.dataLista = dataLista;
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
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoCasaOracao == null) ? 0 : codigoCasaOracao.hashCode());
		result = prime * result + ((dataLista == null) ? 0 : dataLista.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((observacao == null) ? 0 : observacao.hashCode());
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
		EntidadeLista other = (EntidadeLista) obj;
		if (codigoCasaOracao == null) {
			if (other.codigoCasaOracao != null)
				return false;
		} else if (!codigoCasaOracao.equals(other.codigoCasaOracao))
			return false;
		if (dataLista == null) {
			if (other.dataLista != null)
				return false;
		} else if (!dataLista.equals(other.dataLista))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (observacao == null) {
			if (other.observacao != null)
				return false;
		} else if (!observacao.equals(other.observacao))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	
}