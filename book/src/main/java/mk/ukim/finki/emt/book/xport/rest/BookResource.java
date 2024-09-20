package mk.ukim.finki.emt.book.xport.rest;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.book.domain.model.Book;
import mk.ukim.finki.emt.book.domain.model.BookId;
import mk.ukim.finki.emt.book.domain.model.Category;
import mk.ukim.finki.emt.book.domain.model.Status;
import mk.ukim.finki.emt.book.service.BookService;
import mk.ukim.finki.emt.book.service.forms.BookForm;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Currency;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class BookResource {

    private final BookService bookService;


    @GetMapping
    public List<Book> getAll(@RequestParam (required = false) Category category){
        if (category != null)
            return bookService.filterByCategory(category);
        return bookService.getAll();
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable BookId id){
        Book book= this.bookService.findById(id);
        return book;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createBook(@RequestBody BookForm bookForm) {
        if(bookForm == null) {
            return ResponseEntity.notFound().build();
        }

        this.bookService.createBook(bookForm);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/categories")
    public ResponseEntity<Category[]> getCategories(){
        Category[] categories = Category.values();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/status")
    public ResponseEntity<Status[]> getStatus(){
        Status[] statuses = Status.values();
        return ResponseEntity.ok(statuses);
    }

    @GetMapping("/currency")
    public ResponseEntity<Currency[]> getCurrency(){
        Currency[] currencies = Currency.values();
        return ResponseEntity.ok(currencies);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable BookId id) {
        if(id == null) {
            return ResponseEntity.notFound().build();
        }

        if(bookService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }

        this.bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }

}
