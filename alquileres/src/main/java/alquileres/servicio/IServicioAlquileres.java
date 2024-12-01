package alquileres.servicio;

import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import usuarios.modelo.Usuario;

public interface IServicioAlquileres {

	public void reservar(String idUsuario, String idBicicleta) throws EntidadNoEncontrada, Exception;
	
	public void confirmarReserva(String idUsuario) throws Exception;
	
	public void alquilar(String idUsuario, String idBicicleta) throws EntidadNoEncontrada, Exception;
	
	public Usuario historialUsuario(String idUsuario) throws EntidadNoEncontrada, RepositorioException;
	
	public void dejarBicicleta(String idUsuario, String idEstacion) throws EntidadNoEncontrada, RepositorioException, ServicioAlquileresException;
	
	public void liberarBloqueo(String idUsuario) throws EntidadNoEncontrada, RepositorioException;
}
