package com.lopez83.springb.rabbitmq.publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lopez83.springb.rabbitmq.RegisterQueues;
import com.lopez83.springb.rabbitmq.model.MyMessage;

/**
 * @author Oscar Lopez - <lopez83@gmail.com>
 * 
 * Publisher component exposing to methods to publish messages on rabbitmq
 */
@Component
public class Publisher {
   
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * Publish a message on a specific routing key
     * 
     * @param routingKey
     * @param level
     * @param text
     */
    public void publish(String routingKey, String level, String text) {
    	MyMessage msg = new MyMessage(level,text);
    	rabbitTemplate.convertAndSend(routingKey, msg);
    }
    
    /**
     * Publish same message on every routing key defined on RoutingKeys
     * 
     * @param text
     */
    public void publishToAllQueues(String text){
    	for (RegisterQueues.RoutingKeys routingKeyE : RegisterQueues.RoutingKeys.values()) {
			String routingKeyValue = routingKeyE.getValue();
			MyMessage msg = new MyMessage("ALL",text);
			rabbitTemplate.convertAndSend(routingKeyValue, msg);
		}
    }
}