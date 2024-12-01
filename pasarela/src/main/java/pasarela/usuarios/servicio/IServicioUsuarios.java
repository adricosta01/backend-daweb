package pasarela.usuarios.servicio;

import java.io.IOException;
import java.util.Map;

import pasarela.usuarios.dto.UsuarioDTO;

public interface IServicioUsuarios {

	public String SolicitadCodigoActivacion(String id) throws IOException;
	
	public void AltaUsuario(UsuarioDTO usuarioDTO) throws IOException;

	public Map<String, Object> VerificarCredenciales(String nombreUsuario, String password) throws IOException;
	
	public Map<String, Object> VerificarUsuarioOAuth2(String OAuth2Id) throws IOException;
}
