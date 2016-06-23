package com.lehman.ic9.ui;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import com.lehman.ic9.ic9engine;
import com.lehman.ic9.ic9exception;

/**
 * Browser object implements a JavaFX browser window. This 
 * object is experimental and still in development. Built 
 * upon JavaFX this object extends Application.
 * @author Austin Lehman
 */
public class browserWin extends Stage
{
    private ic9engine eng = null;
    
    // The scene.
    private Scene scene = null;
    
    private static String title = "IC9 Browser";
    private static int width = 1024;
    private static int height = 768;
    private static boolean undecorated = false;
    private static boolean transparent = false;
    private static boolean maximized = false;
    private static boolean fullScreen = false;
    
    private static browserRegion br = null;
    
    /**
     * Sets the base parameters for the browser window.
     * @param Eng Is an instance of the ic9engine.
     * @param Title Is a String with the browser window title.
     * @param Width Is an int with the window width.
     * @param Height Is an int with the window height.
     */
    public browserWin(ic9engine Eng, String Title, int Width, int Height) {
        eng = Eng;
        title = Title;
        width = Width;
        height = Height;
        br = new browserRegion(this, Eng);
    }
    
    
    public void showWin() throws Exception
    {
        // If undecorated.
        if (undecorated) {
            this.initStyle(StageStyle.UNDECORATED);
        }
        
        // If transparent window, set the style to transparent.
        if(transparent) {
            this.initStyle(StageStyle.TRANSPARENT);
        }
        
        if(maximized) {
            if(transparent || undecorated) {
                Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                width = (int) screenBounds.getWidth();
                height = (int) screenBounds.getHeight();
            }
            else {
                this.setMaximized(maximized);
            }
        }
        
        this.setFullScreen(fullScreen);
        
        // Load the content.
        br.setStyle("-fx-background-color: rgba(255, 255, 255, 0.0);");
        br.load();
        
        // create the scene
        this.setTitle(title);
        scene = new Scene(br, width, height, Color.web("#666970"));
        // Transparent window.
        if (transparent) scene.setFill(Color.TRANSPARENT);
        this.setScene(scene);
        
        this.show();
    }
    
    /**
     * Sets the target URL for the browser window. This method has no effect 
     * if open() has already been called.
     * @param Url Is a String with the URL target.
     * @throws MalformedURLException Exception
     * @throws URISyntaxException Exception
     */
    public void setUrl(String Url) throws MalformedURLException, URISyntaxException 
    {
        URL u = new URL(Url);
        br.target = u.toURI();
    }
    
    /**
     * Sets the provided file name as the browser window target. This method has 
     * no effect if open() has already been called.
     * @param FileName Is a String with the file (.html) to open.
     * @throws ic9exception Exception
     */
    public void setFile(String FileName) throws ic9exception
    {
        File file = new File(FileName);
        if(file.exists())
            br.target = file.toURI();
        else
            throw new ic9exception("browser.loadFile(): Provided file name '" + FileName + "' doesn't exist.");
    }
    
    /**
     * Sets the flag for an undecorated window. If set to true the window 
     * will be missing the border and top window bar. This method has no effect 
     * if open() has already been called.
     * @param Undecorated Is a boolean with true for undecorated and false for not.
     */
    public void setUndecorated(boolean Undecorated)
    {
        undecorated = Undecorated;
    }
    
    /**
     * Sets the window transparent flag. If set to true the window itself will 
     * be transparent and also undecorated. If the web page body background style 
     * is set to a solid color, then it won't render as transparent. To set the 
     * body of the HTML document to transparent you can use something like 
     * background-color: rgba(0, 0, 0, 0.1); . This method has no effect if 
     * open() has already been called.
     * @param Transparent Is a boolean with true for transparent and false for not.
     */
    public void setTransparent(boolean Transparent)
    {
        transparent = Transparent;
        this.br.setDraggable(true);
    }
}
