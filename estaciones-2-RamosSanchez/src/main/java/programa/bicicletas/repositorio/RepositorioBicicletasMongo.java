package programa.bicicletas.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;

import programa.bicicletas.modelo.Bicicleta;


public interface RepositorioBicicletasMongo extends RepositorioBicicletas, MongoRepository<Bicicleta, String>{

}
