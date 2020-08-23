package com.example.pcapp;

public class Car {

    private String brand, model, fuel, key;

    public Car() {

    }

    public Car(String brand, String model, String fuel, String key) {
        this.brand = brand;
        this.model = model;
        this.fuel = fuel;
        this.key = key;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getFuel() {
        return fuel;
    }

    public String getKey() {
        return key;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
