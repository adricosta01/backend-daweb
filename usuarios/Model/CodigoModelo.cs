using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace Codigos.Modelo
{

    public class Codigo
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        public string Id {get; set;}
        public string IdUsuario {get; set;}
        public DateTime fechaExpiracion { get; set; }

        public Codigo(string IdUsuario)
        {
            this.IdUsuario = IdUsuario;
            this.fechaExpiracion = DateTime.Now.AddMinutes(30);
        }
    }
}