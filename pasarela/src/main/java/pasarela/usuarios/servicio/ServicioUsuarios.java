package pasarela.usuarios.servicio;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pasarela.usuarios.dto.UsuarioDTO;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Service
@Transactional
public class ServicioUsuarios implements IServicioUsuarios {

	private Retrofit retrofit;
	UsuarioRestClient servicio;
	
	@Autowired
	public ServicioUsuarios() {
		retrofit = new Retrofit.Builder()
				.baseUrl("http://localhost:7065/")
				.addConverterFactory(JacksonConverterFactory.create())
				.build();
		
		this.servicio = retrofit.create(UsuarioRestClient.class);
	}
	
	@Override
	public String SolicitadCodigoActivacion(String id) throws IOException {
	    Call<CodigoActivacionResponse> solicitarCodigoLlamada = servicio.SolicitadCodigoActivacion(id);
	    Response<CodigoActivacionResponse> respuesta = solicitarCodigoLlamada.execute();
	    
	    if (respuesta.isSuccessful()) {
	        CodigoActivacionResponse codigoResponse = respuesta.body();
	        if (codigoResponse != null) {
	            return codigoResponse.getId();
	        } else {
	            throw new IOException("La respuesta no contiene datos válidos");
	        }
	    } else {
	        throw new IOException("La solicitud no fue exitosa: " + respuesta.message());
	    }
	}
	
	@Override
	public void AltaUsuario(UsuarioDTO usuarioDTO) throws IOException {
		Call<UsuarioDTO> altaUsuarioLlamada = servicio.AltaUsuario(usuarioDTO);
		Response<UsuarioDTO> respuesta = altaUsuarioLlamada.execute();
		
		if (!respuesta.isSuccessful()) {
	        throw new IOException("La solicitud no fue exitosa: " + respuesta.message());
	    }
	}

	@Override
	public Map<String, Object> VerificarCredenciales(String nombreUsuario, String password) throws IOException {
		Call<Map<String, Object>> verificarCredencialesLlamada = servicio.VerificarCredenciales(nombreUsuario, password);
		Response<Map<String, Object>> respuesta = verificarCredencialesLlamada.execute();
		
		if (respuesta.isSuccessful()) {
	        return respuesta.body();
	    } else {
	        throw new IOException("La solicitud no fue exitosa: " + respuesta.message());
	    }
	}

	@Override
	public Map<String, Object> VerificarUsuarioOAuth2(String OAuth2Id) throws IOException {
		Call<Map<String, Object>> verificarOAuth2Llamada = servicio.VerificarUsuarioOAuth2(OAuth2Id);
		Response<Map<String, Object>> respuesta = verificarOAuth2Llamada.execute();
		
		if (respuesta.isSuccessful()) {
	        return respuesta.body();
	    } else {
	        throw new IOException("La solicitud no fue exitosa: " + respuesta.message());
	    }
	}

}
