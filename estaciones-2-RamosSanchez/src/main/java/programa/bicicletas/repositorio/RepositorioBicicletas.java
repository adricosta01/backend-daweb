package programa.bicicletas.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import programa.bicicletas.modelo.Bicicleta;

@NoRepositoryBean
public interface RepositorioBicicletas extends PagingAndSortingRepository<Bicicleta, String>{

	Page<Bicicleta> findByestacionID(String idEstacion, Pageable pageable);
	
    Page<Bicicleta> findByEstacionIDAndDisponibleIsTrue(String idEstacion, Pageable pageable);
}
