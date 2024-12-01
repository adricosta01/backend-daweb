package usuarios.servicio;

import java.util.HashMap;
import java.util.Map;

public class ServicioUsuario implements IServicioUsuario {

	@Override
	public Map<String, Object> verificarCredenciales(String username, String password) {
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("sub", username);
		claims.put("roles", "usuario,gestor");
		return claims;
	}

}
