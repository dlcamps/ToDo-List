package model.exceptions;

import model.Item;
import model.ItemRegular;
import model.ToDoList;
import model.exceptions.RemoveOnEmptyListException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Main;

import static org.junit.jupiter.api.Assertions.*;

public class TestRemoveOnEmptyListException {
    ToDoList myList;
    Item item;

    @BeforeEach
    public void setup () {
        myList = new ToDoList();
        item = new ItemRegular();
    }

    @Test
    public void testRemoveOnNotEmptyList() {
        myList.add(item);
        try {
            //option2();
        } catch (RemoveOnEmptyListException e) {
            fail("ERROR: Caught RemoveOnEmptyListException");
        }
        myList.remove(item.getName());
        try {
            //option2();
        } catch (RemoveOnEmptyListException e) {
            // Success
        }
    }
}
