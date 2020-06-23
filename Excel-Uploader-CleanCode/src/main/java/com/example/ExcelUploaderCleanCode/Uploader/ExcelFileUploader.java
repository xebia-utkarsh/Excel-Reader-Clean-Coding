package com.example.ExcelUploaderCleanCode.Uploader;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Component
public class ExcelFileUploader {
    private static final Logger log = LoggerFactory.getLogger(ExcelFileUploader.class);
    public Map<Integer, List> MapOfExcelFile = new HashMap<>();


    public Map readDataFile() throws IOException {
        System.out.println("File Read");
        File excelFile = new File("D:/Tools/Dev/JavaDev/Spring/Excel-Uploader-CleanCode/src/main/resources/Sample-Data-File.xlsx");
        FileInputStream excelInputStream = new FileInputStream(excelFile);
        log.info("Successfully Uploaded Excel Sheet");

        XSSFWorkbook excelWorkBook = new XSSFWorkbook(excelInputStream);
        XSSFSheet excelSheet = excelWorkBook.getSheetAt(0);
        Iterator rowsOfExcel = excelSheet.rowIterator();

        while (rowsOfExcel.hasNext()) {
            XSSFRow excelRow = (XSSFRow) rowsOfExcel.next();
            Iterator cellsOfExcel = excelRow.cellIterator();

            List value = new LinkedList<>();

            while (cellsOfExcel.hasNext()) {
                XSSFCell cell = (XSSFCell) cellsOfExcel.next();
                cell.setCellType(Cell.CELL_TYPE_STRING);
                value.add(cell);
            }

            MapOfExcelFile.put(excelRow.getRowNum(), value);

        }
        return MapOfExcelFile;


    }
}
