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
@Table(name = "itens_informacoes_financeiras_grupo")
public class ItensInformacaoFinanceiraGrupo implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Boolean status = true;

	@ManyToOne
	private InformacaoFinanceira informacoesFinanceiras;

	@ManyToOne
	private GrupoResiduo grupoResiduos;

	private boolean cobraValorParte = false;
	private String periodicidadeColeta = "Quinzenal";// Mensal, Bimestral, Trimestral
	private Double valorFixo = 0.;
	private Double pesoValorFixo = 0.;
	private Double valorPorQuiloExcedente = 0.;

	public Double getValorFixo() {
		if (valorFixo == null) {
			valorFixo = 0.;
		}
		return valorFixo;
	}

	public void setValorFixo(Double valorFixo) {
		this.valorFixo = valorFixo;
	}

	public Double getPesoValorFixo() {
		if (pesoValorFixo == null) {
			pesoValorFixo = 0.;
		}
		return pesoValorFixo;
	}

	public void setPesoValorFixo(Double pesoValorFixo) {
		this.pesoValorFixo = pesoValorFixo;
	}

	public Double getValorPorQuiloExcedente() {
		return valorPorQuiloExcedente;
	}

	public void setValorPorQuiloExcedente(Double valorPorQuiloExcedente) {
		this.valorPorQuiloExcedente = valorPorQuiloExcedente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public InformacaoFinanceira getInformacoesFinanceiras() {
		return informacoesFinanceiras;
	}

	public void setInformacoesFinanceiras(InformacaoFinanceira informacoesFinanceiras) {
		this.informacoesFinanceiras = informacoesFinanceiras;
	}

	public GrupoResiduo getGrupoResiduos() {
		return grupoResiduos;
	}

	public void setGrupoResiduos(GrupoResiduo grupoResiduos) {
		this.grupoResiduos = grupoResiduos;
	}

	public boolean isCobraValorParte() {
		return cobraValorParte;
	}

	public void setCobraValorParte(boolean cobraValorParte) {
		this.cobraValorParte = cobraValorParte;
	}

	public String getPeriodicidadeColeta() {
		return periodicidadeColeta;
	}

	public void setPeriodicidadeColeta(String periodicidadeColeta) {
		this.periodicidadeColeta = periodicidadeColeta;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (cobraValorParte ? 1231 : 1237);
		result = prime * result + ((grupoResiduos == null) ? 0 : grupoResiduos.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((informacoesFinanceiras == null) ? 0 : informacoesFinanceiras.hashCode());
		result = prime * result + ((periodicidadeColeta == null) ? 0 : periodicidadeColeta.hashCode());
		result = prime * result + ((pesoValorFixo == null) ? 0 : pesoValorFixo.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((valorFixo == null) ? 0 : valorFixo.hashCode());
		result = prime * result + ((valorPorQuiloExcedente == null) ? 0 : valorPorQuiloExcedente.hashCode());
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
		ItensInformacaoFinanceiraGrupo other = (ItensInformacaoFinanceiraGrupo) obj;
		if (cobraValorParte != other.cobraValorParte)
			return false;
		if (grupoResiduos == null) {
			if (other.grupoResiduos != null)
				return false;
		} else if (!grupoResiduos.equals(other.grupoResiduos))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (informacoesFinanceiras == null) {
			if (other.informacoesFinanceiras != null)
				return false;
		} else if (!informacoesFinanceiras.equals(other.informacoesFinanceiras))
			return false;
		if (periodicidadeColeta == null) {
			if (other.periodicidadeColeta != null)
				return false;
		} else if (!periodicidadeColeta.equals(other.periodicidadeColeta))
			return false;
		if (pesoValorFixo == null) {
			if (other.pesoValorFixo != null)
				return false;
		} else if (!pesoValorFixo.equals(other.pesoValorFixo))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (valorFixo == null) {
			if (other.valorFixo != null)
				return false;
		} else if (!valorFixo.equals(other.valorFixo))
			return false;
		if (valorPorQuiloExcedente == null) {
			if (other.valorPorQuiloExcedente != null)
				return false;
		} else if (!valorPorQuiloExcedente.equals(other.valorPorQuiloExcedente))
			return false;
		return true;
	}

}
