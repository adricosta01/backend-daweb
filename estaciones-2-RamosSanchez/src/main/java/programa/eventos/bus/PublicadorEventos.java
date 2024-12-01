package programa.eventos.bus;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import programa.eventos.config.RabbitMQConfig;

@Component
public class PublicadorEventos {

	@Autowired
	RabbitTemplate rabbitTemplate;
	
	public void bicicletaDesactivada(Object evento) {
		rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME,
				"citybike.estaciones.bicicleta-desactivada",
				evento);
	}
}
