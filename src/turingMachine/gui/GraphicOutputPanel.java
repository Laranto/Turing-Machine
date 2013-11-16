package turingMachine.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.JPanel;

import turingMachine.logic.State;
import turingMachine.logic.Tape;
import turingMachine.logic.TuringMachine;
import turingMachine.logic.Tape.StepResult;

/**
 * Displaying orbs
 * 
 * 
 */
public class GraphicOutputPanel extends JPanel implements Observer {

	private static final int NODE_DIAMETER = 50;

	private static final long serialVersionUID = 1L;

	private static final Color DEFAULT_COLOR = new Color(255, 255, 255);

	private Map<Integer, List<State>> stateTree;
	private Map<State, Point> statePositionList;

	private State startState;
	private Tape tape;
	private StepResult progression;

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof TuringMachine) {
			stateTree = new TreeMap<>();
			startState = ((TuringMachine) arg).getStartState();
			buildStateList(0, startState);
			buildPositionList();
		} else if (arg instanceof Tape) {
			this.tape = ((Tape) arg);
		} else if (arg instanceof StepResult) {
			progression = (StepResult) arg;
		}

		repaint();
	}

	private void buildPositionList() {
		statePositionList = new HashMap<>();
		for (Entry<Integer, List<State>> treeLevel : stateTree.entrySet()) {
			for (int i = 0; i < treeLevel.getValue().size(); i++) {
				statePositionList.put(treeLevel.getValue().get(i), new Point(
						calculateXPosition(treeLevel.getKey()),
						calculateYPosition(i, treeLevel.getValue().size())));
			}
		}
	}

	/**
	 * Creates the tree of states, which then can be drawn
	 * 
	 * @param level
	 * @param state
	 */
	private void buildStateList(int level, State state) {
		List<State> treeLevel = stateTree.get(level);
		if (treeLevel == null) {
			treeLevel = new ArrayList<>();
			stateTree.put(level, treeLevel);
		}

		treeLevel.add(state);

		for (State followingState : state.getFollowingStates()) {

			if (!isStateInTree(followingState)) {
				buildStateList(level + 1, followingState);
			}
		}

	}

	/**
	 * Checks whether the state is in the tree
	 * 
	 * @param state
	 * @return
	 */
	private boolean isStateInTree(State state) {
		for (List<State> treeLevel : stateTree.values()) {
			if (treeLevel.contains(state)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		for (Entry<State, Point> node : statePositionList.entrySet()) {

			for (State followingState : node.getKey().getFollowingStates()) {
				Point followingPosition = statePositionList.get(followingState);
				g.setColor(Color.BLACK);
				drawArrow(g, calculateNodeCenter(node.getValue().x),
						calculateNodeCenter(node.getValue().y),
						calculateNodeCenter(followingPosition.x),
						calculateNodeCenter(followingPosition.y), node.getKey()
								.getComputation(followingState)
								.toTranslationCode());

			}
		}
		
		for (Entry<State, Point> node : statePositionList.entrySet()) {

			if (node.getKey().equals(startState)) {
				drawArrow(g, 0, getHeight() / 2, calculateNodeCenter(node.getValue().x),
						calculateNodeCenter(node.getValue().y), "");
			}

			if (tape!=null&&node.getKey().equals(tape.getCurrentState())) {
				g.setColor(progression.getColor());
			} else {
				g.setColor(DEFAULT_COLOR);
			}
			drawNode(g, node.getValue().x, node.getValue().y, node.getKey()
					.getIdentifier(),node.getKey().isEnd());
		}
	}

	void drawArrow(Graphics g, int x1, int y1, int x2, int y2, String label) {

		if (x1 == x2 && y1 == y2) {
			int outsideX = x2 + NODE_DIAMETER / 2;
			int outsideY = y2 + NODE_DIAMETER;
			g.drawLine(x1, y1, outsideX, outsideY);
			x1 = outsideX;
			y1 = outsideY;
			g.drawString(label, (x1 + outsideX) / 2, (y1 + outsideY) / 2);
			outsideX -= NODE_DIAMETER;
			g.drawLine(x1, y1, outsideX, outsideY);
			x1 = outsideX;
		} else {
			g.drawString(label, (x1 * 2 + x2) / 3, (y1 * 2 + y2) / 3 - 4);

		}

		int ARR_SIZE = 4;

		Graphics2D g2 = (Graphics2D) g.create();

		double dx = x2 - x1, dy = y2 - y1;
		double angle = Math.atan2(dy, dx);
		int len = (int) Math.sqrt(dx * dx + dy * dy);
		len -= NODE_DIAMETER / 2;
		AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
		at.concatenate(AffineTransform.getRotateInstance(angle));
		g2.transform(at);

		// Draw horizontal arrow starting in (0, 0)
		g2.drawLine(0, 0, len, 0);
		g2.fillPolygon(new int[] { len, len - ARR_SIZE, len - ARR_SIZE, len },
				new int[] { 0, -ARR_SIZE, ARR_SIZE, 0 }, 4);
	}

	private int calculateNodeCenter(int pos) {
		return pos + NODE_DIAMETER / 2;
	}

	/**
	 * Draws a node and writes the identifier in it.
	 * 
	 * @param g
	 * @param x
	 * @param y
	 * @param identifier
	 * @param isEndNode 
	 */
	private void drawNode(Graphics g, int x, int y, String identifier, boolean isEndNode) {

		g.fillOval(x, y, NODE_DIAMETER, NODE_DIAMETER);
		g.setColor(Color.black);
		g.drawOval(x, y, NODE_DIAMETER, NODE_DIAMETER);
		if(isEndNode)
		{
			//Bit smaller circle, fucking magix numbers
			g.drawOval(x+2, y+2, NODE_DIAMETER-4, NODE_DIAMETER-4);			
		}
		g.drawString(identifier, x + 5, y + NODE_DIAMETER / 2 + 5);

	}

	private int calculateYPosition(int positionInLevel, int totalLevelSize) {
		return this.getHeight() / (totalLevelSize + 1) * (positionInLevel + 1)
				- NODE_DIAMETER / 2;
	}

	private int calculateXPosition(Integer level) {
		return (int) ((this.getWidth()) / stateTree.size() * (level + 0.5d))
				- NODE_DIAMETER / 2;
	}

}
