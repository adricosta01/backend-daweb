package sitiosTuristicos.modelo;

import java.util.LinkedList;

import repositorio.Identificable;

public class SitioTuristico implements Identificable{

	private String id;
	private String nombre;
	private String urlArticuloWikipedia;
	private String resumenArticulo;
	private String imagen;
	private LinkedList<String> categorias;
	private LinkedList<String> urlsComplementarios;
	
	public SitioTuristico() {
		
	}
	public SitioTuristico(String id, String nombre, 
			String urlArticuloWikipedia, String resumenArticulo, String imagen, LinkedList<String> categorias2, 
			LinkedList<String> enlacesExternos) 
	{
		this.id = id;
		this.nombre = nombre;
		this.urlArticuloWikipedia = urlArticuloWikipedia;
		this.resumenArticulo = resumenArticulo;
		this.imagen = imagen;
		this.categorias = categorias2;
		this.urlsComplementarios = enlacesExternos;
		
	}
	 
	@Override
	public String getId() {
		return id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getUrlArticuloWikipedia() {
		return urlArticuloWikipedia;
	}
	
	public String getResumenArticulo() {
		return resumenArticulo;
	}
	
	public LinkedList<String> getCategorias() {
		return categorias;
	}
	
	public LinkedList<String> getUrlsComplementarios() {
		return urlsComplementarios;
	}
	
	public String getImagen() {
		return imagen;
	}
	@Override
	public void setId(String id) {
		this.id = id;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setUrlArticuloWikipedia(String urlArticuloWikipedia) {
		this.urlArticuloWikipedia = urlArticuloWikipedia;
	}
	
	public void setResumenArticulo(String resumenArticulo) {
		this.resumenArticulo = resumenArticulo;
	}
	
	public void setCategorias(LinkedList<String> categorias) {
		this.categorias = categorias;
	}
	
	public void setUrlsComplementarios(LinkedList<String> urlsComplementarios) {
		this.urlsComplementarios = urlsComplementarios;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
	
}
