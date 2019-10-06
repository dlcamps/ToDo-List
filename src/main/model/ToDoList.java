package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ToDoList implements FileRead, FileWrite {
    ArrayList<Item> list = new ArrayList<Item>();
    String newLine = System.getProperty("line.separator");
    Integer itemLine;
    Item itemToBeRemoved;
    Integer itemListPosition;
    Item itemToImport;
    String nameToExport;
    List<String> lines;
    PrintWriter writer;

    public void fileRead() throws IOException {
        lines = Files.readAllLines(Paths.get("ToDoList.txt"));
        for (String line : lines) {
            //ArrayList<String> partsOfLine = splitOnSpace(line);
            itemToImport = new Item();
            itemToImport.setName(line);
            list.add(itemToImport);
        }
    }

    public void fileWrite() throws FileNotFoundException, UnsupportedEncodingException {
        writer = new PrintWriter("ToDoList.txt","UTF-8");
        for (Item i : list) {
            nameToExport = i.getName();
            writer.println(nameToExport);
        }
        writer.close();
    }

/*    public static ArrayList<String> splitOnSpace(String line) {
        String[] splits = line.split(" ");
        return new ArrayList<>(Arrays.asList(splits));
    }*/

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
