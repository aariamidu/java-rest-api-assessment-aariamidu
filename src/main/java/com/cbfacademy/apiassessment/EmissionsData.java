package com.cbfacademy.apiassessment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EmissionsData {
    private double co2e;
    private double distance;
    private String origin;
    private String destination;
    private String treeSpecies;
    private double co2StoragePerYear;
    private double co2AbsorptionIn80Years;
    private String journeyType;

    // Constructors
    public EmissionsData() {
    }

    public EmissionsData(double co2e, double distance, String origin, String destination,
            String treeSpecies, double co2StoragePerYear, double co2AbsorptionIn80Years) {
        this.co2e = co2e;
        this.distance = distance;
        this.origin = origin;
        this.destination = destination;
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

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
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

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
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

    public String getJourneyType() {
        return journeyType;
    }

    public void setJourneyType(String journeyType) {
        this.journeyType = journeyType;

    }

    public int getJourneyTypeAsInt() {
        if (journeyType == null) {
            return 1; // Assuming default is 'one way'
        }
        String lowerCaseJourneyType = journeyType.toLowerCase();
        if ("one way".equals(lowerCaseJourneyType)) {
            return 1;
        } else if ("return".equals(lowerCaseJourneyType)) {
            return 2;
        } else {
            return 1; // Handle other cases or set a default value
        }
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

    @Override
    public String toString() {
        return "EmissionsData{" +
                "co2e=" + co2e +
                ", distance=" + distance +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", treeSpecies='" + treeSpecies + '\'' +
                ", co2StoragePerYear=" + co2StoragePerYear +
                ", co2AbsorptionIn80Years=" + co2AbsorptionIn80Years +
                '}';
    }
}
