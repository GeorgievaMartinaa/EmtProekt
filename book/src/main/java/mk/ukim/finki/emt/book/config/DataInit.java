package mk.ukim.finki.emt.book.config;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.emt.book.domain.model.Book;
import mk.ukim.finki.emt.book.domain.model.Category;
import mk.ukim.finki.emt.book.domain.model.Status;
import mk.ukim.finki.emt.book.domain.repository.BookRepository;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Currency;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInit {

    private final BookRepository bookRepository;

    public DataInit(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostConstruct
    public void initData(){
        Book b1 = Book.build("Book1","Desc",0, Money.valueOf(Currency.MKD,350), Status.Available, Category.Mystery,100);
        Book b2 = Book.build("Book2","Desc2",10, Money.valueOf(Currency.MKD,500), Status.Available, Category.Drama,150);
        Book b3 = Book.build("Book3","Desc3",5, Money.valueOf(Currency.MKD,1000), Status.Available, Category.Biography,200);
        Book b4 = Book.build("Book4","Desc4",0, Money.valueOf(Currency.MKD,500), Status.Available, Category.Biography,250);


        if(bookRepository.findAll().isEmpty())
            bookRepository.saveAll(Arrays.asList(b1,b2,b3,b4));

    }
}
