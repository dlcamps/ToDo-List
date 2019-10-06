package ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.Item;
import model.ToDoList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

public class TestToDoList {
    private ArrayList<Item> list;
    private Item i;

    @BeforeEach
    public void setup() {
        list = new ArrayList<Item>();
        i = new Item();
    }
    @Test
    public void addTest() {
        assertFalse(list.contains(i));
        list.add(i);
        assertTrue(list.contains(i));
    }
    @Test
    public void removeTest() {
        list.add(i);
        assertTrue(list.contains(i));
        list.remove(i);
        assertFalse(list.contains(i));
    }
}
