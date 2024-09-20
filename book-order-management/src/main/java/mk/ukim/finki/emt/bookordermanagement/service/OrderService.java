package mk.ukim.finki.emt.bookordermanagement.service;

import mk.ukim.finki.emt.bookordermanagement.domain.exeptions.BookOrderIdNotExistExeption;
import mk.ukim.finki.emt.bookordermanagement.domain.exeptions.OrderIdNotExistsExeption;
import mk.ukim.finki.emt.bookordermanagement.domain.model.BookOrderId;
import mk.ukim.finki.emt.bookordermanagement.domain.model.Order;
import mk.ukim.finki.emt.bookordermanagement.domain.model.OrderId;
import mk.ukim.finki.emt.bookordermanagement.service.forms.BookOrderForm;
import mk.ukim.finki.emt.bookordermanagement.service.forms.OrderForm;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    OrderId placeOrder(OrderForm orderForm);
    List<Order> findAll();
    Optional<Order> findById(OrderId id);
    void addItem(OrderId orderId, BookOrderForm bookOrderForm) throws OrderIdNotExistsExeption;
    void deleteItem(OrderId orderId, BookOrderId bookOrderId) throws OrderIdNotExistsExeption, BookOrderIdNotExistExeption;
}
