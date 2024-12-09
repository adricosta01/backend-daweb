package pasarela.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private SecuritySuccessHandler successHandler;
	@Autowired
	private JwtRequestFilter authenticationRequestFilter;

	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.httpBasic().disable().csrf().disable()
			.authorizeRequests()
			.antMatchers("/auth/login").permitAll()
			.antMatchers("/auth/register").permitAll()
			.antMatchers("/estaciones/**").permitAll()
			.antMatchers("/alquileres/**").permitAll()
			.antMatchers("/usuarios/codigo").hasAuthority("gestor")
			.antMatchers("/usuarios/bajaUsuario/**").hasAuthority("gestor")
			.antMatchers("/usuarios/getUsuarios").hasAuthority("gestor")
			.antMatchers("/auth/oauth2").authenticated()
			.and()
			.oauth2Login().successHandler(this.successHandler)
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		httpSecurity.addFilterBefore(this.authenticationRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
