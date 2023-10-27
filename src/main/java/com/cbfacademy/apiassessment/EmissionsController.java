package com.cbfacademy.apiassessment;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmissionsController {

    private final EmissionsCalculatorService emissionsCalculatorService;

    public EmissionsController(EmissionsCalculatorService emissionsCalculatorService) {
        this.emissionsCalculatorService = emissionsCalculatorService;
    }

    @PostMapping("api/calculate-emissions")
    public CalculationResult calculateEmissions(@RequestBody EmissionRequest request) {
        CalculationResult result = emissionsCalculatorService.calculateEmissions(request);
        if (result != null) {
            emissionsCalculatorService.saveResultToJsonFile(result);
        }
        return result;
    }
}
