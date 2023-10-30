package com.cbfacademy.apiassessment;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;

@RestController

public class EmissionsController {

    private static final String JSON_FILE_PATH = "src/main/resources/emissionsData.json";

    @PostMapping("/api/journeys")
    public ResponseEntity<String> saveEmissionsData(@RequestBody JourneyRequest journeyRequest) {
        EmissionsCalculatorService service = new EmissionsCalculatorService();
        DestinationAddressService addressService = new DestinationAddressService();
        String address = addressService.getDestinationAddress(journeyRequest.id).getAddress();
        EmissionsData emissionsData = service.calculateEmissions(JSON_FILE_PATH, JSON_FILE_PATH, JSON_FILE_PATH, JSON_FILE_PATH, JSON_FILE_PATH);
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonData = objectMapper.writeValueAsString(emissionsData);

            FileWriter fileWriter = new FileWriter(JSON_FILE_PATH);
            fileWriter.write(jsonData);
            fileWriter.close();

            return ResponseEntity.ok("Emissions data saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving emissions data: " + e.getMessage());
        }
    }
}
