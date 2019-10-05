package base.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "fechamento_mes")
public class FechamentoMes implements Serializable {

	public FechamentoMes() {
		super();
	}

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Integer mes;
	private Integer ano;
	private Date dataFechamento;
	private Double valorTotalPorQuilo = 0.;
	private Double valorFinal = 0.;
	private Boolean emitidoContasReceber = false;
	private Integer quantidadePontos;
	private Double diferencaGerarContasPagar = 0.;
	private Boolean status = true;
	@ManyToOne
	private InformacaoFinanceira informacaoFinanceira;
	
	private Double valorFixoTodosPontos=0.;
	private Double valorExcedenteTodosPontos=0.;
	private Double pesoExcedenteTodosPontos=0.;
	private Long idInformacaoFinanceiraUnico;
	

	private Double valorFixoMensalContratoPonto = 0.;//
	private Double valorFixoMensalCobradoParte = 0.;//
	private Double valorExcedenteGruposContrato = 0.;//
	private Double valorExcedenteGruposCobradosParte = 0.;//
	private Double valorFaturar=0.;
	private Double pesoExcedenteGruposContrato = 0.;//
	private Double pesoExcedenteCobradoParte = 0.;//
	private Double pesoColetadoGruposContrato = 0.;//
	private Double pesoColetadoGrupoParte = 0.;//
	private Double valorPagoFaturamento=0.;
	
	private Boolean faturado = false;
	
	private String observacao="";	
	private Boolean fechamentoAnteriorAberto=false;
	private Double valorDesconto=0.;
	
	@Temporal(TemporalType.DATE)
	private Date dataInicialFechamento;
	@Temporal(TemporalType.DATE)
	private Date dataFinalFechamento;
	
	
	
	
	
	
	public Date getDataInicialFechamento() {
		return dataInicialFechamento;
	}

	public void setDataInicialFechamento(Date dataInicialFechamento) {
		this.dataInicialFechamento = dataInicialFechamento;
	}

	public Date getDataFinalFechamento() {
		return dataFinalFechamento;
	}

	public void setDataFinalFechamento(Date dataFinalFechamento) {
		this.dataFinalFechamento = dataFinalFechamento;
	}

	public Double getValorDesconto() {
	if(valorDesconto==null) {
		valorDesconto=0.;
	}
		return valorDesconto;
	}

	public void setValorDesconto(Double valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public Boolean getFechamentoAnteriorAberto() {
		return fechamentoAnteriorAberto;
	}

	public void setFechamentoAnteriorAberto(Boolean fechamentoAnteriorAberto) {
		this.fechamentoAnteriorAberto = fechamentoAnteriorAberto;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Double getValorPagoFaturamento() {
		return valorPagoFaturamento;
	}

	public void setValorPagoFaturamento(Double valorPagoFaturamento) {
		this.valorPagoFaturamento = valorPagoFaturamento;
	}

	public Double getValorFaturar() {
		return valorFaturar;
	}

	public void setValorFaturar(Double valorFaturar) {
		this.valorFaturar = valorFaturar;
	}

	public Long getIdInformacaoFinanceiraUnico() {
		return idInformacaoFinanceiraUnico;
	}

	public void setIdInformacaoFinanceiraUnico(Long idInformacaoFinanceiraUnico) {
		this.idInformacaoFinanceiraUnico = idInformacaoFinanceiraUnico;
	}

	public Boolean getFaturado() {
		if(faturado==null) {
			faturado = false;
		}
		return faturado;
	}

	public void setFaturado(Boolean faturado) {
		this.faturado = faturado;
	}

	public Double getValorExcedenteTodosPontos() {
		return valorExcedenteTodosPontos;
	}

	public void setValorExcedenteTodosPontos(Double valorExcedenteTodosPontos) {
		this.valorExcedenteTodosPontos = valorExcedenteTodosPontos;
	}

	public Double getPesoExcedenteTodosPontos() {
		return pesoExcedenteTodosPontos;
	}

	public void setPesoExcedenteTodosPontos(Double pesoExcedenteTodosPontos) {
		this.pesoExcedenteTodosPontos = pesoExcedenteTodosPontos;
	}

	public Double getValorFixoTodosPontos() {
		return valorFixoTodosPontos;
	}

	public void setValorFixoTodosPontos(Double valorFixoTodosPontos) {
		this.valorFixoTodosPontos = valorFixoTodosPontos;
	}

	public InformacaoFinanceira getInformacaoFinanceira() {
		return informacaoFinanceira;
	}

	public void setInformacaoFinanceira(InformacaoFinanceira informacaoFinanceira) {
		this.informacaoFinanceira = informacaoFinanceira;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Date getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(Date dataFechamento) {
		this.dataFechamento = dataFechamento;
	}

	public Double getValorTotalPorQuilo() {
		return valorTotalPorQuilo;
	}

	public void setValorTotalPorQuilo(Double valorTotalPorQuilo) {
		this.valorTotalPorQuilo = valorTotalPorQuilo;
	}

	public Double getValorFinal() {
		return valorFinal;
	}

	public void setValorFinal(Double valorFinal) {
		this.valorFinal = valorFinal;
	}

	public Boolean getEmitidoContasReceber() {
		return emitidoContasReceber;
	}

	public void setEmitidoContasReceber(Boolean emitidoContasReceber) {
		this.emitidoContasReceber = emitidoContasReceber;
	}

	public Integer getQuantidadePontos() {
		return quantidadePontos;
	}

	public void setQuantidadePontos(Integer quantidadePontos) {
		this.quantidadePontos = quantidadePontos;
	}

	public Double getDiferencaGerarContasPagar() {
		return diferencaGerarContasPagar;
	}

	public void setDiferencaGerarContasPagar(Double diferencaGerarContasPagar) {
		this.diferencaGerarContasPagar = diferencaGerarContasPagar;
	}

	public Double getValorFixoMensalContratoPonto() {
		return valorFixoMensalContratoPonto;
	}

	public void setValorFixoMensalContratoPonto(Double valorFixoMensalContratoPonto) {
		this.valorFixoMensalContratoPonto = valorFixoMensalContratoPonto;
	}

	public Double getValorFixoMensalCobradoParte() {
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
		return pesoColetadoGrupoParte;
	}

	public void setPesoColetadoGrupoParte(Double pesoColetadoGrupoParte) {
		this.pesoColetadoGrupoParte = pesoColetadoGrupoParte;
	}

}
