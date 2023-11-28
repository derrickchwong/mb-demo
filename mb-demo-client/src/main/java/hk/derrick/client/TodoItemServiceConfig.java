package hk.derrick.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class TodoItemServiceConfig {

  @Bean
  public TodoItemServiceClient todoItemService(){
    TodoItemServiceClient todoItemServiceClient = new TodoItemServiceClient(WebClient.builder()
        .baseUrl("http://localhost:8080")
        .build());
    return todoItemServiceClient;
  }
}
