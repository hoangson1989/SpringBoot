package sonny.books.BookApplication.Data;

import org.springframework.stereotype.Repository;
import sonny.books.BookApplication.Domain.Book;

import java.util.Collection;
import java.util.HashMap;

@Repository
public class BookRepositoryImpl implements IBookRepository {
    private final HashMap<String, Book> books = new HashMap<>();
    @Override
    public boolean addBook(Book book) {
        books.put(book.getIsbn(), book);
        return true;
    }

    @Override
    public Book deleteBook(String isbn) {
        return books.remove(isbn);
    }

    @Override
    public Book getBook(String isbn) {
        return books.get(isbn);
    }

    @Override
    public Collection<Book> getAllBooks() {
        return books.values();
    }
}
