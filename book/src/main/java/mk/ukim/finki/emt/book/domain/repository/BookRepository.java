package mk.ukim.finki.emt.book.domain.repository;

import mk.ukim.finki.emt.book.domain.model.Book;
import mk.ukim.finki.emt.book.domain.model.BookId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,BookId> {
}
