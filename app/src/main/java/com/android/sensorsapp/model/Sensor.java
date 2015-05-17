package com.android.sensorsapp.model;

import com.android.sensorsapp.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Sensor implements Serializable {

    private String title;
    private String state;
    private int icon;
    private int color;
    private boolean lowBattery;
    private boolean tampered;

    public Sensor(String title, String state, int icon,
                  int color, boolean lowBattery, boolean tampered)
    {
        this.title = title;
        this.state = state;
        this.icon = icon;
        this.color = color;
        this.lowBattery = lowBattery;
        this.tampered = tampered;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isLowBattery() {
        return lowBattery;
    }

    public void setLowBattery(boolean lowBattery) {
        this.lowBattery = lowBattery;
    }

    public boolean isTampered() {
        return tampered;
    }

    public void setTampered(boolean tampered) {
        this.tampered = tampered;
    }

    public static List<Sensor> getTempSensors() {
        List<Sensor> sensors = new ArrayList<>();
        sensors.add(new Sensor("Livingroom Light", "ON", R.mipmap.icon_livingroom_light, 0xff00a8e5, true, false));
        sensors.add(new Sensor("Kitchen Door", "OPEN", R.mipmap.icon_kitchen_door, 0xff0096cc, false, true));
        sensors.add(new Sensor("Hall Motion Sensor", "NO MOTION", R.mipmap.icon_hall_motion, 0xff0083b3, false, false));
        sensors.add(new Sensor("Main Thermostat", "Idle. LO 74F. HI 86F", R.mipmap.icon_main_thermostat, 0xff007099, true, true));
        return sensors;
    }

}
