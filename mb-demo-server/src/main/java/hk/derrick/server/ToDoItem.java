package hk.derrick.server;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class ToDoItem {
    @Id
    private String id; 
    private String description;
    private Date createdDate;
    private Status status;
    private Date completedDate;
    private Date dueDate;

    public enum Status {NEW, COMPLETED};

    // constructor for description
    public ToDoItem(String description) {
        this.description = description;
    }
}
