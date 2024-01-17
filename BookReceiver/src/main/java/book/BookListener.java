package book;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class BookListener {

    @JmsListener(destination = "addQueue")
    public void receiveMessage(final String bookString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Book book = objectMapper.readValue(bookString, Book.class);
            System.out.println("JMS receiver received add message:" + book);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @JmsListener(destination = "updateQueue")
    public void receiveUpdateMessage(final String bookString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Book book = objectMapper.readValue(bookString, Book.class);
            System.out.println("JMS receiver received update message:" + book);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @JmsListener(destination = "deleteQueue")
    public void receiveDeleteMessage(final String bookString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Book book = objectMapper.readValue(bookString, Book.class);
            System.out.println("JMS receiver received delete message:" + book);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
