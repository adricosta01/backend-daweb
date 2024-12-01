package estaciones.servicio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import bicicletas.dto.BicicletaDTO;
import bicicletas.modelo.Bicicleta;
import estaciones.modelo.Estacion;
import estaciones.repositorio.NoHayEspacioBiciException;
import estaciones.repositorio.RepositorioEstacionesAdHoc;
import historicos.modelo.Historico;
import historicos.modelo.Registro;
import historicos.repositorio.RepositorioHistoricoAdHoc;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;
import sitiosTuristicos.modelo.SitioTuristico;
import sitiosTuristicos.servicio.IServicioSitioTuristico;
import sitiosTuristicos.servicio.ProcesamientoDBPediaException;
import sitiosTuristicos.servicio.SitioTuristicoResumen;

public class ServicioEstaciones implements IServicioEstaciones {

	private RepositorioEstacionesAdHoc repoEstaciones = FactoriaRepositorios.getRepositorio(Estacion.class);
	private Repositorio<Bicicleta, String> repoBicicletas = FactoriaRepositorios.getRepositorio(Bicicleta.class);
    private RepositorioHistoricoAdHoc repoHistorico = FactoriaRepositorios.getRepositorio(Historico.class);
	private IServicioSitioTuristico servicioSitioTuristico = FactoriaServicios.getServicio(IServicioSitioTuristico.class);
    
	@Override
	public String altaEstacion(String nombre, int numPuestos, int direccionPostal, double latitud, double longitud)
			throws RepositorioException {

		// Control de integridad de los datos

		if (nombre == null || nombre.isEmpty())
			throw new IllegalArgumentException("nombre: no debe ser nulo ni vacio");

		if (numPuestos <= 0)
			throw new IllegalArgumentException("numPuestos: no puede ser igual o menor que 0");

		if (direccionPostal <= 0)
			throw new IllegalArgumentException("direccionPostal: no puede ser igual o menor que 0");

		
		Estacion estacion = new Estacion(nombre, numPuestos, direccionPostal, latitud, longitud);

		estacion.setFechaAlta(LocalDate.now());

		String id = repoEstaciones.add(estacion);
	
		return id;
	}

	@Override
	public Set<SitioTuristico> getSitiosTuristicosProximos(String id) throws RepositorioException, EntidadNoEncontrada, Exception {

		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");

		Estacion estacion = repoEstaciones.getById(id);
		
		Set<SitioTuristicoResumen> sitiosTuristicosResumen = servicioSitioTuristico.obtenerSitosDeInteres(estacion.getLatitud(), estacion.getLongitud());
		
		
		Set<SitioTuristico> sitiosTuristicos = new HashSet<SitioTuristico>();
		
		for(SitioTuristicoResumen sitioTuristico : sitiosTuristicosResumen) {
			try {
				SitioTuristico sitioAux = servicioSitioTuristico.obtenerInformacionSitioDeInteres(sitioTuristico.getId());
				sitiosTuristicos.add(sitioAux);
			} catch(ProcesamientoDBPediaException e) {
				System.out.println(e.getMessage());
			}
		}

		
		return sitiosTuristicos;
	}

	@Override
	public void establecerSitiosTuristicos(String id, Set<SitioTuristico> sitiosTuristicos)
			throws RepositorioException, EntidadNoEncontrada {
		
		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");

		Estacion estacion = repoEstaciones.getById(id);

		estacion.addSitiosTuristicos(sitiosTuristicos);
		repoEstaciones.update(estacion);
	}

	@Override
	public Estacion getEstacion(String id) throws RepositorioException, EntidadNoEncontrada{
		
		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");

		return repoEstaciones.getById(id);
	}

	@Override
	public String altaBici(String modelo, String idEstacion) throws RepositorioException, EntidadNoEncontrada {
		
		if (modelo == null || modelo.isEmpty())
			throw new IllegalArgumentException("Modelo: no debe ser nulo ni vacio");

		if (idEstacion == null || idEstacion.isEmpty())
			throw new IllegalArgumentException("idEstacion: no debe ser nulo ni vacio");
		
		Bicicleta bicicleta = new Bicicleta(modelo, LocalDate.now());
		repoBicicletas.add(bicicleta);
		Estacion estacion = repoEstaciones.getById(idEstacion);
		estacion.addBici(bicicleta);
		repoEstaciones.update(estacion);
		bicicleta.setEstacion(idEstacion);
		repoBicicletas.update(bicicleta);
		
		Historico historico = new Historico(bicicleta.getId());
		Registro registro = new Registro(idEstacion, LocalDateTime.now());
		historico.addRegistro(registro);
		repoHistorico.add(historico);
		return bicicleta.getId();
	}

