package hk.derrick.server;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import hk.derrick.core.TodoItem;

@RestController
public class ToDoItemController {
    @Autowired
    private ToDoItemService toDoItemService;

    // constructor 
    public ToDoItemController(ToDoItemService toDoItemService) {
        this.toDoItemService = toDoItemService;
    }

    // HTTP Get to get all todo item
    @GetMapping("/todos")
    public List<TodoItem> getAllToDoItems() {
        return toDoItemService.getAll();
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<TodoItem> getToDoItemById(@PathVariable("id") String id) {
        try {
            return new ResponseEntity<>(toDoItemService.getById(id), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/todos")
    public ResponseEntity<TodoItem> saveToDoItem(@RequestBody TodoItem toDoItem) {
        try{
            return new ResponseEntity<>(toDoItemService.save(toDoItem), HttpStatus.CREATED);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }

}
