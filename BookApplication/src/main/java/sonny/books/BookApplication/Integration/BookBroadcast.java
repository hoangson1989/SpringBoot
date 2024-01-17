package sonny.books.BookApplication.Integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import sonny.books.BookApplication.Domain.Book;

@Component
public class BookBroadcast {
    @Autowired
    JmsTemplate jmsTemplate;
    public void sendBookMessage(Book book,String queue) {
        try {
            //convert person to JSON string
            ObjectMapper objectMapper = new ObjectMapper();
            String bookString = objectMapper.writeValueAsString(book);
            System.out.println("Sending a JMS message:" + bookString);
            jmsTemplate.convertAndSend(queue, bookString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("sent message.");
    }
}
