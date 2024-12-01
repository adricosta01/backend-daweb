package aadd.estaciones_RamosSanchez;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

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

public class TestRepositorios {
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
	public void testAddEstacion() throws RepositorioException, EntidadNoEncontrada {
		String id = servicioEstaciones.altaEstacion("Prueba", 20, 30000, 38, -1);
		Estacion estacion = repoEstaciones.getById(id);
		assertTrue(estacion.getId().equals(id));
	}
	
	@Test
	public void testAddBici() throws RepositorioException, EntidadNoEncontrada {
		Bicicleta bici = new Bicicleta("Vespa", LocalDate.now());
		String idBici = repoBicicletas.add(bici);
		repoBicicletas.getById(idBici);
		assertTrue(bici.getId().equals(idBici));
	}
	
	@Test
	public void testDeleteEstacion() throws RepositorioException, EntidadNoEncontrada {
		String id = servicioEstaciones.altaEstacion("Prueba", 20, 30000, 38, -1);
		Estacion estacion = repoEstaciones.getById(id);
		try {
			repoEstaciones.delete(estacion);
			repoEstaciones.getById(id);
		} catch (EntidadNoEncontrada e){
			
		}
		
	}
	
	@Test
	public void testDeleteBici() throws RepositorioException, EntidadNoEncontrada {
		Bicicleta bici = new Bicicleta("Vespa", LocalDate.now());
		String idBici = repoBicicletas.add(bici);
		Bicicleta bici2 = repoBicicletas.getById(idBici);
		try {
			repoBicicletas.delete(bici2);
			repoBicicletas.getById(idBici);
		} catch (EntidadNoEncontrada e){
			
		}
	}
	
	@Test
	public void testUpdateEstacion() throws RepositorioException, EntidadNoEncontrada {
		int codigoPostal = 123;
		
		String id = servicioEstaciones.altaEstacion("Prueba", 20, 30000, 38, -1);
		Estacion estacion = servicioEstaciones.getEstacion(id);
		estacion.setCodigoPostal(codigoPostal);
		repoEstaciones.update(estacion);
		Estacion estacionActualizada = repoEstaciones.getById(id);
		assertTrue(estacionActualizada.getId().equals(id) && estacionActualizada.getCodigoPostal() == codigoPostal);
	}
	@Test
	public void testUpdateBici() throws RepositorioException, EntidadNoEncontrada {
		String modelo = "Contador";
		
		Bicicleta bicicleta = new Bicicleta("Vespa", LocalDate.now());
		String idBici = repoBicicletas.add(bicicleta);
		bicicleta.setModelo(modelo);
		repoBicicletas.update(bicicleta);
		Bicicleta bicicletaActualizada = repoBicicletas.getById(idBici);
		assertTrue(bicicletaActualizada.getId().equals(idBici) && bicicletaActualizada.getModelo().equals(modelo));
	}
	
}
