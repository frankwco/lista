package base.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "pessoa")
@Inheritance(strategy = InheritanceType.JOINED)
public class Pessoa implements Serializable {

	public Pessoa() {
		super();
	}

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String observacao;

	private String nomeRazaoSocial;
	private String nomeFantasia;
	private String cpfCnpj;
	private String rgInscricaoEstadual;
	private Integer diaVencimentoFatura = 10;
	private String cep;
	private String tipo; // o tipo pode ser cliente, ponto de coleta ou usuario
							// colaborador.
	private String permissao="CLIENTE";
	private String endereco;
	private String numero;
	private String bairro;
	@ManyToOne
	private Cidade cidade;
	private String email = "";
	private String senha = "kkskssddf199";
	private Boolean status;
	private String responsavel;
	private String telefone1;
	private String telefone2;
	private String tipoPessoa = "Física"; // pode ser pessoa física ou jurídica

	private String edificio;
	private String sala;
	private String andar;
	private String complemento;
	private String email2;

	private String cpfResponsavel;
	private String rgResponsavel;
	private String orgaoEmissorRgResponsavel;
	private String enderecoResponsavel;
	@ManyToOne
	private Cidade cidadeResponsavel;
	
	private String apelido;
	
	

	public String getApelido() {
		if(apelido==null) {
			apelido="";
		}
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public String getPermissao() {
		return permissao;
	}

	public void setPermissao(String permissao) {
		this.permissao = permissao;
	}

	public String getCpfResponsavel() {
		return cpfResponsavel;
	}

	public void setCpfResponsavel(String cpfResponsavel) {
		this.cpfResponsavel = cpfResponsavel;
	}

	public String getRgResponsavel() {
		return rgResponsavel;
	}

	public void setRgResponsavel(String rgResponsavel) {
		this.rgResponsavel = rgResponsavel;
	}

	public String getOrgaoEmissorRgResponsavel() {
		return orgaoEmissorRgResponsavel;
	}

	public void setOrgaoEmissorRgResponsavel(String orgaoEmissorRgResponsavel) {
		this.orgaoEmissorRgResponsavel = orgaoEmissorRgResponsavel;
	}

	public String getEnderecoResponsavel() {
		return enderecoResponsavel;
	}

	public void setEnderecoResponsavel(String enderecoResponsavel) {
		this.enderecoResponsavel = enderecoResponsavel;
	}

	public Cidade getCidadeResponsavel() {
		return cidadeResponsavel;
	}

	public void setCidadeResponsavel(Cidade cidadeResponsavel) {
		this.cidadeResponsavel = cidadeResponsavel;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getAndar() {
		return andar;
	}

	public void setAndar(String andar) {
		this.andar = andar;
	}

	public String getEdificio() {
		return edificio;
	}

	public void setEdificio(String edificio) {
		this.edificio = edificio;
	}

	public String getSala() {
		return sala;
	}

	public void setSala(String sala) {
		this.sala = sala;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getNomeFantasia() {
		if (nomeFantasia == null) {
			nomeFantasia = "";
		}
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getNomeRazaoSocial() {
		if (nomeRazaoSocial == null) {
			nomeRazaoSocial = "";
		}
		return nomeRazaoSocial;
	}

	public void setNomeRazaoSocial(String nomeRazaoSocial) {
		this.nomeRazaoSocial = nomeRazaoSocial;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public String getRgInscricaoEstadual() {
		return rgInscricaoEstadual;
	}

	public void setRgInscricaoEstadual(String rgInscricaoEstadual) {
		this.rgInscricaoEstadual = rgInscricaoEstadual;
	}

	public Integer getDiaVencimentoFatura() {
		return diaVencimentoFatura;
	}

	public void setDiaVencimentoFatura(Integer diaVencimentoFatura) {
		this.diaVencimentoFatura = diaVencimentoFatura;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cep == null) ? 0 : cep.hashCode());
		result = prime * result + ((cidade == null) ? 0 : cidade.hashCode());
		result = prime * result + ((cpfCnpj == null) ? 0 : cpfCnpj.hashCode());
		result = prime * result + ((diaVencimentoFatura == null) ? 0 : diaVencimentoFatura.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nomeRazaoSocial == null) ? 0 : nomeRazaoSocial.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((observacao == null) ? 0 : observacao.hashCode());
		result = prime * result + ((responsavel == null) ? 0 : responsavel.hashCode());
		result = prime * result + ((rgInscricaoEstadual == null) ? 0 : rgInscricaoEstadual.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		Pessoa other = (Pessoa) obj;
		if (cep == null) {
			if (other.cep != null)
				return false;
		} else if (!cep.equals(other.cep))
			return false;
		if (cidade == null) {
			if (other.cidade != null)
				return false;
		} else if (!cidade.equals(other.cidade))
			return false;
		if (cpfCnpj == null) {
			if (other.cpfCnpj != null)
				return false;
		} else if (!cpfCnpj.equals(other.cpfCnpj))
			return false;
		if (diaVencimentoFatura == null) {
			if (other.diaVencimentoFatura != null)
				return false;
		} else if (!diaVencimentoFatura.equals(other.diaVencimentoFatura))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nomeRazaoSocial == null) {
			if (other.nomeRazaoSocial != null)
				return false;
		} else if (!nomeRazaoSocial.equals(other.nomeRazaoSocial))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (observacao == null) {
			if (other.observacao != null)
				return false;
		} else if (!observacao.equals(other.observacao))
			return false;
		if (responsavel == null) {
			if (other.responsavel != null)
				return false;
		} else if (!responsavel.equals(other.responsavel))
			return false;
		if (rgInscricaoEstadual == null) {
			if (other.rgInscricaoEstadual != null)
				return false;
		} else if (!rgInscricaoEstadual.equals(other.rgInscricaoEstadual))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}

	public String getNomeRazaoSocialNomeFantasia() {
		if (!getNomeRazaoSocial().equals("") && !getNomeFantasia().equals("")) {
			return getNomeRazaoSocial() + " - " + getNomeFantasia();
		} else {
			return getNomeRazaoSocial();
		}
	}

	public String getNomeRazaoSocialNomeFantasiaEndereco() {
		String retorno = "";
		if (!getNomeRazaoSocial().equals("") && !getNomeFantasia().equals("")) {
			retorno = getNomeRazaoSocial() + " - " + getNomeFantasia();
		} else {
			retorno = getNomeRazaoSocial();
		}
		
		if(!getApelido().equals("")) {
			retorno+=" ("+getApelido()+")";
		}

		if (getEndereco() != null) {
			retorno += ". " + getEndereco();
			if (getNumero() != null && !getNumero().trim().equals("")) {
				retorno += ", " + getNumero();
			}
			if(getCidade()!=null){
				retorno += " - " + getCidade().getNome()+"-"+getCidade().getEstado().getSigla();
			}
		}
		return retorno;
	}

}
