package turingMachine.logic;

/**
 * A Computation of a Turing machine. Is created on compiling of a Turing machine.
 * 
 * @author Arni
 */
public class Computation {
	/**
	 * the state that the Computation comes from
	 */
	private State currentState;
	private String input;
	/**
	 * the state that will be active after this computation
	 */
	private State targetState;
	private String output;
	/**
	 * Which direction the Computation the tape will move.
	 */
	private MoveDirection moveDirection;
	
	/**
	 * @param input
	 * @param output
	 * @param moveDirection
	 * @param targetState
	 */
	public Computation(State currentState, String input, String output,
			MoveDirection moveDirection, State targetState) {
		super();
		this.currentState = currentState;
		this.input = input;
		this.output = output;
		this.moveDirection = moveDirection;
		this.targetState = targetState;
	}
	public State getCurrentState(){
		return currentState;
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
	@Override
	public String toString() {
		return "(" + currentState.getIdentifier() + ", " + input
				+ ", ) = (" + targetState.getIdentifier() + ", " + output
				+ ", " + moveDirection + ")";
	}
}
