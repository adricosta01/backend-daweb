package rabbitmq.estaciones;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import usuarios.modelo.Usuario;

public class Productor {
	
	private Channel channel;
	
	public Productor() throws KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUri("amqps://fqzlqcrg:tbPHA76fsXqCKOBBPK07FZirB2N1k8PZ@stingray.rmq.cloudamqp.com/fqzlqcrg");
		Connection connection = factory.newConnection();
		channel = connection.createChannel();
	}
	
	public void eventoBicicletaAlquilada(String idBicicleta) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(new JSONRabbitMQ(idBicicleta));
		channel.basicPublish("citybike","citybike.alquileres.bicicleta-alquilada",
				new AMQP.BasicProperties.Builder()
				.contentType("application/json")
				.build(),
				json.getBytes());
	}
	
	public void eventoBicicletaAlquilerConcluido(String idBicicleta) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(new JSONRabbitMQ(idBicicleta));
		channel.basicPublish("citybike","citybike.alquileres.bicicleta-alquiler-concluido",
				new AMQP.BasicProperties.Builder()
				.contentType("application/json")
				.build(),
				json.getBytes());
	}
}
