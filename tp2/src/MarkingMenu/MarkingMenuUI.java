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
		System.out.print("paint");
		super.paint(g,c);
		
		int posX = 200;
		int posY = 350;
		int rayon = 100;
		//int nbr_elem = 5;
		
		//if(m_controller.isDrawn) {
			g.setColor(Color.BLACK);
			g.fillOval(posX - rayon, posY - rayon, 2 * rayon, 2 * rayon);

			g.setColor(Color.gray);
			g.fillOval(posX - rayon + 4, posY - rayon + 4, (rayon * 2) - 8, (rayon * 2)- 8);
			
			g.setColor(Color.BLACK);
			
			Tool[] tools = m_controller.getTools();
			//TODO ne fonctionne qu'avec certaines valeurs
			int n = 4 * 2;
			for(int i = 0; i < 4 ; i++) {
				g.drawLine(posX, 
						posY,
						posX + (int)(rayon * cos(Math.toRadians(360 / 4) * i)),
						posY + (int)(rayon * sin(Math.toRadians(360 / 4) * i)));

				
				g.drawString(tools[i].toString(), 
					posX + (int)(rayon * cos(Math.toRadians(45) * i+1)), 
					posY + (int)(rayon * sin(Math.toRadians(45) * i+1)));
			}
		//}
	}
}
