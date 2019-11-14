package model;

import java.io.IOException;
import java.util.Observer;

public interface FileRead {
    void fileRead(String fileNameToRead, Observer observer) throws IOException;
}
