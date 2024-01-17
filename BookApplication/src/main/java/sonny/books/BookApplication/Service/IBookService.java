package sonny.books.BookApplication.Service;

import sonny.books.BookApplication.Domain.Book;

import java.util.Collection;

public interface IBookService {
   public boolean addBook(Book book);
   public boolean updateBook(Book book);
   public Book deleteBook(String isbn);
   public Book getBook(String isbn);
   public Collection<Book> getAllBooks();
}
