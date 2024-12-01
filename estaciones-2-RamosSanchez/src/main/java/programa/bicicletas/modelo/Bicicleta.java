package programa.bicicletas.modelo;

import java.time.LocalDate;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import programa.incidencias.modelo.Incidencia;
import programa.repositorio.Identificable;



@Document(collection ="bicicletas")
public class Bicicleta implements Identificable {
	
	@Id
	private String id;
	private String modelo;
	private LocalDate fechaAlta;
	private LocalDate fechaBaja;
	private Incidencia incidencia;
	private boolean disponible;
	private String estacionID;
	private String motivoBaja;
			
	public Bicicleta(String modelo, LocalDate fechaAlta) {
		this.modelo = modelo;
		this.fechaAlta = fechaAlta;
		this.incidencia = null;
		this.disponible = true;
		this.estacionID = null;
		this.fechaBaja = null;
		this.motivoBaja = null;
	}
	
	public Bicicleta() {
		
	}
	
	@Override
	public String getId() {
		return id;
	}
	
	public String getModelo() {
		return modelo;
	}
	
	public LocalDate getFechaAlta() {
		return fechaAlta;
	}

	public LocalDate getFechaBaja() {
		return fechaBaja;
	}
	
	public Incidencia getIncidencia() {
		return incidencia;
	}
	
	public String getEstacionID() {
		return estacionID;
	}
	
	public String motivoBaja() {
		return motivoBaja;
	}
	
	@Override
	public void setId(String id) {
		this.id = id;	
	}
	
	public void setIncidencia(Incidencia incidencia) {
		this.incidencia = incidencia;
	}
	
	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public void setFechaBaja(LocalDate fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
	public void setEstaDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	public void setEstacion(String estacionID) {
		this.estacionID = estacionID;
	}
	
	public void setMotivoBaja(String motivoBaja) {
		this.motivoBaja = motivoBaja;
	}
	public void eliminarEstacion() {
		this.estacionID = null;
	}
	
	public boolean isEstacionada() {
		return estacionID != null;
	}
	public boolean isDisponible() {
		return disponible;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bicicleta other = (Bicicleta) obj;
		return Objects.equals(id, other.id);
	}
}
