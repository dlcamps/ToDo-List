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
    ArrayList<ItemRegular> listRegular = new ArrayList<ItemRegular>();
    ArrayList<ItemUrgent> listUrgent = new ArrayList<ItemUrgent>();
    String newLine = System.getProperty("line.separator");
    Integer itemLine;
    ItemRegular itemToBeRemovedRegular;
    ItemUrgent itemToBeRemovedUrgent;
    Integer itemListPosition;
    ItemRegular itemToImport;
    String nameToExport;
    List<String> lines;
    PrintWriter writer;

    public void fileRead() throws IOException {
        lines = Files.readAllLines(Paths.get("ToDoList.txt"));
        for (String line : lines) {
            //ArrayList<String> partsOfLine = splitOnSpace(line);
            itemToImport = new ItemRegular();
            itemToImport.setName(line);
            listRegular.add(itemToImport);
        }
    }

    public void fileWrite() throws FileNotFoundException, UnsupportedEncodingException {
        writer = new PrintWriter("ToDoList.txt","UTF-8");
        for (ItemRegular i : listRegular) {
            nameToExport = i.getName();
            writer.println(nameToExport);
        }
        writer.close();
    }

/*    public static ArrayList<String> splitOnSpace(String line) {
        String[] splits = line.split(" ");
        return new ArrayList<>(Arrays.asList(splits));
    }*/

    public void addRegular(ItemRegular i) {
        listRegular.add(i);
    }

    public void addUrgent(ItemUrgent i) {
        listUrgent.add(i);
    }

    public void removeRegular(String s) {
        itemListPosition = Integer.parseInt(s);
        itemToBeRemovedRegular = listRegular.get(itemListPosition - 1);
        listRegular.remove(itemToBeRemovedRegular);
    }

    public void removeUrgent(String s) {
        itemListPosition = Integer.parseInt(s);
        itemToBeRemovedUrgent = listUrgent.get(itemListPosition - 1);
        listRegular.remove(itemToBeRemovedUrgent);
    }

    public void showItems(ToDoList tdl) {
        itemLine = 1;
        System.out.println(newLine + "[TO-DO]");
        for (ItemRegular i: listRegular) {
            System.out.println("(" + itemLine + ")" + " " + i.getName());
            itemLine++;
        }
        System.out.println(newLine);
    }
}
