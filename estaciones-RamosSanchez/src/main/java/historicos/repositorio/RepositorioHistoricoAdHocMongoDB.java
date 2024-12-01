package historicos.repositorio;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import historicos.modelo.Historico;
import repositorio.RepositorioMongoDB;
import utils.PropertiesReader;

public class RepositorioHistoricoAdHocMongoDB extends RepositorioMongoDB<Historico>
		implements RepositorioHistoricoAdHoc {

	protected MongoClient mongoClient;
	protected MongoDatabase database;
	protected MongoCollection<Historico> coleccion;

	public RepositorioHistoricoAdHocMongoDB() {
		PropertiesReader properties;
		try {
			properties = new PropertiesReader("mongo.properties");

			String connectionString = properties.getProperty("mongouri");

			MongoClient mongoClient = MongoClients.create(connectionString);

			String mongoDatabase = properties.getProperty("mongodatabase");

			database = mongoClient.getDatabase(mongoDatabase);

			CodecRegistry defaultCodecRegistry = CodecRegistries.fromRegistries(
					MongoClientSettings.getDefaultCodecRegistry(),
					CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

			coleccion = database.getCollection("historico", Historico.class).withCodecRegistry(defaultCodecRegistry);

		} catch (Exception e) {

		}
	}

	@Override
	public MongoCollection<Historico> getColeccion() {
		return coleccion;
	}

	@Override
	public Historico getByBicleta(String idBicicleta) {
		Document query = new Document("id_bicicleta", idBicicleta);

         // Ejecutar la consulta y obtener el primer resultado
        Historico historico = getColeccion().find(query).first();
        return historico;
         
	}
}
