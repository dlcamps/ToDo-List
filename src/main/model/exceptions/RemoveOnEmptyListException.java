package model.exceptions;

public class RemoveOnEmptyListException extends Exception {
    String newLine = System.getProperty("line.separator");

    public RemoveOnEmptyListException() {
        System.out.println("ERROR: No items in the list to remove, please add an item: " + newLine);
    }
}
