package com.cbfacademy.apiassessment;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DestinationAddressService {

    private static final String DESTINATION_FILE_PATH = "src/main/resources/destination.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<DestinationAddress> getDestinationAddresses() {
        try {
            DestinationAddress[] addressesArray = objectMapper.readValue(new File(DESTINATION_FILE_PATH), DestinationAddress[].class);
            return new ArrayList<>(Arrays.asList(addressesArray));
        } catch (IOException e) {
            e.printStackTrace(); 
            return new ArrayList<>(); 
        }
    }

    public void saveDestinationAddresses(List<DestinationAddress> addresses) {
        try {
            objectMapper.writeValue(new File(DESTINATION_FILE_PATH), addresses);
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }
}
