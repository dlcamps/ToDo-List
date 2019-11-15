package model.observer;

import model.ItemRegular;
import model.ItemUrgent;
import model.ToDoList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Observer;

import static org.junit.jupiter.api.Assertions.*;

public class TestAutoSave {
    private Observer autoSave;
    private String readName;
    private String writeName;
    private ToDoList myList;
    private ItemRegular iR;
    private ItemUrgent iU;

    @BeforeEach
    public void setup() {
        autoSave = new AutoSave();
        readName = "ForTestRead";
        writeName = "ForTestWrite";
        myList = new ToDoList();
        iR = new ItemRegular();
        iU = new ItemUrgent();
        iR.setName("Regular Item");
        iU.setName("Urgent Item");
    }

    @Test
    public void testUpdateFromAdd() throws IOException {
        assertTrue(myList.isEmpty());
        myList.add(iR);
        assertTrue(myList.contains(iR));
        myList.setFileName(1, readName);
        myList.fileRead(autoSave);
        assertTrue(myList.contains(iR));
    }

    @Test
    public void testUpdateFromRemove() throws IOException {
        assertTrue(myList.isEmpty());
        myList.add(iU);
        assertTrue(myList.contains(iU));
        myList.removeWithItem(iU);
        myList.setFileName(1, readName);
        myList.fileRead(autoSave);
        assertFalse(myList.contains(iU));
    }
}
