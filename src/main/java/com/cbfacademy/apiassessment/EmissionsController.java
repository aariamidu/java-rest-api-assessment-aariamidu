package com.cbfacademy.apiassessment;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.io.FileWriter;
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
        int destinationId = journeyRequest.getDestinationId(); // Retrieving the destination ID from the JourneyRequest
        logger.info("Received destinationId: {}", destinationId);
        List<DestinationAddress> destinations = destinationAddressService.getDestinationAddresses();
        logger.info("Available destinations: {}", destinations);

        // Fetching the destination address using the destination ID
        DestinationAddress destinationAddress = destinationAddressService.getDestinationAddress(destinationId);

        if (destinationAddress == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Destination address not found for the provided ID");
        }

        String travelMode = "car";
        String carType = "petrol";
        String origin = journeyRequest.getOrigin();
        String journeyType = journeyRequest.getJourneyType();

        EmissionsData emissionsData = emissionsCalculatorService.calculateEmissions(travelMode, carType, origin,
                destinationId, journeyType);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonData = objectMapper.writeValueAsString(emissionsData);

            FileWriter fileWriter = new FileWriter(JSON_FILE_PATH);
            fileWriter.write(jsonData);
            fileWriter.close();

            return ResponseEntity.ok("Emissions data saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving emissions data: " + e.getMessage());
        }
    }
}
