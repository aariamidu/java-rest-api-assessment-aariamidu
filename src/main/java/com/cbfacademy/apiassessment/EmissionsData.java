package com.cbfacademy.apiassessment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EmissionsData extends JourneyRequest {
    private double co2e;
    private double distance;
    private String treeSpecies;
    private double co2StoragePerYear;
    private double co2AbsorptionIn80Years;

    public EmissionsData(double co2e, double distance, String treeSpecies, double co2StoragePerYear,
            double co2AbsorptionIn80Years, String origin, int destinationId, String journeyType) {
        super(origin, destinationId, journeyType);
        this.co2e = co2e;
        this.distance = distance;
        this.treeSpecies = treeSpecies;
        this.co2StoragePerYear = co2StoragePerYear;
        this.co2AbsorptionIn80Years = co2AbsorptionIn80Years;
    }

    // Getters
    public double getCo2e() {
        return co2e;
    }

    public double getDistance() {
        return distance;
    }

    public String getTreeSpecies() {
        return treeSpecies;
    }

    public double getCo2StoragePerYear() {
        return co2StoragePerYear;
    }

    public double getCo2AbsorptionIn80Years() {
        return co2AbsorptionIn80Years;
    }

    // Setters
    public void setCo2e(double co2e) {
        this.co2e = co2e;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setTreeSpecies(String treeSpecies) {
        this.treeSpecies = treeSpecies;
    }

    public void setCo2StoragePerYear(double co2StoragePerYear) {
        this.co2StoragePerYear = co2StoragePerYear;
    }

    public void setCo2AbsorptionIn80Years(double co2AbsorptionIn80Years) {
        this.co2AbsorptionIn80Years = co2AbsorptionIn80Years;
    }

    public String toJsonString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String toString() {
        return "EmissionsData{" +
                "co2e=" + co2e +
                ", distance=" + distance +
                ", origin='" + origin + '\'' +
                ", destinationId='" + destinationId + '\'' +
                ", treeSpecies='" + treeSpecies + '\'' +
                ", co2StoragePerYear=" + co2StoragePerYear +
                ", co2AbsorptionIn80Years=" + co2AbsorptionIn80Years +
                '}';
    }
}