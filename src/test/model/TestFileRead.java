package model;

import model.Item;
import model.ToDoList;
import observer.AutoSave;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observer;

import static org.junit.jupiter.api.Assertions.*;

public class TestFileRead {
    private ToDoList myList;
    private Item itemToCompare;
    private Observer autoSave;
    private String fileNameToRead = "ForTestRead";

    @BeforeEach
    public void setup() {
        myList = new ToDoList();
        autoSave = new AutoSave();
    }

    @Test
    public void testFileRead() throws IOException {
        myList.fileRead(fileNameToRead, autoSave);
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
