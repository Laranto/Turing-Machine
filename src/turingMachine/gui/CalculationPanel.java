package turingMachine.gui;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import turingMachine.gui.utility.CalculationModel;
import turingMachine.logic.Computation;
import turingMachine.logic.Tape;

/**
 * Shows all the calculation steps
 * @author Michael
 *
 */
public class CalculationPanel extends JPanel implements Observer{

	private static final long serialVersionUID = 1L;
	private JList<Computation> list = new JList<Computation>();

	public CalculationPanel() {
		super();
		setBounds(0, 0, 784, 290);
		setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 784, 290);
		add(scrollPane);
		scrollPane.setViewportView(list);
	}

	@SuppressWarnings("unchecked")
	private void updateList(List<Computation> computations) {
		list.setModel(new CalculationModel(computations));
		list.repaint();
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof Tape){
			updateList(((Tape)arg).getComputationHistory());
		}
	}
}
