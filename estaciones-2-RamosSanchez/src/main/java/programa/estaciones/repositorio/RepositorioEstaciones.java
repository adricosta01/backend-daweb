package programa.estaciones.repositorio;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import programa.estaciones.modelo.Estacion;


@NoRepositoryBean
public interface RepositorioEstaciones extends PagingAndSortingRepository<Estacion, String>{

}
