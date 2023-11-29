package hk.derrick.server;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;
import hk.derrick.core.TodoItem;

@Configuration
@EnableKafka
@Slf4j
public class KafkaConfig {

  @Value(value = "${spring.kafka.bootstrap-servers}")
  private String bootstrapAddress;

  @Value(value = "${spring.application.name}")
  private String applicationId;


  @Bean
  public ConcurrentKafkaListenerContainerFactory<?, TodoItem> todoConsumerFactory() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, applicationId);


    var deserializer = new JsonDeserializer<>(TodoItem.class);
    deserializer.addTrustedPackages("hk.derrick.client", "hk.derrick.server", "hk.derrick.core");

    ConsumerFactory<String, TodoItem> consumerFactory = new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);

    ConcurrentKafkaListenerContainerFactory<String, TodoItem> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory);

    return factory;

  }
}
