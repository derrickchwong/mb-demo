package hk.derrick.client;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface TodoItemService {

  @PostMapping("/todos")
  TodoItem createTodoItem(@RequestBody TodoItem todoItem);

}
