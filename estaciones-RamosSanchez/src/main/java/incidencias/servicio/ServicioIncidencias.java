package incidencias.servicio;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import bicicletas.modelo.Bicicleta;
import bicicletas.repositorio.RepositorioBicicletaAdHoc;
import estaciones.repositorio.NoHayEspacioBiciException;
import estaciones.servicio.IServicioEstaciones;
import incidencias.dto.IncidenciaDTO;
import incidencias.modelo.EstadoIncidencia;
import incidencias.modelo.Incidencia;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

public class ServicioIncidencias implements IServicioIncidencias {

	private RepositorioBicicletaAdHoc repoBicicletas = FactoriaRepositorios.getRepositorio(Bicicleta.class);
	private IServicioEstaciones servicioEstaciones = FactoriaServicios.getServicio(IServicioEstaciones.class);

	@Override
	public void crearIncidencia(String idBici, String descripcion) throws RepositorioException, EntidadNoEncontrada, IncidenciaExistenteException {

		if (idBici == null || idBici.isEmpty())
			throw new IllegalArgumentException("ID: no debe ser nulo ni vacio");

		if (descripcion == null || descripcion.isEmpty())
			throw new IllegalArgumentException("descripcion: no debe ser nulo ni vacio");

		Bicicleta bicicleta = repoBicicletas.getById(idBici);

		if(bicicleta.getIncidencia() == null || bicicleta.getIncidencia().getEstado().equals(EstadoIncidencia.CANCELADA) || 
				bicicleta.getIncidencia().getEstado().equals(EstadoIncidencia.RESUELTA)) {
			
			Incidencia incidencia = new Incidencia(descripcion, idBici);	
			bicicleta.setIncidencia(incidencia);
			repoBicicletas.update(bicicleta);
		}
		else
			throw new IncidenciaExistenteException("Ya existe una incidencia abierta para esta bicicleta.");	
	}

	@Override
	public void gestionarIncidencias(String idBici, EstadoIncidencia estadoDeseado, String informacionGestion)
			throws RepositorioException, EntidadNoEncontrada {
		Bicicleta bicicleta = repoBicicletas.getById(idBici);
		Incidencia incidencia = bicicleta.getIncidencia();

		if (incidencia.getEstado() == EstadoIncidencia.PENDIENTE) {
			if (estadoDeseado == EstadoIncidencia.CANCELADA) {
				incidencia.setInformacionGestion(informacionGestion);
				incidencia.setFechaCierre(LocalDate.now());
				incidencia.setEstado(EstadoIncidencia.CANCELADA);
				bicicleta.setEstaDisponible(true);
				repoBicicletas.update(bicicleta);
			} else if (estadoDeseado == EstadoIncidencia.ASIGNADA) {
				incidencia.setInformacionGestion(informacionGestion);
				incidencia.setEstado(EstadoIncidencia.ASIGNADA);
				repoBicicletas.update(bicicleta);
				servicioEstaciones.retirarBici(idBici);
			} else
				throw new IllegalArgumentException("Cambio de estado no permitido");
		} else {
			throw new IllegalArgumentException(
					"No se puede realizar la acción con el estado inicial de" + incidencia.getEstado().toString());
		}
	}

	@Override
	public void gestionarIncidencias(String idBici, String informacionGestion, boolean estaReparada)
			throws RepositorioException, EntidadNoEncontrada, NoHayEspacioBiciException {
		Bicicleta bicicleta = repoBicicletas.getById(idBici);
		Incidencia incidencia = bicicleta.getIncidencia();
		if (incidencia.getEstado() == EstadoIncidencia.ASIGNADA) {
			incidencia.setInformacionGestion(informacionGestion);
			incidencia.setFechaCierre(LocalDate.now());
			incidencia.setEstado(EstadoIncidencia.RESUELTA);
			if (estaReparada) {
				bicicleta.setEstaDisponible(true);
				repoBicicletas.update(bicicleta);
				servicioEstaciones.estacionarBici(idBici);
			} else {
				repoBicicletas.update(bicicleta);
				servicioEstaciones.bajaBicicleta(idBici, "bicicleta dañada");
			}
		} else {
			throw new IllegalArgumentException(
					"No se puede realizar la acción con el estado inicial de" + incidencia.getEstado().toString());
		}
	}

	@Override
	public List<Incidencia> recuperarIncidenciasAbiertas() throws RepositorioException {

		return repoBicicletas.getBicicletasConIncidenciaAbierta();
	}
	
	@Override
	public List<IncidenciaDTO> recuperarIncidenciasAbiertasDTO() throws RepositorioException, EntidadNoEncontrada{
		List<Incidencia> incidencias = recuperarIncidenciasAbiertas();
		List<IncidenciaDTO> idto = new LinkedList<IncidenciaDTO>();
		for(Incidencia i: incidencias) {
			idto.add(incidenciaToDTO(i));
		}
		return idto;
	}
	
	private IncidenciaDTO incidenciaToDTO(Incidencia i){
		IncidenciaDTO incidencia = new IncidenciaDTO(i.getDescripcion(), i.getIdBici());
		incidencia.setDescripcion(i.getDescripcion());
		incidencia.setEstado(i.getEstado());
		incidencia.setFechaCierre(i.getFechaCierre());
		incidencia.setFechaCreacion(i.getFechaCreacion());
		incidencia.setId(i.getId());
		incidencia.setInformacionGestion(i.getInformacionGestion());
		return incidencia;
	}
}
