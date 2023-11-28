package hk.derrick.server;

import hk.derrick.server.ToDoItem;
import hk.derrick.server.ToDoItemController;
import hk.derrick.server.ToDoItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ToDoItemControllerTest {

    @InjectMocks
    private ToDoItemController toDoItemController;

    @Mock
    private ToDoItemService toDoItemService;

    @Test
    public void getAllToDoItems_shouldReturnAllToDoItems() {
        // Arrange
        ToDoItem toDoItem1 = new ToDoItem("Buy milk");
        ToDoItem toDoItem2 = new ToDoItem("Wash car");
        when(toDoItemService.getAll()).thenReturn(List.of(toDoItem1, toDoItem2));
            
        // Act
        List<ToDoItem> actual = toDoItemController.getAllToDoItems();

        // Assert
        assertEquals(2, actual.spliterator().getExactSizeIfKnown());
        assertTrue(actual.contains(toDoItem1));
        assertTrue(actual.contains(toDoItem2));
    }

    @Test
    public void saveToDoItem_shouldSaveToDoItem() {
        // Arrange
        ToDoItem toDoItem = new ToDoItem("Buy milk");
        when(toDoItemService.save(toDoItem)).thenReturn(toDoItem);

        // Act
        ResponseEntity<ToDoItem> actual = toDoItemController.saveToDoItem(toDoItem);

        // Assert
        assertEquals(HttpStatus.CREATED, actual.getStatusCode());
        assertEquals(toDoItem, actual.getBody());
    }

    @Test
    public void saveToDoItem_shouldReturnBadRequest_whenToDoItemIsInvalid() {
        // Arrange
        ToDoItem toDoItem = new ToDoItem("");

        // date of yesterday
        toDoItem.setDueDate(new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000));

        // when todoitemservice.save is called, return http status bad request 
        when(toDoItemService.save(toDoItem))
        .thenThrow(new IllegalArgumentException("Due date cannot be earlier than today"));



        // Act
        ResponseEntity<ToDoItem> actual = toDoItemController.saveToDoItem(toDoItem);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
    }
}
