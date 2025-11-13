package ru.kryuchkov.maksim.ustest.repository;

import ru.kryuchkov.maksim.ustest.exception.ExcelFileParseException;

import java.io.IOException;
import java.util.Optional;

public interface ExcelFileRepository {

    Optional<Long> getNthNumber(String path, int n) throws IOException, ExcelFileParseException;

}
