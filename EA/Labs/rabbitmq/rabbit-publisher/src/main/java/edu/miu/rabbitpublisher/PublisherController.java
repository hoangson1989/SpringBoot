package edu.miu.rabbitpublisher;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/publisher")
public class PublisherController {

    private final RabbitTemplate rabbitTemplate;

    @GetMapping("/fanout")
    public void publishToFanout(){
        rabbitTemplate.convertAndSend("hi-fanout-exchange",
                "",
                "Hi . . . " + LocalDateTime.now());
    }

    @GetMapping("/direct/q1")
    public void publishToDirectQ1(){
        rabbitTemplate.convertAndSend("direct-exchange",
                "q1-test",
                "Hi from direct 1:    " + LocalDateTime.now());
    }

    @GetMapping("/direct/q2")
    public void publishToDirectQ2(){
        rabbitTemplate.convertAndSend("direct-exchange",
                "q2-test",
                "Hi from direct 2:   " + LocalDateTime.now());
    }


}
