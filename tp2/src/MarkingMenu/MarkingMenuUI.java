package MarkingMenu;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

import Paint.PaintController.Tool;

public class MarkingMenuUI extends ComponentUI{
	MarkingMenuController m_controller;
	
	public MarkingMenuUI(MarkingMenuController controller) {
		m_controller = controller;
	}
	
	@Override
	public void paint(Graphics g, JComponent c) {
		if(m_controller.isDrawn) {
			super.paint(g,c);
			
			int posX = 0;
			int posY = 0;
			int rayon = 50;
			
			g.setColor(Color.BLACK);
			g.fillOval(posX - rayon, posY - rayon, 2 * rayon, 2 * rayon);
			
			g.setColor(Color.gray);
			g.fillOval(posX - rayon + 4, posY - rayon + 4, (rayon * 2) - 8, (rayon * 2)- 8);
			
			g.setColor(Color.BLACK);
			
			Tool[] tools = m_controller.getTools();
			//TODO ne fonctionne qu'avec certaines valeurs
			for(int i = 0; i < tools.length ; i++) {
				g.drawLine(posX, 
						posY,
						posX + (int)(rayon * cos(Math.toRadians(360 / tools.length) * i)),
						posY + (int)(rayon * sin(Math.toRadians(360 / tools.length) * i)));
				
				g.drawString(tools[i].toString(), 
						posX + (int)(rayon * cos((2 * Math.PI /tools.length) * i)), 
						posY + (int)(rayon * sin((2 * Math.PI /tools.length) * i)));
			}
			System.out.println("MarkingMenu printed: " + tools.length);
		}
	}
}
