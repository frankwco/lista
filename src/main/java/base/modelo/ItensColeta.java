package base.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "itens_coleta")
public class ItensColeta implements Serializable {

	public ItensColeta() {
		super();
	}

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String observacao;

	@ManyToOne
	private Coleta coleta;

	@ManyToOne
	private GrupoResiduo grupoResiduo;

	private Boolean status;

	private Double quantidadeColetada;
	
	private Long fechamentoMes_id;

	
	public Long getFechamentoMes_id() {
		return fechamentoMes_id;
	}

	public void setFechamentoMes_id(Long fechamentoMes_id) {
		this.fechamentoMes_id = fechamentoMes_id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Coleta getColeta() {
		return coleta;
	}

	public void setColeta(Coleta coleta) {
		this.coleta = coleta;
	}

	public GrupoResiduo getGrupoResiduo() {
		return grupoResiduo;
	}

	public void setGrupoResiduo(GrupoResiduo grupoResiduo) {
		this.grupoResiduo = grupoResiduo;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Double getQuantidadeColetada() {
		if (quantidadeColetada == null) {
			quantidadeColetada = 0.;
		}
		return quantidadeColetada;
	}

	public void setQuantidadeColetada(Double quantidadeColetada) {
		this.quantidadeColetada = quantidadeColetada;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coleta == null) ? 0 : coleta.hashCode());
		result = prime * result + ((grupoResiduo == null) ? 0 : grupoResiduo.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((observacao == null) ? 0 : observacao.hashCode());
		result = prime * result + ((quantidadeColetada == null) ? 0 : quantidadeColetada.hashCode());
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
		ItensColeta other = (ItensColeta) obj;
		if (coleta == null) {
			if (other.coleta != null)
				return false;
		} else if (!coleta.equals(other.coleta))
			return false;
		if (grupoResiduo == null) {
			if (other.grupoResiduo != null)
				return false;
		} else if (!grupoResiduo.equals(other.grupoResiduo))
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
		if (quantidadeColetada == null) {
			if (other.quantidadeColetada != null)
				return false;
		} else if (!quantidadeColetada.equals(other.quantidadeColetada))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

}
