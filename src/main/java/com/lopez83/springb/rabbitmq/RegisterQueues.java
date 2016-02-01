package com.lopez83.springb.rabbitmq;

import java.util.Arrays;
import java.util.List;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author Oscar Lopez - <lopez83@gmail.com>
 *
 * Responsible of creating the exchange of topic type, defining the routing keys
 * and binding the routing keys to the exchange.
 * 
 */
@Component
public class RegisterQueues {

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Bean
	TopicExchange exchange() {
		return new TopicExchange("my-exchange");
	}
	
    @Bean
    public MessageConverter jsonMessageConverter(){
        return new JsonMessageConverter();
    }
    
    /**
     * Set the message converter to the rabbitTemplate to convert objects to json
     */
	public void configTemplate() {
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
	}

	/**
	 * Defines the routing keys to be used
	 *
	 */
	public enum RoutingKeys {

		INFO("info-queue"), ERROR("error-queue");

		private String value;

		RoutingKeys(String setValue) {
			value = setValue;
		}

		public String getValue() {
			return value;
		}
	}
	
	/**
	 * Instantiate the queues
	 * @return
	 */
	@Bean
	public List<Queue> qs() {
		return Arrays.asList(
				new Queue(RoutingKeys.INFO.getValue(), true),
				new Queue(RoutingKeys.ERROR.getValue(), true));
	}

	/**
	 * Bind each queue to the exchange
	 * @return
	 */
	@Bean
	public List<Binding> bs() {
		return Arrays.asList(
				BindingBuilder.bind(qs().get(0)).to(exchange()).with(qs().get(0).getName()), 
				BindingBuilder.bind(qs().get(1)).to(exchange()).with(qs().get(1).getName()));
	}

}
