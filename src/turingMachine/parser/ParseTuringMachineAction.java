package turingMachine.parser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import turingMachine.TuringAutomata;
import turingMachine.logic.TuringMachine;

public class ParseTuringMachineAction implements ActionListener {

	private TuringAutomata turingAutomata;

	public ParseTuringMachineAction(TuringAutomata turingAutomata) {
		this.turingAutomata = turingAutomata;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setFileFilter(new FileNameExtensionFilter("Turing Machine files","turing"));
		if(fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION)
		{
				try {
					TuringMachine tm = Parser.parseTuringMachineFile(fileChooser.getSelectedFile());
					turingAutomata.setTuringMachine(tm);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				} catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Parsing error!" + e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
		}
	}

}
