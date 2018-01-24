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
	
	public static void main(String args[])
	{
		Rectangle rect = new Rectangle(5, 4);
		System.out.print("Rectangle: " + rect.width + " x " + rect.height + " = " + rect.getVolume());
	}
}
