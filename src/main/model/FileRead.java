package model;

import java.io.IOException;
import java.util.Observer;

public interface FileRead {
    void fileRead(String readFile, Observer observer) throws IOException;
}
