package mk.ukim.finki.emt.bookordermanagement.service.forms;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

import mk.ukim.finki.emt.sharedkernel.domain.financial.Currency;

import java.util.ArrayList;
import java.util.List;


@Data
public class OrderForm {

    @NotNull
    private Currency currency;

    @Valid
    @NotEmpty
    private List<BookOrderForm> items = new ArrayList<>();
}
