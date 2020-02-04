package ru.bookstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bookstore.domain.User;

/**
 * Created by Rubanov.Maksim on 03.12.2019.
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
}