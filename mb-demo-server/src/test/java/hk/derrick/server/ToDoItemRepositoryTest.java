package hk.derrick.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import hk.derrick.core.TodoItem;
import hk.derrick.server.ToDoItemRepository;
// import org.testcontainers.containers.PostgreSQLContainer;


@RunWith(SpringRunner.class)
@DataJpaTest
public class ToDoItemRepositoryTest {

    @Autowired
    private ToDoItemRepository toDoItemRepository;

    // @ClassRule
    // public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11.1")
    //   .withDatabaseName("integration-tests-db")
    //   .withUsername("sa")
    //   .withPassword("sa");
      
    @Test
    public void testFindById() {
        TodoItem toDoItem = new TodoItem();
        
        toDoItem.setId(UUID.randomUUID().toString());
        toDoItem.setDescription("Read a book about JPA");
        toDoItem.setDueDate(new Timestamp(System.currentTimeMillis()).toInstant());

        toDoItemRepository.save(toDoItem);

        TodoItem foundToDoItem = toDoItemRepository.findById(toDoItem.getId()).get();

        assertEquals(toDoItem, foundToDoItem);
    }

    @Test
    public void testFindAll() {
        TodoItem toDoItem1 = new TodoItem();
        toDoItem1.setId(UUID.randomUUID().toString());
        toDoItem1.setDescription("Read a book about JPA");
        toDoItem1.setDueDate(new Timestamp(System.currentTimeMillis()).toInstant());

        TodoItem toDoItem2 = new TodoItem();
        toDoItem2.setId(UUID.randomUUID().toString());
        toDoItem2.setDescription("Read a book about Spring Boot");
        toDoItem2.setDueDate(new Timestamp(System.currentTimeMillis()).toInstant());

        toDoItemRepository.save(toDoItem1);
        toDoItemRepository.save(toDoItem2);

        List<TodoItem> toDoItems = toDoItemRepository.findAll();

        assertEquals(2, toDoItems.size());
        assertTrue(toDoItems.contains(toDoItem1));
        assertTrue(toDoItems.contains(toDoItem2));
    }

    @Test
    public void testFindByDueDate() {
        TodoItem toDoItem1 = new TodoItem();
        toDoItem1.setId(UUID.randomUUID().toString());
        toDoItem1.setDescription("Read a book about JPA");
        Instant now = new Timestamp(System.currentTimeMillis()).toInstant();
        toDoItem1.setDueDate(now);

        TodoItem toDoItem2 = new TodoItem();
        toDoItem2.setId(UUID.randomUUID().toString());
        toDoItem2.setDescription("Read a book about Spring Boot");
        toDoItem2.setDueDate(new Timestamp(System.currentTimeMillis() + 1000 * 60 * 60 * 24).toInstant());

        toDoItemRepository.save(toDoItem1);
        toDoItemRepository.save(toDoItem2);

        List<TodoItem> toDoItems = toDoItemRepository.findByDueDate(now);

        assertEquals(1, toDoItems.size());
        assertTrue(toDoItems.contains(toDoItem1));
    }
}  
