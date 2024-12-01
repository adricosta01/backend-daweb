package sitiosTuristicos.servicio;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Optional;
import java.util.Set;

import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import sitiosTuristicos.modelo.SitioTuristico;

public interface IServicioSitioTuristico {

	public Set<SitioTuristicoResumen> obtenerSitosDeInteres(double longitud, double latitud) throws Exception;
	
	public SitioTuristico obtenerInformacionSitioDeInteres(String id) throws RepositorioException, EntidadNoEncontrada,  ProcesamientoDBPediaException;
	
}
