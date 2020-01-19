package ru.bookstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bookstore.domain.BookGenries;

public interface BookGenriesRepository extends JpaRepository<BookGenries,Long> {
}
