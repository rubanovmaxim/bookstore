package ru.bookstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bookstore.domain.Order;
import ru.bookstore.domain.OrderContent;

import java.util.List;


/**
 * Created by Rubanov.Maksim on 04.12.2019.
 */

@Repository
public interface OrderContentRepository extends JpaRepository<OrderContent, Long> {

    List<OrderContent> findAllByOrderId(Long orderId);
}
