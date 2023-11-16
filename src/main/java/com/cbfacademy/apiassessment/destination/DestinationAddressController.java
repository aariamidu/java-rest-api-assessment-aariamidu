package com.cbfacademy.apiassessment.destination;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cbfacademy.apiassessment.emissions.JsonFileWriter;

import java.util.List;

/**
 * REST controller for managing destination addresses.
 */
@RestController
@RequestMapping("/api/destination-addresses")
public class DestinationAddressController {

    private final DestinationAddressService destinationAddressService;

    // Constructor with dependency injection
    public DestinationAddressController(DestinationAddressService destinationAddressService,
            JsonFileWriter jsonFileWriter) {
        this.destinationAddressService = destinationAddressService;

    }

    // Get all destination addresses
    @GetMapping
    public List<DestinationAddress> getDestinationAddresses() {
        return destinationAddressService.getDestinationAddresses();
    }

    // Add a new destination address
    @PostMapping
    public ResponseEntity<DestinationAddress> addDestinationAddress(@RequestBody DestinationAddress address) {
        try {
            destinationAddressService.addDestinationAddress(address);
            return new ResponseEntity<>(address, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete a destination address by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDestinationAddress(@PathVariable int id) {
        boolean deletionResult = destinationAddressService.deleteDestinationAddress(id);
        if (deletionResult) {
            return new ResponseEntity<>("Address deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Address not found", HttpStatus.NOT_FOUND);
        }
    }

    // Update a destination address by ID
    @PutMapping("/{id}")
    public ResponseEntity<DestinationAddress> updateDestinationAddress(@PathVariable int id,
            @RequestBody DestinationAddress address) {
        if (address != null && isValidDestinationAddress(address)) {
            DestinationAddress updatedAddress = destinationAddressService.updateDestinationAddress(id, address);
            if (updatedAddress != null) {
                return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Validate if a destination address is valid
    private boolean isValidDestinationAddress(DestinationAddress address) {
        // Checks if the address object is not null
        return address != null;
    }

    // Get a destination address by ID
    @GetMapping("/{id}")
    public ResponseEntity<DestinationAddress> getDestinationAddressById(@PathVariable int id) {
        DestinationAddress destinationAddress = destinationAddressService.getDestinationAddress(id);
        if (destinationAddress != null) {
            return ResponseEntity.ok(destinationAddress);// If address found, return with 200 OK status
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);// If address not found, return with 404 Not Found status
        }
    }

    // Save multiple destination addresses
    public void saveDestinationAddresses(@RequestBody List<DestinationAddress> addresses) {
    }
}