package ui;

import java.util.ArrayList;
import java.util.Scanner;

public class ToDoList {
    ArrayList<String> myList = new ArrayList<String>();
    //ArrayList<ToDoList> crossedToDoList = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    public ToDoList() {
        int choice = 0;
        //ListEntry entry = new ListEntry();

        while (true) {
            System.out.println("Select an Option:");
            System.out.println("[1] Add an Item");
            System.out.println("[2] Remove an Item");
            System.out.println("[3] Show All Items");
            System.out.println("[4] Quit the Program");

            choice = scanner.nextInt();
            //System.out.println("You selected: " + choice);

            if (choice == 1) {
                System.out.println("Enter the Item Text: ");
                myList.add(scanner.nextLine());
            }
            if (choice == 2) {
                System.out.println(myList);
                System.out.println("Type the Item to Remove");
            }
            if (choice == 3) {
                System.out.println(myList);
            }
            if (choice == 4) {
                break;
            }
        }

    }

    public static void main(String[] args) {
        new ToDoList();
    }
}