package com.cbfacademy.apiassessment;

public class EmissionRequest {
    private String travelMode;
    private String carType;
    private String origin;
    private String destination;
    private int journeyType;

    // getters and setters
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

    public int getJourneyType() {
        return journeyType;
    }

    public void setJourneyType(int journeyType) {
        this.journeyType = journeyType;
    }
}


