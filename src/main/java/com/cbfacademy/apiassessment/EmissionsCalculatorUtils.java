package com.cbfacademy.apiassessment;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Scanner;


public class EmissionsCalculatorUtils {
   public static final String london_bus = "passenger_vehicle-vehicle_type_local_bus-fuel_source_na-distance_na-engine_size_na";
    public static final String regular_bus = "passenger_vehicle-vehicle_type_local_bus_not_london-fuel_source_na-distance_na-engine_size_na";
    public static final String underground_overground = "passenger_train-route_type_underground-fuel_source_na";
    
    
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
    
            System.out.print("Enter activity ID: ");
            String activityId = scanner.nextLine();
            
            System.out.print("Enter distance (in km): ");
            double distance = Double.parseDouble(scanner.nextLine());
    
            System.out.print("Enter distance unit (km/miles): ");
            String distanceUnit = scanner.nextLine();
    
            double calculatedEmissions = calculateEmissions(activityId, distance, distanceUnit);
            System.out.println("Calculated Emissions: " + calculatedEmissions + " kgCO2e");
    
            scanner.close();
        }
    
    
   
       
   public static double calculateEmissions(String activityId, double distance, String distanceUnit) {
    activityId = activityId.toLowerCase();
    String apiUrl = "https://beta4.api.climatiq.io/estimate";
    String apiKey = "FC2PSR1GFXM6PYKMGN1W70SQVSPZ";
    double emissions = 0.0;

    try {
        // Construct the request body based on activityId, distance, and distance unit
        String requestBody = "{\"emission_factor\": {\"activity_id\": \"" + activityId + "\",\"source\": \"BEIS\","
             + "\"region\": \"GB-LON\",\"year\": 2023,\"source_lca_activity\": \"well_to_tank\","
             + "\"data_version\": \"5.5\"},\"parameters\": {\"distance\": " + distance + ","
             + "\"distance_unit\": \"" + distanceUnit + "\"}}";

        URL url = new URL(apiUrl);
    
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // request method to POST
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + apiKey);
        connection.setDoOutput(true);

        // request body
        try (OutputStream os = connection.getOutputStream()) {
            byte[] requestBodyBytes = requestBody.getBytes("utf-8");
            os.write(requestBodyBytes, 0, requestBodyBytes.length);
        }

        int responseCode = connection.getResponseCode();

        // Parse JSON response
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Read and parse the API response
            InputStream responseStream = connection.getInputStream();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseJson = objectMapper.readTree(responseStream);
        
        
            System.out.println("API Response: " + responseJson);
        
            JsonNode co2eNode = responseJson.get("co2e");
        
            if (co2eNode != null && !co2eNode.isNull()) {
                emissions = co2eNode.asDouble();
        
                // Printing extracted values
                System.out.println("Emissions: " + emissions + " kgCO2e");
            } else {
                System.out.println("Error: Missing or null values in the JSON response for 'co2e' field.");
            }
        } else {
            // Handle error cases
            System.out.println("Error: Unable to fetch data from the API. HTTP Status Code: " + responseCode);
        }
        
        // Close the connection
        connection.disconnect();

    } catch (Exception e) {
        // Handle exceptions
        e.printStackTrace();
    }

    return emissions;
}
}
