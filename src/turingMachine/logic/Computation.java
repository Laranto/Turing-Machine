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
	
	
	public Computation(String input, String output,
			MoveDirection moveDirection, State targetState) {
		super();
		this.input = input;
		this.output = output;
		this.moveDirection = moveDirection;
		this.targetState = targetState;
	}
	

	public String getInput() {
		return input;
	}
	public String getOutput() {
		return output;
	}
	public MoveDirection getMoveDirection() {
		return moveDirection;
	}
	public State getTargetState() {
		return targetState;
	}
	
}
