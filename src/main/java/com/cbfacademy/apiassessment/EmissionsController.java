package com.cbfacademy.apiassessment;

import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/journeys")
public class EmissionsController {

    private final EmissionsCalculatorService emissionsCalculatorService;
    private final DestinationAddressService destinationAddressService;
    private final JsonFileWriter jsonFileWriter;
    private final Logger logger = LoggerFactory.getLogger(EmissionsController.class);

    private long generateCustomId(List<EmissionsData> emissionsDataList) {
        long maxId = emissionsDataList.stream()
                .mapToLong(EmissionsData::getId)
                .max()
                .orElse(0);

        return maxId + 1;
    }

    public EmissionsController(EmissionsCalculatorService emissionsCalculatorService,
            DestinationAddressService destinationAddressService, JsonFileWriter jsonFileWriter) {
        this.emissionsCalculatorService = emissionsCalculatorService;
        this.destinationAddressService = destinationAddressService;
        this.jsonFileWriter = jsonFileWriter;
    }

    @PostMapping
    public ResponseEntity<String> saveEmissionsData(@RequestBody JourneyRequest journeyRequest) {
        int destinationId = journeyRequest.getDestinationId();
        logger.info("Received destinationId: {}", destinationId);

        DestinationAddress destinationAddress = destinationAddressService.getDestinationAddress(destinationId);

        if (destinationAddress == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Destination address not found for the provided ID");
        }

        List<EmissionsData> existingEmissionsData = readEmissionsDataFromFile();

        long newId = generateCustomId(existingEmissionsData);

        EmissionsData emissionsData = emissionsCalculatorService.calculateEmissions(newId,
                journeyRequest.getTravelMode(),
                journeyRequest.getCarType(), journeyRequest.getOrigin(), destinationId,
                journeyRequest.getJourneyType());

        existingEmissionsData.add(emissionsData);
        QuickSort quickSort = new QuickSort();
        quickSort.sort(existingEmissionsData);

        boolean writeSuccess = jsonFileWriter.writeEmissionsJsonFile(existingEmissionsData);

        if (writeSuccess) {
            return ResponseEntity.ok("Emissions data saved successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving emissions data.");
        }
    }

    @GetMapping
    public ResponseEntity<List<EmissionsData>> getEmissionsData() {
        List<EmissionsData> emissionsDataList = readEmissionsDataFromFile();

        if (emissionsDataList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(emissionsDataList);
    }

    private List<EmissionsData> readEmissionsDataFromFile() {
        TypeReference<List<EmissionsData>> typeReference = new TypeReference<List<EmissionsData>>() {
        };
        return jsonFileWriter.readEmissionsJsonFile(typeReference);
    }

}
