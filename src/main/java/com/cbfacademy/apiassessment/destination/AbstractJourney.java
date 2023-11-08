package com.cbfacademy.apiassessment.destination;

public abstract class AbstractJourney {
    protected String origin;
    private String journeyType;
    private String travelMode;
    private String carType;

    public AbstractJourney(String origin, String journeyType, String travelMode, String carType) {
        this.origin = origin;
        this.journeyType = journeyType;
        this.travelMode = travelMode;
        this.carType = carType;
    }

    public String getOrigin() {
        return origin;
    }

    public String getJourneyType() {
        return journeyType;
    }

    public String getTravelMode() {
        return travelMode;
    }

    public String getCarType() {
        return carType;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setJourneyType(String journeyType) {
        this.journeyType = journeyType;

    }

    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }

    public void setCarType(String carType) {
        this.carType = carType;
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
            return 1;
        }
    }
}
