package reservas.modelo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import repositorio.Identificable;

public class Reserva implements Identificable{
	
	private String id;
	private String idBicicleta;
	private LocalDateTime creada;
	private LocalDateTime caducidad;
	
	private static final int TIEMPO_MAXIMO_RESERVA = 30;

	public Reserva(String idBicicleta) {
		this.idBicicleta = idBicicleta;
		this.creada = LocalDateTime.now();
		this.caducidad = this.creada.plusMinutes(TIEMPO_MAXIMO_RESERVA);
	}
	
	public Reserva(String idBicicleta, LocalDateTime creada) {
		this.idBicicleta = idBicicleta;
		this.creada = creada;
		this.caducidad = this.creada.plusMinutes(TIEMPO_MAXIMO_RESERVA);
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

	public LocalDateTime getCreada() {
		return creada;
	}

	public void setCreada(LocalDateTime creada) {
		this.creada = creada;
	}

	public LocalDateTime getCaducidad() {
		return caducidad;
	}

	public void setCaducidad(LocalDateTime caducidad) {
		this.caducidad = caducidad;
	}

	public boolean caducada() {
		return LocalDateTime.now().isAfter(caducidad);
	}
	
	public boolean activa() {
		return !caducada();
	}
}
