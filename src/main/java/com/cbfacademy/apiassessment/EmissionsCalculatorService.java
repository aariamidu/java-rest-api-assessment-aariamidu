package com.cbfacademy.apiassessment;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmissionsCalculatorService {

    private static final String API_URL = "https://beta4.api.climatiq.io/travel/distance";
    private static final String API_KEY = "FC2PSR1GFXM6PYKMGN1W70SQVSPZ";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<EmissionsData> emissionsDataList = new ArrayList<>();

    public EmissionsData calculateEmissions(String travelMode, String carType, String origin, String destination,
            String journeyType) {
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

                EmissionsData emissionsData = new EmissionsData(co2e, distanceKm, origin, destination, null, 0, 0);
                emissionsDataList.add(emissionsData);
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
