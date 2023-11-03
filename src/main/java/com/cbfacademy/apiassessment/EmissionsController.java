package com.cbfacademy.apiassessment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class EmissionsController {

    private static final String JSON_FILE_PATH = "src/main/resources/emissionsData.json";
    private final EmissionsCalculatorService emissionsCalculatorService;
    private final DestinationAddressService destinationAddressService;
    private final Logger logger = LoggerFactory.getLogger(EmissionsController.class);

    public EmissionsController(EmissionsCalculatorService emissionsCalculatorService,
            DestinationAddressService destinationAddressService) {
        this.emissionsCalculatorService = emissionsCalculatorService;
        this.destinationAddressService = destinationAddressService;
    }

    @PostMapping("/api/journeys")
    public ResponseEntity<String> saveEmissionsData(@RequestBody JourneyRequest journeyRequest) {
        int destinationId = journeyRequest.getDestinationId();
        logger.info("Received destinationId: {}", destinationId);

        DestinationAddress destinationAddress = destinationAddressService.getDestinationAddress(destinationId);

        if (destinationAddress == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Destination address not found for the provided ID");
        }
        int id = 0;
        String travelMode = journeyRequest.getTravelMode();
        String carType = journeyRequest.getCarType();
        String origin = journeyRequest.getOrigin();
        String journeyType = journeyRequest.getJourneyType();

        EmissionsData emissionsData = emissionsCalculatorService.calculateEmissions(id, travelMode, carType, origin,
                destinationId, journeyType);

        List<EmissionsData> existingEmissionsData = readEmissionsDataFromFile();
        existingEmissionsData.add(emissionsData);

        try (FileWriter fileWriter = new FileWriter(JSON_FILE_PATH)) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(fileWriter, existingEmissionsData);
            return ResponseEntity.ok("Emissions data saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving emissions data: " + e.getMessage());
        }
    }

    private List<EmissionsData> readEmissionsDataFromFile() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File(JSON_FILE_PATH);

            if (!file.exists()) {
                // Returns a new empty list if the file does not exist
                return new ArrayList<>();
            }

            return objectMapper.readValue(file, new TypeReference<List<EmissionsData>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();

            return new ArrayList<>();
        }
    }
}
