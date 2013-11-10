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
import turingMachine.parser.ParseTuringMachineAction;

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
		createTabbedPane();
		frame.setVisible(true);
	}
	
	private void createTabbedPane() {
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 784, 341);
		frame.getContentPane().add(tabbedPane);
		
		createTuringMachinePanel(tabbedPane);
		createKNFPanel(tabbedPane);
	}

	private void createKNFPanel(JTabbedPane tabbedPane) {
		KNFPanel knfPanel = new KNFPanel();
		addObserver(knfPanel);
		tabbedPane.addTab("KNF", null, knfPanel, null);
	}

	private void createTuringMachinePanel(JTabbedPane tabbedPane) {
		TuringMachinePanel tmPanel = new TuringMachinePanel();
		addObserver(tmPanel);
		tabbedPane.addTab("Turing-Machine", null, tmPanel, null);
	}

	private void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menu = new JMenu("Operate");
		menu.setMnemonic(KeyEvent.VK_O);
		menu.getAccessibleContext().setAccessibleDescription("Operation functions for the Turing machine");
		
		JMenuItem parseMenuItem = new JMenuItem("Parse",KeyEvent.VK_P);
		parseMenuItem.getAccessibleContext().setAccessibleDescription("Let's you select a folder with the turing machine settings to be parsed");
		parseMenuItem.addActionListener(new ParseTuringMachineAction(this));
		
		menu.add(parseMenuItem);
		menuBar.add(menu);
		frame.setJMenuBar(menuBar);
		
		JMenu mnStep = new JMenu("Step");
		menuBar.add(mnStep);
		
		JMenuItem mntmForward = new JMenuItem("Forward");
		mntmForward.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
		mnStep.add(mntmForward);
		
		JMenuItem mntmStepToEnd = new JMenuItem("Run All");
		mntmStepToEnd.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.CTRL_MASK));
		mnStep.add(mntmStepToEnd);
	}
	
	public void setTuringMachine(TuringMachine turingMachine){
		this.turingMachine = turingMachine;
		setChanged();
	}
	
	public void setWord(String word){
		this.word = word;
	}

	public static void main(String[] args) {
		new TuringAutomata();
	}
}
