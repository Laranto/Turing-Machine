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



	private TuringMachine turingMachine;



	private String word;
	
	public Tape(TuringMachine turingMachine, String word){
		this.turingMachine = turingMachine;
		this.word = word;
		
	}
	
	
	public void runStep(int numberOfSteps){
		
	}
	
	public void runAll(){
		
	}
	
	public State getCurrentState(){
		return currentState;
	}
	
	public int getPosition(){
		return position;
	}
}
