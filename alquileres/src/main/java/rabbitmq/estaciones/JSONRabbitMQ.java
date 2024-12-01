package rabbitmq.estaciones;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JSONRabbitMQ {
	
	private String idBicicleta;
	
	public JSONRabbitMQ() {
		
	}
	
	public JSONRabbitMQ(String idBicicleta) {
		this.idBicicleta = idBicicleta;
	}

	public String getId() {
		return idBicicleta;
	}

	public void setId(String idBicicleta) {
		this.idBicicleta = idBicicleta;
	}
	
}
