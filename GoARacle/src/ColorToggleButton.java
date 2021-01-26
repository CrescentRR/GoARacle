import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JToggleButton;

public class ColorToggleButton extends JToggleButton{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void paintComponent(Graphics g) {
		Color bg,text;
		
		if(!isEnabled()) {
			bg=Color.decode("#36393E");
			text=Color.decode("#36393E");
		}
		
		if(getModel().isPressed()) {
			bg=Color.decode("#7289da");
			text=Color.decode("#ffffff");
			
		}
		else {
			bg=Color.decode("#36393E");
			text=Color.decode("#ffffff");
		}
		
		setBackground(bg);
		setForeground(text);
		
		
		super.paintComponent(g);
		
	}
}
