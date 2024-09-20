package mk.ukim.finki.emt.bookordermanagement.service;

import mk.ukim.finki.emt.bookordermanagement.domain.exeptions.OrderIdNotExistsExeption;
import mk.ukim.finki.emt.bookordermanagement.domain.model.Order;
import mk.ukim.finki.emt.bookordermanagement.domain.model.OrderId;
import mk.ukim.finki.emt.bookordermanagement.domain.model.OrderRequest;
import mk.ukim.finki.emt.bookordermanagement.domain.valueObjects.Book;
import mk.ukim.finki.emt.bookordermanagement.domain.valueObjects.BookId;
import mk.ukim.finki.emt.bookordermanagement.service.forms.BookOrderForm;
import mk.ukim.finki.emt.bookordermanagement.service.forms.OrderForm;
import mk.ukim.finki.emt.bookordermanagement.xport.client.BookClient;
import mk.ukim.finki.emt.sharedkernel.domain.events.orders.BookOrdered;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Currency;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class OrderServiceImplTest {
    @Autowired
    private OrderService orderService;

    @Autowired
    private BookClient bookClient;

    private static Book newBook(String name, Money price, int bookCoount){
        Book b = new Book(BookId.randomId(BookId.class), name, price, bookCoount);
        return b;
    }

    @Test
    public void testPlaceOrder(){
        BookOrderForm bookOrderForm = new BookOrderForm();
        bookOrderForm.setBook(newBook("Ova zavrshuva so nas", Money.valueOf(Currency.MKD,450),5));
        bookOrderForm.setQuantity(1);

        BookOrderForm bookOrderForm2 = new BookOrderForm();
        bookOrderForm2.setBook(newBook("Veriti", Money.valueOf(Currency.MKD,350),5));
        bookOrderForm2.setQuantity(2);

        OrderForm orderForm = new OrderForm();
        orderForm.setCurrency(Currency.MKD);
        orderForm.setItems(Arrays.asList(bookOrderForm,bookOrderForm2));

        OrderId newOrderId = orderService.placeOrder(orderForm);
        Order newOrder = orderService.findById(newOrderId).orElseThrow(OrderIdNotExistsExeption::new);

        System.out.println("New Order od Testot:  "+ newOrder);
        Assertions.assertEquals(newOrder.total(), Money.valueOf(Currency.MKD,1150.0));


    }

    @Test
    public void testPlaceOrderWithRealData() {
        List<Book> books = bookClient.findAll();
        for (Book book:books) {
            System.out.println(book.getName()+"  "+book.getBookCount()+"  "+book.getPrice().getAmount());

        }


        Book b1 = books.get(0);
        Book b2 = books.get(1);

        BookOrderForm bookOrderForm1 = new BookOrderForm();
        bookOrderForm1.setBook(b1);
        bookOrderForm1.setQuantity(1);

        BookOrderForm bookOrderForm2 = new BookOrderForm();
        bookOrderForm2.setBook(b2);
        bookOrderForm2.setQuantity(1);

        OrderForm orderForm = new OrderForm();
        orderForm.setCurrency(Currency.MKD);
        orderForm.setItems(Arrays.asList(bookOrderForm1,bookOrderForm2));

        OrderId newOrderId = orderService.placeOrder(orderForm);
        Order newOrder = orderService.findById(newOrderId).orElseThrow(OrderIdNotExistsExeption::new);

        Money outMoney = b1.getPrice().multiply(bookOrderForm1.getQuantity()).add(b2.getPrice().multiply(bookOrderForm2.getQuantity()));
        Assertions.assertEquals(newOrder.total(),outMoney);
    }

    @Test
    public void TestToJson(){
        BookOrdered event = new BookOrdered("book-id", 1);

        // Act: повикување на toJson() за серјализација на објектот во JSON
        String jsonOutput = event.toJson();
        System.out.println( "JsonOtput Martina : "+jsonOutput);

        // Assert: проверка дека JSON не е null или празен
       // assertNotNull(jsonOutput, "The JSON output should not be null.");
        assertFalse(jsonOutput.isEmpty(), "The JSON output should not be empty.");
    }


}
