package turingMachine;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import turingMachine.logic.Tape;
import turingMachine.logic.Tape.StepResult;

public class TuringMachinePanel extends JPanel implements Observer{

	public TuringMachinePanel(){
		
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof Tape){
			System.out.println(((Tape)arg).getCurrentState().getIdentifier());
		}else if(arg instanceof StepResult){
			System.out.println(((StepResult)arg));
		}
	}
}
