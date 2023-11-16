package com.cbfacademy.apiassessment.destination;

/**
 * Represents a journey request with specific information.
 * This class is a child of AbstractJourney, inheriting its properties origin,
 * journeyType, travelMode and carType.
 */
public class JourneyRequest extends AbstractJourney {
    private int destinationId;

    // Constructor for JourneyRequest class.
    public JourneyRequest(String origin, int destinationId, String journeyType, String travelMode, String carType) {
        super(origin, journeyType, travelMode, carType);
        this.destinationId = destinationId;

    }

    // Getters and setters
    public int getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }

}
