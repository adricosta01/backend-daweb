package pasarela.security;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import pasarela.usuarios.dto.UsuarioDTO;
import pasarela.usuarios.servicio.IServicioUsuarios;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	private static final String SECRET_KEY = "secreto";
	private static final List<String> ALLOWED_URLS = new LinkedList<>(Arrays.asList( "/auth/login", "/swagger-ui.html","/swagger-ui","/v3/api-docs","/swagger-resources","/webjars","/estaciones/.*","/estaciones/.*/bicicletas/.*/estacionar"));
	
	IServicioUsuarios servicioUsuario;
	
	@Autowired
	public JwtRequestFilter(IServicioUsuarios servicioUsuario) {
		this.servicioUsuario = servicioUsuario;
	}
	
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		// Comprueba que la petición lleve el token JWT y lo valida ...
		String authorization = request.getHeader("Authorization");
		
		if (ALLOWED_URLS.stream().anyMatch(url -> request.getRequestURI().startsWith(url))) {
			if(request.getRequestURI().startsWith("/auth/login")) {
				String idCodigo = servicioUsuario.SolicitadCodigoActivacion(request.getParameter("id"));
				servicioUsuario.AltaUsuario(new UsuarioDTO(request.getParameter("id"), request.getParameter("nombre"),request.getParameter("nombreCompleto"), request.getParameter("password"), "cliente", null, idCodigo));
				Map<String, Object> claims = servicioUsuario.VerificarCredenciales(request.getParameter("nombre"), request.getParameter("password"));
				
				Date caducidad = Date.from(Instant.now().plusSeconds(3600));
				String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, SECRET_KEY)
						.setExpiration(caducidad).compact();
				
				response.getWriter().append(token);
				return;
			}
	        chain.doFilter(request, response);
	        return;
	    }
		
		if(request.getRequestURI().startsWith("/auth/oauth2")){
			chain.doFilter(request, response);
			return;
		}
		if (authorization == null || !authorization.startsWith("Bearer ")) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No se ha encontrado el token o no es correcto.");
			return;
		} else {
			String token = authorization.substring("Bearer ".length()).trim();

			Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
			Date caducidad = claims.getExpiration();
			if (caducidad.before(new Date())) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token caducado.");
				return;
			}
			else {	
				// Establece el contexto de seguridad
				ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
				authorities.add(new SimpleGrantedAuthority((String) claims.get("rol")));
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);
				// Establecemos la autenticación en el contexto de seguridad
				// Se interpreta como que el usuario ha superado la autenticación
				SecurityContextHolder.getContext().setAuthentication(auth);
				chain.doFilter(request, response);
			}
		}
	}
}
