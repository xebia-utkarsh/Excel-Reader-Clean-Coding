package com.example.ExcelUploaderCleanCode.Uploader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JSONFileUploader {
    public static final Logger log = LoggerFactory.getLogger(ExcelFileUploader.class);
    List<String> cellEntry = new ArrayList<>();

    @PostConstruct
    public List readJsonFile() throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();

        String jasonFilePath = "D:/Tools/Dev/JavaDev/Spring/Excel-Uploader-CleanCode/src/main/resources/mapping.json";

        FileReader jsonFileReader = new FileReader(jasonFilePath);
        log.info("JSON File Loaded Successfully");


        Object parseJASON = jsonParser.parse(jsonFileReader);
        JSONObject objectOfJASON = (JSONObject) parseJASON;

        cellEntry.add((String) objectOfJASON.get("name"));
        cellEntry.add((String) objectOfJASON.get("age"));
        cellEntry.add((String) objectOfJASON.get("class"));
        cellEntry.add((String) objectOfJASON.get("rank"));
        cellEntry.add((String) objectOfJASON.get("section"));

        return cellEntry;


    }
}
