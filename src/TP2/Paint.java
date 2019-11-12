package TP2;
//////////////////////////////////////////////////////////////////////////////
// file    : Paint.java
// content : basic painting app
//////////////////////////////////////////////////////////////////////////////


import javax.swing.SwingUtilities;


/* paint *******************************************************************/

class Paint {/*extends JFrame {
	Vector<Shape> shapes = new Vector<Shape>();

	class Tool extends AbstractAction implements MouseInputListener {
	   Point o;
		Shape shape;
		public Tool(String name) { super(name); }
		public void actionPerformed(ActionEvent e) {
			System.out.println("using tool " + this);
			panel.removeMouseListener(tool);
			panel.removeMouseMotionListener(tool);
			tool = this;
			panel.addMouseListener(tool);
			panel.addMouseMotionListener(tool);
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
					shapes.add(shape = path);
				}
				path.lineTo(e.getX(), e.getY());
				panel.repaint();
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
				panel.repaint();
			}
		}
	};
	Tool tool;

	JPanel panel;
	
	public Paint(String title) {
		super(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(800, 600));
		add(new JToolBar() {{
			for(AbstractAction tool: tools) {
				add(tool);
			}
		}}, BorderLayout.NORTH);
		add(panel = new JPanel() {	
			public void paintComponent(Graphics g) {
				super.paintComponent(g);	
				Graphics2D g2 = (Graphics2D)g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				                    RenderingHints.VALUE_ANTIALIAS_ON);
		
				g2.setColor(Color.WHITE);
				g2.fillRect(0, 0, getWidth(), getHeight());
				
				g2.setColor(Color.BLACK);
				for(Shape shape: shapes) {
					g2.draw(shape);
				}
			}
		});

		pack();
		setVisible(true);
	}


/* main *********************************************************************/

	public static void main(String argv[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				PaintController paint = new PaintController("paint");
			}
		});
	}

}
