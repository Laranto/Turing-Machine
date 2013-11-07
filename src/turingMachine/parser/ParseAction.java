package turingMachine.parser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

public class ParseAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.showOpenDialog(fileChooser);
		
	}

}
