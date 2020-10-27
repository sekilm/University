package org.example.p9.core.domain.validators;

public class MyException extends RuntimeException {
    public MyException(String msg) {
        super(msg);
    }

    public MyException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public MyException(Throwable cause) {
        super(cause);
    }
}
