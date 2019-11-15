package observer;

import model.ToDoList;
import ui.Main;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Observable;
import java.util.Observer;

public class AutoSave implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        ToDoList tdl = (ToDoList) o;
        Main m = (Main) arg;
        String writeTo = m.getFileName(2);
        try {
            tdl.fileWrite(writeTo);
        } catch (FileNotFoundException e) {
            //
        } catch (UnsupportedEncodingException e) {
            //
        }
    }
}
