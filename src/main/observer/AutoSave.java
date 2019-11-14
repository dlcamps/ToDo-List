package observer;

import model.ToDoList;

import java.util.Observable;
import java.util.Observer;

public class AutoSave implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        ToDoList tdl = (ToDoList) o;
        ((ToDoList) o).fileWrite();
    }
}
