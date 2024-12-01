package pasarela.security;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import pasarela.usuarios.dto.UsuarioDTO;
import pasarela.usuarios.servicio.IServicioUsuarios;

@Component
public class SecuritySuccessHandler implements AuthenticationSuccessHandler {
	
	private static final String SECRET_KEY = "secreto";
	
	IServicioUsuarios servicioUsuario;
	
	@Autowired
	public SecuritySuccessHandler(IServicioUsuarios servicioUsuario) {
		this.servicioUsuario = servicioUsuario;
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {
		DefaultOAuth2User usuario = (DefaultOAuth2User) authentication.getPrincipal();
		Map<String, Object> claims = servicioUsuario.VerificarUsuarioOAuth2(Integer.toString(usuario.getAttribute("id")));
		
		if (claims != null) {
			Date caducidad = Date.from(Instant.now().plusSeconds(3600));
			String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, SECRET_KEY)
					.setExpiration(caducidad).compact();
			
			response.getWriter().append(token);
		} else {
			String idCodigo = servicioUsuario.SolicitadCodigoActivacion(Integer.toString(usuario.getAttribute("id")));
			servicioUsuario.AltaUsuario(new UsuarioDTO(null, usuario.getAttribute("login"),null, null, "cliente", Integer.toString(usuario.getAttribute("id")), idCodigo));
			claims = servicioUsuario.VerificarUsuarioOAuth2(Integer.toString(usuario.getAttribute("id")));
			
			Date caducidad = Date.from(Instant.now().plusSeconds(3600));
			String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, SECRET_KEY)
					.setExpiration(caducidad).compact();
			
			response.getWriter().append(token);			
		}
	}
}