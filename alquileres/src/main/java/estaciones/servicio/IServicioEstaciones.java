package estaciones.servicio;

import java.io.IOException;

public interface IServicioEstaciones {

	public boolean tieneHuecoDisponible(String id) throws IOException;
	
	public boolean situarBicicleta(String idEstacion, String idBicicleta) throws IOException;
}
