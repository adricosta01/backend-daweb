package repositorio;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import utils.EntityManagerHelper;

public abstract class RepositorioJPA<T extends Identificable, E extends Identificable> implements RepositorioString<T> {
	
	public abstract Class<E> getClasePersistente();
	public abstract String getNombre();
    protected abstract T convertirADominio(E entidadPersistente);
    protected abstract E convertirAPersistencia(T entidadDominio);
    
	@Override
	public String add(T entity) throws RepositorioException {
		EntityManager em = EntityManagerHelper.getEntityManager();
		E instance = convertirAPersistencia(entity);
		try {
			em.getTransaction().begin();		
			em.persist(instance);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new RepositorioException("Error al guardar la entidad con id "+instance.getId(),e);
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			EntityManagerHelper.closeEntityManager();
		}
		entity.setId(instance.getId());
		return instance.getId();
	}

	@Override
	public void update(T object) throws RepositorioException, EntidadNoEncontrada {
		EntityManager em = EntityManagerHelper.getEntityManager();
		E entity = convertirAPersistencia(object);
		try {
			em.getTransaction().begin();
			
			E instance = em.find(getClasePersistente(), entity.getId());
			if(instance == null) {
				throw new EntidadNoEncontrada(entity.getId() + " no existe en el repositorio");
			}
			entity = em.merge(entity);			
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new RepositorioException("Error al actualizar la entidad con id "+entity.getId(),e);
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			EntityManagerHelper.closeEntityManager();
		}
	}

	@Override
	public void delete(T entity) throws RepositorioException, EntidadNoEncontrada {
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			em.getTransaction().begin();
			E instance = em.find(getClasePersistente(), entity.getId());
			if(instance == null) {
				throw new EntidadNoEncontrada(entity.getId() + " no existe en el repositorio");
			}
			em.remove(instance);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new RepositorioException("Error al borrar la entidad con id "+entity.getId(),e);
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			EntityManagerHelper.closeEntityManager();
		}
	}

	@Override
	public T getById(String id) throws EntidadNoEncontrada, RepositorioException {
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {			
			E instance = em.find(getClasePersistente(), id);

			if (instance != null) {
				em.refresh(instance);
			} else {
				throw new EntidadNoEncontrada(id + " no existe en el repositorio");
			}

			return convertirADominio(instance);

		} catch (RuntimeException re) {
			throw new RepositorioException("Error al recuperar la entidad con id "+id,re);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll() throws RepositorioException{
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			final String queryString = " SELECT model from " + getNombre() + " model ";

			Query query = em.createQuery(queryString);

			query.setHint(QueryHints.REFRESH, HintValues.TRUE);

			List<E> lista = query.getResultList();
			
			return lista.stream().map(e -> convertirADominio(e)).collect(Collectors.toList());
		} catch (RuntimeException re) {

			throw new RepositorioException("Error buscando todas las entidades de "+getNombre(),re);

		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getIds() throws RepositorioException{
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			final String queryString = " SELECT model.id from " + getNombre() + " model ";

			Query query = em.createQuery(queryString);

			query.setHint(QueryHints.REFRESH, HintValues.TRUE);

			return query.getResultList();

		} catch (RuntimeException re) {

			throw new RepositorioException("Error buscando todos los ids de "+getNombre(),re);

		}
	}

}
