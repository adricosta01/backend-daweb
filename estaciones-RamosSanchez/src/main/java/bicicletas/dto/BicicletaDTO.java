package bicicletas.dto;

import estaciones.modelo.Estacion;

public class BicicletaDTO {

	private String id;
	private String modelo;
	private Estacion estacion;
	
	public BicicletaDTO(String id, String modelo) {
		this.id = id;
		this.modelo = modelo;
	}
	
	public String getId() {
		return id;
	}
	
	public String getModelo() {
		return modelo;
	}
	
	public Estacion getEstacion() {
		return estacion;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
	public void setEstacion(Estacion estacion) {
		this.estacion = estacion;
	}
	
	
}
