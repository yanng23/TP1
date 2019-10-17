import javax.swing.JFrame;
import javax.swing.JSlider;

public class Main {

	public static void main(String[] args) {
		JFrame frame = new JFrame("RangeSlider");
		frame.setSize(400, 100);

		RangeSlider le_m = new RangeSlider();
		le_m.getModel().setExtent(20);
        frame.getContentPane().add(le_m);
        
        
        frame.setVisible(true);
	}

}
