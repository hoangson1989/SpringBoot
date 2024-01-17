package sonny.books.BookApplication.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sonny.books.BookApplication.Domain.Book;
import sonny.books.BookApplication.Domain.CustomErrorType;
import sonny.books.BookApplication.Service.IBookService;

import java.util.Collection;

@RestController
public class BookController {

    @Autowired
    private IBookService bookService;

    @PostMapping("/book")
    public ResponseEntity<?> addBook(@RequestBody Book book) {
        bookService.addBook(book);
        return new ResponseEntity<Book>(book, HttpStatus.OK);
    }

    @GetMapping("/book/{bookIsbn}")
    public ResponseEntity<?> getBook(@PathVariable String bookIsbn) {
        Book book = bookService.getBook(bookIsbn);
        if (book != null) {
            return new ResponseEntity<Book>(book, HttpStatus.OK);
        } else {
            CustomErrorType errorType = new CustomErrorType("No Book found with Isbn: " + bookIsbn);
            return new ResponseEntity<CustomErrorType>(errorType, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/book")
    public Collection<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PutMapping("/book")
    public ResponseEntity<?> updateBook(@RequestBody Book newBook) {
        Book book = bookService.getBook(newBook.getIsbn());
        if (book != null) {
            bookService.addBook(newBook);
            return new ResponseEntity<Book>(newBook, HttpStatus.OK);
        } else {
            CustomErrorType errorType = new CustomErrorType("No Book found with Isbn: " + newBook.getIsbn());
            return new ResponseEntity<CustomErrorType>(errorType, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/book/{bookIsbn}")
    public ResponseEntity<?> deleteBook(@PathVariable String bookIsbn) {
        Book book = bookService.getBook(bookIsbn);
        if (book != null) {
            bookService.deleteBook(bookIsbn);
            return new ResponseEntity<Book>(HttpStatus.NO_CONTENT);
        } else {
            CustomErrorType errorType = new CustomErrorType("No Book found with Isbn: " + bookIsbn);
            return new ResponseEntity<CustomErrorType>(errorType, HttpStatus.NOT_FOUND);
        }
    }
}
