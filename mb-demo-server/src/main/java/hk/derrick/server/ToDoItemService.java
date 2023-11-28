package hk.derrick.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hk.derrick.server.ToDoItem.Status;
import java.util.Date;
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
    public ToDoItem save(ToDoItem toDoItem) throws IllegalArgumentException{

        if (toDoItem.getDueDate() != null) {
            // print due date in log
            log.info("Due date {}", toDoItem.getDueDate().toString());
            // check if due date is earlier than today
            if(toDoItem.getDueDate().before(new Date())) {
                log.error("Due date {} cannot be earlier than today",
                    toDoItem.getDueDate().toString());
                throw new IllegalArgumentException("Due date cannot be earlier than today");
            }
        }
        
        toDoItem.setCreatedDate(new Date());
        toDoItem.setCompletedDate(null);
        toDoItem.setId(java.util.UUID.randomUUID().toString());
        toDoItem.setStatus(Status.NEW);
        return toDoItemRepository.save(toDoItem);
    }

    

    // function to delete todoitem to db 
    public void delete(ToDoItem toDoItem) {
        toDoItemRepository.delete(toDoItem);
    }

    // function to complete an item
    public ToDoItem complete(ToDoItem toDoItem) {
        toDoItem.setStatus(Status.COMPLETED);
        //set the completed data to now
        toDoItem.setCompletedDate(new Date());
        return toDoItemRepository.save(toDoItem);
    }

    // function to return all todo items
    public List<ToDoItem> getAll() {
        return toDoItemRepository.findAll();
    }
}
