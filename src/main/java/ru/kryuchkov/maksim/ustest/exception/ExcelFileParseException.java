package ru.kryuchkov.maksim.ustest.exception;

public class ExcelFileParseException extends RuntimeException {

    public ExcelFileParseException() {
        super();
    }

    public ExcelFileParseException(String message) {
        super(message);
    }

}
