package model.observer;

import model.ToDoList;
import ui.Main;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Observable;
import java.util.Observer;

public class AutoSave implements Observer {
    @Override
    // REQUIRES: Observable and observer
    // MODIFIES: ToDoList
    // EFFECTS: Calls fileWrite any time an item is added or removed
    public void update(Observable o, Object arg) {
        ToDoList tdl = (ToDoList) o;
        try {
            tdl.fileWrite();
        } catch (FileNotFoundException e) {
            //
        } catch (UnsupportedEncodingException e) {
            //
        }
    }
}
