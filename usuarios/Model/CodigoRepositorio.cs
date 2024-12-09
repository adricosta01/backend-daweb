
using Codigos.Modelo;
using MongoDB.Driver;
using Repositorio;

namespace Codigos.Repositorio{

    public class RepositorioCodigoMongoDB : Repositorio<Codigo, string>
    {

        private readonly IMongoCollection<Codigo> codigos;

        public RepositorioCodigoMongoDB(){
            var database = new MongoClient("mongodb+srv://adrianhernandezcosta01:arso@cluster0.zllxg5q.mongodb.net/arso?retryWrites=true&w=majority&appName=Cluster0")
            .GetDatabase("arso");
            codigos = database.GetCollection<Codigo>("codigo.net");
        }

        public string Add(Codigo entity)
        {
            codigos.InsertOne(entity);

            return entity.Id;
        }

        public void Delete(Codigo entity)
        {
            codigos.DeleteOne(codigo => codigo.Id == entity.Id);
        }

        public List<Codigo> GetAll()
        {
            return codigos.Find(_ => true).ToList();
        }

        public Codigo GetById(string id)
        {
            return codigos
                .Find(codigo => codigo.Id == id)
                .FirstOrDefault();
        }

        public List<string> GetIds()
        {
            var todas =  codigos.Find(_ => true).ToList();

            return todas.Select(c => c.Id).ToList();
        }

        public void Update(Codigo entity)
        {
            codigos.ReplaceOne(codigo => codigo.Id == entity.Id, entity);
        }
    }
}