package hk.derrick.client;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TodoItem {
  private String id;
  private String description;
  private Date createdDate;
  private Status status;
  private Date completedDate;
  private Date dueDate;

  public enum Status {NEW, COMPLETED};
}