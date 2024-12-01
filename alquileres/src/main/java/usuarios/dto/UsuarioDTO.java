package usuarios.dto;

import java.util.ArrayList;

import alquileres.dto.AlquilerDTO;
import reservas.dto.ReservaDTO;

public class UsuarioDTO {

	private String id;
	private ArrayList<ReservaDTO> reservas;
	private ArrayList<AlquilerDTO> alquileres;
	
	public UsuarioDTO(String id, ArrayList<ReservaDTO> reservas, ArrayList<AlquilerDTO> alquileres) {
		this.id = id;
		this.reservas = reservas;
		this.alquileres = alquileres;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ArrayList<ReservaDTO> getReservas() {
		return reservas;
	}
	public void setReservas(ArrayList<ReservaDTO> reservas) {
		this.reservas = reservas;
	}
	public ArrayList<AlquilerDTO> getAlquileres() {
		return alquileres;
	}
	public void setAlquileres(ArrayList<AlquilerDTO> alquileres) {
		this.alquileres = alquileres;
	}
	
	
}
