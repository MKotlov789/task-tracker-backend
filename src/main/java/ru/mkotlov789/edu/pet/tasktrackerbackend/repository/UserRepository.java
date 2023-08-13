package ru.mkotlov789.edu.pet.tasktrackerbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mkotlov789.edu.pet.tasktrackerbackend.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    boolean existsUserByUsername(String username);
}
