package alquileres.servicio;

/*
 * Excepción notificada si el servicio alquileres no funciona
 */

@SuppressWarnings("serial")
public class ServicioAlquileresException extends Exception {

	public ServicioAlquileresException(String msg) {
		super(msg);
	}
}
