package com.cbfacademy.apiassessment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class DestinationAddressController {

    private final DestinationAddressService destinationAddressService;

    public DestinationAddressController(DestinationAddressService destinationAddressService) {
        this.destinationAddressService = destinationAddressService;
    }

    @GetMapping("/api/destination-addresses")
    public List<DestinationAddress> getDestinationAddresses() {
        return destinationAddressService.getDestinationAddresses();
    }

    @GetMapping("/api/destination-addresses/{id}")
    public ResponseEntity<DestinationAddress> getDestinationAddressById(@PathVariable int destinationId) {
        DestinationAddress destinationAddress = destinationAddressService.getDestinationAddress(destinationId);
        if (destinationAddress != null) {
            return ResponseEntity.ok(destinationAddress);// If address found, return with 200 OK status
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);// If address not found, return with 404 Not Found status
        }
    }

    @PostMapping("/api/destination-addresses/add")
    public ResponseEntity<DestinationAddress> addDestinationAddress(@RequestBody DestinationAddress address) {
        if (address != null && isValidDestinationAddress(address)) {
            DestinationAddress addedAddress = destinationAddressService.addDestinationAddress(address);
            return new ResponseEntity<>(addedAddress, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/api/destination-addresses/update/{id}")
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

    private boolean isValidDestinationAddress(DestinationAddress address) {
        // Checks if the address object is not null
        return address != null;
    }

    @DeleteMapping("/api/destination-addresses/delete/{id}")
    public ResponseEntity<String> deleteDestinationAddress(@PathVariable int id) {
        boolean deletionResult = destinationAddressService.deleteDestinationAddress(id);
        if (deletionResult) {
            return new ResponseEntity<>("Address deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Address not found with the given ID", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/api/destination-addresses")
    public void saveDestinationAddresses(@RequestBody List<DestinationAddress> addresses) {
        destinationAddressService.saveDestinationAddresses(addresses);
    }
}
