package pasarela.rest;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import pasarela.usuarios.dto.UsuarioDTO;
import pasarela.usuarios.servicio.IServicioUsuarios;
import pasarela.verificador.Verificador;

@RestController
@RequestMapping("/auth")
public class PasarellaController {

	private static final String SECRET_KEY = "secret";
	
	IServicioUsuarios servicioUsuarios;
	
	@Autowired
	public PasarellaController(IServicioUsuarios servicioUsuarios) {
		this.servicioUsuarios = servicioUsuarios;
	}

	@PostMapping("/login")
	public ResponseEntity<Verificador> login(@RequestParam("username") String username,
			@RequestParam("password") String password) throws JsonProcessingException, IOException {
		Map<String, Object> claims = servicioUsuarios.VerificarCredenciales(username, password);
		if (claims != null) {
			
			Date caducidad = Date.from(Instant.now().plusSeconds(3600));
			String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, SECRET_KEY)
					.setExpiration(caducidad).compact();
			Verificador usuario = new Verificador(claims.get("idUsuario").toString(), claims.get("rol").toString(), 
					token, claims.get("nombreUsuario").toString());
			return ResponseEntity.ok(usuario);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestParam("id") String id, @RequestParam("nombre") String nombre,
			 @RequestParam("nombreCompleto") String nombreCompleto, @RequestParam("password") String password) throws JsonProcessingException, IOException {
		String idCodigo = servicioUsuarios.SolicitadCodigoActivacion(id);
		servicioUsuarios.AltaUsuario(new UsuarioDTO(id, nombre, nombreCompleto, password, "cliente", null, idCodigo));
		Map<String, Object> claims = servicioUsuarios.VerificarCredenciales(nombre, password);
		if (claims != null) {
			return ResponseEntity.status(HttpStatus.OK).build();
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
