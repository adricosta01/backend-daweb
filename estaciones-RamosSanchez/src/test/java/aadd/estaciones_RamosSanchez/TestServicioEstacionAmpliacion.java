package aadd.estaciones_RamosSanchez;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import bicicletas.dto.BicicletaDTO;
import bicicletas.modelo.Bicicleta;
import estaciones.modelo.Estacion;
import estaciones.repositorio.RepositorioEstacionesAdHoc;
import estaciones.servicio.IServicioEstaciones;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;
import sitiosTuristicos.modelo.SitioTuristico;
import sitiosTuristicos.servicio.IServicioSitioTuristico;

public class TestServicioEstacionAmpliacion {

	IServicioEstaciones servicioEstaciones;
	IServicioSitioTuristico servicioSitioTuristico;
	Set<String> nombresSitiosTuristicos;
	RepositorioEstacionesAdHoc repoEstaciones;
	Repositorio<Bicicleta, String> repoBicicletas;
	@Before
	public void setUp() {
		servicioEstaciones = FactoriaServicios.getServicio(IServicioEstaciones.class);
		servicioSitioTuristico = FactoriaServicios.getServicio(IServicioSitioTuristico.class);
		nombresSitiosTuristicos = new HashSet<String>();
		repoEstaciones = FactoriaRepositorios.getRepositorio(Estacion.class);
		repoBicicletas = FactoriaRepositorios.getRepositorio(Bicicleta.class);
	}
	
	@Test
	public void testAltaBici() throws RepositorioException, EntidadNoEncontrada {
		String id = servicioEstaciones.altaEstacion("Madrid", 20, 30000, 38, -1);
		String idBicicleta = servicioEstaciones.altaBici("Modelo nuevo", id);
		Bicicleta bici = repoBicicletas.getById(idBicicleta);
		assertTrue(idBicicleta.equals(bici.getId()));
	}

	@Test
	public void testEstacionarBici() throws RepositorioException, EntidadNoEncontrada {
		String id = servicioEstaciones.altaEstacion("Madrid", 20, 30000, 38, -1);
		String idBicicleta = servicioEstaciones.altaBici("Modelo nuevo", id);
		servicioEstaciones.retirarBici(idBicicleta);
		servicioEstaciones.estacionarBici(idBicicleta, id);
		Estacion estacion = repoEstaciones.getById(id);
		assertTrue(estacion.getBicis().contains(idBicicleta));
	}
	@Test
	public void testRetirarBici() throws RepositorioException, EntidadNoEncontrada {
		String id = servicioEstaciones.altaEstacion("Madrid", 20, 30000, 38, -1);
		String idBicicleta = servicioEstaciones.altaBici("Modelo nuevo", id);
		servicioEstaciones.retirarBici(idBicicleta);
		Estacion estacion = repoEstaciones.getById(id);
		Bicicleta bici = repoBicicletas.getById(idBicicleta);
		assertTrue(!bici.isEstacionada() && !estacion.getBicis().contains(idBicicleta));
	}
	@Test
	public void testBajaBici() throws RepositorioException, EntidadNoEncontrada {
		String id = servicioEstaciones.altaEstacion("Madrid", 20, 30000, 38, -1);
		String idBicicleta = servicioEstaciones.altaBici("Modelo nuevo", id);
		servicioEstaciones.bajaBicicleta(idBicicleta, "manillar roto");
		Estacion estacion = repoEstaciones.getById(id);
		Bicicleta bici = repoBicicletas.getById(idBicicleta);
		assertTrue(!bici.isEstacionada() && !estacion.getBicis().contains(idBicicleta)
				&& !bici.isDisponible());	
	}
	
	
	@Test
	public void testRecuperarBicisCercanas() throws RepositorioException, EntidadNoEncontrada {
		
		for(Estacion e : repoEstaciones.getAll()) {
			repoEstaciones.delete(e);
		}
		
		String id = servicioEstaciones.altaEstacion("E1", 20, 1, 1, 1);
		String id2 = servicioEstaciones.altaEstacion("E2", 20, 10, 1, 2);
		String id3 = servicioEstaciones.altaEstacion("E3", 20, 10, 2, 2);
		String id4 = servicioEstaciones.altaEstacion("E4", 20, 10, 20, 10);
		
		String idb = servicioEstaciones.altaBici("Bici", id);
		String idb2 = servicioEstaciones.altaBici("Bici2", id2);
		String idb3 = servicioEstaciones.altaBici("Bici3", id3);
		String idb4 = servicioEstaciones.altaBici("Bici4", id4);
		
		List<BicicletaDTO> bicis = servicioEstaciones.recuperarBicisCercanas(1, 1);
		
		assertEquals(3, bicis.size());
	}
	
	@Test
	public void testRecuperarEstacionesPorSitiosTuristicos() throws RepositorioException, EntidadNoEncontrada {
		
		for(Estacion e : repoEstaciones.getAll()) {
			repoEstaciones.delete(e);
		}
		
		String id = servicioEstaciones.altaEstacion("E1", 20, 1, 1, 1);
		String id2 = servicioEstaciones.altaEstacion("E2", 20, 10, 1, 2);
		String id3 = servicioEstaciones.altaEstacion("E3", 20, 10, 2, 2);
		String id4 = servicioEstaciones.altaEstacion("E4", 20, 10, 20, 10);
		
		SitioTuristico st = new SitioTuristico();

		Estacion e = servicioEstaciones.getEstacion(id);
		e.addSitioTuristico(st);
		e.addSitioTuristico(st);
		e.addSitioTuristico(st);

		repoEstaciones.update(e);

		Estacion e3 = servicioEstaciones.getEstacion(id3);
		e.addSitioTuristico(st);
		e.addSitioTuristico(st);

		repoEstaciones.update(e3);
		
		Estacion e2 = servicioEstaciones.getEstacion(id2);
		e.addSitioTuristico(st);

		repoEstaciones.update(e2);
		
		Estacion e4 = servicioEstaciones.getEstacion(id4);
		
		Set<Estacion> estaciones = servicioEstaciones.recuperarEstacionesPorSitiosTuristicos();
		
		Set<Estacion> estacionesExpected = new HashSet<>();
		estacionesExpected.add(e);
		estacionesExpected.add(e3);
		estacionesExpected.add(e2);
		estacionesExpected.add(e4);
		
		assertEquals(estacionesExpected, estaciones);
	}
}
