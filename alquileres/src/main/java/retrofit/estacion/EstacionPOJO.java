package retrofit.estacion;


import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EstacionPOJO{
	
	private String id;
	private String nombre;
	private int maxPuestosBicis;
	private int numBicis;
	
	public EstacionPOJO() {
		
	}
	
	public String getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}
	
	
	public int getMaxPuestosBicis() {
		return maxPuestosBicis;
	}
	
	public int getNumBicis() {
		return numBicis;
	}
	
	public void setId(String id) {
		this.id = id;	
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setMaxPuestosBicis(int maxPuestosBicis) {
		this.maxPuestosBicis = maxPuestosBicis;
	}
	
	
	public void setNumBicis(int numBicis) {
		this.numBicis = numBicis;
	}
	
	@Override
	public String toString() {
		return "Estacion [id=" + id + ", nombre=" + nombre + ", maxPuestosBicis="
				+ maxPuestosBicis + "]";
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
		EstacionPOJO other = (EstacionPOJO) obj;
		return Objects.equals(id, other.id);
	}		
}