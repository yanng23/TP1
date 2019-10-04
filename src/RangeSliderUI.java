import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;

import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicSliderUI;

public class RangeSliderUI extends BasicSliderUI {

	public RangeSliderUI(RangeSlider b) {
		super(b);
	}
	
	private Shape getCursorShape() {
		Rectangle shape = new Rectangle(3, 10);
		return shape;
	}

	@Override
    public void paint( Graphics g, JComponent c ) {
    	super.paint(g, c);
    	/*Shape shape = getCursorShape();

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.CYAN);
        g2d.fill(shape);

        g2d.setColor(Color.BLUE);
        g2d.draw(shape);
        
        g2d.dispose();*/
    }
}
