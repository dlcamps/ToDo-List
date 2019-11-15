package model.observer;

import model.ToDoList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Main;

import java.util.Observer;

public class TestAutoSave {
    private Observer autoSave;
    private String writeName;
    private ToDoList myList;

    @BeforeEach
    public void setup() {
        autoSave = new AutoSave();
        writeName = "ForTestWrite";
        myList = new ToDoList();
    }
}
