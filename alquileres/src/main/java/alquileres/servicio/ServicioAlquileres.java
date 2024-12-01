package alquileres.servicio;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeoutException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import alquileres.modelo.Alquiler;
import estaciones.servicio.ServicioEstaciones;
import rabbitmq.estaciones.JSONRabbitMQ;
import rabbitmq.estaciones.Productor;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.RepositorioException;
import reservas.modelo.Reserva;
import usuarios.modelo.Usuario;
import usuarios.repositorio.RepositorioUsuarioAdHoc;

public class ServicioAlquileres implements IServicioAlquileres {

	private final String URL_RABBITMQ = "amqps://fqzlqcrg:tbPHA76fsXqCKOBBPK07FZirB2N1k8PZ@stingray.rmq.cloudamqp.com/fqzlqcrg";
	private RepositorioUsuarioAdHoc repoUsuarios = FactoriaRepositorios.getRepositorio(Usuario.class);
	private Productor productor;
	private ServicioEstaciones servicioEstaciones = new ServicioEstaciones();

	public ServicioAlquileres() throws ServicioAlquileresException {
		try {
			productor = new Productor();
		} catch (Exception e) {
			throw new ServicioAlquileresException("rabbitmq: excepción al crear el bus del consumidor");
		}
		consumidor();
	}

	@Override
	public void reservar(String idUsuario, String idBicicleta) throws EntidadNoEncontrada, Exception {
		Usuario usuario = repoUsuarios.getById(idUsuario);
		if (usuario.reservaActiva() != null)
			throw new Exception("El usuario ya tiene una reserva activa.");
		if (usuario.alquilerActivo() != null)
			throw new Exception("El usuario ya tiene un alquiler activo.");
		if (usuario.bloqueado() || usuario.superaTiempo())
			throw new Exception("El usuario no puede hacer ninguna reserva.");

		Reserva reserva = new Reserva(idBicicleta);
		ArrayList<Reserva> reservas = usuario.getReservas();
		reservas.add(reserva);
		usuario.setReservas(reservas);
		repoUsuarios.update(usuario);
	}

	@Override
	public void confirmarReserva(String idUsuario) throws Exception {
		Usuario usuario = repoUsuarios.getById(idUsuario);
		if (usuario.reservaActiva() == null)
			throw new Exception("El usuario no tiene una reserva activa.");

		Alquiler alquiler = new Alquiler(usuario.reservaActiva().getIdBicicleta());
		ArrayList<Alquiler> alquileres = usuario.getAlquileres();
		alquileres.add(alquiler);
		usuario.setAlquileres(alquileres);
		usuario.getReservas().remove(usuario.reservaActiva());
		repoUsuarios.update(usuario);
	}

	@Override
	public void alquilar(String idUsuario, String idBicicleta) throws Exception {
		Usuario usuario = repoUsuarios.getById(idUsuario);
		if (usuario.reservaActiva() != null)
			throw new Exception("El usuario ya tiene una reserva activa.");
		if (usuario.alquilerActivo() != null)
			throw new Exception("El usuario ya tiene un alquiler activo.");
		if (usuario.bloqueado() || usuario.superaTiempo())
			throw new Exception("El usuario no tiene permitido alquilar.");

		Alquiler alquiler = new Alquiler(idBicicleta);
		ArrayList<Alquiler> alquileres = usuario.getAlquileres();
		alquileres.add(alquiler);
		usuario.setAlquileres(alquileres);
		repoUsuarios.update(usuario);
		productor.eventoBicicletaAlquilada(idBicicleta);
	}

	@Override
	public Usuario historialUsuario(String idUsuario) throws EntidadNoEncontrada, RepositorioException {
		return repoUsuarios.getById(idUsuario);
	}

	@Override
	public void dejarBicicleta(String idUsuario, String idEstacion)
			throws ServicioAlquileresException, RepositorioException, EntidadNoEncontrada {
		Usuario usuario = repoUsuarios.getById(idUsuario);

		Alquiler alquiler = usuario.alquilerActivo();
		alquiler.setFin(LocalDateTime.now());
		try {
			if (servicioEstaciones.tieneHuecoDisponible(idEstacion)
					&& servicioEstaciones.situarBicicleta(idEstacion, alquiler.getIdBicicleta())) {
				productor.eventoBicicletaAlquilerConcluido(alquiler.getIdBicicleta());
				alquiler.setFin(LocalDateTime.now());
			}
				

		} catch (IOException e) {
			throw new ServicioAlquileresException("El servicio Estaciones ha dado error");
		}
		repoUsuarios.update(usuario);
	}

	@Override
	public void liberarBloqueo(String idUsuario) throws EntidadNoEncontrada, RepositorioException {
		Usuario usuario = repoUsuarios.getById(idUsuario);
		ArrayList<Reserva> reservas = usuario.getReservas();
		Iterator<Reserva> iterator = reservas.iterator();
		while (iterator.hasNext()) {
			Reserva reserva = iterator.next();
			if (reserva.caducada()) {
				iterator.remove();
			}
		}

		usuario.setReservas(reservas);
		repoUsuarios.update(usuario);
	}

	public void consumidor() throws ServicioAlquileresException {
		ConnectionFactory factory = new ConnectionFactory();
		try {
			factory.setUri(URL_RABBITMQ);
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			boolean autoAck = false;
			channel.basicConsume("citybike-alquileres", autoAck, "arso-consumidor", new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
						byte[] body) throws IOException {
					// String routingKey = envelope.getRoutingKey(); No nos hace falta porque solo consumimos un tipo de mensaje
					long deliveryTag = envelope.getDeliveryTag();
					try {
						ObjectMapper objectMapper = new ObjectMapper();
						JSONRabbitMQ bicicletaDTO = objectMapper.readValue(new String(body), JSONRabbitMQ.class);
						String idBicicleta = bicicletaDTO.getId();

						List<Usuario> listaUsuarios = repoUsuarios.getAll();
						boolean flag = true;
						int numUsuario = 0;

						while (flag && numUsuario <= listaUsuarios.size()) {
							Usuario usuario = listaUsuarios.get(numUsuario);
							Reserva reserva = usuario.reservaActiva();
							if (reserva != null) {
								if (reserva.getIdBicicleta().equals(idBicicleta)) {
									usuario.borrarReservaActiva();
									repoUsuarios.update(usuario);
									flag = false;
								}
							}
							numUsuario++;
						}
					} catch (RepositorioException e) {
						e.printStackTrace();
					} catch (EntidadNoEncontrada e) {
						e.printStackTrace();
					}

					channel.basicAck(deliveryTag, false);
				}
			});
		} catch (Exception e) {
			throw new ServicioAlquileresException("rabbitmq: excepción al crear el bus del consumidor");
		}
	}
}
