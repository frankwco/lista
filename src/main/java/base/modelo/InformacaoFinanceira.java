package base.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import base.modelo.Pessoa;
import dao.GenericDAO;

@Entity
@Table(name = "informacoes_financeiras")
public class InformacaoFinanceira implements Serializable {

	public InformacaoFinanceira() {
		super();
	}

	// atributos da classe usuario + serialversionUID auto
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private PontoColeta pontoColetaGerarContrato;

	@ManyToOne
	private Cliente cliente;

	private Boolean vigente = true;
	@Temporal(TemporalType.DATE)
	private Date dataInicial = new Date();
	@Temporal(TemporalType.DATE)
	private Date dataFinal = new Date();
	private Date dataEmissaoContrato = new Date();
	private Date dataPrimeiroPagamento = new Date();

	private Boolean status;
	private String responsavel;
	private String existeValorFixoMensal = "não";
	private String cobraExcedente = "Não";
	private Double valorExcedente = 0.;
	private Double pesoMensal = 0.;
	private Double valorFixoMensalPonto = 0.;
	private Double valorFixoMensalTotal = 0.;
	private Boolean cobradoPorQuilo = false;
	private Double valorPorQuilo = 0.;
	private Integer diaVencimentoFatura = 10;
	private String formaCobranca;
	private String romaneioContratoComDadosDo;
	private boolean contratoGeradoPeloCliente = false;
	private boolean gerarContratoParaEstaInformacao = false;
	private Long geradoPelasInformacoesFinanceirasID;
	private Long idInformacaoFinanceiraUnico;
	private Integer codigoContratoAtual = 0;
	private String recebimentoBoletoDeposito;
	private String banco;
	private String agencia;
	private String conta;
	private Boolean comunicarEmail = false;
	private Integer quantidadeMesesContrato;

	private Boolean fechamentoDataDiferente = false;
	private Integer diaFechamento=0;	
	@Temporal(TemporalType.DATE)
	private Date dataUltimoFechamento;
	
	@OneToMany(mappedBy = "informacoesFinanceiras", fetch = FetchType.EAGER)
	private List<ItensInformacaoFinanceiraPontoColeta> listaItensInformacoesFinanceirasPontoColeta;

	public List<ItensInformacaoFinanceiraPontoColeta> getListaItensInformacoesFinanceirasPontoColeta() {

		return listaItensInformacoesFinanceirasPontoColeta;
	}
	
	

	public Boolean getFechamentoDataDiferente() {
		return fechamentoDataDiferente;
	}



	public void setFechamentoDataDiferente(Boolean fechamentoDataDiferente) {
		this.fechamentoDataDiferente = fechamentoDataDiferente;
	}



	public Integer getDiaFechamento() {
		return diaFechamento;
	}



	public void setDiaFechamento(Integer diaFechamento) {
		this.diaFechamento = diaFechamento;
	}



	public Date getDataUltimoFechamento() {
		return dataUltimoFechamento;
	}



	public void setDataUltimoFechamento(Date dataUltimoFechamento) {
		this.dataUltimoFechamento = dataUltimoFechamento;
	}



	public String getListaItensInformacoesFinanceiraPontoColetaString() {
		String retorno = "Sem ponto";
		if (listaItensInformacoesFinanceirasPontoColeta != null
				&& listaItensInformacoesFinanceirasPontoColeta.size() > 0) {
			retorno="";
			int contador=1;
			for (int x=0;x<listaItensInformacoesFinanceirasPontoColeta.size();x++) {
				ItensInformacaoFinanceiraPontoColeta l = listaItensInformacoesFinanceirasPontoColeta.get(x);
				if (l.getPontoColeta() != null && l.getStatus()==true) {
					
					retorno +="<b>"+(contador)+"-</b> "+" ("+l.getPontoColeta().getId()+") "+ l.getPontoColeta().getNomeRazaoSocialNomeFantasiaEndereco()+" ";
					contador++;
				}
			}
		}
		return retorno;
	}	
	
	public Long getIdInformacaoFinanceiraUnico() {
		return idInformacaoFinanceiraUnico;
	}

	public void setIdInformacaoFinanceiraUnico(Long idInformacaoFinanceiraUnico) {
		this.idInformacaoFinanceiraUnico = idInformacaoFinanceiraUnico;
	}

	public String getFormaCobrancaRecebimento() {
		return formaCobranca+"/"+recebimentoBoletoDeposito;
	}

	public void setListaItensInformacoesFinanceirasPontoColeta(
			List<ItensInformacaoFinanceiraPontoColeta> listaItensInformacoesFinanceirasPontoColeta) {
		this.listaItensInformacoesFinanceirasPontoColeta = listaItensInformacoesFinanceirasPontoColeta;
	}

	public Integer getQuantidadeMesesContrato() {
		return quantidadeMesesContrato;
	}

	public void setQuantidadeMesesContrato(Integer quantidadeMesesContrato) {
		this.quantidadeMesesContrato = quantidadeMesesContrato;
	}

	public Boolean getComunicarEmail() {
		return comunicarEmail;
	}

