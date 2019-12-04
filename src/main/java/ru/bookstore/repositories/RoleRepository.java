package ru.bookstore.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.bookstore.domain.Role;


/**
 * Created by Rubanov.Maksim on 04.12.2019.
 */

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

}
