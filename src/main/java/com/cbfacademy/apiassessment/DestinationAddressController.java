package com.cbfacademy.apiassessment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
            return ResponseEntity.ok(destinationAddress); // Address found, return with 200 OK status
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null); // Address not found, return with 404 Not Found status
        }
    }

    @PostMapping("/api/destination-addresses")
    public void saveDestinationAddresses(@RequestBody List<DestinationAddress> addresses) {
        destinationAddressService.saveDestinationAddresses(addresses);
    }
}
