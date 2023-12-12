package hk.derrick.server;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hk.derrick.core.TodoItem;
import hk.derrick.core.TodoItem.Status;

import java.sql.Timestamp;
import java.util.List;

@Service
@Slf4j
public class ToDoItemService {
    
    @Autowired
    private ToDoItemRepository toDoItemRepository;
    
    // constructor 
    public ToDoItemService(ToDoItemRepository toDoItemRepository) {
        this.toDoItemRepository = toDoItemRepository;
    }

    // function to save todoitem to db 
    public TodoItem save(TodoItem toDoItem) throws IllegalArgumentException{

        log.info("received todo item");
        if (toDoItem.getDueDate() != null) {
            // print due date in log
            log.info("Due date {}", toDoItem.getDueDate().toString());
            // check if due date is earlier than today
            if(toDoItem.getDueDate().isBefore(new Timestamp(System.currentTimeMillis()).toInstant()) ) {
                log.error("Due date {} cannot be earlier than today",
                    toDoItem.getDueDate().toString());
                throw new IllegalArgumentException("Due date cannot be earlier than today");
            }
        }
        if(toDoItem.getStatus() == Status.COMPLETED) {
            log.error("New todo item cannot be completed");
            throw new IllegalArgumentException("New todo item cannot be completed");
        }
    
        
        toDoItem.setCreatedDate(new Timestamp(System.currentTimeMillis()).toInstant());
        toDoItem.setCompletedDate(null);
        toDoItem.setId(java.util.UUID.randomUUID().toString());
        toDoItem.setStatus(Status.NEW);
        log.info("todo: {}", toDoItem);
        return toDoItemRepository.save(toDoItem);
    }

    

    // function to delete todoitem to db 
    public void delete(TodoItem toDoItem) {
        toDoItemRepository.delete(toDoItem);
    }

    // function to complete an item
    public TodoItem complete(TodoItem toDoItem) {
        toDoItem.setStatus(Status.COMPLETED);
        //set the completed data to now
        toDoItem.setCompletedDate(new Timestamp(System.currentTimeMillis()).toInstant());
        return toDoItemRepository.save(toDoItem);
    }

    // function to return all todo items
    public List<TodoItem> getAll() {
        return toDoItemRepository.findAll();
    }

    // function to get todoitem by id 
    public TodoItem getById(String id) {
        return toDoItemRepository.findById(id).orElse(null);
    }
}
