package sitiosTuristicos.servicio;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;
import sitiosTuristicos.modelo.SitioTuristico;

public class ServicioSitioTuristico implements IServicioSitioTuristico {

	private Repositorio<SitioTuristico, String> repositorioSitiosTuristicos =  FactoriaRepositorios.getRepositorio(SitioTuristico.class);
	
	@Override
	public Set<SitioTuristicoResumen> obtenerSitosDeInteres(double latitud, double longitud) throws Exception {

		HashSet<SitioTuristicoResumen> resultado = new HashSet<>();

		DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
		DocumentBuilder analizador = factoria.newDocumentBuilder();
		Document documento = analizador.parse("http://api.geonames.org/findNearbyWikipedia?lat=" + latitud + "&lng="
				+ longitud + "&username=aadd&lang=es");

		NodeList titulos = documento.getElementsByTagName("title");
		NodeList descripciones = documento.getElementsByTagName("summary");
		NodeList distancias = documento.getElementsByTagName("distance");
		NodeList wikipediaUrls = documento.getElementsByTagName("wikipediaUrl");
		for (int i = 0; i < titulos.getLength(); i++) {
			
			String titulo = titulos.item(i).getTextContent();
			String descripcion = descripciones.item(i).getTextContent();
			double distancia = Double.parseDouble(distancias.item(i).getTextContent());
			String wikipediaUrl = wikipediaUrls.item(i).getTextContent();
			
			String id = "";
			for(char c: titulo.toCharArray()) {
				if ( c == ' ') {
					id += "_";
				}else {
					id+=c;
				}
			}
			SitioTuristicoResumen sitio = new SitioTuristicoResumen(titulo, descripcion, distancia, wikipediaUrl);
			sitio.setId(id);
			resultado.add(sitio);
		}
		return resultado;
	}

	@Override
	public SitioTuristico obtenerInformacionSitioDeInteres(String id) throws RepositorioException, ProcesamientoDBPediaException{
		try {
			return repositorioSitiosTuristicos.getById(id);
		} catch (RepositorioException | EntidadNoEncontrada e) {
			
		}
		
		SitioTuristico sitio = leerDBPedia(id);
		repositorioSitiosTuristicos.add(sitio);
		return sitio;
	}

	private SitioTuristico leerDBPedia(String id) throws ProcesamientoDBPediaException {
		try {
			String urlString = "https://es.dbpedia.org/data/"+id+".json";

			String urlObjeto = "http://es.dbpedia.org/resource/"+id;

			InputStreamReader fuente = new InputStreamReader(new URL(urlString).openStream());

			JsonReader jsonReader = Json.createReader(fuente);
			JsonObject obj = jsonReader.readObject();

			JsonObject objResource = obj.getJsonObject(urlObjeto);

			//Nombre
			JsonObject nombreJSON = (JsonObject) objResource
					.getJsonArray("http://www.w3.org/2000/01/rdf-schema#label")
					.get(0);

			String nombre = nombreJSON.getString("value");


			//Descripcion
			String descripcion;
			try {
				JsonObject descripcionJSON = (JsonObject) objResource
						.getJsonArray("http://dbpedia.org/ontology/abstract")
						.get(0);
	
				descripcion = descripcionJSON.getString("value");
			}catch(Exception e) {
				descripcion = null;
			}

			// Resumen
			LinkedList<String> categorias = new LinkedList<String>();
			try {
				JsonArray listaCategoriasJSON = objResource
						.getJsonArray("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
			
				for(int i = 0; i<listaCategoriasJSON.size(); i++) {
					JsonObject auxObj = (JsonObject) listaCategoriasJSON.get(i);
					categorias.add(auxObj.getString("value"));
				}
			}catch(Exception e) {
				
			}

			LinkedList<String> enlacesExternos = new LinkedList<String>();
			try {
				JsonArray listaEnlacesExternosJSON = objResource
						.getJsonArray("http://dbpedia.org/ontology/wikiPageExternalLink"); // Enlaces Externos
	
				if(listaEnlacesExternosJSON!= null) {
					for(int i = 0; i<listaEnlacesExternosJSON.size(); i++) {
						JsonObject auxObj = (JsonObject) listaEnlacesExternosJSON.get(i);
						enlacesExternos.add(auxObj.getString("value"));
					}
				}
			}catch(Exception e) {
				
			}
			
			String imagen;
			try {
				JsonObject imagenJSON = (JsonObject) objResource
						.getJsonArray("http://es.dbpedia.org/property/imagen")
						.get(0); // Imagen
				imagen = imagenJSON.getString("value");
				
			}catch (Exception e) {
				imagen = null;
			}

			SitioTuristico sitio = new SitioTuristico(id, nombre, urlString, descripcion, imagen, categorias, enlacesExternos);
			return sitio;
			
		} catch (Exception e) {
			throw new ProcesamientoDBPediaException("Excepción al procesar DBPedia: No se encontró el valor "+ id);
		}
			
	}

}
