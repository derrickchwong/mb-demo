package hk.derrick.client;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

import hk.derrick.core.TodoItem;

public interface TodoItemService {

  @PostExchange("/todos")
  TodoItem createTodoItem(@RequestBody TodoItem todoItem);

}
