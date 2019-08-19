package base.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "coleta")
public class Coleta implements Serializable {

	public Coleta() {
		super();
	}

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Temporal(TemporalType.DATE)
	private Date dataColeta=new Date();
	
	private String observacao;
	
	@ManyToOne
	private PontoColeta pontoColeta;
	
	@ManyToOne
	private Usuario motorista;
	
	@ManyToOne
	private Usuario responsavelCadastro;

	private Boolean status;
	
	@ManyToOne
	private FechamentoMes fechamentoMes;
	
	
	public FechamentoMes getFechamentoMes() {
		return fechamentoMes;
	}

	public void setFechamentoMes(FechamentoMes fechamentoMes) {
		this.fechamentoMes = fechamentoMes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataColeta() {
		return dataColeta;
	}

	public void setDataColeta(Date dataColeta) {
		this.dataColeta = dataColeta;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public PontoColeta getPontoColeta() {
		return pontoColeta;
	}

	public void setPontoColeta(PontoColeta pontoColeta) {
		this.pontoColeta = pontoColeta;
	}

	public Usuario getMotorista() {
		return motorista;
	}

	public void setMotorista(Usuario motorista) {
		this.motorista = motorista;
	}

	public Usuario getResponsavelCadastro() {
		return responsavelCadastro;
	}

	public void setResponsavelCadastro(Usuario responsavelCadastro) {
		this.responsavelCadastro = responsavelCadastro;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataColeta == null) ? 0 : dataColeta.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((motorista == null) ? 0 : motorista.hashCode());
		result = prime * result + ((observacao == null) ? 0 : observacao.hashCode());
		result = prime * result + ((pontoColeta == null) ? 0 : pontoColeta.hashCode());
		result = prime * result + ((responsavelCadastro == null) ? 0 : responsavelCadastro.hashCode());
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
		Coleta other = (Coleta) obj;
		if (dataColeta == null) {
			if (other.dataColeta != null)
				return false;
		} else if (!dataColeta.equals(other.dataColeta))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (motorista == null) {
			if (other.motorista != null)
				return false;
		} else if (!motorista.equals(other.motorista))
			return false;
		if (observacao == null) {
			if (other.observacao != null)
				return false;
		} else if (!observacao.equals(other.observacao))
			return false;
		if (pontoColeta == null) {
			if (other.pontoColeta != null)
				return false;
		} else if (!pontoColeta.equals(other.pontoColeta))
			return false;
		if (responsavelCadastro == null) {
			if (other.responsavelCadastro != null)
				return false;
		} else if (!responsavelCadastro.equals(other.responsavelCadastro))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	

}
