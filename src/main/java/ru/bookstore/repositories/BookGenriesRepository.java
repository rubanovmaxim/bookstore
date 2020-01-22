package ru.bookstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bookstore.domain.BookGenries;

import java.util.List;

public interface BookGenriesRepository extends JpaRepository<BookGenries,Long> {

    public List<BookGenries> findAllByBookId(long bookId);
}
