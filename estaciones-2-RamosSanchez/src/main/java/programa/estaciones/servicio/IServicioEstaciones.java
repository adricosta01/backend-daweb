package programa.estaciones.servicio;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import programa.bicicletas.modelo.Bicicleta;
import programa.estaciones.modelo.Estacion;
import programa.repositorio.EntidadNoEncontrada;
import programa.repositorio.RepositorioException;



public interface IServicioEstaciones {

	public String altaEstacion(String nombre, int numPuestos, int direccionPostal, double latitud, double longitud) throws RepositorioException;
		
	public Estacion getEstacion(String id) throws RepositorioException, EntidadNoEncontrada;
	
	public String altaBici(String modelo, String idEstacion) throws RepositorioException, EntidadNoEncontrada;
	
	public void estacionarBici(String idBici, String idEstacion) throws RepositorioException, EntidadNoEncontrada;
			
	public void bajaBicicleta(String idBici, String motivoBaja) throws RepositorioException, EntidadNoEncontrada;

	public Page<Bicicleta> getBicisEstacion(String idEstacion, Pageable pageable) throws RepositorioException, EntidadNoEncontrada;

	public Page<Bicicleta> getBicisDisponiblesEstacion(String idEstacion, Pageable pageable) throws RepositorioException, EntidadNoEncontrada;

	public Page<Estacion> getEstaciones(Pageable pageable) throws RepositorioException, EntidadNoEncontrada;
}
