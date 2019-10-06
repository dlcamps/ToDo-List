package ui;

import model.Item;
import model.ToDoList;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    ToDoList myList;
    Item newItem;
    String choice;
    Scanner scanner = new Scanner(System.in);
    String newLine = System.getProperty("line.separator");

    public static void main(String[] args) throws IOException {
        new Main();
    }

    public Main() throws IOException {

        myList = new ToDoList();
        myList.fileRead();

        System.out.println("[1] Add an Item");
        System.out.println("[2] Remove an Item");
        System.out.println("[3] Show All Items");
        System.out.println("[4] Save the File & Quit the Program" + newLine);

        while (true) {
            System.out.println("Select an Option:");
            choice = scanner.next();

            if (choice.equals("1")) {
                option1();
            }
            if (choice.equals("2")) {
                option2();
            }
            if (choice.equals("3")) {
                option3();
            }
            if (choice.equals("4")) {
                myList.fileWrite();
                break;
            }
        }
    }

    public void option1() {
        System.out.println("Enter the Item Text: ");
        newItem = new Item();
        scanner.nextLine();
        newItem.setName(scanner.nextLine());
        myList.add(newItem);
        System.out.println("Item Added" + newLine);
    }

    public void option2() {
        myList.showItems(myList);
        System.out.println("Type the Line Number of the Item to Remove: ");
        scanner.nextLine();
        myList.remove(scanner.nextLine());
        System.out.println("Item Removed" + newLine);
    }

    public void option3() {
        myList.showItems(myList);
    }
}