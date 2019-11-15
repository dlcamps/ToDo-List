package model;

import model.observer.AutoSave;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Observer;

import static org.junit.jupiter.api.Assertions.*;

public class TestFileWrite {
    private ToDoList myList;
    private ItemUrgent i1;
    private ItemRegular i2;
    private ItemRegular i3;
    private Item itemToCompare;
    private String writeName = "ForTestWrite";
    private Observer autoSave;

    @BeforeEach
    public void setup() {
        myList = new ToDoList();
        autoSave = new AutoSave();
        i1 = new ItemUrgent();
        i2 = new ItemRegular();
        i3 = new ItemRegular();
        i1.setName("ToDo1");
        i2.setName("ToDo2");
        i3.setName("ToDo3");
        myList.add(i1);
        myList.add(i2);
        myList.add(i3);
    }
    @Test
    public void testFileWrite() throws IOException {
        myList.setFileName(1, writeName);
        myList.setFileName(2, writeName);
        assertTrue(myList.fileNameToRead.equals("ForTestWrite"));
        assertTrue(myList.fileNameToWrite.equals("ForTestWrite"));
        myList.fileWrite();
        myList.fileRead(autoSave);
        itemToCompare = myList.getItem(0);
        assertEquals(itemToCompare.getName(), "[!!!] ToDo1");
        itemToCompare = myList.getItem(1);
        assertEquals(itemToCompare.getName(), "ToDo2");
        itemToCompare = myList.getItem(2);
        assertEquals(itemToCompare.getName(), "ToDo3");
    }
}
