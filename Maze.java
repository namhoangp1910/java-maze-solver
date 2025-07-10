import becker.robots.*;

/**
 * CS1A - Assignment 3 - "The Maze" <br>
 * Quarter: 2024 <br>
 * Represents a robot designed to solve a maze. It extends the RobotSE class
 * to add custom movement tracking and maze navigation logic.
 * * @author Phuong Nam Hoang
 */
class MazeBot extends RobotSE {
	// Instance variables to track the robot's movement statistics.
	int totalMoves = 0;
	int movesWest = 0;
	int movesEast = 0;
	int movesSouth = 0;
	int movesNorth = 0;

	public MazeBot(City theCity, int str, int ave, Direction dir, int numThings) {
		super(theCity, str, ave, dir, numThings);
	}

	/**
	 * Overrides the default move method to increment movement counters
	 * before executing the parent class's move action.
	 */
	public void move() {
		this.countDirection();
		if (this.frontIsClear()) {
			super.move();
			totalMoves++;
		}
	}

	/**
	 * Increments the appropriate directional move counter based on the
	 * robot's current heading.
	 */
	public void countDirection() {
		switch (this.getDirection()) {
		case NORTH:
			movesNorth++;
			break;
		case SOUTH:
			movesSouth++;
			break;
		case WEST:
			movesWest++;
			break;
		case EAST:
			movesEast++;
			break;
		default:
			break;
		}
	}

	/**
	 * Overrides the default putThing method to ensure a thing is only placed
	 * if the robot has one in its backpack and there isn't one already present.
	 */
	public void putThing() {
		if (!this.canPickThing()) {
			if (this.countThingsInBackpack() > 0) {
				super.putThing();
			}
		}
	}

	/**
	 * Prints a formatted summary of all moves made by the robot during its
	 * navigation.
	 */
	public void printEverything() {
		System.out.println("TOTAL NUMBER OF MOVES MADE: " + totalMoves);
		System.out.println("NUMBER OF TIMES MOVED EAST: " + movesEast);
		System.out.println("NUMBER OF TIMES MOVED SOUTH: " + movesSouth);
		System.out.println("NUMBER OF TIMES MOVED WEST: " + movesWest);
		System.out.println("NUMBER OF TIMES MOVED NORTH: " + movesNorth);
	}

	/**
	 * A private helper method to check if the robot has reached the
	 * maze's designated end coordinates.
	 * @return true if the robot is at (Street 10, Avenue 9).
	 */
	private boolean isAtEndSpot() {
		return getAvenue() == 9 && getStreet() == 10;
	}

	/**
	 * Contains the primary wall-following algorithm for the robot to solve the maze.
	 * The robot will continue to execute this logic until it reaches the end spot.
	 */
	public void navigateMaze() {
		while (!isAtEndSpot()) {
			if (this.frontIsClear()) {
				this.turnRight();

				if (this.frontIsClear()) {
					this.putThing();
					this.move();

				} else {
					this.putThing();
					this.turnLeft();
					this.move();
				}
			}

			else {
				this.putThing();
				this.turnRight();

				if (this.frontIsClear()) {
					this.putThing();
					this.move();

				}

				else {
					this.putThing();
					this.turnRight();
				}
			}
		}
	}
}

/**
 * This class is responsible for creating the maze environment and running the
 * simulation. It contains the main method to start the program.
 */
public class Maze extends Object {
	/**
	 * Constructs the static walls of the maze within the given city.
	 * @param theCity The city where the maze will be built.
	 */
	private static void makeMaze(City theCity) {
		for (int i = 1; i < 11; i++) {
			// north wall
			new Wall(theCity, 1, i, Direction.NORTH);

			// Second to north wall
			if (i <= 9)
				new Wall(theCity, 1, i, Direction.SOUTH);

			// Third to north wall
			if (i >= 4)
				new Wall(theCity, 4, i, Direction.SOUTH);

			// south wall
			if (i != 9) // (9, 10, SOUTH), is where the 'exit' is
				new Wall(theCity, 10, i, Direction.SOUTH);

			// west wall
			if (i != 1) // (1, 1, WEST) is where the 'entrance' is
				new Wall(theCity, i, 1, Direction.WEST);

			// second to most western wall
			if (i >= 3 && i < 6)
				new Wall(theCity, i, 6, Direction.WEST);

			// east wall
			new Wall(theCity, i, 10, Direction.EAST);
		}

		// Cul de Sac
		new Wall(theCity, 3, 10, Direction.WEST);
		new Wall(theCity, 3, 10, Direction.SOUTH);

		new Wall(theCity, 2, 8, Direction.WEST);
		new Wall(theCity, 2, 8, Direction.SOUTH);

		new Wall(theCity, 10, 8, Direction.NORTH);
		new Wall(theCity, 10, 9, Direction.EAST);
		new Wall(theCity, 10, 9, Direction.NORTH);
		makeSpiral(theCity, 8, 9, 3);
		new Wall(theCity, 8, 10, Direction.SOUTH);

		makeSpiral(theCity, 10, 5, 4);
	}

	/**
	 * A helper method to create spiral wall patterns within the maze.
	 * @param theCity The city where the spiral will be built.
	 * @param st The starting street.
	 * @param ave The starting avenue.
	 * @param size The initial size of the spiral.
	 */
	public static void makeSpiral(City theCity, int st, int ave, int size) {
		Direction facing = Direction.EAST;

		while (size > 0) {
			int spacesLeft = size;
			int aveChange = 0;
			int stChange = 0;
			switch (facing) {
			case EAST:
				stChange = -1;
				break;
			case NORTH:
				aveChange = -1;
				break;
			case WEST:
				stChange = 1;
				break;
			case SOUTH:
				aveChange = 1;
				break;
			}

			while (spacesLeft > 0) {
				new Wall(theCity, st, ave, facing);
				ave += aveChange;
				st += stChange;
				--spacesLeft;
			}
			// back up one space
			ave -= aveChange;
			st -= stChange;

			switch (facing) {
			case EAST:
				facing = Direction.NORTH;
				break;
			case NORTH:
				facing = Direction.WEST;
				size--;
				break;
			case WEST:
				facing = Direction.SOUTH;
				break;
			case SOUTH:
				facing = Direction.EAST;
				size--;
				break;
			}
		}
	}

	/**
	 * The main entry point for the application. It creates the city,
	 * instantiates the robot, and starts the maze navigation.
	 */
	public static void main(String[] args) {
		City calgary = new City(12, 12);
		MazeBot don = new MazeBot(calgary, 1, 1, Direction.EAST, 10);

		Maze.makeMaze(calgary);

		// Shows the number of things at each intersection to help debug.
		calgary.showThingCounts(true);

		don.navigateMaze();
		don.printEverything();
	}
}