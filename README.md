# Java Maze Solver Robot

This project is a Java application that simulates a robot solving a pre-defined maze. It was developed as an assignment for an introductory computer science course (CS1A) and demonstrates object-oriented programming principles using the `becker.robots` library.

The robot begins at the maze entrance and autonomously navigates to the exit, dropping markers ("things") along its path. Upon completion, it reports statistics about its journey.

![Maze Solver Screenshot](https://i.imgur.com/a/gY4hYtT.png)
*(Suggestion: You can replace this with a screenshot or GIF of your own program running!)*

## Features

-   **Object-Oriented Design**: The `MazeBot` class inherits from the `becker.robots.RobotSE` class, overriding methods like `move()` and `putThing()` to add custom functionality.
-   **Algorithmic Navigation**: The robot uses a "right-hand rule" (or wall-following) algorithm to navigate the maze, ensuring it can solve any simple, connected maze.
-   **State Tracking**: The robot keeps track of its own state, including the total number of moves and the number of moves made in each cardinal direction (North, South, East, West).
-   **Custom Environment**: The `Maze` class procedurally generates the walls and spirals of the maze within the Becker `City` environment.

## How to Compile and Run

This project requires the `becker.jar` library file to be included in the classpath for compilation and execution.

### Prerequisites

1.  Java Development Kit (JDK) 8 or higher.
2.  The `becker.jar` file. (You can usually find this with your course materials).

### Instructions

1.  Place your `Maze.java` file and the `becker.jar` file in the same directory.

2.  Open a terminal or command prompt and navigate to that directory.

3.  **Compile the code:**
    * On macOS/Linux:
        ```bash
        javac -cp ".:becker.jar" Maze.java
        ```
    * On Windows:
        ```bash
        javac -cp ".;becker.jar" Maze.java
        ```

4.  **Run the application:**
    * On macOS/Linux:
        ```bash
        java -cp ".:becker.jar" Maze
        ```
    * On Windows:
        ```bash
        java -cp ".;becker.jar" Maze
        ```

This will launch the Becker Robots GUI, and you will see the `MazeBot` navigate and solve the maze.
