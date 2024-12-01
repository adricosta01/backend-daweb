package programa.historicos.modelo;

import java.time.LocalDateTime;

import org.bson.codecs.pojo.annotations.BsonProperty;

public class Registro {

	@BsonProperty(value="id_estacion")
	private String idEstacion;
	@BsonProperty(value="fecha_inicio")
	private LocalDateTime fechaInicio;
	@BsonProperty(value="fecha_fin")
	private LocalDateTime fechaFin;
	
	public Registro(String idEstacion, LocalDateTime fechaInicio) {
		this.idEstacion = idEstacion;
		this.fechaInicio = fechaInicio;
	}
	
	public Registro() {
		
	}
	
	public String getIdEstacion() {
		return idEstacion;
	}
	
	public LocalDateTime getFechaFin() {
		return fechaFin;
	}
	
	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}
	
	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public void setFechaFin(LocalDateTime fechaFin) {
		this.fechaFin = fechaFin;
	}
	public void setIdEstacion(String idEstacion) {
		this.idEstacion = idEstacion;
	}
}
