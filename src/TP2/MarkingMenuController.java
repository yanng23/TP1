package TP2;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

import javax.swing.JComponent;

import TP2.PaintController.State;
import TP2.PaintData.Tool;


@SuppressWarnings("serial")
public class MarkingMenuController extends JComponent implements MouseMotionListener{
	PaintUI m_paintUI;
	
	MarkingMenuUI m_ui;
	MarkingMenuData m_data;
	Tool[] m_tools;
	PaintController m_paintController;
	
	Dimension m_dimension ;

	public MarkingMenuController(PaintController paintController){
		m_paintController = paintController ;
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
	
	public void mousePressed() {
		m_data.isDrawn = true;
	}
	
	// Return the item selected
	public Tool mouseReleased() {
		m_data.isDrawn = false;
		return getToolSelected();
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
		
		for(int i = 0; i < m_tools.length; i++) {
			if(teta > (i * (360 / m_tools.length)) && teta < ((i + 1) * (360 / m_tools.length))) { 
				return m_tools[i];
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
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if(m_paintController.getState() == State.MarkingMenu) {
			m_data.mouseX = e.getPoint().x;
			m_data.mouseY = e.getPoint().y;
			
			if(Point2D.distance(m_data.mouseX, m_data.mouseY, m_data.x, m_data.y) > m_data.rayon) {
				System.out.println("selected");
				m_data.isDrawn = false;
				m_paintController.toolSelected(getToolSelected());
			}
			
			m_paintUI.rePaint();			
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
