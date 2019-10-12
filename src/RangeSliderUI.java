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
    public Rectangle thumbRectLeft = null;
    
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
    	
    	paintLeftThumb(g);
    }
	
	public void calculateLeftThumbLocation() {
		int val_left  = 10;
        int valuePosition = xPositionForValue(val_left);
        
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
    
    public void setUpperThumbLocation(int x, int y)  {
    	thumbRectLeft.x = x;
    	thumbRectLeft.y = y;
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
			
            offset = 0;
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
				System.out.println("Right clicked");
				offset = currentMouseX - thumbRect.x;
				m_state = State.DraggingRight;
				return;
			}
			//Clicked left thumb
			else if(thumbRectLeft.contains(currentMouseX, currentMouseY)) {
				System.out.println("Left clicked");
				offset = currentMouseX - thumbRect.x;
				m_state = State.DraggingLeft;
				return;
			}
			/*
			if (!SwingUtilities.isLeftMouseButton(e)) {
				return;
			}
			
			isDragging = false;
			slider.setValueIsAdjusting(true);
			
			Dimension sbSize = slider.getSize();
			int direction = POSITIVE_SCROLL;
			
			switch (slider.getOrientation()) {
			case JSlider.VERTICAL:
				if ( thumbRect.isEmpty() ) {
					int scrollbarCenter = sbSize.height / 2;
					if ( !drawInverted() ) {
						direction = (currentMouseY < scrollbarCenter) ?
								POSITIVE_SCROLL : NEGATIVE_SCROLL;
					}
					else {
						direction = (currentMouseY < scrollbarCenter) ?
								NEGATIVE_SCROLL : POSITIVE_SCROLL;
					}
				}
				else {
					int thumbY = thumbRect.y;
					if ( !drawInverted() ) {
						direction = (currentMouseY < thumbY) ?
								POSITIVE_SCROLL : NEGATIVE_SCROLL;
					}
					else {
						direction = (currentMouseY < thumbY) ?
								NEGATIVE_SCROLL : POSITIVE_SCROLL;
					}
				}
				break;
			case JSlider.HORIZONTAL:
				if ( thumbRect.isEmpty() ) {
					int scrollbarCenter = sbSize.width / 2;
					if ( !drawInverted() ) {
						direction = (currentMouseX < scrollbarCenter) ?
								NEGATIVE_SCROLL : POSITIVE_SCROLL;
					}
					else {
						direction = (currentMouseX < scrollbarCenter) ?
								POSITIVE_SCROLL : NEGATIVE_SCROLL;
					}
				}
				else {
					int thumbX = thumbRect.x;
					if ( !drawInverted() ) {
						direction = (currentMouseX < thumbX) ?
								NEGATIVE_SCROLL : POSITIVE_SCROLL;
					}
					else {
						direction = (currentMouseX < thumbX) ?
								POSITIVE_SCROLL : NEGATIVE_SCROLL;
					}
				}
				break;
			}
			
			if (shouldScroll(direction)) {
				scrollDueToClickInTrack(direction);
			}
			if (shouldScroll(direction)) {
				scrollTimer.stop();
				scrollListener.setDirection(direction);
				scrollTimer.start();
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
            System.out.println(m_state);
            if (m_state == State.DraggingLeft && m_state == State.DraggingRight) {
                return;
            }

            slider.setValueIsAdjusting(true);

            int halfThumbWidth = thumbRect.width / 2;
            int thumbLeft = e.getX() - offset;
            int trackLeft = trackRect.x;
            int trackRight = trackRect.x + (trackRect.width - 1);
            int hMax = xPositionForValue(slider.getMaximum() - slider.getExtent());
            trackRight = hMax;
            
            thumbLeft = Math.max(thumbLeft, trackLeft - halfThumbWidth);
            thumbLeft = Math.min(thumbLeft, trackRight - halfThumbWidth);
            System.out.println(thumbLeft + " " + thumbRect.y);
            
            if(m_state == State.DraggingLeft)
            	setThumbLocation(thumbLeft, thumbRect.y);
            else if (m_state == State.DraggingRight)
            	setUpperThumbLocation(thumbLeft, thumbRect.y);
            
            thumbMiddle = thumbLeft + halfThumbWidth;
            slider.setValue(valueForXPosition(thumbMiddle));
        }
	}
}
