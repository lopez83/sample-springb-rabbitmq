package com.lopez83.springb.rabbitmq.consumer;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.lopez83.springb.rabbitmq.RegisterQueues;
import com.lopez83.springb.rabbitmq.model.MyMessage;

/**
 * @author Oscar Lopez - <lopez83@gmail.com>
 * 
 * Queues listeners using RabbitListener annotation
 */
@Component
public class QueueListener {

	private static Logger log = LoggerFactory.getLogger(QueueListener.class);
	private CountDownLatch latch = new CountDownLatch(1);

	@Autowired
	Gson gson;

	/**
	 * Listener for Info queue
	 * 
	 * @param message The message consumed from the queue
	 */
	@RabbitListener(queues = "info-queue")
	public void receiveInfoMessage(Message message) {
		MyMessage myMessage = gson.fromJson(new String(message.getBody()),
				MyMessage.class);

		log.info("Received msg on queue "
				+ RegisterQueues.RoutingKeys.INFO.getValue() + ": "
				+ myMessage.toString());
		latch.countDown();
	}

	/**
	 * Listener for Error queue
	 * 
	 * @param message The message consumed from the queue
	 */
	@RabbitListener(queues = "error-queue")
	public void receiveErrorMessage(Message message) throws JsonParseException,
			JsonMappingException, IOException {
		MyMessage myMessage = gson.fromJson(new String(message.getBody()),
				MyMessage.class);
		log.error("Received msg on queue "
				+ RegisterQueues.RoutingKeys.ERROR.getValue() + ": "
				+ myMessage.toString());
		latch.countDown();
	}
}