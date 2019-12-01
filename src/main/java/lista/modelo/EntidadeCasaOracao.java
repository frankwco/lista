package lista.modelo;
 
 import java.io.Serializable;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.Table;
 
 @Entity
 @Table(name="tabelacasaoracao")
 public class EntidadeCasaOracao
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
 
   @Id
   @GeneratedValue(strategy=GenerationType.AUTO)
   @Column(name="idCasaOracao")
   private Long id;
   private String endereco;
   private String cidade;
   private String telefone;
   private String codigoCasaOracao;
   private String status;
   private String estado;
   
   
public String getEstado() {
	return estado;
}
public void setEstado(String estado) {
	this.estado = estado;
}
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getEndereco() {
	return endereco;
}
public void setEndereco(String endereco) {
	this.endereco = endereco;
}
public String getCidade() {
	return cidade;
}
public void setCidade(String cidade) {
	this.cidade = cidade;
}
public String getTelefone() {
	return telefone;
}
public void setTelefone(String telefone) {
	this.telefone = telefone;
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
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((cidade == null) ? 0 : cidade.hashCode());
	result = prime * result + ((codigoCasaOracao == null) ? 0 : codigoCasaOracao.hashCode());
	result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + ((status == null) ? 0 : status.hashCode());
	result = prime * result + ((telefone == null) ? 0 : telefone.hashCode());
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
	EntidadeCasaOracao other = (EntidadeCasaOracao) obj;
	if (cidade == null) {
		if (other.cidade != null)
			return false;
	} else if (!cidade.equals(other.cidade))
		return false;
	if (codigoCasaOracao == null) {
		if (other.codigoCasaOracao != null)
			return false;
	} else if (!codigoCasaOracao.equals(other.codigoCasaOracao))
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
	if (status == null) {
		if (other.status != null)
			return false;
	} else if (!status.equals(other.status))
		return false;
	if (telefone == null) {
		if (other.telefone != null)
			return false;
	} else if (!telefone.equals(other.telefone))
		return false;
	return true;
}
 
 
 }