package auth;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import alquileres.servicio.IServicioAlquileres;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import servicio.FactoriaServicios;
import usuarios.servicio.IServicioUsuario;

@Path("auth")
public class ControladorAuth {

	IServicioUsuario servicioUsuario = FactoriaServicios.getServicio(IServicioUsuario.class);
	
	private static final String SECRET_KEY = "secreto";

	
	/*
	curl --location "http://localhost:8080/api/auth/login" --header "Content-Type: application/x-www-form-urlencoded" --data-urlencode "username=usuario" --data-urlencode "password=clave"
	*/
	@POST
	@Path("/login")
	@PermitAll
	public Response login(@FormParam("username") String username, @FormParam("password") String password) {
		Map<String, Object> claims = servicioUsuario.verificarCredenciales(username, password);
		if (claims != null) {
			Date caducidad = Date.from(Instant.now().plusSeconds(3600)); // 1 hora de validez
			String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, SECRET_KEY)
					.setExpiration(caducidad).compact();

			return Response.ok(token).build();
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).entity("Credenciales inválidas").build();
		}
	}
}
