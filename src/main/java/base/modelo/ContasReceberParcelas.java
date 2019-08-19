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

@Entity
@Table(name = "contas_receber_parcelas")
public class ContasReceberParcelas implements Serializable {

	public ContasReceberParcelas() {
		super();
	}

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Date dataVencimento;
	private Date dataPagamento;
	@ManyToOne
	private ContasReceber contasReceber;
	private Integer parcela;
	private Integer quantidadeParcelas;
	private Double valorPago;
	private Double acrescimoJuros;
	private Double desconto;
	private Double valorParcela;
	private String observacao;
	private Integer referenteMes;
	private Integer referenteAno;
	private Boolean status = true;
	private String nossoNumero;
	private Boolean boletoEmitido = false;
	private Long idInformacaoFinanceiraUnico;

	@ManyToOne
	private FechamentoMes fechamentoMes;

	public Long getIdInformacaoFinanceiraUnico() {
		return idInformacaoFinanceiraUnico;
	}

	public void setIdInformacaoFinanceiraUnico(Long idInformacaoFinanceiraUnico) {
		this.idInformacaoFinanceiraUnico = idInformacaoFinanceiraUnico;
	}

	public Boolean getBoletoEmitido() {
		return boletoEmitido;
	}

	public void setBoletoEmitido(Boolean boletoEmitido) {
		this.boletoEmitido = boletoEmitido;
	}

	public FechamentoMes getFechamentoMes() {
		return fechamentoMes;
	}

	public void setFechamentoMes(FechamentoMes fechamentoMes) {
		this.fechamentoMes = fechamentoMes;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public String getNossoNumero() {
		if (nossoNumero == null) {
			nossoNumero = "";
		}
		return nossoNumero;
	}

	public void setNossoNumero(String nossoNumero) {
		this.nossoNumero = nossoNumero;
	}

	public Integer getReferenteMes() {
		return referenteMes;
	}

	public void setReferenteMes(Integer referenteMes) {
		this.referenteMes = referenteMes;
	}

	public Integer getReferenteAno() {
		return referenteAno;
	}

	public void setReferenteAno(Integer referenteAno) {
		this.referenteAno = referenteAno;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public ContasReceber getContasReceber() {
		return contasReceber;
	}

	public void setContasReceber(ContasReceber contasReceber) {
		this.contasReceber = contasReceber;
	}

	public Integer getParcela() {
		return parcela;
	}

	public void setParcela(Integer parcela) {
		this.parcela = parcela;
	}

	public Integer getQuantidadeParcelas() {
		return quantidadeParcelas;
	}

	public void setQuantidadeParcelas(Integer quantidadeParcelas) {
		this.quantidadeParcelas = quantidadeParcelas;
	}

	public Double getValorPago() {
		return valorPago;
	}

	public void setValorPago(Double valorPago) {
		this.valorPago = valorPago;
	}

	public Double getAcrescimoJuros() {
		return acrescimoJuros;
	}

	public void setAcrescimoJuros(Double acrescimoJuros) {
		this.acrescimoJuros = acrescimoJuros;
	}

	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

	public Double getValorParcela() {
		return valorParcela;
	}

	public void setValorParcela(Double valorParcela) {
		this.valorParcela = valorParcela;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
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
		result = prime * result + ((acrescimoJuros == null) ? 0 : acrescimoJuros.hashCode());
		result = prime * result + ((contasReceber == null) ? 0 : contasReceber.hashCode());
		result = prime * result + ((dataVencimento == null) ? 0 : dataVencimento.hashCode());
		result = prime * result + ((desconto == null) ? 0 : desconto.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((observacao == null) ? 0 : observacao.hashCode());
		result = prime * result + ((parcela == null) ? 0 : parcela.hashCode());
		result = prime * result + ((quantidadeParcelas == null) ? 0 : quantidadeParcelas.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((valorPago == null) ? 0 : valorPago.hashCode());
		result = prime * result + ((valorParcela == null) ? 0 : valorParcela.hashCode());
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
		ContasReceberParcelas other = (ContasReceberParcelas) obj;
		if (acrescimoJuros == null) {
			if (other.acrescimoJuros != null)
				return false;
		} else if (!acrescimoJuros.equals(other.acrescimoJuros))
			return false;
		if (contasReceber == null) {
			if (other.contasReceber != null)
				return false;
		} else if (!contasReceber.equals(other.contasReceber))
			return false;
		if (dataVencimento == null) {
			if (other.dataVencimento != null)
				return false;
		} else if (!dataVencimento.equals(other.dataVencimento))
			return false;
		if (desconto == null) {
			if (other.desconto != null)
				return false;
		} else if (!desconto.equals(other.desconto))
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
		if (parcela == null) {
			if (other.parcela != null)
				return false;
		} else if (!parcela.equals(other.parcela))
			return false;
		if (quantidadeParcelas == null) {
			if (other.quantidadeParcelas != null)
				return false;
		} else if (!quantidadeParcelas.equals(other.quantidadeParcelas))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (valorPago == null) {
			if (other.valorPago != null)
				return false;
		} else if (!valorPago.equals(other.valorPago))
			return false;
		if (valorParcela == null) {
			if (other.valorParcela != null)
				return false;
		} else if (!valorParcela.equals(other.valorParcela))
			return false;
		return true;
	}

}
