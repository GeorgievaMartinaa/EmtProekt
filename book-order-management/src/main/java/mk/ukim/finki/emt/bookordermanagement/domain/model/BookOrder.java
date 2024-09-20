package mk.ukim.finki.emt.bookordermanagement.domain.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NonNull;
import mk.ukim.finki.emt.bookordermanagement.domain.valueObjects.BookId;
import mk.ukim.finki.emt.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;


@Entity
@Getter
public class BookOrder extends AbstractEntity<BookOrderId> {

    private Money bookPrice;

    @Column(name = "qty", nullable = false)
    private int quantity;

    @AttributeOverride(name = "id", column = @Column(name = "book_id", nullable = false))
    private BookId bookId;

    public Money subtotal() {
        return bookPrice.multiply(quantity);
    }

    public BookOrder(@NonNull BookId bookId, @NonNull Money bookPrice, int quantity) {
            super();
        DomainObjectId.randomId(BookOrderId.class);
        this.bookId = bookId;
        this.bookPrice=bookPrice;
        this.quantity=quantity;
    }


    private BookOrder() {
        super(DomainObjectId.randomId(BookOrderId.class));
    }

}
