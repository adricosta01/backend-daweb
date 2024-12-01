package estaciones.repositorio;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;

import bicicletas.modelo.Bicicleta;
import estaciones.modelo.Estacion;
import repositorio.RepositorioException;
import repositorio.RepositorioMongoDB;
import utils.PropertiesReader;

public class RepositorioEstacionesAdHocMongoDB extends RepositorioMongoDB<Estacion>
		implements RepositorioEstacionesAdHoc {

	protected MongoClient mongoClient;
	protected MongoDatabase database;
	protected MongoCollection<Estacion> coleccion;

	public RepositorioEstacionesAdHocMongoDB() {
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

			coleccion = database.getCollection("estacion", Estacion.class).withCodecRegistry(defaultCodecRegistry);

		} catch (Exception e) {

		}
	}

	@Override
	public MongoCollection<Estacion> getColeccion() {
		return coleccion;
	}

	@Override
	public Estacion getEstacionPuestosLibres() throws NoHayEspacioBiciException {
		FindIterable<Estacion> resultados = getColeccion().find();

		MongoCursor<Estacion> it = resultados.iterator();
		while (it.hasNext()) {
			Estacion aux = it.next();
			if (aux.getMaxPuestosBicis() - aux.getNumBicis() > 0) {
				return aux;
			}
		}
		throw new NoHayEspacioBiciException("No hay espacio en ninguna estación para estacionar la bici");
	}

	public List<Estacion> getBicisEnEstacionesCercanas(double longitud, double latitud) {
		coleccion.createIndex(Indexes.geo2dsphere("coordenadas"));

		FindIterable<Estacion> result = coleccion
				.find(Filters.nearSphere("coordenadas", new Point(new Position(longitud, latitud)),null,null))
				.limit(3);

		List<Estacion> estaciones = new LinkedList<>();
		result.into(estaciones);
		
		return estaciones;
	}
	
	public Set<Estacion> getEstacionesPorSitiosTuristicos(){
		
		List<Estacion> estaciones = coleccion.find().into(new ArrayList<>());
		
		Set<Estacion> estacionesOrdenadas = estaciones.stream()
                .sorted(Comparator.comparingInt(estacion -> estacion.getSitiosTuristicosCercanos().size()))
                .collect(Collectors.toSet());
		
		return estacionesOrdenadas;
	}

}
