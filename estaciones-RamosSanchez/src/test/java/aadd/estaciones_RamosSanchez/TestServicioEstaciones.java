package aadd.estaciones_RamosSanchez;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

import bicicletas.modelo.Bicicleta;
import estaciones.modelo.Estacion;
import estaciones.repositorio.RepositorioEstacionesAdHoc;
import estaciones.repositorio.RepositorioEstacionesAdHocMongoDB;
import estaciones.servicio.IServicioEstaciones;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;
import sitiosTuristicos.modelo.SitioTuristico;
import sitiosTuristicos.servicio.IServicioSitioTuristico;

public class TestServicioEstaciones {

	IServicioEstaciones servicioEstaciones;
	IServicioSitioTuristico servicioSitioTuristico;
	Set<String> nombresSitiosTuristicos;
	@Before
	public void setUp() {
		servicioEstaciones = FactoriaServicios.getServicio(IServicioEstaciones.class);
		servicioSitioTuristico = FactoriaServicios.getServicio(IServicioSitioTuristico.class);
		nombresSitiosTuristicos = new HashSet<String>();
	}

	@Test
	public void testAltaEstacion() {
		try {
			assertTrue("Creada correctamente la estación.",
					servicioEstaciones.altaEstacion("Madrid", 20, 30000, 38, -1).getClass().equals(String.class));
		} catch (RepositorioException e) {
			fail("Error al dar de alta a la estación.");
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAltaEstacionNombreIncorrecto() {
		try {
			assertTrue("Creada correctamente la estación.",
					servicioEstaciones.altaEstacion("", 20, 30000, 38, -1).getClass().equals(String.class));
		} catch (RepositorioException e) {

		}
	}

	@Test
	public void testGetSitiosTuristicosProximos() throws RepositorioException, EntidadNoEncontrada, Exception {
		nombresSitiosTuristicos.add("Beniel");
		nombresSitiosTuristicos.add("Santomera");
		nombresSitiosTuristicos.add("Cartaginense");
		nombresSitiosTuristicos.add("Beniaján");
		nombresSitiosTuristicos.add("Santa Cruz (Murcia)");
		nombresSitiosTuristicos.add("El Raal");
		nombresSitiosTuristicos.add("Cabezo Negro de Zeneta");
		nombresSitiosTuristicos.add("Carthaginense");
		
		boolean flag = true;
		Set<SitioTuristico> sitiosTuristicos = servicioEstaciones
				.getSitiosTuristicosProximos(servicioEstaciones.altaEstacion("Madrid", 20, 30000, 38, -1));
		for(SitioTuristico sitioTuristico : sitiosTuristicos) {
			if(!nombresSitiosTuristicos.contains(sitioTuristico.getNombre())) {
				flag = false;
			}				
		}
		assertTrue(flag);
	}

	@Test
	public void testEstablecerSitiosTuristicos() throws RepositorioException, EntidadNoEncontrada {
		String id = servicioEstaciones.altaEstacion("Madrid", 20, 30000, 38, -1);
		Set<SitioTuristico> st = new HashSet<>();
		st.add(new SitioTuristico("1","SitioTuristico1", null, null, null,new LinkedList<String>(),new LinkedList<String>()));
		st.add(new SitioTuristico("2","SitioTuristico2", null, null, null,new LinkedList<String>(),new LinkedList<String>()));
		servicioEstaciones.establecerSitiosTuristicos(id, st);
		
		assertTrue(st.size() == 2);
	}
}