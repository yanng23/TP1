package MarkingMenu;

import java.awt.Dimension;

import javax.swing.JComponent;

import Paint.PaintController.Tool;

@SuppressWarnings("serial")
public class MarkingMenuController extends JComponent{
	public boolean isDrawn;
	MarkingMenuUI m_ui;
	Tool[] m_tools;
	
	Dimension m_dimension ;

	public MarkingMenuController(){
		isDrawn = false;
		m_dimension = new Dimension(100,100);
		
		
		m_ui = new MarkingMenuUI(this);
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
}
