package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ToDoList extends Observable implements FileRead, FileWrite {

    ArrayList<Item> list = new ArrayList<Item>();
    Item itemToBeRemoved;
    Integer itemListPosition;
    Item itemToImport;
    String nameToExport;
    List<String> lines;
    PrintWriter writer;
    Map<String, Integer> itemLocations = new HashMap<>(); // 1 = Regular, 2 = Urgent
    String fileNameToRead = "ToDoList";
    String fileNameToWrite = "ToDoList";

    public void fileRead(Observer observer) throws IOException {
        lines = Files.readAllLines(Paths.get("data/" + fileNameToRead + ".txt"));
        for (String line : lines) {
            if (line.trim().length() == 0) {
                continue;
            } else if ((line.length() >= 5) && (line.substring(0, 5).equals("[!!!]"))) {
                itemToImport = new ItemUrgent();
                itemToImport.setName(line.substring(6));
                itemLocations.put(itemToImport.removeUrgentTag(itemToImport), 2);
            } else {
                itemToImport = new ItemRegular();
                itemToImport.setName(line);
                itemLocations.put(itemToImport.getName(), 1);
            }
            list.add(itemToImport);
        }
        addObserver(observer);
    }

    public void fileWrite() throws FileNotFoundException, UnsupportedEncodingException {
        writer = new PrintWriter("data/" + fileNameToWrite + ".txt","UTF-8");
        for (Item i : list) {
            nameToExport = i.getName();
            writer.println(nameToExport);
        }
        writer.close();
    }

    public void add(Item i) {
        list.add(i);
        if (i instanceof ItemRegular) {
            itemLocations.put(i.getName(), 1);
        } else if (i instanceof ItemUrgent) {
            itemLocations.put(i.removeUrgentTag(i), 2);
        }
        setChanged();
        notifyObservers();
    }

    public void removeWithString(String s) {
        itemListPosition = Integer.parseInt(s);
        itemToBeRemoved = list.get(itemListPosition - 1);
        list.remove(itemToBeRemoved);
        if (itemToBeRemoved instanceof ItemUrgent) {
            itemLocations.remove(itemToBeRemoved.removeUrgentTag(itemToBeRemoved));
        } else {
            itemLocations.remove(itemToBeRemoved.getName());
        }
        setChanged();
        notifyObservers();
    }

    public void setFileName(Integer i, String name) { // 1 = Read, 2 = Write
        if (i == 1) {
            this.fileNameToRead = name;
        } else if (i == 2) {
            this.fileNameToWrite = name;
        }
    }

    public ArrayList<Item> getList() {
        return list;
    }

    public Map<String, Integer> getMap() {
        return itemLocations;
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    // For testing
    public void removeWithItem(Item i) {
        list.remove(i);
        if (i instanceof ItemUrgent) {
            itemLocations.remove(i.removeUrgentTag(i));
        } else {
            itemLocations.remove(i.getName());
        }
    }

    // For testing
    public boolean contains(Item i) {
        return list.contains(i);
    }

    // For testing
    public Item getItem(int i) {
        return list.get(i);
    }
}
