package aadd.estaciones_RamosSanchez;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import bicicletas.modelo.Bicicleta;
import bicicletas.repositorio.RepositorioBicicletaAdHoc;
import estaciones.repositorio.NoHayEspacioBiciException;
import estaciones.servicio.IServicioEstaciones;
import incidencias.modelo.EstadoIncidencia;
import incidencias.modelo.Incidencia;
import incidencias.servicio.IServicioIncidencias;
import incidencias.servicio.IncidenciaExistenteException;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

public class TestServicioIncidencias {

	IServicioEstaciones servicioEstaciones;
	IServicioIncidencias servicioIncidencias;	
	RepositorioBicicletaAdHoc repoBicicletas;
	
	@Before
	public void setUp(){
		servicioEstaciones = FactoriaServicios.getServicio(IServicioEstaciones.class);
		servicioIncidencias = FactoriaServicios.getServicio(IServicioIncidencias.class);
		repoBicicletas = FactoriaRepositorios.getRepositorio(Bicicleta.class);		
	}
	
	@Test
	public void testCrearIncidencia() throws RepositorioException, EntidadNoEncontrada, IncidenciaExistenteException {
		String id = servicioEstaciones.altaEstacion("E1", 20, 1, 1, 1);
		String idBici = servicioEstaciones.altaBici("Modelo1", id);
		
		servicioIncidencias.crearIncidencia(idBici, "Incidencia1");
				
		assertEquals("Incidencia1", repoBicicletas.getById(idBici).getIncidencia().getDescripcion());
	}
	
	@Test
	public void testGestionarIncidenciaPendienteACancelada() throws RepositorioException, EntidadNoEncontrada, IncidenciaExistenteException{
		String id = servicioEstaciones.altaEstacion("E2", 20, 1, 1, 1);
		String idBici = servicioEstaciones.altaBici("Modelo1", id);
		
		servicioIncidencias.crearIncidencia(idBici, "Incidencia1");
		
		servicioIncidencias.gestionarIncidencias(idBici, EstadoIncidencia.CANCELADA, "Esta cancelada");
		
		assertEquals("Esta cancelada", repoBicicletas.getById(idBici).getIncidencia().getInformacionGestion());
	}
	
	@Test
	public void testGestionarIncidenciaPendienteAAsignada() throws RepositorioException, EntidadNoEncontrada, IncidenciaExistenteException{
		String id = servicioEstaciones.altaEstacion("E3", 20, 1, 1, 1);
		String idBici = servicioEstaciones.altaBici("Modelo1", id);
		
		servicioIncidencias.crearIncidencia(idBici, "Incidencia1");
		
		servicioIncidencias.gestionarIncidencias(idBici, EstadoIncidencia.ASIGNADA, "Esta asignada");
		
		assertEquals("Esta asignada", repoBicicletas.getById(idBici).getIncidencia().getInformacionGestion());
	}
	
	@Test
	public void testGestionarIncidenciaAsignadaAResuelta() throws RepositorioException, EntidadNoEncontrada, NoHayEspacioBiciException, IncidenciaExistenteException{
		String id = servicioEstaciones.altaEstacion("E4", 20, 1, 1, 1);
		String idBici = servicioEstaciones.altaBici("Modelo1", id);
		
		servicioIncidencias.crearIncidencia(idBici, "Incidencia1");
		
		servicioIncidencias.gestionarIncidencias(idBici, EstadoIncidencia.ASIGNADA, "Esta asignada");
		servicioIncidencias.gestionarIncidencias(idBici, "Esta resuelta", true);
		
		assertEquals("Esta resuelta", repoBicicletas.getById(idBici).getIncidencia().getInformacionGestion());
	}
	
	@Test
	public void testGestionarIncidenciaAsignadaAResueltaNoReparada() throws RepositorioException, EntidadNoEncontrada, NoHayEspacioBiciException, IncidenciaExistenteException{
		String id = servicioEstaciones.altaEstacion("E5", 20, 1, 1, 1);
		String idBici = servicioEstaciones.altaBici("Modelo1", id);
		
		servicioIncidencias.crearIncidencia(idBici, "Incidencia1");
		
		servicioIncidencias.gestionarIncidencias(idBici, EstadoIncidencia.ASIGNADA, "Esta asignada");
		servicioIncidencias.gestionarIncidencias(idBici, "No esta resuelta", false);
		
		assertEquals("No esta resuelta", repoBicicletas.getById(idBici).getIncidencia().getInformacionGestion());
	}
	
	@Test
	public void testRecuperarIncidenciasAbiertas() throws RepositorioException, EntidadNoEncontrada, NoHayEspacioBiciException, IncidenciaExistenteException{
		
		for(Bicicleta b : repoBicicletas.getAll()) {
			repoBicicletas.delete(b);
		}
		
		String id = servicioEstaciones.altaEstacion("E6", 20, 1, 1, 1);
		String idBici = servicioEstaciones.altaBici("Modelo1", id);
		String idBici2 = servicioEstaciones.altaBici("Modelo2", id);
		
		servicioIncidencias.crearIncidencia(idBici, "Incidencia1");
		servicioIncidencias.crearIncidencia(idBici2, "Incidencia2");

		servicioIncidencias.gestionarIncidencias(idBici, EstadoIncidencia.ASIGNADA, "Esta asignada");
		servicioIncidencias.gestionarIncidencias(idBici, "No esta resuelta", false);
		
		List<Incidencia> incidencias = servicioIncidencias.recuperarIncidenciasAbiertas();
		
		assertEquals(1, incidencias.size());
	}

}
