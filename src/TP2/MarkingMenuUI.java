package TP2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

import TP2.MarkingMenu.State;
import TP2.PaintData.Tool;

public class MarkingMenuUI extends ComponentUI{
	MarkingMenu m_controller;
	MarkingMenuData m_data;
	
	public MarkingMenuUI(MarkingMenu controller, MarkingMenuData data) {
		m_controller = controller;
		m_data = data;
	}
	
	@Override
	public void paint(Graphics g, JComponent c) {
		if(m_controller.getState() != State.IDLE) {
			super.paint(g,c);
			
			g.setColor(Color.BLACK);
			g.fillOval(m_data.x - m_data.rayon, m_data.y - m_data.rayon, 2 * m_data.rayon, 2 * m_data.rayon);
			
			if(m_controller.getState() == State.SelectingColor) {
				Color[] items = m_controller.getColors();
				
				int angle_slice = 360 / items.length;
				for(int i = 0; i < items.length; i++) {
					g.setColor(items[i]);
					g.fillArc(m_data.x - m_data.rayon + 4, m_data.y - m_data.rayon + 4, (m_data.rayon * 2) - 8, (m_data.rayon * 2)- 8, i * angle_slice, angle_slice);
				}
			}
			else {
				Tool[] items = m_controller.getTools();
				
				int angle_slice = 360 / items.length;
				for(int i = 0; i < items.length; i++) {
					if(items[i].equals(m_data.getCurrentlySelectedTools())) 
						g.setColor(Color.LIGHT_GRAY);
					else
						g.setColor(Color.gray);
					
					g.fillArc(m_data.x - m_data.rayon + 4, m_data.y - m_data.rayon + 4, (m_data.rayon * 2) - 8, (m_data.rayon * 2)- 8, i * angle_slice, angle_slice);

				}
				for(int i = 0; i < items.length; i++) {
					g.setColor(Color.black);
					g.drawLine(m_data.x, 
							m_data.y,
							m_data.x + (int)(m_data.rayon * cos(Math.toRadians(360 / items.length) * i)),
							m_data.y + (int)(m_data.rayon * sin(Math.toRadians(360 / items.length) * i)));
						
					g.drawString(items[i].toString(), 
						m_data.x + (int)(m_data.rayon * cos((2 * Math.PI /items.length) * i)), 
						m_data.y + (int)(m_data.rayon * sin((2 * Math.PI /items.length) * i)));
				}
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
