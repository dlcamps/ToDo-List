package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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

    // fileRead() version with specified file to read
    public void fileRead(String fileNameToRead) throws IOException {
        lines = Files.readAllLines(Paths.get("data/" + fileNameToRead + ".txt"));
        for (String line : lines) {
            if (line.trim().length() == 0) {
                continue;
            } else if ((line.length() >= 5) && (line.substring(0, 5).equals("[!!!]"))) {
                itemToImport = new ItemUrgent();
                itemToImport.setName(line.substring(6));
            } else {
                itemToImport = new ItemRegular();
                itemToImport.setName(line);
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
    }

    public void remove(String s) {
        itemListPosition = Integer.parseInt(s);
        itemToBeRemoved = list.get(itemListPosition - 1);
        list.remove(itemToBeRemoved);
    }

    public void showItems(ToDoList tdl) {
        itemLine = 1;
        System.out.println(newLine + "[TO-DO]");
        for (Item i: list) {
            System.out.println("(" + itemLine + ")" + " " + i.getName());
            itemLine++;
        }
        System.out.println(newLine);
    }
}
