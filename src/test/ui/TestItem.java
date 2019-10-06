package ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.Item;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestItem {
    private Item i;

    @BeforeEach
    public void setup() {
        i = new Item();
    }
    @Test
    public void setNameTest() {
        i.setName("Task");
        assertTrue(i.name == "Task");
    }
    @Test
    public void getNameTest() {
        i.setName("Task");
        assertTrue(i.getName() == "Task");
    }
}
