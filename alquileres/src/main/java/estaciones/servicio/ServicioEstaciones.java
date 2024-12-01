package estaciones.servicio;

import java.io.IOException;

import retrofit.estacion.EstacionPOJO;
import retrofit.estacion.EstacionService;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ServicioEstaciones implements IServicioEstaciones{

	private Retrofit retrofit;
	public ServicioEstaciones() {
		retrofit = new Retrofit.Builder()
				.baseUrl("http://localhost:8090/")
				.addConverterFactory(JacksonConverterFactory.create())
				.build();
	}
	
	@Override
	public boolean tieneHuecoDisponible(String id) throws IOException {
		EstacionService servicio = retrofit.create(EstacionService.class);
		Call<EstacionPOJO> estacionLlamada = servicio.getEstacion(id);
		Response<EstacionPOJO> respuesta = estacionLlamada.execute();
		EstacionPOJO estacion = respuesta.body();
		return estacion.getMaxPuestosBicis() - estacion.getNumBicis() > 0;
	}

	@Override
	public boolean situarBicicleta(String idEstacion, String idBicicleta) throws IOException {
		EstacionService servicio = retrofit.create(EstacionService.class);
		Call<String> dejarBiciLlamada = servicio.dejarBicicleta(idEstacion, idBicicleta);
		Response<String> respuesta = dejarBiciLlamada.execute();
		return respuesta.isSuccessful();
	}

}
