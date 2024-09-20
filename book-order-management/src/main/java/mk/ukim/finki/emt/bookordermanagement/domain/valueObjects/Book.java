package mk.ukim.finki.emt.bookordermanagement.domain.valueObjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;

@Getter
public class Book implements ValueObject {
    private final BookId id;
    private final String name;
    private final Money price;
    private final int bookCount;


    @JsonCreator
    public Book(BookId id, String name, Money price, int bookCount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.bookCount = bookCount;
    }
}
