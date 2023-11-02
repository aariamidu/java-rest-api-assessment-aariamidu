package com.cbfacademy.apiassessment;

public class JourneyRequest extends AbstractJourney {
    private int destinationId;

    public JourneyRequest(String origin, int destinationId, String journeyType) {
        super(origin, journeyType);
        this.destinationId = destinationId;

    }

    public int getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }

}
