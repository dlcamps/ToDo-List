package ui;

import model.Item;
import model.ItemRegular;
import model.ItemUrgent;
import model.ToDoList;
import model.exceptions.RemoveOnEmptyListException;
import model.observer.AutoSave;
import network.ReadWebPage;

import java.io.IOException;
import java.util.*;

// TODO: Implement overridden getName() in ItemUrgent
// TODO: Refactor option4() & option5() to move ToDoList related methods out of Main

public class Main {
    ToDoList myList;
    Integer itemLine;
    ArrayList<Item> printList;
    Map<String, Integer> printMap;
    Observer autoSave = new AutoSave();
    Item newItem;
    String itemType;
    String choice;
    Scanner scanner = new Scanner(System.in);
    String newLine = System.getProperty("line.separator");
    ArrayList<String> itemNameImportList;

    public static void main(String[] args) throws IOException, RemoveOnEmptyListException {
        //ReadWebPage.main(null);
        new Main();
    }

    public Main() throws IOException, RemoveOnEmptyListException {

        myList = new ToDoList();
        myList.fileRead(autoSave);

        System.out.println("[1] Add an Item");
        System.out.println("[2] Remove an Item");
        System.out.println("[3] Show All Items");
        System.out.println("[4] Show All Urgent Items");
        System.out.println("[5] Quit the Program" + newLine);

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
                option4();
            }
            if (choice.equals("5")) {
                System.out.println("Exiting...");
                break;
            }
        }
    }

    // REQUIRES: N/A
    // MODIFIES: Displays sub-menu to choose item type
    // EFFECTS: Adds item to the list
    public void option1() {
        System.out.println("Select Item Type: [1] Regular [2] Urgent");
        scanner.nextLine();
        itemType = scanner.nextLine();
        if (itemType.equals("1")) {                 // Regular
            System.out.println("Enter the Item Text: ");
            newItem = new ItemRegular();
            newItem.setName(scanner.nextLine());
            myList.add(newItem);
        } else if (itemType.equals("2")) {          // Urgent
            System.out.println("Enter the URGENT Item Text: ");
            newItem = new ItemUrgent();
            newItem.setName(scanner.nextLine());
            myList.add(newItem);
        }
        System.out.println("*** Item Added ***" + newLine);
    }

    // REQUIRES: N/A
    // MODIFIES: Displays sub-menu to remove an item
    // EFFECTS: Removes selected item
    public void option2() throws RemoveOnEmptyListException {
        try {
            if (myList.isEmpty()) {
                throw new RemoveOnEmptyListException();
            } else if (!myList.isEmpty()) {
                option3();
                System.out.println("Type the Line Number of the Item to Remove: ");
                scanner.nextLine();
                myList.removeWithString(scanner.nextLine());
                System.out.println("*** Item Removed ***" + newLine);
            }
        } catch (RemoveOnEmptyListException e) {
            return;
        } finally {
            return;
        }
    }

    // REQUIRES: N/A
    // MODIFIES: N/A
    // EFFECTS: Displays all items
    public void option3() {
        itemLine = 1;
        printList = new ArrayList<>();
        printList = myList.getList();

        System.out.println(newLine + "—————[TO-DO]—————");
        for (Item i: printList) {
            System.out.println("(" + itemLine + ")" + " " + i.getName());
            itemLine++;
        }
        System.out.println(newLine);
    }

    // REQUIRES: N/A
    // MODIFIES: N/A
    // EFFECTS: Displays urgent items only
    public void option4() {
        printMap = myList.getMap();

        System.out.println("—————[URGENT]—————");
        for (String s : printMap.keySet()) {
            if (printMap.get(s) == 2) {
                System.out.println("- " + s);
            }
        }
        System.out.println(newLine);
    }
}