	public void setComunicarEmail(Boolean comunicarEmail) {
		this.comunicarEmail = comunicarEmail;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public String getRecebimentoBoletoDeposito() {
		return recebimentoBoletoDeposito;
	}

	public void setRecebimentoBoletoDeposito(String recebimentoBoletoDeposito) {
		this.recebimentoBoletoDeposito = recebimentoBoletoDeposito;
	}

	public Integer getCodigoContratoAtual() {
		return codigoContratoAtual;
	}

	public void setCodigoContratoAtual(Integer codigoContratoAtual) {
		this.codigoContratoAtual = codigoContratoAtual;
	}

	public PontoColeta getPontoColetaGerarContrato() {
		return pontoColetaGerarContrato;
	}

	public void setPontoColetaGerarContrato(PontoColeta pontoColetaGerarContrato) {
		this.pontoColetaGerarContrato = pontoColetaGerarContrato;
	}

	public Date getDataEmissaoContrato() {
		return dataEmissaoContrato;
	}

	public void setDataEmissaoContrato(Date dataEmissaoContrato) {
		this.dataEmissaoContrato = dataEmissaoContrato;
	}

	public Date getDataPrimeiroPagamento() {
		return dataPrimeiroPagamento;
	}

	public void setDataPrimeiroPagamento(Date dataPrimeiroPagamento) {
		this.dataPrimeiroPagamento = dataPrimeiroPagamento;
	}

	public boolean isContratoGeradoPeloCliente() {
		return contratoGeradoPeloCliente;
	}

	public void setContratoGeradoPeloCliente(boolean contratoGeradoPeloCliente) {
		this.contratoGeradoPeloCliente = contratoGeradoPeloCliente;
	}

	public boolean isGerarContratoParaEstaInformacao() {
		return gerarContratoParaEstaInformacao;
	}

	public void setGerarContratoParaEstaInformacao(boolean gerarContratoParaEstaInformacao) {
		this.gerarContratoParaEstaInformacao = gerarContratoParaEstaInformacao;
	}

	public String getFormaCobranca() {
		return formaCobranca;
	}

	public void setFormaCobranca(String formaCobranca) {
		this.formaCobranca = formaCobranca;
	}

	public String getRomaneioContratoComDadosDo() {
		return romaneioContratoComDadosDo;
	}

	public void setRomaneioContratoComDadosDo(String romaneioContratoComDadosDo) {
		this.romaneioContratoComDadosDo = romaneioContratoComDadosDo;
	}

	public Long getGeradoPelasInformacoesFinanceirasID() {
		return geradoPelasInformacoesFinanceirasID;
	}

	public void setGeradoPelasInformacoesFinanceirasID(Long geradoPelasInformacoesFinanceirasID) {
		this.geradoPelasInformacoesFinanceirasID = geradoPelasInformacoesFinanceirasID;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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

	public Boolean getVigente() {
		return vigente;
	}

	public void setVigente(Boolean vigente) {
		this.vigente = vigente;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public String getExisteValorFixoMensal() {
		return existeValorFixoMensal;
	}

	public void setExisteValorFixoMensal(String existeValorFixoMensal) {
		this.existeValorFixoMensal = existeValorFixoMensal;
	}

	public String getCobraExcedente() {
		return cobraExcedente;
	}

	public void setCobraExcedente(String cobraExcedente) {
		this.cobraExcedente = cobraExcedente;
	}

	public Double getValorExcedente() {
		if(valorExcedente==null)
		{
			valorExcedente=0.;
		}
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

	public Integer getDiaVencimentoFatura() {
		return diaVencimentoFatura;
	}

	public void setDiaVencimentoFatura(Integer diaVencimentoFatura) {
		this.diaVencimentoFatura = diaVencimentoFatura;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cobraExcedente == null) ? 0 : cobraExcedente.hashCode());
		result = prime * result + ((cobradoPorQuilo == null) ? 0 : cobradoPorQuilo.hashCode());
		result = prime * result + ((dataFinal == null) ? 0 : dataFinal.hashCode());
		result = prime * result + ((dataInicial == null) ? 0 : dataInicial.hashCode());
		result = prime * result + ((diaVencimentoFatura == null) ? 0 : diaVencimentoFatura.hashCode());
		result = prime * result + ((existeValorFixoMensal == null) ? 0 : existeValorFixoMensal.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((pesoMensal == null) ? 0 : pesoMensal.hashCode());
		result = prime * result + ((responsavel == null) ? 0 : responsavel.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((valorExcedente == null) ? 0 : valorExcedente.hashCode());
		result = prime * result + ((valorFixoMensalPonto == null) ? 0 : valorFixoMensalPonto.hashCode());
		result = prime * result + ((valorFixoMensalTotal == null) ? 0 : valorFixoMensalTotal.hashCode());
		result = prime * result + ((valorPorQuilo == null) ? 0 : valorPorQuilo.hashCode());
		result = prime * result + ((vigente == null) ? 0 : vigente.hashCode());
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
		InformacaoFinanceira other = (InformacaoFinanceira) obj;
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
		if (dataFinal == null) {
			if (other.dataFinal != null)
				return false;
		} else if (!dataFinal.equals(other.dataFinal))
			return false;
		if (dataInicial == null) {
			if (other.dataInicial != null)
				return false;
		} else if (!dataInicial.equals(other.dataInicial))
			return false;
		if (diaVencimentoFatura == null) {
			if (other.diaVencimentoFatura != null)
				return false;
		} else if (!diaVencimentoFatura.equals(other.diaVencimentoFatura))
			return false;
		if (existeValorFixoMensal == null) {
			if (other.existeValorFixoMensal != null)
				return false;
		} else if (!existeValorFixoMensal.equals(other.existeValorFixoMensal))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (pesoMensal == null) {
			if (other.pesoMensal != null)
				return false;
		} else if (!pesoMensal.equals(other.pesoMensal))
			return false;
		if (responsavel == null) {
			if (other.responsavel != null)
				return false;
		} else if (!responsavel.equals(other.responsavel))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
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
		if (vigente == null) {
			if (other.vigente != null)
				return false;
		} else if (!vigente.equals(other.vigente))
			return false;
		return true;
	}

}
