package hk.derrick.client;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import hk.derrick.core.TodoItem;
import hk.derrick.core.TodoItem.Status;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
    todoItem.setCreatedDate(new Timestamp(System.currentTimeMillis()).toInstant());
    kafkaProducer.sendItem(todoItem);
    log.info("sent event to kafka");
    return ResponseEntity.ok().build();
  }


  @PostMapping("/todos")
  public ResponseEntity<TodoItem> createTodoItem(@RequestBody TodoItem todoItem) {
    try {
      TodoItemService todoItemService = todoItemServiceClient.getTodoItemService();
      TodoItem todoItem2 = todoItemService.createTodoItem(todoItem);
      return new ResponseEntity<>(todoItem2, HttpStatus.CREATED);
    }catch (Exception e){
      log.error("Cannot create todo item");
      e.printStackTrace();
      return ResponseEntity.badRequest().build();
    }

  }
}
