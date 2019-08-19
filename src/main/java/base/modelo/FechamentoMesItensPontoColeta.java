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
@Table(name = "fechamento_mes_itens_ponto_coleta")
public class FechamentoMesItensPontoColeta implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Boolean status = true;

	@ManyToOne
	private FechamentoMes fechamentoMes;

	@ManyToOne
	private ItensInformacaoFinanceiraPontoColeta itensPontoColeta;

	private Double valorTotal = 0.;
	private Double valorFixoMensalContratoPonto = 0.;//
	private Double valorFixoMensalCobradoParte = 0.;//
	private Double valorExcedenteGruposContrato = 0.;//
	private Double valorExcedenteGruposCobradosParte = 0.;//
	private Double pesoExcedenteGruposContrato = 0.;//
	private Double pesoExcedenteCobradoParte = 0.;//
	private Double pesoColetadoGruposContrato = 0.;//
	private Double pesoColetadoGrupoParte = 0.;//
	private String gruposFazemParteContrato = "";
	private String gruposCobradosParte = "";
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
	public FechamentoMes getFechamentoMes() {
		return fechamentoMes;
	}
	public void setFechamentoMes(FechamentoMes fechamentoMes) {
		this.fechamentoMes = fechamentoMes;
	}
	public ItensInformacaoFinanceiraPontoColeta getItensPontoColeta() {
		return itensPontoColeta;
	}
	public void setItensPontoColeta(ItensInformacaoFinanceiraPontoColeta itensPontoColeta) {
		this.itensPontoColeta = itensPontoColeta;
	}
	public Double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
	public Double getValorFixoMensalContratoPonto() {
		return valorFixoMensalContratoPonto;
	}
	public void setValorFixoMensalContratoPonto(Double valorFixoMensalContratoPonto) {
		this.valorFixoMensalContratoPonto = valorFixoMensalContratoPonto;
	}
	public Double getValorFixoMensalCobradoParte() {
		if(valorFixoMensalCobradoParte==null) {
			valorFixoMensalCobradoParte=0.;
		}
		return valorFixoMensalCobradoParte;
	}
	public void setValorFixoMensalCobradoParte(Double valorFixoMensalCobradoParte) {
		this.valorFixoMensalCobradoParte = valorFixoMensalCobradoParte;
	}
	public Double getValorExcedenteGruposContrato() {
		return valorExcedenteGruposContrato;
	}
	public void setValorExcedenteGruposContrato(Double valorExcedenteGruposContrato) {
		this.valorExcedenteGruposContrato = valorExcedenteGruposContrato;
	}
	public Double getValorExcedenteGruposCobradosParte() {
		return valorExcedenteGruposCobradosParte;
	}
	public void setValorExcedenteGruposCobradosParte(Double valorExcedenteGruposCobradosParte) {
		this.valorExcedenteGruposCobradosParte = valorExcedenteGruposCobradosParte;
	}
	public Double getPesoExcedenteGruposContrato() {
		return pesoExcedenteGruposContrato;
	}
	public void setPesoExcedenteGruposContrato(Double pesoExcedenteGruposContrato) {
		this.pesoExcedenteGruposContrato = pesoExcedenteGruposContrato;
	}
	public Double getPesoExcedenteCobradoParte() {
		return pesoExcedenteCobradoParte;
	}
	public void setPesoExcedenteCobradoParte(Double pesoExcedenteCobradoParte) {
		this.pesoExcedenteCobradoParte = pesoExcedenteCobradoParte;
	}
	public Double getPesoColetadoGruposContrato() {
		return pesoColetadoGruposContrato;
	}
	public void setPesoColetadoGruposContrato(Double pesoColetadoGruposContrato) {
		this.pesoColetadoGruposContrato = pesoColetadoGruposContrato;
	}
	public Double getPesoColetadoGrupoParte() {
		if(pesoColetadoGrupoParte==null) {
			pesoColetadoGrupoParte=0.;
		}
		return pesoColetadoGrupoParte;
	}
	public void setPesoColetadoGrupoParte(Double pesoColetadoGrupoParte) {
		this.pesoColetadoGrupoParte = pesoColetadoGrupoParte;
	}
	public String getGruposFazemParteContrato() {
		return gruposFazemParteContrato;
	}
	public void setGruposFazemParteContrato(String gruposFazemParteContrato) {
		this.gruposFazemParteContrato = gruposFazemParteContrato;
	}
	public String getGruposCobradosParte() {
		return gruposCobradosParte;
	}
	public void setGruposCobradosParte(String gruposCobradosParte) {
		this.gruposCobradosParte = gruposCobradosParte;
	}

	
}
