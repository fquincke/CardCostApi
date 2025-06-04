package org.fran.exeptions;

import java.io.IOException;

public class InvalidCountryException extends IOException {
    public InvalidCountryException(String message) {
        super(message);
    }
}
