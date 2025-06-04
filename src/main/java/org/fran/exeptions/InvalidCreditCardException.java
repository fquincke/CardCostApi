package org.fran.exeptions;

import java.io.IOException;

public class InvalidCreditCardException extends IOException {
    public InvalidCreditCardException(String message) {
        super(message);
    }
}
