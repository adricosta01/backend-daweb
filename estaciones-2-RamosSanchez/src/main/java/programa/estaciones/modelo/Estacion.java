package programa.estaciones.modelo;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import programa.bicicletas.modelo.Bicicleta;
import programa.repositorio.Identificable;


@Document(collection ="estaciones")
public class Estacion implements Identificable{
	
	@Id
	private String id;
	private String nombre;
	private LocalDate fechaAlta;
	private int maxPuestosBicis;
	private int codigoPostal;
	private double longitud;	
	private double latitud;
	private Set<String> bicis;
	private List<Double> coordenadas;
	private int numBicis;
	
	public Estacion(String nombre, int maxPuestosBicis, int codigoPostal, double latitud, double longitud) {
		this.nombre = nombre;
		this.maxPuestosBicis = maxPuestosBicis;
		this.codigoPostal = codigoPostal;
		this.latitud = latitud;
		this.longitud = longitud;
		this.bicis = new HashSet<String>();
		this.numBicis = 0;
		this.coordenadas = new LinkedList<Double>();
		this.coordenadas.add(longitud);
		this.coordenadas.add(latitud);		
	}
	public Estacion() {
		
	}
	@Override
	public String getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}
	
	public LocalDate getFechaAlta() {
		return fechaAlta;
	}
	
	public int getMaxPuestosBicis() {
		return maxPuestosBicis;
	}
	
	public int getCodigoPostal() {
		return codigoPostal;
	}
	
	public double getLongitud() {
		return longitud;
	}
	
	public double getLatitud() {
		return latitud;
	}
	
	public Set<String> getBicis(){
		return bicis;
	}
	
	public int getNumBicis() {
		return numBicis;
	}
	
	public List<Double> getCoordenadas() {
		return coordenadas;
	}
	
	@Override
	public void setId(String id) {
		this.id = id;	
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	
	public void setMaxPuestosBicis(int maxPuestosBicis) {
		this.maxPuestosBicis = maxPuestosBicis;
	}
	
	public void setCodigoPostal(int codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	
	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}
	
	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}
	
	public void setCoordenadas(List<Double> coordenadas) {
		this.coordenadas = coordenadas;
	}
	
	public void setBicis(Set<String> bicis) {
		this.bicis = bicis;
		this.numBicis = bicis.size();
	}
	
	public void setNumBicis(int numBicis) {
		this.numBicis = numBicis;
	}
	
	public void addBici(Bicicleta bici) {
		bicis.add(bici.getId());
		this.numBicis++;
	}
	
	public void remBici(Bicicleta bici) {
		bicis.remove(bici.getId());
		this.numBicis--;
	}
	@Override
	public String toString() {
		return "Estacion [id=" + id + ", nombre=" + nombre + ", fechaAlta=" + fechaAlta + ", maxPuestosBicis="
				+ maxPuestosBicis + ", codigoPostal=" + codigoPostal + ", longitud=" + longitud + ", latitud=" + latitud
				+ ", bicis=" + bicis + "]";
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
		Estacion other = (Estacion) obj;
		return Objects.equals(id, other.id);
	}		
}