package sonny.books.BookApplication.Data;

import sonny.books.BookApplication.Domain.Book;

import java.util.Collection;

public interface IBookRepository {
    public boolean addBook(Book book);
    public Book deleteBook(String isbn);
    public Book getBook(String isbn);
    public Collection<Book> getAllBooks();
}
