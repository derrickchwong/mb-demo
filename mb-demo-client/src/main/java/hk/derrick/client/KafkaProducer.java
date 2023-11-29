package hk.derrick.client;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import hk.derrick.core.TodoItem;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class KafkaProducer {
    
  private final KafkaTemplate kafkaTemplate;

  public void sendItem(TodoItem todoItem){
    kafkaTemplate.send("todos", todoItem.getId(), todoItem);
  }


}
