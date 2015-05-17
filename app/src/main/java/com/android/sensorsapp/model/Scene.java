package com.android.sensorsapp.model;

import java.io.Serializable;
import java.util.Random;

public class Scene implements Serializable {

    public enum ThermostatMode {
        AUTO, HEAT, COOL, OFF
    }

    private String name;
    private String description;
    private boolean hallLamp;
    private int bedroomLight;
    private int coolingTemp;
    private int heatingTemp;
    private ThermostatMode mode;
    private boolean fan;

    private boolean checked;
    private int color;

    public Scene() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isHallLampOn() {
        return hallLamp;
    }

    public void setHallLamp(boolean hallLamp) {
        this.hallLamp = hallLamp;
    }

    public int getBedroomLight() {
        return bedroomLight;
    }

    public void setBedroomLight(int bedroomLight) {
        this.bedroomLight = bedroomLight;
    }

    public boolean isBedroomLightOn() {
        return bedroomLight == 100;
    }

    public boolean isBedroomLightOff() {
        return bedroomLight == 0;
    }

    public int getCoolingTemp() {
        return coolingTemp;
    }

    public void setCoolingTemp(int coolingTemp) {
        this.coolingTemp = coolingTemp;
    }

    public int getHeatingTemp() {
        return heatingTemp;
    }

    public void setHeatingTemp(int heatingTemp) {
        this.heatingTemp = heatingTemp;
    }

    public ThermostatMode getMode() {
        return mode;
    }

    public void setMode(ThermostatMode mode) {
        this.mode = mode;
    }

    public boolean isFanOn() {
        return fan;
    }

    public void setFan(boolean fan) {
        this.fan = fan;
    }

    public boolean isChecked() { return checked; }

    public void setChecked(boolean checked) { this.checked = checked; }

    public int getSensorsCount() {
        int result = 0;
        result += isHallLampOn() ? 1 : 0;
        result += !isBedroomLightOff() ? 1 : 0;
        result += (mode != ThermostatMode.OFF) ? 1 : 0;
        result += isFanOn() ? 1 : 0;
        return result;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

}
