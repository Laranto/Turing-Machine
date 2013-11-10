package turingMachine;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Observable;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import turingMachine.logic.TuringMachine;
import turingMachine.parser.ParseTuringMachine;

/**
 * @author Arni
 *
 */
public class TuringAutomata extends Observable{
	
	private JFrame frame;
	private TuringMachine turingMachine;
	private String word;

	public TuringAutomata() {
		frame = new JFrame("Turing Automata");
		frame.setSize(800, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createMenuBar();
		frame.setVisible(true);
	}
	
	private void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menu = new JMenu("Operate");
		menu.setMnemonic(KeyEvent.VK_O);
		menu.getAccessibleContext().setAccessibleDescription("Operation functions for the Turing machine");
		
		JMenuItem parseMenuItem = new JMenuItem("Parse",KeyEvent.VK_P);
		parseMenuItem.getAccessibleContext().setAccessibleDescription("Let's you select a folder with the turing machine settings to be parsed");
		parseMenuItem.addActionListener(new ParseTuringMachine(this));
		
		menu.add(parseMenuItem);
		menuBar.add(menu);
		frame.setJMenuBar(menuBar);
		
		JMenu mnStep = new JMenu("Step");
		menuBar.add(mnStep);
		
		JMenuItem mntmBack = new JMenuItem("Back");
		mntmBack.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK));
		mnStep.add(mntmBack);
		
		JMenuItem mntmForward = new JMenuItem("Forward");
		mntmForward.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
		mnStep.add(mntmForward);
		
		JMenuItem mntmStepToEnd = new JMenuItem("To End");
		mntmStepToEnd.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.CTRL_MASK));
		mnStep.add(mntmStepToEnd);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 784, 341);
		frame.getContentPane().add(tabbedPane);
		
		TuringMachinePanel tmPanel = new TuringMachinePanel();
		tabbedPane.addTab("Turing-Machine", null, tmPanel, null);
		
		KNFPanel knfPanel = new KNFPanel();
		tabbedPane.addTab("KNF", null, knfPanel, null);
	}
	
	public void setTuringMachine(TuringMachine turingMachine){
		this.turingMachine = turingMachine;
	}
	
	public void setWord(String word){
		this.word = word;
	}

	public static void main(String[] args) {
		new TuringAutomata();
	}
}
