package turingMachine.logic;

/**
 * @author Arni
 *
 */
public enum MoveDirection {
	L(-1),R(1),S(0);

	
	private int direction;

	private MoveDirection(int direction) {
		this.direction = direction;
	}
	
	public int move(int position) {
		return position+direction;
	}
}
