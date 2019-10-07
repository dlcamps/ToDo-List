package ui;

import model.ItemRegular;
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
    ArrayList<ItemRegular> list;
    ItemRegular i1;
    ItemRegular i2;
    ItemRegular i3;
    PrintWriter writer;
    String nameToExport;
    List<String> lines;
    ItemRegular itemToImport;
    ItemRegular itemToCompare;

    @BeforeEach
    public void setup() {
        list = new ArrayList<ItemRegular>();
        i1 = new ItemRegular();
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
        writer = new PrintWriter("ForTestWrite.txt","UTF-8");
        for (ItemRegular i : list) {
            nameToExport = i.getName();
            writer.println(nameToExport);
        }
        writer.close();
        lines = Files.readAllLines(Paths.get("ForTestWrite.txt"));
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
}
