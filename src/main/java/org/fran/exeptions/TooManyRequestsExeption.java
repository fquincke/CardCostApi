package org.fran.exeptions;

import java.io.IOException;

public class TooManyRequestsExeption extends IOException {
    public TooManyRequestsExeption(String message) {
        super(message);
    }
}
