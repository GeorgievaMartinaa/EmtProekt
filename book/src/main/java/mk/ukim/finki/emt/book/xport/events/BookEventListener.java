package mk.ukim.finki.emt.book.xport.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.book.domain.model.BookId;
import mk.ukim.finki.emt.book.service.BookService;
import mk.ukim.finki.emt.sharedkernel.domain.config.TopicHolder;
import mk.ukim.finki.emt.sharedkernel.domain.events.DomainEvent;
import mk.ukim.finki.emt.sharedkernel.domain.events.orders.BookOrdered;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.SimpleTimeZone;

@Service
@AllArgsConstructor
//ovoa e subscriber (listener)
public class BookEventListener {
    private final BookService bookService;

    @KafkaListener(topics = TopicHolder.TOPIC_BOOK_ORDERED, groupId = "book")
    public void consumeBookOrderedEvent(String jsonMessage) {

        try {
            if (jsonMessage == null || jsonMessage.isEmpty()) {
                throw new IllegalArgumentException("Received empty message");
            }
            BookOrdered event = DomainEvent.fromJson(jsonMessage, BookOrdered.class);
            bookService.bookOrdered(BookId.of(event.getBookid()), event.getQuantity());

        } catch (JsonProcessingException e) {
            System.err.println("Error parsing JSON message: " + jsonMessage);
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error processing message: " + jsonMessage);
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = TopicHolder.TOPIC_BOOK_ADDED, groupId = "book")
    public void consumeBookAddedEvent(String jsonMessage) {

        try {
            if (jsonMessage == null || jsonMessage.isEmpty()) {
                throw new IllegalArgumentException("Received empty message");
            }
            BookOrdered event = DomainEvent.fromJson(jsonMessage, BookOrdered.class);
            bookService.bookOrdered(BookId.of(event.getBookid()), event.getQuantity());

        } catch (JsonProcessingException e) {
            System.err.println("Error parsing JSON message: " + jsonMessage);
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error processing message: " + jsonMessage);
            e.printStackTrace();
        }
    }
}
