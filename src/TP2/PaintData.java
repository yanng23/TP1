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
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

import TP2.Paint.State;
import javafx.util.Pair;


public class PaintData {
	Paint m_controller;
	Vector<Pair<Shape, Color>> m_shapes = new Vector<Pair<Shape, Color>>();

	Color[] m_colors;
	Color m_color;
	
	Tool m_tools[];
	Tool m_tool;
	
	public PaintData(Paint controller) {
		m_controller = controller;
		
		m_color = Color.black;
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
						if(SwingUtilities.isLeftMouseButton(e) && m_controller.getState() == State.IDLE) {
							Path2D.Double path = (Path2D.Double)shape;
							if(path == null) {
								path = new Path2D.Double();
								path.moveTo(o.getX(), o.getY());
								m_shapes.add(new Pair<Shape, Color>(shape = path, m_color));
							}
							path.lineTo(e.getX(), e.getY());
							m_controller.repaint();			
						}
					}
				},
				new Tool("Rect", m_controller) {
					public void mouseDragged(MouseEvent e) {
							if(SwingUtilities.isLeftMouseButton(e) && m_controller.getState() == State.IDLE) {
							Rectangle2D.Double rect = (Rectangle2D.Double)shape;
							if(rect == null) {
								rect = new Rectangle2D.Double(o.getX(), o.getY(), 0, 0);
								m_shapes.add(new Pair<Shape, Color>(shape = rect, m_color));
							}
							rect.setRect(min(e.getX(), o.getX()), min(e.getY(), o.getY()),
							             abs(e.getX()- o.getX()), abs(e.getY()- o.getY()));
							m_controller.repaint();
						}
					}
				},
				new Tool("Ellipse", m_controller) {
					public void mouseDragged(MouseEvent e) {
							if(SwingUtilities.isLeftMouseButton(e) && m_controller.getState() == State.IDLE) {
							Ellipse2D.Double ellipse = (Ellipse2D.Double)shape;
							if(ellipse == null) {
								ellipse = new Ellipse2D.Double(o.getX(), o.getY(), 0, 0);
								m_shapes.add(new Pair<Shape, Color>(shape = ellipse, m_color));
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
	
	public Color[] getColors() {
		return m_colors;
	}
	
	class Tool extends AbstractAction implements MouseInputListener {
		   Point o;
		   String m_name;
			Shape shape;
			Paint m_paintController;
			public Tool(String name, Paint paintController) { 
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
		

	public void changeTool(Tool t) {
		m_tool = t;
	}
	
	public void changeColor(Color c) {
		m_color = c;
	}
	
	public void changeActiveColoredTool(Tool tool, Color color) {
		m_tool = tool;
		m_color = color;
	}
	
	public Vector<Pair<Shape, Color>> getShape(){
		return m_shapes;
	}

	public Tool getActiveTool() {
		return m_tool;
	}
}
