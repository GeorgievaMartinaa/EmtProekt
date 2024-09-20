package mk.ukim.finki.emt.sharedkernel.domain.financial;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;

import java.util.Objects;


@Embeddable
@Getter
public class Money implements ValueObject {

    @Enumerated(value = EnumType.STRING)
    @Column(name = "currency")
    private final Currency currency;

    @Column(name = "amount")
    private final double amount;

    protected Money(){
        this.amount = 0.0;
        this.currency = null;
    }

    public Money(@NonNull Currency currency, @NonNull double amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public static Money valueOf(Currency currency, double amount)
    {
        return new Money(currency,amount);
    }

    public Money add(Money money){
        if(!currency.equals(money.currency)){
            throw new IllegalArgumentException("Cannot add Money objects with different currencies");
        }
        return new Money(currency,amount+money.amount);
    }

    public Money substract(Money money){
        if(!currency.equals(money.currency)){
            throw new IllegalArgumentException("Cannot substract Money objects with different currencies");
        }
        return new Money(currency,amount-money.amount);
    }

    public Money multiply( int n){
        return new Money(currency,amount*n);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return amount == money.amount && currency == money.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, amount);
    }


}
