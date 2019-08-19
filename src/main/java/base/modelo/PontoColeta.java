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
@Table(name = "ponto_coleta")
@PrimaryKeyJoinColumn(name = "id")
public class PontoColeta extends Pessoa implements Serializable {

	public PontoColeta() {
		super();
	}

	// atributos da classe usuario + serialversionUID auto
	private static final long serialVersionUID = 1L;
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private Long id;

	private Boolean acessoSistema = false;
	private Boolean status;

	private Boolean emitirContrato = true;
	private Integer diaVencimentoFatura = 10;
	@ManyToOne
	private Cliente cliente;

	private String razaoSocialCliente = "";

	private String modeloCadastroPonto;

	@ManyToOne
	private Rota rota;

	private String nomeRota;

	private Integer ordem = 0;

	private String periodicidadeColeta;

	public String getNomeRota() {
		return nomeRota;
	}

	public void setNomeRota(String nomeRota) {
		this.nomeRota = nomeRota;
	}

	public Rota getRota() {
		return rota;
	}

	public void setRota(Rota rota) {
		this.rota = rota;
	}

	public Integer getOrdem() {
		if (ordem == null) {
			ordem = 0;
		}
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	public String getPeriodicidadeColeta() {
		return periodicidadeColeta;
	}

	public void setPeriodicidadeColeta(String periodicidadeColeta) {
		this.periodicidadeColeta = periodicidadeColeta;
	}

	public String getRazaoSocialCliente() {
		return razaoSocialCliente;
	}

	public void setRazaoSocialCliente(String razaoSocialCliente) {
		this.razaoSocialCliente = razaoSocialCliente;
	}

	public Boolean getAcessoSistema() {
		return acessoSistema;
	}

	public void setAcessoSistema(Boolean acessoSistema) {
		this.acessoSistema = acessoSistema;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Boolean getEmitirContrato() {
		return emitirContrato;
	}

	public void setEmitirContrato(Boolean emitirContrato) {
		this.emitirContrato = emitirContrato;
	}

	public Integer getDiaVencimentoFatura() {
		return diaVencimentoFatura;
	}

	public void setDiaVencimentoFatura(Integer diaVencimentoFatura) {
		this.diaVencimentoFatura = diaVencimentoFatura;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getModeloCadastroPonto() {
		return modeloCadastroPonto;
	}

	public void setModeloCadastroPonto(String modeloCadastroPonto) {
		this.modeloCadastroPonto = modeloCadastroPonto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((acessoSistema == null) ? 0 : acessoSistema.hashCode());
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((diaVencimentoFatura == null) ? 0 : diaVencimentoFatura.hashCode());
		result = prime * result + ((emitirContrato == null) ? 0 : emitirContrato.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		PontoColeta other = (PontoColeta) obj;
		if (acessoSistema == null) {
			if (other.acessoSistema != null)
				return false;
		} else if (!acessoSistema.equals(other.acessoSistema))
			return false;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (diaVencimentoFatura == null) {
			if (other.diaVencimentoFatura != null)
				return false;
		} else if (!diaVencimentoFatura.equals(other.diaVencimentoFatura))
			return false;
		if (emitirContrato == null) {
			if (other.emitirContrato != null)
				return false;
		} else if (!emitirContrato.equals(other.emitirContrato))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

}
