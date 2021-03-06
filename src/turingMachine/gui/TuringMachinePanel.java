package turingMachine.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import turingMachine.logic.Tape;
import turingMachine.logic.Tape.StepResult;
import turingMachine.logic.TuringMachine;

/**
 * Panel showing the read/write head of the machine with its current state and the tape beneath it
 * @author Arni
 *
 */
public class TuringMachinePanel extends JPanel implements Observer{

	private static final long serialVersionUID = 1L;

	private static final int CHARACTERS_AROUND_HEAD_ON_TAPE = 15;

	private Tape tape; 
	private String blankSymbol;
	private StepResult progression;
	
	/**
	 * Size of the reading head of the Turing machine.
	 */
	private static int TM_READER_WIDTH=100, TM_READER_HEIGHT=40;
	
	public TuringMachinePanel(){
		this.setDoubleBuffered(true);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof Tape){
			this.tape = (Tape) arg;
		}else if(arg instanceof TuringMachine)
		{
			blankSymbol = ((TuringMachine)arg).getBlankSymbol();
		}if(arg instanceof StepResult)
		{
			progression=(StepResult) arg;
		}
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if(tape!=null)
		{
			drawConnectorLine(g);
			drawReadWriteHead(g);
			drawTape(g);
		}
	}

	/**
	 * Some visual aid to see which field on the tape is currently under access of the read/write head
	 * @param g
	 */
	private void drawConnectorLine(Graphics g) {
		g.drawLine(this.getWidth()/2, 0, this.getWidth()/2, this.getHeight()/2);
	}

	
	/**
	 * Draws a tape with the word on it. The fields that aren't filled with the word hold the blank symbol and are grayed for visual simplicity.
	 * @param g
	 */
	private void drawTape(Graphics g) {
		List<String> tapeSymbols = tape.getSymbolAroundReadingHead(CHARACTERS_AROUND_HEAD_ON_TAPE);
		int tapeFieldWidth = this.getWidth()/tapeSymbols.size();
		int padding = (this.getWidth()-tapeFieldWidth*tapeSymbols.size())/2;
		for(int i = 0;i<tapeSymbols.size();i++)
		{
			Rectangle tapeSection = new Rectangle(padding+tapeFieldWidth*i,(this.getHeight()-TM_READER_HEIGHT)/2,tapeFieldWidth,TM_READER_HEIGHT);
			if(tapeSymbols.get(i).equals(blankSymbol)){
				g.setColor(Color.LIGHT_GRAY);
			}else{
				g.setColor(Color.white);
			}
			drawRectangle(g, tapeSection);
			g.drawString(tapeSymbols.get(i), tapeSection.x+tapeSection.width/2, tapeSection.y+tapeSection.height/2);
		}
	}

	private void drawRectangle(Graphics g, Rectangle rectangle) {
		g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		g.setColor(Color.black);
		g.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
	}

	/**
	 * Draws the read/write header in a nice light blue color
	 * @param g
	 */
	private void drawReadWriteHead(Graphics g) {
		Rectangle readHead = new Rectangle(this.getWidth()/2-TM_READER_WIDTH/2,0,TM_READER_WIDTH,TM_READER_HEIGHT);
		g.setColor(progression.getColor());
		drawRectangle(g, readHead);
		g.drawString(tape.getCurrentState().getIdentifier(),readHead.x+5, readHead.y+readHead.height/2);
	}
	
	
	
}
