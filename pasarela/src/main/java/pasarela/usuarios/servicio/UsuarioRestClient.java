package pasarela.usuarios.servicio;

import java.util.Map;

import pasarela.usuarios.dto.UsuarioDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UsuarioRestClient {
	
	@FormUrlEncoded
	@POST("api/usuarios/codigo")
	Call<CodigoActivacionResponse> SolicitadCodigoActivacion(@Field("id") String id);
	
	@POST("api/usuarios/altaUsuario")
	Call<UsuarioDTO> AltaUsuario(@Body UsuarioDTO usuarioDTO);

	@FormUrlEncoded
	@POST("api/usuarios/verificar")
	Call<Map<String, Object>> VerificarCredenciales(@Field("nombreUsuario") String nombreUsuario, @Field("password") String password);

	@FormUrlEncoded
	@POST("api/usuarios/verificar/oauth2")
	Call<Map<String, Object>> VerificarUsuarioOAuth2(@Field("OAuth2Id") String OAuth2Id);
}
