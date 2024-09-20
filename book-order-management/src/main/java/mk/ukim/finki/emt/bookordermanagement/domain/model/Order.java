package mk.ukim.finki.emt.bookordermanagement.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import mk.ukim.finki.emt.bookordermanagement.domain.valueObjects.Book;
import mk.ukim.finki.emt.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Currency;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;


import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Table(name = "orders")
public class Order extends AbstractEntity<OrderId> {

    private Instant date;

    @Column(name = "order_currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<BookOrder> bookOrderSet = new HashSet<>();

    public Order(Instant now, @NotNull Currency currency) {
        super(OrderId.randomId(OrderId.class));
        this.date=now;
        this.currency=currency;
    }

    public Order() {
        super(OrderId.randomId(OrderId.class));
    }


    public Money total(){
        Money total =bookOrderSet.stream().map(BookOrder::subtotal).reduce(new Money(currency,0), Money::add);
        System.out.println("Вкупно: " + total);
        return total;
    }

    //ova Book e value object a ne knigata od drugiot ogranicen kontekst
    public BookOrder addBookInOrder(@NonNull Book book, int qty){
        Objects.requireNonNull(book,"book can't be null");
        var item =new BookOrder(book.getId(), book.getPrice(),qty);

        bookOrderSet.add(item);

        return item;
    }

    public void removeBookFromOrder(@NonNull BookOrderId bookOrderId){
        Objects.requireNonNull(bookOrderId,"book can't be null");

        bookOrderSet.removeIf(b->b.getId().equals(bookOrderId));
    }
}


