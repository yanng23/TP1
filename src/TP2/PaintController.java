package TP2;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import TP2.PaintData.Tool;

@SuppressWarnings("serial")
public class PaintController implements MouseListener{
	/*
	public class Tool extends AbstractAction implements MouseInputListener {
		   Point o;
			Shape shape;
			String m_name;
			public Tool(String name) { 
				super(name); 
				m_name = name;
			}
			public void actionPerformed(ActionEvent e) {
				System.out.println("using tool " + this);
			}
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) { o = e.getPoint(); }
			public void mouseReleased(MouseEvent e) { shape = null; }
			public void mouseDragged(MouseEvent e) {}
			public void mouseMoved(MouseEvent e) {}
			public String toString() { return m_name; }
	}
		
	Tool m_tools[] = {
			new Tool("pen") {
				public void mouseDragged(MouseEvent e) {
					Path2D.Double path = (Path2D.Double)shape;
					if(path == null) {
						path = new Path2D.Double();
						path.moveTo(o.getX(), o.getY());
						shapes.add(shape = path);
					}
					path.lineTo(e.getX(), e.getY());
					m_paintUI.repaint();
				}
			},
			new Tool("rect") {
				public void mouseDragged(MouseEvent e) {
					Rectangle2D.Double rect = (Rectangle2D.Double)shape;
					if(rect == null) {
						rect = new Rectangle2D.Double(o.getX(), o.getY(), 0, 0);
						shapes.add(shape = rect);
					}
					rect.setRect(min(e.getX(), o.getX()), min(e.getY(), o.getY()),
					             abs(e.getX()- o.getX()), abs(e.getY()- o.getY()));
					m_paintUI.repaint();
				}
			}
	};*/
		
	enum State{
			IDLE,
			MarkingMenu,
	}
	
	State m_state;
	PaintUI m_paintUI;
	PaintData m_data;
	
	MarkingMenuController m_markingMenu;
	
	public PaintController(String title) {
		m_state = State.IDLE;
		m_data = new PaintData(this);
		
		m_markingMenu = new MarkingMenuController();
		m_markingMenu.setTools(m_data.getTools());
	
		m_paintUI = new PaintUI(title, m_markingMenu, this);
		m_markingMenu.setPaintUI(m_paintUI);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(m_state == State.IDLE && e.getButton() == MouseEvent.BUTTON3) {
			m_state = State.MarkingMenu;
			m_markingMenu.mousePressed();
			m_markingMenu.setOrigin(e.getPoint());
			//Without it the red line is starting at 0,0 before a mouse motion
			m_markingMenu.setMousePosition(e.getPoint());
			
			System.out.println("Changed state to MarkingMenu");			
			m_paintUI.rePaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(m_state == State.MarkingMenu) {
			m_state = State.IDLE;
			m_markingMenu.mouseReleased();
			
			System.out.println("Changed state to IDLE");
			m_paintUI.rePaint();
		}
	}

	public void repaint() {
		m_paintUI.rePaint();
	}
	
	public Tool[] getTools() {
		return m_data.getTools();
	}
	
	public Vector<Shape> getShape(){
		return m_data.getShape();
	}

	public void switchTool(Tool tool) {
		System.out.println("using tool " + this.toString());
		m_paintUI.getPanel().removeMouseListener(m_data.getActiveTool());
		m_paintUI.getPanel().removeMouseMotionListener(m_data.getActiveTool());
		m_data.changeActiveTool(tool);
		m_paintUI.getPanel().addMouseListener(tool);
		m_paintUI.getPanel().addMouseMotionListener(tool);
	}
}