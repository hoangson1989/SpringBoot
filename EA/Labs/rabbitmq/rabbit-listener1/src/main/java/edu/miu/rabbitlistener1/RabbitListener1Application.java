package edu.miu.rabbitlistener1;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class RabbitListener1Application {

	public static void main(String[] args) {
		SpringApplication.run(RabbitListener1Application.class, args);
	}


	@RabbitListener(queues = {"q-hi-1"})
	public void bindToHiQueue1(String payload) {
		System.out.println(payload);
	}

	@RabbitListener(queues = {"q-direct-1"})
	public void bindToDirectQueue1(String payload) {
		System.out.println(payload);
	}

}
