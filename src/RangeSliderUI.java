import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicSliderUI;

public class RangeSliderUI extends BasicSliderUI {
    /** Thumb rectangle */
    protected Rectangle thumbRectLeft = null;
    
	public RangeSliderUI(RangeSlider b) {
		super(b);
	}
	
	@Override
    public void paint( Graphics g, JComponent c ) {
    	super.paint(g, c);
    	
    	paintLeftThumb(g);
    }
	
	public void calculateLeftThumbLocation() {
		int val_left  = 10;
        int valuePosition = xPositionForValue(val_left);
        
        System.out.println(valuePosition);
        
        thumbRectLeft.x = valuePosition - (thumbRectLeft.width / 2);
        thumbRectLeft.y = trackRect.y;
	}
	
    /**
     * Calculates the thumb size rectangle.
     */
    protected void calculateLeftThumbSize() {
        Dimension size = getThumbSize();
        thumbRectLeft.setSize( size.width, size.height );
    }
	@Override
    protected void calculateGeometry() {
		super.calculateGeometry();
		
    	calculateLeftThumbSize();
    	calculateLeftThumbLocation();
    }
    
    @Override
    public void installUI(JComponent c)   {
    	thumbRectLeft = new Rectangle();
    	super.installUI(c);    	
    }
    
    /**
     * Paints the thumb.
     * @param g the graphics
     */
    public void paintLeftThumb(Graphics g)  {
        Rectangle knobBounds = thumbRectLeft;
        
        int w = knobBounds.width;
        int h = knobBounds.height;

        g.translate(knobBounds.x, knobBounds.y);

        if ( slider.isEnabled() ) {
            g.setColor(slider.getBackground());
        }
        else {
            g.setColor(slider.getBackground().darker());
        }
        
            int cw = w / 2;
            g.fillRect(1, 1, w-3, h-1-cw);
            Polygon p = new Polygon();
            p.addPoint(1, h-cw);
            p.addPoint(cw-1, h-1);
            p.addPoint(w-2, h-1-cw);
            g.fillPolygon(p);

            g.setColor(getHighlightColor());
            g.drawLine(0, 0, w-2, 0);
            g.drawLine(0, 1, 0, h-1-cw);
            g.drawLine(0, h-cw, cw-1, h-1);

            g.setColor(Color.black);
            g.drawLine(w-1, 0, w-1, h-2-cw);
            g.drawLine(w-1, h-1-cw, w-1-cw, h-1);

            g.setColor(getShadowColor());
            g.drawLine(w-2, 1, w-2, h-2-cw);
            g.drawLine(w-2, h-1-cw, w-1-cw, h-2);

        g.translate(-knobBounds.x, -knobBounds.y);
    }
}
