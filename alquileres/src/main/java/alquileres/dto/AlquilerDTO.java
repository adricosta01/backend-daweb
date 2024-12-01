package alquileres.dto;

import java.time.LocalDateTime;

public class AlquilerDTO {

	private String id;
	private String idBicicleta;
	private String inicio;
	private String fin;
	
	public AlquilerDTO(String id, String idBicicleta, String inicio, String fin) {
		this.id = id;
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
	public String getInicio() {
		return inicio;
	}
	public void setInicio(String inicio) {
		this.inicio = inicio;
	}
	public String getFin() {
		return fin;
	}
	public void setFin(String fin) {
		this.fin = fin;
	}	
}
