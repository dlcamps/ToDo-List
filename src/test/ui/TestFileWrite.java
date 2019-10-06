package ui;

import model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFileWrite {
    ArrayList<Item> list;
    Item i1;
    Item i2;
    Item i3;
    PrintWriter writer;
    String nameToExport;
    List<String> lines;
    Item itemToImport;
    Item itemToCompare;

    @BeforeEach
    public void setup() {
        list = new ArrayList<Item>();
        i1 = new Item();
        i2 = new Item();
        i3 = new Item();
        i1.setName("ToDo1");
        i2.setName("ToDo2");
        i3.setName("ToDo3");
        list.add(i1);
        list.add(i2);
        list.add(i3);
    }

    @Test
    public void testFileWrite() throws IOException {
        writer = new PrintWriter("ForTestWrite.txt","UTF-8");
        for (Item i : list) {
            nameToExport = i.getName();
            writer.println(nameToExport);
        }
        writer.close();
        lines = Files.readAllLines(Paths.get("ForTestWrite.txt"));
        for (String line : lines) {
            //ArrayList<String> partsOfLine = splitOnSpace(line);
            itemToImport = new Item();
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
}
