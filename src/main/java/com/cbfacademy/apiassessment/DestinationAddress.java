package com.cbfacademy.apiassessment;

public class DestinationAddress {
    private int id;
    private String name;
    private String address;

    public DestinationAddress() {
    }

    public DestinationAddress(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
