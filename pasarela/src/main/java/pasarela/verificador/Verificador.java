package pasarela.verificador;

public class Verificador {
	
	 public String idUsuario;
	 public String rol;
	 public String token;
	 public String nombreUsuario;
	 
	public Verificador(String idUsuario, String rol, String token, String nombreUsuario) {
		super();
		this.idUsuario = idUsuario;
		this.rol = rol;
		this.token = token;
		this.nombreUsuario = nombreUsuario;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	 
	 
	 
}
