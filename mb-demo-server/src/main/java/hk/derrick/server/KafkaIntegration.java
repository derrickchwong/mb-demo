package hk.derrick.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class KafkaIntegration {
    
    ToDoItemService toDoItemService;

    public KafkaIntegration(ToDoItemService toDoItemService){
        this.toDoItemService = toDoItemService;
    }


  @Bean
  public IntegrationFlow ticketIntegrationFlow(
    ConcurrentKafkaListenerContainerFactory<?, ToDoItem> consumerFactory,
    KafkaTemplate kafkaTemplate ){

    return IntegrationFlow
        .from(Kafka.channel(kafkaTemplate, consumerFactory, "todos"))
        .handle(message -> toDoItemService.save((ToDoItem)message.getPayload()))
        .get();
            

  }  


}
