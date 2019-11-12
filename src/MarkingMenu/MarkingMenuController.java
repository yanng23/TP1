package MarkingMenu;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;

import Paint.PaintController.Tool;

@SuppressWarnings("serial")
public class MarkingMenuController extends JComponent implements MouseMotionListener{
	
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
	
	public void setOrigin(int originX, int originY) {
		m_data.x = originX;
		m_data.y = originY;
	}
	

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Aller ca drag");
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
