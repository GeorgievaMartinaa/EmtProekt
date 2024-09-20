package mk.ukim.finki.emt.book.service;

import mk.ukim.finki.emt.book.domain.model.Book;
import mk.ukim.finki.emt.book.domain.model.BookId;
import mk.ukim.finki.emt.book.domain.model.Category;
import mk.ukim.finki.emt.book.domain.model.Status;
import mk.ukim.finki.emt.book.service.forms.BookForm;

import java.util.List;

public interface BookService {
    Book findById(BookId id);
    Book createBook(BookForm form);
    List<Book> getAll();
    Book bookOrdered(BookId bookId, int quantity);
    void deleteBook(BookId bookId);
    List<Book> filterByCategory(Category category);



}
