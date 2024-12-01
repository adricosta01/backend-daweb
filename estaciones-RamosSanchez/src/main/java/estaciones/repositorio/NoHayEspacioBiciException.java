package estaciones.repositorio;

/*
 * Excepción notificada si no existe una entidad con el identificador
 * proporcionado en el repositorio.
 */

@SuppressWarnings("serial")
public class NoHayEspacioBiciException extends Exception {

		
	public NoHayEspacioBiciException(String msg) {
		super(msg);		
	}
	
		
}
