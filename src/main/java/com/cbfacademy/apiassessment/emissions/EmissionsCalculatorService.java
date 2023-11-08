
package com.cbfacademy.apiassessment.emissions;

import com.cbfacademy.apiassessment.destination.DestinationAddress;
import com.cbfacademy.apiassessment.destination.DestinationAddressService;
import com.cbfacademy.apiassessment.trees.Tree;
import com.cbfacademy.apiassessment.trees.TreeService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmissionsCalculatorService {

    private static final String API_URL = "https://beta4.api.climatiq.io/travel/distance";
    @Value("${api.key}")
    private String apiKey;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<EmissionsData> emissionsDataList = new ArrayList<>();
    private final TreeService treeService;
    private final DestinationAddressService destinationAddressService;
    private final Logger logger = LoggerFactory.getLogger(EmissionsCalculatorService.class);

    public EmissionsCalculatorService(DestinationAddressService destinationAddressService, TreeService treeService) {
        this.destinationAddressService = destinationAddressService;
        this.treeService = treeService;
    }

    public EmissionsData calculateEmissions(long id, String travelMode, String carType, String origin,
            int destinationId, String journeyType) {

        DestinationAddress destinationAddress = destinationAddressService.getDestinationAddress(destinationId);

        if (destinationAddress == null) {
            logger.error("Destination address not found for ID: {}", destinationId);
            // Returns a default or empty response
            return new EmissionsData(0, origin, "Unknown", travelMode, carType, journeyType, 0.0, 0.0, "Unknown", 0.0,
                    0.0);

        }
        try {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setDoOutput(true);

            String destination = destinationAddress.getAddress();
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

                // Doubles CO2 and distance for return journeys
                if ("return".equalsIgnoreCase(journeyType)) {
                    co2e *= 2;
                    distanceKm *= 2;
                }

                Tree randomTree = treeService.getRandomTree();
                String treeSpecies = randomTree != null ? randomTree.getSpecies() : "Unknown";
                double co2StoragePerYear = randomTree != null ? randomTree.getCo2StoragePerTreePerYear() : 0;
                double co2AbsorptionIn80Years = randomTree != null ? randomTree.getCo2AbsorptionPerTreeIn80Years() : 0;

                EmissionsData emissionsData = new EmissionsData(id, origin, destinationAddress.getAddress(), travelMode,
                        carType,
                        journeyType, co2e, distanceKm, treeSpecies, co2StoragePerYear, co2AbsorptionIn80Years);

                return emissionsData;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String createRequestBody(String travelMode, String carType, String origin, String destination,
            String journeyType) {

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

        if ("return".equalsIgnoreCase(journeyType)) {
            requestBodyNode.put("return_journey", true);
        }

        return requestBodyNode.toString();
    }

    public List<EmissionsData> getEmissionsDataList() {
        return emissionsDataList;
    }
}
