# PLEASE READ THIS DOC!

## SETUP - REQUIRED!
**You need to setup your project a little bit before it works.** <br>
Step 1. Change groupId and archiveId in pom.xml to your project ones.<br>
Step 2. Change the plugin.yml to your plugin<br>
Step 3. Change pom.xml's properties.minecraftversion to your plugins minecraft version.<br>
Step 3. Simply hit run and compile your plugin!<br>

## TO ADD DEPENDENCIES
**in order to use maven, do <i>not</i> use the repl.it packager. use the pom.xml.** <br>
Step 1. Add dependency/repository to pom.<br>
Step 1A. Add it to the dependencies of plugin.yml<br>
Step 2. Run project to import.<br>
Step 3. Done!

## TO RUN PROJECT
Running project isnt really a thing in repl with minecraft plugins as theres no server to run it on (unless you run a server in repl, but you cant test it in-browser unless you fork the @gavingogamingrepl/gavlercraft repl - its only on 1.5.2 minecraft though.), but hit the run button to compile your plugin. Then, download it from `target/<archiveId>-<version>.jar` and throw it on a minecraft server with the same version as the plugin.