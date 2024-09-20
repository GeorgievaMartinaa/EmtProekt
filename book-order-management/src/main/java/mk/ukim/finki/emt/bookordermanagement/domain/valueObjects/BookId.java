package mk.ukim.finki.emt.bookordermanagement.domain.valueObjects;

import jakarta.persistence.Embeddable;
import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;

@Embeddable
public class BookId extends DomainObjectId {

    private BookId(){
        super(BookId.randomId(BookId.class).getId());
    }

    public BookId(String uuid) {
        super(uuid);
    }

}
