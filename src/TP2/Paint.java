package TP2;
import java.awt.Color;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.SwingUtilities;

import TP2.PaintData.Tool;
import javafx.util.Pair;

public class Paint implements MouseListener{
		
	public enum State{
			IDLE,
			MarkingMenu,
			MarkingMenuColor,
	}
	
	State m_state;
	PaintUI m_paintUI;
	PaintData m_data;
	
	MarkingMenu m_markingMenu;
	
	public Paint(String title) {
		m_state = State.IDLE;
		m_data = new PaintData(this);
		
		m_markingMenu = new MarkingMenu(this);
		m_markingMenu.setTools(m_data.getTools());
		m_markingMenu.setColors(m_data.getColors());
	
		m_paintUI = new PaintUI(title, m_markingMenu, this);
		m_markingMenu.setPaintUI(m_paintUI);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(m_state == State.IDLE && e.getButton() == MouseEvent.BUTTON3) {
			m_state = State.MarkingMenu;
			m_markingMenu.mousePressed();
			m_markingMenu.setOrigin(e.getPoint());
			//Without it the red line is starting at 0,0 before a mouse motion
			m_markingMenu.setMousePosition(e.getPoint());
			m_markingMenu.setIsSelectingTool();
			System.out.println("Changed state to MarkingMenu");			
			m_paintUI.repaint();
		}
	}

	public void toolSelected(Tool t) {
		if(t != null) {
			System.out.println("using tool " + this.toString());
			m_paintUI.getPanel().removeMouseListener(m_data.getActiveTool());
			m_paintUI.getPanel().removeMouseMotionListener(m_data.getActiveTool());
			
			m_data.changeTool(t);
			
			m_paintUI.getPanel().addMouseListener(t);
			m_paintUI.getPanel().addMouseMotionListener(t);			
		}
		
		m_paintUI.rePaint();
	}
	
	public void colorSelected() {
		m_state = State.IDLE;
	}
	
	public State getState() {
		return m_state;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if(SwingUtilities.isRightMouseButton(e)) {
			m_state = State.IDLE;
			m_markingMenu.setIDLE();
			m_paintUI.rePaint();
		}
	}

	public void repaint() {
		m_paintUI.rePaint();
	}
	
	public Tool[] getTools() {
		return m_data.getTools();
	}
	
	public Vector<Pair<Shape, Color>> getShape(){
		return m_data.getShape();
	}

	public void switchTool(Tool tool) {
		m_paintUI.getPanel().removeMouseListener(m_data.getActiveTool());
		m_paintUI.getPanel().removeMouseMotionListener(m_data.getActiveTool());
		
		m_data.changeTool(tool);
		
		m_paintUI.getPanel().addMouseListener(tool);
		m_paintUI.getPanel().addMouseMotionListener(tool);
	}

	public void switchColor(Color colorSelected) {
		m_data.changeColor(colorSelected);
		m_state = State.IDLE;
	}
}