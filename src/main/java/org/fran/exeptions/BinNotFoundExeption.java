package org.fran.exeptions;

import java.io.IOException;

public class BinNotFoundExeption extends IOException {
    public BinNotFoundExeption(String message) {
        super(message);
    }
}
