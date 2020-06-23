package com.example.ExcelUploaderCleanCode.Service;

import com.example.ExcelUploaderCleanCode.Uploader.ExcelFileUploader;
import com.example.ExcelUploaderCleanCode.Uploader.JSONFileUploader;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ConvertToTemplate {
    private static final Logger log = LoggerFactory.getLogger(ExcelFileUploader.class);
    @Autowired
    public ExcelFileUploader excelFileUploader;
    @Autowired
    public JSONFileUploader jsonFileUploader;
    Map<Integer, List> MapOfTemplate = new HashMap<>();

    @PreDestroy
    public void generateOutputFileFromTemplateFile() throws IOException, ParseException {
        List<String> jsonValues = jsonFileUploader.readJsonFile();
        MapOfTemplate = excelFileUploader.MapOfExcelFile;
        File inputTemplate = new File("D:/Tools/Dev/JavaDev/Spring/Excel-Uploader-CleanCode/src/main/resources/Sample-Template-File.xlsx");
        FileInputStream inputStream = new FileInputStream(inputTemplate);
        log.info("Template File Loaded Successfully");

        XSSFWorkbook templateWorkBook = new XSSFWorkbook(inputStream);
        XSSFSheet templateSheet = templateWorkBook.getSheetAt(0);

        for (Integer hash_key : MapOfTemplate.keySet()) {
            int mapIterator = 0;
            for (Object hash_value : MapOfTemplate.get(hash_key)) {
                if (hash_key == 0) break;
                CellReference cellReference = new CellReference(jsonValues.get(mapIterator));
                Row row = templateSheet.getRow(cellReference.getRow());
                Cell cell = row.getCell(cellReference.getCol());
                if (cell == null) {
                    cell = row.createCell(cellReference.getCol());
                }
                cell.setCellValue(Cell.CELL_TYPE_STRING);
                String toPutValueInCell = hash_value.toString();
                log.info("Value: " + toPutValueInCell + ": Row Number :" + cell.getRowIndex() + ": Column Number : " + cell.getColumnIndex());
                cell.setCellValue(toPutValueInCell);
                mapIterator++;
                FileOutputStream fileOut = new FileOutputStream("output" + hash_key + ".xlsx");
                templateWorkBook.write(fileOut);
                fileOut.close();

            }


        }


    }

}
