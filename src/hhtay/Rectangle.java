package hhtay;

public class Rectangle {

	private int width;
	private int height;
	
	public Rectangle()
	{
	}
	
	public Rectangle(int w, int h)
	{
		width = w;
		height = h;
	}
	
	public int getVolume()
	{
		return width * height;
	}
}
