package application;

import book.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@SpringBootApplication
public class RestClientApplication implements CommandLineRunner {
	@Autowired
	private RestOperations restTemplate;

	public static void main(String[] args) {
		SpringApplication.run(RestClientApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String bookServer = "http://localhost:8080/book";
		// add Book 123
		restTemplate.postForLocation(bookServer, new Book("123", "Sonny", "Lab 2 part 1", 12.99));

		// add Book 111
		restTemplate.postForLocation(bookServer, new Book("111", "Katy", "Lab 2 part 2", 31.99));

		// get Book 123 exist
		Book book123 = restTemplate.getForObject(bookServer + "/{bookIsbn}", Book.class, "123");
		System.out.println("----------- get Book 123 ------------------");
        assert book123 != null;
        System.out.println(book123.getIsbn()+","+book123.getTitle()+","+book123.getAuthor()+","+book123.getPrice());

		//get all Books
		ResponseEntity<Book[]> response = restTemplate.getForEntity(bookServer,Book[].class);
		System.out.println("----------- get all Books-----------------------");
		Book[] books = response.getBody();
		System.out.println(Arrays.toString(books));

		// delete Book 111
		restTemplate.delete(bookServer +"/{isbn}", "111");
		System.out.println("-----------delete Book 111--------------------");

		// update price for Book 123
		book123.setPrice(11.11);
		restTemplate.put(bookServer, book123);
		System.out.println("-----------update Book 123--------------------");

		response = restTemplate.getForEntity(bookServer,Book[].class);
		System.out.println("----------- get all Books again--------------------");
		books = response.getBody();
		System.out.println(Arrays.toString(books));
	}


	@Bean
	RestOperations restTemplate() {
		return new RestTemplate();
	}
}
