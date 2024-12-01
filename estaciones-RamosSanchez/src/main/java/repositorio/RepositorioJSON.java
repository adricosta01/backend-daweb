package repositorio;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbConfig;
import javax.json.bind.config.PropertyNamingStrategy;
import javax.json.bind.config.PropertyOrderStrategy;
import javax.json.bind.spi.JsonbProvider;

public abstract class RepositorioJSON<T extends Identificable> implements RepositorioString<T> {

	private JsonbConfig config = new JsonbConfig()
			.withNullValues(true)
			.withFormatting(true)
			.withPropertyNamingStrategy(
			PropertyNamingStrategy.LOWER_CASE_WITH_UNDERSCORES)
			.withPropertyOrderStrategy(
			PropertyOrderStrategy.LEXICOGRAPHICAL);;
	
	private Jsonb json = JsonbProvider.provider()
			.create()
			.withConfig(config)
			.build();; 
	@Override
	public String add(T entity) throws RepositorioException {

		try {

			// Crear un objeto Jsonb

			// Serializar la entidad a JSON y escribirla directamente en un archivo
			json.toJson(entity,
					Files.newBufferedWriter(Paths.get("JSON/" + entity.getId() + ".json"), StandardOpenOption.CREATE));

		} catch (Exception e) {
			throw new RepositorioException("Fallo al guardar " + entity, e);
		}

		return entity.getId();
	}

	@Override
	public void update(T entity) throws RepositorioException, EntidadNoEncontrada {

		String id = entity.getId();

		// existe el documento?

		File fichero = new File("JSON/" + id + ".json");
		if (!fichero.exists()) {
			throw new EntidadNoEncontrada("No existe: " + id);
		}

		// guardar el documento

		try {

			// Crear un objeto Jsonb
			

			// Serializar la entidad a JSON y escribirla directamente en un archivo
			json.toJson(entity,
					Files.newBufferedWriter(Paths.get("JSON/" + id + ".json"), StandardOpenOption.CREATE));

		} catch (Exception e) {
			throw new RepositorioException("Fallo al guardar " + entity, e);
		}
	}

	@Override
	public void delete(T entity) throws RepositorioException, EntidadNoEncontrada {

		String id = entity.getId();

		// existe el documento?

		File fichero = new File("JSON/" + id + ".json");
		if (!fichero.exists()) {
			throw new EntidadNoEncontrada("No existe: " + id);
		}

		// borrar el documento

		fichero.delete();
	}

	public abstract Class<T> getClase();

	@Override
	public T getById(String id) throws RepositorioException, EntidadNoEncontrada {

		// existe el documento?

		File fichero = new File("JSON/" + id + ".json");
		if (!fichero.exists()) {
			throw new EntidadNoEncontrada("No existe: " + id);
		}

		// recuperar el documento

		try {

			Reader entrada = new FileReader("JSON/" + id + ".json");
			T entidad = (T) json.fromJson(entrada, getClase());

			return entidad;
		} catch (Exception e) {
			throw new RepositorioException("Fallo al recuperar " + id, e);
		}

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
		List<String> ids = new LinkedList<>();

		try {
			File jsonDirectory = new File("JSON");

			if (jsonDirectory.isDirectory()) {
				File[] files = jsonDirectory.listFiles();

				if (files != null) {
					for (File file : files) {
						if (file.isFile() && file.getName().endsWith(".json")) {
							String fileName = file.getName();
							String id = fileName.substring(0, fileName.lastIndexOf(".json"));
							ids.add(id);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new RepositorioException("Fallo al obtener IDs", e);
		}
		return ids;
	}

}
