package programa.historicos.modelo;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import programa.repositorio.Identificable;


@Document(collection ="historicos")
public class Historico implements Identificable{
	
	@Id
	private String id;
	
	@BsonProperty(value="id_bicicleta")
	private String idBicicleta;
	
	@BsonProperty(value="registros")
	private Set<Registro> registros;
	
	public Historico(String idBicicleta) {
		this.idBicicleta = idBicicleta;
		this.registros = new HashSet<Registro>();
	}
	
	public Historico() {
		
	}
	@Override
	public String getId() {
		return id;
	}
	
	public String getIdBicicleta() {
		return idBicicleta;
	}
	
	public Set<Registro> getRegistros() {
		return registros;
	}
	
	@Override
	public void setId(String id) {
		this.id = id;
		
	}
	public void setIdBicicleta(String idBicicleta) {
		this.idBicicleta = idBicicleta;
	}
	
	public void setRegistros(Set<Registro> registros) {
		this.registros = registros;
	}
	public void addRegistro(Registro registro) {
		this.registros.add(registro);
	}
	
	public Registro getRegistroActual(String idEstacion) {
		
		return this.registros.stream().
				filter(r -> (r.getFechaFin() == null || r.getFechaFin().isEqual(null)) && r.getIdEstacion().equals(idEstacion)).
				findFirst().get();
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Historico other = (Historico) obj;
		return Objects.equals(id, other.id);
	}
	
}
