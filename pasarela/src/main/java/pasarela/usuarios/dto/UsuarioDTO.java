package pasarela.usuarios.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioDTO {

    public String Id;
    public String nombreUsuario;
    public String nombreCompleto;
    public String password; 
    public String rol;
    public String OAuth2Id;
    public String idCodigo;
	
    public UsuarioDTO() {}
    
    public UsuarioDTO(String Id, String nombreUsuario,String nombreCompleto, String password, String rol, String OAuth2Id, String idCodigo){
        this.Id = Id;
        this.nombreUsuario = nombreUsuario;
        this.nombreCompleto = nombreCompleto;
        this.password = password;
        this.rol = rol;
        this.OAuth2Id = OAuth2Id;
        this.idCodigo = idCodigo;
    }
    
    public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	public String getOAuth2Id() {
		return OAuth2Id;
	}
	public void setOAuth2Id(String oAuth2Id) {
		OAuth2Id = oAuth2Id;
	}
	public String getIdCodigo() {
		return idCodigo;
	}
	public void setIdCodigo(String idCodigo) {
		this.idCodigo = idCodigo;
	}  
}
