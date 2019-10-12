package lista.modelo;
 
 import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.Table;
 
 @Entity
 @Table(name="tabelavisitas")
 public class EntidadeVisitas
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
 
   @Id
   @GeneratedValue(strategy=GenerationType.AUTO)
   @Column(name="idCasaOracao")
   private Long id;
   private Date dataVisita = new Date();
   private String cidade;
   private String onde;
   private String codigoCasaOracao;
   private String status;
   
   
public String getOnde() {
	return onde;
}
public void setOnde(String onde) {
	this.onde = onde;
}
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}

public String getCidade() {
	return cidade;
}
public void setCidade(String cidade) {
	this.cidade = cidade;
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
public Date getDataVisita() {
	return dataVisita;
}
public void setDataVisita(Date dataVisita) {
	this.dataVisita = dataVisita;
}


 
 
 }