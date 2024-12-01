package programa.historicos.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;

import programa.historicos.modelo.Historico;

public interface RepositorioHistoricosMongo extends RepositorioHistoricos, MongoRepository<Historico, String>{

}
