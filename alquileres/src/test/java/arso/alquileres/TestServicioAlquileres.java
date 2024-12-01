package arso.alquileres;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import alquileres.modelo.Alquiler;
import alquileres.servicio.IServicioAlquileres;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.RepositorioException;
import reservas.modelo.Reserva;
import servicio.FactoriaServicios;
import usuarios.modelo.Usuario;
import usuarios.repositorio.RepositorioUsuarioAdHoc;

public class TestServicioAlquileres {
//
//	static IServicioAlquileres servicioAlquileres;
//	static RepositorioUsuarioAdHoc repoUsuarios;
//
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//		servicioAlquileres = FactoriaServicios.getServicio(IServicioAlquileres.class);
//		repoUsuarios = FactoriaRepositorios.getRepositorio(Usuario.class);
//	}
//	
//	@AfterClass
//	public static void setUpAfterClass() throws Exception {
//		for(Usuario u : repoUsuarios.getAll()) {
//			repoUsuarios.delete(u);
//		}
//		Usuario u = new Usuario(new ArrayList<Reserva>() ,new ArrayList<Alquiler>());
//	}
//	
//	@Test
//	public void testReservar() throws EntidadNoEncontrada, Exception {
//		Usuario u = new Usuario(new ArrayList<Reserva>() ,new ArrayList<Alquiler>());
//		u.setId("2");
//		repoUsuarios.add(u);
//		
//		servicioAlquileres.reservar(u.getId(), "2");
//		
//		u = repoUsuarios.getById(u.getId());
//		assertEquals(1, u.getReservas().size());
//	}
//	
//	@Test(expected = Exception.class)
//	public void testReservarTeniendoReservaActiva() throws EntidadNoEncontrada, Exception {
//		Reserva r = new Reserva("1");
//		Usuario u = new Usuario(new ArrayList<Reserva>(Collections.singletonList(r)),new ArrayList<Alquiler>());
//		u.setId("3");
//		repoUsuarios.add(u);
//		
//		servicioAlquileres.reservar("2", "2");	
//	}
//
//	@Test(expected = Exception.class)
//	public void testReservarTeniendoAlquilerActivo() throws EntidadNoEncontrada, Exception {
//		Alquiler a = new Alquiler("1");
//		Usuario u = new Usuario(new ArrayList<Reserva>(),new ArrayList<Alquiler>(Collections.singletonList(a)));
//		u.setId("4");
//		repoUsuarios.add(u);
//		
//		servicioAlquileres.reservar("2", "2");	
//	}
//	
//	@Test(expected = Exception.class)
//	public void testReservarEstandoBloqueado() throws EntidadNoEncontrada, Exception {		
//		Usuario u = new Usuario(new ArrayList<Reserva>() ,new ArrayList<Alquiler>());
//		u.setId("5");
//		repoUsuarios.add(u);
//		
//		servicioAlquileres.reservar(u.getId(), "2");
//		servicioAlquileres.reservar(u.getId(), "3");
//		servicioAlquileres.reservar(u.getId(), "4");
//		
//		u = repoUsuarios.getById(u.getId());
//		u.getReservas().get(0).setCaducidad(LocalDateTime.now().minusDays(1));
//		u.getReservas().get(1).setCaducidad(LocalDateTime.now().minusDays(1));
//		u.getReservas().get(2).setCaducidad(LocalDateTime.now().minusDays(1));
//		repoUsuarios.update(u);	
//		
//		servicioAlquileres.reservar(u.getId(), "5");
//	}
//	
//	@Test(expected = Exception.class)
//	public void testReservarSuperaTiempoUso() throws EntidadNoEncontrada, Exception {
//		Alquiler a = new Alquiler("1");
//		a.setFin(LocalDateTime.now().plusHours(1));
//		Usuario u = new Usuario(new ArrayList<Reserva>(),new ArrayList<Alquiler>(Collections.singletonList(a)));
//		u.setId("6");
//		repoUsuarios.add(u);
//		
//		servicioAlquileres.reservar("2", "2");
//		
//	}
//	
//	@Test
//	public void testConfirmarReserva() throws EntidadNoEncontrada, Exception {
//		Usuario u = new Usuario(new ArrayList<Reserva>() ,new ArrayList<Alquiler>());
//		u.setId("7");
//		repoUsuarios.add(u);
//		
//		servicioAlquileres.reservar(u.getId(), "2");
//		servicioAlquileres.confirmarReserva(u.getId());
//		
//		u = repoUsuarios.getById(u.getId());
//		assertEquals(0, u.getReservas().size());
//	}
//	
//	@Test(expected = Exception.class)
//	public void testConfirmarReservaTeniendoReservaActiva() throws EntidadNoEncontrada, Exception {
//		Reserva r = new Reserva("1");
//		Usuario u = new Usuario(new ArrayList<Reserva>(Collections.singletonList(r)),new ArrayList<Alquiler>());
//		u.setId("8");
//		repoUsuarios.add(u);
//		
//		servicioAlquileres.reservar("5", "2");
//		servicioAlquileres.confirmarReserva(u.getId());
//		
//		u = repoUsuarios.getById(u.getId());
//		assertEquals(0, u.getReservas().size());
//	}
//	
//
//	@Test
//	public void testAlquilar() throws EntidadNoEncontrada, Exception {
//		Usuario u = new Usuario(new ArrayList<Reserva>() ,new ArrayList<Alquiler>());
//		u.setId("9");
//		repoUsuarios.add(u);
//		
//		servicioAlquileres.alquilar(u.getId(), "661684e3181612217edab8d8");// 661684e3181612217edab8d8
//		
//		u = repoUsuarios.getById(u.getId());
//		assertEquals(1, u.getAlquileres().size());
//	}
//	
//	@Test(expected = Exception.class)
//	public void testAlquilarTeniendoReservaActiva() throws EntidadNoEncontrada, Exception {
//		Reserva r = new Reserva("1");
//		Usuario u = new Usuario(new ArrayList<Reserva>(Collections.singletonList(r)),new ArrayList<Alquiler>());
//		u.setId("10");
//		repoUsuarios.add(u);
//		
//		servicioAlquileres.alquilar(u.getId(), "2");
//		
//		u = repoUsuarios.getById(u.getId());
//		assertEquals(1, u.getAlquileres().size());
//	}
//	
//	@Test(expected = Exception.class)
//	public void testAlquilarTeniendoAlquilerActivo() throws EntidadNoEncontrada, Exception {
//		Alquiler a = new Alquiler("1");
//		Usuario u = new Usuario(new ArrayList<Reserva>(),new ArrayList<Alquiler>(Collections.singletonList(a)));
//		u.setId("11");
//		repoUsuarios.add(u);
//		
//		servicioAlquileres.alquilar(u.getId(), "2");
//		
//		u = repoUsuarios.getById(u.getId());
//		assertEquals(1, u.getAlquileres().size());
//	}
//	
//	@Test(expected = Exception.class)
//	public void testAlquilarEstandoBloqueado() throws EntidadNoEncontrada, Exception {		
//		Usuario u = new Usuario(new ArrayList<Reserva>() ,new ArrayList<Alquiler>());
//		u.setId("12");
//		repoUsuarios.add(u);
//		
//		servicioAlquileres.reservar(u.getId(), "2");
//		servicioAlquileres.reservar(u.getId(), "3");
//		servicioAlquileres.reservar(u.getId(), "4");
//		
//		u = repoUsuarios.getById(u.getId());
//		u.getReservas().get(0).setCaducidad(LocalDateTime.now().minusDays(1));
//		u.getReservas().get(1).setCaducidad(LocalDateTime.now().minusDays(1));
//		u.getReservas().get(2).setCaducidad(LocalDateTime.now().minusDays(1));
//		repoUsuarios.update(u);	
//		
//		servicioAlquileres.alquilar(u.getId(), "5");
//	}
//	
//	@Test(expected = Exception.class)
//	public void testAlquilarSuperaTiempoUso() throws EntidadNoEncontrada, Exception {
//		Alquiler a = new Alquiler("1");
//		a.setFin(LocalDateTime.now().plusHours(1));
//		Usuario u = new Usuario(new ArrayList<Reserva>(),new ArrayList<Alquiler>(Collections.singletonList(a)));
//		u.setId("13");
//		repoUsuarios.add(u);
//		
//		servicioAlquileres.alquilar("2", "2");
//		
//	}
//
//	@Test
//	public void testHistorialUsuario() throws RepositorioException, EntidadNoEncontrada {
//		Usuario u = new Usuario(new ArrayList<Reserva>() ,new ArrayList<Alquiler>());
//		u.setId("14");
//		repoUsuarios.add(u);
//		
//		Usuario u2 = servicioAlquileres.historialUsuario(u.getId());
//		
//		assertEquals(u.getId(), u2.getId());
//	}
//
//	@Test
//	public void testDejarBicicleta() throws EntidadNoEncontrada, RepositorioException, Exception {
//		Usuario u = new Usuario(new ArrayList<Reserva>() ,new ArrayList<Alquiler>());
//		u.setId("15");
//		repoUsuarios.add(u);
//		
//		servicioAlquileres.alquilar(u.getId(), "661684e3181612217edab8d8");
//		
//		servicioAlquileres.dejarBicicleta(u.getId(), "66168480181612217edab8d7");
//		
//		u = repoUsuarios.getById(u.getId());
//		assertNotNull(u.getAlquileres().get(0).getFin());
//		
//	}
//
//	@Test
//	public void testLiberarBloqueo() throws EntidadNoEncontrada, Exception {
//		Usuario u = new Usuario(new ArrayList<Reserva>() ,new ArrayList<Alquiler>());
//		u.setId("16");
//		repoUsuarios.add(u);
//		
//		servicioAlquileres.reservar(u.getId(), "2");
//		
//		u = repoUsuarios.getById(u.getId());
//		u.getReservas().get(0).setCaducidad(LocalDateTime.now().minusDays(1));
//		repoUsuarios.update(u);
//		
//		servicioAlquileres.liberarBloqueo(u.getId());
//		
//		u = repoUsuarios.getById(u.getId());
//		assertFalse(u.bloqueado());
//	}
}
