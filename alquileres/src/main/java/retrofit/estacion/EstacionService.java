package retrofit.estacion;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EstacionService {
	
	@GET("estaciones/{id}")
	Call<EstacionPOJO> getEstacion(@Path("id") String id);
	
	@POST("estaciones/{idEstacion}/bicicletas/{idBici}/estacionar")
	Call<String> dejarBicicleta(@Path("idEstacion") String idEstacion, @Path("idBici") String idBicicleta);
}
