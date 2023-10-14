package com.cbfacademy.apiassessment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class EmissionsCalculator {

    public static void main(String[] args) {
        boolean continueCalculations = true;
        ObjectMapper objectMapper = new ObjectMapper();

        try (Scanner scanner = new Scanner(System.in)) {
            while (continueCalculations) {
                int travelModeChoice = showTravelModeMenu(scanner);
                String travelMode;
                String carType = "";
                
                switch (travelModeChoice) {
                    case 1:
                        travelMode = "air";
                        break;
                    case 2:
                        travelMode = "car";
                        carType = showCarTypeMenu(scanner, travelMode);
                        break;
                    case 3:
                        travelMode = "rail";
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        continue;
                }

        int journeyTypeChoice = showJourneyTypeMenu(scanner);
         double distanceMultiplier = (journeyTypeChoice == 2) ? 2.0 : 1.0;

        String apiUrl = "https://beta4.api.climatiq.io/travel/distance";
        String apiKey = "";
        try {
            ObjectNode requestBodyNode = objectMapper.createObjectNode();
            requestBodyNode.put("travel_mode", travelMode);
            ObjectNode originNode = objectMapper.createObjectNode();
            originNode.put("query", getOrigin(scanner));
            ObjectNode destinationNode = objectMapper.createObjectNode();
            destinationNode.put("query", getDestination(scanner));
            requestBodyNode.set("origin", originNode);
            requestBodyNode.set("destination", destinationNode);

        if ("car".equalsIgnoreCase(travelMode)) {
            ObjectNode carDetailsNode = objectMapper.createObjectNode();
            carDetailsNode.put("car_type", carType);
            requestBodyNode.set("car_details", carDetailsNode);
                }

   
        String requestBody = objectMapper.writeValueAsString(requestBodyNode);

        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

         connection.setRequestMethod("POST");
         connection.setRequestProperty("Content-Type", "application/json");
         connection.setRequestProperty("Authorization", "Bearer " + apiKey);
         connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
                byte[] requestBodyBytes = requestBody.getBytes("utf-8");
                os.write(requestBodyBytes, 0, requestBodyBytes.length);
            }
            int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            InputStream responseStream = connection.getInputStream();
            JsonNode responseJson = objectMapper.readTree(responseStream);
            double co2e = responseJson.get("co2e").asDouble() * distanceMultiplier;
            double distanceKm = responseJson.get("distance_km").asDouble() * distanceMultiplier;
            String originName = responseJson.get("origin").get("name").asText();
            String destinationName = responseJson.get("destination").get("name").asText();
            System.out.println("CO2 Emissions: " + co2e + " kg");
            System.out.println("Distance: " + distanceKm + " km");
            System.out.println("Origin: " + originName);
            System.out.println("Destination: " + destinationName);
         } else {
            System.err.println("Request failed with response code: " + responseCode);
            System.err.println("Error response:");
            String errorResponse = getResponseString(connection.getErrorStream());
            System.err.println(errorResponse);
            }
            connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.print("Do you want to calculate another route? (y/n): ");
            String userInput = scanner.nextLine().toLowerCase();
            continueCalculations = userInput.equals("y") || userInput.equals("y");
            }
                } catch (Exception e) {
                 e.printStackTrace();
                }   
        }

    private static String getResponseString(InputStream responseStream) throws IOException {
        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        }
        return response.toString();
    }


    private static String getOrigin(Scanner scanner) {
        System.out.print("Enter your origin destination: ");
        return scanner.nextLine();
    }

    private static String getDestination(Scanner scanner) {
        System.out.print("Enter the Fujitsu office address you are trying to get to: ");
        return scanner.nextLine();
    }

    private static int showTravelModeMenu(Scanner scanner) {
        System.out.println("Select Travel Mode:");
        System.out.println("1. Air");
        System.out.println("2. Car");
        System.out.println("3. Rail");
        System.out.print("Choose a number: ");

        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            choice = 0;
        }
        return choice;
    }

    private static String showCarTypeMenu(Scanner scanner, String travelMode) {
        if ("car".equalsIgnoreCase(travelMode)) {
        System.out.println("Select Car Type:");
        System.out.println("1. Petrol");
        System.out.println("2. Diesel");
        System.out.println("3. Hybrid");
        System.out.println("4. Plugin Hybrid");
        System.out.println("5. Battery");
        System.out.print("Choose a number: ");

        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            choice = 0;
        }

        Map<Integer, String> carTypeMap = new HashMap<>();
        carTypeMap.put(1, "petrol");
        carTypeMap.put(2, "diesel");
        carTypeMap.put(3, "hybrid");
        carTypeMap.put(4, "plugin_hybrid");
        carTypeMap.put(5, "battery");

        return carTypeMap.getOrDefault(choice, "petrol"); 
    } else {
        return "petrol"; 
    }
    }

    private static int showJourneyTypeMenu(Scanner scanner) {
        System.out.println("Select Journey Type:");
        System.out.println("1. Single");
        System.out.println("2. Return");
        System.out.print("Choose a number: ");

        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            choice = 0;
        }
        return choice;
    }
}
