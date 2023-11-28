package hk.derrick.client;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoClientController {

  TodoItemServiceClient todoItemServiceClient;

  public TodoClientController(TodoItemServiceClient todoItemServiceClient) {
    this.todoItemServiceClient = todoItemServiceClient;
  }

  @PostMapping("/todos")
  public ResponseEntity<TodoItem> createTodoItem(@RequestBody TodoItem todoItem) {
    try {
      TodoItemService todoItemService = todoItemServiceClient.getTodoItemService();
      return
          new ResponseEntity<>(todoItemService.createTodoItem(todoItem), HttpStatus.CREATED);
    }catch (IllegalStateException e){
      return ResponseEntity.badRequest().build();
    }

  }
}
