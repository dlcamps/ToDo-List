package ui;

import model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.ItemRegular;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestItem extends Item {
    private ItemRegular i;

    @BeforeEach
    public void setup() {
        i = new ItemRegular();
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
