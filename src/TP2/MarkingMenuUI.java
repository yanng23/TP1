package TP2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

import TP2.PaintData.Tool;

public class MarkingMenuUI extends ComponentUI{
	MarkingMenuController m_controller;
	MarkingMenuData m_data;
	
	public MarkingMenuUI(MarkingMenuController controller, MarkingMenuData data) {
		m_controller = controller;
		m_data = data;
	}
	
	@Override
	public void paint(Graphics g, JComponent c) {
		if(m_data.isDrawn) {
			super.paint(g,c);
			
			g.setColor(Color.BLACK);
			g.fillOval(m_data.x - m_data.rayon, m_data.y - m_data.rayon, 2 * m_data.rayon, 2 * m_data.rayon);
			
			g.setColor(Color.gray);
			g.fillOval(m_data.x - m_data.rayon + 4, m_data.y - m_data.rayon + 4, (m_data.rayon * 2) - 8, (m_data.rayon * 2)- 8);
			
			g.setColor(Color.BLACK);
			
			Tool[] tools = m_controller.getTools();
			for(int i = 0; i < tools.length ; i++) {
				g.drawLine(m_data.x, 
						m_data.y,
						m_data.x + (int)(m_data.rayon * cos(Math.toRadians(360 / tools.length) * i)),
						m_data.y + (int)(m_data.rayon * sin(Math.toRadians(360 / tools.length) * i)));
				
				g.drawString(tools[i].toString(), 
						m_data.x + (int)(m_data.rayon * cos((2 * Math.PI /tools.length) * i)), 
						m_data.y + (int)(m_data.rayon * sin((2 * Math.PI /tools.length) * i)));
			}
			
			
			//Draw the line
			g.setColor(Color.red);
			g.drawLine(m_data.x,
					m_data.y,
					m_data.mouseX,
					m_data.mouseY);
		}
	}
}
