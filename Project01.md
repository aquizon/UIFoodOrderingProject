# Food Ordering Management Interface Project

## Description

* An Interface that allows the user (more specifically a food order manager) to add, remove, and display orders, drivers, and restaurants in table databases. The user can select a certain order and "complete" the order, removing it from the current orders and into a list of completed orders. The user can also add and remove drivers and restaurants, which will reflect when trying to add orders. The user can switch between tabs to manage all these functionalities.

## Getting Started

### Dependencies

Requires one of the two systems:
* MacOS 10.12.0 or beyond 
* Windows 10

### Installing/Requirements

* Install Java19 jdk from the Java website 
* Install JavaFX19 sdk from the JAVAFX19 website

``NB: compatible versions of Java17 and JavaFX17 will also work, but the JavaFX17 package is locked beehind a request wall on the website``

* Any java program editor such as VSCode or JEdit

### Executing program

**Firstly**, on the command line interface, you'll need to **create a variable that points to your JavaFX library** location. 

Depending on your operating system you'll need different commands in order to point to your JavaFX application while compiling


On MacOS the command is 
```
export JAVAFX_HOME="/path/to/javafx/lib"
```
and on Windows the command is 
```
set JAVAFX_HOME="\path\to\javafx\lib"
```
You can replace ``"\path\to\javafx\lib"`` with the absolute path to the library located inside of your javaFX jdk file. 

**Secondly**, in order to **compile your program**, change directories to the folder that contains the program you would like to compile and run the following command on either system
```
javac --module-path %JAVAFX_HOME% --add-modules javafx.controls,javafx.fxml <javaFile.java>
```
replacing ``<javaFile.java>`` with the name of your file.

**Lastly**, **run the program** with the following command
```
java --module-path %JAVAFX_HOME% --add-modules javafx.controls,javafx.fxml <javaFile>
```
replacing ``javaFile`` with the name of your compiled file.


## Help

If you run into errors, here are some things you should ask yourself:

* Are my Java and JavaFX compatible versions?
* Have I typed/pasted in the correct path to JavaFX library?
* Have I changed directories to the correct place?

If you run into these errors, you can fix them by
* Checking your operating system version and redownloading compatible Java and JavaFX versions
* Placing your JavaFX sdk into an easily accessible place
* Using the ``ls`` command on MacOS or ``dir`` command on Windows to display what is inside of the current folder.
    * If you are on MacOS you may also use ``pwd`` to check your absolute path, but windows automatically shows you this so it does not recognize this command

## Author
* Allen Quizon

## Version History
* Part 1
    * Designed the interface on paper and then followed by implementation
    * Added the orders in progress and orders completed tables
    * Added add order to orders in progrress functionality
    * All functionality on one window
* Part 2
    * Added drivers table
        * Added add and remove driver functionality
    * Added restaurants table
        * Added add and remove driver functionality
    * Made a tab pane to hold all the different tables
        * Orders in progress, orders completed, drivers, and restaurants all got their own tabs
    * Added time functionality to both order tables
    * Added notification bars at the bottom of every table except the orders completed table
* Part 3
    * Formatted all the tables
    * Made sure all functionality works
    * Connected restaurants and drivers table lists to the drop downs when adding orders

## Acknowledgments
* Thank you to Elodie Fourquet for supporting me and helping me solve issues with TableViews throughout this project
* Thank you to DomPizzie for the README format