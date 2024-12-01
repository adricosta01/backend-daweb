package estaciones.modelo;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

import bicicletas.modelo.Bicicleta;
import repositorio.Identificable;
import sitiosTuristicos.modelo.SitioTuristico;



public class Estacion implements Identificable{
	
	@BsonId
	@BsonRepresentation(BsonType.OBJECT_ID)
	private String id;
	
	@BsonProperty(value="nombre")
	private String nombre;
	
	@BsonProperty(value="fecha_alta")
	private LocalDate fechaAlta;
	
	@BsonProperty(value="max_puestos_bicis")
	private int maxPuestosBicis;
	
	@BsonProperty(value="codigo_postal")
	private int codigoPostal;
	
	@BsonProperty(value="longitud")
	private double longitud;
	@BsonProperty(value="latitud")
	
	private double latitud;
	@BsonProperty(value="bicis")
	private Set<String> bicis;
	
	@BsonProperty(value="coordenadas")
	private List<Double> coordenadas;
	
	@BsonProperty(value="num_bicis")
	private int numBicis;
	
	@BsonProperty(value="sitios_turisticos_cercanos")
	private Set<SitioTuristico> sitiosTuristicosCercanos;
	
	public Estacion(String nombre, int maxPuestosBicis, int codigoPostal, double latitud, double longitud) {
		this.nombre = nombre;
		this.maxPuestosBicis = maxPuestosBicis;
		this.codigoPostal = codigoPostal;
		this.latitud = latitud;
		this.longitud = longitud;
		this.sitiosTuristicosCercanos = new HashSet<SitioTuristico>();
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
	
	public Set<SitioTuristico> getSitiosTuristicosCercanos() {
		return sitiosTuristicosCercanos;
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
	
	public void setSitiosTuristicosCercanos(Set<SitioTuristico> sitiosTuristicosCercanos){
		this.sitiosTuristicosCercanos = sitiosTuristicosCercanos;
	}
	
	public void setBicis(Set<String> bicis) {
		this.bicis = bicis;
		this.numBicis = bicis.size();
	}
	
	public void setNumBicis(int numBicis) {
		this.numBicis = numBicis;
	}
	
	public void addSitiosTuristicos(Set<SitioTuristico> sitiosTuristicos) {
		
		for (SitioTuristico sitioTuristico : sitiosTuristicos) {
			this.sitiosTuristicosCercanos.add(sitioTuristico);
		}
	}
	
	public void addSitioTuristico(SitioTuristico sitioTuristico) {	
		this.sitiosTuristicosCercanos.add(sitioTuristico);
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
				+ ", bicis=" + bicis + ", sitiosTuristicosCercanos=" + sitiosTuristicosCercanos + "]";
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
