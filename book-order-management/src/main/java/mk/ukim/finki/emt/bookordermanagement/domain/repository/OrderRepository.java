package mk.ukim.finki.emt.bookordermanagement.domain.repository;

import mk.ukim.finki.emt.bookordermanagement.domain.model.Order;
import mk.ukim.finki.emt.bookordermanagement.domain.model.OrderId;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, OrderId> {
}
