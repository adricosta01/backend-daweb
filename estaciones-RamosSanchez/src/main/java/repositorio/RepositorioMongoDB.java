package repositorio;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

public abstract class RepositorioMongoDB<T extends Identificable> implements RepositorioString<T> {

	public abstract MongoCollection<T> getColeccion();

	@Override
	public String add(T entity) throws RepositorioException {
		InsertOneResult result = getColeccion().insertOne(entity);
		if (result.getInsertedId() != null)
			return result.getInsertedId().asObjectId().getValue().toString();
		entity.setId(result.getInsertedId().asString().toString());
		return result.getInsertedId().asString().toString();
	}

	@Override
	public void update(T entity) throws RepositorioException, EntidadNoEncontrada {
		Bson filter = Filters.eq("_id", new ObjectId(entity.getId()));
	    UpdateResult result = getColeccion().replaceOne(filter, entity);
	    if(result.getMatchedCount() == 0) {
	    	throw new EntidadNoEncontrada("No existe: " + entity.getId());
	    }
	    
	    
	}

	@Override
	public void delete(T entity) throws RepositorioException, EntidadNoEncontrada {
		DeleteResult result = getColeccion().deleteOne(new Document("_id", new ObjectId(entity.getId())));
		
		if(result.getDeletedCount() == 0) {
			throw new EntidadNoEncontrada("No existe: " + entity.getId());
		}
	}

	@Override
	public T getById(String id) throws RepositorioException, EntidadNoEncontrada {
		ObjectId objectId = new ObjectId(id);
		T objeto = getColeccion().find(new Document("_id", objectId)).first();
		if(objeto == null)
			throw new EntidadNoEncontrada("No existe: " + id);
		return objeto;
	}

	@Override
	public List<T> getAll() throws RepositorioException {
		LinkedList<T> resultado = new LinkedList<>();

		for (String id : getIds()) {
			try {
				T entity = getById(id);
				resultado.add(entity);
			} catch (EntidadNoEncontrada e) {
				// No va a suceder
			}
		}

		return resultado;
	}

	@Override
	public List<String> getIds() throws RepositorioException {
		FindIterable<T> iterableObjetos = getColeccion().find();
		List<String> listaIds = new LinkedList<>();
		for(T objeto: iterableObjetos) {
			listaIds.add(objeto.getId());
		}
		return listaIds;
	}

}
