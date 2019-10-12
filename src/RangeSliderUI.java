import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicSliderUI;
import javax.swing.plaf.basic.BasicSliderUI.TrackListener;

public class RangeSliderUI extends BasicSliderUI {
	
    /** Thumb rectangle */
    public Rectangle thumbRectRight = null;
    
	public RangeSliderUI(RangeSlider b) {
		super(b);
	}
	
	@Override
    public TrackListener createTrackListener(JSlider slider) {
        return new RangeTrackListener();
    }
	
	@Override
    public void paint( Graphics g, JComponent c ) {
    	super.paint(g, c);
    	
    	paintRightThumb(g);
    }
	
    @Override
    protected void calculateThumbLocation() {
        // Call superclass method for lower thumb location.
        super.calculateThumbLocation();
        
		int valuePosition = xPositionForValue(slider.getValue() + slider.getExtent());

		thumbRectRight.x = valuePosition - (thumbRectRight.width / 2);
		thumbRectRight.y = trackRect.y;
    }
	
    /**
     * Calculates the thumb size rectangle.
     */
    protected void calculateRightThumbSize() {
        Dimension size = getThumbSize();
        thumbRectRight.setSize( size.width, size.height );
    }
	@Override
    protected void calculateGeometry() {
		super.calculateGeometry();
		
    	calculateRightThumbSize();
    	//calculateRightThumbLocation();
    }
    
    @Override
    public void installUI(JComponent c)   {
    	thumbRectRight = new Rectangle();
    	super.installUI(c);    	
    }
    
    /**
     * Paints the thumb.
     * @param g the graphics
     */
    public void paintRightThumb(Graphics g)  {
        Rectangle knobBounds = thumbRectRight;
        
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
    
    public void setRightThumbLocation(int x, int y)  {
        Rectangle upperUnionRect = new Rectangle();
        upperUnionRect.setBounds(thumbRectRight);

        thumbRectRight.setLocation(x, y);

        SwingUtilities.computeUnion(thumbRectRight.x, thumbRectRight.y, thumbRectRight.width, thumbRectRight.height, upperUnionRect);
        slider.repaint(upperUnionRect.x, upperUnionRect.y, upperUnionRect.width, upperUnionRect.height);
    }
    
	public enum State{
		DraggingLeft,
		DraggingRight,
		IDLE
	}
	
	public class RangeTrackListener extends BasicSliderUI.TrackListener {
		State m_state = State.IDLE;
		int offset;
		
		@Override
		public void mouseReleased(MouseEvent e) {
			System.out.println("IDLE");
			m_state = State.IDLE;
			slider.repaint();
            slider.setValueIsAdjusting(false);
            super.mouseReleased(e);
		}
		@Override
		public void mousePressed(MouseEvent e) {
			System.out.println("Clicked");
			if (!slider.isEnabled()) {
				return;
			}
			
			calculateGeometry();
			int currentMouseX = e.getX();
			int currentMouseY = e.getY();
			
			// Clicked right thumb?
			
			if (thumbRect.contains(currentMouseX, currentMouseY)) {
				System.out.println("Left clicked");
				offset = currentMouseX - thumbRect.x;
				m_state = State.DraggingLeft;
			}
			//Clicked left thumb
			/*
			if(thumbRectRight.contains(currentMouseX, currentMouseY)) {
				System.out.println("Left clicked");
				offset = currentMouseX - thumbRectRight.x;
				m_state = State.DraggingLeft;
			}*/
		}
		
		@Override
        public void mouseDragged(MouseEvent e) {
            int thumbMiddle;

            if (!slider.isEnabled()) {
                return;
            }

            currentMouseX = e.getX();
            currentMouseY = e.getY();

            slider.setValueIsAdjusting(true);
            
            int halfThumbWidth;
            int thumbLeft;
            int trackLeft;
            int trackRight;
            int hMax;
            int hMin;
            switch(m_state) {
            case DraggingLeft:
                halfThumbWidth = thumbRectRight.width / 2;
                thumbLeft = e.getX() - offset;
                trackLeft = trackRect.x;
                trackRight = trackRect.x + (trackRect.width - 1);
                hMax = xPositionForValue(slider.getValue() +
                                            slider.getExtent());

                trackRight = hMax;

                thumbLeft = Math.max(thumbLeft, trackLeft - halfThumbWidth);
                thumbLeft = Math.min(thumbLeft, trackRight - halfThumbWidth);

                setThumbLocation(thumbLeft, thumbRect.y);
                
                thumbMiddle = thumbLeft + halfThumbWidth;
                
                int diff = valueForXPosition(thumbMiddle) - slider.getValue();
                slider.setExtent(slider.getExtent() - diff);
                slider.setValue(valueForXPosition(thumbMiddle));
            	break;
            	
            case DraggingRight:
                halfThumbWidth = thumbRectRight.width / 2;
                thumbLeft = e.getX() - offset;
                trackLeft = trackRect.x;
                trackRight = trackRect.x + (trackRect.width - 1);
                hMax = xPositionForValue(slider.getMaximum() -
                                            slider.getExtent());

                trackRight = hMax;

                thumbLeft = Math.max(thumbLeft, trackLeft - halfThumbWidth);
                thumbLeft = Math.min(thumbLeft, trackRight - halfThumbWidth);

                setRightThumbLocation(thumbLeft, thumbRectRight.y);

                thumbMiddle = thumbLeft + halfThumbWidth;
                slider.setValue(valueForXPosition(thumbMiddle));
                System.out.println("Right:" + slider.getValue());
                break;
			default:
				return;
            }      
        }
	}
}
