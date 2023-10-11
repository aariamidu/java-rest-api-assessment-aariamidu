package com.cbfacademy.apiassessment;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EmissionsCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your origin destination: ");
        String origin = scanner.nextLine();

        System.out.print("Enter the Fujitsu office address you are trying to get to: ");
        String destination = scanner.nextLine();

        System.out.print("Enter your travel mode (air, car, or rail): ");
        String travelMode = scanner.nextLine();

        String carType = "";
        if ("car".equalsIgnoreCase(travelMode)) {
            System.out.print("Enter your car type (petrol, diesel, electric, or hybrid): ");
            carType = scanner.nextLine();
        }


        String apiUrl = "https://beta4.api.climatiq.io/travel/distance";
        String apiKey = ""; 

        try {
            // request parameters
            String requestBody = "{"
            + "\"travel_mode\": \"" + travelMode + "\","
            + "\"origin\": {\"query\": \"" + origin + "\"},"
            + "\"destination\": {\"query\": \"" + destination + "\"},"
            + "\"car_details\": {\"car_type\": \"" + carType + "\"}"
            + "}";

            // URL object
            URL url = new URL(apiUrl);

            // Connection object
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // request method to POST
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);

            // input and output streams
            connection.setDoOutput(true);

            // request body
            try (OutputStream os = connection.getOutputStream()) {
                byte[] requestBodyBytes = requestBody.getBytes("utf-8");
                os.write(requestBodyBytes, 0, requestBodyBytes.length);
            }

            // Get HTTP response code
            int responseCode = connection.getResponseCode();

            // Parse JSON response
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read and parse the API response
                InputStream responseStream = connection.getInputStream();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode responseJson = objectMapper.readTree(responseStream);

            // Extracting values from the JSON response
            double co2e = responseJson.get("co2e").asDouble();
            double distanceKm = responseJson.get("distance_km").asDouble();
            String originName = responseJson.get("origin").get("name").asText();
            String destinationName = responseJson.get("destination").get("name").asText();

            // Printing extracted values
            System.out.println("CO2 Emissions: " + co2e + " kg");
            System.out.println("Distance: " + distanceKm + " km");
            System.out.println("Origin: " + originName);
            System.out.println("Destination: " + destinationName);

        } 
        connection.disconnect();
    }catch (Exception e) {
          
            e.printStackTrace();
        } finally {
       
            scanner.close();
        }
        
    }
}
