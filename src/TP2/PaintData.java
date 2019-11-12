package TP2;

import static java.lang.Math.abs;
import static java.lang.Math.min;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.event.MouseInputListener;

import TP2.PaintController.State;
import javafx.util.Pair;


public class PaintData {
	PaintController m_controller;
	Vector<Pair<Shape, Color>> m_shapes = new Vector<Pair<Shape, Color>>();
	Color[] m_colors;
	Color m_colorSelected;
	
	Tool m_tools[];
	Tool m_tool;
	
	public PaintData(PaintController controller) {
		m_controller = controller;
		
		m_colorSelected = Color.pink;
		m_colors = new Color[] {
				Color.black,
				Color.green,
				Color.yellow,
				Color.pink,
				Color.orange
		};
		
		m_tools = new Tool[] {
				new Tool("Pen", m_controller) {
					public void mouseDragged(MouseEvent e) {
						if(m_controller.getState() == State.IDLE) {
							Path2D.Double path = (Path2D.Double)shape;
							if(path == null) {
								path = new Path2D.Double();
								path.moveTo(o.getX(), o.getY());
								m_shapes.add(new Pair<Shape, Color>(shape = path, m_colorSelected));
							}
							path.lineTo(e.getX(), e.getY());
							m_controller.repaint();			
						}
					}
				},
				new Tool("Rect", m_controller) {
					public void mouseDragged(MouseEvent e) {
							if(m_controller.getState() == State.IDLE) {
							Rectangle2D.Double rect = (Rectangle2D.Double)shape;
							if(rect == null) {
								rect = new Rectangle2D.Double(o.getX(), o.getY(), 0, 0);
								m_shapes.add(new Pair<Shape, Color>(shape = rect, m_colorSelected));
							}
							rect.setRect(min(e.getX(), o.getX()), min(e.getY(), o.getY()),
							             abs(e.getX()- o.getX()), abs(e.getY()- o.getY()));
							m_controller.repaint();
						}
					}
				},
				new Tool("Ellipse", m_controller) {
					public void mouseDragged(MouseEvent e) {
							if(m_controller.getState() == State.IDLE) {
							Ellipse2D.Double ellipse = (Ellipse2D.Double)shape;
							if(ellipse == null) {
								ellipse = new Ellipse2D.Double(o.getX(), o.getY(), 0, 0);
								m_shapes.add(new Pair<Shape, Color>(shape = ellipse, m_colorSelected));
							}
							ellipse.setFrame(min(e.getX(), o.getX()), min(e.getY(), o.getY()),
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
	
	public Vector<Pair<Shape, Color>> getShape(){
		return m_shapes;
	}

	public Tool getActiveTool() {
		return m_tool;
	}
}
