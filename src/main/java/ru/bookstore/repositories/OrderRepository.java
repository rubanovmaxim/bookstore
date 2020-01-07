package ru.bookstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bookstore.domain.Order;

import java.util.Optional;


/**
 * Created by Rubanov.Maksim on 04.12.2019.
 */

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    public Optional<Order> findByIdAndUserId(Long orderId, Long userId);
}
