package turingMachine.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import turingMachine.logic.Computation;

public class CalculationModel implements ListModel {

	private List<Computation> computations = new ArrayList<Computation>();
	
	public CalculationModel() {
		super();
	}	
	
	public CalculationModel(List<Computation> list) {
		super();
		this.computations = list;
	}

	@Override
	public void addListDataListener(ListDataListener arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getElementAt(int index) {
		return computations.get(index).toString();
	}

	@Override
	public int getSize() {
		return computations.size();
	}

	@Override
	public void removeListDataListener(ListDataListener arg0) {

	}

}
