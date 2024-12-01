package usuarios.repositorio;

import persistencia.jpa.UsuarioEntidad;
import repositorio.RepositorioJPA;
import usuarios.modelo.Usuario;

public class RepositorioUsuarioAdHocJPA extends RepositorioJPA<Usuario, UsuarioEntidad> implements RepositorioUsuarioAdHoc{
	
	@Override
	public Class<UsuarioEntidad> getClasePersistente() {
		return UsuarioEntidad.class;
	}

	@Override
	protected Usuario convertirADominio(UsuarioEntidad entidadPersistente) {
		return new Usuario(entidadPersistente.getId(), entidadPersistente.getReservas(), entidadPersistente.getAlquileres());
	}
	
	@Override
	protected UsuarioEntidad convertirAPersistencia(Usuario entidadDominio) {
		return new UsuarioEntidad(entidadDominio.getId(), entidadDominio.getReservas(), entidadDominio.getAlquileres());
	}

	@Override
	public String getNombre() {
		return UsuarioEntidad.class.getName().substring(UsuarioEntidad.class.getName().lastIndexOf(".") + 1);
	}
}
