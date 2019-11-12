package Paint;
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

import MarkingMenu.MarkingMenuController;

@SuppressWarnings("serial")
class PaintUI extends JFrame {
	PaintController m_controller;
    JPanel m_panel;
    
	Vector<Shape> m_shapes = new Vector<Shape>();
 	
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
                for(Shape shape: m_shapes) {
                    g2.draw(shape);
                }
                
                System.out.println("Background printed");
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
        
        JButton b = new JButton("Submit");
        b.setBounds(50, 150, 100, 30);
        m_panel.add(b);
        
        add(m_panel);
        
        pack();
        setVisible(true);
    }
    
    public void rePaint() {
    	m_panel.repaint();
    }
    /*
	public void setController(PaintController controller) {
		m_controller = controller;
		m_panel.addMouseListener(m_controller);
		
        add(new JToolBar() {{
	        for(AbstractAction tool: controller.getTools()) {
	            add(tool);
	        }
	    }}, BorderLayout.NORTH);
	}*/
}