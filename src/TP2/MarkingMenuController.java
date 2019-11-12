package TP2;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;

import TP2.PaintController.Tool;

@SuppressWarnings("serial")
public class MarkingMenuController extends JComponent implements MouseMotionListener{
	PaintUI m_paintUI;
	
	MarkingMenuUI m_ui;
	MarkingMenuData m_data;
	Tool[] m_tools;
	
	Dimension m_dimension ;

	public MarkingMenuController(){
		//TODO change this to fit the actual size of the window
		m_dimension = new Dimension(800,600);
		m_data = new MarkingMenuData(50);
		m_ui = new MarkingMenuUI(this, m_data);
		
		
		this.setUI(m_ui);
	}
	
	public void setPaintUI(PaintUI paintUI) {
		m_paintUI = paintUI;
	}
	
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
    
	
	public void setTools(Tool[] tools) {
		m_tools = tools;
	}
	
	public Tool[] getTools() {
		return m_tools;
	}
	
	public void setActive(boolean active) {
		m_data.isDrawn = active;
	}
	
	public void setOrigin(Point origin) {
		m_data.x = origin.x;
		m_data.y = origin.y;
	}
	
	public void setMousePosition(Point mouse) {
		m_data.mouseX = mouse.x;
		m_data.mouseY = mouse.y;
	}
	

	@Override
	public void mouseDragged(MouseEvent e) {
		m_data.mouseX = e.getPoint().x;
		m_data.mouseY = e.getPoint().y;
		
		m_paintUI.rePaint();
		System.out.println(m_data.mouseX);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
