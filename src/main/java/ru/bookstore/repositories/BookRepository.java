package ru.bookstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bookstore.domain.Book;


/**
 * Created by Rubanov.Maksim on 04.12.2019.
 */

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
