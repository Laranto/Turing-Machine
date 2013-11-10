package turingMachine.logic;

import java.util.HashMap;


/**
 * @author Arni
 *
 */
public class Tape {
	
	/**
	 * The position the reader is at
	 */
	private int position;
	/**
	 * The state the reader is in
	 */
	private State currentState;



	private TuringMachine turingMachine;


	private HashMap<Integer,String> word;

	
	public Tape(TuringMachine turingMachine, String initialWord){
		this.turingMachine = turingMachine;
		this.word = new HashMap<>(initialWord.length());
		char[] wordParts = initialWord.toCharArray();
		for (int i = 0; i<wordParts.length;i++) {
			this.word.put(i,((Character)wordParts[i]).toString());
		}
		position = 0;
		currentState = turingMachine.getStartState();
	}
	
	
	public void runStep(int numberOfSteps){
		for(int i = 0 ; i<numberOfSteps;i++)
		{
			Computation comp = currentState.compute(getInput());
			word.put(position, comp.getOutput());
			position = comp.getMoveDirection().move(position);
			currentState = comp.getTargetState();
			
		}
	}
	
	private String getInput() {
		String input = new String();
		if(isPositionOutOfBounds())
		{
			input = turingMachine.getBlankSymbol();
		}else
		{
			input = word.get(position);
		}
		return input;
	}


	private boolean isPositionOutOfBounds() {
		return position<0||position>word.size()-1;
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
