package lista.modelo;

import java.io.Serializable;
import java.sql.Time;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tabelaservicocomum")
public class EntidadeServicoComum implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idServicoComum")
	private Long id;
	private String descricao;

	@JoinColumn(name = "idTipoServico")
	@ManyToOne
	private EntidadeTipoServico tipoServico;

	@Column(name = "diaSemana")
	private String diaSemana;

	@Column(name = "dia")
	private Integer dia;

	@Column(name = "hora")
	private Time hora;

	@Column(name = "mesSeguinte")
	private Boolean mesSeguinte;

	@Column(name = "mesAnterior")
	private Boolean mesAnterior;

	@Column(name = "janeiro")
	private Boolean janeiro;

	@Column(name = "fevereiro")
	private Boolean fevereiro;

	@Column(name = "marco")
	private Boolean marco;

	@Column(name = "abril")
	private Boolean abril;

	@Column(name = "maio")
	private Boolean maio;

	@Column(name = "junho")
	private Boolean junho;

	@Column(name = "julho")
	private Boolean julho;

	@Column(name = "agosto")
	private Boolean agosto;

	@Column(name = "setembro")
	private Boolean setembro;

	@Column(name = "outubro")
	private Boolean outubro;

	@Column(name = "novembro")
	private Boolean novembro;

	@Column(name = "dezembro")
	private Boolean dezembro;
	private Boolean inativar;
	private String status;
	private String codigoCasaOracao;
	private String horaStr;

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

	public String getDiaSemana() {
		return diaSemana;
	}

	public void setDiaSemana(String diaSemana) {
		this.diaSemana = diaSemana;
	}

	public Integer getDia() {
		return dia;
	}

	public void setDia(Integer dia) {
		this.dia = dia;
	}

	public Time getHora() {
		return hora;
	}

	public void setHora(Time hora) {
		this.hora = hora;
	}

	public Boolean getMesSeguinte() {
		return mesSeguinte;
	}

	public void setMesSeguinte(Boolean mesSeguinte) {
		this.mesSeguinte = mesSeguinte;
	}

	public Boolean getMesAnterior() {
		return mesAnterior;
	}

	public void setMesAnterior(Boolean mesAnterior) {
		this.mesAnterior = mesAnterior;
	}

	public Boolean getJaneiro() {
		return janeiro;
	}

	public void setJaneiro(Boolean janeiro) {
		this.janeiro = janeiro;
	}

	public Boolean getFevereiro() {
		return fevereiro;
	}

	public void setFevereiro(Boolean fevereiro) {
		this.fevereiro = fevereiro;
	}

	public Boolean getMarco() {
		return marco;
	}

	public void setMarco(Boolean marco) {
		this.marco = marco;
	}

	public Boolean getAbril() {
		return abril;
	}

	public void setAbril(Boolean abril) {
		this.abril = abril;
	}

	public Boolean getMaio() {
		return maio;
	}

	public void setMaio(Boolean maio) {
		this.maio = maio;
	}

	public Boolean getJunho() {
		return junho;
	}

	public void setJunho(Boolean junho) {
		this.junho = junho;
	}

	public Boolean getJulho() {
		return julho;
	}

	public void setJulho(Boolean julho) {
		this.julho = julho;
	}

	public Boolean getAgosto() {
		return agosto;
	}

	public void setAgosto(Boolean agosto) {
		this.agosto = agosto;
	}

	public Boolean getSetembro() {
		return setembro;
	}

	public void setSetembro(Boolean setembro) {
		this.setembro = setembro;
	}

	public Boolean getOutubro() {
		return outubro;
	}

	public void setOutubro(Boolean outubro) {
		this.outubro = outubro;
	}

	public Boolean getNovembro() {
		return novembro;
	}

	public void setNovembro(Boolean novembro) {
		this.novembro = novembro;
	}

	public Boolean getDezembro() {
		return dezembro;
	}

	public void setDezembro(Boolean dezembro) {
		this.dezembro = dezembro;
	}

	public Boolean getInativar() {
		return inativar;
	}

	public void setInativar(Boolean inativar) {
		this.inativar = inativar;
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

	public String getHoraStr() {
		return horaStr;
	}

	public void setHoraStr(String horaStr) {
		this.horaStr = horaStr;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((abril == null) ? 0 : abril.hashCode());
		result = prime * result + ((agosto == null) ? 0 : agosto.hashCode());
		result = prime * result + ((codigoCasaOracao == null) ? 0 : codigoCasaOracao.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((dezembro == null) ? 0 : dezembro.hashCode());
		result = prime * result + ((dia == null) ? 0 : dia.hashCode());
		result = prime * result + ((diaSemana == null) ? 0 : diaSemana.hashCode());
		result = prime * result + ((fevereiro == null) ? 0 : fevereiro.hashCode());
		result = prime * result + ((hora == null) ? 0 : hora.hashCode());
		result = prime * result + ((horaStr == null) ? 0 : horaStr.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((inativar == null) ? 0 : inativar.hashCode());
		result = prime * result + ((janeiro == null) ? 0 : janeiro.hashCode());
		result = prime * result + ((julho == null) ? 0 : julho.hashCode());
		result = prime * result + ((junho == null) ? 0 : junho.hashCode());
		result = prime * result + ((maio == null) ? 0 : maio.hashCode());
		result = prime * result + ((marco == null) ? 0 : marco.hashCode());
		result = prime * result + ((mesAnterior == null) ? 0 : mesAnterior.hashCode());
		result = prime * result + ((mesSeguinte == null) ? 0 : mesSeguinte.hashCode());
		result = prime * result + ((novembro == null) ? 0 : novembro.hashCode());
		result = prime * result + ((outubro == null) ? 0 : outubro.hashCode());
		result = prime * result + ((setembro == null) ? 0 : setembro.hashCode());
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
		EntidadeServicoComum other = (EntidadeServicoComum) obj;
		if (abril == null) {
			if (other.abril != null)
				return false;
		} else if (!abril.equals(other.abril))
			return false;
		if (agosto == null) {
			if (other.agosto != null)
				return false;
		} else if (!agosto.equals(other.agosto))
			return false;
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
		if (dezembro == null) {
			if (other.dezembro != null)
				return false;
		} else if (!dezembro.equals(other.dezembro))
			return false;
		if (dia == null) {
			if (other.dia != null)
				return false;
		} else if (!dia.equals(other.dia))
			return false;
		if (diaSemana == null) {
			if (other.diaSemana != null)
				return false;
		} else if (!diaSemana.equals(other.diaSemana))
			return false;
		if (fevereiro == null) {
			if (other.fevereiro != null)
				return false;
		} else if (!fevereiro.equals(other.fevereiro))
			return false;
		if (hora == null) {
			if (other.hora != null)
				return false;
		} else if (!hora.equals(other.hora))
			return false;
		if (horaStr == null) {
			if (other.horaStr != null)
				return false;
		} else if (!horaStr.equals(other.horaStr))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (inativar == null) {
			if (other.inativar != null)
				return false;
		} else if (!inativar.equals(other.inativar))
			return false;
		if (janeiro == null) {
			if (other.janeiro != null)
				return false;
		} else if (!janeiro.equals(other.janeiro))
			return false;
		if (julho == null) {
			if (other.julho != null)
				return false;
		} else if (!julho.equals(other.julho))
			return false;
		if (junho == null) {
			if (other.junho != null)
				return false;
		} else if (!junho.equals(other.junho))
			return false;
		if (maio == null) {
			if (other.maio != null)
				return false;
		} else if (!maio.equals(other.maio))
			return false;
		if (marco == null) {
			if (other.marco != null)
				return false;
		} else if (!marco.equals(other.marco))
			return false;
		if (mesAnterior == null) {
			if (other.mesAnterior != null)
				return false;
		} else if (!mesAnterior.equals(other.mesAnterior))
			return false;
		if (mesSeguinte == null) {
			if (other.mesSeguinte != null)
				return false;
		} else if (!mesSeguinte.equals(other.mesSeguinte))
			return false;
		if (novembro == null) {
			if (other.novembro != null)
				return false;
		} else if (!novembro.equals(other.novembro))
			return false;
		if (outubro == null) {
			if (other.outubro != null)
				return false;
		} else if (!outubro.equals(other.outubro))
			return false;
		if (setembro == null) {
			if (other.setembro != null)
				return false;
		} else if (!setembro.equals(other.setembro))
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