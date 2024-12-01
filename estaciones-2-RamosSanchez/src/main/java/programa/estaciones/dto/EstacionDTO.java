package programa.estaciones.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import programa.estaciones.modelo.Estacion;

@Schema(description ="DTO de la entidad Estacion")
public class EstacionDTO {

	@Schema(description ="Identificador de la estación")
	@NotNull
	private String id;
	@Schema(description ="Nombre de la estación")
	@NotNull
	private String nombre;
	@Schema(description ="Fecha de alta de la estión")
	@NotNull
	private String fechaAlta;
	@Schema(description ="Número máximo de bicis que permite la estación")
	@NotNull
	private int maxPuestosBicis;
	@Schema(description ="Código postal de la estación")
	private int codigoPostal;
	@Schema(description ="Coordenada Y de la localización de la estación")
	private double longitud;	
	@Schema(description ="Coordenada X de la localización de la estación")
	private double latitud;
	@Schema(description ="Número de bicis estacionadas en la estación")
	@NotNull
	@Min(value = 0)
	private int numBicis;
	@Schema(description ="Valor booleano que expresa si la estación tiene huecos disponibles para estacionar una bici")
	private boolean tieneHuecosLibres;
	
	public EstacionDTO(String id, String nombre, String fechaAlta, int maxPuestosBicis, int codigoPostal,
			double longitud, double latitud, int numBicis) {
		this.id = id;
		this.nombre = nombre;
		this.fechaAlta = fechaAlta;
		this.maxPuestosBicis = maxPuestosBicis;
		this.codigoPostal = codigoPostal;
		this.longitud = longitud;
		this.latitud = latitud;
		this.numBicis = numBicis;
		this.tieneHuecosLibres = maxPuestosBicis - numBicis > 0 ? true : false;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public int getMaxPuestosBicis() {
		return maxPuestosBicis;
	}
	public void setMaxPuestosBicis(int maxPuestosBicis) {
		this.maxPuestosBicis = maxPuestosBicis;
	}
	public int getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(int codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public double getLongitud() {
		return longitud;
	}
	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}
	public double getLatitud() {
		return latitud;
	}
	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}
	public int getNumBicis() {
		return numBicis;
	}
	public void setNumBicis(int numBicis) {
		this.numBicis = numBicis;
	}
	public boolean isTieneHuecosLibres() {
		return tieneHuecosLibres;
	}
	public void setTieneHuecosLibres(boolean tieneHuecosLibres) {
		this.tieneHuecosLibres = tieneHuecosLibres;
	}
	
	public static EstacionDTO transformToDTO(Estacion estacion){
		EstacionDTO estacionDTO = new EstacionDTO(estacion.getId(), estacion.getNombre(),
				estacion.getFechaAlta().toString(), estacion.getMaxPuestosBicis(), estacion.getCodigoPostal(),
				estacion.getLongitud(), estacion.getLatitud(), estacion.getNumBicis());

		return estacionDTO;
	}
}
