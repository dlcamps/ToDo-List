package model;

import model.Item;
import model.ToDoList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestFileRead {
    private ToDoList myList;
    private Item itemToCompare;

    @BeforeEach
    public void setup() {
        myList = new ToDoList();
    }

    @Test
    public void testFileRead() throws IOException {
        myList.fileRead("ForTestRead");
        itemToCompare = myList.getItem(0);
        assertEquals(itemToCompare.getName(), "[!!!] ToDo1");
        itemToCompare = myList.getItem(1);
        assertEquals(itemToCompare.getName(), "ToDo2");
        itemToCompare = myList.getItem(2);
        assertEquals(itemToCompare.getName(), "ToDo3");
        itemToCompare = myList.getItem(3);
        assertEquals(itemToCompare.getName(), "ToDo4");
    }
}
