package programa.historicos.repositorio;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import programa.historicos.modelo.Historico;

@NoRepositoryBean
public interface RepositorioHistoricos extends PagingAndSortingRepository<Historico, String>{

	Historico findByidBicicleta(String idBicicleta);
}
