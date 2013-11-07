package turingMachine;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import turingMachine.parser.ParseAction;

/**
 * @author Arni
 *
 */
public class TuringAutomata extends JFrame{
	
	public TuringAutomata() {
		super("Turing Automata");
		this.setSize(800, 400);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		createMenuBar();
		this.setVisible(true);
	}
	
	private void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menu = new JMenu("Operate");
		menu.setMnemonic(KeyEvent.VK_O);
		menu.getAccessibleContext().setAccessibleDescription("Operation functions for the Turing machine");
		
		JMenuItem parseMenuItem = new JMenuItem("Parse",KeyEvent.VK_P);
		parseMenuItem.getAccessibleContext().setAccessibleDescription("Let's you select a foleder with the turing machine settins to be parsed");
		parseMenuItem.addActionListener(new ParseAction());
		
		menu.add(parseMenuItem);
		menuBar.add(menu);
		this.setJMenuBar(menuBar);
	}

	public static void main(String[] args) {
		new TuringAutomata();
	}
}
