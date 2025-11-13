package ru.kryuchkov.maksim.ustest.repository;

import java.io.IOException;
import java.util.Optional;

public interface ExcelFileRepository {

    Optional<Long> getNthNumber(String path, int n) throws IOException;

}
