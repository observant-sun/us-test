package ru.kryuchkov.maksim.ustest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kryuchkov.maksim.ustest.dto.NthSmallestNumberDto;
import ru.kryuchkov.maksim.ustest.service.ExcelFileService;

@RestController
@RequestMapping("/excel-file")
public class ExcelFileController {

    private final ExcelFileService excelFileService;

    public ExcelFileController(ExcelFileService excelFileService) {
        this.excelFileService = excelFileService;
    }

    @GetMapping
    public NthSmallestNumberDto getNthSmallestNumber(
            @RequestParam String pathToFile,
            @RequestParam Integer n
    ) {
        return excelFileService.getNthSmallestNumber(pathToFile, n);
    }

}
