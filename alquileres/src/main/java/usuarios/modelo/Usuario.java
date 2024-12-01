package usuarios.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlRootElement;

import alquileres.modelo.Alquiler;
import persistencia.jpa.AlquilerEntidad;
import persistencia.jpa.ReservaEntidad;
import repositorio.Identificable;
import reservas.modelo.Reserva;

@XmlRootElement
public class Usuario implements Identificable{

	private String id;
	private ArrayList<Reserva> reservas;
	private ArrayList<Alquiler> alquileres;
	
	private static final int TIEMPO_MAXIMO_POR_DIA = 60;
	private static final int TIEMPO_MAXIMO_POR_SEMANA = 180;
	private static final int RESERVAS_MAXIMAS_CADUCADAS_PERMITIDAS = 3;
	
	public Usuario(String id, List<ReservaEntidad> reservas, List<AlquilerEntidad> alquileres) {
		this.id = id;
		this.alquileres = (ArrayList<Alquiler>) alquileres.stream().map(a -> new Alquiler(a.getIdBicicleta(), a.getInicio(), a.getFin()))
												.collect(Collectors.toList());
		this.reservas = (ArrayList<Reserva>) reservas.stream().map(r -> new Reserva(r.getIdBicicleta(), r.getCreada()))
												.collect(Collectors.toList());
	}
	
	public Usuario(ArrayList<Reserva> reservas, ArrayList<Alquiler> alquileres) {
		this.alquileres = alquileres;
		this.reservas = reservas;
	}
	
	public Usuario() {
		this.alquileres = new ArrayList<Alquiler>();
		this.reservas = new ArrayList<Reserva>();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(ArrayList<Reserva> reservas) {
		this.reservas = reservas;
	}

	public ArrayList<Alquiler> getAlquileres() {
		return alquileres;
	}

	public void setAlquileres(ArrayList<Alquiler> alquileres) {
		this.alquileres = alquileres;
	}

	public int reservasCadudadas() {
		return (int) reservas.stream()
							 .filter(r -> r.caducada())
							 .count();
	}
	
	public int tiempoUsoHoy() {
		return (int) alquileres.stream()
							   .filter(a -> a.getInicio().isAfter(LocalDateTime.now().minusDays(1)))
							   .mapToInt(Alquiler::getTiempo)
							   .sum();
	}
	
	public int tiempoUsoSemana() {
		return (int) alquileres.stream()
							   .filter(a -> a.getInicio().isAfter(LocalDateTime.now().minusWeeks(1)))
							   .mapToInt(Alquiler::getTiempo)
							   .sum();
	}
	public void borrarReservaActiva() {
		Reserva reserva = reservaActiva();
		reservas.remove(reserva);
	}
	public boolean superaTiempo() {
		return tiempoUsoHoy() >= TIEMPO_MAXIMO_POR_DIA || tiempoUsoSemana() >= TIEMPO_MAXIMO_POR_SEMANA;
	}
	
	public Reserva reservaActiva() {
		if (reservas.isEmpty()) {
            return null;
        }

		Reserva ultimaReserva = reservas.get(reservas.size() - 1);
        if (ultimaReserva.activa()) {
            return ultimaReserva; 
        }
        
       return null;
	}
	
	public Alquiler alquilerActivo() {
		if (alquileres.isEmpty()) {
            return null;
        }

		Alquiler ultimoAlquiler = alquileres.get(alquileres.size() - 1);
        if (ultimoAlquiler.getActivo()) {
            return ultimoAlquiler; 
        }
        
       return null;
	}
	
	public boolean bloqueado() {
		return reservasCadudadas() >= RESERVAS_MAXIMAS_CADUCADAS_PERMITIDAS;
	}
}
