package com.cbfacademy.apiassessment;

//Superclass(`JourneyRequest`):`JourneyRequest`is the superclass or parent,and it contains common properties and methods related to a journey request.-It has three fields:`origin`,`destinationId`,and`journeyType`.`origin`and`destination`are protected,meaning they are accessible to subclasses but not to the external classes.-It provides getters and setters for`origin`,`destinationId`,and`journeyType`.

//Subclass(`EmissionsData`):`EmissionsData`is the subclass or child of`JourneyRequest`.-It inherits the fields and methods from`JourneyRequest`because it extends the`JourneyRequest`class.-`EmissionsData`has additional fields:`co2e`,`distance`,`treeSpecies`,`co2StoragePerYear`,and`co2AbsorptionIn80Years`.-The constructor of`EmissionsData`takes additional parameters(`co2e`,`distance`,`treeSpecies`,`co2StoragePerYear`,and`co2AbsorptionIn80Years`)along with the parameters of the superclass(`origin`,`destination`,and`journeyType`).It initializes both the fields of the subclass and the superclass using the`super`keyword.This ensures that the superclass fields are properly initialized when creating an object of`EmissionsData`.-`EmissionsData`includes getters and setters for its specific fields(`co2e`,`distance`,`treeSpecies`,`co2StoragePerYear`,and`co2AbsorptionIn80Years`).

public class JourneyRequest {
    protected String origin;
    protected int destinationId;
    private String journeyType;

    public JourneyRequest(String origin, int destinationId, String journeyType) {
        this.origin = origin;
        this.destinationId = destinationId;
        this.journeyType = journeyType;
    }

    public String getOrigin() {
        return origin;
    }

    public int getDestinationId() {
        return destinationId;
    }

    public String getJourneyType() {
        return journeyType;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
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
