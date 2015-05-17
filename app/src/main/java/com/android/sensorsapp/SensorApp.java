package com.android.sensorsapp;

import android.app.Application;

import com.android.sensorsapp.model.Scene;

import java.util.ArrayList;
import java.util.List;

public class SensorApp extends Application {

    private static List<Scene> scenes = new ArrayList<>();

    public static List<Scene> getScenes() {
        return scenes;
    }

    public static void addScene(Scene scene) {
        if (scenes == null) {
            scenes = new ArrayList<>();
        }
        scenes.add(scene);
    }

}
