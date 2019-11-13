package TP2;

import java.awt.Color;

import TP2.PaintData.Tool;

public class MarkingMenuData {
	public int x;
	public int y;
	public int rayon;
	public int mouseX;
	public int mouseY;
	public boolean isDrawn;
	Tool[] m_tools;
	Color[] m_colors;
	
	public Tool m_selectedTool;
	public Color m_selectedColor;
	
	public MarkingMenuData(int rayon) {
		this.x = 0;
		this.y = 0;
		this.rayon = rayon;
		this.isDrawn = false;
	}
	
	public void setTools(Tool[] t) {
		m_tools = t;
	}
	
	public Tool[] getTools() {
		return m_tools;
	}
	
	public void setColors(Color[] c) {
		m_colors = c;
	}
	
	public Color[] getColors() {
		return m_colors;
	}
}
