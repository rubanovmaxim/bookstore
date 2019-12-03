package ru.bookstore.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.bookstore.domain.User;

import java.util.List;

/**
 * Created by Rubanov.Maksim on 03.12.2019.
 */

@Repository
public interface UserRepository extends CrudRepository<User, Long> { // Long: Type of Employee ID.

    User findByUserName(String userName);


}