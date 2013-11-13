package turingMachine.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TuringMachine {

	private String machineName;
	private String statesName;
	private String alphabetName;
	private String tmAlphabetName;
	private String computationsName;
	private String startStateName;
	private String blankSymbol;
	private String endStatesName;
	private List<String> stateDefinitions;
	private List<String> computionDefinitions;
	private List<String> endingStateDefinitions;
	
	private Map<String,State> states;
	
	public TuringMachine(String machineName, String states, String alphabet,
			String tmAlphabet, String computations, String startState,
			String blankSymbol, String endStates) {
		this.machineName = machineName.trim();;
		this.statesName = states.trim();
		this.alphabetName = alphabet.trim();
		this.tmAlphabetName = tmAlphabet.trim();
		this.computationsName = computations.trim();
		this.startStateName = startState.trim();
		this.blankSymbol = blankSymbol;
		this.endStatesName = endStates.trim();
		stateDefinitions = new ArrayList<>();
		computionDefinitions = new ArrayList<>();
		endingStateDefinitions = new ArrayList<>();
	}

	public String getMachineName() {
		return machineName;
	}

	public String getStatesName() {
		return statesName;
	}

	public String getAlphabetName() {
		return alphabetName;
	}

	public String getTmAlphabetName() {
		return tmAlphabetName;
	}

	public String getComputationsName() {
		return computationsName;
	}

	public String getStartStateName() {
		return startStateName;
	}

	public String getBlankSymbol() {
		return blankSymbol;
	}

	public String getEndStatesName() {
		return endStatesName;
	}

	public List<String> getStateDefinitions() {
		return stateDefinitions;
	}

	public List<String> getComputionDefinitions() {
		return computionDefinitions;
	}

	public List<String> getEndingStateDefinitions() {
		return endingStateDefinitions;
	}
	
	public State getStartState()
	{
		return states.get(startStateName);
	}

	@Override
	public String toString() {
		return "TuringMachine [" + machineName + "={" + statesName + ", "
				+ alphabetName + ", " + tmAlphabetName + ", "
				+ computationsName + ", " + startStateName + ", " + blankSymbol
				+ ", " + endStatesName + "}]";
	}

	/**
	 * Builds the Turing Machine using the definitions lists. Make sure those lists are filled before invoking this method 
	 */
	public void compile() {
		createStates();
		setEndingStates();
		assignComputations();
	}

	private void assignComputations() {
		for(String computationDefinition : computionDefinitions)
		{
			//(q0,0)=(q0,0,R)
			String[] parts = computationDefinition.replaceAll("[\\(\\)]", "").split("[,=]");
			states.get(parts[0].trim()).putComputation(
					new Computation(
							states.get(parts[0].trim()),
							parts[1].trim(),
							parts[3].trim(),
							MoveDirection.valueOf(parts[4].trim()),
							states.get(parts[2].trim())
							)
					);
		}
	}

	private void setEndingStates() {
		for (String endState : endingStateDefinitions) {
			states.get(endState).setEnd(true);
		}
	}

	private void createStates() {
		states = new HashMap<String, State>();
		for (String  state : stateDefinitions) {
			states.put(state, new State(state));
		}
	}

}
