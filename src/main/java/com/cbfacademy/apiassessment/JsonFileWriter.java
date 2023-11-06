
package com.cbfacademy.apiassessment;

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

@Component
public class JsonFileWriter {
    private final Logger logger = LoggerFactory.getLogger(JsonFileWriter.class);
    private final String destinationJsonFilePath = "src/main/resources/destination.json";
    private final String emissionsJsonFilePath = "src/main/resources/emissionsData.json";
    private final String treesJsonFilePath = "src/main/resources/trees.json";

    public boolean writeDestinationJsonFile(List<?> data) {
        return writeToJsonFile(destinationJsonFilePath, data);
    }

    public boolean writeEmissionsJsonFile(List<?> data) {
        return writeToJsonFile(emissionsJsonFilePath, data);
    }

    public boolean writeTreesJsonFile(List<?> data) {
        return writeToJsonFile(treesJsonFilePath, data);
    }

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

    public <T> List<T> readDestinationJsonFile(TypeReference<List<T>> typeReference) {
        return readFromJsonFile(destinationJsonFilePath, typeReference);
    }

    public <T> List<T> readEmissionsJsonFile(TypeReference<List<T>> typeReference) {
        return readFromJsonFile(emissionsJsonFilePath, typeReference);
    }

    public <T> List<T> readTreesJsonFile(TypeReference<List<T>> typeReference) {
        return readFromJsonFile(treesJsonFilePath, typeReference);
    }

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
