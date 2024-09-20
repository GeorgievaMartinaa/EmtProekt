package mk.ukim.finki.emt.bookordermanagement.domain.model;

import lombok.Data;
import mk.ukim.finki.emt.bookordermanagement.service.forms.BookOrderForm;


import java.util.ArrayList;
import java.util.List;

@Data
public class OrderRequest {
    private String currency;
    private List<BookOrderForm> items;
}