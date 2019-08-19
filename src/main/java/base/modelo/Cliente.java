package base.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import base.modelo.Pessoa;
import dao.GenericDAO;

@Entity
@Table(name = "cliente")
@PrimaryKeyJoinColumn(name = "id")
public class Cliente extends Pessoa implements Serializable {

	public Cliente() {
		super();
	}

	// atributos da classe usuario + serialversionUID auto
	private static final long serialVersionUID = 1L;
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private Long id;

	private Boolean status;
	private Boolean contratoGeradoCliente = false;
	private Boolean umContratoTodosPontos = false;
	private Boolean umBoletoTodosPontos = false;
	private String existeValorFixoMensal = "não"; 
	private Boolean cobraExcedente = false;
	private Double valorExcedente = 0.;
	private Double pesoMensal = 0.;
	private Double valorFixoMensalPonto = 0.;
	private Double valorFixoMensalTotal = 0.;
	private Boolean cobradoPorQuilo = false;
	private Double valorPorQuilo = 0.;

	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public Boolean getContratoGeradoCliente() {
		return contratoGeradoCliente;
	}
	public void setContratoGeradoCliente(Boolean contratoGeradoCliente) {
		this.contratoGeradoCliente = contratoGeradoCliente;
	}
	public Boolean getUmContratoTodosPontos() {
		return umContratoTodosPontos;
	}
	public void setUmContratoTodosPontos(Boolean umContratoTodosPontos) {
		this.umContratoTodosPontos = umContratoTodosPontos;
	}
	public Boolean getUmBoletoTodosPontos() {
		return umBoletoTodosPontos;
	}
	public void setUmBoletoTodosPontos(Boolean umBoletoTodosPontos) {
		this.umBoletoTodosPontos = umBoletoTodosPontos;
	}
	public String getExisteValorFixoMensal() {
		return existeValorFixoMensal;
	}
	public void setExisteValorFixoMensal(String existeValorFixoMensal) {
		this.existeValorFixoMensal = existeValorFixoMensal;
	}
	public Boolean getCobraExcedente() {
		return cobraExcedente;
	}
	public void setCobraExcedente(Boolean cobraExcedente) {
		this.cobraExcedente = cobraExcedente;
	}
	public Double getValorExcedente() {
		return valorExcedente;
	}
	public void setValorExcedente(Double valorExcedente) {
		this.valorExcedente = valorExcedente;
	}
	public Double getPesoMensal() {
		return pesoMensal;
	}
	public void setPesoMensal(Double pesoMensal) {
		this.pesoMensal = pesoMensal;
	}
	public Double getValorFixoMensalPonto() {
		return valorFixoMensalPonto;
	}
	public void setValorFixoMensalPonto(Double valorFixoMensalPonto) {
		this.valorFixoMensalPonto = valorFixoMensalPonto;
	}
	public Double getValorFixoMensalTotal() {
		return valorFixoMensalTotal;
	}
	public void setValorFixoMensalTotal(Double valorFixoMensalTotal) {
		this.valorFixoMensalTotal = valorFixoMensalTotal;
	}
	public Boolean getCobradoPorQuilo() {
		return cobradoPorQuilo;
	}
	public void setCobradoPorQuilo(Boolean cobradoPorQuilo) {
		this.cobradoPorQuilo = cobradoPorQuilo;
	}
	public Double getValorPorQuilo() {
		return valorPorQuilo;
	}
	public void setValorPorQuilo(Double valorPorQuilo) {
		this.valorPorQuilo = valorPorQuilo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cobraExcedente == null) ? 0 : cobraExcedente.hashCode());
		result = prime * result + ((cobradoPorQuilo == null) ? 0 : cobradoPorQuilo.hashCode());
		result = prime * result + ((contratoGeradoCliente == null) ? 0 : contratoGeradoCliente.hashCode());
		result = prime * result + ((existeValorFixoMensal == null) ? 0 : existeValorFixoMensal.hashCode());
		result = prime * result + ((pesoMensal == null) ? 0 : pesoMensal.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((umBoletoTodosPontos == null) ? 0 : umBoletoTodosPontos.hashCode());
		result = prime * result + ((umContratoTodosPontos == null) ? 0 : umContratoTodosPontos.hashCode());
		result = prime * result + ((valorExcedente == null) ? 0 : valorExcedente.hashCode());
		result = prime * result + ((valorFixoMensalPonto == null) ? 0 : valorFixoMensalPonto.hashCode());
		result = prime * result + ((valorFixoMensalTotal == null) ? 0 : valorFixoMensalTotal.hashCode());
		result = prime * result + ((valorPorQuilo == null) ? 0 : valorPorQuilo.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (cobraExcedente == null) {
			if (other.cobraExcedente != null)
				return false;
		} else if (!cobraExcedente.equals(other.cobraExcedente))
			return false;
		if (cobradoPorQuilo == null) {
			if (other.cobradoPorQuilo != null)
				return false;
		} else if (!cobradoPorQuilo.equals(other.cobradoPorQuilo))
			return false;
		if (contratoGeradoCliente == null) {
			if (other.contratoGeradoCliente != null)
				return false;
		} else if (!contratoGeradoCliente.equals(other.contratoGeradoCliente))
			return false;
		if (existeValorFixoMensal == null) {
			if (other.existeValorFixoMensal != null)
				return false;
		} else if (!existeValorFixoMensal.equals(other.existeValorFixoMensal))
			return false;
		if (pesoMensal == null) {
			if (other.pesoMensal != null)
				return false;
		} else if (!pesoMensal.equals(other.pesoMensal))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (umBoletoTodosPontos == null) {
			if (other.umBoletoTodosPontos != null)
				return false;
		} else if (!umBoletoTodosPontos.equals(other.umBoletoTodosPontos))
			return false;
		if (umContratoTodosPontos == null) {
			if (other.umContratoTodosPontos != null)
				return false;
		} else if (!umContratoTodosPontos.equals(other.umContratoTodosPontos))
			return false;
		if (valorExcedente == null) {
			if (other.valorExcedente != null)
				return false;
		} else if (!valorExcedente.equals(other.valorExcedente))
			return false;
		if (valorFixoMensalPonto == null) {
			if (other.valorFixoMensalPonto != null)
				return false;
		} else if (!valorFixoMensalPonto.equals(other.valorFixoMensalPonto))
			return false;
		if (valorFixoMensalTotal == null) {
			if (other.valorFixoMensalTotal != null)
				return false;
		} else if (!valorFixoMensalTotal.equals(other.valorFixoMensalTotal))
			return false;
		if (valorPorQuilo == null) {
			if (other.valorPorQuilo != null)
				return false;
		} else if (!valorPorQuilo.equals(other.valorPorQuilo))
			return false;
		return true;
	}
	
	
}
