package programa.incidencias.modelo;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import programa.repositorio.Identificable;

@Entity
@Table(name="Incidencia")
public class Incidencia implements Identificable{
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private String id;
	@Column(name = "fechaCreacion", columnDefinition = "DATE")
	private LocalDate fechaCreacion;
	@Column(name = "descripcion")
	private String descripcion;
	@Column(name = "idBici")
	private String idBici;
	@Enumerated ( EnumType.STRING )
	private EstadoIncidencia estado;
	@Column(name = "fechaCierre", columnDefinition = "DATE")
	private LocalDate fechaCierre;
	
	private String informacionGestion;
	
	public Incidencia(String descripcion, String idBici) {
		this.fechaCreacion = LocalDate.now();
		this.estado = EstadoIncidencia.PENDIENTE;
		this.descripcion = descripcion;
		this.idBici = idBici;
	}
	
	public Incidencia() {
		
	}
	
	@Override
	public String getId() {
		return id;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public EstadoIncidencia getEstado() {
		return estado;
	}
	
	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}
	
	public LocalDate getFechaCierre() {
		return fechaCierre;
	}
	
	public String getInformacionGestion() {
		return informacionGestion;
	}
	
	public String getIdBici() {
		return idBici;
	}
	@Override
	public void setId(String id) {
		this.id = id;	
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public void setEstado(EstadoIncidencia estado) {
		this.estado = estado;
	}
	
	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	public void setFechaCierre(LocalDate fechaCierre) {
		this.fechaCierre = fechaCierre;
	}
	
	public void setInformacionGestion(String informacionGestion) {
		this.informacionGestion = informacionGestion;
	}
	public void setIdBici(String idBici) {
		this.idBici = idBici;
	}
	
}
