package TP2;

import static java.lang.Math.abs;
import static java.lang.Math.min;

import java.awt.Point;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.event.MouseInputListener;

import TP2.PaintController.State;


public class PaintData {
	PaintController m_controller;
	Vector<Shape> m_shapes = new Vector<Shape>();
	Tool m_tools[];
	Tool m_tool;
	
	public PaintData(PaintController controller) {
		m_controller = controller;
		
		m_tools = new Tool[] {
				new Tool("pen", m_controller) {
					public void mouseDragged(MouseEvent e) {
						if(m_controller.getState() == State.IDLE) {
							Path2D.Double path = (Path2D.Double)shape;
							if(path == null) {
								path = new Path2D.Double();
								path.moveTo(o.getX(), o.getY());
								m_shapes.add(shape = path);
							}
							path.lineTo(e.getX(), e.getY());
							m_controller.repaint();			
						}
					}
				},
				new Tool("rect", m_controller) {
					public void mouseDragged(MouseEvent e) {
							if(m_controller.getState() == State.IDLE) {
							Rectangle2D.Double rect = (Rectangle2D.Double)shape;
							if(rect == null) {
								rect = new Rectangle2D.Double(o.getX(), o.getY(), 0, 0);
								m_shapes.add(shape = rect);
							}
							rect.setRect(min(e.getX(), o.getX()), min(e.getY(), o.getY()),
							             abs(e.getX()- o.getX()), abs(e.getY()- o.getY()));
							m_controller.repaint();
						}
					}
				}
		};
	}
	
	public Tool[] getTools() {
		return m_tools;
	}
	
	class Tool extends AbstractAction implements MouseInputListener {
		   Point o;
		   String m_name;
			Shape shape;
			PaintController m_paintController;
			public Tool(String name, PaintController paintController) { 
				super(name); 
				m_name = name;
				m_paintController = paintController;
			}
			public void actionPerformed(ActionEvent e) {
				m_controller.switchTool(this);
				/*
				System.out.println("using tool " + this);
				panel.removeMouseListener(tool)
				panel.removeMouseMotionListener(tool);
				m_tool = this;
				panel.addMouseListener(tool);
				panel.addMouseMotionListener(tool);
				*/
			}
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) { o = e.getPoint(); }
			public void mouseReleased(MouseEvent e) { shape = null; }
			public void mouseDragged(MouseEvent e) {}
			public void mouseMoved(MouseEvent e) {}
			public String toString() {return m_name; }
		}
		
	


	public void changeActiveTool(Tool tool) {
		m_tool = tool;
	}
	
	public Vector<Shape> getShape(){
		return m_shapes;
	}

	public Tool getActiveTool() {
		return m_tool;
	}
}
