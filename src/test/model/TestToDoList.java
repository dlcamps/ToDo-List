package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestToDoList {
    private ToDoList myTestList;
    private ItemRegular iR; // Regular Item
    private ItemUrgent iU; // Urgent Item
    private Map<String, Integer> itemLocations;
    private ArrayList<Item> testList;
    private Map<String, Integer> testMap;

    @BeforeEach
    public void setup() {
        myTestList = new ToDoList();
        testList = new ArrayList<Item>();
        itemLocations = new HashMap<>();
        testMap = new HashMap<>();

        iR = new ItemRegular();
        iR.setName("Regular Item");
        iU = new ItemUrgent();
        iU.setName("Urgent Item");
    }
    @Test
    public void testAdd() {
        assertFalse(myTestList.contains(iR));
        assertFalse(itemLocations.containsKey(iR.getName()));

        myTestList.add(iR);
        itemLocations.put(iR.getName(), 1);

        assertTrue(myTestList.contains(iR));
        assertTrue(itemLocations.containsKey(iR.getName()));

        assertFalse(myTestList.contains(iU));
        assertFalse(itemLocations.containsKey(iU.removeUrgentTag(iU)));

        myTestList.add(iU);
        itemLocations.put(iU.removeUrgentTag(iU), 1);

        assertTrue(myTestList.contains(iU));
        assertTrue(itemLocations.containsKey(iU.removeUrgentTag(iU)));
    }
    @Test
    public void testCreateItem() {
        assertTrue(myTestList.isEmpty());
        myTestList.createItem(1, "Test 1 - Regular");
        assertTrue(myTestList.getItem(0).getName().equals("Test 1 - Regular"));
        myTestList.createItem(2, "Test 2 - Urgent");
        assertTrue(myTestList.getItem(1).getName().equals("[!!!] Test 2 - Urgent"));
    }
    @Test
    public void testRemoveWithString() {
        myTestList.add(iR);
        myTestList.add(iU);
        myTestList.removeWithString("1");
        assertFalse(myTestList.contains(iR));
        assertTrue(myTestList.contains(iU));
        myTestList.removeWithString("1");
        assertFalse(myTestList.contains(iU));
        myTestList.add(iR);
        myTestList.add(iU);
        myTestList.removeWithString("2");
        assertFalse(myTestList.contains(iU));
    }
    @Test
    public void testRemoveWithItem() {
        myTestList.add(iR);
        itemLocations.put(iR.getName(), 1);

        assertTrue(myTestList.contains(iR));
        assertTrue(itemLocations.containsKey(iR.getName()));

        myTestList.removeWithItem(iR);
        itemLocations.remove(iR.getName());

        assertFalse(myTestList.contains(iR));
        assertFalse(itemLocations.containsKey(iR.getName()));

        myTestList.add(iU);
        itemLocations.put(iU.removeUrgentTag(iU), 2);

        assertTrue(myTestList.contains(iU));
        assertTrue(itemLocations.containsKey(iU.removeUrgentTag(iU)));

        myTestList.removeWithItem(iU);
        itemLocations.remove(iU.removeUrgentTag(iU));

        assertFalse(myTestList.contains(iU));
        assertFalse(itemLocations.containsKey(iU.removeUrgentTag(iU)));
    }
    @Test
    public void testGetList() {
        myTestList.add(iR);
        testList = myTestList.getList();
        assertTrue(testList.contains(iR));
    }
    @Test
    public void testGetMap() {
        myTestList.add(iR);
        itemLocations.put(iR.getName(), 1);
        assertTrue(myTestList.contains(iR));
        assertTrue(itemLocations.containsKey(iR.getName()));
        testMap = myTestList.getMap();
        assertTrue(testMap.containsKey("Regular Item"));
    }
}
