package mk.ukim.finki.emt.bookordermanagement.service;

import mk.ukim.finki.emt.bookordermanagement.domain.model.Order;
import mk.ukim.finki.emt.bookordermanagement.domain.model.OrderId;
import mk.ukim.finki.emt.bookordermanagement.service.forms.OrderForm;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    OrderId placeOrder(OrderForm orderForm);
    List<Order> findAll();
    Optional<Order> findById(OrderId id);
}
