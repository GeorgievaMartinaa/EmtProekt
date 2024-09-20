package mk.ukim.finki.emt.bookordermanagement.domain.model;

import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;


public class BookOrderId extends DomainObjectId {
    private BookOrderId(){
        super(BookOrderId.randomId(BookOrderId.class).getId());
    }

    public BookOrderId(@NonNull String uuid) {
        super(uuid);
    }

}
