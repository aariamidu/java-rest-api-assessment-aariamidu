package com.cbfacademy.apiassessment;

public class JourneyRequest extends AbstractJourney {
    private int destinationId;

    public JourneyRequest(String origin, int destinationId, String journeyType, String travelMode, String carType) {
        super(origin, journeyType, travelMode, carType);
        this.destinationId = destinationId;

    }

    public int getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }

}
