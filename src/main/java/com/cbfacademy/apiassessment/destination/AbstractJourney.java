package com.cbfacademy.apiassessment.destination;

/**
 * Abstract class representing a journey with basic information such as origin,
 * journey type, travel mode, and car type.
 */
public abstract class AbstractJourney {
    protected String origin;
    private String journeyType;
    private String travelMode;
    private String carType;

    // Constructor for AbstractJourney class.
    public AbstractJourney(String origin, String journeyType, String travelMode, String carType) {
        this.origin = origin;
        this.journeyType = journeyType;
        this.travelMode = travelMode;
        this.carType = carType;
    }

    // Getters and setters
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

    /**
     * Gets the journey type as an integer.
     * 1 represents "one way," 2 represents "return" (default is assumed to be "one
     * way").
     *
     * @return The integer representation of the journey type.
     */
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
