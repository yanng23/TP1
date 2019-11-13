package TP2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

import javax.swing.JComponent;

import TP2.PaintData.Tool;


@SuppressWarnings("serial")
public class MarkingMenu extends JComponent implements MouseMotionListener{
	public enum State {
		IDLE,
		SelectingTool,
		SelectingColor
	}
	
	
	MarkingMenuUI m_ui;
	MarkingMenuData m_data;
	Paint m_paintController;
	PaintUI m_paintUI;
	
	Dimension m_dimension;
	State m_state;
	
	public MarkingMenu(Paint paintController){
		m_paintController = paintController ;
		//TODO change this to fit the actual size of the window
		m_dimension = new Dimension(800,600);
		m_data = new MarkingMenuData(75);
		m_ui = new MarkingMenuUI(this, m_data);
		
		m_state = State.IDLE;
		
		this.setUI(m_ui);
	}
	
	public void setPaintUI(PaintUI paintUI) {
		m_paintUI = paintUI;
	}
	
	/* These functions are required for the Jcomponent to be drawn */
    @Override
    public Dimension getPreferredSize() {
        return m_dimension ;
    }

    @Override
    public Dimension getMaximumSize() {
        return m_dimension ;
    }

    @Override
    public Dimension getMinimumSize() {
        return m_dimension ;
    }
    /*------------------------------------------------------------*/
	
	public void setTools(Tool[] t) {
		m_data.setTools(t);
	}
	
	public Tool[] getTools() {
		return m_data.getTools();
	}
	
	public Color[] getColors() {
		return m_data.getColors();
	}
	
	public void mousePressed() {
		m_data.isDrawn = true;
	}
	
	// Return the item selected
	public Tool mouseReleased() {
		m_data.isDrawn = false;
		return getToolSelected();
	}
	
	public State getState() {
		return m_state;
	}
	
	public Tool getToolSelected() {
		double teta = Math.toDegrees(Math.atan2(
				m_data.mouseY - m_data.y, //y
				m_data.mouseX - m_data.x)); //x
		//We want a result between 0 to 360 degrees counter clock 
		if(teta < 0)
			teta *= -1;
		else
			teta  = 360.d - teta;
		
		for(int i = 0; i < getTools().length; i++) {
			if(teta > (i * (360 / getTools().length)) && teta <= ((i + 1) * (360 / getTools().length))) { 
				return getTools()[i];
			}
		}
		return null;
	}
	
	public Color getColorSelected() {
		double teta = Math.toDegrees(Math.atan2(
				m_data.mouseY - m_data.y, //y
				m_data.mouseX - m_data.x)); //x
		//We want a result between 0 to 360 degrees counter clock 
		if(teta < 0)
			teta *= -1;
		else
			teta  = 360.d - teta;
		
		for(int i = 0; i < getColors().length; i++) {
			if(teta > (i * (360 / getColors().length)) && teta <= ((i + 1) * (360 / getColors().length))) { 
				return getColors()[i];
			}
		}
		return null;
	}
	
	public void setOrigin(Point origin) {
		m_data.x = origin.x;
		m_data.y = origin.y;
	}
	
	public void setMousePosition(Point mouse) {
		m_data.mouseX = mouse.x;
		m_data.mouseY = mouse.y;
	}
	
	/* MouseMotionListener implementation*/
	@Override
	public void mouseDragged(MouseEvent e) {
		m_data.mouseX = e.getPoint().x;
		m_data.mouseY = e.getPoint().y;
		
		Tool t = getToolSelected();
		if(t != null)
			m_data.setCurrentlySelectedTools(t);
		
		if(Point2D.distance(m_data.mouseX, m_data.mouseY, m_data.x, m_data.y) > m_data.rayon) {
			if(m_state == State.SelectingTool) {
				m_paintController.toolSelected(getToolSelected());
				//Without this line the red line is starting at 0,0 before a mouse motion
				setMousePosition(e.getPoint());
				setOrigin(e.getPoint());
				m_state = State.SelectingColor;
			}
			else if(m_state == State.SelectingColor) {
				m_paintController.switchColor(getColorSelected());
				m_state = State.IDLE;
			}
		}
		m_paintUI.rePaint();		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void setColors(Color[] colors) {
		m_data.setColors(colors);
	}
	
	public void setIDLE() {
		m_state = State.IDLE;
	}
	
	public void setIsSelectingTool() {
		m_state = State.SelectingTool;
	}
}
