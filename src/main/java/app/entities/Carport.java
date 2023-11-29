package app.entities;

public class Carport {
    private int id;
    public static int counter = 0;
    private int width;
    private int length;
    private int height;
    private Shed shed;


    public Carport(int width, int length, int height) {
        this.id = counter++;
        this.width = width;
        this.length = length;
        this.height = height;
    }

    public Carport(int width, int length, int height, Shed shed) {
        this.id = counter++;
        this.width = width;
        this.length = length;
        this.height = height;
        this.shed = shed;
    }

}
