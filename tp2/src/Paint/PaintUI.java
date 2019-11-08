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
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import MarkingMenu.MarkingMenuController;

@SuppressWarnings("serial")
class PaintUI extends JFrame {
	PaintController m_controller;
    JPanel m_panel;
    
	Vector<Shape> m_shapes = new Vector<Shape>();

	/*
    class Tool extends AbstractAction implements MouseInputListener {
 	   Point o;
 		Shape shape;
 		public Tool(String name) { super(name); }
 		public void actionPerformed(ActionEvent e) {
 			System.out.println("using tool " + this);
 			m_panel.removeMouseListener(tool);
 			m_panel.removeMouseMotionListener(tool);
 			tool = this;
 			m_panel.addMouseListener(tool);
 			m_panel.addMouseMotionListener(tool);
 		}
 		public void mouseClicked(MouseEvent e) {}
 		public void mouseEntered(MouseEvent e) {}
 		public void mouseExited(MouseEvent e) {}
 		public void mousePressed(MouseEvent e) { o = e.getPoint(); }
 		public void mouseReleased(MouseEvent e) { shape = null; }
 		public void mouseDragged(MouseEvent e) {}
 		public void mouseMoved(MouseEvent e) {}
 	}
 	
 	Tool tools[] = {
 		new Tool("pen") {
 			public void mouseDragged(MouseEvent e) {
 				Path2D.Double path = (Path2D.Double)shape;
 				if(path == null) {
 					path = new Path2D.Double();
 					path.moveTo(o.getX(), o.getY());
 					m_shapes.add(shape = path);
 				}
 				path.lineTo(e.getX(), e.getY());
 				m_panel.repaint();
 			}
 		},
 		new Tool("rect") {
 			public void mouseDragged(MouseEvent e) {
 				Rectangle2D.Double rect = (Rectangle2D.Double)shape;
 				if(rect == null) {
 					rect = new Rectangle2D.Double(o.getX(), o.getY(), 0, 0);
 					m_shapes .add(shape = rect);
 				}
 				rect.setRect(min(e.getX(), o.getX()), min(e.getY(), o.getY()),
 				             abs(e.getX()- o.getX()), abs(e.getY()- o.getY()));
 				m_panel.repaint();
 			}
 		}
 	};
 	Tool tool;*/
 	
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
                
                g2.setColor(Color.WHITE);
                g2.fillRect(0, 0, getWidth(), getHeight());
                
                g2.setColor(Color.BLACK);
                for(Shape shape: m_shapes) {
                    g2.draw(shape);
                }
            }
        };
        m_controller = controller;
        m_panel.add(markingMenu);
        add(new JToolBar() {{
        	for(AbstractAction tool: controller.getTools()) {
        		add(tool);
        	}
        }}, BorderLayout.NORTH);
        
        add(markingMenu);
        
        pack();
        setVisible(true);
    }
    
	public void setController(PaintController controller) {
		m_controller = controller;
		m_panel.addMouseListener(m_controller);
		
        add(new JToolBar() {{
	        for(AbstractAction tool: controller.getTools()) {
	            add(tool);
	        }
	    }}, BorderLayout.NORTH);
	}
}