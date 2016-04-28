package sample;

import javax.swing.ImageIcon;

public class Dice {
	private int value;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}

	private ImageIcon icon;

	public Dice(int value, ImageIcon icon) {
		this.value = value;
		this.icon = icon;
	}

}
