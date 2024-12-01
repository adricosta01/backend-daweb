package programa.security;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class SecuritySuccessHandler implements AuthenticationSuccessHandler {
	
	private static final String SECRET_KEY = "secreto";

	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {
		DefaultOAuth2User usuario = (DefaultOAuth2User) authentication.getPrincipal();
		Map<String, Object> claims = verificarCredenciales(usuario);
		if (claims != null) {
			Date caducidad = Date.from(Instant.now().plusSeconds(3600));
			String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, SECRET_KEY)
					.setExpiration(caducidad).compact();
			
			response.getWriter().append(token);
		} else {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No está autorizado para acceder a la aplicación");
		}
	}
	
	private Map<String, Object> verificarCredenciales(DefaultOAuth2User user) {
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("sub", user.getName());
		claims.put("rol", "usuario,gestor");
		return claims;
	}
}