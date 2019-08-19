 package lista.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tabelausuario")
public class EntidadeUsuario
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  @Column(name="idUsuario")
  private Long id;
  private String nomeCompleto;
  private String fone;
  private String celular;
  private String status;
  private String email;
  private String nomeUsuario;
  private String senha;
  private String cargo;
  private String codigoCasaOracao;
  private String cadastrarUsuario;
  private String alterarSenha;
  private String permitirCadastroUsuario;
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getNomeCompleto() {
	return nomeCompleto;
}
public void setNomeCompleto(String nomeCompleto) {
	this.nomeCompleto = nomeCompleto;
}
public String getFone() {
	return fone;
}
public void setFone(String fone) {
	this.fone = fone;
}
public String getCelular() {
	return celular;
}
public void setCelular(String celular) {
	this.celular = celular;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getNomeUsuario() {
	return nomeUsuario;
}
public void setNomeUsuario(String nomeUsuario) {
	this.nomeUsuario = nomeUsuario;
}
public String getSenha() {
	return senha;
}
public void setSenha(String senha) {
	this.senha = senha;
}
public String getCargo() {
	return cargo;
}
public void setCargo(String cargo) {
	this.cargo = cargo;
}
public String getCodigoCasaOracao() {
	return codigoCasaOracao;
}
public void setCodigoCasaOracao(String codigoCasaOracao) {
	this.codigoCasaOracao = codigoCasaOracao;
}
public String getCadastrarUsuario() {
	return cadastrarUsuario;
}
public void setCadastrarUsuario(String cadastrarUsuario) {
	this.cadastrarUsuario = cadastrarUsuario;
}
public String getAlterarSenha() {
	return alterarSenha;
}
public void setAlterarSenha(String alterarSenha) {
	this.alterarSenha = alterarSenha;
}
public String getPermitirCadastroUsuario() {
	return permitirCadastroUsuario;
}
public void setPermitirCadastroUsuario(String permitirCadastroUsuario) {
	this.permitirCadastroUsuario = permitirCadastroUsuario;
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((alterarSenha == null) ? 0 : alterarSenha.hashCode());
	result = prime * result + ((cadastrarUsuario == null) ? 0 : cadastrarUsuario.hashCode());
	result = prime * result + ((cargo == null) ? 0 : cargo.hashCode());
	result = prime * result + ((celular == null) ? 0 : celular.hashCode());
	result = prime * result + ((codigoCasaOracao == null) ? 0 : codigoCasaOracao.hashCode());
	result = prime * result + ((email == null) ? 0 : email.hashCode());
	result = prime * result + ((fone == null) ? 0 : fone.hashCode());
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + ((nomeCompleto == null) ? 0 : nomeCompleto.hashCode());
	result = prime * result + ((nomeUsuario == null) ? 0 : nomeUsuario.hashCode());
	result = prime * result + ((permitirCadastroUsuario == null) ? 0 : permitirCadastroUsuario.hashCode());
	result = prime * result + ((senha == null) ? 0 : senha.hashCode());
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
	EntidadeUsuario other = (EntidadeUsuario) obj;
	if (alterarSenha == null) {
		if (other.alterarSenha != null)
			return false;
	} else if (!alterarSenha.equals(other.alterarSenha))
		return false;
	if (cadastrarUsuario == null) {
		if (other.cadastrarUsuario != null)
			return false;
	} else if (!cadastrarUsuario.equals(other.cadastrarUsuario))
		return false;
	if (cargo == null) {
		if (other.cargo != null)
			return false;
	} else if (!cargo.equals(other.cargo))
		return false;
	if (celular == null) {
		if (other.celular != null)
			return false;
	} else if (!celular.equals(other.celular))
		return false;
	if (codigoCasaOracao == null) {
		if (other.codigoCasaOracao != null)
			return false;
	} else if (!codigoCasaOracao.equals(other.codigoCasaOracao))
		return false;
	if (email == null) {
		if (other.email != null)
			return false;
	} else if (!email.equals(other.email))
		return false;
	if (fone == null) {
		if (other.fone != null)
			return false;
	} else if (!fone.equals(other.fone))
		return false;
	if (id == null) {
		if (other.id != null)
			return false;
	} else if (!id.equals(other.id))
		return false;
	if (nomeCompleto == null) {
		if (other.nomeCompleto != null)
			return false;
	} else if (!nomeCompleto.equals(other.nomeCompleto))
		return false;
	if (nomeUsuario == null) {
		if (other.nomeUsuario != null)
			return false;
	} else if (!nomeUsuario.equals(other.nomeUsuario))
		return false;
	if (permitirCadastroUsuario == null) {
		if (other.permitirCadastroUsuario != null)
			return false;
	} else if (!permitirCadastroUsuario.equals(other.permitirCadastroUsuario))
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
	return true;
}

  
}