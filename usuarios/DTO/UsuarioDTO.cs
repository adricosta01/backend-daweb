
using Codigos.Modelo;

namespace Usuarios.DTO{

    public class UsuarioDTO{

        public string Id {get; set;}
        public string nombreUsuario {get; set;}
        public string nombreCompleto {get; set;}
        public string password {get; set;}
        public string rol {get; set;}
        public string OAuth2Id {get; set;}
        public string idCodigo {get; set;}

        public UsuarioDTO(string Id, string nombreUsuario, string password, string rol, string OAuth2Id, string idCodigo){
            this.Id = Id;
            this.nombreUsuario = nombreUsuario;
            this.password = password;
            this.rol = rol;
            this.OAuth2Id = OAuth2Id;
            this.idCodigo = idCodigo;
        }

    }
}