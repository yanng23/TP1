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
    	
    	paintMiddle(g);
    	paintThumb(g);
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
    @Override
    public void paintThumb(Graphics g)  {
        Rectangle knobBounds = thumbRect;
        
        int w = knobBounds.width;
        int h = knobBounds.height;

        g.translate(knobBounds.x, knobBounds.y);

        if ( slider.isEnabled() ) {
            g.setColor(slider.getBackground());
        }
        else {
            g.setColor(slider.getBackground().darker());
        }
        g.fillRect(0, 0, w, h);

        g.setColor(Color.black);
        g.drawLine(0, h-1, w-1, h-1);
        g.drawLine(w-1, 0, w-1, h-1);

        g.setColor(getHighlightColor());
        g.drawLine(0, 0, 0, h-2);
        g.drawLine(1, 0, w-2, 0);

        g.setColor(getShadowColor());
        g.drawLine(1, h-2, w-2, h-2);
        g.drawLine(w-2, 1, w-2, h-3);

        g.translate(-knobBounds.x, -knobBounds.y);
        
        knobBounds = thumbRectRight;
        
        w = knobBounds.width;
        h = knobBounds.height;

        g.translate(knobBounds.x, knobBounds.y);

        if ( slider.isEnabled() ) {
            g.setColor(slider.getBackground());
        }
        else {
            g.setColor(slider.getBackground().darker());
        }
        
        g.fillRect(0, 0, w, h);

        g.setColor(Color.black);
        g.drawLine(0, h-1, w-1, h-1);
        g.drawLine(w-1, 0, w-1, h-1);

        g.setColor(getHighlightColor());
        g.drawLine(0, 0, 0, h-2);
        g.drawLine(1, 0, w-2, 0);

        g.setColor(getShadowColor());
        g.drawLine(1, h-2, w-2, h-2);
        g.drawLine(w-2, 1, w-2, h-3);

        g.translate(-knobBounds.x, -knobBounds.y);
    }
    
    public void paintMiddle(Graphics g) {
    	int thick = 4;
    	g.translate(thumbRect.x + thumbRect.width / 2, thumbRect.y + thumbRect.height / 2 - thick / 2);// - thick/2);
    	g.fillRect(0, 0, thumbRectRight.x - thumbRect.x, 4);
    	g.translate(-(thumbRect.x + thumbRect.width / 2), -(thumbRect.y + thumbRect.height / 2 - thick / 2));
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
		SelectedRight,
		SelectedLeft,
		IDLE
	}
	
	public class RangeTrackListener extends BasicSliderUI.TrackListener {
		State m_state = State.IDLE;
		int offset;
		
		@Override
		public void mouseReleased(MouseEvent e) {
			switch(m_state) {
			case DraggingLeft:
				m_state = State.SelectedLeft;
				break;
			case DraggingRight:
				m_state = State.SelectedRight;
				break;
			}
			slider.repaint();
            slider.setValueIsAdjusting(false);
            super.mouseReleased(e);
		}
		@Override
		public void mousePressed(MouseEvent e) {
			if (!slider.isEnabled()) {
				return;
			}
			
			calculateGeometry();
			int currentMouseX = e.getX();
			int currentMouseY = e.getY();
			
			// Clicked right thumb?
			if (thumbRect.contains(currentMouseX, currentMouseY)) {
				offset = currentMouseX - thumbRect.x;
				m_state = State.DraggingLeft;
			}
			//Clicked left thumb
			else if(thumbRectRight.contains(currentMouseX, currentMouseY)) {
				offset = currentMouseX - thumbRectRight.x;
				m_state = State.DraggingRight;
			}
			else {
				click(e);
			}
		}
		
		public void click(MouseEvent e) {
			slider.setValueIsAdjusting(true);
            currentMouseX = e.getX();
            currentMouseY = e.getY();
            
			int direction;
			switch(m_state) {
			case SelectedLeft:
				direction = (currentMouseX < xPositionForValue(slider.getValue())) ? NEGATIVE_SCROLL : POSITIVE_SCROLL;
				switch(direction) {
				case POSITIVE_SCROLL:
					if(slider.getExtent() >= 2)
						MoveLeft(xPositionForValue(slider.getValue() + 2));
					else
						MoveLeft(xPositionForValue(slider.getValue()) + slider.getExtent());
					break;
				case NEGATIVE_SCROLL:
					if(slider.getValue() >= 2)
						MoveLeft(xPositionForValue(slider.getValue() - 2));
					else
						MoveLeft(xPositionForValue(0));
					break;
				}
				break;
			case SelectedRight:
				direction = (currentMouseX < xPositionForValue(slider.getValue() + slider.getExtent())) ? NEGATIVE_SCROLL : POSITIVE_SCROLL;
				switch(direction) {
				case POSITIVE_SCROLL:
					slider.setExtent(slider.getExtent() + 2);
					break;
				case NEGATIVE_SCROLL:
					slider.setExtent(slider.getExtent() - 2);
					break;
				}
				break;
			}
		}
		
		public void MoveLeft(int thumbMiddle) {
            int diff = valueForXPosition(thumbMiddle) - slider.getValue();
            slider.setExtent(slider.getExtent() - diff);
            slider.setValue(valueForXPosition(thumbMiddle));
		}
		
		public void MoveRight(int thumbMiddle) {
			
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
                MoveLeft(thumbMiddle);
            	break;
            	
            case DraggingRight:
                halfThumbWidth = thumbRect.width / 2;
                thumbLeft = currentMouseX - offset;
                trackLeft = trackRect.x;
                trackRight = trackRect.x + (trackRect.width - 1);
                hMin = xPositionForValue(slider.getValue());

                trackLeft = hMin;
                
                thumbLeft = Math.max(thumbLeft, trackLeft - halfThumbWidth);
                thumbLeft = Math.min(thumbLeft, trackRight - halfThumbWidth);

                setRightThumbLocation(thumbLeft, thumbRect.y);
                
                // Update slider extent.
                thumbMiddle = thumbLeft + halfThumbWidth;
                slider.setExtent(valueForXPosition(thumbMiddle) - slider.getValue());
                break;
			default:
				return;
            }      
        }
	}
}
