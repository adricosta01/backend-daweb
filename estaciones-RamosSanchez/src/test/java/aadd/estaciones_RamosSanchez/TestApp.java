package aadd.estaciones_RamosSanchez;


import java.util.Set;
import servicio.FactoriaServicios;
import sitiosTuristicos.modelo.SitioTuristico;
import estaciones.servicio.IServicioEstaciones;
import repositorio.EntidadNoEncontrada;

public class TestApp {

	private static IServicioEstaciones servicioEstaciones = FactoriaServicios.getServicio(IServicioEstaciones.class);
	
	public static void main(String[] args) throws EntidadNoEncontrada, Exception {
		
		String id = servicioEstaciones.altaEstacion("Madrid", 20, 30000, 38, -1);
		Set<SitioTuristico> s = servicioEstaciones.getSitiosTuristicosProximos(id);

		for(SitioTuristico st : s) {
			System.out.println(st.getNombre());
		}
	}

}
