package edu.miu.cs.wdfs.integration;

import edu.miu.cs.wdfs.model.NewAPIPayload;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class Receiver {

    // This class defines a Kafka message receiver that listens to a specific topic.

    @KafkaListener(topics = "${app.topic.newApiTopic}")
    // The @KafkaListener annotation specifies the topic to listen to.

    public void receive(@Payload NewAPIPayload newAPIPayload, @Headers MessageHeaders headers) {
        // This method is called when a message is received from the specified Kafka topic.

        System.out.println("received message=" + newAPIPayload.toString());
        // Print the received message to the console.

        headers.keySet().forEach(key -> System.out.println(key + " : " + headers.get(key)));
        // Print the message headers to the console.
    }
}
