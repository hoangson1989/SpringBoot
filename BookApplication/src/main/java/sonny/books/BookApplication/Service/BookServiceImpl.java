package sonny.books.BookApplication.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sonny.books.BookApplication.Data.IBookRepository;
import sonny.books.BookApplication.Domain.Book;
import sonny.books.BookApplication.Integration.BookBroadcast;

import java.util.Collection;

@Service
public class BookServiceImpl implements IBookService {

    @Autowired
    private IBookRepository bookRepo;

    @Autowired
    private BookBroadcast bookSender;

    @Override
    public boolean addBook(Book book) {
        bookSender.sendBookMessage(book,"addQueue");
        return bookRepo.addBook(book);
    }

    @Override
    public boolean updateBook(Book book) {
        bookSender.sendBookMessage(book,"updateQueue");
        return bookRepo.addBook(book);
    }

    @Override
    public Book deleteBook(String isbn) {
        Book b = bookRepo.deleteBook(isbn);
        bookSender.sendBookMessage(b,"deleteQueue");
        return b;
    }

    @Override
    public Book getBook(String isbn) {
        Book b = bookRepo.getBook(isbn);
        return b;
    }

    @Override
    public Collection<Book> getAllBooks() {
        return bookRepo.getAllBooks();
    }
}
