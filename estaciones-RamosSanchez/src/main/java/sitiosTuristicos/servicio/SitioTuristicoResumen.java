package sitiosTuristicos.servicio;

public class SitioTuristicoResumen {

	private String id;
	private String nombre;
	private String descripcion;
	private double distanciaCoordenadas;
	private String urlArticuloWikipedia;

	public SitioTuristicoResumen(String nombre, String descripcion, double distanciaCoordenadas, String urlArticuloWikipedia) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.distanciaCoordenadas = distanciaCoordenadas;
		this.urlArticuloWikipedia = urlArticuloWikipedia;
	}
	
	public String getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}
	
	public double getDistanciaCoordenadas() {
		return distanciaCoordenadas;
	}

	public String getUrlArticuloWikipedia() {
		return urlArticuloWikipedia;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setDistanciaCoordenadas(double distanciaCoordenadas) {
		this.distanciaCoordenadas = distanciaCoordenadas;
	}

	public void setUrlArticuloWikipedia(String urlArticuloWikipedia) {
		this.urlArticuloWikipedia = urlArticuloWikipedia;
	}

	@Override
	public String toString() {
		return "EncuestaResumen [id=" + id + ", nombre=" + nombre + ", distanciaCoordenadas=" + distanciaCoordenadas
				+ ", urlWikipedia=" + urlArticuloWikipedia + "]";
	}

}
