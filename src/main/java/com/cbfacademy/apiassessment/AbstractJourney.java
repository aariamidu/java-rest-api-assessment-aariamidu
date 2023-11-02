package com.cbfacademy.apiassessment;

public abstract class AbstractJourney {
    protected String origin;
    private String journeyType;

    public AbstractJourney(String origin, String journeyType) {
        this.origin = origin;
        this.journeyType = journeyType;
    }

    public String getOrigin() {
        return origin;
    }

    public String getJourneyType() {
        return journeyType;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
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
            return 1;
        }
    }
}
