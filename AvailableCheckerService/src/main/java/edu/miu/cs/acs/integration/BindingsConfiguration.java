package edu.miu.cs.acs.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.cs.acs.database.URLRepository;
import edu.miu.cs.acs.models.ApiPayload;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.Base64;
import java.util.function.Consumer;

@Log4j2
@AllArgsConstructor
@Configuration
public class BindingsConfiguration {

    private MessageGateway messageGateway;
    @Autowired
    URLRepository urlRepository;

    @Bean
    public Consumer<Message<ApiPayload>> newDataConsumer() {
        return inMessage -> {
            log.info("[ACS-newDataConsumer] received message: {}", inMessage);
            String url = inMessage.getPayload().getUrl();
            if (url != null) {
                String storedURl = urlRepository.findById(url).orElseGet(ApiPayload::new).getUrl();
                if (storedURl == null || !storedURl.equals(url)) {
                    messageGateway.sendToInputChannel(inMessage);
                }
            }
        };
    }

    private void saveURL(ApiPayload payload) {
        String url = payload.getUrl();
        if (url != null) {
            String storedURl = urlRepository.findById(url).orElseGet(ApiPayload::new).getUrl();
            if (storedURl == null || !storedURl.equals(url)) {
                urlRepository.save(payload);
            }
        }
    }

    @Bean
    public Consumer<Message<ApiPayload>> unavailableDataConsumer() {
        return inMessage -> {
            log.info("[ACS-unavailableDataConsumer] received message: {}", inMessage);
            Object obj = inMessage.getPayload();
            String encode = obj.toString().replace("\"","");
            byte[] decodedBytes = Base64.getDecoder().decode( encode);
            String jsonStr = new String(decodedBytes);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                ApiPayload payload = objectMapper.readValue(jsonStr, ApiPayload.class);
                saveURL(payload);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Bean
    public Consumer<Message<ApiPayload>> freeDataConsumer() {
        return inMessage -> {
            log.info("[ACS-freeDataConsumer] received message: {}", inMessage);
            Object obj = inMessage.getPayload();
            String encode = obj.toString().replace("\"","");
            byte[] decodedBytes = Base64.getDecoder().decode( encode);
            String jsonStr = new String(decodedBytes);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                ApiPayload payload = objectMapper.readValue(jsonStr, ApiPayload.class);
                saveURL(payload);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
