package hk.derrick.server;

import hk.derrick.server.ToDoItem;
import hk.derrick.server.ToDoItemRepository;
import hk.derrick.server.ToDoItemService;
import hk.derrick.server.ToDoItem.Status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Date;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class ToDoItemServiceTest {

    @Mock
    private ToDoItemRepository toDoItemRepository;

    @Test
    public void testSave() throws IllegalArgumentException {
        // Create a ToDoItem object.
        ToDoItem toDoItem = new ToDoItem();
        
        toDoItem.setDescription("This is a test ToDo item.");
        

        // Create a ToDoItemService object.
        ToDoItemService toDoItemService = new ToDoItemService(toDoItemRepository);

        // Verify that the ToDoItem object was saved to the repository.

        ToDoItem expected = new ToDoItem();
        expected.setDescription("This is a test ToDo item.");
        expected.setId(UUID.randomUUID().toString());
        expected.setCreatedDate(new Date());
        expected.setStatus(Status.NEW);

        Mockito.when(toDoItemRepository.save(toDoItem))
            .thenReturn(expected);

        // Call the save() method.
        ToDoItem savedToDoItem = toDoItemService.save(toDoItem);

        

        // Verify that the saved ToDoItem object has the correct values.
        
        assertEquals(toDoItem.getDescription(), savedToDoItem.getDescription());
        assertEquals(Status.NEW, savedToDoItem.getStatus());
        assertNotNull(savedToDoItem.getCreatedDate());
        assertNull(savedToDoItem.getCompletedDate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveWithInvalidDueDate() throws IllegalArgumentException {
        // Create a ToDoItem object with an invalid due date.
        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setDescription("This is a test ToDo item.");
        toDoItem.setDueDate(new Date(System.currentTimeMillis() - 1000));

        // Create a ToDoItemService object.
        ToDoItemService toDoItemService = new ToDoItemService(toDoItemRepository);

        // Call the save() method.
        toDoItemService.save(toDoItem);
    }
}
