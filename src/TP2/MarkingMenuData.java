package TP2;

public class MarkingMenuData {
	public int x;
	public int y;
	public int rayon;
	public int mouseX;
	public int mouseY;
	public boolean isDrawn;
	
	public MarkingMenuData(int rayon) {
		this.x = 0;
		this.y = 0;
		this.rayon = rayon;
		this.isDrawn = false;
	}
}
