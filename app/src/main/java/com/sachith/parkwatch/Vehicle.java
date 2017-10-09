package com.sachith.parkwatch;

/**
 * Created by sachs on 9/10/2017.
 */

public class Vehicle {
//    private String id;
    private String timestamp;
    private String registration;
    private String make;
    private String model;
    private String colour;
    private String type;
    private String carSpaces;


    public Vehicle(/*String id,*/ String timestamp, String registration, String make, String model, String colour, String type, String carSpaces) {
//        this.id = id;
        this.timestamp = timestamp;
        this.registration = registration;
        this.make = make;
        this.model = model;
        this.colour = colour;
        this.type = type;
        this.carSpaces = carSpaces;
    }

//    public String getId() {
//        return id;
//    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getRegistration() {
        return registration;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getColour() {
        return colour;
    }

    public String getType() {
        return type;
    }

    public String getCarSpaces() {
        return carSpaces;
    }
}
