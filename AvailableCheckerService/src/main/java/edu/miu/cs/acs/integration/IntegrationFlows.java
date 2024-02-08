package edu.miu.cs.acs.integration;

import edu.miu.cs.acs.database.URLRepository;
import edu.miu.cs.acs.domain.controlflow.BusinessOrchestrator;
import edu.miu.cs.acs.models.ApiPayload;
import edu.miu.cs.acs.models.ApiTestStatus;
import edu.miu.cs.acs.models.CheckedApiMessage;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.router.HeaderValueRouter;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

/**
 * This class is used to define all the service activators that handle the output and input channels messages
 */
import java.util.Objects;

@Log4j2
@AllArgsConstructor
@Configuration
public class IntegrationFlows {

    private StreamBridge streamBridge;
    private IntegrationProperties integrationProperties;
    private BusinessOrchestrator businessOrchestrator;
    @Autowired
    URLRepository urlRepository;
    /**
     * Input channel handler
     * @param inputMessage
     * @return inputMessage
     */
    @ServiceActivator(inputChannel = Channels.INPUT_CHANNEL, outputChannel = Channels.ROUTING_CHANNEL)
    public Message<ApiPayload> processInput(Message<ApiPayload> inputMessage) {
        log.info("[ACS-processInput] processing new message: {}", inputMessage);
        String url = inputMessage.getPayload().getUrl();

        ApiPayload newPayload = inputMessage.getPayload();
        newPayload.setStatus("USED");

        Message<ApiPayload> outMessage = MessageBuilder
                .withPayload(newPayload)
                .copyHeaders(inputMessage.getHeaders())
                .build();

        ApiTestStatus testStatus = businessOrchestrator.testApi(url).getTestStatus();
        ServiceLine serviceLine;
        switch (testStatus) {
            case SUCCESSFUL -> serviceLine = ServiceLine.SUCCESSFUL;
            case UNAUTHORIZED -> serviceLine = ServiceLine.UNAUTHORIZED;
            default -> serviceLine = ServiceLine.FAILED;
        }
        return MessageBuilder
                .fromMessage(outMessage)
                .setHeader(HeaderUtils.SERVICE_LINE, serviceLine.getValue()).build();
    }

    /**
     * handles APIs that are free and dont require a key
     * @param inputMessage
     * @return
     */
    @ServiceActivator(inputChannel = Channels.UNAUTHORIZED_API_CHANNEL, outputChannel = Channels.ROUTING_CHANNEL)
    public Message<ApiPayload> processUnauthorizedApi(Message<ApiPayload> inputMessage) {
        log.info("[ACS-processUnauthorizedApi] processing api message: {}", inputMessage);

        String url = inputMessage.getPayload().getUrl();
        CheckedApiMessage checkedApiMessage = businessOrchestrator.tryToExtractKey(url);
        ApiTestStatus testStatus = checkedApiMessage.getTestStatus();
        ServiceLine serviceLine;

        ApiPayload apiInfo = inputMessage.getPayload();
        if (Objects.requireNonNull(testStatus) == ApiTestStatus.SUCCESSFUL_AUTHORIZED) {
            serviceLine = ServiceLine.SUCCESSFUL;
            apiInfo.setApiKey(checkedApiMessage.getApiKey());
            apiInfo.setType("FREE");
        } else {
            serviceLine = ServiceLine.FAILED;
            apiInfo.setType("UNCERTAIN");
            apiInfo.setStatus("unauthorized");
        }
        return MessageBuilder
                .withPayload(apiInfo)
                .copyHeaders(inputMessage.getHeaders())
                .setHeader(HeaderUtils.SERVICE_LINE, serviceLine.getValue())
                .build();
    }

    /**
     * Message Header based routing
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = Channels.ROUTING_CHANNEL)
    public HeaderValueRouter router() {
        HeaderValueRouter router = new HeaderValueRouter(HeaderUtils.SERVICE_LINE);
        router.setChannelMapping(ServiceLine.SUCCESSFUL.getValue(), ServiceLine.SUCCESSFUL.getChannel());
        router.setChannelMapping(ServiceLine.FAILED.getValue(), ServiceLine.FAILED.getChannel());
        router.setChannelMapping(ServiceLine.UNAUTHORIZED.getValue(), ServiceLine.UNAUTHORIZED.getChannel());
        return router;
    }

    @ServiceActivator(inputChannel = Channels.SUCCESSFUL_API_CHANNEL)
    public void sendSuccessfulApiMessage(Message<ApiPayload> message) {
        //
        ApiPayload newPayload = message.getPayload();
        newPayload.setType("FREE");
        urlRepository.save(newPayload);

        Message<ApiPayload> outMessage = MessageBuilder
                .withPayload(newPayload)
                .copyHeaders(message.getHeaders())
                .build();
        //
        streamBridge.send(integrationProperties.getSuccessDestination(), outMessage);
        acknowledge(outMessage);
        log.info("[ACS-sendSuccessfulApiMessage] Sent message to freeAPI topic {}. Message = {}", integrationProperties.getSuccessDestination(), message);
    }

    @ServiceActivator(inputChannel = Channels.FAILED_API_CHANNEL)
    public void sendFailedApiMessage(Message<ApiPayload> message) {
        //
        ApiPayload newPayload = message.getPayload();
        newPayload.setType("UNAVAILABLE");
        urlRepository.save(newPayload);

        Message<ApiPayload> outMessage = MessageBuilder
                .withPayload(newPayload)
                .copyHeaders(message.getHeaders())
                .build();
        //
        streamBridge.send(integrationProperties.getFailedDestination(), outMessage);
        acknowledge(outMessage);
        log.info("[ACS-sendFailedApiMessage] Sent message to uncertainAPI {}. Message = {}", integrationProperties.getFailedDestination(), message);
    }

    private void acknowledge(Message<?> message) {
        Acknowledgment acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
        if (acknowledgment != null) {
            acknowledgment.acknowledge();
            log.debug("Acknowledged message: {}", message);
        }
    }
}
