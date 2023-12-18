package app.entities;

public class Shed {
    private int id;
    public static int counter = 0;
    private int width;
    private int length;


    public Shed(int width, int length) {
        this.id = counter++;
        this.width = width;
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }
}
