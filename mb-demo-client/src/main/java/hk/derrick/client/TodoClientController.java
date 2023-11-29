package hk.derrick.client;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import hk.derrick.client.TodoItem.Status;

@RestController
public class TodoClientController {

  TodoItemServiceClient todoItemServiceClient;

  @Autowired
  KafkaProducer kafkaProducer;

  public TodoClientController(TodoItemServiceClient todoItemServiceClient, KafkaProducer kafkaProducer) {
    this.todoItemServiceClient = todoItemServiceClient;
    this.kafkaProducer = kafkaProducer;
  }

  @PostMapping("/todos-event")
  public ResponseEntity createTodoItemEvent(@RequestBody TodoItem todoItem) {
    todoItem.setId(UUID.randomUUID().toString());
    todoItem.setStatus(Status.NEW);
    todoItem.setCreatedDate(new Date());
    kafkaProducer.sendItem(todoItem);
    return ResponseEntity.ok().build();
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
