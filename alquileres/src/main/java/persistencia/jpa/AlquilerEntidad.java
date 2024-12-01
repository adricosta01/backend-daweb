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
public class AlquilerEntidad implements Identificable{
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private String id;
	
	private String idBicicleta;
	
	@Column(name = "fecha_inicio", columnDefinition = "TIMESTAMP")
	private LocalDateTime inicio;
	
	@Column(name = "fecha_fin", columnDefinition = "TIMESTAMP")
	private LocalDateTime fin;
	
	public AlquilerEntidad() {
	}
	
	public AlquilerEntidad(String idBicicleta, LocalDateTime inicio, LocalDateTime fin) {
		this.idBicicleta = idBicicleta;
		this.inicio = inicio;
		this.fin = fin;
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
	public LocalDateTime getInicio() {
		return inicio;
	}
	public void setInicio(LocalDateTime inicio) {
		this.inicio = inicio;
	}
	public LocalDateTime getFin() {
		return fin;
	}
	public void setFin(LocalDateTime fin) {
		this.fin = fin;
	}
	
	
	
}
