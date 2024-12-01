package persistencia.jpa;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import repositorio.Identificable;

@Entity
public class ReservaEntidad implements Identificable{
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private String id;
	
	private String idBicicleta;
	
	@Column(name = "fecha_creacion", columnDefinition = "TIMESTAMP")
	private LocalDateTime creada;
	
	public ReservaEntidad() {
	}
	
	public ReservaEntidad(String idBicicleta, LocalDateTime creada) {
		this.idBicicleta = idBicicleta;
		this.creada = creada;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdBicicleta() {
		return idBicicleta;
	}
	public void setIdBicicleta(String idBicicleta) {
		this.idBicicleta = idBicicleta;
	}
	public LocalDateTime getCreada() {
		return creada;
	}
	public void setCreada(LocalDateTime creada) {
		this.creada = creada;
	}
	
}
