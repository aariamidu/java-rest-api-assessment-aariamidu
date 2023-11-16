
package com.cbfacademy.apiassessment.emissions;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Component for writing and reading JSON files.
 */
@Component
public class JsonFileWriter {
    private final Logger logger = LoggerFactory.getLogger(JsonFileWriter.class);

    // File paths for JSON files
    private final String destinationJsonFilePath = "src/main/resources/destination.json";
    private final String emissionsJsonFilePath = "src/main/resources/emissionsData.json";
    private final String treesJsonFilePath = "src/main/resources/trees.json";

    // Write data to the destination JSON file
    public boolean writeDestinationJsonFile(List<?> data) {
        return writeToJsonFile(destinationJsonFilePath, data);
    }

    // Write data to the emissions JSON file
    public boolean writeEmissionsJsonFile(List<?> data) {
        return writeToJsonFile(emissionsJsonFilePath, data);
    }

    // Write data to the trees JSON file
    public boolean writeTreesJsonFile(List<?> data) {
        return writeToJsonFile(treesJsonFilePath, data);
    }

    // Private method to write data to a JSON file
    private boolean writeToJsonFile(String jsonFilePath, List<?> data) {
        try (FileWriter fileWriter = new FileWriter(jsonFilePath)) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue(fileWriter, data);
            return true;
        } catch (IOException e) {
            logger.error("Error writing to JSON file: {}", jsonFilePath, e);
            return false;
        }
    }

    // Read data from the destination JSON file
    public <T> List<T> readDestinationJsonFile(TypeReference<List<T>> typeReference) {
        return readFromJsonFile(destinationJsonFilePath, typeReference);
    }

    // Read data from the emissions JSON file
    public <T> List<T> readEmissionsJsonFile(TypeReference<List<T>> typeReference) {
        return readFromJsonFile(emissionsJsonFilePath, typeReference);
    }

    // Read data from the trees JSON file
    public <T> List<T> readTreesJsonFile(TypeReference<List<T>> typeReference) {
        return readFromJsonFile(treesJsonFilePath, typeReference);
    }

    // Private method to read data from a JSON file

    private <T> List<T> readFromJsonFile(String jsonFilePath, TypeReference<List<T>> typeReference) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File(jsonFilePath);

            if (!file.exists()) {
                // Returns an empty list if the file does not exist
                return new ArrayList<>();
            }

            return objectMapper.readValue(file, typeReference);
        } catch (IOException e) {
            logger.error("Error reading from JSON file: {}", jsonFilePath, e);
            return new ArrayList<>();
        }
    }
}
