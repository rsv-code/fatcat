#!/usr/bin/ic9

include ("lib/FxApp.js");
include ("lib/BrowserWin.js");

var app = new FxApp(function () {
	// Called when the app is launched to build the UI.
	var win = new BrowserWin("Test", 1024, 768);
	win.setTransparent(true);
	win.setFile("browser/index.html");
	win.show();
});

app.launch();