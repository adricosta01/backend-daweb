package reservas.dto;

import java.time.LocalDateTime;

public class ReservaDTO {

	private String id;
	private String idBicicleta;
	private String creada;
	private String caducidad;
	
	public ReservaDTO(String id, String idBicicleta, String creada, String caducidad) {
		this.id = id;
		this.idBicicleta = idBicicleta;
		this.creada = creada;
		this.caducidad = caducidad;
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
	public String getCreada() {
		return creada;
	}
	public void setCreada(String creada) {
		this.creada = creada;
	}
	public String getCaducidad() {
		return caducidad;
	}
	public void setCaducidad(String caducidad) {
		this.caducidad = caducidad;
	}
	
	
}
