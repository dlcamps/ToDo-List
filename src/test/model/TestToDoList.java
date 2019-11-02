package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestToDoList {
    private ToDoList myList;
    private ItemRegular iR; // Regular Item
    private ItemUrgent iU; // Urgent Item
    private Map<String, Integer> itemLocations;
    private ArrayList<Item> testList;
    private Map<String, Integer> testMap;

    @BeforeEach
    public void setup() {
        myList = new ToDoList();
        testList = new ArrayList<Item>();
        itemLocations = new HashMap<>();
        testMap = new HashMap<>();

        iR = new ItemRegular();
        iR.setName("Regular Item");
        iU = new ItemUrgent();
        iU.setName("Urgent Item");
    }
    @Test
    public void testGetList() {
        myList.add(iR);
        testList = myList.getList();
        assertTrue(testList.contains(iR));
    }
    @Test
    public void testGetMap() {
        myList.add(iR);
        itemLocations.put(iR.getName(), 1);
        assertTrue(myList.contains(iR));
        assertTrue(itemLocations.containsKey(iR.getName()));
        testMap = myList.getMap();
        assertTrue(testMap.containsKey("Regular Item"));
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
    public void testRemoveWithString() {
        myList.add(iR);
        myList.add(iU);
        myList.removeWithString("1");
        assertFalse(myList.contains(iR));
        assertTrue(myList.contains(iU));
        myList.removeWithString("1");
        assertFalse(myList.contains(iU));
        myList.add(iR);
        myList.add(iU);
        myList.removeWithString("2");
        assertFalse(myList.contains(iU));
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
