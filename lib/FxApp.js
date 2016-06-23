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
/*global Java, BaseObj */

includeJar ("lib/fatcat.jar");

/**
 * 
 * @constructor
 */
function FxApp(OnStart) {
    var NativeFxApp;
    BaseObj.call(this);

    if (!isDef(OnStart) || !isFunct(OnStart)) {
        throw ("FxApp.FxApp(): Expecting first arguement to be the on start handler.");
    }
    
    // The native Java object.
    NativeFxApp = Java.type("com.lehman.ic9.ui.fxApp");
    this.native = new NativeFxApp();
    this.native.init(getEngine(), OnStart);
}
FxApp.prototype = new BaseObj();

/**
 * Launches the application window and shows it.
 */
FxApp.prototype.launch = function () {
    this.native.launchApp();
};

FxApp.prototype.constructor = FxApp;


function getFunctNames(ObjName) {
    var name, names = [];
    for (name in this[ObjName]) {
        if (isFunct(this[ObjName][name])) {
            names.push(name);
        }
    }
    return names;
}

function callNashornObj() {
    var cinfo = arguments[0];
    var ret = this[cinfo.objName][cinfo.methName].apply(this[cinfo.objName], cinfo.args);
    return ret;
}
