package com.cbfacademy.apiassessment;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStream;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmissionsCalculatorService {

    private static final String OUTPUT_FILE_PATH = "src/main/resources/emissions_data.json";
    private static final String API_URL = "https://beta4.api.climatiq.io/travel/distance";
    private static final String API_KEY = "FC2PSR1GFXM6PYKMGN1W70SQVSPZ";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(EmissionsCalculatorService.class);

    public CalculationResult calculateEmissions(EmissionRequest request) {
        String travelMode = request.getTravelMode();
        String carType = request.getCarType();
        String origin = request.getOrigin();
        String destination = request.getDestination();
        int journeyType = request.getJourneyTypeAsInt();

        try {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
            connection.setDoOutput(true);

            String requestBody = createRequestBody(travelMode, carType, origin, destination, journeyType);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] requestBodyBytes = requestBody.getBytes("utf-8");
                os.write(requestBodyBytes, 0, requestBodyBytes.length);
            }

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream responseStream = connection.getInputStream();
                JsonNode responseJson = objectMapper.readTree(responseStream);

                double co2e = responseJson.get("co2e").asDouble();
                double distanceKm = responseJson.get("distance_km").asDouble();

                CalculationResult result = new CalculationResult();
                result.setCo2Emissions(co2e);
                result.setDistanceKm(distanceKm);
                result.setOrigin(origin);
                result.setDestination(destination);
                result.setTravelMode(travelMode);
                result.setCarType(carType);
                saveResultToJsonFile(result);

                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String createRequestBody(String travelMode, String carType, String origin, String destination,
            int journeyType) {
        ObjectNode requestBodyNode = JsonNodeFactory.instance.objectNode();
        requestBodyNode.put("travel_mode", travelMode);

        ObjectNode originNode = JsonNodeFactory.instance.objectNode();
        originNode.put("query", origin);
        requestBodyNode.set("origin", originNode);

        ObjectNode destinationNode = JsonNodeFactory.instance.objectNode();
        destinationNode.put("query", destination);
        requestBodyNode.set("destination", destinationNode);

        if ("car".equalsIgnoreCase(travelMode)) {
            ObjectNode carDetailsNode = JsonNodeFactory.instance.objectNode();
            carDetailsNode.put("car_type", carType);
            requestBodyNode.set("car_details", carDetailsNode);
        }

        if (journeyType == 2) {
            requestBodyNode.put("return_journey", true);
        }

        return requestBodyNode.toString();
    }

    public void saveResultToJsonFile(CalculationResult result) {
        try {
            ArrayNode dataArray = readJsonFile();
            if (dataArray == null) {
                dataArray = objectMapper.createArrayNode();
            }

            ObjectNode resultNode = objectMapper.createObjectNode();
            resultNode.put("origin", result.getOrigin());
            resultNode.put("destination", result.getDestination());
            resultNode.put("co2Emissions", result.getCo2Emissions());
            resultNode.put("distanceKm", result.getDistanceKm());
            resultNode.put("travelMode", result.getTravelMode());
            resultNode.put("carType", result.getCarType());

            dataArray.add(resultNode);
            objectMapper.writeValue(new File(OUTPUT_FILE_PATH), dataArray);
        } catch (IOException e) {
            LOGGER.error("Error occurred while saving data to JSON file", e);
            throw new RuntimeException("Failed to save data to JSON file", e);
        }
    }

    public class JsonFileReadException extends RuntimeException {

        public JsonFileReadException(String message) {
            super(message);
        }

        public JsonFileReadException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    private ArrayNode readJsonFile() {
        try {
            File file = new File(OUTPUT_FILE_PATH);
            if (file.exists()) {
                return (ArrayNode) objectMapper.readTree(file);
            } else {
                return objectMapper.createArrayNode();
            }
        } catch (IOException e) {
            LOGGER.error("Error occurred while reading JSON file", e);
            throw new JsonFileReadException("Error occurred while reading JSON file", e);
        }
    }

}
