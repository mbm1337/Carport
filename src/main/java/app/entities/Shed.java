package app.entities;

public class Shed {
    private int id;
    public static int counter = 0;
    private int width;
    private int length;
    private boolean shedside;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isShedside() {
        return shedside;
    }

    public void setShedside(boolean shedside) {
        this.shedside = shedside;
    }

    public Shed(int width, int length, boolean shedside) {
        this.id = counter++;
        this.width = width;
        this.length = length;
        this.shedside = shedside;
    }
}
