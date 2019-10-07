package ui;

import model.ItemRegular;
import model.ToDoList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFileRead {
    ToDoList myList;
    ArrayList<ItemRegular> list;
    ItemRegular itemToImport;
    List<String> lines;
    ItemRegular itemToCompare;
    Integer listCounter;
    Boolean noErrors;

    @BeforeEach
    public void setup() {
        myList = new ToDoList();
        list = new ArrayList<ItemRegular>();
    }

    @Test
    public void testFileRead() throws IOException {
        lines = Files.readAllLines(Paths.get("ForTestRead.txt"));
        for (String line : lines) {
            //ArrayList<String> partsOfLine = splitOnSpace(line);
            itemToImport = new ItemRegular();
            itemToImport.setName(line);
            list.add(itemToImport);
        }
        itemToCompare = list.get(0);
        assertEquals(itemToCompare.getName(), "ToDo1");
        itemToCompare = list.get(1);
        assertEquals(itemToCompare.getName(), "ToDo2");
        itemToCompare = list.get(2);
        assertEquals(itemToCompare.getName(), "ToDo3");
    }

/*    @Test
    public void testFileRead() throws IOException {
        listCounter = 0;
        lines = Files.readAllLines(Paths.get("ToDoList.txt"));
        for (String line : lines) {
            itemToImport = new Item();
            itemToImport.setName(line);
            list.add(itemToImport);
            for (Item i : list) {
                if (i.getName() == lines.get(listCounter)) {
                    noErrors = true;
                    listCounter++;
                }
                noErrors = false;
            }
        }
        assertTrue(noErrors);
    }*/
}
