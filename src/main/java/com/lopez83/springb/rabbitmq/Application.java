
package com.lopez83.springb.rabbitmq;


import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.lopez83.springb.rabbitmq.RegisterQueues.RoutingKeys;
import com.lopez83.springb.rabbitmq.publisher.Publisher;

/**
 * @author Oscar Lopez - <lopez83@gmail.com>
 * Boot Application
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

	private static Logger log = LoggerFactory.getLogger(Application.class);
	
	@Autowired
	AnnotationConfigApplicationContext context;

	@Autowired
	Publisher msgSender;		
	
	@Autowired 
	RegisterQueues registerQueues;		
	
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {    	
    	registerQueues.configTemplate();
    	log.info("Waiting for queues to be created before sending a msg");
    	Thread.sleep(3000);    	    	
    	log.info("Sending messages...");
    	msgSender.publish(RoutingKeys.INFO.getValue(), "INFO", "info message description time:"+new Timestamp(System.currentTimeMillis()));
        msgSender.publish(RoutingKeys.ERROR.getValue(), "ERROR", "error message description time:"+new Timestamp(System.currentTimeMillis()));
        msgSender.publishToAllQueues("broadcast msg time:"+new Timestamp(System.currentTimeMillis()));
        context.close();
        log.info("Done");
    }
}