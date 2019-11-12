package TP2;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import javafx.util.Pair;

@SuppressWarnings("serial")
class PaintUI extends JFrame {
	PaintController m_controller;
    JPanel m_panel;
    
 	
    public PaintUI(String title, MarkingMenuController markingMenu, PaintController controller){
        super(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 600));
        
        m_panel = new JPanel() {
            public void paintComponent(Graphics g) {
            	super.paintComponent(g);	
                
                Graphics2D g2 = (Graphics2D)g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(Color.YELLOW);
                g2.fillRect(0, 0, getWidth(), getHeight());
                
                g2.setColor(Color.BLACK);
                for(Pair<Shape, Color> shape: m_controller.getShape()) {
                	g2.setColor(shape.getValue());
                    g2.draw(shape.getKey());
                }
            }
        };
        m_controller = controller;
        m_panel.addMouseListener(m_controller);
        
        add(new JToolBar() {{
        	for(AbstractAction tool: controller.getTools()) {
        		add(tool);
        	}
        }}, BorderLayout.NORTH);
        m_panel.add(markingMenu);
        m_panel.addMouseMotionListener(markingMenu);
        
        add(m_panel);
        
        pack();
        setVisible(true);
    }
    
    public JPanel getPanel() {
    	return m_panel;
    }
    
    public void rePaint() {
    	m_panel.repaint();
    }
}