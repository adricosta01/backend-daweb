package bicicletas.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import bicicletas.modelo.Bicicleta;
import incidencias.modelo.EstadoIncidencia;
import incidencias.modelo.Incidencia;
import repositorio.RepositorioException;
import repositorio.RepositorioJPA;
import utils.EntityManagerHelper;

public class RepositorioBicicletaAdHocJPA extends RepositorioJPA<Bicicleta> implements RepositorioBicicletaAdHoc{

	@Override
	public Class<Bicicleta> getClase() {
		return Bicicleta.class;
	}

	@Override
	public String getNombre() {
		return Bicicleta.class.getName().substring(Bicicleta.class.getName().lastIndexOf(".") + 1);
	}

	
	public Bicicleta getByID(String Id) throws RepositorioException{
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
		Query query = em.createNamedQuery("Revista.getByname");
		
		query.setParameter("keyword", "%"+Id+"%");
		
		query.setHint(QueryHints.REFRESH, HintValues.TRUE);
		
		return (Bicicleta) query.getSingleResult();
		}catch(RuntimeException ru) {
			throw new RepositorioException("Error buscando revistas por palabra clave", ru);
		}
		
	}
	
	@Override
	public List<Incidencia> getBicicletasConIncidenciaAbierta() throws RepositorioException{
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
		
			String queryString = "SELECT i "
					+ " FROM Bicicleta b "
					+ " INNER JOIN b.incidencia  i "
					+ " WHERE i.estado = :condicion1 OR i.estado = :condicion2 ";
			
			Query query = em.createQuery(queryString);						
			query.setParameter("condicion1", EstadoIncidencia.PENDIENTE);
			query.setParameter("condicion2", EstadoIncidencia.ASIGNADA);
			query.setHint(QueryHints.REFRESH, HintValues.TRUE);

			return query.getResultList();
		}catch(RuntimeException ru) {
			throw new RepositorioException("Error buscando revistas publicadas por tema", ru);
		}
	}
	
}