	@Override
	public void estacionarBici(String idBici, String idEstacion) throws RepositorioException, EntidadNoEncontrada {
		Bicicleta bicicleta = repoBicicletas.getById(idBici);
		Estacion estacion = repoEstaciones.getById(idEstacion);
		estacion.addBici(bicicleta);
		repoEstaciones.update(estacion);
		bicicleta.setEstacion(idEstacion);
		repoBicicletas.update(bicicleta);
		
		//Modificamos el histórico
		Historico historico = repoHistorico.getByBicleta(idBici);
		Registro registro = new Registro(idEstacion, LocalDateTime.now());
		historico.addRegistro(registro);
		repoHistorico.update(historico);
	}

	@Override
	public void estacionarBici(String idBici) throws RepositorioException, EntidadNoEncontrada, NoHayEspacioBiciException {
		Bicicleta bici = repoBicicletas.getById(idBici);
		Estacion estacion = repoEstaciones.getEstacionPuestosLibres();
		estacion.addBici(bici);
		repoEstaciones.update(estacion);
		bici.setEstacion(idBici);
		repoBicicletas.update(bici);
		
		Historico historico = repoHistorico.getByBicleta(idBici);
		Registro registro = new Registro(estacion.getId(), LocalDateTime.now());
		historico.addRegistro(registro);
		repoHistorico.update(historico);
	}

	@Override
	public void retirarBici(String idBici) throws RepositorioException, EntidadNoEncontrada {
		Bicicleta bicicleta = repoBicicletas.getById(idBici);
		Estacion estacion = repoEstaciones.getById(bicicleta.getEstacionID());
		
		
		//Añadimos fecha fin
		Historico historico = repoHistorico.getByBicleta(idBici);
		Registro registro = historico.getRegistroActual(bicicleta.getEstacionID());
		registro.setFechaFin(LocalDateTime.now());
		repoHistorico.update(historico);
		
		estacion.remBici(bicicleta);
		bicicleta.eliminarEstacion();
		repoEstaciones.update(estacion);
		repoBicicletas.update(bicicleta);
	}
	
	@Override
	public void bajaBicicleta(String idBici, String motivoBaja) throws RepositorioException, EntidadNoEncontrada {
		Bicicleta bicicleta = repoBicicletas.getById(idBici);
		String estacionID = bicicleta.getEstacionID();
		if (estacionID != null && !estacionID.isEmpty()) {
			Estacion estacion = repoEstaciones.getById(bicicleta.getEstacionID());
						
			//Añadimos fecha fin
			Historico historico = repoHistorico.getByBicleta(idBici);
			Registro registro = historico.getRegistroActual(bicicleta.getEstacionID());
			registro.setFechaFin(LocalDateTime.now());
			repoHistorico.update(historico);
			
			estacion.remBici(bicicleta);
			bicicleta.eliminarEstacion();
			repoEstaciones.update(estacion);
		}
				
		bicicleta.setFechaBaja(LocalDate.now());
		bicicleta.setMotivoBaja(motivoBaja);
		bicicleta.setEstaDisponible(false);
		repoBicicletas.update(bicicleta);		
	}
	
	@Override
	public List<BicicletaDTO> recuperarBicisCercanas(double longitud, double latitud) throws RepositorioException, EntidadNoEncontrada{
		List<Estacion> estaciones = repoEstaciones.getBicisEnEstacionesCercanas(longitud, latitud);
		
		Bicicleta bici;
		List<BicicletaDTO> bicicletas = new LinkedList<>();
		
		for(Estacion estacion : estaciones) {
			for(String idBici : estacion.getBicis()) {
				bici = repoBicicletas.getById(idBici);
				if(bici.isDisponible() && bici.isEstacionada()) {
					bicicletas.add(transformToDTO(bici));
				}
			}
		}
		
		return bicicletas;
	}
	
	public Set<Estacion> recuperarEstacionesPorSitiosTuristicos(){
		return repoEstaciones.getEstacionesPorSitiosTuristicos();
	}
	
	private BicicletaDTO transformToDTO(Bicicleta bicicleta) throws RepositorioException, EntidadNoEncontrada {
		BicicletaDTO bdto = new BicicletaDTO(bicicleta.getId(), bicicleta.getModelo());
		bdto.setEstacion(getEstacion(bicicleta.getEstacionID()));
		
		return bdto;
	}

}
