package com.cbfacademy.apiassessment;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DestinationAddressService {

    private static final String DESTINATION_FILE_PATH = "src/main/resources/destination.json";
    private final ObjectMapper objectMapper;

    public DestinationAddressService() {
        this.objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    }

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

    public DestinationAddress getDestinationAddress(int destinationId) {
        List<DestinationAddress> addresses = getDestinationAddresses();
        for (DestinationAddress address : addresses) {
            if (address.getId() == destinationId) {
                return address;
            }
        }
        return null; // If address with the given ID not found
    }

    public DestinationAddress addDestinationAddress(DestinationAddress address) {
        List<DestinationAddress> addresses = getDestinationAddresses();
        int newId = addresses.stream().mapToInt(DestinationAddress::getId).max().orElse(0) + 1;
        address.setId(newId);
        addresses.add(address);
        saveDestinationAddresses(addresses);
        return address;
    }

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

    public void saveDestinationAddresses(List<DestinationAddress> addresses) {
        try {
            objectMapper.writeValue(new File(DESTINATION_FILE_PATH), addresses);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
