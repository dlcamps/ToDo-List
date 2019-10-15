package model;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public interface FileWrite {
    void fileWrite(String fileNameToWrite) throws FileNotFoundException, UnsupportedEncodingException;
}
