using Codigos.Modelo;
using Microsoft.AspNetCore.Mvc;
using Repositorio;
using Usuarios.DTO;
using Usuarios.Modelo;

namespace Usuarios.Servicio
{

    public interface IServicioUsuarios
    {
        Usuario Get(string id);

        string SolicitadCodigoActivacion(string id);

        void AltaUsuario(UsuarioDTO usuarioDTO);

        void BajaUsuario(string id);

        Dictionary<string, string> VerificarCredenciales(string nombreUsuario, string password);

        Dictionary<string, string> VerificarUsuarioOAuth2(string OAuth2Id);

        List<Usuario> GetUsuarios();
    }

    public class ServicioUsuarios : IServicioUsuarios
    {

        private Repositorio<Usuario, string> repositorioUsuarios;
        private Repositorio<Codigo, string> repositorioCodigos;

        public ServicioUsuarios(Repositorio<Usuario, string> repositorioUsuarios, Repositorio<Codigo, string> repositorioCodigos)
        {
            this.repositorioUsuarios = repositorioUsuarios;
            this.repositorioCodigos = repositorioCodigos;
        }

        public void AltaUsuario(UsuarioDTO usuarioDTO)
        {
            Codigo codigo = repositorioCodigos.GetById(usuarioDTO.idCodigo);
            
            if(usuarioDTO.password != null){
                if (usuarioDTO.Id != codigo.IdUsuario){
                    throw new Exception("El codigo de activación o el usuario es erroneo.");
                }

                Usuario usuario = new Usuario(usuarioDTO.Id, usuarioDTO.nombreUsuario, usuarioDTO.nombreCompleto, usuarioDTO.password, usuarioDTO.rol);
                usuarioDTO.Id = repositorioUsuarios.Add(usuario);
            }
            else{
                if (usuarioDTO.OAuth2Id != codigo.IdUsuario){
                    throw new Exception("El codigo de activación o el usuario es erroneo.");
                }

                Usuario usuario = new Usuario(usuarioDTO.nombreUsuario, usuarioDTO.OAuth2Id, usuarioDTO.rol);
                usuarioDTO.Id = repositorioUsuarios.Add(usuario);
            }
        }

        public void BajaUsuario(string id)
        {
            Usuario usuario = repositorioUsuarios.GetById(id);
            repositorioUsuarios.Delete(usuario);
        }

        public Usuario Get(string id)
        {
            return repositorioUsuarios.GetById(id);
        }

        public List<Usuario> GetUsuarios()
        {
            return repositorioUsuarios.GetAll();
        }

        public string SolicitadCodigoActivacion(string idUsuario)
        {
            if (idUsuario == null){
                throw new Exception("El idUsuario no puede ser nulo.");
            }
            
            Codigo codigo = new Codigo(idUsuario);
            repositorioCodigos.Add(codigo);

            return codigo.Id;
        }

        public Dictionary<string, string> VerificarCredenciales(string nombreUsuario, string password)
        {
            List<Usuario> usuarios = repositorioUsuarios.GetAll();

            foreach (Usuario usuario in usuarios)
            {
                if (usuario.nombreUsuario == nombreUsuario && usuario.password == password)
                {
                    return new Dictionary<string, string>
                    {
                        { "idUsuario", usuario.Id.ToString() },
                        { "nombreUsuario", usuario.nombreUsuario},
                        { "rol", usuario.rol }
                    };
                }
            }

            return null;
        }

        public Dictionary<string, string> VerificarUsuarioOAuth2(string OAuth2Id)
        {
            List<Usuario> usuarios = repositorioUsuarios.GetAll();

            foreach (Usuario usuario in usuarios)
            {
                if (usuario.OAuth2Id == OAuth2Id)
                {
                    return new Dictionary<string, string>
                    {
                        { "idUsuario", usuario.Id.ToString() },
                        { "nombreUsuario", usuario.nombreUsuario },
                        { "rol", usuario.rol }
                    };
                }
            }

            return null;
        }
    }
}