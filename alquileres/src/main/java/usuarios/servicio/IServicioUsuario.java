package usuarios.servicio;

import java.util.Map;

public interface IServicioUsuario {

	public Map<String, Object> verificarCredenciales(String username, String password);

}
