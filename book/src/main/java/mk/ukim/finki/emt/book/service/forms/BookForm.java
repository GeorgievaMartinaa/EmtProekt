package mk.ukim.finki.emt.book.service.forms;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import mk.ukim.finki.emt.book.domain.model.Category;
import mk.ukim.finki.emt.book.domain.model.Status;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Currency;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class BookForm {

    @NotNull
    private String name;

    private String description;

    @Min(0)
    private float discount;

    private final Currency currency;

    @Min(0)
    private final double price;


//Dali tuka treba da stoe @Enumerated
    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Category category;

    private int bookCount;
}
