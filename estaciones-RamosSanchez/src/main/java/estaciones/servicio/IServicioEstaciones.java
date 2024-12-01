package estaciones.servicio;

import java.util.List;
import java.util.Set;

import bicicletas.dto.BicicletaDTO;
import bicicletas.modelo.Bicicleta;
import estaciones.modelo.Estacion;
import estaciones.repositorio.NoHayEspacioBiciException;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import sitiosTuristicos.modelo.SitioTuristico;


public interface IServicioEstaciones {

	public String altaEstacion(String nombre, int numPuestos, int direccionPostal, double latitud, double longitud) throws RepositorioException;
	
	public Set<SitioTuristico> getSitiosTuristicosProximos(String id) throws RepositorioException, EntidadNoEncontrada, Exception;
 
	public void establecerSitiosTuristicos(String id, Set<SitioTuristico> sitiosTuristicos) throws RepositorioException, EntidadNoEncontrada;
	
	public Estacion getEstacion(String id) throws RepositorioException, EntidadNoEncontrada;
	
	public String altaBici(String modelo, String idEstacion) throws RepositorioException, EntidadNoEncontrada;
	
	public void estacionarBici(String idBici, String idEstacion) throws RepositorioException, EntidadNoEncontrada;
	
	public void estacionarBici(String idBici) throws RepositorioException, EntidadNoEncontrada, NoHayEspacioBiciException;
	
	public void retirarBici(String idBici) throws RepositorioException, EntidadNoEncontrada;
	
	public void bajaBicicleta(String idBici, String motivoBaja) throws RepositorioException, EntidadNoEncontrada;
	
	public List<BicicletaDTO> recuperarBicisCercanas(double longitud, double latitud) throws RepositorioException, EntidadNoEncontrada;
	
	public Set<Estacion> recuperarEstacionesPorSitiosTuristicos();
}
