package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToDoList implements FileRead, FileWrite {

    private ArrayList<Item> list = new ArrayList<Item>();
    String newLine = System.getProperty("line.separator");
    Integer itemLine;
    Item itemToBeRemoved;
    Integer itemListPosition;
    Item itemToImport;
    String nameToExport;
    List<String> lines;
    PrintWriter writer;
    Map<String, Integer> itemLocations = new HashMap<>(); // 1 = Regular, 2 = Urgent

    public void fileRead(String fileNameToRead) throws IOException {
        lines = Files.readAllLines(Paths.get("data/" + fileNameToRead + ".txt"));
        for (String line : lines) {
            if (line.trim().length() == 0) {
                continue;
            } else if ((line.length() >= 5) && (line.substring(0, 5).equals("[!!!]"))) {
                itemToImport = new ItemUrgent();
                itemToImport.setName(line.substring(6));
                itemLocations.put(itemToImport.getName().substring(6), 2);
            } else {
                itemToImport = new ItemRegular();
                itemToImport.setName(line);
                itemLocations.put(itemToImport.getName(), 1);
            }
            list.add(itemToImport);
        }
    }

    public void fileWrite(String fileNameToWrite) throws FileNotFoundException, UnsupportedEncodingException {
        writer = new PrintWriter("data/" + fileNameToWrite + ".txt","UTF-8");
        for (Item i : list) {
            nameToExport = i.getName();
            writer.println(nameToExport);
        }
        writer.close();
    }

    public Item getItem(int i) {
        return list.get(i);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void add(Item i) {
        list.add(i);
        if (i instanceof ItemRegular) {
            itemLocations.put(i.getName(), 1);
        } else if (i instanceof ItemUrgent) {
            itemLocations.put(i.getName().substring(6), 2);
        }
    }

    public void remove(String s) {
        itemListPosition = Integer.parseInt(s);
        itemToBeRemoved = list.get(itemListPosition - 1);
        list.remove(itemToBeRemoved);
        itemLocations.remove(itemToBeRemoved.getName().substring(6));
    }

    public void showItems(ToDoList tdl) {
        itemLine = 1;
        System.out.println(newLine + "—————[TO-DO]—————");
        for (Item i: list) {
            System.out.println("(" + itemLine + ")" + " " + i.getName());
            itemLine++;
        }
        System.out.println(newLine);
    }

    public void showUrgentItems(ToDoList tdl) {
        System.out.println("—————[URGENT]—————");
        for (String s : itemLocations.keySet()) {
            if (itemLocations.get(s) == 2) {
                System.out.println("- " + s);
            }
        }
        System.out.println(newLine);
    }
}
