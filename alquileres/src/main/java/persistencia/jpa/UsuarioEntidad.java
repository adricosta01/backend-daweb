package persistencia.jpa;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import alquileres.modelo.Alquiler;
import repositorio.Identificable;
import reservas.modelo.Reserva;

@Entity
public class UsuarioEntidad implements Identificable{

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private String id;
	@OneToMany(cascade = CascadeType.ALL)
	private List<ReservaEntidad> reservas;
	@OneToMany(cascade = CascadeType.ALL)
	private List<AlquilerEntidad> alquileres;
	
	public UsuarioEntidad() {
	}
	
	public UsuarioEntidad(String id, List<Reserva> reservas, List<Alquiler> alquileres) {
		this.id = id;
		this.reservas = reservas.stream().map(r -> new ReservaEntidad(r.getIdBicicleta(), r.getCreada())).collect(Collectors.toList());
		this.alquileres = alquileres.stream().map(r -> new AlquilerEntidad(r.getIdBicicleta(), r.getInicio(), r.getFin())).collect(Collectors.toList());
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<ReservaEntidad> getReservas() {
		return reservas;
	}
	public void setReservas(List<ReservaEntidad> reservas) {
		this.reservas = reservas;
	}
	public List<AlquilerEntidad> getAlquileres() {
		return alquileres;
	}
	public void setAlquileres(List<AlquilerEntidad> alquileres) {
		this.alquileres = alquileres;
	}
		
}
