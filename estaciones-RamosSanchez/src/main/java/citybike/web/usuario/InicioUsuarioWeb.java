package citybike.web.usuario;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import citybike.web.bicicleta.OperacionesBicicletaWeb;
import citybike.web.incidencia.OperacionesIncidenciaWeb;
import citybike.web.incidenciasabiertas.OperacionesIncidenciaAbiertaWeb;

@Named
@javax.enterprise.context.SessionScoped
public class InicioUsuarioWeb implements Serializable {

	private String nombre;
	private String contraseña;
	private String tipo;
	private Map<String, String> tipos;
	@Inject
	protected FacesContext facesContext;
	@Inject
	private OperacionesIncidenciaWeb operacionesIncidenciaWeb;
	@Inject
    private OperacionesBicicletaWeb operacionesBicicletaWeb;
	@Inject
    private OperacionesIncidenciaAbiertaWeb operacionesIncidenciaGestorWeb;
	protected ResourceBundle bundle;

	public InicioUsuarioWeb() {
		tipos = new HashMap<>();
		tipos.put("cliente", "cliente");
		tipos.put("gestor", "gestor");
	}

	@PostConstruct
	public void initBundle() {
		bundle = ResourceBundle.getBundle("i18n.text", facesContext.getViewRoot().getLocale());
	}

	public String getNombre() {
		return nombre;
	}

	public String getContraseña() {
		return contraseña;
	}

	public Map<String, String> getTipos() {
		return tipos;
	}

	public String getTipo() {
		return tipo;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public void setTipos(Map<String, String> tipos) {
		this.tipos = tipos;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void iniciarSesion() {
		if(this.nombre.equals("") || this.contraseña.equals("")) {
			FacesMessage mensajeError = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"Nombre de usuario y/o contraseña sin completar.");
			FacesContext.getCurrentInstance().addMessage("form:messages", mensajeError);
		}else {			
			try {
				if (this.tipo.equals("cliente"))
					facesContext.getExternalContext().redirect("cliente/inicioCliente.xhtml");
				else
					facesContext.getExternalContext().redirect("gestor/inicioGestor.xhtml");
	
			} catch (IOException e) {
	
				facesContext.addMessage(null,
	
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se ha podido navegar"));
	
				e.printStackTrace();
	
			}
		}
	}

	public String getUrlInicioBicicleta() {
		if (this.tipo == null || this.tipo.equals(""))
			return "";
		else if (this.tipo.equals("cliente"))
			return "/cliente/inicioCliente.xhtml";
		else
			return "/gestor/inicioGestor.xhtml";
	}

	public String getUrlActionButton1() {
		if (this.tipo == null || this.tipo.equals(""))
			return "";
		else if (this.tipo.equals("cliente"))
			return "/cliente/buscadorBicis.xhtml";
		else
			return "/gestor/incidenciasAbiertas.xhtml";
	}

	public String getTextActionButton1() {
		if (this.tipo == null || this.tipo.equals(""))
			return "null";
		else if (this.tipo.equals("cliente"))
			return "buscadorBici";
		else
			return "verIncidenciasAbiertas";
	}

	public String getUrlActionButton2() {
		if (this.tipo == null || this.tipo.equals(""))
			return "";
		else if (this.tipo.equals("cliente")) {
			if (operacionesIncidenciaWeb.getBicicleta() == null)
				return "";
			else
				return "/cliente/crearIncidencia.xhtml";
		} else {
			if(operacionesIncidenciaGestorWeb.getIncidenciaSeleccionada() == null) {
				return "";
			}
			else
				return "/gestor/gestionarIncidencia.xhtml";
		}
	}

	public String getTextActionButton2() {
		if (this.tipo == null || this.tipo.equals(""))
			return "null";
		else if (this.tipo.equals("cliente"))
			return "crearIncidencia";
		else
			return "gestionarIncidencia";
	}

	public void cerrarSesion() {
		this.tipo = "";
		this.nombre = "";
		this.contraseña = "";
		operacionesBicicletaWeb.setBicicletaSeleccionada(null);
		operacionesIncidenciaGestorWeb.setIncidenciaSeleccionada(null);
		operacionesIncidenciaGestorWeb.setDescripcion(null);
		try {
			facesContext.getExternalContext().redirect("/index.xhtml");
		} catch (IOException e) {

			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se ha podido navegar"));
			e.printStackTrace();
		}
	}
}
