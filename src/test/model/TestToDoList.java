package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

public class TestToDoList {
    private ToDoList myList;
    private ItemRegular iR; // Regular Item
    private ItemUrgent iU; // Urgent Item
    private Map<String, Integer> itemLocations;

    @BeforeEach
    public void setup() {
        myList = new ToDoList();
        itemLocations = new HashMap<>();
        iR = new ItemRegular();
        iR.setName("Regular Item 1");
        iU = new ItemUrgent();
        iU.setName("Urgent Item 1");
    }
    @Test
    public void testAdd() {
        assertFalse(myList.contains(iR));
        assertFalse(itemLocations.containsKey(iR.getName()));

        myList.add(iR);
        itemLocations.put(iR.getName(), 1);

        assertTrue(myList.contains(iR));
        assertTrue(itemLocations.containsKey(iR.getName()));

        assertFalse(myList.contains(iU));
        assertFalse(itemLocations.containsKey(iU.removeUrgentTag(iU)));

        myList.add(iU);
        itemLocations.put(iU.removeUrgentTag(iU), 1);

        assertTrue(myList.contains(iU));
        assertTrue(itemLocations.containsKey(iU.removeUrgentTag(iU)));
    }
    @Test
    public void testRemoveWithItem() {
        myList.add(iR);
        itemLocations.put(iR.getName(), 1);

        assertTrue(myList.contains(iR));
        assertTrue(itemLocations.containsKey(iR.getName()));

        myList.removeWithItem(iR);
        itemLocations.remove(iR.getName());

        assertFalse(myList.contains(iR));
        assertFalse(itemLocations.containsKey(iR.getName()));

        myList.add(iU);
        itemLocations.put(iU.removeUrgentTag(iU), 2);

        assertTrue(myList.contains(iU));
        assertTrue(itemLocations.containsKey(iU.removeUrgentTag(iU)));

        myList.removeWithItem(iU);
        itemLocations.remove(iU.removeUrgentTag(iU));

        assertFalse(myList.contains(iU));
        assertFalse(itemLocations.containsKey(iU.removeUrgentTag(iU)));
    }
}
