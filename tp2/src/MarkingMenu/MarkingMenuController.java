package MarkingMenu;

import javax.swing.JComponent;

import Paint.PaintController.Tool;

@SuppressWarnings("serial")
public class MarkingMenuController extends JComponent{
	public boolean isDrawn;
	MarkingMenuUI m_ui;
	Tool[] m_tools;
	
	public MarkingMenuController(){
		isDrawn = false;
		m_ui = new MarkingMenuUI(this);
		
		this.setUI(m_ui);
	}

	public void setTools(Tool[] tools) {
		m_tools = tools;
	}
	
	public Tool[] getTools() {
		return m_tools;
	}
	
}
