package turingMachine.logic;

public class Computation {
	private String input;
	private String output;
	/**
	 * Which direction the Computation the tape will move.
	 */
	private MoveDirection moveDirection;
	/**
	 * the state that will be active after this computation
	 */
	private State targetState;
	
}
