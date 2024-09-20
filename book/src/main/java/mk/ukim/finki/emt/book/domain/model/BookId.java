package mk.ukim.finki.emt.book.domain.model;

import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;

import javax.validation.constraints.NotNull;

public class BookId extends DomainObjectId {
    private BookId(){
        super(BookId.randomId(BookId.class).getId());
    }

    public BookId(@NotNull String id) {
        super(id);
    }

    public static BookId of(String id){
        BookId b = new BookId(id);
        return b;
    }
}
