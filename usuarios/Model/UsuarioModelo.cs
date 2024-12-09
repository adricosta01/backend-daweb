
using Codigos.Modelo;
using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace Usuarios.Modelo{

    public class Usuario{

        [BsonId]
        [BsonRepresentation(BsonType.String)]
        public string Id {get; set;}
        public string nombreUsuario {get; set;}
        public string nombreCompleto {get; set;}
        public string password {get; set;}
        public string rol {get; set;}
        public string OAuth2Id {get; set;}

        public Usuario(string Id, string nombreUsuario, string nombreCompleto, string password, string rol){
            this.Id = Id;
            this.nombreUsuario = nombreUsuario;
            this.nombreCompleto = nombreCompleto;
            this.password = password;
            this.rol = rol;
        }

        public Usuario(string nombreUsuario, string OAuth2Id, string rol){
            this.nombreUsuario = nombreUsuario;
            this.OAuth2Id = OAuth2Id;
            this.rol = rol;
        }
    }
}