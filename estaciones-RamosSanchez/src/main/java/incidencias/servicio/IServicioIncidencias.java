package incidencias.servicio;

import java.util.List;

import estaciones.repositorio.NoHayEspacioBiciException;
import incidencias.dto.IncidenciaDTO;
import incidencias.modelo.EstadoIncidencia;
import incidencias.modelo.Incidencia;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

public interface IServicioIncidencias {

	public void crearIncidencia(String idBici, String descripcion) throws RepositorioException, EntidadNoEncontrada, IncidenciaExistenteException;
	
	public void gestionarIncidencias(String idBici, EstadoIncidencia estadoDeseado, String informacionGestion) throws RepositorioException, EntidadNoEncontrada;
	
	public void gestionarIncidencias(String idBici, String informacionGestion, boolean estaReparada) throws RepositorioException, EntidadNoEncontrada, NoHayEspacioBiciException;
	
	public List<Incidencia> recuperarIncidenciasAbiertas() throws RepositorioException;
	
	public List<IncidenciaDTO> recuperarIncidenciasAbiertasDTO() throws RepositorioException, EntidadNoEncontrada;
}
