package turingMachine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Observable;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import turingMachine.logic.Tape;
import turingMachine.logic.TuringMachine;
import turingMachine.parser.ParseTuringMachineAction;

/**
 * @author Arni
 *
 */
public class TuringAutomata extends Observable{
	
	private JFrame frame;
	private TuringMachine turingMachine;
	private JMenuItem setWordMenuItem;
	private Tape tape;

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
		
		JMenuItem parseMenuItem = new JMenuItem("Parse...",KeyEvent.VK_P);
		parseMenuItem.getAccessibleContext().setAccessibleDescription("Lets you select a file with the turing machine settings to be parsed");
		parseMenuItem.addActionListener(new ParseTuringMachineAction(this));
		menu.add(parseMenuItem);
		
		setWordMenuItem = new JMenuItem("Set Word...",KeyEvent.VK_P);
		setWordMenuItem.setEnabled(false);
		setWordMenuItem.getAccessibleContext().setAccessibleDescription("requires file parsing");
		setWordMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setWord(JOptionPane.showInputDialog(frame,"Add a word:"));
			}
		});
		
		menu.add(setWordMenuItem);
		menuBar.add(menu);
		frame.setJMenuBar(menuBar);
		
		JMenu mnStep = new JMenu("Step");
		menuBar.add(mnStep);
		
		JMenuItem mntmForward = new JMenuItem("Run One");
		mntmForward.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
		mntmForward.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setChanged();
				notifyObservers(tape.runStep(1));
			}
		});
		mnStep.add(mntmForward);
		
		JMenuItem mntmStepXSteps = new JMenuItem("Run...");
		mntmStepXSteps.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setChanged();
				notifyObservers(tape.runStep(Integer.parseInt(JOptionPane.showInputDialog(frame,"Number of Steps:"))));
			}
		});
		mntmStepXSteps.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.CTRL_MASK));
		mnStep.add(mntmStepXSteps);
		
		JMenuItem mntmAllSteps = new JMenuItem("Run All");
		mntmAllSteps.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setChanged();
				notifyObservers(tape.runAll());
			}
		});
		mntmAllSteps.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.CTRL_MASK));
		mnStep.add(mntmAllSteps);
	}
	
	public void setTuringMachine(TuringMachine turingMachine){
		this.turingMachine = turingMachine;
		setWordMenuItem.getAccessibleContext().setAccessibleDescription("Lets you set a word");
		setWordMenuItem.setEnabled(true);
		setChanged();
		notifyObservers(turingMachine);
	}
	
	public void setWord(String word){
		tape = new Tape(turingMachine, word);
		setChanged();
		notifyObservers(tape);
	}

	public static void main(String[] args) {
		new TuringAutomata();
	}
}
