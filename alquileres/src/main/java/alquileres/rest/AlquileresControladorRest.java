package alquileres.rest;

import java.net.URI;
import java.util.ArrayList;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import alquileres.dto.AlquilerDTO;
import alquileres.modelo.Alquiler;
import alquileres.servicio.IServicioAlquileres;
import repositorio.EntidadNoEncontrada;
import reservas.dto.ReservaDTO;
import reservas.modelo.Reserva;
import servicio.FactoriaServicios;
import usuarios.dto.UsuarioDTO;
import usuarios.modelo.Usuario;

@Path("alquileres")
public class AlquileresControladorRest {

	private IServicioAlquileres servicio = FactoriaServicios.getServicio(IServicioAlquileres.class);
	
	@Context
	private HttpServletRequest servletRequest;
	
	@Context
	private UriInfo uriInfo;
		
	/*
	 curl --location --request POST "http://localhost:8080/api/usuarios/2/reserva/12" --header "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c3VhcmlvIiwicm9sZXMiOiJ1c3VhcmlvIiwiZXhwIjoxNzA5OTMyMTQzfQ.cxmx2tNBY7AumgLOwg4frydOcWs0dvEOT6LZ_7Rm32g"
	*/
	@POST
	@Path("{id}/reserva/{id_bicicleta}")
	@Consumes({MediaType.APPLICATION_JSON})
	@RolesAllowed("usuario")
	public Response reservar(@PathParam("id") String id, @PathParam("id_bicicleta") String idBicicleta) throws Exception {
		servicio.reservar(id, idBicicleta);
		URI nuevaURL = this.uriInfo.getAbsolutePathBuilder().path(id).build();
		return Response.created(nuevaURL).build();
	}
	
	// curl --location "http://localhost:8080/api/usuario/2" --header "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c3VhcmlvIiwicm9sZXMiOiJ1c3VhcmlvLGdlc3RvciIsImV4cCI6MTcxMjg5MzUwNH0.VIz2Dni2XtzLxvj0htx_y5Donr4Lu5vwj_BCNfLZyF4"
	
	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON})
	@RolesAllowed("usuario")
	public Response historialUsuario(@PathParam("id") String id) throws Exception {
		Usuario usuario = servicio.historialUsuario(id);
		UsuarioDTO usuarioDto = transformUsuarioToDTO(usuario);
		return Response.status(Response.Status.OK).entity(usuarioDto).build();
	}
	
	// curl --location --request POST "http://localhost:8080/api/usuario/1/alquilar/66198c49ee55de01779db915" --header "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c3VhcmlvIiwicm9sZXMiOiJ1c3VhcmlvLGdlc3RvciIsImV4cCI6MTcxMjk1MzQ2OH0.Od1mpr_bpKiL1kww95K7jYR3tvPiN7Nurso_f4VBD3Y"
	
	@POST
	@Path("{id}/alquilar/{bicicleta_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed("usuario")
	public Response alquilar(@PathParam("id") String id, @PathParam("bicicleta_id") String idBicicleta) throws Exception {
		try {
			servicio.alquilar(id, idBicicleta);
		} catch(EntidadNoEncontrada e) {
			
		} catch(Exception e) {
			
		}
		return Response.status(Response.Status.OK).build();
	}
	
	// curl --location --request PUT "http://localhost:8080/api/usuario/2" --header "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c3VhcmlvIiwicm9sZXMiOiJ1c3VhcmlvLGdlc3RvciIsImV4cCI6MTcxMDEwMzAwOH0.XG17Y44eHYT3lnWNqeRMb3BKyhYycgP91DoQh_ftXyw"
	
	@PUT
	@Path("{id}")
	@Consumes({MediaType.APPLICATION_JSON})
	@RolesAllowed("usuario")
	public Response confirmarReserva(@PathParam("id") String id) throws Exception {
		servicio.confirmarReserva(id);
		return Response.status(Response.Status.OK).build();
	}
		
		//curl -location -request PATCH http://localhost:8080/api/usuarios/2 --header "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c3VhcmlvIiwicm9sZXMiOiJ1c3VhcmlvLGdlc3RvciIsImV4cCI6MTcxMDEwMzAwOH0.XG17Y44eHYT3lnWNqeRMb3BKyhYycgP91DoQh_ftXyw"
		
		@DELETE
		@Path("{id}")
		@Consumes({MediaType.APPLICATION_JSON})
		@RolesAllowed("usuario")
		public Response liberarBloqueo(@PathParam("id") String id) throws Exception {
			servicio.liberarBloqueo(id);
			return Response.status(Response.Status.OK).build();
		}
		
		//curl -location -request POST http://localhost:8080/api/usuarios/1/estacionar/66198218cd9b8130608e3850 --header "Authorization: Bearer curl -location -request POST http://localhost:8080/api/usuarios/1/estacionar/66198218cd9b8130608e3850 --header "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c3VhcmlvIiwicm9sZXMiOiJ1c3VhcmlvLGdlc3RvciIsImV4cCI6MTcxMjk1MzQ2OH0.Od1mpr_bpKiL1kww95K7jYR3tvPiN7Nurso_f4VBD3Y""
		@POST
		@Path("{id}/estacionar/{estacion_id}}")
		@Consumes({MediaType.APPLICATION_JSON})
		@RolesAllowed("gestor")
		public Response dejarBicicleta(@PathParam("id") String id, @PathParam("{estacion_id}") String id_bicicleta) throws Exception {
			servicio.dejarBicicleta(id, id_bicicleta);
			return Response.status(Response.Status.OK).build();
		}
		
		private UsuarioDTO transformUsuarioToDTO(Usuario usuario) {
			ArrayList<ReservaDTO> reservasDTOs = new ArrayList<ReservaDTO>();
			for(Reserva r : usuario.getReservas()) {
				reservasDTOs.add(new ReservaDTO(r.getId(), r.getIdBicicleta(), r.getCreada().toString(), r.getCaducidad().toString()));
			}
			
			ArrayList<AlquilerDTO> alquileresDTOs = new ArrayList<AlquilerDTO>();
			for(Alquiler a : usuario.getAlquileres()) {
				alquileresDTOs.add(new AlquilerDTO(a.getId(), a.getIdBicicleta(), a.getInicio().toString(), a.getFin().toString()));
			}
			
			return new UsuarioDTO(usuario.getId(), reservasDTOs, alquileresDTOs);
		}
}
