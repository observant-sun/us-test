package ru.kryuchkov.maksim.ustest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kryuchkov.maksim.ustest.dto.NthSmallestNumberDto;
import ru.kryuchkov.maksim.ustest.exception.BadRequestException;
import ru.kryuchkov.maksim.ustest.exception.ExcelFileParseException;
import ru.kryuchkov.maksim.ustest.repository.ExcelFileRepository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
public class ExcelFileService {

    private final ExcelFileRepository excelFileRepository;

    public ExcelFileService(ExcelFileRepository excelFileRepository) {
        this.excelFileRepository = excelFileRepository;
    }

    public NthSmallestNumberDto getNthSmallestNumber(String pathToFile, int n) throws ExcelFileParseException {
        if (pathToFile == null || pathToFile.isEmpty()) {
            return mapToNthSmallestNumberDto(null);
        }
        try {
            Optional<Long> numberOpt = excelFileRepository.getNthNumber(pathToFile, n);
            return mapToNthSmallestNumberDto(numberOpt.orElse(null));
        } catch (FileNotFoundException e) {
            throw new BadRequestException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // можно было бы использовать MapStruct, но это было бы слишком для такого маленького проекта
    private NthSmallestNumberDto mapToNthSmallestNumberDto(
            Long number
    ) {
        return new NthSmallestNumberDto(number);
    }
}
