package ru.bookstore.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.bookstore.domain.User;

import java.util.List;

/**
 * Created by Rubanov.Maksim on 03.12.2019.
 */

public interface UserRepository extends CrudRepository<User, Long> { // Long: Type of Employee ID.

    User findByUserName(String userName);

    List<User> findByFullNameLike(String fullName);



}