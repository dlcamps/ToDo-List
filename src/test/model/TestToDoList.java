package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestToDoList {
    private ArrayList<Item> list;
    private ItemRegular itemRegular;
    private ItemUrgent itemUrgent;
    private Map<String, Integer> itemLocations;

    @BeforeEach
    public void setup() {
        list = new ArrayList<Item>();
        itemLocations = new HashMap<>();
        itemRegular = new ItemRegular();
        itemRegular.setName("Regular Item 1");
        itemUrgent = new ItemUrgent();
        itemUrgent.setName("Urgent Item 1");
    }
    @Test
    public void addTest() {
        assertFalse(list.contains(itemRegular));
        assertFalse(itemLocations.containsKey(itemRegular.getName()));
        list.add(itemRegular);
        itemLocations.put(itemRegular.getName(), 1);
        assertTrue(list.contains(itemRegular));
        assertTrue(itemLocations.containsKey(itemRegular.getName()));

        assertFalse(list.contains(itemUrgent));
        assertFalse(itemLocations.containsKey(itemUrgent.getName().substring(6)));
        list.add(itemUrgent);
        itemLocations.put(itemUrgent.getName().substring(6), 1);
        assertTrue(list.contains(itemUrgent));
        assertTrue(itemLocations.containsKey(itemUrgent.getName().substring(6)));
    }
    @Test
    public void removeTest() {
        list.add(itemRegular);
        itemLocations.put(itemRegular.getName(), 1);
        assertTrue(list.contains(itemRegular));
        assertTrue(itemLocations.containsKey(itemRegular.getName()));
        list.remove(itemRegular);
        itemLocations.remove(itemRegular.getName());
        assertFalse(list.contains(itemRegular));
        assertFalse(itemLocations.containsKey(itemRegular.getName()));

        list.add(itemUrgent);
        itemLocations.put(itemUrgent.getName().substring(6), 2);
        assertTrue(list.contains(itemUrgent));
        assertTrue(itemLocations.containsKey(itemUrgent.getName().substring(6)));
        list.remove(itemUrgent);
        itemLocations.remove(itemUrgent.getName().substring(6));
        assertFalse(list.contains(itemUrgent));
        assertFalse(itemLocations.containsKey(itemUrgent.getName().substring(6)));
    }
}
