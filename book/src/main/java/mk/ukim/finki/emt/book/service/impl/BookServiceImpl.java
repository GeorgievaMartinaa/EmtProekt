package mk.ukim.finki.emt.book.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.book.domain.exeptions.BookNotExistsExeption;
import mk.ukim.finki.emt.book.domain.exeptions.BookNotFoundExeption;
import mk.ukim.finki.emt.book.domain.model.Book;
import mk.ukim.finki.emt.book.domain.model.BookId;
import mk.ukim.finki.emt.book.domain.model.Category;
import mk.ukim.finki.emt.book.domain.model.Status;
import mk.ukim.finki.emt.book.domain.repository.BookRepository;
import mk.ukim.finki.emt.book.service.BookService;
import mk.ukim.finki.emt.book.service.forms.BookForm;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public Book findById(BookId id) {
        return bookRepository.findById(id).orElseThrow(BookNotExistsExeption::new);
    }

    @Override
    public Book createBook(BookForm form) {
        Book book = Book.build(form.getName(), form.getDescription(), form.getDiscount(),Money.valueOf(form.getCurrency(),form.getPrice()),form.getStatus(),form.getCategory(), form.getBookCount());

        bookRepository.save(book);

        return book;
    }

    @Override
    public Book bookOrdered(BookId bookId, int quantity) {
       // System.out.println("Koja kniga se poracuva "+bookId+" i vo kolkava kolicina "+ quantity);
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundExeption::new);
      //  System.out.println("Knigata koja se poracuva e:  " + book.getName() + "Ima bookCount "+ book.getBookCount());
        book.orderBook(quantity);
        bookRepository.saveAndFlush(book);
        return book;
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public void deleteBook(BookId bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    public List<Book> filterByCategory(Category category) {
        List<Book> books = this.getAll();
        List<Book> filteredBooks = new ArrayList<>();

        for(Book b : books){
            if(b.getCategory() == category)
                filteredBooks.add(b);
        }
        if(filteredBooks.isEmpty())
            System.out.println("Sorry, we couldn't find book with category  " + category);

        return filteredBooks;
    }

}
