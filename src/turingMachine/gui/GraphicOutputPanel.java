package turingMachine.gui;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import turingMachine.logic.Tape;
import turingMachine.logic.Tape.StepResult;

/**
 * Displaying orbs
 * 
 * @author Michael
 *
 */
public class GraphicOutputPanel extends JPanel implements Observer{

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof Tape){
			System.out.println(((Tape)arg).getCurrentState().getIdentifier());
		}else if(arg instanceof StepResult){
			System.out.println(((StepResult)arg));
		}
	}

}
