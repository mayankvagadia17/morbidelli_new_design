package com.morbidelli.morbidelli_design.model;

public class Vehicle {
    private int id;
    private String name;
    private String description;
    private int imageResource;
    private String type;
    private String power;
    private String battery;
    private String range;
    private String acceleration;
    private String category;
    
    // Original constructor for TestRideActivity compatibility
    public Vehicle(String name, int imageResId, String category) {
        this.name = name;
        this.imageResource = imageResId;
        this.category = category;
        this.id = name.hashCode(); // Generate ID from name
        this.description = "Premium vehicle with advanced features";
        this.type = "Motorcycle";
        this.power = "200";
        this.battery = "N/A";
        this.range = "N/A";
        this.acceleration = "N/A";
    }
    
    // Extended constructor for ModelSliderActivity
    public Vehicle(int id, String name, String description, int imageResource, 
                   String type, String power, String battery, String range, String acceleration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageResource = imageResource;
        this.type = type;
        this.power = power;
        this.battery = battery;
        this.range = range;
        this.acceleration = acceleration;
        this.category = type; // Use type as category for compatibility
    }
    
    // Getters
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public int getImageResource() {
        return imageResource;
    }
    
    public int getImageResId() {
        return imageResource; // Alias for compatibility
    }
    
    public String getType() {
        return type;
    }
    
    public String getPower() {
        return power;
    }
    
    public String getBattery() {
        return battery;
    }
    
    public String getRange() {
        return range;
    }
    
    public String getAcceleration() {
        return acceleration;
    }
    
    public String getCategory() {
        return category;
    }
    
    // Setters
    public void setId(int id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
    
    public void setImageResId(int imageResId) {
        this.imageResource = imageResId; // Alias for compatibility
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public void setPower(String power) {
        this.power = power;
    }
    
    public void setBattery(String battery) {
        this.battery = battery;
    }
    
    public void setRange(String range) {
        this.range = range;
    }
    
    public void setAcceleration(String acceleration) {
        this.acceleration = acceleration;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
}
