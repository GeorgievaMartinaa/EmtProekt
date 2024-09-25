package mk.ukim.finki.emt.bookordermanagement.service.forms;

import lombok.Data;
import mk.ukim.finki.emt.bookordermanagement.domain.valueObjects.Book;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class BookOrderForm {

    @NotNull
    private Book book;

    @Min(1)
    private int quantity;
}
