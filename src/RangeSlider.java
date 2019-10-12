import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSliderUI.TrackListener;

@SuppressWarnings("serial")
public class RangeSlider extends JSlider {
	
	public RangeSlider() {
		setOrientation(JSlider.HORIZONTAL);
	}
	
    public static ComponentUI createUI(JComponent b)    {
        return new RangeSliderUI((RangeSlider)b);
    }
    
    public void updateUI() {
        setUI(new RangeSliderUI(this));
        //setUI((RangeSliderUI)UIManager.getUI(this));
        updateLabelUIs();
    }
    
}
