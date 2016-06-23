// Include needed tools.
include("util/ant.js");
include("util/ant/javaTargets.js");

// Build target.
ant.add(new Target({ id: 'build', depends: ['java_compile', 'java_jar', 'remove_build'] }));

// Create build directory target.
ant.add(new Mkdir({ id: 'make_build_dir', dir: 'build' }));

// Create the compile target.
ant.add(new Javac({ 
	id: 'java_compile',
	depends: ["make_build_dir"], 
	baseDir: "src", 										// Base code directory. Javac will be ran from here.
	srcDir: "com/lehman/ic9/ui", 							// Directory with the .java files.
	destDir: "../build",									// Destination directory. Path will be relative to baseDir.
	classPath: [ sys.getAssemblyPath() + "ic9.jar" ]		// List of classpath arguments needed.
}));

// Create the jar target.
ant.add(new Jar({
	id: 'java_jar',
	baseDir: 'build',										// Base jar directory. Jar command will be ran from here.
	jarName: '../lib/fatcat.jar',							// Result .jar file. Path will be relative to baseDir
	classFiles: 'com'										// Class files path, file name or wildcard (*.class).
}));

//Target to remove the build directory.
ant.add(new Rmdir({ id: 'remove_build', dir: 'build' }));


// Start the build.
ant.run(['build']);
