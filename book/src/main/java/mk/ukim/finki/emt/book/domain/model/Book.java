package mk.ukim.finki.emt.book.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;



@Entity
@Getter
public class Book extends AbstractEntity<BookId> {

    private String name;
    private String description;
    private float discount;
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "price_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "price_currency"))
    })
    private Money price;
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "price_amount_discounted")),
            @AttributeOverride(name = "currency", column = @Column(name = "price_currency_discounted"))
    })
    private Money priceWithDiscount;
    //novo (Kolku primeroci od knigata ima na zaliha)
    private int bookCount;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Category category;

    private Book() {
        super(BookId.randomId(BookId.class));
    }

    public static Book build(String name, String description, float discount,Money price, Status status, Category category, int bookCount) {
        Book b = new Book();
        b.name=name;
        b.description = description;
        b.discount = discount;
        b.price=price;
        b.priceWithDiscount = getDiscountedPrice(price,discount);
        b.status = changeBookStatus(status, bookCount);
        b.category = category;
        b.bookCount = bookCount;

        return b;
    }

    public void addBook(int qty) {
        this.bookCount = this.bookCount + qty;
    }

    public void orderBook(int qty) {
        if (this.bookCount >= qty)
            this.bookCount = this.bookCount - qty;
        if(this.bookCount >0){
            this.status =Status.Available;
        } else if (this.bookCount == 0)
            this.status=Status.OutOfStock;
    }

    private static Money getDiscountedPrice(Money price, float discount) {
        if (discount > 0) {
            double discountAmount = price.getAmount() * (discount / 100.0);
            return Money.valueOf(price.getCurrency(), price.getAmount() - discountAmount);
        }
        return price;
    }


    private static Status changeBookStatus(Status status, int bookCount) {
        if (bookCount == 0){
            status = Status.OutOfStock;}
        else status = Status.Available;

        return status;
    }
}
