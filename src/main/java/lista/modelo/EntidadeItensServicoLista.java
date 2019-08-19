package lista.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tabelaitensservicolista")
public class EntidadeItensServicoLista implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idItensServicoLista")
	private Long id;

	@Temporal(TemporalType.DATE)
	private Date dataServicoDate;
	private String dataServicoStr;
	private String hora;

	@Column(name = "descricao", length = 1000)
	private String descricao;

	@Column(name = "atendidoPor")
	private String atendidoPor;

	@JoinColumn(name = "idServicoLista")
	@ManyToOne
	private EntidadeServicoLista servicoLista;
	private String status;
	private String codigoCasaOracao;
	private String codigoMesAno;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataServicoDate() {
		return dataServicoDate;
	}

	public void setDataServicoDate(Date dataServicoDate) {
		this.dataServicoDate = dataServicoDate;
	}

	public String getDataServicoStr() {
		return dataServicoStr;
	}

	public void setDataServicoStr(String dataServicoStr) {
		this.dataServicoStr = dataServicoStr;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getAtendidoPor() {
		return atendidoPor;
	}

	public void setAtendidoPor(String atendidoPor) {
		this.atendidoPor = atendidoPor;
	}

	public EntidadeServicoLista getServicoLista() {
		return servicoLista;
	}

	public void setServicoLista(EntidadeServicoLista servicoLista) {
		this.servicoLista = servicoLista;
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

	public String getCodigoMesAno() {
		return codigoMesAno;
	}

	public void setCodigoMesAno(String codigoMesAno) {
		this.codigoMesAno = codigoMesAno;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((atendidoPor == null) ? 0 : atendidoPor.hashCode());
		result = prime * result + ((codigoCasaOracao == null) ? 0 : codigoCasaOracao.hashCode());
		result = prime * result + ((codigoMesAno == null) ? 0 : codigoMesAno.hashCode());
		result = prime * result + ((dataServicoDate == null) ? 0 : dataServicoDate.hashCode());
		result = prime * result + ((dataServicoStr == null) ? 0 : dataServicoStr.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((hora == null) ? 0 : hora.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((servicoLista == null) ? 0 : servicoLista.hashCode());
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
		EntidadeItensServicoLista other = (EntidadeItensServicoLista) obj;
		if (atendidoPor == null) {
			if (other.atendidoPor != null)
				return false;
		} else if (!atendidoPor.equals(other.atendidoPor))
			return false;
		if (codigoCasaOracao == null) {
			if (other.codigoCasaOracao != null)
				return false;
		} else if (!codigoCasaOracao.equals(other.codigoCasaOracao))
			return false;
		if (codigoMesAno == null) {
			if (other.codigoMesAno != null)
				return false;
		} else if (!codigoMesAno.equals(other.codigoMesAno))
			return false;
		if (dataServicoDate == null) {
			if (other.dataServicoDate != null)
				return false;
		} else if (!dataServicoDate.equals(other.dataServicoDate))
			return false;
		if (dataServicoStr == null) {
			if (other.dataServicoStr != null)
				return false;
		} else if (!dataServicoStr.equals(other.dataServicoStr))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (hora == null) {
			if (other.hora != null)
				return false;
		} else if (!hora.equals(other.hora))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (servicoLista == null) {
			if (other.servicoLista != null)
				return false;
		} else if (!servicoLista.equals(other.servicoLista))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

}