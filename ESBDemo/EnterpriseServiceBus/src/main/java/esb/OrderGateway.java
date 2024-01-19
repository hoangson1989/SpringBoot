package esb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

@MessagingGateway
public class OrderGateway {
    @Autowired
    @Qualifier("warehousechannel")
    MessageChannel warehouseChannel;

    public void sendOrder(Order order) {
        Message<Order> orderMessage = MessageBuilder.withPayload(order).build();
        warehouseChannel.send(orderMessage);
    }
}


//@MessagingGateway
//public interface OrderGatewaya {
//     @Gateway(requestChannel = "warehousechannel")
//     String sendOrder(Message<Order> message);
//}