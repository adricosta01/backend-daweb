package programa.estaciones.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;

import programa.estaciones.modelo.Estacion;


public interface RepositorioEstacionesMongo extends RepositorioEstaciones, MongoRepository<Estacion, String>{
	

}
