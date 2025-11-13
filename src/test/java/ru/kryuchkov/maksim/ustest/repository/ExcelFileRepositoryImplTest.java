package ru.kryuchkov.maksim.ustest.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.kryuchkov.maksim.ustest.util.SelectionUtils;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ExcelFileRepositoryImplTest {

    private final SelectionUtils selectionUtils = Mockito.mock(SelectionUtils.class);

    private final ExcelFileRepository repository = new ExcelFileRepositoryImpl(selectionUtils);

    @Test
    void getNthNumber() throws IOException {
        long[] numbers = {1L, 4L, 6L, 3L, 2L, 6L};
        int n = 3;

        Mockito.when(selectionUtils.getNthSmallestNumber(numbers, n)).thenReturn(Optional.of(2L));

        Optional<Long> result = repository.getNthNumber("src/test/resources/test1.xlsx", n);

        Mockito.verify(selectionUtils).getNthSmallestNumber(numbers, n);

        assertTrue(result.isPresent());
        assertEquals(2L, result.get());
    }
}