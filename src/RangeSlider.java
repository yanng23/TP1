import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSliderUI.TrackListener;

@SuppressWarnings("serial")
public class RangeSlider extends JSlider {
	
	public RangeSlider() {
		setOrientation(JSlider.HORIZONTAL);
	}
	
	public RangeSlider(int low, int up) {
		getModel().setValue(low);
		getModel().setExtent(up-low);
	}
	
	public int GetLow() {
		return getModel().getValue();
	}
	
	public int GetUp() {
		return getModel().getValue() + getModel().getExtent();
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
