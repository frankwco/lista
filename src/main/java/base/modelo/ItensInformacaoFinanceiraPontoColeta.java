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
@Table(name = "itens_informacoes_financeiras_ponto_coleta")
public class ItensInformacaoFinanceiraPontoColeta implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Boolean status = true;

	@ManyToOne
	private InformacaoFinanceira informacoesFinanceiras;

	@ManyToOne
	private PontoColeta pontoColeta;

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

	public PontoColeta getPontoColeta() {
		return pontoColeta;
	}

	public void setPontoColeta(PontoColeta pontoColeta) {
		this.pontoColeta = pontoColeta;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((informacoesFinanceiras == null) ? 0 : informacoesFinanceiras.hashCode());
		result = prime * result + ((pontoColeta == null) ? 0 : pontoColeta.hashCode());
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
		ItensInformacaoFinanceiraPontoColeta other = (ItensInformacaoFinanceiraPontoColeta) obj;
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
		if (pontoColeta == null) {
			if (other.pontoColeta != null)
				return false;
		} else if (!pontoColeta.equals(other.pontoColeta))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return pontoColeta.getNomeRazaoSocialNomeFantasiaEndereco();
	}


}
