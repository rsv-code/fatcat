# Fatcat Lib for Ic9
Fat web client for Ic9.

**Note:** This library is experimental at this point.

This library is a fat client that is built on JavaFX WebView component. Underneath it uses Webkit JS core. There is a Nashor <-> Webkit bridge that allows calls between the two JS engines. Objects are deep copied between the two engines.

## Building

Run the build.js file.

```
$ ic9 build.js
```

## Running

See/run the test.js file for a simple example.

```
$ ic9 test.js
```
