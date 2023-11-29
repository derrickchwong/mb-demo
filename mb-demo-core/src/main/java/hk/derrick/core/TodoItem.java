package hk.derrick.core;

import java.lang.annotation.Inherited;
import java.time.Instant;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class TodoItem {
  @Id
  private String id;
  private String description;
  private Instant createdDate;
  private Status status;
  private Instant completedDate;
  private Instant dueDate;

  public enum Status {NEW, COMPLETED};

  // constructor for description 
  public TodoItem(String description) {
    this.description = description;
  }
}