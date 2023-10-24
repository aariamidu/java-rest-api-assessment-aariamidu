package com.cbfacademy.apiassessment;

public class Tree {
    private Long id;
    private String species;
    private double co2StoragePerTreePerYear;
    private double co2AbsorptionPerTreeIn80Years;
    
    public Tree() {
    }

    public Tree(Long id, String species, double co2StoragePerTreePerYear, double co2AbsorptionPerTreeIn80Years) {
        this.id = id;
        this.species = species;
        this.co2StoragePerTreePerYear = co2StoragePerTreePerYear;
        this.co2AbsorptionPerTreeIn80Years = co2AbsorptionPerTreeIn80Years;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public double getCo2StoragePerTreePerYear() {
        return co2StoragePerTreePerYear;
    }

    public void setCo2StoragePerTreePerYear(double co2StoragePerTreePerYear) {
        this.co2StoragePerTreePerYear = co2StoragePerTreePerYear;
    }

    public double getCo2AbsorptionPerTreeIn80Years() {
        return co2AbsorptionPerTreeIn80Years;
    }

    public void setCo2AbsorptionPerTreeIn80Years(double co2AbsorptionPerTreeIn80Years) {
        this.co2AbsorptionPerTreeIn80Years = co2AbsorptionPerTreeIn80Years;
    }
}
