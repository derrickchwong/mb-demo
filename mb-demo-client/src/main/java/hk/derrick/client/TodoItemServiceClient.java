package hk.derrick.client;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

public class TodoItemServiceClient {

  private final TodoItemService todoItemService;

  public TodoItemServiceClient(WebClient webClient){

    HttpServiceProxyFactory httpServiceProxyFactory =
        HttpServiceProxyFactory.builderFor(WebClientAdapter.create(webClient))
            .build();
    todoItemService = httpServiceProxyFactory.createClient(TodoItemService.class);
  }

  public TodoItemService getTodoItemService() {
    return todoItemService;
  }
}
