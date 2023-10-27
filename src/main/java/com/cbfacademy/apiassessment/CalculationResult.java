package com.cbfacademy.apiassessment;

public class CalculationResult {
    private double co2Emissions;
    private double distanceKm;
    private String origin;
    private String destination;
    private String travelMode;
    private String carType;

    // getters and setters
    public double getCo2Emissions() {
        return co2Emissions;
    }

    public void setCo2Emissions(double co2Emissions) {
        this.co2Emissions = co2Emissions;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(double distanceKm) {
        this.distanceKm = distanceKm;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

}