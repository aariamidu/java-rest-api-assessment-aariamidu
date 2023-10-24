package com.cbfacademy.apiassessment;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStream;
import java.io.OutputStream;

@Service
public class EmissionsCalculatorService {

    private static final String OUTPUT_FILE_PATH = "emissions_data.json";
    private static final String API_URL = "https://beta4.api.climatiq.io/travel/distance";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CalculationResult calculateEmissions(EmissionRequest request) {
        String travelMode = request.getTravelMode();
        String carType = request.getCarType();
        String origin = request.getOrigin();
        String destination = request.getDestination();
        int journeyType = request.getJourneyType();

        try {
            // Making a request to the external API
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

          
            String requestBody = createRequestBody(travelMode, carType, origin, destination, journeyType);

            // Send request
            try (OutputStream os = connection.getOutputStream()) {
                byte[] requestBodyBytes = requestBody.getBytes("utf-8");
                os.write(requestBodyBytes, 0, requestBodyBytes.length);
            }

            // Read API response
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream responseStream = connection.getInputStream();
                JsonNode responseJson = objectMapper.readTree(responseStream);
                
                // Extracts CO2 emissions and distance from the API response
                double co2e = responseJson.get("co2e").asDouble();
                double distanceKm = responseJson.get("distance_km").asDouble();

                CalculationResult result = new CalculationResult();
                result.setCo2Emissions(co2e);
                result.setDistanceKm(distanceKm);
                result.setOrigin(origin);
                result.setDestination(destination);

                // Save the result to a JSON file
                saveResultToJsonFile(result);

                return result;
            }
        } catch (IOException e) {
            e.printStackTrace(); 
        }

        return null; 
    }

    private String createRequestBody(String travelMode, String carType, String origin, String destination, int journeyType) {
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
    

        private void saveResultToJsonFile(CalculationResult result) {
            try {
                // Read existing JSON data from the file
                JsonNode existingData = objectMapper.readTree(new File(OUTPUT_FILE_PATH));
    
                // Create a new JSON node for the current result
                ObjectNode resultNode = JsonNodeFactory.instance.objectNode();
                resultNode.put("origin", result.getOrigin());
                resultNode.put("destination", result.getDestination());
                resultNode.put("co2Emissions", result.getCo2Emissions());
                resultNode.put("distanceKm", result.getDistanceKm());
                 // Add the new result node to the existing JSON data
            if (existingData == null) {
                existingData = JsonNodeFactory.instance.arrayNode();
            }
            ((ObjectNode) existingData).withArray("results").add(resultNode);

            // Write the updated JSON data back to the file
            objectMapper.writeValue(new File(OUTPUT_FILE_PATH), existingData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
