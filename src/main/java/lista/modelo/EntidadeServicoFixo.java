package lista.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tabelaservicofixo")
public class EntidadeServicoFixo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idServicoFixo")
	private Long id;
	private String descricao;

	@JoinColumn(name = "idTipoServico")
	@ManyToOne
	private EntidadeTipoServico tipoServico;
	private String status;
	private String codigoCasaOracao;
	private String hora;

	@Temporal(TemporalType.DATE)
	private Date dataServico;
	private Boolean incluirMesAnterior;
	private String atendidoPor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public EntidadeTipoServico getTipoServico() {
		return tipoServico;
	}

	public void setTipoServico(EntidadeTipoServico tipoServico) {
		this.tipoServico = tipoServico;
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

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public Date getDataServico() {
		return dataServico;
	}

	public void setDataServico(Date dataServico) {
		this.dataServico = dataServico;
	}

	public Boolean getIncluirMesAnterior() {
		return incluirMesAnterior;
	}

	public void setIncluirMesAnterior(Boolean incluirMesAnterior) {
		this.incluirMesAnterior = incluirMesAnterior;
	}

	public String getAtendidoPor() {
		return atendidoPor;
	}

	public void setAtendidoPor(String atendidoPor) {
		this.atendidoPor = atendidoPor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((atendidoPor == null) ? 0 : atendidoPor.hashCode());
		result = prime * result + ((codigoCasaOracao == null) ? 0 : codigoCasaOracao.hashCode());
		result = prime * result + ((dataServico == null) ? 0 : dataServico.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((hora == null) ? 0 : hora.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((incluirMesAnterior == null) ? 0 : incluirMesAnterior.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		EntidadeServicoFixo other = (EntidadeServicoFixo) obj;
		if (atendidoPor == null) {
			if (other.atendidoPor != null)
				return false;
		} else if (!atendidoPor.equals(other.atendidoPor))
			return false;
		if (codigoCasaOracao == null) {
			if (other.codigoCasaOracao != null)
				return false;
		} else if (!codigoCasaOracao.equals(other.codigoCasaOracao))
			return false;
		if (dataServico == null) {
			if (other.dataServico != null)
				return false;
		} else if (!dataServico.equals(other.dataServico))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (hora == null) {
			if (other.hora != null)
				return false;
		} else if (!hora.equals(other.hora))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (incluirMesAnterior == null) {
			if (other.incluirMesAnterior != null)
				return false;
		} else if (!incluirMesAnterior.equals(other.incluirMesAnterior))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (tipoServico == null) {
			if (other.tipoServico != null)
				return false;
		} else if (!tipoServico.equals(other.tipoServico))
			return false;
		return true;
	}
}