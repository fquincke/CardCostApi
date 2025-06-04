package org.fran.exeptions;

import java.io.IOException;

public class CountryAlreadyExistsException extends IOException {
    public CountryAlreadyExistsException(String message) {
        super(message);
    }
}
