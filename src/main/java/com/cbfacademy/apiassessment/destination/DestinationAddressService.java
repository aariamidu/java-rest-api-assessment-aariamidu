package com.cbfacademy.apiassessment.destination;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.cbfacademy.apiassessment.emissions.JsonFileWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Service class for managing destination addresses.
 */
@Service
public class DestinationAddressService {

    private static final String DESTINATION_FILE_PATH = "src/main/resources/destination.json";
    private final ObjectMapper objectMapper;
    private final JsonFileWriter jsonFileWriter;

    // Constructor with dependency injection
    public DestinationAddressService(JsonFileWriter jsonFileWriter) {
        this.objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        this.jsonFileWriter = jsonFileWriter;
    }

    // Get a list of all destination addresses
    public List<DestinationAddress> getDestinationAddresses() {
        try {
            DestinationAddress[] addressesArray = objectMapper.readValue(new File(DESTINATION_FILE_PATH),
                    DestinationAddress[].class);
            return new ArrayList<>(Arrays.asList(addressesArray));
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Get a specific destination address by ID
    public DestinationAddress getDestinationAddress(int destinationId) {
        List<DestinationAddress> addresses = getDestinationAddresses();
        for (DestinationAddress address : addresses) {
            if (address.getId() == destinationId) {
                return address;
            }
        }
        return null; // If address with the given ID not found
    }

    // Add a new destination address
    public void addDestinationAddress(DestinationAddress newAddress) {
        List<DestinationAddress> addresses = getDestinationAddresses();
        int maxId = addresses.stream().mapToInt(DestinationAddress::getId).max().orElse(0);
        int newId = maxId + 1;
        DestinationAddress destinationAddress = new DestinationAddress(newId, newAddress.getName(),
                newAddress.getAddress());
        addresses.add(destinationAddress);
        boolean writeSuccess = jsonFileWriter.writeDestinationJsonFile(addresses);
        if (!writeSuccess) {
            System.out.println("Failed to write addresses to destination.json");
        }
    }

    // Update an existing destination address
    public DestinationAddress updateDestinationAddress(int id, DestinationAddress updatedAddress) {
        List<DestinationAddress> addresses = getDestinationAddresses();
        for (DestinationAddress address : addresses) {
            if (address.getId() == id) {
                // Updates the existing address with the new data
                address.setName(updatedAddress.getName());
                address.setAddress(updatedAddress.getAddress());
                // Saves the updated addresses
                saveDestinationAddresses(addresses);
                return address;
            }
        }
        return null; // If address with the given ID not found
    }

    // Delete a destination address by ID
    public boolean deleteDestinationAddress(int destinationId) {
        List<DestinationAddress> addresses = getDestinationAddresses();
        for (DestinationAddress address : addresses) {
            if (address.getId() == destinationId) {
                addresses.remove(address);
                saveDestinationAddresses(addresses);
                return true;
            }
        }
        return false; // If address with the given ID not found
    }

    // Save a list of destination addresses to the JSON file
    public void saveDestinationAddresses(List<DestinationAddress> addresses) {
        try {
            objectMapper.writeValue(new File(DESTINATION_FILE_PATH), addresses);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
