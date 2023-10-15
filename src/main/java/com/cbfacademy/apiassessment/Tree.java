package com.cbfacademy.apiassessment;

public class Tree {
    private Long id;
    private String type;
    private double co2IntakeAfterOneYear;
    private int co2IntakeOverLifetime;

    public Tree(long l, String type, double co2IntakeAfterOneYear, int co2IntakeOverLifetime) {
        this.id = l;
        this.type = type;
        this.co2IntakeAfterOneYear = co2IntakeAfterOneYear;
        this.co2IntakeOverLifetime = co2IntakeOverLifetime;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getCo2IntakeAfterOneYear() {
        return co2IntakeAfterOneYear;
    }

    public void setCo2IntakeAfterOneYear(double co2IntakeAfterOneYear) {
        this.co2IntakeAfterOneYear = co2IntakeAfterOneYear;
    }

    public double getCo2IntakeOverLifetime() {
        return co2IntakeOverLifetime;
    }

    public void setCo2IntakeOverLifetime(int co2IntakeOverLifetime) {
        this.co2IntakeOverLifetime = co2IntakeOverLifetime;
    }
}
