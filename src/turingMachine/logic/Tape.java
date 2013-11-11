package turingMachine.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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

	/**
	 * The Turing Machine the Tape runs on
	 */
	private TuringMachine turingMachine;

	/**
	 * Word written on the tape
	 */
	private HashMap<Integer, String> word;
	
	/**
	 * Keeps track of the Computations that have been run on the tape. Only information is relayed via this
	 */
	private ArrayList<Computation> computationHistory; 

	/**
	 * 
	 * @param turingMachine
	 *            Turing machine the Tape runs on
	 * @param initialWord
	 *            Word that stands initially on the tape
	 */
	public Tape(TuringMachine turingMachine, String initialWord) {
		this.turingMachine = turingMachine;
		this.word = new HashMap<>(initialWord.length());
		char[] wordParts = initialWord.toCharArray();
		for (int i = 0; i < wordParts.length; i++) {
			this.word.put(i, ((Character) wordParts[i]).toString());
		}
		position = 0;
		currentState = turingMachine.getStartState();
		computationHistory = new ArrayList<>();
	}

	/**
	 * Runs the given number of steps
	 * 
	 * @param numberOfSteps
	 * @return The state which the Turing machine is in. If <code>SUCCESS</code>
	 *         or <code>FAILURE</code> is returned the machine can't run
	 *         further.
	 */
	public StepResult runStep(int numberOfSteps) {
		StepResult result = StepResult.INPROGRESS;
		for (int i = 0; i < numberOfSteps; i++) {
			Computation comp = currentState.compute(getInputAt(position));
			result = computeResult(comp);
			if (result != StepResult.INPROGRESS) {
				break;
			}
			computeStep(comp);

		}
		return result;
	}

	/**
	 * Checks the state of the current computation.
	 * @param comp the computation that should be run this step. Can be null
	 * @return 
	 * <code>SUCCESS</code> if the computation is null and the <code>currentState</code> is an end state.<br>
	 * <code>FAILURE</code> if the computation is null and the <code>currentState</code> is <b>not</b> an end state.<br>
	 * <code>INPROGRESS</code> else
	 */
	private StepResult computeResult(Computation comp) {
		if (comp == null) {
			if (currentState.isEnd()) {
				return StepResult.SUCCESS;
			} else {
				return StepResult.FAILURE;
			}
		}
		computationHistory.add(comp);
		return StepResult.INPROGRESS;
	}

	private void computeStep(Computation comp) {
		word.put(position, comp.getOutput());
		position = comp.getMoveDirection().move(position);
		currentState = comp.getTargetState();
	}

	/**
	 * Get the input symbol the next computation. If not in the word anymore, a blank symbol will be used
	 * @return the input symbol as string
	 */
	private String getInputAt(int symbolPosition) {
		String input = word.get(symbolPosition);
		if(input == null)
		{
			input = turingMachine.getBlankSymbol();
		}
		return input;
	}


	/**
	 * Runs the tape until it ends in <code>SUCCESS</code> or <code>FAILURE</code> 
	 * @return <code>SUCCESS</code> or <code>FAILURE</code>
	 */
	public StepResult runAll() {
		StepResult result = StepResult.INPROGRESS;
		while (result == StepResult.INPROGRESS) {
			result = runStep(1);
		}

		return result;
	}

	/**
	 * @return state of the reading head
	 */
	public State getCurrentState() {
		return currentState;
	}

	/**
	 * @return position of the reading head on the tape
	 */
	public int getPosition() {
		return position;
	}
	
	/**
	 * @param symbolCount  
	 * @return the symbols (symbolCount*2 +1 in total) around the reading head
	 */
	public List<String> getSymbolAroundReadingHead(int symbolCount)
	{
		LinkedList<String> symbols = new LinkedList<String>();
		symbols.add(getInputAt(position));
		for(int i = 1;i<symbolCount;i++)
		{
			symbols.addFirst(getInputAt(position-i));
			symbols.addLast(getInputAt(position+i));
		}
		return symbols;
	}
	
	

	/**
	 * @return a list of all computations that have run so far
	 */
	public List<Computation> getComputationHistory() {
		return Collections.unmodifiableList(computationHistory);
	}



	public enum StepResult {
		INPROGRESS, FAILURE, SUCCESS
	}
}
