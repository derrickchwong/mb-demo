package hk.derrick.server;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoItemRepository extends JpaRepository<ToDoItem, String>{
    // find all todoitem where the due date is today 
    List<ToDoItem> findByDueDate(Date date);
}
