package bicicletas.modelo;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import incidencias.modelo.Incidencia;
import repositorio.Identificable;

@Entity
@Table(name="Bicicleta")
@NamedQueries({
	@NamedQuery(name="Revista.getByID", query="SELECT b FROM Bicicleta b WHERE b.id LIKE :ID "),
})
public class Bicicleta implements Identificable {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private String id;
	@Column(name = "modelo")
	private String modelo;
	@Column(name = "fecha_alta", columnDefinition = "DATE")
	private LocalDate fechaAlta;
	@Column(name = "fecha_baja", columnDefinition = "DATE")
	private LocalDate fechaBaja;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Incidencia incidencia;
	@Column(name = "disponible")
	private boolean disponible;
	@Column(name = "estacion_ID")
	private String estacionID;
	@Column(name = "motivo_baja")
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
