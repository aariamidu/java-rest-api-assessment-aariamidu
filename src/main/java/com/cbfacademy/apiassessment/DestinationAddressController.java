package com.cbfacademy.apiassessment;

import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/destination-addresses")
    public List<DestinationAddress> getDestinationAddresses() {
        return destinationAddressService.getDestinationAddresses();
    }

    @PostMapping("/destination-addresses")
    public void saveDestinationAddresses(@RequestBody List<DestinationAddress> addresses) {
        destinationAddressService.saveDestinationAddresses(addresses);
    }
}
