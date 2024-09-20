package mk.ukim.finki.emt.sharedkernel.domain.events.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.config.TopicHolder;
import mk.ukim.finki.emt.sharedkernel.domain.events.DomainEvent;

@Getter
public class BookOrdered extends DomainEvent {
    private String bookid;
    private int quantity;

    public BookOrdered(String topic) {
        super(TopicHolder.TOPIC_BOOK_ORDERED);
    }
    public BookOrdered() {
        super(TopicHolder.TOPIC_BOOK_ORDERED);
    }

    @JsonCreator
    public BookOrdered(@JsonProperty("bookid") String bookid, @JsonProperty("quantity") int quantity) {
        super(TopicHolder.TOPIC_BOOK_ORDERED);
        this.bookid = bookid;
        this.quantity = quantity;
    }

}
