package com.lehman.ic9.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import javax.script.ScriptException;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import netscape.javascript.JSObject;

import com.lehman.ic9.ic9engine;

public class browserHooks
{
    class Delta { double x, y; public String toString() { return "(" + this.x + ", " + this.y + ")"; } } 
    
    private Stage stage = null;
    private WebView layout = null;
    private ic9engine eng = null;
    private JSObject win = null;
    
    final Delta dragDelta = new Delta();
    
    private boolean dragSupport = false;
    
    public browserHooks(Stage Stg, WebView Layout, ic9engine Eng, JSObject Win, boolean DragSupport)
    {
        this.stage = Stg;
        this.layout = Layout;
        this.eng = Eng;
        this.win = Win;
        
        if (DragSupport) {
            layout.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                  // record a delta distance for the drag and drop operation.
                  dragDelta.x = stage.getX() - mouseEvent.getScreenX();
                  dragDelta.y = stage.getY() - mouseEvent.getScreenY();
                }
              });
              layout.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                  stage.setX(mouseEvent.getScreenX() + dragDelta.x);
                  stage.setY(mouseEvent.getScreenY() + dragDelta.y);
                }
              });
        }
    }
    
    public void winError(String error, String url, int line)
    {
        System.err.println("[" + url + " : " + String.valueOf(line) + "] " + error);
    }
    
    public void conLog(String text)
    {
        System.out.println(text);
    }
    
    public void conInfo(String text)
    {
        System.out.println("[info] " + text);
    }
    
    public void conWarn(String text)
    {
        System.out.println("[warning] " + text);
    }
    
    public void conError(String text)
    {
        System.err.println("[error] " + text);
    }
    
    public void moveTo(int X, int Y) {
        this.stage.setX(X);
        this.stage.setY(Y);
    }
    
    public Object invoke(String FunctionName, Object args) throws NoSuchMethodException, ScriptException
    {
        List<Object> Args = new ArrayList<Object>();
        Object ret = null;
        if (args instanceof JSObject) {
            Object len = ((JSObject)args).getMember("length");
            int n = ((Number)len).intValue();
            for (int i = 0; i < n; ++i){
                Object targ = ((JSObject)args).getSlot(i);
                if (targ instanceof JSObject) {
                    String jstr = (String) this.win.call("JStringify", targ);
                    Object tobj = this.eng.invokeFunction("jParse", jstr);
                    Args.add(tobj);
                } else {
                    Args.add(targ);
                }
            }
        } else {
            Args.add(args);
        }
        Object result = this.eng.invokeFunction(FunctionName, Args.toArray(new Object[Args.size()]));
        if (result instanceof ScriptObjectMirror)
        {
            // Do object conversion here.
            String jstr = (String)this.eng.invokeFunction("jStringify", result);
            ret = this.win.call("JParse", jstr);
        }
        else
        {
            // Primitive type, simply return.
            ret = result;
        }
        return ret;
    }
    
    public Object eval(String FileName, String ScriptContents)
    {
        return this.eng.eval(FileName, ScriptContents);
    }
}
