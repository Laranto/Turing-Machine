package turingMachine.logic;

import java.util.HashMap;
import java.util.Map;

public class State {

	/**
	 * 
	 */
	private String identifier;
	
	/**
	 * All possible calculations you can do from that state. Key is the read value.
	 */
	private Map<String,Computation> computations;
	/**
	 * Whether the state is a ending state or not 
	 */
	private boolean isEnd;

	public State(String stateDefinition) {
		this.identifier = stateDefinition;
		isEnd=false;
		computations = new HashMap<String, Computation>();
		
	}

	public boolean isEnd() {
		return isEnd;
	}

	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}
	
	public void putComputation(Computation comp)
	{
		computations.put(comp.getInput(), comp);
	}
	
	public Computation compute(String input)
	{
		return computations.get(input);
	}

	public String getIdentifier() {
		return identifier;
	}
	
	
}
