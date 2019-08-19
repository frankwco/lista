 package lista.modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tabelacontroleacesso")
public class EntidadeControleAcesso
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long id;
  private String codigoCasaOracao;
  private String status;
  private String usuario;
  private String dataHora;
  private Long idUsuario;
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getCodigoCasaOracao() {
	return codigoCasaOracao;
}
public void setCodigoCasaOracao(String codigoCasaOracao) {
	this.codigoCasaOracao = codigoCasaOracao;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getUsuario() {
	return usuario;
}
public void setUsuario(String usuario) {
	this.usuario = usuario;
}
public String getDataHora() {
	return dataHora;
}
public void setDataHora(String dataHora) {
	this.dataHora = dataHora;
}
public Long getIdUsuario() {
	return idUsuario;
}
public void setIdUsuario(Long idUsuario) {
	this.idUsuario = idUsuario;
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((codigoCasaOracao == null) ? 0 : codigoCasaOracao.hashCode());
	result = prime * result + ((dataHora == null) ? 0 : dataHora.hashCode());
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + ((idUsuario == null) ? 0 : idUsuario.hashCode());
	result = prime * result + ((status == null) ? 0 : status.hashCode());
	result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
	EntidadeControleAcesso other = (EntidadeControleAcesso) obj;
	if (codigoCasaOracao == null) {
		if (other.codigoCasaOracao != null)
			return false;
	} else if (!codigoCasaOracao.equals(other.codigoCasaOracao))
		return false;
	if (dataHora == null) {
		if (other.dataHora != null)
			return false;
	} else if (!dataHora.equals(other.dataHora))
		return false;
	if (id == null) {
		if (other.id != null)
			return false;
	} else if (!id.equals(other.id))
		return false;
	if (idUsuario == null) {
		if (other.idUsuario != null)
			return false;
	} else if (!idUsuario.equals(other.idUsuario))
		return false;
	if (status == null) {
		if (other.status != null)
			return false;
	} else if (!status.equals(other.status))
		return false;
	if (usuario == null) {
		if (other.usuario != null)
			return false;
	} else if (!usuario.equals(other.usuario))
		return false;
	return true;
}

  
}