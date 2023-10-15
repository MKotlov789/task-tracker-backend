package ru.mkotlov789.edu.pet.tasktrackerbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mkotlov789.edu.pet.tasktrackerbackend.model.Task;
import ru.mkotlov789.edu.pet.tasktrackerbackend.model.User;

import java.util.List;
import java.util.Optional;
@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    List<Task> findByUserId(Long userId);
    void deleteByUser(User user);

}
