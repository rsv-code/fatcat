package com.lehman.ic9.ui;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import com.lehman.ic9.ic9engine;
import com.lehman.ic9.ic9exception;
import com.lehman.ic9.io.file;
import com.lehman.ic9.sys.sys;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebErrorEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

public class browserRegion extends Region
{
    private WebView wv = null;
    private WebEngine we = null;
    
    public URI target = null;
    
    private Stage stage = null;
    private ic9engine eng = null;
    private boolean wrapStdout = false;
    
    private boolean onLoadInitRan = false;
    boolean dragable = false;
    
    public browserRegion(Stage Stg, ic9engine Eng) { this.stage = Stg; this.eng = Eng; }
    
    public void setDraggable(boolean Dragable) {
        this.dragable = Dragable;
    }
    
    public void load() throws MalformedURLException, URISyntaxException
    {
        this.wv = new WebView();
        this.wv.setPickOnBounds(true);
        this.wv.setStyle("-fx-background-color: rgba(255, 255, 255, 0.0);");
        this.we = wv.getEngine();
        
        
        if(this.target == null)
        {
            System.out.println("this.target is null, setting to google.com.");
            this.target = (new URL("http://www.google.com")).toURI();
        }
        
        
        we.setOnError(new EventHandler<WebErrorEvent>()
        {
            @Override
            public void handle(WebErrorEvent event)
            {
                System.out.println("browser.onWebError(): " + event.getMessage());
            }
        });
        
        we.getLoadWorker().exceptionProperty().addListener(new ChangeListener<Throwable>() {
            @Override
            public void changed(ObservableValue<? extends Throwable> ov, Throwable t, Throwable t1)
            {
                System.out.println("browser.onLoadWebError(): " + t1.getMessage());
            }
        });
        
        we.getLoadWorker().stateProperty().addListener(new ChangeListener<State>()
        {
            @Override
            public void changed(ObservableValue<? extends State> observable, State oldValue, State state)
            {
                if (!onLoadInitRan) {
                    JSObject window = (JSObject) we.executeScript("window");
                    browserHooks bridge = new browserHooks(stage, wv, eng, window, dragable);
                    window.setMember("java", bridge);
                    window.setMember("Java", browserType.class);
                    
                    // Add jsenv.js and other needed functions for the bridge.
                    String stdJsLib = sys.getCurrentPath() + sys.seperator() + "stdjslib_fatcat/WebkitEnv.js";
                    try {
                        we.executeScript(file.read(stdJsLib));
                    } catch (ic9exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    
                    if (state == State.RUNNING || state == State.SUCCEEDED)
                    {
                        we.executeScript("document.body.style.overflow = 'hidden';");
                    }
                    onLoadInitRan = true;
                }
            }
        });
        
        we.documentProperty().addListener(new WebDocumentListener(we));
        
        // Load the URI.
        we.load(this.target.toString());
        
        // add the web view to the scene
        getChildren().add(wv);
    }
 
    @Override
    protected void layoutChildren()
    {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(wv , 0, 0, w, h, 0, HPos.CENTER, VPos.CENTER);
    }
 
    @Override
    protected double computePrefWidth(double height)
    {
        return 1024;
    }
 
    @Override
    protected double computePrefHeight(double width)
    {
        return 768;
    }
}
