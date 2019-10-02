package ui.model;

import java.util.ArrayList;

public class ToDoList {
    ArrayList<Item> list = new ArrayList<Item>();
    String newLine = System.getProperty("line.separator");

    public void add(Item i) {
        list.add(i);
    }

    public void remove(Item i) {
        list.remove(i);
    }

    public void showItems(ToDoList tdl) {
        System.out.println(newLine);
        System.out.println("TO DO:");
        for (Item i: list) {
            System.out.println(i.getName());
        }
        System.out.println(newLine);
    }
}
