package turingMachine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import turingMachine.gui.CalculationPanel;
import turingMachine.gui.GraphicOutputPanel;
import turingMachine.gui.TuringMachinePanel;
import turingMachine.logic.Tape;
import turingMachine.logic.Tape.StepResult;
import turingMachine.logic.TuringMachine;
import turingMachine.parser.ParseTuringMachineAction;

import javax.swing.JLabel;

import java.awt.Font;

/**
 * @author Arni
 *
 */
public class TuringAutomata extends Observable{
	
	private static final String FRAME_TITLE = "Turing Automata";
	private JFrame frame;
	private TuringMachine turingMachine;
	private JMenuItem setWordMenuItem;
	private JMenuItem countTapeMenuItem;
	private Tape tape;
	private JLabel lblSteps;
	private JMenu mnStep;
	private JLabel lblStepResultValue;

	public TuringAutomata() {
		frame = new JFrame(FRAME_TITLE);
		frame.setSize(800, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		createMenuBar();
		createTabbedPane();
		frame.setVisible(true);
	}
	
	private void createTabbedPane() {
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 784, 313);
		frame.getContentPane().add(tabbedPane);
		
		createTuringMachinePanel(tabbedPane);
		createGraphicOutputPanel(tabbedPane);
		createCalculationPanel(tabbedPane);
		
		lblSteps = new JLabel("0");
		lblSteps.setToolTipText("");
		lblSteps.setBounds(728, 325, 46, 14);
		frame.getContentPane().add(lblSteps);
		
		JLabel lblTotalSteps = new JLabel("Total Steps:");
		lblTotalSteps.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTotalSteps.setBounds(627, 324, 91, 14);
		frame.getContentPane().add(lblTotalSteps);
		
		JLabel lblStepResult = new JLabel("State: ");
		lblStepResult.setBounds(10, 316, 46, 14);
		frame.getContentPane().add(lblStepResult);
		
		lblStepResultValue = new JLabel("");
		lblStepResultValue.setBounds(66, 316, 165, 14);
		frame.getContentPane().add(lblStepResultValue);
	}

	private void createTuringMachinePanel(JTabbedPane tabbedPane) {
		TuringMachinePanel tmPanel = new TuringMachinePanel();
		addObserver(tmPanel);
		tabbedPane.addTab("Turing-Machine", null, tmPanel, null);
	}
	
	private void createGraphicOutputPanel(JTabbedPane tabbedPane) {
		GraphicOutputPanel graphicOutputPanel = new GraphicOutputPanel();
		addObserver(graphicOutputPanel);
		tabbedPane.addTab("Graphical Output", null, graphicOutputPanel, null);
	}
	
	private void createCalculationPanel(JTabbedPane tabbedPane) {
		CalculationPanel calculationPanel = new CalculationPanel();
		addObserver(calculationPanel);
		tabbedPane.addTab("Calculations", null, calculationPanel, null);
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
		
		countTapeMenuItem = new JMenuItem("Get Tape Length");
		countTapeMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, tape.getTapeContentCount(), "Number of symbols on the tape",JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		menu.add(setWordMenuItem);
		menu.add(countTapeMenuItem);
		menuBar.add(menu);
		frame.setJMenuBar(menuBar);
		
		mnStep = new JMenu("Step");
		mnStep.setEnabled(false);
		menuBar.add(mnStep);
		
		JMenuItem mntmForward = new JMenuItem("Run One");
		mntmForward.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
		mntmForward.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setChanged();
				updateStepResult(tape.runStep(1));
				notifyObservers(tape);
				updateAnzahlSchritte();
			}
		});
		mnStep.add(mntmForward);
		
		JMenuItem mntmStepXSteps = new JMenuItem("Run...");
		mntmStepXSteps.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int anzahlSteps = Integer.parseInt(JOptionPane.showInputDialog(frame,"Number of Steps:"));
				updateStepResult(tape.runStep(anzahlSteps));
				updateAnzahlSchritte();
			}
		});
		mntmStepXSteps.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
		mnStep.add(mntmStepXSteps);
		
		JMenuItem mntmAllSteps = new JMenuItem("Run All");
		mntmAllSteps.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateStepResult(tape.runAll());
				updateAnzahlSchritte();
			}
		});
		mntmAllSteps.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.CTRL_MASK));
		mnStep.add(mntmAllSteps);
	}
	
	protected void updateAnzahlSchritte() {
		lblSteps.setText(String.valueOf(tape.getComputationHistory().size()));
		lblSteps.repaint();
	}
	
	protected void updateStepResult(StepResult stepResult) {
		lblStepResultValue.setText(String.valueOf(stepResult));
		lblStepResultValue.repaint();
		setChanged();
		notifyObservers(stepResult);
		setChanged();
		notifyObservers(tape);
	}

	public void setTuringMachine(TuringMachine turingMachine){
		this.turingMachine = turingMachine;
		setWordMenuItem.getAccessibleContext().setAccessibleDescription("Lets you set a word");
		setWordMenuItem.setEnabled(true);
		mnStep.setEnabled(true);
		this.frame.setTitle(FRAME_TITLE + " - " + turingMachine.getMachineName());
		setChanged();
		notifyObservers(turingMachine);
	}
	
	public void setWord(String word){
		tape = new Tape(turingMachine, word);
		updateAnzahlSchritte();
		updateStepResult(StepResult.INITIAL);
		setChanged();
		notifyObservers(tape);
	}

	public static void main(String[] args) {
		new TuringAutomata();
	}
}
