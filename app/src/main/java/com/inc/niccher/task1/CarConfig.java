package com.inc.niccher.task1;

/**
 * Created by niccher on 04/06/19.
 */

public class CarConfig {
    String Maker, Body, Model, Year, Mileage, Condition,Engine,Color,Transmision,Interior,Fuel,Desc,Timest;

    public CarConfig() {
    }

    public CarConfig(String maker, String body, String model, String year, String mileage, String condition, String engine, String color, String transmision, String interior, String fuel, String desc, String timest) {
        Maker = maker;
        Body = body;
        Model = model;
        Year = year;
        Mileage = mileage;
        Condition = condition;
        Engine = engine;
        Color = color;
        Transmision = transmision;
        Interior = interior;
        Fuel = fuel;
        Desc = desc;
        Timest = timest;
    }

    public String getMaker() {
        return Maker;
    }

    public void setMaker(String maker) {
        Maker = maker;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getMileage() {
        return Mileage;
    }

    public void setMileage(String mileage) {
        Mileage = mileage;
    }

    public String getCondition() {
        return Condition;
    }

    public void setCondition(String condition) {
        Condition = condition;
    }

    public String getEngine() {
        return Engine;
    }

    public void setEngine(String engine) {
        Engine = engine;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getTransmision() {
        return Transmision;
    }

    public void setTransmision(String transmision) {
        Transmision = transmision;
    }

    public String getInterior() {
        return Interior;
    }

    public void setInterior(String interior) {
        Interior = interior;
    }

    public String getFuel() {
        return Fuel;
    }

    public void setFuel(String fuel) {
        Fuel = fuel;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getTimest() {
        return Timest;
    }

    public void setTimest(String timest) {
        Timest = timest;
    }
}