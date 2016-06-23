/*
 * Copyright 2016 Austin Lehman
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

"use strict";
/*global Java, BaseObj, getEngine, setDef */

/**
 * Browser UI component is based upon JavaFX WebView object 
 * which uses Webkit underneath. This component creates a new 
 * FX application and will either load a URL or a provided 
 * HTML file. This object is experimental and still in 
 * development.
 * @param Eng Is an instance of the ic9engine.
 * @param Title Is a String with the browser window title.
 * @param Width Is an int with the window width.
 * @param Height Is an int with the window height.
 * @constructor
 */
function BrowserWin(Title, Width, Height) {
    BaseObj.call(this);

    // Set defaults.
    Title = setDef(Title, "IC9 Browser");
    Width = setDef(Width, 1024);
    Height = setDef(Height, 768);

    // The native Java object.
    this.native = null;
    var NativeBrowserWin = Java.type("com.lehman.ic9.ui.browserWin");
    this.native = new NativeBrowserWin(getEngine() , Title, Width, Height);
}
BrowserWin.prototype = new BaseObj();

/**
 * Sets the target URL for the browser window. This method has no effect 
 * if open() has already been called.
 * @param Url Is a String with the URL target.
 * @returns Object instance.
 */
BrowserWin.prototype.setUrl = function (UrlString) {
    this.native.setUrl(UrlString);
    return this;
};

/**
 * Sets the provided file name as the browser window target. This method has 
 * no effect if open() has already been called.
 * @param FileName Is a String with the file (.html) to open.
 * @returns Object instance.
 */
BrowserWin.prototype.setFile = function (FileName) {
    this.native.setFile(FileName);
    return this;
};

/**
 * Sets the flag for an undecorated window. If set to true the window 
 * will be missing the border and top window bar. This method has no effect 
 * if open() has already been called.
 * @param Undecorated Is a boolean with true for undecorated and false for not.
 */
BrowserWin.prototype.setUndecorated = function (Undecorated) {
    this.native.setUndecorated(Undecorated);
    return this;
};

/**
 * Sets the window transparent flag. If set to true the window itself will 
 * be transparent and also undecorated. If the web page body background style 
 * is set to a solid color, then it won't render as transparent. To set the 
 * body of the HTML document to transparent you can use something like 
 * background-color: rgba(0, 0, 0, 0.1); . This method has no effect if 
 * open() has already been called.
 * @param Transparent Is a boolean with true for transparent and false for not.
 */
BrowserWin.prototype.setTransparent = function (Transparent) {
    this.native.setTransparent(Transparent);
    return this;
};

/**
 * Sets the window maximized flag. If set to true the window will open 
 * maximized. This method has no effect if open() has already been 
 * called.
 * @param Maximized Is a boolean with true for maximized and false for not.
 */
BrowserWin.prototype.setMaximized = function (Maximized) {
    this.native.setMaximized(Maximized);
    return this;
};

/**
 * Sets the window full screen flag. If set to true the window will open 
 * in full screen mode. This method has no effect if open() has already 
 * been called.
 * @param FullScreen Is a boolean with true for full screen and false for not.
 */
BrowserWin.prototype.setFullScreen = function (FullScreen) {
    this.native.setFullScreen(FullScreen);
    return this;
};

BrowserWin.prototype.show = function () {
    this.native.showWin();
    return this;
};

BrowserWin.prototype.constructor = BrowserWin;