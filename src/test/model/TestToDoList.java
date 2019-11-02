package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

public class TestToDoList {
    private ToDoList myList;
    private ItemRegular itemRegular;
    private ItemUrgent itemUrgent;
    private Map<String, Integer> itemLocations;

    @BeforeEach
    public void setup() {
        myList = new ToDoList();
        itemLocations = new HashMap<>();
        itemRegular = new ItemRegular();
        itemRegular.setName("Regular Item 1");
        itemUrgent = new ItemUrgent();
        itemUrgent.setName("Urgent Item 1");
    }
    @Test
    public void addTest() {
        assertFalse(myList.contains(itemRegular));
        assertFalse(itemLocations.containsKey(itemRegular.getName()));
        myList.add(itemRegular);
        itemLocations.put(itemRegular.getName(), 1);
        assertTrue(myList.contains(itemRegular));
        assertTrue(itemLocations.containsKey(itemRegular.getName()));

        assertFalse(myList.contains(itemUrgent));
        assertFalse(itemLocations.containsKey(itemUrgent.getName().substring(6)));
        myList.add(itemUrgent);
        itemLocations.put(itemUrgent.getName().substring(6), 1);
        assertTrue(myList.contains(itemUrgent));
        assertTrue(itemLocations.containsKey(itemUrgent.getName().substring(6)));
    }
    @Test
    public void removeTest() {
        myList.add(itemRegular);
        itemLocations.put(itemRegular.getName(), 1);
        assertTrue(myList.contains(itemRegular));
        assertTrue(itemLocations.containsKey(itemRegular.getName()));
        myList.removeWithItem(itemRegular);
        itemLocations.remove(itemRegular.getName());
        assertFalse(myList.contains(itemRegular));
        assertFalse(itemLocations.containsKey(itemRegular.getName()));

        myList.add(itemUrgent);
        itemLocations.put(itemUrgent.getName().substring(6), 2);
        assertTrue(myList.contains(itemUrgent));
        assertTrue(itemLocations.containsKey(itemUrgent.getName().substring(6)));
        myList.removeWithItem(itemUrgent);
        itemLocations.remove(itemUrgent.getName().substring(6));
        assertFalse(myList.contains(itemUrgent));
        assertFalse(itemLocations.containsKey(itemUrgent.getName().substring(6)));
    }
}
