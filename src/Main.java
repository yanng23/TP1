import javax.swing.JFrame;
import javax.swing.JSlider;

public class Main {

	public static void main(String[] args) {
		JSlider bs;
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("HelloWorldSwing");
		frame.setSize(400, 100);

		RangeSlider le_m = new RangeSlider();
        frame.getContentPane().add(le_m);
        
        JSlider js = new JSlider();
        //frame.getContentPane().add(js);
        
        
        frame.setVisible(true);
	}

}
