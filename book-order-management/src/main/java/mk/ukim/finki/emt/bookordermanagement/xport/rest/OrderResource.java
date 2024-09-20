package mk.ukim.finki.emt.bookordermanagement.xport.rest;

import com.fasterxml.jackson.core.JsonParser;
import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.bookordermanagement.domain.exeptions.OrderIdNotExistsExeption;
import mk.ukim.finki.emt.bookordermanagement.domain.model.*;
import mk.ukim.finki.emt.bookordermanagement.domain.valueObjects.Book;
import mk.ukim.finki.emt.bookordermanagement.service.OrderService;
import mk.ukim.finki.emt.bookordermanagement.service.forms.BookOrderForm;
import mk.ukim.finki.emt.bookordermanagement.service.forms.OrderForm;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Currency;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;



@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class OrderResource {
    private final OrderService orderService;

    @PostMapping("/place")
    public OrderId placeOrder(@RequestBody OrderRequest orderRequest) {
        try {

            Currency currencyEnum = Currency.valueOf(orderRequest.getCurrency());
            OrderForm orderForm = new OrderForm();
            orderForm.setCurrency(currencyEnum);
            orderForm.setItems(orderRequest.getItems());

            return orderService.placeOrder(orderForm);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }

    @PostMapping("/deleteItem/{orderId}/{bookOrderId}")
    public ResponseEntity deleteItemFromOrder(@PathVariable OrderId orderId,
                                              @PathVariable BookOrderId bookOrderId) {

        if (orderId == null) {
            return ResponseEntity.notFound().build();
        }
        if (bookOrderId == null)
            return ResponseEntity.notFound().build();

        this.orderService.deleteItem(orderId, bookOrderId);
        return ResponseEntity.ok().build();

    }
}
