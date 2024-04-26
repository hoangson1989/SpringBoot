package edu.miu.rabbitlistener2;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class RabbitListener2Application {

	public static void main(String[] args) {
		SpringApplication.run(RabbitListener2Application.class, args);
	}

	@RabbitListener(queues = {"q-hi-2"})
	public void bindToHiQueue2(String payload) {
		System.out.println(payload);
	}

	@RabbitListener(queues = {"q-direct-2"})
	public void bindToDirectQueue1(String payload) {
		System.out.println(payload);
	}

}
