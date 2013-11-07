package turingMachine.logic;

import java.util.List;

public class State {

	/**
	 * 
	 */
	private String identifier;
	
	/**
	 * All possible calculations you can do from that state
	 */
	private List<Computation> possibleCalculations;
	/**
	 * Wether the state is a ending state or not 
	 */
	boolean isEnd;
}
