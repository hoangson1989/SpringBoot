package edu.miu.rabbitpublisher;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@EnableRabbit
@SpringBootApplication
public class RabbitPublisherApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitPublisherApplication.class, args);
    }

    @Bean
    public Queue hiQueue1() {
        return new Queue("q-hi-1", true);
    }

    @Bean
    public Queue hiQueue2() {
        return new Queue("q-hi-2", true);
    }

    @Bean
    FanoutExchange hiFanoutExchange() {
        return new FanoutExchange("hi-fanout-exchange");
    }

    @Bean
    Binding hiQueue1Binding(Queue hiQueue1, FanoutExchange hiFanoutExchange) {
        return BindingBuilder.bind(hiQueue1).to(hiFanoutExchange);
    }

    @Bean
    Binding hiQueue2Binding(Queue hiQueue2, FanoutExchange hiFanoutExchange) {
        return BindingBuilder.bind(hiQueue2).to(hiFanoutExchange);
    }


    @Bean
    public Queue directQ1() {
        return new Queue("q-direct-1", true);
    }

    @Bean
    public Queue directQ2() {
        return new Queue("q-direct-2", true);
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange("direct-exchange");
    }

    @Bean
    Binding directQ1Binding(Queue directQ1, DirectExchange directExchange) {
        return BindingBuilder.bind(directQ1).to(directExchange).with("q1-test");
    }

    @Bean
    Binding directQ2Binding(Queue directQ2, DirectExchange directExchange) {
        return BindingBuilder.bind(directQ2).to(directExchange).with("q2-test");
    }



}

