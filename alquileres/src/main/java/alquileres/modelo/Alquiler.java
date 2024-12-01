package alquileres.modelo;

import java.time.Duration;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import repositorio.Identificable;

public class Alquiler implements Identificable{

	private String id;
	private String idBicicleta;
	private LocalDateTime inicio;
	private LocalDateTime fin;
	
	public Alquiler(String idBicicleta) {
		this.idBicicleta = idBicicleta;
		this.inicio = LocalDateTime.now();
	}
	
	public Alquiler(String idBicicleta, LocalDateTime inicio, LocalDateTime fin) {
		this.idBicicleta = idBicicleta;
		this.inicio = inicio;
		this.fin = fin;
	}
	
	@Override
	public String getId() {
		return id;
	}
	
	@Override
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
	
	public boolean getActivo() {
		return this.fin == null;
	}
	
	public int getTiempo() {		
		return (int) Duration.between(this.inicio, (getActivo() ? LocalDateTime.now() : this.fin)).toMinutes();
	}
	
	
	
}
