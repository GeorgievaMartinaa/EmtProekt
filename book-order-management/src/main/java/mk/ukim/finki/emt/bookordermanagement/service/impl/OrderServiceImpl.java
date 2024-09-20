package mk.ukim.finki.emt.bookordermanagement.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.bookordermanagement.domain.exeptions.BookOrderIdNotExistExeption;
import mk.ukim.finki.emt.bookordermanagement.domain.exeptions.OrderIdNotExistsExeption;
import mk.ukim.finki.emt.bookordermanagement.domain.model.BookOrderId;
import mk.ukim.finki.emt.bookordermanagement.domain.model.Order;
import mk.ukim.finki.emt.bookordermanagement.domain.model.OrderId;
import mk.ukim.finki.emt.bookordermanagement.domain.repository.OrderRepository;
import mk.ukim.finki.emt.bookordermanagement.service.OrderService;
import mk.ukim.finki.emt.bookordermanagement.service.forms.BookOrderForm;
import mk.ukim.finki.emt.bookordermanagement.service.forms.OrderForm;
import mk.ukim.finki.emt.sharedkernel.domain.events.orders.BookOrdered;
import mk.ukim.finki.emt.sharedkernel.infra.DomainEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
//import javax.validation.Validator;
import java.time.Instant;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    //Bidejki apl servisi ja orkestirat celata apl mozam da dodadam repozitori.
    private final OrderRepository orderRepository;
    //private final Validator validator;
    private final DomainEventPublisher domainEventPublisher;



    @Override
    public OrderId placeOrder(OrderForm orderForm) {
        System.out.println("OrderForm vo metodot placeOrder "+ orderForm);
        Objects.requireNonNull(orderForm,"order can't be null");

       // var constraintViolation =validator.validate(orderForm);
       /* if(constraintViolation.size() > 0)
        {
            throw new ConstraintViolationException(" The order form is not valid", constraintViolation);
        }*/

        var newOrder = orderRepository.saveAndFlush(toDomainObject(orderForm));
        System.out.println("Producer newOrder" + newOrder);

        //Ovde se povikuva producerot
        newOrder.getBookOrderSet().forEach(item->domainEventPublisher.publish(new BookOrdered(item.getBookId().getId(), item.getQuantity())));

        return newOrder.getId();
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> findById(OrderId id) {
        return orderRepository.findById(id);
    }

    @Override
    public void addItem(OrderId orderId, BookOrderForm bookOrderForm) throws OrderIdNotExistsExeption {
        Order order = findById(orderId).orElseThrow(OrderIdNotExistsExeption::new);
        order.addBookInOrder(bookOrderForm.getBook(), bookOrderForm.getQuantity());

        orderRepository.saveAndFlush(order);

        domainEventPublisher.publish(new BookOrdered(bookOrderForm.getBook().getId().getId(), bookOrderForm.getQuantity()));

    }

    /*@Override
    public void deleteItem(OrderId orderId, BookOrderId bookOrderId, BookOrderForm bookOrderForm) throws OrderIdNotExistsExeption, BookOrderIdNotExistExeption {
        Order order = findById(orderId).orElseThrow(OrderIdNotExistsExeption::new);
        order.removeBookFromOrder(bookOrderId);

        orderRepository.saveAndFlush(order);
    }*/

    @Override
    public void deleteItem(OrderId orderId, BookOrderId bookOrderId) throws OrderIdNotExistsExeption, BookOrderIdNotExistExeption {
        Order order = findById(orderId).orElseThrow(OrderIdNotExistsExeption::new);
        order.removeBookFromOrder(bookOrderId);

        orderRepository.saveAndFlush(order);
    }

        //konvertira od orderform vo order
        private Order toDomainObject(OrderForm orderForm){
        var order = new Order(Instant.now(),orderForm.getCurrency());

        //gi lista site podatoci od order formata i gi stava vo narachkata
        orderForm.getItems().forEach(item-> order.addBookInOrder(item.getBook(),item.getQuantity()));

        return order;
    }
}
