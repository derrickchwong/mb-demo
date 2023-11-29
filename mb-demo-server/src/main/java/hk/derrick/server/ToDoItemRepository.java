package hk.derrick.server;

import java.sql.Date;
import java.time.Instant;
import java.util.List;

import hk.derrick.core.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoItemRepository extends JpaRepository<TodoItem, String>{
    // find all todoitem where the due date is today 
    List<TodoItem> findByDueDate(Instant date);
}
