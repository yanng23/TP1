package TP2;
import java.awt.Color;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import TP2.PaintData.Tool;
import javafx.util.Pair;

@SuppressWarnings("serial")
public class PaintController implements MouseListener{
		
	public enum State{
			IDLE,
			MarkingMenu,
			Drawing
	}
	
	State m_state;
	PaintUI m_paintUI;
	PaintData m_data;
	
	MarkingMenuController m_markingMenu;
	
	public PaintController(String title) {
		m_state = State.IDLE;
		m_data = new PaintData(this);
		
		m_markingMenu = new MarkingMenuController(this);
		m_markingMenu.setTools(m_data.getTools());
	
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
			
			System.out.println("Changed state to MarkingMenu");			
			m_paintUI.repaint();
		}
	}

	public void toolSelected(Tool t) {
		m_state = State.IDLE;
		if(t != null) {
			System.out.println("using tool " + this.toString());
			m_paintUI.getPanel().removeMouseListener(m_data.getActiveTool());
			m_paintUI.getPanel().removeMouseMotionListener(m_data.getActiveTool());
			
			m_data.changeActiveTool(t);
			
			m_paintUI.getPanel().addMouseListener(t);
			m_paintUI.getPanel().addMouseMotionListener(t);			
		}
		
		m_paintUI.rePaint();
	}
	
	public State getState() {
		return m_state;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		/*if(m_state == State.MarkingMenu) {
			m_state = State.IDLE;
			
			Tool t = m_markingMenu.mouseReleased();
			if(t != null)
				switchTool(t);
			m_paintUI.rePaint();
		}*/
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
		System.out.println("using tool " + this.toString());
		m_paintUI.getPanel().removeMouseListener(m_data.getActiveTool());
		m_paintUI.getPanel().removeMouseMotionListener(m_data.getActiveTool());
		m_data.changeActiveTool(tool);
		m_paintUI.getPanel().addMouseListener(tool);
		m_paintUI.getPanel().addMouseMotionListener(tool);
	}
}