package ui;

import model.Item;
import model.ItemRegular;
import model.ItemUrgent;
import model.ToDoList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestFileWrite {
    ToDoList myList;
    ArrayList<Item> list;
    ItemUrgent i1;
    ItemRegular i2;
    ItemRegular i3;
    Item itemToCompare;

    @BeforeEach
    public void setup() {
        myList = new ToDoList();
        list = new ArrayList<Item>();
        i1 = new ItemUrgent();
        i2 = new ItemRegular();
        i3 = new ItemRegular();
        i1.setName("ToDo1");
        i2.setName("ToDo2");
        i3.setName("ToDo3");
        list.add(i1);
        list.add(i2);
        list.add(i3);
    }

    @Test
    public void testFileWrite() throws IOException {
        myList.fileWrite("ForTestWrite");
        myList.fileRead("ForTestWrite");
        itemToCompare = list.get(0);
        assertEquals(itemToCompare.getName(), "[!!!] ToDo1");
        itemToCompare = list.get(1);
        assertEquals(itemToCompare.getName(), "ToDo2");
        itemToCompare = list.get(2);
        assertEquals(itemToCompare.getName(), "ToDo3");
    }
}
