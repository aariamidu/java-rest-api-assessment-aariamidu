package com.cbfacademy.apiassessment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileWriter;
import java.io.IOException;

@RestController
public class EmissionsController {

    private static final String JSON_FILE_PATH = "emissionsData.json";

    @PostMapping("/save-emissions")
    public ResponseEntity<String> saveEmissionsData(@RequestBody EmissionsData emissionsData) {
        try (FileWriter fileWriter = new FileWriter(JSON_FILE_PATH, true)) {
            fileWriter.write(emissionsData.toJsonString() + "\n");
            fileWriter.flush();
            return ResponseEntity.ok("Emissions data saved successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving emissions data: " + e.getMessage());
        }
    }
}
