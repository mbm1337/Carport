package app.util;

public class ShedCalculator {

    private int length;
    private int width;

    public ShedCalculator(int length, int width) {
        this.length = length;
        this.width = width;
    }

    public int shedArea(int length, int width){
        return length * width;

    }
}
