package turingMachine.logic;

import java.util.HashMap;

import org.omg.PortableInterceptor.SUCCESSFUL;


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
	
	
	public StepResult runStep(int numberOfSteps){
		StepResult result=StepResult.INPROGRESS;
		for(int i = 0 ; i<numberOfSteps;i++)
		{
			Computation comp = currentState.compute(getInput());
			result = computeResult(comp);
			if(result!=StepResult.INPROGRESS)
			{
				break;
			}
			computeStep(comp);
			
		}
		return result;
	}


	private StepResult computeResult(Computation comp) {
		if(comp==null)
		{
			if(currentState.isEnd())
			{
				return StepResult.SUCCESS;
			}else
			{
				return StepResult.FAILURE;
			}
		}
		return StepResult.INPROGRESS;
	}


	private void computeStep(Computation comp) {
		word.put(position, comp.getOutput());
		position = comp.getMoveDirection().move(position);
		currentState = comp.getTargetState();
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


	public StepResult runAll(){
		StepResult result = StepResult.INPROGRESS;
		while(result==StepResult.INPROGRESS)
		{
			result=runStep(1);
		}
		
		return result;
	}
	
	public State getCurrentState(){
		return currentState;
	}
	
	public int getPosition(){
		return position;
	}
	
	public enum StepResult{
		INPROGRESS, FAILURE, SUCCESS
	}
}
