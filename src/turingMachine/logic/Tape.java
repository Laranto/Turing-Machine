package turingMachine.logic;

import java.util.LinkedList;

/**
 * @author Arni
 *
 */
public class Tape {
	
	//TODO Get something better than this...
	private LinkedList<String> tape;
	
	
	
	/**
	 * The position the reader is at
	 */
	private int position;
	/**
	 * The state the reader is in
	 */
	private State currentState;
}
