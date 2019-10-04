import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.SliderUI;

@SuppressWarnings("serial")
public class RangeSlider extends JSlider {
	public RangeSlider() {
		setOrientation(JSlider.HORIZONTAL);
	}
	
    public static ComponentUI createUI(JComponent b)    {
        return new RangeSliderUI((RangeSlider)b);
    }
    
/*
    public SliderUI getUI() {
        return(RangeSliderUI)super.ui;
    }
    
    public void setUI(RangeSliderUI ui) {
        super.setUI(ui);
    }
*/
    public void updateUI() {
        setUI(new RangeSliderUI(this));
        updateLabelUIs();
    }
    
}
