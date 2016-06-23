package com.lehman.ic9.ui;

import java.util.Map;

import com.lehman.ic9.ic9engine;

import javafx.application.Application;
import javafx.stage.Stage;

public class fxApp extends Application
{
    private static boolean initRan = false;
    private static ic9engine eng = null;
    private static Map<String, Object> onStart = null;
    
    // Neede by JavaFX to be no-arg constructor.
    public fxApp() { }
    
    public void init(ic9engine Eng, Map<String, Object> OnStart)
    {
        eng = Eng;
        onStart = OnStart;
        initRan = true;
    }
    
    /**
     * Static method called by the member open() method to 
     * launch the application.
     */
    public static void start() { launch(); }
    
    /**
     * Launches the application window and shows it.
     */
    public void launchApp()
    {
        start();
    }
    
    /**
     * Called from JavaFX to create the application.
     */
    @Override
    public void start(Stage ignoreStage) throws Exception
    {
        if(initRan) {
            eng.invokeMethod(onStart, "call");
        } else {
            ignoreStage.show();
        }
    }
    
}
