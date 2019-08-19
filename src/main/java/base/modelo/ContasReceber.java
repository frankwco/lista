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
import javax.persistence.Transient;

@Entity
@Table(name = "contas_receber")
public class ContasReceber implements Serializable {

	public ContasReceber() {
		super();
	}

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Date dataCadastro = new Date();
	@ManyToOne(fetch=FetchType.LAZY)
	private InformacaoFinanceira informacaoFinanceira;
	
	@ManyToOne
	private FechamentoMes fechamentoMes;	
	private Double valorTotal;
	private String observacao;
	private Integer quantidadeParcelas;
	private Double valorParcelas;
	private Date dataPrimeiroVencimento = new Date();
	@Transient
	private Integer nossoNumero;
	private Integer mesPrimeiraParcela;
	private Integer anoPrimeiraParcela;
	private Boolean status=true;
	

		
	public FechamentoMes getFechamentoMes() {
		return fechamentoMes;
	}

	public void setFechamentoMes(FechamentoMes fechamentoMes) {
		this.fechamentoMes = fechamentoMes;
	}

	public Integer getMesPrimeiraParcela() {
		return mesPrimeiraParcela;
	}

	public void setMesPrimeiraParcela(Integer mesPrimeiraParcela) {
		this.mesPrimeiraParcela = mesPrimeiraParcela;
	}

	public Integer getAnoPrimeiraParcela() {
		return anoPrimeiraParcela;
	}

	public void setAnoPrimeiraParcela(Integer anoPrimeiraParcela) {
		this.anoPrimeiraParcela = anoPrimeiraParcela;
	}

	
	

	public Integer getNossoNumero() {
		return nossoNumero;
	}

	public void setNossoNumero(Integer nossoNumero) {
		this.nossoNumero = nossoNumero;
	}

	public Date getDataPrimeiroVencimento() {
		return dataPrimeiroVencimento;
	}

	public void setDataPrimeiroVencimento(Date dataPrimeiroVencimento) {
		this.dataPrimeiroVencimento = dataPrimeiroVencimento;
	}

	public Double getValorParcelas() {
		return valorParcelas;
	}

	public void setValorParcelas(Double valorParcelas) {
		this.valorParcelas = valorParcelas;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public InformacaoFinanceira getInformacaoFinanceira() {
		return informacaoFinanceira;
	}

	public void setInformacaoFinanceira(InformacaoFinanceira informacaoFinanceira) {
		this.informacaoFinanceira = informacaoFinanceira;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Integer getQuantidadeParcelas() {
		return quantidadeParcelas;
	}

	public void setQuantidadeParcelas(Integer quantidadeParcelas) {
		this.quantidadeParcelas = quantidadeParcelas;
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
		result = prime * result + ((dataCadastro == null) ? 0 : dataCadastro.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((informacaoFinanceira == null) ? 0 : informacaoFinanceira.hashCode());
		result = prime * result + ((observacao == null) ? 0 : observacao.hashCode());
		result = prime * result + ((quantidadeParcelas == null) ? 0 : quantidadeParcelas.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((valorTotal == null) ? 0 : valorTotal.hashCode());
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
		ContasReceber other = (ContasReceber) obj;
		if (dataCadastro == null) {
			if (other.dataCadastro != null)
				return false;
		} else if (!dataCadastro.equals(other.dataCadastro))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (informacaoFinanceira == null) {
			if (other.informacaoFinanceira != null)
				return false;
		} else if (!informacaoFinanceira.equals(other.informacaoFinanceira))
			return false;
		if (observacao == null) {
			if (other.observacao != null)
				return false;
		} else if (!observacao.equals(other.observacao))
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
		if (valorTotal == null) {
			if (other.valorTotal != null)
				return false;
		} else if (!valorTotal.equals(other.valorTotal))
			return false;
		return true;
	}

	
	
}
