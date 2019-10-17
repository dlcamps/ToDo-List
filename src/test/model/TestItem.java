package model;

import model.Item;
import model.ItemUrgent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.ItemRegular;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestItem extends Item {
    private ItemRegular iR;
    private ItemUrgent iU;

    @BeforeEach
    public void setup() {
        iR = new ItemRegular();
        iU = new ItemUrgent();
    }
    @Test
    public void setNameTest() {
        iR.setName("Regular Task");
        assertTrue(iR.name.equals("Regular Task"));
        iU.setName("Urgent Task");
        assertTrue(iU.name.equals("[!!!] Urgent Task"));
    }
    @Test
    public void getNameTest() {
        iR.setName("Regular Task");
        assertTrue(iR.getName().equals("Regular Task"));
        iU.setName(("Urgent Task"));
        assertTrue(iU.getName().equals("[!!!] Urgent Task"));
    }
}
