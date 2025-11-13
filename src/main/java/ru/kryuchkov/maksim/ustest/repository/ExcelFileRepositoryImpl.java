package ru.kryuchkov.maksim.ustest.repository;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Repository;
import ru.kryuchkov.maksim.ustest.exception.ExcelFileParseException;
import ru.kryuchkov.maksim.ustest.util.SelectionUtils;

import java.io.File;
import java.io.FileNotFoundException;
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
    public Optional<Long> getNthNumber(String path, int n) throws IOException, ExcelFileParseException {
        long[] numbers = readNumbersColumnFromXlsxFile(path);
        return selectionUtils.getNthSmallestNumber(numbers, n);
    }

    private long[] readNumbersColumnFromXlsxFile(String path) throws IOException, ExcelFileParseException {
        ArrayList<Long> numbers = new ArrayList<>();
        File file = new File(path);
        if (!file.exists() && !file.isDirectory()) {
            throw new FileNotFoundException(path);
        }
        try (Workbook workbook = WorkbookFactory.create(file)) {
            Sheet sheet = workbook.getSheetAt(0);
            log.debug("Read sheet {}", sheet.getSheetName());
            int lastRowNum = sheet.getLastRowNum();
            log.debug("LastRowNum: {}", lastRowNum);
            for (int rowIndex = 0; rowIndex <= lastRowNum; rowIndex++) {
                Long numericCellValue = getNumericCellValueFromFirstColumn(sheet, rowIndex);
                if (numericCellValue == null) break;
                numbers.add(numericCellValue);
            }
        }
        return numbers.stream().mapToLong(i -> i).toArray();
    }

    private Long getNumericCellValueFromFirstColumn(Sheet sheet, int i) throws ExcelFileParseException {
        Row row = sheet.getRow(i);
        if (row == null) {
            log.debug("Row {} is null, stopping parsing", i);
            return null;
        }
        Cell cell = row.getCell(0);
        if (cell == null) {
            log.debug("Cell 0 at row {} is undefined, stopping parsing", i);
            return null;
        }
        if (cell.getCellType() != CellType.NUMERIC) {
            log.error("Cell 0 at row {} is not numeric type", i);
            throw new ExcelFileParseException("Cell 0 at row " + i + " is not numeric type");
        }
        // по тз в столбце только целые числа
        return (long) cell.getNumericCellValue();
    }

}
