package turingMachine.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A state of a Turing machine.
 * @author Arni
 *
 */
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

	/**
	 * @return Whether the state is a end state/acceptable state of the Turing machine.
	 */
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

	public List<State> getFollowingStates() {
		List<State> followingStates = new ArrayList<>();
		for (Computation comp : computations.values()) {
			followingStates.add(comp.getTargetState());
		}
		return followingStates;
	}
	
	/**
	 * Gets the computation that leads to the target state.
	 * @param target
	 * @return computation or null if no computation to  the target state is available.
	 */
	public Computation getComputation(State target)
	{
		for (Computation comp : computations.values()) {
			if(comp.getTargetState().equals(target))
			{
				return comp;
			}
		}
		
		return null;
	}
	
}
