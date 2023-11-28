package hk.derrick.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import hk.derrick.server.ToDoItem;
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
        ToDoItem toDoItem = new ToDoItem();
        
        toDoItem.setId(UUID.randomUUID().toString());
        toDoItem.setDescription("Read a book about JPA");
        toDoItem.setDueDate(new Date(System.currentTimeMillis()));

        toDoItemRepository.save(toDoItem);

        ToDoItem foundToDoItem = toDoItemRepository.findById(toDoItem.getId()).get();

        assertEquals(toDoItem, foundToDoItem);
    }

    @Test
    public void testFindAll() {
        ToDoItem toDoItem1 = new ToDoItem();
        toDoItem1.setId(UUID.randomUUID().toString());
        toDoItem1.setDescription("Read a book about JPA");
        toDoItem1.setDueDate(new Date(System.currentTimeMillis()));

        ToDoItem toDoItem2 = new ToDoItem();
        toDoItem2.setId(UUID.randomUUID().toString());
        toDoItem2.setDescription("Read a book about Spring Boot");
        toDoItem2.setDueDate(new Date(System.currentTimeMillis()));

        toDoItemRepository.save(toDoItem1);
        toDoItemRepository.save(toDoItem2);

        List<ToDoItem> toDoItems = toDoItemRepository.findAll();

        assertEquals(2, toDoItems.size());
        assertTrue(toDoItems.contains(toDoItem1));
        assertTrue(toDoItems.contains(toDoItem2));
    }

    @Test
    public void testFindByDueDate() {
        ToDoItem toDoItem1 = new ToDoItem();
        toDoItem1.setId(UUID.randomUUID().toString());
        toDoItem1.setDescription("Read a book about JPA");
        Date now = new Date(System.currentTimeMillis());
        toDoItem1.setDueDate(now);

        ToDoItem toDoItem2 = new ToDoItem();
        toDoItem2.setId(UUID.randomUUID().toString());
        toDoItem2.setDescription("Read a book about Spring Boot");
        toDoItem2.setDueDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24));

        toDoItemRepository.save(toDoItem1);
        toDoItemRepository.save(toDoItem2);

        List<ToDoItem> toDoItems = toDoItemRepository.findByDueDate(now);

        assertEquals(1, toDoItems.size());
        assertTrue(toDoItems.contains(toDoItem1));
    }
}  
