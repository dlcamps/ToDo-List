package model;

import java.io.IOException;
import java.util.Observer;

public interface FileRead {
    void fileRead(Observer observer) throws IOException;
}
