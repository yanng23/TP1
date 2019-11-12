package Paint;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.event.MouseInputListener;

import MarkingMenu.MarkingMenuController;

@SuppressWarnings("serial")
public class PaintController implements MouseListener{
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
			}
		},
		new Tool("rect") {
			public void mouseDragged(MouseEvent e) {
			}
		},
		new Tool("rect") {
			public void mouseDragged(MouseEvent e) {
			}
		},
		new Tool("rect") {
			public void mouseDragged(MouseEvent e) {
			}
		},
		new Tool("rect") {
			public void mouseDragged(MouseEvent e) {
			}
		}
	};
		
	enum State{
			IDLE,
			MarkingMenu,
	}
	
	State m_state;
	PaintUI m_paintUI;
	
	MarkingMenuController m_markingMenu;
	
	public PaintController(String title) {
		m_state = State.IDLE;
		m_markingMenu = new MarkingMenuController();
		m_markingMenu.setTools(getTools());
		
		m_paintUI = new PaintUI(title, m_markingMenu, this);
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
			m_markingMenu.setActive(true);
			m_markingMenu.setOrigin(e.getPoint().x, e.getPoint().y);
			
			System.out.println("Changed state to MarkingMenu");			
			m_paintUI.rePaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(m_state == State.MarkingMenu) {
			m_state = State.IDLE;
			m_markingMenu.setActive(false);
			
			System.out.println("Changed state to IDLE");
			m_paintUI.rePaint();
		}
	}
	
	public Tool[] getTools() {
		return m_tools;
	}
}