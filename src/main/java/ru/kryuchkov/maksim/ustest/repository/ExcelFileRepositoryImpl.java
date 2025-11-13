package ru.kryuchkov.maksim.ustest.repository;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Repository;
import ru.kryuchkov.maksim.ustest.exception.ExcelFileParseException;
import ru.kryuchkov.maksim.ustest.util.SelectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;


@Slf4j
@Repository
public class ExcelFileRepositoryImpl implements ExcelFileRepository {

    private final SelectionUtils selectionUtils;

    public ExcelFileRepositoryImpl(SelectionUtils selectionUtils) {
        this.selectionUtils = selectionUtils;
    }

    @Override
    public Optional<Long> getNthNumber(String path, int n) throws IOException {
        long[] numbers = readNumbersColumnFromXlsxFile(path);
        return selectionUtils.getNthSmallestNumber(numbers, n);
    }

    private long[] readNumbersColumnFromXlsxFile(String path) throws IOException {
        ArrayList<Long> numbers = new ArrayList<>();
        File file = new File(path);
        try (Workbook workbook = WorkbookFactory.create(file)) {
            Sheet sheet = workbook.getSheetAt(0);
            log.debug("Read sheet {}", sheet.getSheetName());
            log.debug("LastRowNum: {}", sheet.getLastRowNum());
            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    log.debug("Row {} is null, stopping parsing", i);
                    break;
                }
                Cell cell = row.getCell(0);
                if (cell == null) {
                    log.debug("Cell 0 at row {} is undefined, stopping parsing", i);
                    break;
                }
                if (cell.getCellType() != CellType.NUMERIC) {
                    log.error("Cell 0 at row {} is not numeric type", i);
                    throw new ExcelFileParseException("Cell 0 at row " + i + " is not numeric type");
                }
                // по тз в столбце только целые числа
                long numericCellValue = (long) cell.getNumericCellValue();
                numbers.add(numericCellValue);
            }
        }
        return numbers.stream().mapToLong(i -> i).toArray();
    }

}
