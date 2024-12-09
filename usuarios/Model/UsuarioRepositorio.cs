
using MongoDB.Driver;
using Repositorio;
using Usuarios.Modelo;

namespace Usuarios.Repositorio{

    public class RepositorioUsuarioMongoDB : Repositorio<Usuario, string>
    {

        private readonly IMongoCollection<Usuario> usuarios;

        public RepositorioUsuarioMongoDB(){
            var database = new MongoClient("mongodb+srv://adrianhernandezcosta01:arso@cluster0.zllxg5q.mongodb.net/arso?retryWrites=true&w=majority&appName=Cluster0")
            .GetDatabase("arso");
            usuarios = database.GetCollection<Usuario>("usuario.net");
        }

        public string Add(Usuario entity)
        {
            usuarios.InsertOne(entity);

            return entity.Id;
        }

        public void Delete(Usuario entity)
        {
            usuarios.DeleteOne(usuario => usuario.Id == entity.Id);
        }

        public List<Usuario> GetAll()
        {
            return usuarios.Find(_ => true).ToList();
        }

        public Usuario GetById(string id)
        {
            return usuarios
                .Find(usuario => usuario.Id == id)
                .FirstOrDefault();
        }

        public List<string> GetIds()
        {
            var todas =  usuarios.Find(_ => true).ToList();

            return todas.Select(u => u.Id).ToList();
        }

        public void Update(Usuario entity)
        {
            usuarios.ReplaceOne(usuario => usuario.Id == entity.Id, entity);
        }
    }
}