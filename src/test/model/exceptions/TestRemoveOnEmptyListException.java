package model.exceptions;

import model.Item;
import model.ItemRegular;
import model.ToDoList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
            if (myList.isEmpty()) {
                throw new RemoveOnEmptyListException();
            } else if (!myList.isEmpty()) {
                // Success
            }
        } catch (RemoveOnEmptyListException e) {
            fail("ERROR: Caught RemoveOnEmptyListException");
        }
    }

    @Test
    public void testRemoveOnEmptyList() {
        try {
            if (myList.isEmpty()) {
                throw new RemoveOnEmptyListException();
            } else if (!myList.isEmpty()) {
                myList.removeWithString(item.getName());
            }
        } catch (RemoveOnEmptyListException e) {
            // Success
        }
    }
}
