package aadd.estaciones_RamosSanchez;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import estaciones.servicio.IServicioEstaciones;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;
import sitiosTuristicos.modelo.SitioTuristico;
import sitiosTuristicos.servicio.IServicioSitioTuristico;
import sitiosTuristicos.servicio.ProcesamientoDBPediaException;
import sitiosTuristicos.servicio.SitioTuristicoResumen;

public class TestServicioSitiosTuristicos {

	IServicioEstaciones servicioEstaciones;
	IServicioSitioTuristico servicioSitioTuristico;
	Set<String> nombresSitiosTuristicos;

	@Before
	public void setUp() {
		servicioEstaciones = FactoriaServicios.getServicio(IServicioEstaciones.class);
		servicioSitioTuristico = FactoriaServicios.getServicio(IServicioSitioTuristico.class);
		nombresSitiosTuristicos = new HashSet<>();
	}
	
	@Test
	public void testObtenerSitosDeInteres() throws Exception {
		nombresSitiosTuristicos.add("Beniel");
		nombresSitiosTuristicos.add("Santomera");
		nombresSitiosTuristicos.add("Cartaginense");
		nombresSitiosTuristicos.add("Beniaján");
		nombresSitiosTuristicos.add("Santa Cruz (Murcia)");
		nombresSitiosTuristicos.add("El Raal");
		nombresSitiosTuristicos.add("Cabezo Negro de Zeneta");
		nombresSitiosTuristicos.add("Carthaginense");
		
		Set<SitioTuristicoResumen> st = new HashSet<>();
		st = servicioSitioTuristico.obtenerSitosDeInteres(38, -1);
		for(SitioTuristicoResumen str : st) {
			assertTrue(nombresSitiosTuristicos.contains(str.getNombre()));
		}
	}

	@Test
	public void testObtenerInformacionSitioDeInteres() throws MalformedURLException, RepositorioException, IOException, EntidadNoEncontrada, ProcesamientoDBPediaException {
		SitioTuristico st = servicioSitioTuristico.obtenerInformacionSitioDeInteres("Catedral_de_Murcia");
		assertTrue(st.getNombre().equals("Catedral de Murcia"));
	}

}
